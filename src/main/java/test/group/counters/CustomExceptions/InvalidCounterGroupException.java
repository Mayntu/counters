package test.group.counters.CustomExceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.BAD_REQUEST)
public class InvalidCounterGroupException extends InvalidDataException {
    public InvalidCounterGroupException() { super("invalid counter group exception"); }
    public InvalidCounterGroupException(String message) {
        super(message);
    }
}
