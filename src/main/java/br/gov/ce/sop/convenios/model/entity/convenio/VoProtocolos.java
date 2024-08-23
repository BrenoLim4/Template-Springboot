package br.gov.ce.sop.convenios.model.entity.convenio;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;

import java.io.Serializable;
import java.time.LocalDateTime;

@Data
@Entity
@Table(name = "vw_protocolos", schema = "convenios")
public class VoProtocolos implements Serializable {
    @Id
    @Column(name="id")
    private Integer id;

    @Column(name="id_tipo_documento")
    private Integer idTipoDocumento;

    @Column(name="id_tipo_entidade")
    private Integer idTipoEntidade;

    @Column(name = "id_documento")
    private Integer idDocumento;

    @Column(name = "descricao")
    private String descricao;

    @Column(name = "nome_arquivo")
    private String nomeArquivo;

    @Column(name="data_inclusao")
    private LocalDateTime dataInclusao;

    @Column(name="nr_protocolo")
    private String nrProtocolo;

    @Column(name="id_convenente")
    private String idConvenente;

    @Column(name="convenente")
    private String convenente;

    @Column(name="objeto")
    private String objeto;

    @Column(name="id_status")
    private String idStatus;

    @Column(name="status")
    private String status;

    @Column(name="data_hora_protocolo")
    private LocalDateTime dataHoraProtocolo;
}
