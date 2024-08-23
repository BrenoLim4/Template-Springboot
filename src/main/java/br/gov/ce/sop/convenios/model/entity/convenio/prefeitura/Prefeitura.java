package br.gov.ce.sop.convenios.model.entity.convenio.prefeitura;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "prefeituras", schema = "der_ce_coorporativo")
//@PrimaryKeyJoinColumn(name = "id_entidade_governamental", referencedColumnName = "id_pessoa")
@NoArgsConstructor
@Data
@AllArgsConstructor
public class Prefeitura{

    @Column(name = "id_entidade_governamental")
    @Id
    private Integer idPessoa;
    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_empresa_assessora", referencedColumnName = "id_pessoa")
    private Assessoria assessoria;
    @OneToOne
    @JoinColumn(name = "id_entidade_governamental", referencedColumnName = "id_pessoa")
    private PessoaJuridica pessoaJuridica;

    public Prefeitura(Integer idPessoa) {
        this.idPessoa = idPessoa;
    }
}
