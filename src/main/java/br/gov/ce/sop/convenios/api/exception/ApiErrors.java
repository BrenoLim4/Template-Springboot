package br.gov.ce.sop.convenios.api.exception;

import lombok.Getter;
import lombok.extern.java.Log;

import java.util.Collections;
import java.util.List;

@Getter
@Log
public class ApiErrors {

    private final List<String> errors;

    public ApiErrors(List<String> errors, Level level){
        errors = errors.stream().map(level::addLevel).toList();
        errors.forEach(level::gravarLog);
        this.errors = errors;
    }

    public ApiErrors(String message, Level level){
        message = level.addLevel(message);
        level.gravarLog(message);
        this.errors = Collections.singletonList(message);
    }

}