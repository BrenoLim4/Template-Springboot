package br.gov.ce.sop.convenios.api.dto.parametros;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;


public record RAtribuirEngenheiroAnalise(
        @NotNull(message = "Campo id celebração é obrigatório.")
        Integer idCelebracao,
        @NotEmpty(message = "Campo matricula do engenheiro é obrigatório.")
        String matricula
) {
}
