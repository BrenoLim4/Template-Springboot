package br.gov.ce.sop.convenios.api.exception;

import java.util.List;

public class ValidarProtocoloException extends WarningException {

    public ValidarProtocoloException(String message) {
        super(message);
    }

    public ValidarProtocoloException(List<String> errors) {
        super(errors);
    }
}