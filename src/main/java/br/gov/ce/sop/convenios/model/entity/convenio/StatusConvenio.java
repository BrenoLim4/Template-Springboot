package br.gov.ce.sop.convenios.model.entity.convenio;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@Entity
@Table(name = "status_convenio", schema = "convenios")
@AllArgsConstructor
@NoArgsConstructor
public class StatusConvenio implements Serializable {

    public static final int AGUARDANDO_CELEBRACAO = 1;
    public static final int VIGENTE               = 2;
    public static final int CONCLUIDO             = 3;
    public static final int VENCIDO               = 4;
    public static final int RESCINDIDO            = 5;
    public static final int PARALISADO            = 6;
    public static final int CANCELADO             = 7;


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(columnDefinition = "TEXT")
    @NotNull
    private String descricao;

    @Column(length = 3)
    @NotNull
    private String sigla;

    @Column
    private String cor;

    public StatusConvenio(Integer id) {
        this.id = id;
    }

}
