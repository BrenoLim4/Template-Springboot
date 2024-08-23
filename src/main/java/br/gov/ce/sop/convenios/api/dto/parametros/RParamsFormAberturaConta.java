package br.gov.ce.sop.convenios.api.dto.parametros;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;

public record RParamsFormAberturaConta(@NotNull Integer idCelebracao, @Email(message = "Formato do endereço de e-mail inválido!") String email, @NotEmpty String documento) {
}
