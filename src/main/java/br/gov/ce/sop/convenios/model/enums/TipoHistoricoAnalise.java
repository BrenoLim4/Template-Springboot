package br.gov.ce.sop.convenios.model.enums;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum TipoHistoricoAnalise {
    ANALISE_INSERIDA("Analise Inserida."),
    ENGENHEIRO_ATRIBUIDO("Engenheiro atributo."),
    ANALISE_INICIADA("Análise Iniciada."),
    ANALISE_REJEITADA("Análise Rejeitada."),
    ANALISE_APROVADA("Análise Aprovada."),
    ENGENHEIRO_SUBSTITUIDO("Engenheiro substituido."),
    ANALISE_REABERTA("Análise Reaberta.");

    private final String descricao;
}
