package br.gov.ce.sop.convenios.model.entity.celebracao.view;

import br.gov.ce.sop.convenios.model.entity.celebracao.HistoricoAnalise;
import jakarta.persistence.*;
import jakarta.validation.constraints.Size;
import lombok.Data;
import org.hibernate.annotations.Immutable;

import java.time.LocalDateTime;
import java.util.List;

/**
 * Mapping for DB view
 */
@Data
@Entity
@Immutable
@Table(name = "vw_celebracao_consulta_analises", schema = "convenios")
public class VoCelebracaoConsultaAnalise {
    @Id
    @Column(name = "id")
    private Integer id;

    @Column(name = "id_celebracao")
    private Integer idCelebracao;

    @Column(name = "atual")
    private Boolean atual;

    @Size(max = 8)
    @Column(name = "matricula_engenheiro", length = 8)
    private String matriculaEngenheiro;

    @Size(max = 200)
    @Column(name = "engenheiro", length = 200)
    private String engenheiro;

    @Column(name = "id_status")
    private Integer idStatus;

    @Column(name = "status", length = Integer.MAX_VALUE)
    private String status;

    @Column(name = "id_tipo_analise")
    private Integer idTipoAnalise;

    @Column(name = "tipo", length = Integer.MAX_VALUE)
    private String tipo;

    @Column(name = "versao")
    private Integer versao;

    @Column(name = "data_hora_inicio")
    private LocalDateTime dataHoraInicio;

    @Column(name = "data_hora_fim")
    private LocalDateTime dataHoraFim;
    @Column(name = "id_documento_diligencia")
    private Long idDocumentoDiligencia;

//    @OneToMany(mappedBy = "idAnalise", fetch = FetchType.EAGER)
//    private List<VoCelebracaoConsultaAnalisesDocumento> documentos;

    @OneToMany(mappedBy = "idAnalise", fetch = FetchType.EAGER)
    private List<HistoricoAnalise> historicos;

}