package br.gov.ce.sop.convenios.api.dto;

import lombok.Data;

import java.io.Serializable;

@Data
public class CelebracaoReprovadoDocumentosDTO implements Serializable {
    private Integer id;
    private Integer idCelebracao;
    private Integer idAnalise;
    private Integer idTipoAnalise;
    private Integer idPontoEntidade;
    private Integer idDocumento;
    private Integer idTipoDocumento;
    private String nomeDocumento;
    private String nomeArquivo;
    private Integer idStatusEnvio;
    private String descricaoEnvio;
    private Integer idStatusConferencia;
    private String descricaoConferencia;
    private Integer idStatusAssinatura;
    private String descricaoAssinatura;
    private Integer idStatusAssinado;
    private Boolean requerAssinatura;
    private Integer idDocumentoRecusado;
    private String comentarioValidacao;
    private String ocorrencia;
}
