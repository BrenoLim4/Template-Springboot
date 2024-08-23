package br.gov.ce.sop.convenios.model.entity.celebracao;

import br.gov.ce.sop.convenios.model.enums.StatusAnalise;
import br.gov.ce.sop.convenios.model.enums.TipoAnalise;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.time.LocalDateTime;

@Data
@Entity
@Table(name = "analise", schema = "convenios")
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Analise implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @Enumerated
    @Column(name = "id_status", nullable = false)
    private StatusAnalise statusAnalise;
    @ManyToOne
    @JoinColumn(name="id_celebracao", nullable=false, referencedColumnName = "id")
    private Celebracao celebracao;
    @Column(name = "data_ultima_acao")
    private LocalDateTime dataUltimaAcao;
    @Enumerated
    @Column(name = "id_tipo_analise", nullable = false)
    private TipoAnalise tipo;
    private boolean atual;
    private Integer versao;
    @Column(name = "matricula_engenheiro")
    private String matriculaEngenheiro;

    public Analise initNewVersion(){
        return Analise.builder()
                .celebracao(this.getCelebracao())
                .tipo(this.getTipo())
                .statusAnalise(StatusAnalise.AGUARDANDO_CORRECAO_DOCUMENTOS)
                .atual(true)
                .matriculaEngenheiro(this.getMatriculaEngenheiro())
                .versao(this.getVersao() + 1)
                .build();

    }

    public Analise(Integer id) {
        this.id = id;
    }
}
