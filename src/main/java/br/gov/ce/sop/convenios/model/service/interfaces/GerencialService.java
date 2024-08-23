package br.gov.ce.sop.convenios.model.service.interfaces;

import java.time.LocalDateTime;

public interface GerencialService {
    void protocolar(String nrProtocolo, LocalDateTime dataHoraProcesso, Object idEntidade, Integer idTipoEntidade);
}
