package uk.ac.shef.com3529.calculator;

import java.util.Scanner;

public class CommandLineCalculator {

    public static void main(String[] args) {
        new Calculator(new Reader() {
            final Scanner scanner = new Scanner(System.in);

            @Override
            public double readDouble() {
                double dbl = scanner.nextDouble();
                scanner.nextLine();
                return dbl;
            }

            @Override
            public String readWord() {
                return scanner.nextLine().strip();
            }
        }, System.out::println).calculate();
    }
}
