package br.gov.ce.sop.convenios.model.enums;

import br.gov.ce.sop.convenios.model.entity.celebracao.Analise;
import br.gov.ce.sop.convenios.model.entity.celebracao.Celebracao;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum TipoAnalise {
    ADMINISTRATIVA("Análise Administrativa"),
    ENGENHARIA("Análise da Engenharia"),;

    private final String nome;

    public Analise createAnalise(Integer idCelebracao){
        //se for administrativo, irá direto para aguardando analise, caso seja engenharia, Aguardando atribuição do engenheiro
        return Analise.builder()
                .tipo(this)
                .statusAnalise(getStatusAnaliseFromType())
                .celebracao(new Celebracao(idCelebracao))
                .atual(true)
                .versao(1)
                .build();
    }

    public StatusAnalise getStatusAnaliseFromType(){
        //se for administrativo, irá direto para aguardando analise, caso seja engenharia, Aguardando atribuição do engenheiro
        return this.equals(TipoAnalise.ADMINISTRATIVA) ? StatusAnalise.AGUARDANDO_INICIO : StatusAnalise.AGUARDANDO_ATRIBUICAO_ENGENHEIRO;
    }
}
