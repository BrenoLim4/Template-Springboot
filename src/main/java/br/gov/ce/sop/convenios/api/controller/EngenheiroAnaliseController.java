package br.gov.ce.sop.convenios.api.controller;

import br.gov.ce.sop.convenios.api.dto.EngenheiroAnaliseDTO;
import br.gov.ce.sop.convenios.api.dto.parametros.RAtribuirEngenheiroAnalise;
import br.gov.ce.sop.convenios.model.entity.celebracao.EngenheiroAnalise;
import br.gov.ce.sop.convenios.model.entity.celebracao.view.VoEngenheirosAnalise;
import br.gov.ce.sop.convenios.model.service.interfaces.celebracao.EngenheiroAnaliseService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/engenheiro")
@RequiredArgsConstructor
public class EngenheiroAnaliseController {

    private final EngenheiroAnaliseService service;

    @GetMapping
    @Operation(security = {@SecurityRequirement(name = "bearer-key")}, description = "Buscar Engenheiros disponíveis para analise.")
    public List<EngenheiroAnalise> getEngenheirosAtivos(){
        return service.findAllEngenheirosAnaliseAtivos();
    }

    @PostMapping("/buscar")
    @Operation(security = {@SecurityRequirement(name = "bearer-key")}, description = "Buscar lista de engenheiros.")
    public List<VoEngenheirosAnalise> getEngenheiros(@RequestBody EngenheiroAnaliseDTO params){
        return service.findAllVoEngenheirosAnalise(params);
    }

    @PostMapping("/atribuir-analise")
    @Operation(security = {@SecurityRequirement(name = "bearer-key")}, description = "Atribuir análise ao engenheiro.")
    public String atribuirAnalise(@RequestBody @Valid RAtribuirEngenheiroAnalise params){
        return service.atribuirAnalise(params);
    }

    @PostMapping("/cadastrar")
    @Operation(security = {@SecurityRequirement(name = "bearer-key")}, description = "Cadastrar engenheiro.")
    @Secured(value = {"CONVENIOS.GESTOR", "CONVENIOS.MASTER"})
    public void cadastrarEngenheiro(@RequestBody @Valid EngenheiroAnaliseDTO params){
        service.cadastrar(params);
    }

    @PostMapping("/editar")
    @Operation(security = {@SecurityRequirement(name = "bearer-key")}, description = "Editar dados do engenheiro.")
    @Secured(value = {"CONVENIOS.GESTOR", "CONVENIOS.MASTER"})
    public void editarEngenheiro(@RequestBody @Valid EngenheiroAnaliseDTO params){
        service.save(params);
    }

}
