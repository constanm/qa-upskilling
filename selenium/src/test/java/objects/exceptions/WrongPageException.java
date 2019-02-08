package objects.exceptions;

public class WrongPageException extends RuntimeException {

    public WrongPageException(String message) {

        super(message);
    }
}