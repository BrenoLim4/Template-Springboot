package br.gov.ce.sop.convenios.api.dto.parametros;

import jakarta.validation.constraints.NotNull;

public record RCadastrarParecerGecov(
        @NotNull
        Integer idCelebracao,
        @NotNull Integer idPontoEntidade
) {
}
