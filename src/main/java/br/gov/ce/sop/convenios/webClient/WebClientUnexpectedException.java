package br.gov.ce.sop.convenios.webClient;

public class WebClientUnexpectedException extends RuntimeException {


    public WebClientUnexpectedException(String msgError){
        super(msgError);
    }
}
