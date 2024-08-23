package br.gov.ce.sop.convenios.model.entity.celebracao.view;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;

import java.io.Serializable;
import java.time.LocalDate;

@Data
@Entity
@Table(name = "vw_celebracao_envios", schema = "convenios")
public class VoCelebracaoEnvios implements Serializable {
    @Id
    @Column(name = "id")
    private Integer id;

    @Column(name = "data_inclusao")
    private LocalDate dataInclusao;

    @Column(name = "nr_protocolo")
    private String nrProtocolo;

    @Column(name = "id_convenente")
    private Integer idConvenente;

    @Column(name = "convenente")
    private String convenente;

    @Column(name = "objeto")
    private String objeto;

    @Column(name = "status")
    private String status;

}
