package br.gov.ce.sop.convenios.model.entity.planotrabalho;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;

@Data
@Entity
@Table(name = "cronograma_desembolso", schema = "convenios")
public class CronogramaDesembolso implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @OneToOne
    @JoinColumn(name = "id_plano_trabalho", nullable = false)
    private PlanoTrabalho planoTrabalho;

    @Column(name = "valor_repasse")
    @NotNull
    private BigDecimal valorRepasse = BigDecimal.ZERO;

    @Column(name = "valor_contrapartida")
    @NotNull
    private BigDecimal valorContrapartida = BigDecimal.ZERO;
}
