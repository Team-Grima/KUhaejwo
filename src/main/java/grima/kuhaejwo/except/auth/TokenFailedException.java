package grima.kuhaejwo.except.auth;

public class TokenFailedException extends RuntimeException{
    public TokenFailedException() {
        super();
    }

    public TokenFailedException(String message) {
        super(message);
    }
}
