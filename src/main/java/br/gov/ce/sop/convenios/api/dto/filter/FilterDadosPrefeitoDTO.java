package br.gov.ce.sop.convenios.api.dto.filter;

import lombok.Builder;

@Builder
public record FilterDadosPrefeitoDTO(String nome, String cnpj, String razaoSocial, boolean ativo) {

    public FilterDadosPrefeitoDTO {
        cnpj = cnpj != null ? cnpj.replaceAll("[^0-9]", "") : null;
    }
}
