package br.gov.ce.sop.convenios.model.entity.convenio.prefeitura;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.io.Serializable;

@Entity
@Table(name = "vw_dados_prefeito", schema = "convenios")
@Getter
@EqualsAndHashCode
@ToString
public class VoDadosPrefeito implements Serializable {
    @Id
    private Integer id;
    @Setter
    private String cpf;
    private String nome;
    private Boolean ativo;
    @Column(name = "id_prefeitura")
    private Integer idPrefeitura;
    private String cnpj;
    @Column(name = "razao_social")
    private String razaoSocial;
}
