package br.gov.ce.sop.convenios.model.entity.documentodigital;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.Immutable;

/**
 * Mapping for DB view
 */
@Getter
@Setter
@Entity
@Immutable
@Table(name = "vw_celebracao_documentos_geral_detalhes", schema = "convenios")
public class VoCelebracaoDocumentosGeralDetalhes {
    @Id
    @Column(name = "id_documento")
    private Integer idDocumento;

    @Column(name = "id_celebracao")
    private Integer idCelebracao;

    @Column(name = "nome_documento", length = Integer.MAX_VALUE)
    private String nomeDocumento;

    @Column(name = "versao")
    private String versao;

    @Column(name = "status_conferencia")
    private String statusConferencia;

    @Column(name = "status_conferencia_cor")
    private String statusConferenciaCor;

    @Column(name = "status_assinatura")
    private String statusAssinatura;

    @Size(max = 8)
    @Column(name = "status_assinatura_cor", length = 8)
    private String statusAssinaturaCor;

//    private
}