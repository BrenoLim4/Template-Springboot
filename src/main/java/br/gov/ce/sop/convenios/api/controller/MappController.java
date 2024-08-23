package br.gov.ce.sop.convenios.api.controller;

import br.gov.ce.sop.convenios.api.dto.parametros.RCadastrarNovosMapps;
import br.gov.ce.sop.convenios.model.entity.convenio.Convenio;
import br.gov.ce.sop.convenios.model.entity.mapp.Mapp;
import br.gov.ce.sop.convenios.model.entity.mapp.VoDocumentosEspelhoMapp;
import br.gov.ce.sop.convenios.model.service.interfaces.mapp.MappService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;
import org.springframework.lang.Nullable;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/mapp")
@RequiredArgsConstructor
public class MappController {

    private final MappService service;

    @GetMapping()
    @Operation(security = {@SecurityRequirement(name = "bearer-key")})
    public List<Mapp> buscarListaMapps(@RequestParam String codigoMapp ){
        return service.buscarMapps(codigoMapp);
    }

    @GetMapping("/{idConvenio}")
    @Operation(security = {@SecurityRequirement(name = "bearer-key")})
    public List<VoDocumentosEspelhoMapp> buscarMappsConvenio(@PathVariable Integer idConvenio){
        return service.buscarMappsPorIdConvenio(idConvenio);
    }

    @DeleteMapping()
    @Operation(security = {@SecurityRequirement(name ="bearer-key")}, description = "Desassocia Mapp do convênio ao qual está vinculado.")
    public String desassociarMappConvenio(@RequestParam @NotNull Integer idMapp, @RequestParam @Nullable Long idDocumento){
        return service.desassociarMappConvenio(idMapp, idDocumento);
    }

    @PostMapping()
    public String cadastrarNovosMapps(@RequestBody @Valid RCadastrarNovosMapps params){
        service.saveMappsCovenio(params.idsMapps(), new Convenio(params.idConvenio()));
        return "Mapps associados ao convênio com sucesso";
    }
}
