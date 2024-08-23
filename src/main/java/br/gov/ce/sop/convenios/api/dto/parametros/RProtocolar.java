package br.gov.ce.sop.convenios.api.dto.parametros;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDateTime;

public record RProtocolar(@NotNull Integer idTipoEntidade, @NotEmpty String nrProtocolo, @NotNull LocalDateTime dataHoraProcesso, @NotNull Object idEntidade) {
}
