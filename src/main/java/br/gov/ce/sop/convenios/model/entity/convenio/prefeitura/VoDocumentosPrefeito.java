package br.gov.ce.sop.convenios.model.entity.convenio.prefeitura;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;

@Entity
@Table(name = "vw_documentos_prefeito", schema = "convenios")
@Data
public class VoDocumentosPrefeito {

    @Id
    private Integer id;
    @Column(name = "id_entidade")
    private Integer idEntidade;
    @Column(name = "id_tipo_entidade")
    private Integer idTipoEntidade;
    @Column(name = "id_tipo_documento")
    private Integer idTipoDocumento;
    @Column(name = "cpf")
    private String cpf;
    @Column(name = "cnpj_prefeitura")
    private String cnpj;
    @Column(name = "id_prefeitura")
    private Integer idPrefeitura;
    @Column(name = "nome_arquivo")
    private String nomeArquivo;
    @Column(name = "id_documento")
    private Integer idDocumento;
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
    @Column(name = "requer_assinatura")
    private boolean requerAssinatura;
    @Column(name = "status_assinatura")
    private String statusAssinatura;
    @Column(name = "comentario_rejeicao")
    private String comentarioRejeicao;
//    @Transient
//    private final Integer idTipoAnalise = 0;
//    @Transient
//    @Setter
//    private Integer idAnalise;
//    @Transient
//    @Setter
//    private String comentarioValidacao;
}
