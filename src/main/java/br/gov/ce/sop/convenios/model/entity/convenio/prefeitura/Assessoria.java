package br.gov.ce.sop.convenios.model.entity.convenio.prefeitura;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "pessoas_juridicas", schema = "der_ce_coorporativo")
@NoArgsConstructor
@AllArgsConstructor
@Data
public class Assessoria{

    @Column(name = "id_pessoa")
    @Id
    private Integer idPessoa;
    @Column(name = "cnpj")
    protected String cnpj;

    @Column(name = "nome_fantansia")
    protected String nomeFantasia;

    @JoinColumn(name = "id_pessoa", referencedColumnName = "id")
    @OneToOne
    private Pessoa pessoa;

    @Column(name = "id_tipo_pessoa_juridica")
    protected Integer idTipoPessoaJuridica = PessoaJuridica.EMPRESA_PRIVADA;

}
