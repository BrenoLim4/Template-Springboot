package br.gov.ce.sop.convenios.api.exception;

import lombok.Getter;

import java.util.Collections;
import java.util.List;

@Getter
public class WarningException extends RuntimeException {

    private final List<String> allErrors;
    public WarningException(String message) {
        super(message);
        allErrors = Collections.singletonList(message);
    }

    public WarningException(List<String> errors) {
//        super();
        allErrors = errors;
    }
}
