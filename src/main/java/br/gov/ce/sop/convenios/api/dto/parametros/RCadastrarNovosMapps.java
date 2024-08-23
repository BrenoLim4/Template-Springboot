package br.gov.ce.sop.convenios.api.dto.parametros;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;

import java.util.List;

public record RCadastrarNovosMapps(@NotNull Integer idConvenio, @NotEmpty List<Integer> idsMapps) {
}
