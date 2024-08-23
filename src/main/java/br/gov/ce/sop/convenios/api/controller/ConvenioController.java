package br.gov.ce.sop.convenios.api.controller;

import br.gov.ce.sop.convenios.api.dto.parametros.RCadastrarPublicacao;
import br.gov.ce.sop.convenios.api.dto.parametros.RSolicitarConvenio;
import br.gov.ce.sop.convenios.model.dto.ConvenioDTO;
import br.gov.ce.sop.convenios.model.entity.convenio.TipoConvenio;
import br.gov.ce.sop.convenios.model.entity.convenio.VoCadastrosConvenio;
import br.gov.ce.sop.convenios.model.entity.mapp.Mapp;
import br.gov.ce.sop.convenios.model.entity.mapp.VoDocumentosEspelhoMapp;
import br.gov.ce.sop.convenios.model.service.interfaces.convenio.ConvenioService;
import br.gov.ce.sop.convenios.model.service.interfaces.convenio.TipoConvenioService;
import br.gov.ce.sop.convenios.model.service.interfaces.convenio.VoCadastrosConvenioService;
import br.gov.ce.sop.convenios.model.service.interfaces.mapp.MappService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@AllArgsConstructor
@RequestMapping("/convenio")
public class ConvenioController {

    private final ConvenioService convenioService;
    private final TipoConvenioService tipoConvenioService;
    private final VoCadastrosConvenioService voCadastrosConvenioService;
    private final MappService mappService;

    @PostMapping
    @Operation(security = {@SecurityRequirement(name = "bearer-key")})
    public ResponseEntity<String> solicitarNovoConvenio(@RequestBody @Valid RSolicitarConvenio params){
        convenioService.solicitarNovoConvenio(params);
        return ResponseEntity.ok("Convênio Incluído com sucesso, aguardando celebração.");
    }

    @PostMapping("/cadastrar-publicacao")
    @Operation(security = {@SecurityRequirement(name = "bearer-key")})
    public String cadastrarPublicacao(@RequestBody @Valid RCadastrarPublicacao params){
        convenioService.cadastrarPublicacao(params);
        return "Publicação cadastrada com sucesso!";
    }

    @GetMapping("/{nrSacc}/{idConvenio}")
    @Operation(security = {@SecurityRequirement(name = "bearer-key")})
    public ConvenioDTO buscarConvenioSefaz(@PathVariable int nrSacc, @PathVariable int idConvenio){
        return convenioService.buscarConvenioSefaz(nrSacc, idConvenio);
    }

    @GetMapping("/mapps")
    @Operation(security = {@SecurityRequirement(name = "bearer-key")})
    public List<Mapp> buscarListaMapps(@RequestParam String codigoMapp ){
        return mappService.buscarMapps(codigoMapp);
    }

    @GetMapping("/mapps/{idConvenio}")
    @Operation(security = {@SecurityRequirement(name = "bearer-key")})
    public List<VoDocumentosEspelhoMapp> buscarMappsConvenio(@PathVariable Integer idConvenio){
        return mappService.buscarMappsPorIdConvenio(idConvenio);
    }

    @GetMapping("/ultimos-convenios")
    @Operation(security = {@SecurityRequirement(name = "bearer=key")})
    public List<VoCadastrosConvenio> buscarUltimosConvenios() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        return voCadastrosConvenioService.buscarUltimosCadastros(auth.getName());
    }

    @GetMapping("/tipos-convenio")
    @Operation(security = {@SecurityRequirement(name = "bearer=key")})
    public List<TipoConvenio> buscarTiposConvenio(){
        return tipoConvenioService.getAll();
    }
}
