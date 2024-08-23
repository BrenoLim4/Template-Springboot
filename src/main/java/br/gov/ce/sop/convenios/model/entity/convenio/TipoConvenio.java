package br.gov.ce.sop.convenios.model.entity.convenio;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@Entity
@NoArgsConstructor
@Table(name = "tipo_convenio", schema = "convenios")
public class TipoConvenio implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(columnDefinition = "TEXT")
    @NotNull
    private String descricao;

    @Column(length = 3)
    private String sigla;

    public TipoConvenio(Integer id) {
        this.id = id;
    }

}