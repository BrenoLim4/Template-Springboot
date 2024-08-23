package br.gov.ce.sop.convenios.model.dto;

public record BodyUploadDocumento(String base64, Long idDocumento, String idEntidade, String idEntidadeColeta, Integer idTipoDocumento, Integer idTipoEntidadeColeta, String sufixoArquivo, String observacao, String extensaoAlternativa, boolean assinatura, boolean versiona) {
}
