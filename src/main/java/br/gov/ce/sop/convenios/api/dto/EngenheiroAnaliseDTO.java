package br.gov.ce.sop.convenios.api.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.util.Objects;


public record EngenheiroAnaliseDTO(@NotBlank String matricula, @NotBlank String nome, @NotBlank String email, @NotNull Boolean ativo) {

    public EngenheiroAnaliseDTO {
        nome = Objects.isNull(nome) ? nome : nome.toUpperCase();
        email = Objects.isNull(email) ? email : email.toLowerCase();
    }
}
