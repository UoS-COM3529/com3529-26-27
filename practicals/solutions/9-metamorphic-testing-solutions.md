# COM3529 Practical Session 9: Making Metamorphic Tests (Solution)

> [!TIP]
> There are many correct answers to this worksheet, especially to Task 2. Treat these as examples rather than the only possible solution.

All the tests below are in [`JavaTimeMetamorphicTestSolution`](../../code/lib/src/test/java/uk/ac/shef/com3529/javatime/JavaTimeMetamorphicTestSolution.java). The tests for the _Back to Week 1_ extension are in [`CalendarMetamorphicTestSolution`](../../code/lib/src/test/java/uk/ac/shef/com3529/CalendarMetamorphicTestSolution.java).

Tests for relations that do not hold are marked `@Disabled`, so that the build still passes. If you remove the `@Disabled` annotation and run the class with `--info`, you can see the counterexample each one finds:

```bash
./gradlew test --info --tests uk.ac.shef.com3529.javatime.JavaTimeMetamorphicTestSolution
```

## Task 1: From the Slides to JUnit

(a) `DAYS.between` is transitive:

```java
/**
 * MR: DAYS.between is transitive (going from a to c via b takes as many days as going direct).
 *   Source test case:    (a, c)
 *   Transformation:      pick any third date b and split the journey at b
 *   Follow-up test case: (a, b) and (b, c) -- two inputs
 *   Relation:            DAYS.between(a, c) == DAYS.between(a, b) + DAYS.between(b, c)
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
```

The dates do not need to be in order. `DAYS.between` returns a negative number if its second argument is earlier, so the relation holds whatever the order. E.g. for `a` = 10 January, `b` = 1 January and `c` = 5 January we get −5 = −9 + 4. The test above generates the three dates independently, so most of its inputs are not in order anyway.

(b) Changing the time zone of `t` does not affect adding hours:

```java
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
```

> [!NOTE]
> The two outputs have to be compared with `isEqual`, not `equals` (or `assertEquals`). They are the same moment but in different zones, and `equals` also checks that the zones are the same. A test that uses `equals` here will fail, even though `java.time` is behaving correctly.

## Task 2: Invent Your Own Relations

Some example relations are given below, grouped by the prompting question that leads to them. The tests with the same names are in [`JavaTimeMetamorphicTestSolution`](../../code/lib/src/test/java/uk/ac/shef/com3529/javatime/JavaTimeMetamorphicTestSolution.java).

| Prompting question | Source test case | Transformation | Follow-up test case | Relation | Test |
|-|-|-|-|-|-|
| What change shouldn't change the output? | `(a, b)` | move both dates on by `n` days | `(a.plusDays(n), b.plusDays(n))` | `DAYS.between(a.plusDays(n), b.plusDays(n)) == DAYS.between(a, b)` | `shiftingBothDatesDoesNotChangeDaysBetween` |
| What change shouldn't change the output? | `d` | move `d` on by `k` whole weeks | `d.plusDays(7 * k)` | `d.plusDays(7 * k).getDayOfWeek() == d.getDayOfWeek()` | `addingWholeWeeksKeepsTheDayOfWeek` |
| What happens if you swap the inputs? | `(a, b)` | swap the arguments | `(b, a)` | `DAYS.between(b, a) == -DAYS.between(a, b)` | `swappingDatesNegatesDaysBetween` |
| What change has a predictable direction? (months) | `(d, m)` | add `k ≥ 1` more months | `(d, m + k)` | `d.plusMonths(m + k).isAfter(d.plusMonths(m))` | `addingMoreMonthsGivesALaterDate` |
| What change has a predictable amount? (months) | `f`, the 1st of a month | add one month | `f.plusMonths(1)` | `DAYS.between(f, f.plusMonths(1)) == f.lengthOfMonth()` | `aMonthAfterTheFirstIsLengthOfMonthDaysLater` |
| Is there a round trip? (time zones) | `t` | view `t` on zone `z`'s clock | `t.withZoneSameInstant(z)` | `t.withZoneSameInstant(z).withZoneSameInstant(t.getZone()).equals(t)` | `viewingInAnotherZoneAndBackGivesTheOriginal` |
| Does an ordering stay true? (time zones) | `(t, u)`, possibly in different zones | add `n` hours to both | `(t.plusHours(n), u.plusHours(n))` | `t.plusHours(n).isBefore(u.plusHours(n)) == t.isBefore(u)` | `addingHoursToBothKeepsTheirOrder` |

For example, here is the test for the relation involving months:

```java
/**
 * MR: adding more months gives a later date.
 *   Source test case:    (d, m)
 *   Transformation:      add k >= 1 extra months
 *   Follow-up test case: (d, m + k)
 *   Relation:            d.plusMonths(m + k).isAfter(d.plusMonths(m))
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
```

And the round trip involving time zones:

```java
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
```

Here `equals` (via `assertEquals`) is fine, because we expect to get back the original zone as well as the original moment.

When checking your own answers, make sure each relation is actually true (several plausible-sounding relations about months and time zones are not; see Task 3), and that it is worth testing: something like `d.plusDays(0).equals(d)` is true, but is unlikely to find many bugs. Relations don't need to be equalities either. `addingMoreMonthsGivesALaterDate` only checks the direction of the change, which is often all you can say when exact values are hard to predict.

## Task 3: Will It Hold?

| | Relation | Holds? | Counterexample |
|:-:|-|:-:|-|
| A | `d.plusDays(n).plusDays(-n).equals(d)` | Yes | — |
| B | `d.plusMonths(n).plusMonths(-n).equals(d)` | No | `d` = 2025-03-31, `n` = 1 |
| C | `ChronoUnit.MONTHS.between(d, d.plusMonths(n)) == n` | No | `d` = 2025-01-31, `n` = 1 |
| D | `t.plusDays(1).isEqual(t.plusHours(24))`, `t` in London | No | `t` = 2025-03-29T12:00 |

### A

`plusDays` counts real days, and every date has exactly one date `n` days after it and one `n` days before it, so adding `n` days and then adding `-n` days always gets back to where you started. See `addingThenRemovingDaysGivesBackTheDate`.

### B

31 March + 1 month = 30 April (there is no 31 April), and 30 April − 1 month = 30 March.

With the seed used in the solution, the random test reports `d = 2018-05-29, n = 9`: 29 May 2018 + 9 months = 28 February 2019 (2019 is not a leap year), and 28 February 2019 − 9 months = 28 May 2018.

When the day doesn't exist in the target month, `plusMonths` uses the last day of the month instead. Information is lost: 29, 30 and 31 March all become 30 April, so there is no way of getting back to the original date.

### C

`LocalDate.of(2025, 1, 31).plusMonths(1)` is 28 February 2025, but `MONTHS.between(31 January, 28 February)` is 0. `MONTHS.between` counts _complete_ months, and a complete month from 31 January would end on "31 February". 28 February comes before that, so not even one complete month has passed. The random test reports the same input as for B: `MONTHS.between(2018-05-29, 2019-02-28)` is 8, not 9.

Compare this with the relation from the lecture, `DAYS.between(d, d.plusDays(n)) == n`, which does hold. Note also that C only fails when `n` is positive.

### D

In London the clocks go forward on 30 March 2025, so that day only has 23 hours:

- `t.plusDays(1)` is 2025-03-30T12:00+01:00 (the same clock time a day later), but
- `t.plusHours(24)` is 2025-03-30T13:00+01:00.

In October it's the other way round: from `t` = 2025-10-25T12:00+01:00, `plusDays(1)` is 25 hours later. With the solution's seed, the random test reports `t = 2046-03-24T23:22Z[Europe/London]` (the clocks go forward on 25 March 2046).

Clock changes aren't always an hour either. On Lord Howe Island (`Australia/Lord_Howe`) the clocks change by 30 minutes, so a day there can be 23½ or 24½ hours long.

### Question 4

London's clocks change twice a year, and D only fails when the 24 hours after `t` include a clock change. That's about two days' worth of start times a year, so about 2/365 ≈ 0.5% of random inputs. With 1,000 tests we'd expect about 5 failing inputs, so the test reliably fails.

With 50 tests, the chance that none of them is close enough to a clock change is about (1 − 2/365)<sup>50</sup> ≈ 0.76, so roughly three times out of four the test would pass.

So a passing metamorphic test doesn't mean that the relation always holds. Random inputs cover the typical cases well, but tend to miss rare ones like this. Running more tests helps, as does deliberately including some awkward source test cases (the 31st of a month, 29 February, the days the clocks change, the years 1900, 2000 and 2100).

### Question 5

No. `plusHours` adds elapsed time, which is the same in every zone. `plusDays` keeps the same _clock time_ on the zone's own clock, so how much real time it adds depends on whether that zone's clocks change during those days.

For example, take `t` = 2025-03-29T12:00Z[UTC] and `z` = `Europe/London`:

- `t.plusDays(1)` is 2025-03-30T12:00Z[UTC], but
- `t.withZoneSameInstant(z)` is 2025-03-29T12:00Z[Europe/London] (London is on GMT until the 30th), and adding a day gives 2025-03-30T12:00+01:00[Europe/London], which is 11:00 UTC, an hour earlier.

See the disabled test `changingTimeZoneDoesNotAffectAddingDays`. The relation does hold if neither zone ever changes its clocks (e.g. `UTC` and `Asia/Kolkata`).

### Question 6

Some other relations you might have written in Task 2 that fall into the same traps:

| Relation that does not hold | Counterexample |
|-|-|
| `d.plusMonths(1).plusMonths(1).equals(d.plusMonths(2))` | 31 Jan → 28 Feb → 28 Mar, but 31 Jan + 2 months = 31 Mar |
| If `a.isBefore(b)` then `a.plusMonths(n).isBefore(b.plusMonths(n))` | 30 and 31 January both become 28 February |
| `d.plusYears(1).plusYears(-1).equals(d)` | 29 Feb 2024 → 28 Feb 2025 → 28 Feb 2024 |
| `t.plusDays(n)` is `24 * n` hours after `t` | Any `t` shortly before a clock change, in a zone that changes its clocks |
| Comparing `ZonedDateTime`s in different zones with `equals` | Any `t` and any different `z` |

## If You Finish Early

### Break someone else's relations

There's no single answer here. The inputs most likely to break a relation are the ones a random generator rarely produces: the 29th to 31st of a month, 29 February, the days the clocks change, the years 1900 and 2100 (not leap years) and 2000 (a leap year), and zones such as `Australia/Lord_Howe`, whose clocks change by 30 minutes.

### Compose relations

Composing two of the Task 2 relations:

- MR<sub>shift</sub>: `DAYS.between(a.plusDays(n), b.plusDays(n)) == DAYS.between(a, b)`
- MR<sub>swap</sub>: `DAYS.between(b, a) == -DAYS.between(a, b)`
- MR<sub>swap</sub>(MR<sub>shift</sub>): `DAYS.between(b.plusDays(n), a.plusDays(n)) == -DAYS.between(a, b)`

See `swappingAndShiftingDatesNegatesDaysBetween`. Similarly, composing Task 1(b) with the relation that `plusHours` is additive (from the lecture) gives `t.withZoneSameInstant(z).plusHours(p).plusHours(q).isEqual(t.plusHours(p + q))`.

### Back to Week 1

`Calendar.daysBetweenTwoDates` always returns a non-negative number of days (if the second date is earlier, it swaps the dates first). So:

- Swapping the dates doesn't change the result: `daysBetween(b, a) == daysBetween(a, b)`.
- Transitivity only holds if the dates are in order, `a ≤ b ≤ c`. For example, with `a` = 1 January, `b` = 10 January, `c` = 5 January: `daysBetween(a, c)` = 4, but `daysBetween(a, b) + daysBetween(b, c)` = 9 + 5 = 14.
- The worked example's relation becomes `daysBetween(d, d.plusDays(n)) == |n|`.

These are in [`CalendarMetamorphicTestSolution`](../../code/lib/src/test/java/uk/ac/shef/com3529/CalendarMetamorphicTestSolution.java). Both methods compute "the number of days between two dates", but the relations differ because their specifications differ.

Suppose you plant a bug in `isLeapYear` so that it forgets the 400-year rule (`return year % 4 == 0 && year % 100 != 0;`, so 2000 is not a leap year), or the 100-year rule (`return year % 4 == 0;`, so 2100 is a leap year). In both cases:

- The symmetry and transitivity tests still pass. A calendar that is consistently wrong about leap years still gives the same answer in both directions, and still adds up.
- The test for `daysBetween(d, d.plusDays(n)) == |n|` fails, because its follow-up input is made by `java.time`, which gets leap years right. `Calendar` gets the wrong answer whenever the period from `d` to `d.plusDays(n)` includes the end of February 2000 (or 2100). In our runs this was about 1% (for 2000) and 0.4% (for 2100) of random inputs, which is plenty for 1,000 tests.

In other words, a relation that only compares the code under test with itself can't detect a mistake that the code makes consistently.

### Ask an LLM

Answers will vary. The main point is to test each relation the LLM suggests, as you did in Task 3, rather than trusting it. Look out for the same problems as before: month ends, leap days, clock changes, and comparing `ZonedDateTime`s with `equals` rather than `isEqual`.
