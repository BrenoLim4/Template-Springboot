package br.gov.ce.sop.convenios.api.dto.filter;

import br.gov.ce.sop.convenios.utils.StringDeserializer;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.time.LocalDateTime;


@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class FilterProtocolosDTO implements Serializable {
    private LocalDateTime dataInclusao;
    @JsonDeserialize(using = StringDeserializer.class)
    private String Convenente;
    @JsonDeserialize(using = StringDeserializer.class)
    private String Objeto;
}
