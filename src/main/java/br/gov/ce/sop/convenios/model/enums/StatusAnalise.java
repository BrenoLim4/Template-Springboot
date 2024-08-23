package br.gov.ce.sop.convenios.model.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum StatusAnalise {
    AGUARDANDO_INICIO("Aguardando inicio"),
    AGUARDANDO_ATRIBUICAO_ENGENHEIRO("Aguardando atribuição do engenheiro"),
    PARCIALMENTE_CONFERIDA("Parcialmente conferida"),
    REPROVADA("Reprovada"),
    APROVADA("Aprovada"),
    AGUARDANDO_CORRECAO_DOCUMENTOS("Aguardando correção dos documentos");

    private final String nome;
}
