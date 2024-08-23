package br.gov.ce.sop.convenios.api.exception;

public class ProtocoloJaExistente extends WarningException {

    public ProtocoloJaExistente(String protocolo) {
        super("Número de protocolo já existente " + protocolo);
    }
}
