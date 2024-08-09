package test.group.counters.CustomExceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_FOUND)
public class CounterNotFoundException extends NotFoundException {
    public CounterNotFoundException() { super("counter not found exception"); }
    public CounterNotFoundException(String message) { super(message); }
}