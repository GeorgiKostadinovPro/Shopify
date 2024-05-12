package common.exceptions;

public class ProductNotExistException extends RuntimeException {
    public ProductNotExistException(String _message) {
        super(_message);
    }
}
