package uk.ac.shef.com3529.calculator;

import java.util.InputMismatchException;

public class Calculator {

    private final Reader reader;
    private final Writer writer;

    public Calculator(Reader reader, Writer writer) {
        this.reader = reader;
        this.writer = writer;
    }

    private double readNumber() {
        writer.write("Enter a number:");
        double number;
        while (true) {
            try {
                number = reader.readDouble();
                return number;
            } catch (InputMismatchException e) {
                writer.write("Please try again (enter a number):");
            }
        }
    }

    private String readOperator() {
        writer.write("Enter an operator (+, -, *, or /):");
        while (true) {
            String operator = reader.readWord();
            if (operator.equals("+") || operator.equals("-") ||
                    operator.equals("*") || operator.equals("/")) {
                return operator;
            } else {
                writer.write("Please try again (enter an operator):");
            }
        }
    }

    public void calculate() {
        double number1 = readNumber();
        String operator = readOperator();
        double number2 = readNumber();

        double result = number1;
        if (operator.equals("+")) {
            result += number2;
        } else if (operator.equals("-")) {
            result -= number2;
        } else if (operator.equals("*")) {
            result *= number2;
        } else {
            result /= number2;
        }

        writer.write(number1 + " " + operator + " " + number2 + " = " + result);
    }
}
