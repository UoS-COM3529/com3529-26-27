package uk.ac.shef.com3529.javatime;

import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.temporal.ChronoUnit;
import java.util.Random;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

/**
 * Practical Session 9: Making Metamorphic Tests.
 *
 * Metamorphic tests for the java.time library. Each test follows the same
 * pattern as SinMetamorphicTest:
 *   1. generate a random source test input (using a fixed seed),
 *   2. derive a follow-up test input from it,
 *   3. run the code under test on both, and
 *   4. check that the two outputs satisfy the metamorphic relation.
 */
public class JavaTimeMetamorphicTest {

    private static final int NUM_TESTS = 1_000;

    // Fixed seed so that any failure is reproducible
    private static final long SEED = 3529;

    // Random dates are drawn from this range (inclusive)
    private static final LocalDate EARLIEST = LocalDate.of(1970, 1, 1);
    private static final LocalDate LATEST = LocalDate.of(2100, 12, 31);

    // London, New York and Sydney change their clocks (at different times of year).
    // Kolkata and UTC never do.
    private static final ZoneId[] ZONES = {
            ZoneId.of("Europe/London"),
            ZoneId.of("America/New_York"),
            ZoneId.of("Australia/Sydney"),
            ZoneId.of("Asia/Kolkata"),
            ZoneId.of("UTC")
    };

    // ------------------------------------------------------------------
    // Helpers for generating random source test inputs
    // ------------------------------------------------------------------

    /** A random date between EARLIEST and LATEST (inclusive). */
    private LocalDate randomDate(Random random) {
        long numDays = ChronoUnit.DAYS.between(EARLIEST, LATEST);
        return EARLIEST.plusDays(random.nextInt((int) numDays + 1));
    }

    /** A random whole number between min and max (inclusive). */
    private int randomInt(Random random, int min, int max) {
        return min + random.nextInt(max - min + 1);
    }

    /** One of the ZONES above, chosen at random. */
    private ZoneId randomZone(Random random) {
        return ZONES[random.nextInt(ZONES.length)];
    }

    /** A random date and time (to the minute) on the clock of the given zone. */
    private ZonedDateTime randomDateTime(Random random, ZoneId zone) {
        return randomDate(random)
                .atTime(random.nextInt(24), random.nextInt(60))
                .atZone(zone);
    }

    // ------------------------------------------------------------------
    // Worked example
    // ------------------------------------------------------------------

    /**
     * MR: moving the end date n days later adds n to the number of days between the dates.
     *   Source test case:    (a, b)
     *   Transformation:      move b on by n days
     *   Follow-up test case: (a, b.plusDays(n))
     *   Relation:            DAYS.between(a, b.plusDays(n)) == DAYS.between(a, b) + n
     */
    @Test
    public void movingEndDateLaterAddsToDaysBetween() {
        Random random = new Random(SEED);
        for (int i = 0; i < NUM_TESTS; i++) {
            LocalDate a = randomDate(random);              // source test input
            LocalDate b = randomDate(random);
            int n = randomInt(random, -1000, 1000);
            LocalDate followUpB = b.plusDays(n);           // follow-up test input

            long sourceOutput = ChronoUnit.DAYS.between(a, b);
            long followUpOutput = ChronoUnit.DAYS.between(a, followUpB);

            assertEquals(sourceOutput + n, followUpOutput,
                    "DAYS.between(a, b.plusDays(n)) != DAYS.between(a, b) + n"
                            + " for a = " + a + ", b = " + b + ", n = " + n);
        }
    }

    // ------------------------------------------------------------------
    // Task 1: turn two relations from the lecture slides into tests
    // ------------------------------------------------------------------

    // (a) DAYS.between is transitive:
    //     DAYS.between(a, c) == DAYS.between(a, b) + DAYS.between(b, c)

    // (b) Changing the time zone of t does not affect adding hours:
    //     t.withZoneSameInstant(z).plusHours(n).isEqual(t.plusHours(n))

    // ------------------------------------------------------------------
    // Task 2: your own metamorphic relations
    // ------------------------------------------------------------------

    // ------------------------------------------------------------------
    // Task 3: will it hold?
    // ------------------------------------------------------------------

}
