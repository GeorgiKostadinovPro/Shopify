package common.exceptions;

public class CheckoutNotExistException extends RuntimeException {
    public CheckoutNotExistException(String _message) {
        super(_message);
    }
}
