package br.gov.ce.sop.convenios.model.entity.planotrabalho;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;

@Data
@Entity
@Table(name = "contrapartida", schema = "convenios")
public class Contrapartida implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column
    @NotNull
    private BigDecimal valor = BigDecimal.ZERO;

    @ManyToOne
    @JoinColumn(name = "id_cronograma", nullable = false)
    private CronogramaDesembolso cronogramaDesembolso;
}
