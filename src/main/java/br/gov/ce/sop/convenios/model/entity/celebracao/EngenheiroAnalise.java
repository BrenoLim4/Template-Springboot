package br.gov.ce.sop.convenios.model.entity.celebracao;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Table(name = "engenheiro_analise", schema = "convenios")
@Data
public class EngenheiroAnalise {

    @Id
    private String matricula;
    private Boolean ativo = true;
    private String nome;
    private String email;

}
