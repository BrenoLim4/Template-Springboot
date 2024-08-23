package br.gov.ce.sop.convenios.model.entity.convenio;

import br.gov.ce.sop.convenios.model.enums.TipoHistoricoConvenio;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.time.LocalDateTime;

@Data
@Entity
@Table(name = "historico_convenio", schema = "convenios")
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class HistoricoConvenio implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @ManyToOne
    @JoinColumn(name="id_convenio", nullable=false)
    private Convenio convenio;

    @Column(name = "id_tipo_historico")
    @Enumerated(EnumType.ORDINAL)
    private TipoHistoricoConvenio tipoHistoricoConvenio;

    @Column(name = "data_hora")
    @Temporal(TemporalType.TIMESTAMP)
    @NotNull
    @Builder.Default
    private LocalDateTime dataHora = LocalDateTime.now();

    @Column(length = 14)
    @NotNull
    private String matricula;

    @Column(columnDefinition = "TEXT")
    private String observacao;

}
