package br.gov.ce.sop.convenios.model.entity.convenio.prefeitura;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "documento_prefeito_situacao_rejeitado", schema = "convenios")
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class DocumentoPrefeitoSituacaoRejeitado {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "id")
    private Integer id;
     
    @Column(name = "id_prefeito")
    private Integer idPrefeito;
     
    @Column(name = "id_documento")
    private Integer idDocumento;
     
    @Column(name = "comentario")
    private String comentario;
}
