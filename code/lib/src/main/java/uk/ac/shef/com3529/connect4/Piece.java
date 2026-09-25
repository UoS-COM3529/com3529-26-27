package uk.ac.shef.com3529.connect4;

public enum Piece {
    RED,
    YELLOW;

    public String toString() {
        return this == RED ? "Red" : "Yellow";
    }
}
