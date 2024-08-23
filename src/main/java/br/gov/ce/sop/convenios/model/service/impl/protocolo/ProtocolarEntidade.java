package br.gov.ce.sop.convenios.model.service.impl.protocolo;


import lombok.Getter;

import java.util.Arrays;

@Getter
public enum ProtocolarEntidade {

    CELEBRACAO(410),
    PROCESSO_REPASSE(442),
    ADITIVO(420);


//    private final Protocolavel protocolavel;
    private final Integer idTipoEntidade;

    ProtocolarEntidade(Integer idTipoEntidade){
        this.idTipoEntidade = idTipoEntidade;
    }

//    public void protocolar(String nrProtocolo, LocalDateTime dataHoraProcesso, Object idEntidade){
//        protocolavel.protocolar(nrProtocolo, dataHoraProcesso, idEntidade);
//    }

    public static ProtocolarEntidade fromIdTipoEntidade(Integer idTipoEntidade){
        return Arrays.stream(ProtocolarEntidade.values())
                .filter(value -> value.idTipoEntidade.equals(idTipoEntidade))
                .findFirst()
                .orElseThrow(() -> new RuntimeException("not Implemented"));
    }
}
