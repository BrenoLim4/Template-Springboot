package br.gov.ce.sop.convenios.model.enums;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum StatusCelebracao {

    AGUARDANDO_PROTOCOLO("Aguardando Protocolo"),
    AGUARDANDO_ENVIO_DOCUMENTOS("Aguardando Envio dos Documentos"),
    AGUARDANDO_ANALISE_DOCUMENTOS("Aguardando Análise"),
    AGUARDANDO_CORRECAO_DOCUMENTOS("Aguardando Correção dos Documentos"),
    AGUARDANDO_PARECER_GECOV("Aguardando Parecer Gecov"),
//    AGUARDANDO_CERTIDAO_REGULARIDADE,
//    AGUARDANDO_ANALISE_JURIDICO,
//    AGUARDANDO_FINALIZACAO_EPARCERIAS,
    AGUARDANDO_PUBLICACAO("Aguardando Publicação"),
    PUBLICADA("Publicada"),
    ARQUIVADA("Arquivada"),;


    private final String description;
}
