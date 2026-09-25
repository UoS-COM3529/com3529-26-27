package uk.ac.shef.com3529.bankaccount.connect4;

public class GameOverException extends RuntimeException {

    public GameOverException(Piece piece) {
        super("The game is over. " +
                (piece == null ? "It was a draw (board full)." : "The winner was " + piece));
    }
}
