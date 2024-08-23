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
@Table(name = "vw_celebracao_detalhe_resumo", schema = "convenios")
public class VoCelebracaoDetalheResumo implements Serializable{
    @Id
    @Column(name = "id_celebracao")
    private Integer id;
    @Column(name = "id_convenio")
    private Integer idConvenio;
    @Column(name = "id_tipo_convenio")
    private Integer idTipoConvenio;
    @Column(name = "tipo_convenio")
    private String tipoConvenio;
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
    @Column(name = "mapps_ids")
    private String mappsIds;
    @Column(name = "data_inclusao")
    private LocalDate dataInclusao;
    @Column(name = "id_status")
    private Integer idStatus;
//    @Column(name = "status")
    private String status;
    @Column(name = "email_prefeitura")
    private String emailPrefeito;
    @Column(name = "email_assessoria")
    private String emailAssessoria;
}
