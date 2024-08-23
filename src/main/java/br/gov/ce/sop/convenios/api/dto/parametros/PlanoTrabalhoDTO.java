package br.gov.ce.sop.convenios.api.dto.parametros;

import br.gov.ce.sop.convenios.model.entity.planotrabalho.PlanoTrabalho;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;

import java.math.BigDecimal;

@Builder
public record PlanoTrabalhoDTO(
        @NotNull Integer idConvenio,
        @NotEmpty String numero,
        @NotNull BigDecimal valor,
        String descricao,
        String registro
) {

    public static PlanoTrabalhoDTO convertToDTO(PlanoTrabalho planoTrabalho){
        return PlanoTrabalhoDTO.builder()
                .idConvenio(planoTrabalho.getId())
                .numero(planoTrabalho.getNumero())
                .descricao(planoTrabalho.getDescricao())
                .valor(planoTrabalho.getValor())
                .registro(planoTrabalho.getRegistro())
                .build();
    }
}
