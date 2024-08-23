package br.gov.ce.sop.convenios.model.dto.email;

import lombok.Builder;

@Builder
public record AnexoDTO(String base64, String filename) {
}
