package br.gov.ce.sop.convenios.model.entity.convenio;

import br.gov.ce.sop.convenios.model.entity.convenio.prefeitura.Prefeito;
import br.gov.ce.sop.convenios.model.entity.convenio.prefeitura.Prefeitura;
import br.gov.ce.sop.convenios.model.enums.TipoOrigemConvenio;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
@Entity
@Table(name = "convenio", schema = "convenios")
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Convenio implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "nr_convenio")
    private String nrConvenio;

    @Column(name = "nr_processo")
    private String nrProcesso;

    @Column(name = "nr_sacc")
    private Integer nrSacc;

    @Column(name = "data_publicacao")
    private LocalDate dataPublicacao;
    @Column(name = "data_fim_vigencia")
    private LocalDate dataFimVigencia;
    @Column(name = "data_conclusao")
    private LocalDate dataConclusao;
    @Column(name = "data_inclusao")
    @Builder.Default
    private LocalDateTime dataInclusao = LocalDateTime.now();
    @Column(name = "data_assinatura")
    private LocalDate dataAssinatura;

    @NotNull
    private String objeto;
    private BigDecimal valor;
    @Column(name = "valor_total")
    private BigDecimal valorTotal;
    @Enumerated(EnumType.STRING)
    @NotNull
    private TipoOrigemConvenio origem;

    @ManyToOne
    @JoinColumn(name = "id_tipo", nullable = false)
    private TipoConvenio tipoConvenio;

    @ManyToOne
    @JoinColumn(name = "id_status", nullable = false)
    @Builder.Default
    private StatusConvenio statusConvenio = new StatusConvenio(StatusConvenio.AGUARDANDO_CELEBRACAO);

    @ManyToOne
    @JoinColumn(name = "id_convenente", referencedColumnName = "id_entidade_governamental")
    private Prefeitura convenente;
    @ManyToOne
    @JoinColumn(name = "id_prefeito", referencedColumnName = "id")
    private Prefeito prefeito;

    public Convenio(Integer id) {
        this.id = id;
    }
}