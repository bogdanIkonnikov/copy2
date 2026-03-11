package tbank.copy2.exception;

public class IncorrectEmailOrPassException extends RuntimeException {
    public IncorrectEmailOrPassException(String message) {
        super(message);
    }
}
