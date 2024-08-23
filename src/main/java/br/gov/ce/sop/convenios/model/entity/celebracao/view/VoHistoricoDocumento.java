package br.gov.ce.sop.convenios.model.entity.celebracao.view;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.Immutable;

import java.time.Instant;

/**
 * Mapping for DB view
 */
@Getter
@Setter
@Entity
@Immutable
@Table(name = "vw_lista_documentos_digitais_historicos", schema = "documento_digital")
public class VoHistoricoDocumento {
    @Id
    @Column(name = "id")
    private Integer id;

    @Column(name = "id_documento")
    private Integer idDocumento;

    @Size(max = 50)
    @Column(name = "tipo_historico", length = 50)
    private String tipoHistorico;

    @Column(name = "data_hora")
    private Instant dataHora;

    @Size(max = 14)
    @Column(name = "usuario_matricula", length = 14)
    private String usuarioMatricula;

    @Size(max = 20)
    @Column(name = "usuario_nome", length = 20)
    private String usuarioNome;

    @Size(max = 255)
    @Column(name = "observacao")
    private String observacao;

}