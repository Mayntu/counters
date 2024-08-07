package test.group.counters.CustomExceptions;

public class InvalidCounterException extends InvalidDataException {
    public InvalidCounterException() { super("invalid counter exception"); }
    public InvalidCounterException(String message) {
        super(message);
    }
}
