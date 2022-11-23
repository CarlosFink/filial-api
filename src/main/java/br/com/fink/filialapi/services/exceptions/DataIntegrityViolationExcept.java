package br.com.fink.filialapi.services.exceptions;

public class DataIntegrityViolationExcept extends RuntimeException {

    public DataIntegrityViolationExcept(String message, Throwable cause) {
        super(message, cause);
    }
    
    public DataIntegrityViolationExcept(String message) {
        super(message);
    }
}
