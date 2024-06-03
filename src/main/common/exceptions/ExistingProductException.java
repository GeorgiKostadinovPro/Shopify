package common.exceptions;

public class ExistingProductException extends  RuntimeException {
    public ExistingProductException(String _message) {
        super(_message);
    }
}
