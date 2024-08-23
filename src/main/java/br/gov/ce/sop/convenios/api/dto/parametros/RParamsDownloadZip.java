package br.gov.ce.sop.convenios.api.dto.parametros;

import jakarta.validation.constraints.NotNull;
import lombok.Builder;

@Builder
public record RParamsDownloadZip(@NotNull Integer idCelebracao, @NotNull String nrProtocolo) {
}
