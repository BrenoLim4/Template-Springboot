package br.gov.ce.sop.convenios.kafka.producer;

import br.gov.ce.sop.convenios.api.exception.WarningException;
import br.gov.ce.sop.convenios.kafka.dto.KafkaMessageBase;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.java.Log;
import org.apache.kafka.common.KafkaException;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.stereotype.Service;

import java.util.Objects;
import java.util.function.Supplier;

@Service
@EnableScheduling
@RequiredArgsConstructor
@Log
public class KafkaProducerImpl implements KafkaProducer {

    private final KafkaTemplate<String, String> kafkaTemplate;
    private final ObjectMapper objectMapper = new ObjectMapper();

    @Override
    public void sendMessage(Supplier<ProducerRecord> producerRecordSupplier) {
        Objects.requireNonNull(producerRecordSupplier);
        ProducerRecord record = producerRecordSupplier.get();
        KafkaMessageBase message = record.message().get();
        try {
            String jsonMessage = objectMapper.writeValueAsString(message);
            kafkaTemplate.send(record.topico(), record.partition(), message.getDataHoraGeracao().getTime(), message.getKey().toString(), jsonMessage);
//            kafkaTemplate.send(record.topico(), message.getKey().toString(), jsonMessage);
            log.info("Message sent to topic " + message.getKey().toString());
        } catch (JsonProcessingException e) {
            throw new WarningException(String.format("não foi possível converter objeto %s em json", record.message().getClass().getName()));
        } catch (KafkaException ex){
            throw new RuntimeException(ex.getMessage());
        }
    }
}
