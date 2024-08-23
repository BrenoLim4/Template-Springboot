package br.gov.ce.sop.convenios.api.dto;

import br.gov.ce.sop.convenios.utils.StringEncrypted;
import com.fasterxml.jackson.annotation.JsonView;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import lombok.Builder;
import org.hibernate.validator.constraints.br.CPF;

@Builder
public record ColaboradorDTO(
        @JsonView({Views.Editable.class})
        Integer id,
        @CPF
        @JsonDeserialize(using = StringEncrypted.class)
        @JsonView({Views.Insertable.class})
        String cpf,
//        @Max(value = 255, message = "Quantidade máxima de 255 caracteres ultrapassada")
        @NotEmpty(message = "Campo nome deve ser especificado!")
        @JsonView({Views.Editable.class, Views.Insertable.class})
        String nome,
        @Email
        @JsonView({Views.Editable.class, Views.Insertable.class})
        String email,
//        @JsonView({Views.Insertable.class})
        Integer tipoColaborador
) {

    public interface Views{
        interface Insertable{} //inserir um novo colaborador
        interface Editable {} //editar um colaborador existente
    }


}
