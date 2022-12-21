package grima.kuhaejwo.except.auth;

public class LoginFailedException extends RuntimeException{
    public LoginFailedException() {
        super();
    }

    public LoginFailedException(String msg) {
        super(msg);
    }
}
