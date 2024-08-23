package br.gov.ce.sop.convenios.model.entity.celebracao;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@Entity
@Table(name = "status_analise", schema = "convenios")
@AllArgsConstructor
@NoArgsConstructor
public class StatusAnalise implements Serializable {

    public static final int AGUARDANDO_INICIO = 1;
    public static final int PARCIALMENTE_CONFERIDA = 2;
    public static final int REJEITADA = 3;
    public static final int APROVADA = 4;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column()
    @NotNull
    private String descricao;

    @Column(length = 3)
    @NotNull
    private String sigla;

    @Column
    private String cor;

    public StatusAnalise(Integer id){
        this.id = id;
    }

}
