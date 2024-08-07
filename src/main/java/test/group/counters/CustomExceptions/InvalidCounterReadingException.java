package test.group.counters.CustomExceptions;

public class InvalidCounterReadingException extends InvalidDataException {
    public InvalidCounterReadingException() { super("invalid counter reading exception"); }
    public InvalidCounterReadingException(String message) { super(message); }
}
