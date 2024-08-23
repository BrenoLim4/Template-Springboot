package br.gov.ce.sop.convenios.api.dto.filter;

import br.gov.ce.sop.convenios.utils.StringDeserializer;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import jakarta.annotation.Nullable;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.time.LocalDate;


@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class FilterReprovadoDTO implements Serializable {
    private LocalDate dataInclusao;
    @JsonDeserialize(using = StringDeserializer.class)
    private LocalDate convenente;
    @JsonDeserialize(using = StringDeserializer.class)
    private String nrProtocolo;
    @JsonDeserialize(using = StringDeserializer.class)
    private String objeto;
    @JsonDeserialize(using = StringDeserializer.class)
    @Nullable
    private Integer idConvenente;
}
