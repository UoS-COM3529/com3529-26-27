package uk.ac.shef.com3529.javatime;

import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.temporal.ChronoUnit;
import java.util.Random;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

/**
 * Example solutions for Practical Session 9: Making Metamorphic Tests.
 *
 * Relations that do NOT hold for every input are marked @Disabled so that the
 * build still passes. Remove the @Disabled annotation to watch them fail and
 * see the counterexample in the assertion message.
 */
public class JavaTimeMetamorphicTestSolution {

    private static final int NUM_TESTS = 1_000;

    // Fixed seed so that any failure is reproducible
    private static final long SEED = 3529;

    // Random dates are drawn from this range (inclusive)
    private static final LocalDate EARLIEST = LocalDate.of(1970, 1, 1);
    private static final LocalDate LATEST = LocalDate.of(2100, 12, 31);

    private static final ZoneId LONDON = ZoneId.of("Europe/London");

    // London, New York and Sydney change their clocks (at different times of year).
    // Kolkata and UTC never do.
    private static final ZoneId[] ZONES = {
            LONDON,
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
    // Worked example (given in the worksheet)
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
    // Task 1: relations from the lecture slides
    // ------------------------------------------------------------------

    /**
     * MR: DAYS.between is transitive (going from a to c via b takes as many days as going direct).
     *   Source test case:    (a, c)
     *   Transformation:      pick any third date b and split the journey at b
     *   Follow-up test case: (a, b) and (b, c) -- two inputs
     *   Relation:            DAYS.between(a, c) == DAYS.between(a, b) + DAYS.between(b, c)
     *
     * The dates do not need to be in order: DAYS.between is negative when its
     * second argument is earlier, so the relation holds for any a, b and c.
     */
    @Test
    public void daysBetweenIsTransitive() {
        Random random = new Random(SEED);
        for (int i = 0; i < NUM_TESTS; i++) {
            LocalDate a = randomDate(random);
            LocalDate b = randomDate(random);
            LocalDate c = randomDate(random);

            long sourceOutput = ChronoUnit.DAYS.between(a, c);
            long followUpOutput = ChronoUnit.DAYS.between(a, b) + ChronoUnit.DAYS.between(b, c);

            assertEquals(sourceOutput, followUpOutput,
                    "DAYS.between(a, c) != DAYS.between(a, b) + DAYS.between(b, c)"
                            + " for a = " + a + ", b = " + b + ", c = " + c);
        }
    }

    /**
     * MR: changing the time zone of t does not affect adding hours to it.
     *   Source test case:    (t, n)
     *   Transformation:      view t on the clock of another zone z
     *   Follow-up test case: (t.withZoneSameInstant(z), n)
     *   Relation:            t.withZoneSameInstant(z).plusHours(n) is the same moment as t.plusHours(n)
     */
    @Test
    public void changingTimeZoneDoesNotAffectAddingHours() {
        Random random = new Random(SEED);
        for (int i = 0; i < NUM_TESTS; i++) {
            ZonedDateTime t = randomDateTime(random, randomZone(random));
            ZoneId z = randomZone(random);
            int n = randomInt(random, -1000, 1000);

            ZonedDateTime sourceOutput = t.plusHours(n);
            ZonedDateTime followUpOutput = t.withZoneSameInstant(z).plusHours(n);

            assertTrue(followUpOutput.isEqual(sourceOutput),
                    "t.withZoneSameInstant(z).plusHours(n) is not the same moment as t.plusHours(n)"
                            + " for t = " + t + ", z = " + z + ", n = " + n);
        }
    }

    // ------------------------------------------------------------------
    // Task 2: example relations of our own (many other answers are possible)
    // ------------------------------------------------------------------

    /**
     * MR: shifting both dates by the same amount does not change the number of days between them.
     *   Source test case:    (a, b)
     *   Transformation:      move both dates on by n days
     *   Follow-up test case: (a.plusDays(n), b.plusDays(n))
     *   Relation:            DAYS.between(a.plusDays(n), b.plusDays(n)) == DAYS.between(a, b)
     */
    @Test
    public void shiftingBothDatesDoesNotChangeDaysBetween() {
        Random random = new Random(SEED);
        for (int i = 0; i < NUM_TESTS; i++) {
            LocalDate a = randomDate(random);
            LocalDate b = randomDate(random);
            int n = randomInt(random, -1000, 1000);

            long sourceOutput = ChronoUnit.DAYS.between(a, b);
            long followUpOutput = ChronoUnit.DAYS.between(a.plusDays(n), b.plusDays(n));

            assertEquals(sourceOutput, followUpOutput,
                    "DAYS.between(a.plusDays(n), b.plusDays(n)) != DAYS.between(a, b)"
                            + " for a = " + a + ", b = " + b + ", n = " + n);
        }
    }

    /**
     * MR: swapping the two dates negates the number of days between them.
     *   Source test case:    (a, b)
     *   Transformation:      swap the arguments
     *   Follow-up test case: (b, a)
     *   Relation:            DAYS.between(b, a) == -DAYS.between(a, b)
     */
    @Test
    public void swappingDatesNegatesDaysBetween() {
        Random random = new Random(SEED);
        for (int i = 0; i < NUM_TESTS; i++) {
            LocalDate a = randomDate(random);
            LocalDate b = randomDate(random);

            long sourceOutput = ChronoUnit.DAYS.between(a, b);
            long followUpOutput = ChronoUnit.DAYS.between(b, a);

            assertEquals(-sourceOutput, followUpOutput,
                    "DAYS.between(b, a) != -DAYS.between(a, b) for a = " + a + ", b = " + b);
        }
    }

    /**
     * MR: adding a whole number of weeks does not change the day of the week.
     *   Source test case:    d
     *   Transformation:      move d on by 7k days
     *   Follow-up test case: d.plusDays(7 * k)
     *   Relation:            d.plusDays(7 * k).getDayOfWeek() == d.getDayOfWeek()
     */
    @Test
    public void addingWholeWeeksKeepsTheDayOfWeek() {
        Random random = new Random(SEED);
        for (int i = 0; i < NUM_TESTS; i++) {
            LocalDate d = randomDate(random);
            int k = randomInt(random, -500, 500);

            LocalDate followUp = d.plusDays(7 * k);

            assertEquals(d.getDayOfWeek(), followUp.getDayOfWeek(),
                    "d.plusDays(7k) is on a different day of the week from d for d = " + d + ", k = " + k);
        }
    }

    /**
     * MR: adding more months gives a later date.
     *   Source test case:    (d, m)
     *   Transformation:      add k >= 1 extra months
     *   Follow-up test case: (d, m + k)
     *   Relation:            d.plusMonths(m + k).isAfter(d.plusMonths(m))
     *
     * This holds because the follow-up always lands in a later month. Compare it with
     * "if a is before b then a.plusMonths(n) is before b.plusMonths(n)", which does NOT
     * hold: 30 and 31 January both become 28 February.
     */
    @Test
    public void addingMoreMonthsGivesALaterDate() {
        Random random = new Random(SEED);
        for (int i = 0; i < NUM_TESTS; i++) {
            LocalDate d = randomDate(random);
            int m = randomInt(random, -36, 36);
            int k = randomInt(random, 1, 36);

            LocalDate sourceOutput = d.plusMonths(m);
            LocalDate followUpOutput = d.plusMonths(m + k);

            assertTrue(followUpOutput.isAfter(sourceOutput),
                    "d.plusMonths(m + k) is not after d.plusMonths(m)"
                            + " for d = " + d + ", m = " + m + ", k = " + k);
        }
    }

    /**
     * MR: a month after the 1st of a month is as many days later as there are days in that month.
     *   Source test case:    f, the first day of a month
     *   Transformation:      add one month
     *   Follow-up test case: f.plusMonths(1)
     *   Relation:            DAYS.between(f, f.plusMonths(1)) == f.lengthOfMonth()
     */
    @Test
    public void aMonthAfterTheFirstIsLengthOfMonthDaysLater() {
        Random random = new Random(SEED);
        for (int i = 0; i < NUM_TESTS; i++) {
            LocalDate f = randomDate(random).withDayOfMonth(1);

            LocalDate followUp = f.plusMonths(1);

            assertEquals(f.lengthOfMonth(), ChronoUnit.DAYS.between(f, followUp),
                    "DAYS.between(f, f.plusMonths(1)) != f.lengthOfMonth() for f = " + f);
        }
    }

    /**
     * MR: viewing t in another time zone and then back again gives back t (a round trip).
     *   Source test case:    t, in zone t.getZone()
     *   Transformation:      view t on the clock of zone z
     *   Follow-up test case: t.withZoneSameInstant(z)
     *   Relation:            t.withZoneSameInstant(z).withZoneSameInstant(t.getZone()).equals(t)
     */
    @Test
    public void viewingInAnotherZoneAndBackGivesTheOriginal() {
        Random random = new Random(SEED);
        for (int i = 0; i < NUM_TESTS; i++) {
            ZonedDateTime t = randomDateTime(random, randomZone(random));
            ZoneId z = randomZone(random);

            ZonedDateTime followUp = t.withZoneSameInstant(z);
            ZonedDateTime roundTrip = followUp.withZoneSameInstant(t.getZone());

            assertEquals(t, roundTrip,
                    "Round trip through another zone changed t for t = " + t + ", z = " + z);
        }
    }

    /**
     * MR: adding the same number of hours to two moments does not change which comes first.
     *   Source test case:    (t, u), possibly in different zones
     *   Transformation:      add n hours to both
     *   Follow-up test case: (t.plusHours(n), u.plusHours(n))
     *   Relation:            t.plusHours(n).isBefore(u.plusHours(n)) == t.isBefore(u)
     */
    @Test
    public void addingHoursToBothKeepsTheirOrder() {
        Random random = new Random(SEED);
        for (int i = 0; i < NUM_TESTS; i++) {
            ZonedDateTime t = randomDateTime(random, randomZone(random));
            ZonedDateTime u = randomDateTime(random, randomZone(random));
            int n = randomInt(random, -1000, 1000);

            boolean sourceOutput = t.isBefore(u);
            boolean followUpOutput = t.plusHours(n).isBefore(u.plusHours(n));

            assertEquals(sourceOutput, followUpOutput,
                    "Adding n hours changed the order of t and u"
                            + " for t = " + t + ", u = " + u + ", n = " + n);
        }
    }

    // ------------------------------------------------------------------
    // Task 3: will it hold?
    // ------------------------------------------------------------------

    // --- A: d.plusDays(n).plusDays(-n).equals(d) -- HOLDS ---

    /**
     * MR: adding n days and then taking them away again gives back the original date.
     *   Source test case:    d
     *   Transformation:      add n days, then add -n days
     *   Follow-up test case: d.plusDays(n).plusDays(-n)
     *   Relation:            d.plusDays(n).plusDays(-n).equals(d)
     */
    @Test
    public void addingThenRemovingDaysGivesBackTheDate() {
        Random random = new Random(SEED);
        for (int i = 0; i < NUM_TESTS; i++) {
            LocalDate d = randomDate(random);
            int n = randomInt(random, -1000, 1000);

            LocalDate followUp = d.plusDays(n).plusDays(-n);

            assertEquals(d, followUp,
                    "d.plusDays(n).plusDays(-n) != d for d = " + d + ", n = " + n);
        }
    }

    // --- B: d.plusMonths(n).plusMonths(-n).equals(d) -- DOES NOT HOLD ---

    /**
     * MR (does NOT hold): adding n months and then taking them away again gives back the original date.
     *   Counterexample: d = 2025-03-31, n = 1.
     *   31 March + 1 month = 30 April (there is no 31 April), and 30 April - 1 month = 30 March.
     */
    @Disabled("Does not hold: e.g. 2025-03-31 -> 2025-04-30 -> 2025-03-30")
    @Test
    public void addingThenRemovingMonthsGivesBackTheDate() {
        Random random = new Random(SEED);
        for (int i = 0; i < NUM_TESTS; i++) {
            LocalDate d = randomDate(random);
            int n = randomInt(random, -24, 24);

            LocalDate followUp = d.plusMonths(n).plusMonths(-n);

            assertEquals(d, followUp,
                    "d.plusMonths(n).plusMonths(-n) != d for d = " + d + ", n = " + n);
        }
    }

    // --- C: MONTHS.between(d, d.plusMonths(n)) == n -- DOES NOT HOLD ---

    /**
     * MR (does NOT hold): adding n months to d puts it n months on.
     *   Counterexample: d = 2025-01-31, n = 1.
     *   d.plusMonths(1) = 2025-02-28, but MONTHS.between counts *complete* months, and
     *   31 January to 28 February is not a complete month, so the result is 0.
     */
    @Disabled("Does not hold: e.g. MONTHS.between(2025-01-31, 2025-02-28) == 0")
    @Test
    public void addingNMonthsMovesTheDateNMonthsOn() {
        Random random = new Random(SEED);
        for (int i = 0; i < NUM_TESTS; i++) {
            LocalDate d = randomDate(random);
            int n = randomInt(random, -24, 24);

            long monthsBetween = ChronoUnit.MONTHS.between(d, d.plusMonths(n));

            assertEquals(n, monthsBetween,
                    "MONTHS.between(d, d.plusMonths(n)) != n for d = " + d + ", n = " + n);
        }
    }

    // --- D: t.plusDays(1).isEqual(t.plusHours(24)), for t in Europe/London -- DOES NOT HOLD ---

    /**
     * MR (does NOT hold): in London, a day later is 24 hours later.
     *   Counterexample: t = 2025-03-29T12:00 in London.
     *   The clocks go forward on 30 March, so t.plusDays(1) = 2025-03-30T12:00+01:00,
     *   but t.plusHours(24) = 2025-03-30T13:00+01:00. That day was only 23 hours long.
     */
    @Disabled("Does not hold when the clocks change: e.g. t = 2025-03-29T12:00 Europe/London")
    @Test
    public void aDayLaterIsTwentyFourHoursLater_inLondon() {
        Random random = new Random(SEED);
        for (int i = 0; i < NUM_TESTS; i++) {
            ZonedDateTime t = randomDateTime(random, LONDON);

            ZonedDateTime followUp = t.plusDays(1);

            assertTrue(followUp.isEqual(t.plusHours(24)),
                    "t.plusDays(1) is not 24 hours after t for t = " + t);
        }
    }

    // --- Question 5: the Task 1(b) relation with plusDays instead of plusHours -- DOES NOT HOLD ---

    /**
     * MR (does NOT hold): changing the time zone of t does not affect adding days.
     *   Counterexample: t = 2025-03-29T12:00Z[UTC], z = Europe/London.
     *   t.plusDays(1) = 2025-03-30T12:00Z, but on London's clock the same day is only 23 hours long,
     *   so t.withZoneSameInstant(z).plusDays(1) = 2025-03-30T12:00+01:00, which is 11:00 UTC.
     */
    @Disabled("Does not hold: plusDays keeps the clock time of the zone, so the result depends on the zone's clock changes")
    @Test
    public void changingTimeZoneDoesNotAffectAddingDays() {
        Random random = new Random(SEED);
        for (int i = 0; i < NUM_TESTS; i++) {
            ZonedDateTime t = randomDateTime(random, randomZone(random));
            ZoneId z = randomZone(random);
            int n = randomInt(random, -1000, 1000);

            ZonedDateTime sourceOutput = t.plusDays(n);
            ZonedDateTime followUpOutput = t.withZoneSameInstant(z).plusDays(n);

            assertTrue(followUpOutput.isEqual(sourceOutput),
                    "t.withZoneSameInstant(z).plusDays(n) is not the same moment as t.plusDays(n)"
                            + " for t = " + t + ", z = " + z + ", n = " + n);
        }
    }

    // ------------------------------------------------------------------
    // Extension: composing relations
    // ------------------------------------------------------------------

    /**
     * Composition of two Task 2 relations:
     *   MR_shift: DAYS.between(a.plusDays(n), b.plusDays(n)) == DAYS.between(a, b)
     *   MR_swap:  DAYS.between(b, a) == -DAYS.between(a, b)
     *   MR_swap(MR_shift): DAYS.between(b.plusDays(n), a.plusDays(n)) == -DAYS.between(a, b)
     */
    @Test
    public void swappingAndShiftingDatesNegatesDaysBetween() {
        Random random = new Random(SEED);
        for (int i = 0; i < NUM_TESTS; i++) {
            LocalDate a = randomDate(random);
            LocalDate b = randomDate(random);
            int n = randomInt(random, -1000, 1000);

            long sourceOutput = ChronoUnit.DAYS.between(a, b);
            long followUpOutput = ChronoUnit.DAYS.between(b.plusDays(n), a.plusDays(n));

            assertEquals(-sourceOutput, followUpOutput,
                    "DAYS.between(b.plusDays(n), a.plusDays(n)) != -DAYS.between(a, b)"
                            + " for a = " + a + ", b = " + b + ", n = " + n);
        }
    }
}
