package br.gov.ce.sop.convenios.model.dto.email;

import lombok.Builder;
import lombok.Data;

import java.util.Map;

@Data
@Builder
public class BodyMail {
    //parametros utilizado para montar o corpo do email
    private String titulo;
    private String mensagemPrincipal;
    private Link link;
    private Map<String, String> details;
    private Link linkFooter; // Cria url no rodapé do email que executa um redirect
}
