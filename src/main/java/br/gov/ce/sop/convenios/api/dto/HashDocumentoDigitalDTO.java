package br.gov.ce.sop.convenios.api.dto;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class HashDocumentoDigitalDTO {
    private String documentoAntigo;
    private String hash;
}