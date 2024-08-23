package br.gov.ce.sop.convenios.model.entity.celebracao;

import br.gov.ce.sop.convenios.model.entity.convenio.Convenio;
import br.gov.ce.sop.convenios.model.enums.StatusCelebracao;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.time.LocalDateTime;

@Data
@Entity
@Table(name = "celebracao", schema = "convenios")
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Celebracao implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @OneToOne
    @JoinColumn(name = "id_convenio", nullable = false)
    private Convenio convenio;

    @Enumerated
    @Column(name = "id_status")
    private StatusCelebracao statusCelebracao;

    @Column(name = "nr_protocolo")
    private String nrProtocolo;

    @Column(name = "data_hora_protocolo")
    private LocalDateTime dataHoraProtocolo;

    public Celebracao(Integer id) {
        this.id = id;
    }
}
