package br.gov.ce.sop.convenios.security;

import br.gov.ce.sop.convenios.security.filter.TokenAuthenticationFilter;
import br.gov.ce.sop.convenios.security.handler.CustomAccessDeniedHandler;
import br.gov.ce.sop.convenios.security.handler.CustomAuthenticationEntryPoint;
import br.gov.ce.sop.convenios.security.service.TokenService;
import br.gov.ce.sop.convenios.webClient.RequestService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@EnableWebSecurity
@Configuration
@RequiredArgsConstructor
@EnableMethodSecurity
public class SecurityConfiguration {

    @Value("${sistema.slug}")
    private String sistemaSlug;
    private final TokenService tokenService;
    @Value("base-url-api.controle-acesso")
    private String baseUrlControleAcesso;
    private final RequestService requestService;
    private final JdbcTemplate jdbcTemplate;

    // Endpoints que não serão autenticados, disponíveis publicamente
    private static final String[] AUTH_WHITELIST = {
            "/configuration/ui",
            "/configuration/security",
            "/webjars/**",
            // -- Swagger UI v3 (OpenAPI)
            "/v3/api-docs/**",
            "/swagger-resources",
            "/swagger-resources/**",
            "/swagger-ui.html",
            "/swagger-ui/**",
            "/login",
            "/actuator/**",
            "/error",
//            "/doc-digital/**"
//            "/teste/**"
    };

    //     Configuration for authorization
    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
                .cors().and()
                .csrf().disable()
                .addFilterAfter(new TokenAuthenticationFilter(sistemaSlug,jdbcTemplate, tokenService, AUTH_WHITELIST, requestService, baseUrlControleAcesso), UsernamePasswordAuthenticationFilter.class)
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers(AUTH_WHITELIST).permitAll()
                        .anyRequest().authenticated()
                )
                .exceptionHandling()
                .accessDeniedHandler(new CustomAccessDeniedHandler())
                .authenticationEntryPoint(new CustomAuthenticationEntryPoint())
                .and().sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);
        return http.build();
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration) throws Exception {
        return authenticationConfiguration.getAuthenticationManager();
    }

}