package common.exceptions;

public class CashierNotExistException extends RuntimeException {
    public CashierNotExistException(String _message) {
        super(_message);
    }
}
