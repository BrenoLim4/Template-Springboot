package br.gov.ce.sop.convenios.kafka;

import br.gov.ce.sop.convenios.kafka.producer.KafkaProducer;
import br.gov.ce.sop.convenios.kafka.producer.ProducerRecord;
import br.gov.ce.sop.convenios.model.dto.email.BodyMail;
import br.gov.ce.sop.convenios.kafka.dto.SendMailMessage;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.stereotype.Component;

import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.Map;

@EnableScheduling
@Component
@RequiredArgsConstructor
public class TesteKafka {

    @Value("${convenio.email-grupo}")
    private String GECOV_EMAIL_GRUPO;

    private final KafkaProducer kafkaProducer;

//    @Scheduled(fixedRate = 60000)
    public void producerMessage() {
        Map<String, String> details = new LinkedHashMap<>();
        details.put("Contexto", "Teste kafka");
        BodyMail bodyMail = BodyMail.builder()
                .titulo("Teste do email")
                .mensagemPrincipal("Testando produção de mensagens de email")
                .details(details)
                .build();

        kafkaProducer.sendMessage(() -> ProducerRecord.builder()
                .topico("celebracao-solicitada")
                .message(() -> SendMailMessage.builder()
                        .assunto("Teste email")
                        .destinatarios(Collections.singletonList("brenolima756@gmail.com"))
                        .bodyMail(bodyMail)
                        .cc(Collections.singletonList(GECOV_EMAIL_GRUPO))
                        .confirmacaoLeitura(true)
                        .build()
                )
                .build()
        );
    }

}
