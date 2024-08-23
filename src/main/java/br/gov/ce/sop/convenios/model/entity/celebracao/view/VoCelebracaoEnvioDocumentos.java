package br.gov.ce.sop.convenios.model.entity.celebracao.view;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;

import java.io.Serializable;

@Data
@Entity
@Table(name = "vw_celebracao_envio_documentos", schema = "convenios")
public class VoCelebracaoEnvioDocumentos implements Serializable {
    @Id
    @Column(name = "id")
    private Integer id;

    @Column(name = "id_celebracao")
    private Integer idCelebracao;

    @Column(name = "id_ponto_entidade")
    private Integer idPontoEntidade;

    @Column(name = "id_documento")
    private Integer idDocumento;

    @Column(name = "id_tipo_documento")
    private Integer idTipoDocumento;

    @Column(name = "nome_documento")
    private String nomeDocumento;

    @Column(name = "nome_arquivo")
    private String nomeArquivo;

    @Column(name = "id_status_envio")
    private Integer idStatusEnvio;

    @Column(name = "descricao_envio")
    private String descricaoEnvio;

    @Column(name = "id_status_assinatura")
    private Integer idStatusAssinatura;

    @Column(name = "descricao_assinatura")
    private String descricaoAssinatura;

    @Column(name="id_status_assinado")
    private Integer idStatusAssinado;

    @Column(name = "requer_assinatura")
    private Boolean requerAssinatura;

    @Column(name = "id_tipo_analise")
    private Integer idTipoAnalise;

    @Column(name = "externo")
    private Boolean externo;

}
