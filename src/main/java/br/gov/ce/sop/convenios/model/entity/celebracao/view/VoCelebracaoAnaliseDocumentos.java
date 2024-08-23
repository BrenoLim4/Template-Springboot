package br.gov.ce.sop.convenios.model.entity.celebracao.view;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.*;

@Entity
@Table(name = "vw_celebracao_analise_documentos", schema = "convenios")
@Getter
@EqualsAndHashCode
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class VoCelebracaoAnaliseDocumentos {
     
    @Column(name = "id")
    @Id
    private Integer id;
    @Column(name = "id_tipo_analise")
    private Integer idTipoAnalise;
    @Column(name = "id_entidade")
    private Integer idEntidade;
     
    @Column(name = "id_analise")
    private Integer idAnalise;
     
    @Column(name = "id_ponto_entidade")
    private Integer idPontoEntidade;
     
    @Column(name = "id_documento")
    private Integer idDocumento;
     
    @Column(name = "id_tipo_documento")
    private Integer idTipoDocumento;
     
    @Column(name = "nome_arquivo")
    private String nomeArquivo;
     
    @Column(name = "nome_documento")
    private String nomeDocumento;
     
    @Column(name = "id_status_envio")
    private Integer idStatusEnvio;
     
    @Column(name = "status_envio")
    private String statusEnvio;
     
    @Column(name = "id_status_conferencia")
    private Integer idStatusConferencia;
     
    @Column(name = "status_conferencia")
    private String statusConferencia;

    @Column(name = "cor_status_conferencia")
    private String corStatusConferencia;

    @Column(name = "id_status_assinatura")
    private Integer idStatusAssinatura;

    @Column(name = "requer_assinatura")
    private Boolean requerAssinatura;

    @Column(name = "status_assinatura")
    private String statusAssinatura;

    @Column(name = "comentario_validacao")
    private String comentarioValidacao;

}
