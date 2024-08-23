package br.gov.ce.sop.convenios.api.dto.parametros;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;

import java.util.List;

public record RSolicitarConvenio(
        @NotEmpty(message = "Pelo menos um Mapp deve ser informado") List<Integer> idMapps,
        @NotEmpty(message = "objetoConvenio do convênio deve ser informado") String objeto,
        @NotEmpty(message = "Oficio de celebração é obrigatório.") String oficioCelebracao,
        @NotNull(message = "Tipo do convênio é obrigatório") Integer idTipo
) {

}
