package tbank.copy2.exception;

public class ExpiredTokenException extends RuntimeException {
    public ExpiredTokenException(String message) {
        super(message);
    }
}
