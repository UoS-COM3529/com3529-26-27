package uk.ac.shef.com3529.calculator;

import org.junit.jupiter.api.Test;

import java.util.InputMismatchException;

import static org.mockito.Mockito.*;

public class CalculatorTest {

    @Test
    public void shouldCalculatePlusCorrectly() {
        Reader mockedReader = mock();
        Writer mockedWriter = mock();

        when(mockedReader.readDouble()).thenReturn(20.0, 10.0);
        when(mockedReader.readWord()).thenReturn("+");

        Calculator calculator = new Calculator(mockedReader, mockedWriter);
        calculator.calculate();

        verify(mockedWriter).write("20.0 + 10.0 = 30.0");
    }

    @Test
    public void shouldCalculateMinusCorrectly() {
        Reader mockedReader = mock();
        Writer mockedWriter = mock();

        when(mockedReader.readDouble()).thenReturn(20.0, 10.0);
        when(mockedReader.readWord()).thenReturn("-");

        Calculator calculator = new Calculator(mockedReader, mockedWriter);
        calculator.calculate();

        verify(mockedWriter).write("20.0 - 10.0 = 10.0");
    }

    @Test
    public void shouldCalculateTimesCorrectly() {
        Reader mockedReader = mock();
        Writer mockedWriter = mock();

        when(mockedReader.readDouble()).thenReturn(20.0, 10.0);
        when(mockedReader.readWord()).thenReturn("*");

        Calculator calculator = new Calculator(mockedReader, mockedWriter);
        calculator.calculate();

        verify(mockedWriter).write("20.0 * 10.0 = 200.0");
    }

    @Test
    public void shouldCalculateDivideCorrectly() {
        Reader mockedReader = mock();
        Writer mockedWriter = mock();

        when(mockedReader.readDouble()).thenReturn(20.0, 10.0);
        when(mockedReader.readWord()).thenReturn("/");

        Calculator calculator = new Calculator(mockedReader, mockedWriter);
        calculator.calculate();

        verify(mockedWriter).write("20.0 / 10.0 = 2.0");
    }

    @Test
    public void shouldRejectNonOperator() {
        Reader mockedReader = mock();
        Writer mockedWriter = mock();

        when(mockedReader.readDouble()).thenReturn(20.0, 10.0);
        when(mockedReader.readWord()).thenReturn("flibble", "/");

        Calculator calculator = new Calculator(mockedReader, mockedWriter);
        calculator.calculate();

        verify(mockedWriter).write("Please try again (enter an operator):");
    }

    @Test
    public void shouldRejectNonNumberInput() {
        Reader mockedReader = mock();
        Writer mockedWriter = mock();

        when(mockedReader.readDouble()).thenThrow(new InputMismatchException()).thenReturn(20.0, 10.0);
        when(mockedReader.readWord()).thenReturn("/");

        Calculator calculator = new Calculator(mockedReader, mockedWriter);
        calculator.calculate();

        verify(mockedWriter).write("Please try again (enter a number):");
    }
}
