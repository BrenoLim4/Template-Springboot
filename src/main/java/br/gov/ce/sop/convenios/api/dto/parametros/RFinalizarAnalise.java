package br.gov.ce.sop.convenios.api.dto.parametros;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;

public record RFinalizarAnalise(@NotNull Integer idPontoEntidade, @NotNull Integer idAnalise, @NotEmpty String cnpjConvenente, RParamsUploadDocumento documentoDiligencia) {
}
