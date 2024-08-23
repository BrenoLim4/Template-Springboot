package br.gov.ce.sop.convenios.api;

import br.gov.ce.sop.convenios.api.exception.*;
import lombok.extern.java.Log;
import org.springframework.context.support.DefaultMessageSourceResolvable;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.reactive.function.client.WebClientRequestException;
import org.springframework.web.reactive.function.client.WebClientResponseException;

import java.net.URI;
import java.util.List;
import java.util.NoSuchElementException;

@RestControllerAdvice
@Log
public class ApplicationControllerAdvice  {

    @ExceptionHandler(ReportException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ApiErrors handleReportException(ReportException ex) {
        return new ApiErrors(ex.getMessage(), Level.ERROR);
    }

    @ExceptionHandler(RepositoryException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ApiErrors handleRepositoryException(RepositoryException ex) {
        return new ApiErrors(ex.getMessage(), Level.ERROR);
    }

    @ExceptionHandler(NoSuchElementException.class)
    @ResponseStatus(HttpStatus.NOT_ACCEPTABLE)
    public ApiErrors handleNoSuchElementException(NoSuchElementException ex) {
        return new ApiErrors(ex.getMessage(), Level.ERROR);
    }

    @ExceptionHandler(RuntimeException.class)
    @ResponseStatus(HttpStatus.CONFLICT)
    public ApiErrors handleRuntimeException(RuntimeException ex) {
        return new ApiErrors(ex.getMessage(), Level.ERROR);
    }

    @ExceptionHandler(Exception.class)
    @ResponseStatus(HttpStatus.CONFLICT)
    public ApiErrors handleException(Exception ex) {
        return new ApiErrors(ex.getMessage(), Level.ERROR);
    }

    @ExceptionHandler(WebClientResponseException.class)
    @ResponseStatus(HttpStatus.CONFLICT)
    public ApiErrors handleWebClientResponseException(WebClientResponseException ex) {
        if (ex.getStatusCode().equals(HttpStatus.FORBIDDEN)) {
            log.severe(" - Autenticação via token falhou");
        }
        return new ApiErrors(ex.getMessage(), Level.ERROR);
    }

    @ExceptionHandler(WebClientRequestException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ApiErrors handleWebClientRequestException(WebClientRequestException ex) {
        log.severe(ex.getMessage());
        if(ex.getMessage().startsWith("Connection refused")){
            URI uri = ex.getUri();
            return new ApiErrors(String.format("Conexão recusada: host %s://%s",uri.getScheme(), uri.getAuthority()), Level.ERROR);
        }
        return new ApiErrors(ex.getMessage(), Level.ERROR);
    }
    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseStatus(HttpStatus.NOT_ACCEPTABLE)
    public ApiErrors handleNotValidException(MethodArgumentNotValidException ex) {
        List<String> errors = ex.getBindingResult()
                .getAllErrors()
                .stream()
                .map(DefaultMessageSourceResolvable::getDefaultMessage)
                .toList();
        return new ApiErrors(errors, Level.WARNING);
    }

    @ResponseStatus(HttpStatus.NOT_ACCEPTABLE)
    @ExceptionHandler(WarningException.class)
    public ApiErrors handleWarningException(WarningException ex) {
        return new ApiErrors(ex.getAllErrors(), Level.WARNING);
    }

}
