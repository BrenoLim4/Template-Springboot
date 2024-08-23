package br.gov.ce.sop.convenios.api.controller;

import br.gov.ce.sop.convenios.api.dto.PostRequestDTO;
import br.gov.ce.sop.convenios.api.dto.PostResponseDTO;
import br.gov.ce.sop.convenios.api.dto.filter.FilterProtocolosDTO;
import br.gov.ce.sop.convenios.api.dto.parametros.RProtocolar;
import br.gov.ce.sop.convenios.model.entity.convenio.VoProtocolos;
import br.gov.ce.sop.convenios.model.service.interfaces.GerencialService;
import br.gov.ce.sop.convenios.model.service.interfaces.convenio.VoProtocolosService;
import br.gov.ce.sop.convenios.utils.PaginationUtils;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping
@RequiredArgsConstructor
public class GerencialController {

    private final GerencialService gerencialService;
    private final VoProtocolosService voProtocolosService;

    @PostMapping("/protocolar")
    @Operation(security = {@SecurityRequirement(name = "bearer-key")})
    public ResponseEntity<String> protocolar(@RequestBody @Valid RProtocolar params) {
        try {
            voProtocolosService.getEntityById((Integer) params.idEntidade());
        } catch (RuntimeException ex) {
            return ResponseEntity.ofNullable("Documento já se encontra protocolado!");
        }

        gerencialService.protocolar(params.nrProtocolo(), params.dataHoraProcesso(), params.idEntidade(), params.idTipoEntidade());
        return ResponseEntity.ok("Protocolo realizado com sucesso!");
    }

    @PostMapping("/lista-protocolos")
    @Operation(security = {@SecurityRequirement(name = "bearer-key")})
    public PostResponseDTO<VoProtocolos> listaProtocolos(@RequestBody PostRequestDTO<FilterProtocolosDTO> postRequestDTO) {
        FilterProtocolosDTO filterProtocolosDTO = postRequestDTO.getFilters() != null
                ? postRequestDTO.getFilters()
                : new FilterProtocolosDTO();

        Page<VoProtocolos> protocolosPage =
                voProtocolosService.findAllByQuery(filterProtocolosDTO,
                        PaginationUtils.applyPagination(postRequestDTO));
        return new PostResponseDTO<>(protocolosPage);
    }

    @GetMapping("/get-documento")
    @Operation(security = {@SecurityRequirement(name = "bearer-key")})
    public VoProtocolos getDocumento(@RequestParam Integer idEntidade, @RequestParam Integer idTipoDocumento){
        return voProtocolosService.findDocumentoProtocoloByQuery(idEntidade, idTipoDocumento);
    }
}
