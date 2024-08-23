package br.gov.ce.sop.convenios.api.controller;

import br.gov.ce.sop.convenios.api.dto.AssessoriaDTO;
import br.gov.ce.sop.convenios.api.dto.ColaboradorDTO;
import br.gov.ce.sop.convenios.model.enums.TipoColaborador;
import br.gov.ce.sop.convenios.model.service.interfaces.convenio.ColaboradorService;
import br.gov.ce.sop.convenios.model.service.interfaces.convenio.PrefeituraService;
import com.fasterxml.jackson.annotation.JsonView;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/colaborador")
@RequiredArgsConstructor
public class ColaboradorController {

    private final ColaboradorService colaboradorService;
    private final PrefeituraService prefeituraService;

    @PostMapping("/cadastrar")
    @Operation(security = {@SecurityRequirement(name = "bearer-key")}, description = "Realizar cadastro do colaborador.")
    public ResponseEntity<String> cadastrar(@RequestBody @JsonView({ColaboradorDTO.Views.Insertable.class}) @Valid ColaboradorDTO colaboradorDTO){
        String msg = colaboradorService.cadastrarColaborador(colaboradorDTO, TipoColaborador.ENGENHEIRO);
        return ResponseEntity.ok(msg);
    }

    @PutMapping("/editar")
    @Operation(security = {@SecurityRequirement(name = "bearer-key")}, description = "Editar cadastro colaborador.")
    public ResponseEntity<String> editar(@RequestBody @JsonView({ColaboradorDTO.Views.Editable.class}) @Valid ColaboradorDTO colaboradorDTO){
        String msg = colaboradorService.editarColaborador(colaboradorDTO);
        return ResponseEntity.ok(msg);
    }

    @DeleteMapping("/{id}")
    @Operation(security = {@SecurityRequirement(name = "bearer-key")}, description = "Inativar cadastro colaborador.")
    public ResponseEntity<String> deletar(@PathVariable Integer id){
        String msg = colaboradorService.inativarColaborador(id);
        return ResponseEntity.ok(msg);
    }

    @GetMapping()
    @Operation(security = {@SecurityRequirement(name = "bearer-key")}, description = "Consultar lista de colaboradores conforme o cnpjConvenente da prefeitura logada.")
    public ResponseEntity<List<ColaboradorDTO>> getColaboradores(){
        List<ColaboradorDTO> colaboradores = colaboradorService.findAllColaboradoresByPrefeitura();
        return ResponseEntity.ok(colaboradores);
    }

    @PostMapping("/assessoria/salvar")
    @Operation(security = {@SecurityRequirement(name = "bearer-key")}, description = "Realizar cadastro do colaborador.")
    public ResponseEntity<AssessoriaDTO> cadastrarAssessoria(@RequestBody @Valid AssessoriaDTO colaboradorDTO){
        return ResponseEntity.ok(prefeituraService.cadastrarAssessoria(colaboradorDTO));
    }

    @GetMapping("/assessoria")
    @Operation(security = {@SecurityRequirement(name = "bearer-key")}, description = "Consultar assessoria da prefeitura logada.")
    public AssessoriaDTO getAssessoria(){
        return prefeituraService.getAssessoriaLogada();
    }

    @PutMapping("/assessoria/desassociar")
    @Operation(security = {@SecurityRequirement(name = "bearer-key")}, description = "Desassociar assessoria da prefeitura logada.")
    public String desassociarAssessoria(){
        return prefeituraService.desassociarAssessoria();
    }

}
