package br.gov.ce.sop.convenios.model.service.impl.protocolo;

import br.gov.ce.sop.convenios.model.service.interfaces.Protocolavel;

import java.time.LocalDateTime;

public class ProtocolarAditivo implements Protocolavel {



    @Override
    public void protocolar(String nrProtocolo, LocalDateTime dataHoraProcesso, Object idEntidade) {
        System.out.println("Aditivo Protocolado com sucesso [" + nrProtocolo + "] entidade ["+idEntidade+"]");
    }

}
