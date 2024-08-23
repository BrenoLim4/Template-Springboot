package br.gov.ce.sop.convenios.api.dto.parametros;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;

public record RParamsRejeitarDocumento(@NotNull Long id, Integer origem, @NotEmpty(message = "Comentário é obrigatório!") String comentario, Integer idAnalise) {
}
