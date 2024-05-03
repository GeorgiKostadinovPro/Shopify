package common.exceptions;

public class InsufficientQuantityException extends RuntimeException {
    public InsufficientQuantityException(String _message) {
        super(_message);
    }
}
