package br.com.fink.filialapi.exceptions;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class ValidationErrorDetails extends ErrorDetails {

    private List<FieldMessage> errors = new ArrayList<>();

    public ValidationErrorDetails() {
    }

    public ValidationErrorDetails(LocalDateTime timestamp, int status, String message, String details,
            String developerMessage) {
        super(timestamp, status, message, details, developerMessage);
    }

    public List<FieldMessage> getErrors() {
        return errors;
    }

    public void addErrors(String fieldName, String message) {
        this.errors.add(new FieldMessage(fieldName, message));
    }

}