package br.gov.ce.sop.convenios.model.dto.email;

import jakarta.validation.constraints.NotNull;

import java.util.List;

public record SendMailDTO (
        @NotNull
        List<String> destinatarios,
        String remetente,
        @NotNull
        String assunto,
        @NotNull
        BodyMail bodyMail,
        List<String> cc,
        Boolean confirmacaoLeitura,
        List<AnexoDTO> anexos
) {
}
