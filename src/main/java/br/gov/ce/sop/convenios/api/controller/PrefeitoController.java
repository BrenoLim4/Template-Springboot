package br.gov.ce.sop.convenios.api.controller;

import br.gov.ce.sop.convenios.api.dto.ColaboradorDTO;
import br.gov.ce.sop.convenios.api.dto.PostRequestDTO;
import br.gov.ce.sop.convenios.api.dto.PostResponseDTO;
import br.gov.ce.sop.convenios.api.dto.filter.FilterDadosPrefeitoDTO;
import br.gov.ce.sop.convenios.model.entity.convenio.prefeitura.VoDadosPrefeito;
import br.gov.ce.sop.convenios.model.entity.convenio.prefeitura.VoDocumentosPrefeito;
import br.gov.ce.sop.convenios.model.service.interfaces.convenio.PrefeitoService;
import br.gov.ce.sop.convenios.model.service.interfaces.convenio.VoDadosPrefeitoService;
import com.fasterxml.jackson.annotation.JsonView;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/prefeito")
@RequiredArgsConstructor
public class PrefeitoController {

    private final PrefeitoService service;
    private final VoDadosPrefeitoService dadosPrefeitoService;


    @PostMapping
    @Operation(security = {@SecurityRequirement(name = "bearer-key")}, description = "Realizar cadastro do prefeito.")
    public ResponseEntity<String> cadastrar(@RequestBody @JsonView({ColaboradorDTO.Views.Insertable.class}) @Valid ColaboradorDTO colaboradorDTO){
        service.cadastrarPrefeito(colaboradorDTO);
        return ResponseEntity.ok("Prefeito cadastrado com sucesso!");
    }

    @PutMapping("/editar")
    @Operation(security = {@SecurityRequirement(name = "bearer-key")}, description = "Atualizar Cadastro do prefeito.")
    public ResponseEntity<String> atualizar(@RequestBody @JsonView({ColaboradorDTO.Views.Editable.class}) @Valid ColaboradorDTO colaboradorDTO){
        service.atualizarDados(colaboradorDTO);
        return ResponseEntity.ok("Dados do Prefeito atualizado com sucesso!");
    }

    @GetMapping("/documentos")
    @Operation(security = {@SecurityRequirement(name = "bearer-key")}, description = "Lista de documentos do kit-prefeito.")
    public List<VoDocumentosPrefeito> getDocumentosPrefeito(){
        return service.getDocumentosPrefeitoByPrefeituraLogada();
    }

    @GetMapping("/documentos/{cnpj}")
    @Operation(security = {@SecurityRequirement(name = "bearer-key")}, description = "Lista de documentos do kit-prefeito.")
    public List<VoDocumentosPrefeito> getDocumentosPrefeito(@PathVariable String cnpj){
        return service.getDocumentosPrefeitoByCnpjPrefeitura(cnpj);
    }

    @GetMapping("/possui-pendencias")
    @Operation(security = {@SecurityRequirement(name = "bearer-key")}, description = "Verifica se há pendência de documentos do kit-prefeito da prefeitura logada.")
    public boolean possuiPendenciaDocumentos(){
        return service.possuiDocumentosRejeitados();
    }

    @PostMapping("/dados")
    @Operation(security = {@SecurityRequirement(name = "bearer-key")}, description = "Buscar dados do prefeito.")
    public PostResponseDTO<VoDadosPrefeito> getDadosPrefeito(@RequestBody PostRequestDTO<FilterDadosPrefeitoDTO> body){
        return dadosPrefeitoService.getPage(body);
    }
}
