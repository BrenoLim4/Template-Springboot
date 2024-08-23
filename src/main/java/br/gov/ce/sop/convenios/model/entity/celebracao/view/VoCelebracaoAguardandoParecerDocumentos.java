package br.gov.ce.sop.convenios.model.entity.celebracao.view;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;

@Data
@Entity
@Table(name = "vw_celebracao_aguardando_parecer_documentos", schema = "convenios")
public class VoCelebracaoAguardandoParecerDocumentos {
    @Id
    private Integer id;
    @Column(name = "id_entidade")
    private Integer idEntidade;
    @Column(name = "id_convenio")
    private Integer idConvenio;
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
    @Column(name = "id_status_assinatura")
    private Integer idStatusAssinatura;
    @Column(name = "descricao_assinatura")
    private String descricaoAssinatura;
    @Column(name = "requer_assinatura")
    private Boolean requerAssinatura;
    @Column(name = "enviado")
    private Boolean enviado;
    @Column(name = "status_envio")
    private String statusEnvio;
    @Column(name = "id_status_conferencia")
    private Integer idStatusConferencia;
    @Column(name = "status_conferencia")
    private String statusConferencia;
    @Column(name = "cor_status_conferencia")
    private String corStatusConferencia;
    @Column(name = "requer_conferencia")
    private Boolean requerConferencia;
    @Column(name = "externo")
    private Boolean externo;
    @Column(name = "comentario_validacao")
    private String comentarioValidacao;
}
