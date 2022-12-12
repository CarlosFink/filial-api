package br.com.fink.filialapi.services.exceptions;

public class ExceptionDefault extends RuntimeException {

    public ExceptionDefault(String message, Throwable cause) {
        super(message, cause);
    }
    
    public ExceptionDefault(String message) {
        super(message);
    }    
}
