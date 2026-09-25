package uk.ac.shef.com3529.connect4;

public class Connect4 {

    private static final int WINNING_SEQUENCE = 4;

    Piece[][] board;
    int cols, rows;
    Piece turn;
    boolean gameOver;
    Piece winner;

    public Connect4(int cols, int rows) {
        this.cols = cols;
        this.rows = rows;
        board = new Piece[cols][rows];
        turn = Piece.RED;
        gameOver = false;
        winner = null;
    }

    public boolean isGameOver() {
        return gameOver;
    }

    public Piece winner() {
        return winner;
    }

    public Piece whoseTurn() {
        return turn;
    }

    public Piece getPieceAt(int col, int row) {
        return board[col][row];
    }

    public void makeMove(int col) {
        if (isGameOver()) {
            throw new GameOverException(winner);
        }

        if (!isValidCol(col)) {
            throw new IllegalMoveException("Column " + col + " is not a valid column number");
        }

        int row = firstAvailableRow(col);

        if (!isValidRow(row)) {
            throw new ColumnFullException("Column " + col + " is already full");
        }

        board[col][row] = turn;

        if (isBoardFull() || isGameWon()) {
            gameOver = true;
        } else {
            turn = (turn == Piece.RED) ? Piece.YELLOW : Piece.RED;
        }
    }

    int firstAvailableRow(int col) {
        for (int row=rows-1; row >= 0; row--) {
            if (board[col][row] != null) {
                System.out.println(row+1);
                return row + 1;
            }
        }
        return 0;
    }

    boolean isValidCol(int col) {
        return col >= 0 && col < cols;
    }

    boolean isValidRow(int row) {
        return row >=0 && row < rows;
    }

    boolean isBoardFull() {
        for (int col=0; col < cols; col++) {
            for (int row=0; row < rows; row++) {
                if (board[col][row] == null) {
                    return false;
                }
            }
        }
        return true;
    }

    boolean isGameWon() {
        // horizontal check
        for (int col=0; col < cols; col++) {
            if (isGameWon(col, 0, 1, 0)) {
                return true;
            }
        }

        // vertical check
        for (int row=0; row < rows; row++) {
            if (isGameWon(0, row, 0, 1)) {
                return true;
            }
        }

        // diagonal / check
        for (int col=1; col < cols-WINNING_SEQUENCE; col++) {
            if (isGameWon(col, 0, 1, 1)) {
                return true;
            }
        }
        for (int row=0; row <= rows-WINNING_SEQUENCE; row++) {
            if (isGameWon(0, row, 1, 1)) {
                return true;
            }
        }

        // diagonal \ check
        for (int col=WINNING_SEQUENCE-1; col < rows; col++) {
            if (isGameWon(col, 0, -1, 1)) {
                return true;
            }
        }
        for (int row=1; row <= rows-WINNING_SEQUENCE; row++) {
            if (isGameWon(cols-1, row, -1, 1)) {
                return true;
            }
        }

        return false;
    }

    boolean isGameWon(int col, int row, int dCol, int dRow) {

        if (isValidCol(col) && isValidRow(row)) {
            Piece piece = board[col][row];
            int inARow = board[col][row] == null ? 0 : 1;

            col += dCol;
            row += dRow;

            while (isValidCol(col) && isValidRow(row)) {
                if (piece != null && piece == board[col][row]) {
                    inARow ++;
                    if (inARow == WINNING_SEQUENCE) {
                        winner = piece;
                        return true;
                    }
                } else {
                    piece = board[col][row];
                    inARow = board[col][row] == null ? 0 : 1;
                }

                col += dCol;
                row += dRow;
            }
        }

        return false;
    }
}
