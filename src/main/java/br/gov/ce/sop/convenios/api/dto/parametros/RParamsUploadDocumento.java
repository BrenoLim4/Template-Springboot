package br.gov.ce.sop.convenios.api.dto.parametros;

import br.gov.ce.sop.convenios.model.dto.TipoAssinadorDinamicoDTO;
import br.gov.ce.sop.convenios.model.enums.TipoAssinador;
import br.gov.ce.sop.convenios.security.service.TokenService;
import lombok.Builder;

import java.util.Base64;
import java.util.List;

import static java.util.Arrays.asList;

@Builder
public record RParamsUploadDocumento(String base64,
                                     byte[] arquivo,
                                     String idEntidade,
                                     String idEntidadeColeta,
                                     Integer idTipoDocumento,
                                     Integer idTipoEntidadeColeta,
                                     String ocorrencia,
                                     String observacao,
                                     String extensaoAlternativa,
                                     boolean assinatura,
                                     boolean versiona,
                                     Integer idAnalise,
                                     List<TipoAssinadorDinamicoDTO> assinadoresDinamicos) {
    public RParamsUploadDocumento {
        arquivo = Base64.getDecoder().decode(base64);
        base64 = null;
        if(TokenService.isConvenente()){
            assinadoresDinamicos = asList(
                    TipoAssinadorDinamicoDTO.builder()
                    .idTipoAssinador(TipoAssinador.CONVENENTE.idTipoAssinador)
                    .idPessoa(TokenService.getTokenUsername())
                    .build(),
                    TipoAssinadorDinamicoDTO.builder()
                    .idTipoAssinador(TipoAssinador.RESPONSAVEL_TECNICO.idTipoAssinador)
                    .idPessoa(TokenService.getTokenUsername())
                    .build()
            );
        }
    }
}
