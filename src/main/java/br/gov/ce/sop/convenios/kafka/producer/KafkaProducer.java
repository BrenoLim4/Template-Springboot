package br.gov.ce.sop.convenios.kafka.producer;

import java.util.function.Supplier;

public interface KafkaProducer {

    void sendMessage(Supplier<ProducerRecord> producerRecordSupplier);
}
