package br.gov.ce.sop.convenios.api.dto.parametros;

public record RParamsAnexarAssinatura(String assinatura, Integer idAssinador, Integer idTipoColaborador, Long idDocumento, String nomeSistemaOperacional) {
}