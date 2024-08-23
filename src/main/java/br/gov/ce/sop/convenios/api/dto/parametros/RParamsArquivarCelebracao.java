package br.gov.ce.sop.convenios.api.dto.parametros;


import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;

public record RParamsArquivarCelebracao(@NotNull Integer idCelebracao, @NotEmpty String motivo) {
}
