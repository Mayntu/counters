package test.group.counters.CustomExceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.BAD_REQUEST)
public class InvalidCounterException extends InvalidDataException {
    public InvalidCounterException() { super("invalid counter exception"); }
    public InvalidCounterException(String message) {
        super(message);
    }
}
