package uk.ac.shef.com3529.forum;

public class EmailAddressAlreadyExistsException extends RuntimeException {

    public EmailAddressAlreadyExistsException(String message) {
        super(message);
    }
}
