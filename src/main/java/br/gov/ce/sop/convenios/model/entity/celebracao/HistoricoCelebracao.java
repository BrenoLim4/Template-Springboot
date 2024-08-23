package br.gov.ce.sop.convenios.model.entity.celebracao;

import br.gov.ce.sop.convenios.model.enums.TipoHistoricoCelebracao;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.time.LocalDateTime;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "historico_celebracao", schema = "convenios")
public class HistoricoCelebracao implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name="id_celebracao", nullable=false)
    private Integer idCelebracao;

    @Column(name = "id_tipo_historico", nullable = false)
    @Enumerated
    private TipoHistoricoCelebracao tipoHistoricoCelebracao;

    @Column(name = "data_hora")
    @NotNull
    @Builder.Default
    private LocalDateTime dataHora = LocalDateTime.now();

    @Column(length = 14)
    @NotNull
    private String matricula;

    @Column(columnDefinition = "TEXT")
    private String observacao;
    @Column(name = "nome_usuario")
    private String nomeUsuario;
    @Transient
    private String tipo;

    public String getTipo() {
        return tipoHistoricoCelebracao.getDescriacao();
    }
}
