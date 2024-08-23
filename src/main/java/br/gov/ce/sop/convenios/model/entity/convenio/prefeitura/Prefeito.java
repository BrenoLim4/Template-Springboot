package br.gov.ce.sop.convenios.model.entity.convenio.prefeitura;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "prefeito", schema = "convenios")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Prefeito {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "id")
    private Integer id;
    @ManyToOne
    @JoinColumn(name = "id_prefeitura", referencedColumnName = "id_entidade_governamental")
    private Prefeitura prefeitura;
    @Column(name = "cpf", nullable = false)
    private String cpf;
    @Column(name = "nome", nullable = false)
    private String nome;
    @Column(name = "email", nullable = false)
    private String email;
    @Column(name = "ativo")
    private Boolean ativo = true;

}
