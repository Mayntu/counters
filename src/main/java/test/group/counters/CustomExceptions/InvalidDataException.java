package test.group.counters.CustomExceptions;

public class InvalidDataException extends RuntimeException {
    public InvalidDataException(String message)
    {
        super(message);
    }
}
