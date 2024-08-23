package br.gov.ce.sop.convenios.security.filter;

import br.gov.ce.sop.convenios.api.exception.WarningException;
import br.gov.ce.sop.convenios.security.dto.UserDetailsDTO;
import br.gov.ce.sop.convenios.security.service.TokenService;
import br.gov.ce.sop.convenios.security.service.UserDetailsService;
import br.gov.ce.sop.convenios.webClient.RequestService;
import jakarta.servlet.FilterChain;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.AllArgsConstructor;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.lang.NonNull;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.util.AntPathMatcher;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;

@AllArgsConstructor
public class TokenAuthenticationFilter extends OncePerRequestFilter {
    private final String sistemaSlug;
    private final JdbcTemplate jdbcTemplate;
    private final TokenService tokenService;
    private final String[] AUTH_WHITELIST;
    private final RequestService requestService;
    private final String baseUrlControleAcesso;

    @Override
    protected void doFilterInternal(@NonNull HttpServletRequest request, @NonNull HttpServletResponse response, @NonNull FilterChain filterChain) throws IOException {
        try {

            String tokenFromHeader = tokenService.getTokenFromHeader(request);
            if (!StringUtils.hasText(tokenFromHeader)) throw new WarningException("Token não enviado na requisição");

            boolean tokenValid = tokenService.isTokenValid(tokenFromHeader);
            if (!tokenValid) throw new WarningException("Token inválido");

            this.authenticate(tokenFromHeader);

            response.setHeader("Access-Control-Expose-Headers", "Content-Disposition");
            filterChain.doFilter(request, response);
        } catch (Exception e) {
            if (!response.isCommitted()) {
                response.sendError(403, e.getMessage());
            }
        }
    }

    @Override
    protected boolean shouldNotFilter(@NonNull HttpServletRequest request) {
        AntPathMatcher pathMatcher = new AntPathMatcher();
        return Arrays.stream(AUTH_WHITELIST)
                .anyMatch(p -> pathMatcher.match(p, request.getServletPath()));
    }

    private void authenticate(String tokenFromHeader) {
        String matricula = tokenService.getTokenId(tokenFromHeader);

        if (StringUtils.hasText(matricula)) {
            List<String> roles = getRolesByMatriculaAndSistemaSlug(matricula, sistemaSlug);
            UserDetailsDTO user = new UserDetailsDTO();
            user.setUsuario(matricula);

            UserDetails userDetails = UserDetailsService.build(user, getAuthorities(roles));
            UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken = new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());

            SecurityContextHolder.getContext().setAuthentication(usernamePasswordAuthenticationToken);
        }
    }

    private Collection<? extends GrantedAuthority> getAuthorities(List<String> roles) {
        return roles.stream().map(SimpleGrantedAuthority::new).toList();
    }

    private List<String> getRolesByMatriculaAndSistemaSlug(String matricula, String sisSlug) {
//        String[] strings = requestService.requestToUniqueModel(String[].class, () -> Request.builder()
//                .baseUrl(baseUrlControleAcesso)
//                .method(HttpMethod.GET)
//                .endPoint("/role/user/sistema/" + matricula + "/" + sisSlug)
//                .build()
//        );
//        return Arrays.asList(strings);
        return jdbcTemplate.queryForList(
                "select r.role_name from security.roles r " +
                        "join security.roles_x_sistema rs on rs.id_role = r.id_role and rs.ativo " +
                        "join security.sistema s on s.id = rs.id_sistema " +
                        "join security.users_x_roles ur on ur.id_role = r.id_role and ur.id_status = 1 " +
                        "where ur.id_user = ? and s.slug = ? and r.id_status = 1"
                , String.class, matricula, sisSlug);
    }
}
