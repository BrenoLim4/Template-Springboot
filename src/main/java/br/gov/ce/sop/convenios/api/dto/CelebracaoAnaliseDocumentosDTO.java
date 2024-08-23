package br.gov.ce.sop.convenios.api.dto;

import lombok.Data;

@Data
public class CelebracaoAnaliseDocumentosDTO {
    private Integer id;
    private Integer idEntidade;
    private Integer idAnalise;
    private Integer idTipoAnalise;
    private Integer idPontoEntidade;
    private Integer idDocumento;
    private Integer idTipoDocumento;
    private String nomeArquivo;
    private Integer idStatusEnvio;
    private String statusEnvio;
    private Integer idStatusConferencia;
    private String statusConferencia;
    private String corStatusConferencia;
    private boolean requerAssinatura;
    private String statusAssinatura;
    private String comentarioValidacao;
}
