package br.gov.ce.sop.convenios.model.entity.planotrabalho;

import br.gov.ce.sop.convenios.model.entity.convenio.Convenio;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "plano_trabalho", schema = "convenios")
public class PlanoTrabalho  implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @Column(columnDefinition = "TEXT")
    private String descricao;
    @OneToOne
    @JoinColumn(name = "id_convenio", nullable = false)
    private Convenio convenio;
    @Column
    @NotNull
    @Builder.Default
    private BigDecimal valor = BigDecimal.ZERO;
    @Column(name = "valor_concessao")
    @Builder.Default
    private BigDecimal valorConcessao = BigDecimal.ZERO;
    @Builder.Default
    @Column(name = "valor_contrapartida")
    private BigDecimal valorContrapartida = BigDecimal.ZERO;
    @Column(name = "data_emissao")
    @Builder.Default
    private LocalDateTime dataEmissao = LocalDateTime.now();
    @Column
    private String numero;
    @Column
    private String registro;
}
