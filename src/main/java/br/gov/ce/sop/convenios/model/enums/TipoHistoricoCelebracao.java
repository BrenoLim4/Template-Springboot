package br.gov.ce.sop.convenios.model.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum TipoHistoricoCelebracao {
    INSERIDA("Processo de celebração iniciado."),
    PROTOCOLADA("Protocolado."),
    ENVIOS_DOCUMENTOS_FINALIZADO("Envio dos documentos finalizado."),
    ANALISE_FINALIZADA("Análise finalizada."),
    ENVIO_DOCUMENTOS_REJEITADOS_FINALIZADO("Envio dos documentos rejeitados finalizado."),
    PARECER_GECOV_CADASTRADO("Envio da documentação complementar finalizado."),
    ARQUIVADO("Processo de celebração arquivado"),
    PUBLICACAO_CADASTRADA("Publicação cadastrada");

    private final String descriacao;
}
