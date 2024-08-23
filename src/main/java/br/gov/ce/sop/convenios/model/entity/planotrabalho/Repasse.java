package br.gov.ce.sop.convenios.model.entity.planotrabalho;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@Entity
@Table(name = "repasse", schema = "convenios")
public class Repasse implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "data_liberacao")
    @Temporal(TemporalType.TIMESTAMP)
    private LocalDateTime dataLiberacao;

    @Column
    @NotNull
    private BigDecimal valor = BigDecimal.ZERO;

    @Column(name = "valor_executado")
    @NotNull
    private BigDecimal valorExecutado = BigDecimal.ZERO;

    @ManyToOne
    @JoinColumn(name = "id_status", nullable = false)
    private StatusRepasse statusRepasse;

    @ManyToOne
    @JoinColumn(name = "id_cronograma_desembolso", nullable = false)
    private CronogramaDesembolso cronogramaDesembolso;
}
