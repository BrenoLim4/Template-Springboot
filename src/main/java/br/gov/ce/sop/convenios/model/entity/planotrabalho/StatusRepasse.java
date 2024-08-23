package br.gov.ce.sop.convenios.model.entity.planotrabalho;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.io.Serializable;

@Data
@Entity
@Table(name = "status_repasse", schema = "convenios")
public class StatusRepasse implements Serializable {

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
}
