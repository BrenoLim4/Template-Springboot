package br.gov.ce.sop.convenios.model.entity.documentodigital;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@Entity
@Table(name = "tipo_documento_x_tipo_assinador", schema = "documento_digital")
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class TipoDocumentoXTipoAssinador implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "id_tipo_documento")
    private Integer idTipoDocumento;

    @Column(name = "id_tipo_assinador")
    private Integer idTipoAssinador;

}
