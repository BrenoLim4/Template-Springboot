package br.gov.ce.sop.convenios.model.entity.celebracao.view;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;

import java.io.Serializable;
import java.time.LocalDate;

@Entity
@Table(name = "vw_celebracao_aguardando_parecer", schema = "convenios")
@Data
public class VoCelebracaoAguardandoParecer implements Serializable {
    @Id
    private Integer id;
    @Column(name = "id_convenio")
    private Integer idConvenio;
    @Column(name = "cnpj_convenente")
    private String cnpjConvenente;
    @Column(name = "convenente")
    private String convenente;
    @Column(name = "nr_protocolo")
    private String nrProtocolo;
    @Column(name = "prefeito")
    private String prefeito;
    @Column(name = "cpf_prefeito")
    private String cpfPrefeito;
    @Column(name = "objeto")
    private String objeto;
    @Column(name = "data_inclusao")
    private LocalDate dataInclusao;
    @Column(name = "id_status")
    private Integer idStatus;
    @Column(name = "status")
    private String status;
    @Column(name = "mapps")
    private String mapps;
}
