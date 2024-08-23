package br.gov.ce.sop.convenios.api.dto.filter;

import br.gov.ce.sop.convenios.security.service.TokenService;
import lombok.Builder;

import java.util.List;

@Builder
public record FilterConsultaCelebracaoDTO(
        String cnpjConvenente,
        String convenente,
        String nrProtocolo,
        String mapp,
        String objetoConvenio,
        List<Integer> status,
        List<Integer> tiposConvenio,
        String matriculaEngenheiro,
        Boolean apenasAguardandoAtribuicaoEngenheiro
) {

    public FilterConsultaCelebracaoDTO {
        if(TokenService.isConvenente()){
            cnpjConvenente = TokenService.getTokenUsername();
        }
    }
}
