package br.gov.ce.sop.convenios.api.exception;

public class PontoControleNaoValidadoException extends WarningException{

    public PontoControleNaoValidadoException(){
        super("Não foi possível realizar ação! Há documentos pendentes.");
    }
}
