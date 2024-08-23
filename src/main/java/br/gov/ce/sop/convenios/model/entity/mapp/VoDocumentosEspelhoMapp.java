package br.gov.ce.sop.convenios.model.entity.mapp;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "vw_documentos_espelho_mapp", schema = "convenios")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class VoDocumentosEspelhoMapp {
    @Id
    private Long id  ;
    @Column(name = "id_entidade")
    private Integer idEntidade  ;
    @Column(name = "id_tipo_documento")
    private Integer idTipoDocumento  ;
    @Column(name = "id_convenio")
    private Integer idConvenio  ;
    @Column(name = "id_mapp")
    private Integer idMapp  ;
    @Column(name = "codigo_mapp")
    private String codigoMapp  ;
    private String objeto  ;
    private String programa  ;
    @Column(name = "id_documento")
    private Integer idDocumento  ;
    private Boolean enviado  ;
    @Column(name = "status_envio")
    private String statusEnvio;
}

