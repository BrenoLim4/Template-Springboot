package br.gov.ce.sop.convenios.model.entity.celebracao.view;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;

@Entity
@Table(name = "vw_lista_documentos_digitais_assinadores", schema = "documento_digital")
@Data
public class VoListaDocumentosDigitaisAssinadores {
     
    @Id
    private Integer id;

    @Column(name = "id_documento")
    private Integer idDocumento;

    @Column(name = "assinador_tipo")
    private String assinadorTipo;

    @Column(name = "status_assinado_id")
    private Integer statusAssinadoId;

    @Column(name = "status_assinado_descricao")
    private String statusAssinadoDescricao;
}
