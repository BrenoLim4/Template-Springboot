package br.gov.ce.sop.convenios.model.dto;


import lombok.Builder;

@Builder
public record TipoAssinadorDinamicoDTO(Integer idTipoAssinador, String idPessoa) {

}
