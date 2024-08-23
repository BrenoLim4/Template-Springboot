package br.gov.ce.sop.convenios.api.controller;

import br.gov.ce.sop.convenios.api.dto.HashDocumentoDigitalDTO;
import br.gov.ce.sop.convenios.api.dto.parametros.*;
import br.gov.ce.sop.convenios.model.dto.DocumentosDigitais;
import br.gov.ce.sop.convenios.model.entity.celebracao.view.VoHistoricoDocumento;
import br.gov.ce.sop.convenios.model.entity.celebracao.view.VoListaDocumentosDigitaisAssinadores;
import br.gov.ce.sop.convenios.model.service.impl.documentodigital.DocumentoDigitalServiceProxyImpl;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.Base64;
import java.util.List;

@RestController
@AllArgsConstructor
@RequestMapping("/doc-digital")
public class DocumentoDigitalController {

    private final DocumentoDigitalServiceProxyImpl documentoDigitalService;

    @PostMapping("/upload-documento")
    @Operation(security = {@SecurityRequirement(name = "bearer-key")})
    public ResponseEntity<String> uploadDocumento(@RequestBody @Valid RParamsUploadDocumento params) {
        documentoDigitalService.uploadDocumento(params);
        return ResponseEntity.ok("Upload realizado com sucesso!");
    }

    @GetMapping("/download-documento/{id}")
    @Operation(security = {@SecurityRequirement(name = "bearer-key")})
    public HttpEntity<byte[]> downloadDocumento(@PathVariable Long id) {
        byte[] data = documentoDigitalService.downloadDocumento(id);
        DocumentosDigitais documento = documentoDigitalService.getDocumento(id);
        return ResponseEntity.ok()
                .contentType(MediaType.APPLICATION_PDF)
                .contentLength(data.length)
                .header(HttpHeaders.CONTENT_DISPOSITION, String.format("inline; filename=%s.pdf", documento.getNomeArquivo()))
                .body(data);
    }

    @PostMapping("/download-documento-zip/")
    @Operation(security = {@SecurityRequirement(name = "bearer-key")})
    public HttpEntity<byte[]> downloadDocumentosZip(@RequestBody @Valid RParamsDownloadZip params) throws IOException {
        byte[] data = documentoDigitalService.downloadDocumentoZip(params.idCelebracao(), params.nrProtocolo());
        return ResponseEntity.ok()
                .contentType(MediaType.MULTIPART_FORM_DATA)
                .contentLength(data.length)
                .header(HttpHeaders.CONTENT_DISPOSITION, String.format("inline; filename=%s.pdf", params.nrProtocolo() + ".zip"))
                .body(data);
    }

    @GetMapping("/get-doc/{id}")
    @Operation(security = {@SecurityRequirement(name = "bearer-key")})
    public DocumentosDigitais getDocumento(@PathVariable Long id) {
         return documentoDigitalService.getDocumento(id);
    }

    @DeleteMapping("/delete-documento/{id}")
    @Operation(security = {@SecurityRequirement(name = "bearer-key")})
    public ResponseEntity<String> deleteDocumento (@PathVariable Long id) {
        return ResponseEntity.ok(documentoDigitalService.deleteDocumento(id));
    }

    @PostMapping("/conferir")
    @Operation(security = {@SecurityRequirement(name = "bearer-key")})
    public ResponseEntity<String> conferirDocumento (@RequestBody @Valid RParamsConferirDocumento params) {
        return ResponseEntity.ok(documentoDigitalService.conferirDocumento(params));
    }

    @PostMapping("/rejeitar")
    @Operation(security = {@SecurityRequirement(name = "bearer-key")})
    public ResponseEntity<String> rejeitarDocumento (@RequestBody @Valid RParamsRejeitarDocumento params) {
        return ResponseEntity.ok(documentoDigitalService.rejeitarDocumento(params));
    }

    @PostMapping("/gerar-hash-oficio")
    @Operation(security = {@SecurityRequirement(name = "bearer-key")})
    public HashDocumentoDigitalDTO retornarHashOficio(@RequestBody RParamsHashOficio params) throws Exception{
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        return documentoDigitalService.retornarHashOficio(params.base64(), params.idTipoDocumento(), params.nomeSistemaOperacional(), auth.getName());
    }

    @PostMapping("/anexar-assinatura-oficio")
    @Operation(security = {@SecurityRequirement(name = "bearer-key")})
    public String anexarAssinaturaOficio(@RequestBody RParamsAnexarAssinaturaOficio params) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        byte[] data = documentoDigitalService.anexarAssinaturaOficio(params.assinatura(), params.base64(), params.idTipoDocumento(), params.nomeSistemaOperacional(), auth.getName());
        return Base64.getEncoder().encodeToString(data);
    }

    @PostMapping("/gerar-hash")
    @Operation(security = {@SecurityRequirement(name = "bearer-key")})
    public HashDocumentoDigitalDTO retornarHashDocumentoDigital(@RequestBody RParamsHashDocumentoDigital params) {
        return documentoDigitalService.retornarHashDocumentoDigital(params);
    }

    @PostMapping("/anexar-assinatura")
    @Operation(security = {@SecurityRequirement(name = "bearer-key")})
    public ResponseEntity<String> anexarAssinaturaDocumento(@RequestBody RParamsAnexarAssinatura params) {
        return ResponseEntity.ok(documentoDigitalService.anexarAssinaturaDocumento(params));
    }

    @PostMapping("/possui-assinatura-valida")
    @Operation(security = {@SecurityRequirement(name = "bearer-key")})
    public ResponseEntity<String> possuiAssinaturas(@RequestBody RParamsHashOficio documento){
        return ResponseEntity.ok(documentoDigitalService.possuiAssinaturasValidas(documento.base64(), documento.idTipoDocumento()));
    }

    @GetMapping("/assinadores-documento/{id}")
    @Operation(security = {@SecurityRequirement(name = "bearer-key")})
    public List<VoListaDocumentosDigitaisAssinadores> assinadoresDocumento(@PathVariable Long id) {
        return documentoDigitalService.assinadoresDocumento(id);
    }

    @GetMapping("/historico/{id}")
    @Operation(security = {@SecurityRequirement(name = "bearer-key")})
    public List<VoHistoricoDocumento> historicosDocumento(@PathVariable Long id) {
        return documentoDigitalService.getHistoricoDocumento(id);
    }
}
