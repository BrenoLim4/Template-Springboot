package br.gov.ce.sop.convenios.model.entity.celebracao;

import br.gov.ce.sop.convenios.model.enums.TipoHistoricoAnalise;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.time.LocalDateTime;

@Data
@Entity
@Table(name = "historico_analise", schema = "convenios")
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class HistoricoAnalise implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "id_analise")
    private Integer idAnalise;

    @Enumerated
    @Column(name = "id_tipo_historico")
    private TipoHistoricoAnalise tipoHistoricoAnalise;

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

    public String getTipo(){
        return tipoHistoricoAnalise.getDescricao();
    }


}
