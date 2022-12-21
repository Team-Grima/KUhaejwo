package grima.kuhaejwo.except;

public class CAuthenticationEntryPointException extends RuntimeException{
    public CAuthenticationEntryPointException(){
        super();
    }

    public CAuthenticationEntryPointException(String message){
        super(message);
    }

    public CAuthenticationEntryPointException(String message,Throwable cause){
        super(message,cause);
    }
}