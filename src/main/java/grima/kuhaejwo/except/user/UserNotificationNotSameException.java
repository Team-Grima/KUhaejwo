package grima.kuhaejwo.except.user;

public class UserNotificationNotSameException extends RuntimeException {
    public UserNotificationNotSameException() {
    }

    public UserNotificationNotSameException(String message) {
        super(message);
    }
}
