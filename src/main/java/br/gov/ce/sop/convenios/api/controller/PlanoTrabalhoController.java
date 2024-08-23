package br.gov.ce.sop.convenios.api.controller;

import br.gov.ce.sop.convenios.api.dto.parametros.PlanoTrabalhoDTO;
import br.gov.ce.sop.convenios.model.service.interfaces.planotrabalho.PlanoTrabalhoService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/plano-trabalho")
@RequiredArgsConstructor
public class PlanoTrabalhoController {
    private final PlanoTrabalhoService service;
    @PostMapping("/cadastrar")
    @Operation(security = {@SecurityRequirement(name = "bearer-key")}, description = "Realizar cadastro do plano de trabalho.")
    public String cadastrar(@RequestBody @Valid PlanoTrabalhoDTO planoTrabalhoDTO){
        return service.cadastrar(planoTrabalhoDTO);
    }

    @GetMapping("/{idConvenio}")
    @Operation(security = {@SecurityRequirement(name = "bearer-key")})
    public PlanoTrabalhoDTO getPlanoTrabalhoByIdConvenio(@PathVariable Integer idConvenio) {
        return service.findByIdConvenio(idConvenio);
    }

}
