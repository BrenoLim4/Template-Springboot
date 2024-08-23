package br.gov.ce.sop.convenios.api.controller;

import br.gov.ce.sop.convenios.api.dto.PostRequestDTO;
import br.gov.ce.sop.convenios.api.dto.PostResponseDTO;
import br.gov.ce.sop.convenios.api.dto.filter.FilterConsultaCelebracaoDTO;
import br.gov.ce.sop.convenios.model.entity.celebracao.view.VoCelebracaoConsulta;
import br.gov.ce.sop.convenios.model.entity.celebracao.view.VoCelebracaoConsultaAnalisesDocumento;
import br.gov.ce.sop.convenios.model.service.interfaces.ReportService;
import br.gov.ce.sop.convenios.model.service.interfaces.celebracao.AnaliseXDocumentoService;
import br.gov.ce.sop.convenios.model.service.interfaces.celebracao.CelebracaoConsultaService;
import br.gov.ce.sop.convenios.report.dto.PostRequestReportDTO;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/celebracao-consulta")
@RequiredArgsConstructor
public class CelebracaoConsultaController {
    private final CelebracaoConsultaService service;
    private final ReportService reportService;
    private final AnaliseXDocumentoService analiseXDocumentoService;

    private final String TITULO_RELATORIO = "Lista de Celebrações";
    private final String FILE_NAME = "lista-celebracoes";


    @PostMapping("get-page")
    @Operation(security = {@SecurityRequirement(name = "bearer-key")}, description = "Consultar Informações da celebração de forma paginada.")
    public PostResponseDTO<VoCelebracaoConsulta> getPage(@RequestBody PostRequestDTO<FilterConsultaCelebracaoDTO> request) {
        return service.getPage(request);
    }

    @GetMapping("{id}")
    @Operation(security = {@SecurityRequirement(name = "bearer-key")}, description = "Consultar detalhes da celebração")
    public VoCelebracaoConsulta getById(@PathVariable Integer id) {
        return service.getEntityById(id);
    }

    @GetMapping("documentos-analise/{id}")
    @Operation(security = {@SecurityRequirement(name = "bearer-key")}, description = "Consultar Documentos por análise")
    public List<VoCelebracaoConsultaAnalisesDocumento> getByDocumentosAnalise(@PathVariable Integer id) {
        return analiseXDocumentoService.findAllVoDocumentosAnaliseById(id);
    }

    @PostMapping("export-pdf")
    @Operation(security = {@SecurityRequirement(name = "bearer-key")}, description = "Exportar pdf da lista de celebrações.")
    public HttpEntity<byte[]> exportPdf(@RequestBody PostRequestReportDTO<FilterConsultaCelebracaoDTO, VisibleColumnsCelebracao> bodyRequest){
        List<VoCelebracaoConsulta> celebracoes = service.getPage(bodyRequest.request()).getResults();
        final byte[] data = reportService.generateDefaultReportPdf(celebracoes, bodyRequest.visibleColumns(), TITULO_RELATORIO, FILE_NAME);
        return ResponseEntity.ok()
                .contentType(MediaType.parseMediaType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet"))
                .contentLength(data.length)
                .header(HttpHeaders.CONTENT_DISPOSITION, String.format("inline; filename=%s.pdf", FILE_NAME))
                .body(data);
    }

    @PostMapping("export-planilha")
    @Operation(security = {@SecurityRequirement(name = "bearer-key")}, description = "Exportar planilha da lista de celebrações.")
    public HttpEntity<byte[]> exportXlx(@RequestBody PostRequestReportDTO<FilterConsultaCelebracaoDTO, VisibleColumnsCelebracao> bodyRequest){
        List<VoCelebracaoConsulta> celebracoes = service.getPage(bodyRequest.request()).getResults();
        byte[] data = reportService.generateDefaultReportXlsx(celebracoes, bodyRequest.visibleColumns(), TITULO_RELATORIO, FILE_NAME);
        return ResponseEntity.ok()
                .contentType(MediaType.parseMediaType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet"))
                .contentLength(data.length)
                .header(HttpHeaders.CONTENT_DISPOSITION, String.format("inline; filename=%s.xlsx", FILE_NAME))
                .body(data);
    }
}
