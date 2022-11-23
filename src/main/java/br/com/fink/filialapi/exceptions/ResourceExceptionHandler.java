package br.com.fink.filialapi.exceptions;

import javax.servlet.ServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import br.com.fink.filialapi.services.exceptions.DataIntegrityViolationExcept;
import br.com.fink.filialapi.services.exceptions.ObjectNotFoundException;

@ControllerAdvice
public class ResourceExceptionHandler {
    private static final Logger log = LoggerFactory.getLogger(ResourceExceptionHandler.class);

    @ExceptionHandler(ObjectNotFoundException.class)
    public ResponseEntity<StandardError> objectNotFoundException(ObjectNotFoundException e, ServletRequest request) {
        log.info("Filial não encontrada");
        StandardError error = new StandardError((System.currentTimeMillis()), HttpStatus.NOT_FOUND.value(),
                e.getMessage());
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(error);
    }

    @ExceptionHandler(DataIntegrityViolationExcept.class)
    public ResponseEntity<StandardError> dataIntegrityViolationException(DataIntegrityViolationExcept e,
            ServletRequest request) {
        log.info("CNPJ já existente");
        StandardError error = new StandardError((System.currentTimeMillis()), HttpStatus.BAD_REQUEST.value(),
                e.getMessage());
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(error);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<StandardError> validationError(MethodArgumentNotValidException e, ServletRequest request) {
        log.info("Erro na validação dos campos");
        ValidationError errors = new ValidationError(System.currentTimeMillis(), HttpStatus.BAD_REQUEST.value(),
                "Erro na validação dos campos");
        for (FieldError fieldError : e.getBindingResult().getFieldErrors()) {
            errors.addErrors(fieldError.getField(), fieldError.getDefaultMessage());
        }
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errors);
    }

    @ExceptionHandler(HttpMessageNotReadableException.class)
    public ResponseEntity<StandardError> HttpMessageNotReadableException(HttpMessageNotReadableException e,
            ServletRequest request) {
        log.info("Tipo de Filial não é válido");
        StandardError error = new StandardError((System.currentTimeMillis()), HttpStatus.BAD_REQUEST.value(),
                "Tipo de Filial não é válido");
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(error);
    }
}
