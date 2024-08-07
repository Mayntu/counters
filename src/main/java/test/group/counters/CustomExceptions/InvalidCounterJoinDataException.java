package test.group.counters.CustomExceptions;

public class InvalidCounterJoinDataException extends InvalidDataException {
    public InvalidCounterJoinDataException() { super("invalid counter join data exception"); }
    public InvalidCounterJoinDataException(String message) {
        super(message);
    }
}
