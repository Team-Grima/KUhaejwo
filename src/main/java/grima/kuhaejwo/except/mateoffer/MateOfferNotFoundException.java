package grima.kuhaejwo.except.mateoffer;

public class MateOfferNotFoundException extends RuntimeException{
    public MateOfferNotFoundException() {
    }

    public MateOfferNotFoundException(String message) {
        super(message);
    }
}
