package br.com.fink.filialapi.exceptions;

import java.time.LocalDateTime;

public class ErrorDetails {

    private LocalDateTime timestamp;
    private int status;
    private String message;
    private String details;
    private String developerMessage;

    public ErrorDetails() {
    }

    public ErrorDetails(Builder builder) {
        this.timestamp = builder.timestamp;
        this.status = builder.status;
        this.message = builder.message;
        this.details = builder.details;
        this.developerMessage = builder.developerMessage;
    }

    public ErrorDetails(LocalDateTime timestamp, int status, String message, String details, String developerMessage) {
        this.timestamp = timestamp;
        this.status = status;
        this.message = message;
        this.details = details;
        this.developerMessage = developerMessage;
    }

    public Builder builder() {
        return new Builder();
    }

    public LocalDateTime getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(LocalDateTime timestamp) {
        this.timestamp = timestamp;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getDetails() {
        return details;
    }

    public void setDetails(String details) {
        this.details = details;
    }

    public String getDeveloperMessage() {
        return developerMessage;
    }

    public void setDeveloperMessage(String developerMessage) {
        this.developerMessage = developerMessage;
    }

    public static class Builder {
        private LocalDateTime timestamp;
        private int status;
        private String message;
        private String details;
        private String developerMessage;

        public Builder() {
          // Construtor 
        }

        public Builder timestamp(LocalDateTime timestamp) {
            this.timestamp = timestamp;
            return this;
        }

        public Builder status(int status) {
            this.status = status;
            return this;
        }

        public Builder message(String message) {
            this.message = message;
            return this;
        }

        public Builder details(String details) {
            this.details = details;
            return this;
        }

        public Builder developerMessage(String developerMessage) {
            this.developerMessage = developerMessage;
            return this;
        }

        public ErrorDetails build() {
            return new ErrorDetails(this);
        } 
    }
}
