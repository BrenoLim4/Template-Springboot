package br.gov.ce.sop.convenios.api.controller;

import br.gov.ce.sop.convenios.api.dto.*;
import br.gov.ce.sop.convenios.api.dto.filter.*;
import br.gov.ce.sop.convenios.api.dto.parametros.*;
import br.gov.ce.sop.convenios.model.entity.celebracao.StatusCelebracao;
import br.gov.ce.sop.convenios.model.entity.celebracao.view.*;
import br.gov.ce.sop.convenios.model.enums.TipoAnalise;
import br.gov.ce.sop.convenios.model.service.impl.celebracao.CelebracaoAguardandoParecerServiceImpl;
import br.gov.ce.sop.convenios.model.service.impl.celebracao.CelebracaoAguardandoPublicacaoServiceImpl;
import br.gov.ce.sop.convenios.model.service.interfaces.celebracao.*;
import br.gov.ce.sop.convenios.model.service.interfaces.convenio.PrefeituraService;
import br.gov.ce.sop.convenios.security.service.TokenService;
import br.gov.ce.sop.convenios.utils.PaginationUtils;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Objects;

import static br.gov.ce.sop.convenios.utils.MaskUtils.cpfMask;

@RestController
@RequiredArgsConstructor
@RequestMapping("/celebracao")
public class CelebracaoController {
    private final CelebracaoService celebracaoService;
    private final PrefeituraService prefeituraService;
    private final VoCelebracaoEnviosService VoCelebracaoEnviosService;
    private final VoCelebracaoAnalisesService voCelebracaoAnalisesService;
    private final VoCelebracaoReprovadosService voCelebracaoReprovadosService;
    private final VoCelebracaoDetalheResumoService voCelebracaoDetalheResumoService;
    private final VoCelebracaoEnvioDocumentosService voCelebracaoEnvioDocumentosService;
    private final VoCelebracaoAnaliseDocumentosService voCelebracaoAnaliseDocumentosService;
    private final VoCelebracaoReprovadoDocumentosService voCelebracaoReprovadoDocumentosService;
    private final StatusCelebracaoService statusCelebracaoService;
    private final VoCelebracaoAdministracaoService voCelebracaoAdministracaoService;
    private final CelebracaoAguardandoParecerServiceImpl celebracaoAguardandoParecerService;
    private final CelebracaoAguardandoPublicacaoServiceImpl celebracaoAguardandoPublicacaoService;
    private final TokenService tokenService;

    @GetMapping("status")
    @Operation(security = {@SecurityRequirement(name = "bearer-key")})
    public List<StatusCelebracao> getAllStatus() {
        return statusCelebracaoService.getAll();
    }

    @PostMapping("/lista-envios")
    @Operation(security = {@SecurityRequirement(name = "bearer-key")})
    public PostResponseDTO<VoCelebracaoEnvios> listaEnviosCelebracao(@RequestBody PostRequestDTO<FilterEnviosDTO> postRequestDTO) {
        FilterEnviosDTO filterEnviosDTO = postRequestDTO.getFilters() != null
                ? postRequestDTO.getFilters()
                : new FilterEnviosDTO();

        filterEnviosDTO.setIdConvenente(prefeituraService.getIdPessoa());

        Page<VoCelebracaoEnvios> enviosCelebracoesPage =
                VoCelebracaoEnviosService.findAllByQuery(filterEnviosDTO,
                        PaginationUtils.applyPagination(postRequestDTO));

        return new PostResponseDTO<>(enviosCelebracoesPage);
    }

    @PostMapping("/lista-analise")
    @Operation(security = {@SecurityRequirement(name = "bearer-key")})
    public PostResponseDTO<VoCelebracaoAnalises> listaAnaliseCelebracao(@RequestBody PostRequestDTO<FilterAnaliseDTO> postRequestDTO) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();

        FilterAnaliseDTO filterAnaliseDTO = postRequestDTO.getFilters() != null
                ? postRequestDTO.getFilters()
                : new FilterAnaliseDTO();

        if (auth.getAuthorities().stream().anyMatch(a ->  a.toString().equals("CONVENIO.ADMINISTRATIVO"))){
            filterAnaliseDTO.setIdTipoAnalise(0);
        } else {
            filterAnaliseDTO.setMatriculaEngenheiro(auth.getName());
        }

        Page<VoCelebracaoAnalises> analiseCelebracoesPage =
                voCelebracaoAnalisesService.findAllByQuery(filterAnaliseDTO,
                        PaginationUtils.applyPagination(postRequestDTO));
        return new PostResponseDTO<>(analiseCelebracoesPage);
    }

    @PostMapping("/lista-reprovados")
    @Operation(security = {@SecurityRequirement(name = "bearer-key")})
    public PostResponseDTO<VoCelebracaoReprovados> listaReprovadoCelebracao(@RequestBody PostRequestDTO<FilterReprovadoDTO> postRequestDTO) {
        FilterReprovadoDTO filterReprovadoDTO = postRequestDTO.getFilters() != null
                ? postRequestDTO.getFilters()
                : new FilterReprovadoDTO();

        filterReprovadoDTO.setIdConvenente(prefeituraService.getIdPessoa());

        Page<VoCelebracaoReprovados> recusadosCelebracoesPage =
                voCelebracaoReprovadosService.findAllByQuery(filterReprovadoDTO,
                        PaginationUtils.applyPagination(postRequestDTO));

        return new PostResponseDTO<>(recusadosCelebracoesPage);
    }

    @PostMapping("/administrativo")
    @Operation(security = {@SecurityRequirement(name = "bearer-key")})
    public PostResponseDTO<VoCelebracaoAdministracao> celabracoesFromAdministrativo(@RequestBody PostRequestDTO<FilterCelebracaoAdministrativoDTO> postRequestDTO) {
        return voCelebracaoAdministracaoService.getPage(postRequestDTO);
    }

    @GetMapping("/administrativo/{id}")
    @Operation(security = {@SecurityRequirement(name = "bearer-key")})
    public VoCelebracaoAdministracao getCelabracaoAdministrativoByid(@PathVariable Integer id) {
        return voCelebracaoAdministracaoService.getEntityById(id);
    }

    @PostMapping("/administrativo/enviar-formulario-abertura-conta")
    @Operation(security = {@SecurityRequirement(name = "bearer-key")}, description = "Enviar por email formulário de abertura de conta.")
    public String enviarFormularioAberturaConta(@RequestBody @Valid RParamsFormAberturaConta params) {
        return celebracaoService.enviarFormAberturaConta(params);
    }

    @PostMapping("/administrativo/arquivar")
    @Operation(security = {@SecurityRequirement(name = "bearer-key")}, description = "Arquivar processo de celebração.")
    public String arquivar(@RequestBody @Valid RParamsArquivarCelebracao params) {
        return celebracaoService.arquivar(params);
    }

    @PostMapping("/lista-aguardando-parecer")
    @Operation(security = {@SecurityRequirement(name = "bearer-key")})
    public PostResponseDTO<VoCelebracaoAguardandoParecer> celabracoesAguardandoParecer(@RequestBody PostRequestDTO<FilterCelebracaoAguardandoParecerDTO> postRequestDTO) {
        return celebracaoAguardandoParecerService.getPage(postRequestDTO);
    }

    @PostMapping("/lista-aguardando-publicacao")
    @Operation(security = {@SecurityRequirement(name = "bearer-key")})
    public PostResponseDTO<VoCelebracaoAguardandoPublicacao> celabracoesAguardandoPublicacao(@RequestBody PostRequestDTO<FilterCelebracaoAguardandoPublicacaoDTO> postRequestDTO) {
        return celebracaoAguardandoPublicacaoService.getPage(postRequestDTO);
    }

    @GetMapping("/aguardando-parecer/documentos/{idCelebracao}")
    @Operation(security = {@SecurityRequirement(name = "bearer-key")})
    public List<VoCelebracaoAguardandoParecerDocumentos> getAguardandoParecerDocumentos(@PathVariable Integer idCelebracao) {
        return celebracaoAguardandoParecerService.findAllDocumentsByIdCelebracao(idCelebracao);
    }

    @GetMapping("/detalhes_resumo")
    @Operation(security = {@SecurityRequirement(name = "bearer-key")})
    public VoCelebracaoDetalheResumo celebracaoDetalhesResumo(@RequestParam Integer idCelebracao){
        VoCelebracaoDetalheResumo resumoCelebracao = voCelebracaoDetalheResumoService.getEntityById(idCelebracao);
        resumoCelebracao.setCpfPrefeito(cpfMask(resumoCelebracao.getCpfPrefeito(),'*'));
        return !TokenService.isConvenente() || Objects.equals(resumoCelebracao.getCnpjConvenente(), TokenService.getTokenUsername())
                ? resumoCelebracao
                : new VoCelebracaoDetalheResumo();
    }

    @PostMapping("/documentos_envio")
    @Operation(security = {@SecurityRequirement(name = "bearer-key")})
    public PostResponseDTO<VoCelebracaoEnvioDocumentos> celebracaoDocumentosEnvio(@RequestBody PostRequestDTO<CelebracaoEnvioDocumentosDTO> params){
        params.setSize(Integer.MAX_VALUE);

        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        VoCelebracaoDetalheResumo resumoCelebracao = voCelebracaoDetalheResumoService
                .getEntityById(params.getFilters().getIdCelebracao());

        if (!Objects.equals(resumoCelebracao.getCnpjConvenente(), auth.getName())) {
            return new PostResponseDTO<>();
        }

        CelebracaoEnvioDocumentosDTO filterCelebracaoEnvioDocumento = params.getFilters() != null
                ? params.getFilters()
                : new CelebracaoEnvioDocumentosDTO();

        Page<VoCelebracaoEnvioDocumentos> documentosCelebracoesPage =
                voCelebracaoEnvioDocumentosService.buscarDocumentosEnvio(filterCelebracaoEnvioDocumento,
                        PaginationUtils.applyPagination(params));

        return new PostResponseDTO<>(documentosCelebracoesPage);
    }

    @GetMapping("/reprovado/documentos/{idCelebracao}")
    @Operation(security = {@SecurityRequirement(name = "bearer-key")})
    public List<CelebracaoReprovadoDocumentosDTO> celebracaoDocumentosReprovado(@PathVariable Integer idCelebracao){

        VoCelebracaoDetalheResumo resumoCelebracao = voCelebracaoDetalheResumoService
                .getEntityById(idCelebracao);

        if (!Objects.equals(resumoCelebracao.getCnpjConvenente(), TokenService.getTokenUsername())) {
            return null;
        }

        return voCelebracaoReprovadoDocumentosService.findDocumentosReprovado(idCelebracao);
    }

    @PostMapping("/finalizar-envio")
    @Operation(security = {@SecurityRequirement(name = "bearer-key")}, description = "Finalizar envio da documentação básica do administrativo e da Engenharia.")
    public String finalizarEnvioDocumentacao(@RequestBody RFinalizarEnvio params){
        return celebracaoService.finalizarEnvioDocumentos(params);
    }

    @PostMapping("/analise/finalizar")
    @Operation(security = {@SecurityRequirement(name = "bearer-key")}, description = "Finalizar envio da documentação básica do administrativo e da Engenharia.")
    public String finalizarEnvioDocumentacao(@RequestBody @Valid RFinalizarAnalise params){
        return celebracaoService.finalizarAnalise(params);
    }

    @PostMapping("/finalizar-reprovado/{idCelebracao}")
    @Operation(security = {@SecurityRequirement(name = "bearer-key")}, description = "Finalizar envio da documentação básica do administrativo e da Engenharia.")
    public String finalizarCorrecaoDocumentos(@PathVariable Integer idCelebracao){
        return celebracaoService.finalizarCorrecaoDocumento(idCelebracao);
    }

    @PostMapping("/finalizar-parecer")
    @Operation(security = {@SecurityRequirement(name = "bearer-key")}, description = "Finalizar envio da documentação básica do administrativo e da Engenharia.")
    public String finalizarCadastroParecer(@RequestBody @Valid RCadastrarParecerGecov params){
        return celebracaoService.finalizarCadastroParecer(params);
    }

    @GetMapping("/analise/documentos/{idCelebracao}")
    public List<CelebracaoAnaliseDocumentosDTO> getDocumentosAnalise(@PathVariable Integer idCelebracao){
        if (TokenService.getRoles().stream().anyMatch("CONVENIO.ADMINISTRATIVO"::equals)){
            return voCelebracaoAnaliseDocumentosService.findDocumentosParaAnalise(idCelebracao, TipoAnalise.ADMINISTRATIVA.ordinal());
        }
        return voCelebracaoAnaliseDocumentosService.findDocumentosParaAnalise(idCelebracao, TipoAnalise.ENGENHARIA.ordinal());
    }
}
