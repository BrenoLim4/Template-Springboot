package br.gov.ce.sop.convenios.model.dto.email.params;

import lombok.Builder;

@Builder
public record InfoBaseConvenioDTO(String mapps, String objetoConvenio, String tipoObjeto, String status, String nrProtocolo) {
}
