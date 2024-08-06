package test.group.counters.config;


import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import test.group.counters.CustomExceptions.*;

import java.util.Date;

@ControllerAdvice
public class GlobalExceptionHandler
{
    @ExceptionHandler(CounterNotFoundException.class)
    public ResponseEntity<?> handleCounterNotFoundException(CounterNotFoundException ex, WebRequest request) {
        ErrorDetails errorDetails = new ErrorDetails(new Date(), ex.getMessage(), request.getDescription(false));
        return new ResponseEntity<>(errorDetails, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(CounterGroupNotFoundException.class)
    public ResponseEntity<?> handleCounterGroupNotFoundException(CounterGroupNotFoundException ex, WebRequest request) {
        ErrorDetails errorDetails = new ErrorDetails(new Date(), ex.getMessage(), request.getDescription(false));
        return new ResponseEntity<>(errorDetails, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(CounterReadingNotFoundException.class)
    public ResponseEntity<?> handleCounterReadingNotFoundException(CounterReadingNotFoundException ex, WebRequest request) {
        ErrorDetails errorDetails = new ErrorDetails(new Date(), ex.getMessage(), request.getDescription(false));
        return new ResponseEntity<>(errorDetails, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(InvalidCounterGroupException.class)
    public ResponseEntity<?> handleInvalidCounterGroupException(InvalidCounterGroupException ex, WebRequest request) {
        ErrorDetails errorDetails = new ErrorDetails(new Date(), ex.getMessage(), request.getDescription(false));
        return new ResponseEntity<>(errorDetails, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(InvalidCounterJoinDataException.class)
    public ResponseEntity<?> handleInvalidCounterJoinDataException(InvalidCounterJoinDataException ex, WebRequest request) {
        ErrorDetails errorDetails = new ErrorDetails(new Date(), ex.getMessage(), request.getDescription(false));
        return new ResponseEntity<>(errorDetails, HttpStatus.BAD_REQUEST);
    }

    public static class ErrorDetails {
        private Date timestamp;
        private String message;
        private String details;

        public ErrorDetails(Date timestamp, String message, String details) {
            super();
            this.timestamp = timestamp;
            this.message = message;
            this.details = details;
        }

        public void setTimestamp(Date timestamp) {
            this.timestamp = timestamp;
        }

        public Date getTimestamp() {
            return timestamp;
        }

        public void setMessage(String message) {
            this.message = message;
        }

        public String getMessage() {
            return message;
        }

        public void setDetails(String details) {
            this.details = details;
        }

        public String getDetails() {
            return details;
        }
    }
}
