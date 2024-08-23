package br.gov.ce.sop.convenios.model.entity.convenio.prefeitura;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.validator.constraints.br.CNPJ;

@Entity
@Table(name = "pessoas_juridicas", schema = "der_ce_coorporativo")
//@PrimaryKeyJoinColumn(name = "id_pessoa")
//@Inheritance(strategy = InheritanceType.JOINED)
@Getter
@Setter
@NoArgsConstructor
public class PessoaJuridica{

    public static final int PREFEITURA = 1;
    public static final int EMPRESA_PRIVADA = 4;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_pessoa")
    private Integer idPessoa;

    @Column(name = "cnpj")
    @CNPJ
    private String cnpj;

    @Column(name = "nome_fantansia")
    private String nomeFantansia;

    @Column(name = "id_tipo_pessoa_juridica")
    private Integer idTipoPessoaJuridica;

    public PessoaJuridica(Integer idPessoa) {
        this.idPessoa = idPessoa;
    }
}
