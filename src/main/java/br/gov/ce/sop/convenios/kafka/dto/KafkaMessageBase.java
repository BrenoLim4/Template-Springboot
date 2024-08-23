package br.gov.ce.sop.convenios.kafka.dto;

import lombok.EqualsAndHashCode;
import lombok.Getter;

import java.time.Instant;
import java.util.Date;
import java.util.UUID;

@Getter
@EqualsAndHashCode
public abstract class KafkaMessageBase {

    protected final UUID key = UUID.randomUUID();
    protected final Date dataHoraGeracao = Date.from(Instant.now());

}
