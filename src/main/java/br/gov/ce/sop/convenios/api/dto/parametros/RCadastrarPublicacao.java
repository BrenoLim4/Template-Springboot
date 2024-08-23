package br.gov.ce.sop.convenios.api.dto.parametros;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.math.BigDecimal;
import java.time.LocalDate;

public record RCadastrarPublicacao(
        @NotNull(message = "Campo número sacc é obrigatório!")
        Integer nrSacc,
        @NotNull(message = "Campo id Convênio é obrigatório!")
        Integer idConvenio,
        @NotBlank(message = "Número do convênio é obrigatório!")
        String nrConvenio,
        @NotBlank
        String objeto,
        @NotNull(message = "Data publicacao é obrigatório!")
        LocalDate dataPublicacao,
        @NotNull(message = "Data assinatura é obrigatório!")
        LocalDate dataAssinatura,
        @NotNull(message = "Fim vigência é obrigatório!")
        LocalDate dataFimVigencia,
        @NotNull(message = "valor é obrigatório!")
        BigDecimal valor,
        @NotNull(message = "Valor concessão é obrigatório!")
        BigDecimal valorConcessao,
        @NotNull(message = "Valor contrapartida é obrigatório!")
        BigDecimal valorContrapartida,
        @NotBlank(message = "Anexo da publicação é obrigatório")
        String anexoPublicacao

) {
}
