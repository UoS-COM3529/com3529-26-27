package uk.ac.shef.com3529.calculator;

import java.util.InputMismatchException;
import java.util.Scanner;

public class CommandLineCalculator {

    public static void main(String[] args) {
        new Calculator(new Reader() {
            final Scanner scanner = new Scanner(System.in);

            @Override
            public double readDouble() {
                try {
                    double value = scanner.nextDouble();
                    scanner.nextLine();
                    return value;
                } catch (InputMismatchException e) {
                    scanner.nextLine();
                    throw e;
                }
            }

            @Override
            public String readWord() {
                return scanner.nextLine().strip();
            }
        }, System.out::println).calculate();
    }
}
