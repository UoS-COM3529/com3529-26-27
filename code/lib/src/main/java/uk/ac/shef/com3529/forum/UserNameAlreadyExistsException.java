package uk.ac.shef.com3529.forum;

public class UserNameAlreadyExistsException extends RuntimeException {

    public UserNameAlreadyExistsException(String message) {
        super(message);
    }
}
