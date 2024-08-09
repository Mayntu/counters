package test.group.counters.CustomExceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_FOUND)
public class CounterReadingNotFoundException extends NotFoundException {
    public CounterReadingNotFoundException() { super("counter reading not found exception"); }
    public CounterReadingNotFoundException(String message) { super(message); }
}