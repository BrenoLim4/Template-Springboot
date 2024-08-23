package br.gov.ce.sop.convenios.model.entity.planotrabalho;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@Entity
@Table(name = "processo_repasse", schema = "convenios")
public class ProcessoRepasse implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;

    @Column(name = "nr_protocolo")
    private String nrProtocolo;

    @Column(name = "valor")
    @NotNull
    private BigDecimal valor = BigDecimal.ZERO;

    @Column(name = "data_hora_protocolo")
    private LocalDateTime dataHoraProtocolo;

    @Column(name = "observacao")
    private String observacao;

    @ManyToOne
    @JoinColumn(name = "id_repasse", nullable = false)
    private Repasse repasse;

    @ManyToOne
    @JoinColumn(name = "id_status", nullable = false)
    private StatusRepasse status;

    @Column(name = "objeto")
    private String objeto;

    @Column(name = "id_despesa")
    private Integer idDespesa;

//    TODO: Adicionar despacho
//    @ManyToOne
//    @JoinColumn(name = "id_despacho")
//    private Despacho despacho;

}
