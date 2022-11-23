package br.com.fink.filialapi.exceptions;

import java.util.ArrayList;
import java.util.List;

public class ValidationError extends StandardError {
    
    private List<FieldMessage> errors = new ArrayList<>();

    public ValidationError() {
    }

    public ValidationError(long timestamp, int status, String message) {
        super(timestamp, status, message);
    }

    public List<FieldMessage> getErrors() {
        return errors;
    }

    public void addErrors(String fieldName, String message) {
        this.errors.add(new FieldMessage(fieldName, message));
    }
   
}
