package uk.ac.shef.com3529;

import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;

import static org.junit.jupiter.api.Assertions.*;

public class CalendarTestSolution {

    @Test
    public void testDaysBetweenSameDate() {
        // Arrange
        int expected = 0;
        int year = 2026;
        int month = 2;
        int day = 13;
        // Act
        int actual = Calendar.daysBetweenTwoDates(year, month, day, year, month, day);
        // Assert
        assertEquals(expected, actual);
    }

    @Test
    public void testDaysBetweenDatesWithinSameMonth() {
        int expected = 5;
        int year = 2026;
        int month = 2;
        int day1 = 8;
        int day2 = 13;
        int actual = Calendar.daysBetweenTwoDates(year, month, day1, year, month, day2);
        assertEquals(expected, actual);
    }

    @Test
    public void testDaysBetweenDateAcrossMonths() {
        LocalDate d1 = LocalDate.of(2025, 11, 20);
        LocalDate d2 = LocalDate.of(2026, 2, 11);
        int expected = (int) ChronoUnit.DAYS.between(d1, d2);
        int actual = Calendar.daysBetweenTwoDates(d1.getYear(), d1.getMonthValue(), d1.getDayOfMonth(),
                d2.getYear(), d2.getMonthValue(), d2.getDayOfMonth());
        assertEquals(expected, actual);
    }

    @Test
    public void testDaysBetweenDateAcrossLeapYear() {
        LocalDate d1 = LocalDate.of(2023, 11, 20);
        LocalDate d2 = LocalDate.of(2026, 2, 11);
        int expected = (int) ChronoUnit.DAYS.between(d1, d2);
        int actual = Calendar.daysBetweenTwoDates(d1.getYear(), d1.getMonthValue(), d1.getDayOfMonth(),
                d2.getYear(), d2.getMonthValue(), d2.getDayOfMonth());
        assertEquals(expected, actual);
    }

    @Test
    public void testDaysBetweenSwappedInputs() {
        int year1 = 2026;
        int month1 = 2;
        int day1 = 1;
        int year2 = 2026;
        int month2 = 1;
        int day2 = 31;
        int natural = Calendar.daysBetweenTwoDates(year1, month1, day1, year2, month2, day2);
        int reversed = Calendar.daysBetweenTwoDates(year2, month2, day2, year1, month1, day1);
        assertEquals(natural, reversed);
    }

    @Test
    public void testSanitizationForDayAndMonth() {
        LocalDate d1 = LocalDate.of(2026, 1, 1);
        LocalDate d2 = LocalDate.of(2026, 12, 31);
        int expected = (int) ChronoUnit.DAYS.between(d1, d2);
        int actual = Calendar.daysBetweenTwoDates(2026, 0, 0, 2026, 13, 32);
        assertEquals(expected, actual);
    }
}
