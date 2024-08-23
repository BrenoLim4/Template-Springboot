package br.gov.ce.sop.convenios.kafka.dto;

import br.gov.ce.sop.convenios.model.dto.email.AnexoDTO;
import br.gov.ce.sop.convenios.model.dto.email.BodyMail;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class SendMailMessage extends KafkaMessageBase{
    //    @NotNull
    private List<String> destinatarios;
    private String remetente;
    //    @NotNull
    private String assunto;
    //    @NotNull
    private BodyMail bodyMail;
    private List<String> cc;
    private Boolean confirmacaoLeitura;
    private List<AnexoDTO> anexos;

}
