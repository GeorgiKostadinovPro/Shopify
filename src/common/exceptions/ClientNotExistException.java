package common.exceptions;

public class ClientNotExistException extends RuntimeException {
    public ClientNotExistException(String _message) {
        super(_message);
    }
}
