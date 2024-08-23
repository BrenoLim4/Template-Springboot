package br.gov.ce.sop.convenios.model.dto.email.params;

import lombok.Builder;

@Builder
public record ConvenioSolicitadoDTO(String oficioCelebracao, InfoBaseConvenioDTO infoConvenio) {
}
