package uk.ac.shef.com3529.bankaccount.connect4;

public class IllegalMoveException extends RuntimeException {

    public IllegalMoveException(String msg) {
        super(msg);
    }
}
