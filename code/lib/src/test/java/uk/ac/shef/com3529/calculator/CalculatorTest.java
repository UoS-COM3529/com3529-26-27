package uk.ac.shef.com3529.calculator;

import org.junit.jupiter.api.Test;

import java.util.InputMismatchException;

import static org.mockito.Mockito.*;
import org.mockito.InOrder;

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

    @Test
    public void shouldHandleDivisionByZero() {
        Reader mockedReader = mock();
        Writer mockedWriter = mock();

        when(mockedReader.readDouble()).thenReturn(20.0, 0.0);
        when(mockedReader.readWord()).thenReturn("/");

        Calculator calculator = new Calculator(mockedReader, mockedWriter);
        calculator.calculate();

        // Double division by zero yields Infinity rather than throwing
        verify(mockedWriter).write("20.0 / 0.0 = Infinity");
    }

    @Test
    public void shouldRejectMultipleConsecutiveInvalidInputs() {
        Reader mockedReader = mock();
        Writer mockedWriter = mock();

        // Two invalid numbers, then valid ones
        when(mockedReader.readDouble())
                .thenThrow(new InputMismatchException(), new InputMismatchException())
                .thenReturn(20.0, 10.0);
        
        // Two invalid operators, then a valid one
        when(mockedReader.readWord()).thenReturn("flibble", "wibble", "/");

        Calculator calculator = new Calculator(mockedReader, mockedWriter);
        calculator.calculate();

        verify(mockedWriter, times(2)).write("Please try again (enter a number):");
        verify(mockedWriter, times(2)).write("Please try again (enter an operator):");
        verify(mockedWriter).write("20.0 / 10.0 = 2.0");
    }

    @Test
    public void shouldWriteMessagesInOrderWhenOperatorIsRejected() {
        Reader mockedReader = mock();
        Writer mockedWriter = mock();

        when(mockedReader.readDouble()).thenReturn(20.0, 10.0);
        when(mockedReader.readWord()).thenReturn("flibble", "/");

        Calculator calculator = new Calculator(mockedReader, mockedWriter);
        calculator.calculate();

        InOrder inOrder = inOrder(mockedWriter);
        inOrder.verify(mockedWriter).write("Enter a number:");
        inOrder.verify(mockedWriter).write("Enter an operator (+, -, *, or /):");
        inOrder.verify(mockedWriter).write("Please try again (enter an operator):");
        inOrder.verify(mockedWriter).write("Enter a number:");
        inOrder.verify(mockedWriter).write("20.0 / 10.0 = 2.0");
        inOrder.verifyNoMoreInteractions();
    }


    @Test
    public void shouldCalculateFromScriptedInputUsingFakeReader() {
        Reader fakeReader = new FakeReader("20", "*", "10");
        Writer mockedWriter = mock();

        new Calculator(fakeReader, mockedWriter).calculate();

        verify(mockedWriter).write("20.0 * 10.0 = 200.0");
    }

    @Test
    public void shouldRecoverFromRealisticBadInputUsingFakeReader() {
        Reader fakeReader = new FakeReader("abc", "20", "flibble", "/", "10");
        Writer mockedWriter = mock();

        new Calculator(fakeReader, mockedWriter).calculate();

        InOrder inOrder = inOrder(mockedWriter);
        inOrder.verify(mockedWriter).write("Enter a number:");
        inOrder.verify(mockedWriter).write("Please try again (enter a number):");
        inOrder.verify(mockedWriter).write("Enter an operator (+, -, *, or /):");
        inOrder.verify(mockedWriter).write("Please try again (enter an operator):");
        inOrder.verify(mockedWriter).write("Enter a number:");
        inOrder.verify(mockedWriter).write("20.0 / 10.0 = 2.0");
    }
}
