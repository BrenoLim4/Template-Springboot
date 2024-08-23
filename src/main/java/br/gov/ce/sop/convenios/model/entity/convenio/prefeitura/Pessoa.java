package br.gov.ce.sop.convenios.model.entity.convenio.prefeitura;


import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Entity
@Table(name = "pessoas", schema = "der_ce_coorporativo")
@Data
@EqualsAndHashCode(of = {"idPessoa"})
@NoArgsConstructor
@AllArgsConstructor
public class Pessoa implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    protected Integer idPessoa;
    @Column(name = "nome")
    protected String razaoSocial;
    protected String email;

}
