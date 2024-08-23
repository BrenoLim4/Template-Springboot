package br.gov.ce.sop.convenios.model.entity.convenio.colaborador;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Entity
@Table(name = "pessoas_juridicas_colaboradores", schema = "der_ce_coorporativo")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Colaborador implements Serializable {
    @Column(name = "id_pessoa_juridica")
    private Integer idPessoaJuridica;
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "id")
    private Integer id;
    @Column(name = "cpf")
    private String cpf;
    @Column(name = "nome")
    private String nome;
    @Column(name = "email")
    private String email;
    @Column(name = "ativo")
    @Builder.Default
    private Boolean ativo = true;
    @Transient
    private Integer tipoColaborador;
}
