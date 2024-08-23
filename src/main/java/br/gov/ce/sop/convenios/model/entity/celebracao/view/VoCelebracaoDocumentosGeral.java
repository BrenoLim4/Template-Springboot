package br.gov.ce.sop.convenios.model.entity.celebracao.view;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "vw_celebracao_documentos_geral", schema = "convenios")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class VoCelebracaoDocumentosGeral {
    @Id
    private Integer id;
    @Column(name = "id_celebracao")
    private Integer idCelebracao;
    @Column(name = "id_documento")
    private Long idDocumento;
    @Column(name = "id_entidade")
    private String idEntidade;
    @Column(name = "nome_arquivo")
    private String nomeArquivo;
}
