package uk.ac.shef.com3529.forum;

public class UnknownUserException extends RuntimeException {

    public UnknownUserException(String message) {
        super(message);
    }
}
