package br.gov.ce.sop.convenios.model.entity.convenio;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;

import java.io.Serializable;
import java.time.LocalDateTime;

@Entity
@Table(name = "vw_cadastros_convenio", schema = "convenios")
@Data
public class VoCadastrosConvenio implements Serializable {
    @Id
    @Column(name="id")
    private Integer id;

    @Column(name="id_convenente")
    private Integer idConvenente;

    @Column(name="convenente")
    private String convenente;

    @Column(name="objeto")
    private String objeto;

    @Column(name="mapps_ids")
    private String mappsIds;

    @Column(name="status")
    private String status;

    @Column(name="data_cadastro")
    private LocalDateTime dataCadastro;

    @Column(name="nr_protocolo")
    private String nrProtocolo;

}
