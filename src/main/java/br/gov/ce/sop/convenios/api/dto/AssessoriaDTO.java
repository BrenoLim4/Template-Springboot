package br.gov.ce.sop.convenios.api.dto;

import br.gov.ce.sop.convenios.utils.StringDeserializer;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.Builder;
import org.hibernate.validator.constraints.br.CNPJ;

import java.util.Objects;

@Builder
public record AssessoriaDTO(
        @CNPJ
        @NotBlank(message = "Campo cnpj é obrigatório!")
        String cnpj,
        @NotBlank(message = "Campo razão social é obrigatório!")
        @JsonDeserialize(using = StringDeserializer.class)
        String razaoSocial,
        @NotBlank(message = "Campo nome fantasia é obrigatório!")
        @JsonDeserialize(using = StringDeserializer.class)
        String nomeFantasia,
        @Email(message = "Um email válido deve ser fornecido!")
        @JsonDeserialize(using = StringDeserializer.class)
        @NotBlank(message = "Campo email é obrigatório!")
        String email

) {

    public AssessoriaDTO {
//        email = email.toLowerCase();
//        nomeFantasia = nomeFantasia.toLowerCase();
//        razaoSocial = razaoSocial.toLowerCase();
        cnpj = Objects.isNull(cnpj) ? null : cnpj.replaceAll("\\D", "");
    }


}
