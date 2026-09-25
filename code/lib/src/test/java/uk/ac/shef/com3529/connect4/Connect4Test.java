package uk.ac.shef.com3529.connect4;

import org.junit.jupiter.api.Test;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.IsEqual.equalTo;
import static org.hamcrest.core.IsNull.nullValue;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class Connect4Test {

        @Test
        public void shouldInitializeCorrectly() {
            // Given a new Connect 4 Board
            Connect4 c4 = new Connect4(2, 2);

            // Then it's RED's turn
            assertThat(c4.whoseTurn(), equalTo(Piece.RED));

            // Then the board has no piece in every position
            assertThat(c4.getPieceAt(0, 0), nullValue());
            assertThat(c4.getPieceAt(0, 1), nullValue());
            assertThat(c4.getPieceAt(1, 0), nullValue());
            assertThat(c4.getPieceAt(1, 1), nullValue());

            // Then the game is not over
            assertThat(c4.isGameOver(), equalTo(false));

            // Then there is no winner
            assertThat(c4.winner, equalTo(null));
        }

    @Test
    public void shouldChangePieceAfterTurn() {
        // Given a Connect 4 Board, with RED starting first
        Connect4 c4 = new Connect4(7, 6);

        // When RED makes a move
        c4.makeMove(0);

        // Then it's YELLOW's turn next
        assertThat(c4.whoseTurn(), equalTo(Piece.YELLOW));
    }

    @Test
    public void shouldNotAllowColumnToOverflow() {
        // Given a Connect 4 board with 6 rows
        Connect4 c4 = new Connect4(7, 6);

        // When six moves are made in column 0, filling it up
        c4.makeMove(0);
        c4.makeMove(0);
        c4.makeMove(0);
        c4.makeMove(0);
        c4.makeMove(0);
        c4.makeMove(0);

        // Then the seventh move causes a ColumnFullException to be thrown
        assertThrows(ColumnFullException.class, () -> {
            c4.makeMove(0);
        });
    }

    @Test
    public void shouldNotAllowInvalidColumns() {
        // Given a Connect 4 board with 7 columns
        Connect4 c4 = new Connect4(7, 6);

        // Then a move in col -1 causes an IllegalMoveException
        assertThrows(IllegalMoveException.class, () -> {
            c4.makeMove(-1);
        });

        // Then a move in col 7 causes an IllegalMoveException
        assertThrows(IllegalMoveException.class, () -> {
            c4.makeMove(7);
        });
    }

    @Test
    public void shouldEndGameWithWinnerWhenFourInARowHorizontally() {
        // Given a Connect4 board
        Connect4 c4 = new Connect4(7, 6);

        // WHen RED makes a line of four in row 0, with YELLOW playing on top in row 1
        c4.makeMove(0); // RED
        c4.makeMove(0); // YELLOW
        c4.makeMove(1); // RED
        c4.makeMove(1); // YELLOW
        c4.makeMove(2); // RED
        c4.makeMove(2); // YELLOW
        c4.makeMove(3); // RED

        // Then the game is over
        assertThat(c4.isGameOver(), equalTo(true));

        // Then RED is the winner
        assertThat(c4.winner(), equalTo(Piece.RED));
    }

    @Test
    public void shouldEndGameWithWinnerWhenFourInARowVertically() {
        // Given a Connect4 board
        Connect4 c4 = new Connect4(7, 6);

        // When RED makes a line of four in column 0, with YELLOW filling up column 2
        c4.makeMove(0); // RED
        c4.makeMove(1); // YELLOW
        c4.makeMove(0); // RED
        c4.makeMove(1); // YELLOW
        c4.makeMove(0); // RED
        c4.makeMove(1); // YELLOW
        c4.makeMove(0); // RED

        // Then the game is over
        assertThat(c4.isGameOver(), equalTo(true));

        // Then RED has won
        assertThat(c4.winner(), equalTo(Piece.RED));
    }

    @Test
    public void shouldPlaceCounterAboveLast() {
        // Given a Connect4 board
        Connect4 c4 = new Connect4(7, 6);

        // When the first piece is dropped in column 0
        c4.makeMove(0); // RED

        // Then it appears in row 0
        assertThat(c4.getPieceAt(0, 0), equalTo(Piece.RED));

        // When the second piece is dropped in column 1
        c4.makeMove(0); // YELLOW

        // Then it appears in row 1
        assertThat(c4.getPieceAt(0, 1), equalTo(Piece.YELLOW));

        // When the third piece is dropped in column 0
        c4.makeMove(0); // RED

        // Then it appears in row 3
        assertThat(c4.getPieceAt(0, 2), equalTo(Piece.RED));

        // When the fourth piece is dropped in column 0
        c4.makeMove(0);

        // Then it appears in row 4
        assertThat(c4.getPieceAt(0, 3), equalTo(Piece.YELLOW));
    }

}
