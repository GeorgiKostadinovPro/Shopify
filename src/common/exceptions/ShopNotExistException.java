package common.exceptions;

public class ShopNotExistException extends RuntimeException {
    public ShopNotExistException(String _message) {
        super(_message);
    }
}
