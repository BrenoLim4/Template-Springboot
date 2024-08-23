package br.gov.ce.sop.convenios.kafka.producer;

import br.gov.ce.sop.convenios.kafka.dto.KafkaMessageBase;
import lombok.Builder;

import java.util.Objects;
import java.util.function.Supplier;

@Builder
public record ProducerRecord(String topico, Supplier<KafkaMessageBase> message, Integer partition) {


    public ProducerRecord {
        partition = Objects.isNull(partition) ? 0 : partition;
    }
}
