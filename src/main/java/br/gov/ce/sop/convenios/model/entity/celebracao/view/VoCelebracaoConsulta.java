package br.gov.ce.sop.convenios.model.entity.celebracao.view;

import br.gov.ce.sop.convenios.model.entity.celebracao.HistoricoCelebracao;
import br.gov.ce.sop.convenios.model.entity.documentodigital.VoCelebracaoDocumentosGeralDetalhes;
import br.gov.ce.sop.convenios.model.entity.mapp.VoMappDetalhe;
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
@Table(name = "vw_celebracao_consulta", schema = "convenios")
public class VoCelebracaoConsulta {
    @Id
    @Column(name = "id")
    private Integer id;

    @Column(name = "id_convenio")
    private Integer idConvenio;

    @Size(max = 14)
    @Column(name = "cnpj_convenente", length = 14)
    private String cnpjConvenente;
    @Size(max = 50)
    @Column(name = "convenente", length = 50)
    private String convenente;

    @Column(name = "nr_protocolo", length = Integer.MAX_VALUE)
    private String nrProtocolo;

    @Size(max = 300)
    @Column(name = "prefeito", length = 300)
    private String prefeito;

    @Size(max = 14)
    @Column(name = "cpf_prefeito", length = 14)
    private String cpfPrefeito;

    @Column(name = "objeto_convenio", length = Integer.MAX_VALUE)
    private String objetoConvenio;

    @Column(name = "id_tipo_convenio")
    private Integer idTipoConvenio;

    @Column(name = "tipo_convenio", length = Integer.MAX_VALUE)
    private String tipoConvenio;

    @Column(name = "mapps_ids", length = Integer.MAX_VALUE)
    private String mapps;

    @Column(name = "data_inclusao")
    private LocalDateTime dataInclusao;

    @Column(name = "id_status")
    private Integer idStatus;

    @Column(name = "status", length = Integer.MAX_VALUE)
    private String status;
    private String engenheiro;
    @Column(name = "matricula_engenheiro")
    private String matriculaEngenheiro;
    @Column(name = "aguardando_atribuicao_engenheiro")
    private boolean aguardandoAtribuicaoEngenheiro;

    @Transient
    private List<VoCelebracaoConsultaAnalise> analises;
    @Transient
    private List<HistoricoCelebracao> historicos;
    @Transient
    private List<VoMappDetalhe> mappsDetalhes;
    @Transient
    private List<VoCelebracaoDocumentosGeralDetalhes> documentos;
}