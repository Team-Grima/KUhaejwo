package grima.kuhaejwo.except.user;

public class UserNotificationNotFoundException extends RuntimeException {
    public UserNotificationNotFoundException() {
    }

    public UserNotificationNotFoundException(String message) {
        super(message);
    }
}
