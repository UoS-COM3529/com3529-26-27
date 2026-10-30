package uk.ac.shef.com3529.calculator;

import java.util.InputMismatchException;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

public class FakeReader implements Reader {

    private final Queue<String> lines;

    public FakeReader(String... lines) {
        this.lines = new LinkedList<>(List.of(lines));
    }

    @Override
    public double readDouble() {
        String line = nextLine();
        try {
            return Double.parseDouble(line.strip());
        } catch (NumberFormatException e) {
            throw new InputMismatchException(line);
        }
    }

    @Override
    public String readWord() {
        return nextLine().strip();
    }

    private String nextLine() {
        if (lines.isEmpty()) {
            throw new IllegalStateException("FakeReader has run out of input");
        }
        return lines.remove();
    }
}