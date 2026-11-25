package uk.ac.shef.com3529;

import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.Arrays;
import java.util.Random;

import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 * Example solution for the "Back to Week 1" extension of Practical Session 9.
 *
 * Metamorphic tests for Calendar.daysBetweenTwoDates. Unlike CalendarTestSolution,
 * these tests never ask java.time for the expected answer: they only check relations
 * between the outputs of Calendar.daysBetweenTwoDates itself.
 */
public class CalendarMetamorphicTestSolution {

    private static final int NUM_TESTS = 1_000;

    // Fixed seed so that any failure is reproducible
    private static final long SEED = 3529;

    // Random dates are drawn from this range (inclusive)
    private static final LocalDate EARLIEST = LocalDate.of(1970, 1, 1);
    private static final LocalDate LATEST = LocalDate.of(2100, 12, 31);

    /** A random date between EARLIEST and LATEST (inclusive). */
    private LocalDate randomDate(Random random) {
        long numDays = ChronoUnit.DAYS.between(EARLIEST, LATEST);
        return EARLIEST.plusDays(random.nextInt((int) numDays + 1));
    }

    /** A random whole number between min and max (inclusive). */
    private int randomInt(Random random, int min, int max) {
        return min + random.nextInt(max - min + 1);
    }

    /** Calls the method under test with two LocalDates. */
    private int daysBetween(LocalDate a, LocalDate b) {
        return Calendar.daysBetweenTwoDates(
                a.getYear(), a.getMonthValue(), a.getDayOfMonth(),
                b.getYear(), b.getMonthValue(), b.getDayOfMonth());
    }

    /**
     * MR: swapping the dates does not change the result.
     *   Source test case:    (a, b)
     *   Transformation:      swap the arguments
     *   Follow-up test case: (b, a)
     *   Relation:            daysBetween(b, a) == daysBetween(a, b)
     *
     * Note this is different from ChronoUnit.DAYS.between, where swapping the
     * arguments negates the result: daysBetweenTwoDates always returns a
     * non-negative number of days.
     */
    @Test
    public void swappingDatesDoesNotChangeDaysBetween() {
        Random random = new Random(SEED);
        for (int i = 0; i < NUM_TESTS; i++) {
            LocalDate a = randomDate(random);
            LocalDate b = randomDate(random);

            int sourceOutput = daysBetween(a, b);
            int followUpOutput = daysBetween(b, a);

            assertEquals(sourceOutput, followUpOutput,
                    "daysBetween(b, a) != daysBetween(a, b) for a = " + a + ", b = " + b);
        }
    }

    /**
     * MR: daysBetween is transitive, but (unlike DAYS.between) only when the dates are in order.
     *   Source test case:    (a, c), where a <= b <= c
     *   Transformation:      split the journey at b
     *   Follow-up test case: (a, b) and (b, c)
     *   Relation:            daysBetween(a, c) == daysBetween(a, b) + daysBetween(b, c)
     */
    @Test
    public void daysBetweenIsTransitiveForDatesInOrder() {
        Random random = new Random(SEED);
        for (int i = 0; i < NUM_TESTS; i++) {
            LocalDate[] dates = {randomDate(random), randomDate(random), randomDate(random)};
            Arrays.sort(dates);
            LocalDate a = dates[0];
            LocalDate b = dates[1];
            LocalDate c = dates[2];

            int sourceOutput = daysBetween(a, c);
            int followUpOutput = daysBetween(a, b) + daysBetween(b, c);

            assertEquals(sourceOutput, followUpOutput,
                    "daysBetween(a, c) != daysBetween(a, b) + daysBetween(b, c)"
                            + " for a = " + a + ", b = " + b + ", c = " + c);
        }
    }

    /**
     * MR: moving a date n days on (using java.time to make the follow-up) puts it |n| days away.
     *   Source test case:    d
     *   Transformation:      move d on by n days (n may be negative)
     *   Follow-up test case: d.plusDays(n)
     *   Relation:            daysBetween(d, d.plusDays(n)) == |n|
     */
    @Test
    public void movingADateNDaysOnPutsItNDaysAway() {
        Random random = new Random(SEED);
        for (int i = 0; i < NUM_TESTS; i++) {
            LocalDate d = randomDate(random);
            int n = randomInt(random, -1000, 1000);

            int days = daysBetween(d, d.plusDays(n));

            assertEquals(Math.abs(n), days,
                    "daysBetween(d, d.plusDays(n)) != |n| for d = " + d + ", n = " + n);
        }
    }
}
