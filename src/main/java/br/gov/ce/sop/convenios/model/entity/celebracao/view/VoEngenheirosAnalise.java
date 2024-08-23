package br.gov.ce.sop.convenios.model.entity.celebracao.view;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.Immutable;

/**
 * Mapping for DB view
 */

@Entity
@Immutable
@Table(name = "vw_engenheiros_analise", schema = "convenios")
@NoArgsConstructor
@AllArgsConstructor
public class VoEngenheirosAnalise {

    @Id
    @Getter
    private String matricula;

    @Getter
    private String nome;
    @Getter
    private Boolean ativo;
    @Getter
    private String email;
    @Getter
    @Column(name = "analises_concluidas")
    private Integer analisesConcluidas;

    @Getter
    @Column(name = "analises_vigentes")
    private Integer analisesVigentes;

    @Transient
    private Integer totalAnalises;

    public Integer getTotalAnalises() {
        return analisesConcluidas + analisesVigentes;
    }

    public VoEngenheirosAnalise(String matricula, String nome, Boolean ativo) {
        this.matricula = matricula;
        this.nome = nome;
        this.ativo = ativo;
    }
}