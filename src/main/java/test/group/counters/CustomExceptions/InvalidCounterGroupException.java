package test.group.counters.CustomExceptions;

public class InvalidCounterGroupException extends InvalidDataException {
    public InvalidCounterGroupException() { super("invalid counter group exception"); }
    public InvalidCounterGroupException(String message) {
        super(message);
    }
}
