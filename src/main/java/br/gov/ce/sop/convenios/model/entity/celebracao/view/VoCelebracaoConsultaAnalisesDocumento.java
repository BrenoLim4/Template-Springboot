package br.gov.ce.sop.convenios.model.entity.celebracao.view;

import jakarta.persistence.*;
import jakarta.validation.constraints.Size;
import lombok.Data;
import org.hibernate.annotations.Immutable;

import java.time.Instant;
import java.util.List;

/**
 * Mapping for DB view
 */
@Data
@Entity
@Immutable
@Table(name = "vw_celebracao_consulta_analises_documentos", schema = "convenios")
public class VoCelebracaoConsultaAnalisesDocumento {
    @Id
    private Long id;

    @Column(name = "id_analise")
    private Integer idAnalise;

    @Column(name = "id_documento")
    private Integer idDocumento;

    @Column(name = "id_status_conferencia")
    private Integer idStatusConferencia;

    @Column(name = "status_conferencia", length = Integer.MAX_VALUE)
    private String statusConferencia;

    @Size(max = 8)
    @Column(name = "cor", length = 8)
    private String cor;

    @Size(max = 100)
    @Column(name = "nome_arquivo", length = 100)
    private String nomeArquivo;

    @Column(name = "comentario_rejeicao", length = Integer.MAX_VALUE)
    private String comentarioRejeicao;

    @Column(name = "data_hora_upload")
    private Instant dataHoraUpload;
    @OneToMany(mappedBy = "idDocumento", fetch = FetchType.EAGER)
    private List<VoHistoricoDocumento> historicos;

}