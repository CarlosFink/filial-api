package br.com.fink.filialapi.exceptions;

import java.time.LocalDateTime;

import javax.servlet.ServletRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;

import br.com.fink.filialapi.services.exceptions.ObjectNotFoundException;

@ControllerAdvice
public class ResourceExceptionHandler {
    private static final Logger log = LoggerFactory.getLogger(ResourceExceptionHandler.class);

    @ExceptionHandler(ObjectNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ResponseEntity<ErrorDetails> objectNotFoundException(ObjectNotFoundException e, ServletRequest request) {
        log.info("Filial não encontrada");
        ErrorDetails errorDetails = new ErrorDetails().builder()
                .timestamp(LocalDateTime.now())
                .status(HttpStatus.NOT_FOUND.value())
                .message(e.getMessage())
                .details(e.getMessage())
                .developerMessage(e.getClass().getName())
                .build();
        return new ResponseEntity<>(errorDetails, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(DataIntegrityViolationException.class)
    public ResponseEntity<ErrorDetails> dataIntegrityViolationException(DataIntegrityViolationException e,
            ServletRequest request) {
        log.info("CNPJ já existente");
        ErrorDetails errorDetails = new ErrorDetails().builder()
                .timestamp(LocalDateTime.now())
                .status(HttpStatus.BAD_REQUEST.value())
                .message("Ação não permitida, CNPJ existente")
                .details(e.getMessage())
                .developerMessage(e.getClass().getName())
                .build();
        return new ResponseEntity<>(errorDetails, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ErrorDetails> methodArgumentNotValidException(MethodArgumentNotValidException e,
            ServletRequest request) {
        log.info("Erro na validação dos campos");        
        ValidationErrorDetails errorDetails = new ValidationErrorDetails(LocalDateTime.now(),
                HttpStatus.BAD_REQUEST.value(),
                "Erro na validação dos campos", e.getMessage(), e.getClass().getName());
        for (FieldError fieldError : e.getBindingResult().getFieldErrors()) {
            errorDetails.addErrors(fieldError.getField(), fieldError.getDefaultMessage());
        }
        return new ResponseEntity<>(errorDetails, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(HttpMessageNotReadableException.class)
    public ResponseEntity<ErrorDetails> httpMessageNotReadableException(HttpMessageNotReadableException e,
            ServletRequest request) {
        log.info("Erro na formatação da requisição");
        ErrorDetails errorDetails = new ErrorDetails().builder()
                .timestamp(LocalDateTime.now())
                .status(HttpStatus.BAD_REQUEST.value())
                .message("Erro na formatação da requisição")
                .details(e.getMessage())
                .developerMessage(e.getClass().getName())
                .build();
        return new ResponseEntity<>(errorDetails, HttpStatus.BAD_REQUEST);
    }  
}
