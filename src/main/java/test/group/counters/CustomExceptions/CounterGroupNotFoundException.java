package test.group.counters.CustomExceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_FOUND)
public class CounterGroupNotFoundException extends NotFoundException {
    public CounterGroupNotFoundException() { super("counter group not found exception"); }
    public CounterGroupNotFoundException(String message) { super(message); }
}