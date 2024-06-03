package common.exceptions;

public class ExistingCashierException extends RuntimeException {
    public ExistingCashierException(String _message) {
        super(_message);
    }
}
