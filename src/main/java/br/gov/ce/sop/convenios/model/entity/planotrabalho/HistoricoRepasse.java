package br.gov.ce.sop.convenios.model.entity.planotrabalho;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.io.Serializable;
import java.time.LocalDateTime;

@Data
@Entity
@Table(name = "historico_repasse", schema = "convenios")
public class HistoricoRepasse implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @ManyToOne
    @JoinColumn(name="id_repasse", nullable=false)
    private Repasse repasse;

    @ManyToOne
    @JoinColumn(name = "id_tipo_historico", nullable = false)
    private TipoHistoricoRepasse tipoHistoricoRepasse;

    @Column(name = "data_hora")
    @Temporal(TemporalType.TIMESTAMP)
    @NotNull
    private LocalDateTime dataHora = LocalDateTime.now();

    @Column(length = 14)
    @NotNull
    private String matricula;

    @Column(columnDefinition = "TEXT")
    private String observacao;

}