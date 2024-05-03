package common.exceptions;

public class InsufficientPaymentException extends RuntimeException {
    public InsufficientPaymentException(String _message) {
        super(_message);
    }
}
