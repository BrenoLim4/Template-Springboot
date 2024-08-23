package br.gov.ce.sop.convenios.model.entity.celebracao;

import br.gov.ce.sop.convenios.model.enums.StatusConferenciaDocumento;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "analise_x_documento", schema = "convenios")
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class AnaliseXDocumento {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "id")
    private Integer id;
     
    @ManyToOne
    @JoinColumn(name = "id_analise", referencedColumnName = "id")
    private Analise analise;
     
    @Column(name = "id_documento")
    private Integer idDocumento;
     
    @Column(name = "id_documento_recusado")
    private Integer idDocumentoRecusado;
     
    @Column(name = "id_status_conferencia")
    @Enumerated
    private StatusConferenciaDocumento statusConferencia;
     
    @Column(name = "comentario")
    private String comentario;

    private String ocorrencia;
    @Column(name = "id_tipo_documento")
    private Integer idTipoDocumento;

}
