package br.gov.ce.sop.convenios.kafka.dto;


import br.gov.ce.sop.convenios.api.dto.EngenheiroAnaliseDTO;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class EngenheiroCadastradoMessage extends KafkaMessageBase{


    private String matricula;
    private String nome;
    private String email;

    public EngenheiroCadastradoMessage(EngenheiroAnaliseDTO engenheiro) {
        this.matricula = engenheiro.matricula();
        this.nome = engenheiro.nome();
        this.email = engenheiro.email();
    }
}
