package test.group.counters.CustomExceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.BAD_REQUEST)
public class InvalidCounterReadingException extends InvalidDataException {
    public InvalidCounterReadingException() { super("invalid counter reading exception"); }
    public InvalidCounterReadingException(String message) { super(message); }
}