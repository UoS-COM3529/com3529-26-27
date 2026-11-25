# COM3529 Practical Session 9: Making Metamorphic Tests

> [!IMPORTANT]
> Remember to `git pull` before starting!

In this week's [lecture](../slides/9_Metamorphic_testing.pdf) we saw that metamorphic testing lets us test a program even when we can't say what its correct output should be (the _oracle problem_). Instead of checking one output against an expected value, we run the program twice: once on a **source test case**, and once on a **follow-up test case** made from it by some **transformation**. We then check that the two outputs satisfy a **metamorphic relation** (MR).

In this lab you will write metamorphic tests in JUnit for Java's date and time library, [`java.time`](https://docs.oracle.com/en/java/javase/11/docs/api/java.base/java/time/package-summary.html). The main task is **Task 2**, where you invent relations of your own. As a rough guide, aim to spend about 10 minutes on Task 1, 25 minutes on Task 2 and 15 minutes on Task 3.

## Getting Started

### The lecture example

Open [`SinMetamorphicTest`](../code/lib/src/test/java/uk/ac/shef/com3529/SinMetamorphicTest.java), the example from the lecture, which tests `Math.sin`. Each of its tests:

1. generates a random **source test input** (using a fixed seed, so that any failure can be reproduced),
2. derives the **follow-up test input** from it,
3. runs the code under test on both, and
4. asserts the **relation** between the two outputs, with a message that reports the input if the relation is broken.

Run it from the `code` directory:

```bash
./gradlew test --tests uk.ac.shef.com3529.SinMetamorphicTest
```

### Your test class

You will write your tests in [`JavaTimeMetamorphicTest`](../code/lib/src/test/java/uk/ac/shef/com3529/javatime/JavaTimeMetamorphicTest.java). It already contains:

- helper methods for making random source inputs: `randomDate`, `randomInt`, `randomZone` and `randomDateTime`;
- a worked example, shown below, which follows the same pattern as `SinMetamorphicTest`.

```java
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
```

Run it:

```bash
./gradlew test --tests uk.ac.shef.com3529.javatime.JavaTimeMetamorphicTest
```

Write all your tests in this style. Put the four parts of the relation (source test case, transformation, follow-up test case, relation) in a comment above each test, so that anyone reading it can see straight away what it checks.

> [!TIP]
> When a test fails, the assertion message tells you which input broke the relation. In the terminal Gradle only prints a summary, so to see the message, either add `--info` to the command, open the report at `lib/build/reports/tests/test/index.html`, or run the test from your IDE.

### `java.time` quick reference

This is the same table as in the lecture.

| Making values | |
|-|-|
| `LocalDate.of(year, month, day)` | e.g. `LocalDate.of(2025, 1, 31)` is 31 January 2025 |
| `ZoneId.of(String id)` | e.g. `ZoneId.of("Europe/London")`, which changes its clocks in spring and autumn |
| `d.atTime(hour, min).atZone(z)` → `ZonedDateTime` | date `d` at that time, on zone `z`'s clock |

| `LocalDate d, a, b`: a date with no time zone | |
|-|-|
| `d.plusDays(n)` → `LocalDate` | `n` days later (`n` can be negative) |
| `d.plusMonths(n)` → `LocalDate` | `n` months later, using the month's last day if needed (31 Jan + 1 → 28 Feb) |
| `ChronoUnit.DAYS.between(a, b)` → `long` | days from `a` to `b`; negative if `b` is earlier |
| `ChronoUnit.MONTHS.between(a, b)` → `long` | complete months from `a` to `b` |

| `ZonedDateTime t, u`: a date and time in a zone `z` | |
|-|-|
| `t.plusHours(n)` → `ZonedDateTime` | `n` hours of elapsed time later |
| `t.plusDays(n)` → `ZonedDateTime` | same clock time `n` days later; a time skipped by a clock change moves forward |
| `t.withZoneSameInstant(z)` → `ZonedDateTime` | the same moment, on zone `z`'s clock |
| `t.isBefore(u)` → `boolean` | `t` is an earlier moment than `u` |
| `t.isEqual(u)` → `boolean` | `t` and `u` are the same moment (`equals` also requires the same zone) |

## Task 1: From the Slides to JUnit

Turn these two relations from the lecture's _Example answers_ slide into JUnit tests:

**(a)** `DAYS.between` is transitive:
`DAYS.between(a, c) == DAYS.between(a, b) + DAYS.between(b, c)`.
Here the follow-up test case is made up of two inputs. _Do the three dates need to be in order for this relation to hold?_

**(b)** Changing the time zone of `t` does not affect adding hours to it:
`t.withZoneSameInstant(z).plusHours(n).isEqual(t.plusHours(n))`.
Use `randomZone` to choose both the zone of `t` and the zone `z`.

## Task 2: Invent Your Own Relations

This is the main task. Come up with **at least three** metamorphic relations of your own for `java.time`, and write a JUnit test for each. They should not be relations from the slides or from Task 1. Between them:

- at least one must involve **time zones** (`ZonedDateTime`), and
- at least one must involve **months** (`plusMonths` or `MONTHS.between`).

Before you write any code for a relation, write its four parts (source test case, transformation, follow-up test case, relation) in a comment, as in the worked example. If you can't fill in all four parts, the relation probably isn't clear enough yet.

If you're stuck for ideas, try these questions (from the lecture's _Where do Metamorphic Relations come from?_ slide):

- What could you change about the input that **shouldn't change** the output? For example, what could you do to two dates without changing the number of days between them?
- What change to the input should change the output **by a predictable amount**, or **in a predictable direction**?
- Is there a transformation that **undoes** another one (a _round trip_)?
- What happens if you **swap** the inputs?
- If `x` comes before `y`, should that **still be true** after you apply some operation to both?

Some things to bear in mind:

- A relation doesn't have to be an equality. It could be an ordering (`isBefore`, `<=`), or a statement that a result must lie within some range.
- You can use methods that aren't in the quick reference table. Have a look through the [`LocalDate`](https://docs.oracle.com/en/java/javase/11/docs/api/java.base/java/time/LocalDate.html) and [`ZonedDateTime`](https://docs.oracle.com/en/java/javase/11/docs/api/java.base/java/time/ZonedDateTime.html) documentation for others, e.g. `plusWeeks`, `plusYears`, `getDayOfWeek` and `lengthOfMonth`.

> [!IMPORTANT]
> If one of your tests fails, don't assume you have found a bug in `java.time`! When a metamorphic test fails, either the code under test is wrong **or the relation is wrong**. Take the input from the failure message and work out by hand which it is. Task 3 is all about this.

## Task 3: Will It Hold?

Each of the following relations looks reasonable. For each one:

1. **Predict** whether it holds for _every_ input. Write your prediction down before you run anything.
2. **Test it** by writing a metamorphic test, as before, with random `d`, `t` and `n`.
3. **Explain** any failure, using the input reported by the failing assertion.

| | Relation |
|:-:|-|
| A | `d.plusDays(n).plusDays(-n).equals(d)` |
| B | `d.plusMonths(n).plusMonths(-n).equals(d)` |
| C | `ChronoUnit.MONTHS.between(d, d.plusMonths(n)) == n` |
| D | `t.plusDays(1).isEqual(t.plusHours(24))`, where `t` is in `Europe/London` |

Then think about these questions:

4. Relation D only fails for a small fraction of random inputs. Roughly what fraction, and why? Would you still expect it to fail if `NUM_TESTS` were 50 rather than 1,000? What does this tell you about generating source test cases purely at random?
5. In Task 1(b), changing the time zone did not affect `plusHours`. Would that relation still hold if you used `plusDays` instead? Why or why not? (Test it if you have time.)
6. Could any of your own relations from Task 2 fall into the same traps as B, C or D? Check them against month ends, leap days and clock changes.

## If You Finish Early

- **Break someone else's relations.** Swap test classes with a neighbour. Try to find an input that breaks one of their relations, especially one their random inputs are unlikely to produce.
- **Compose relations.** As shown on the lecture's _Metamorphic Relation Composition_ slide, combine two of your relations into a single relation, and write a test for it.
- **Back to Week 1.** In Week 1 you tested [`Calendar.daysBetweenTwoDates`](../code/lib/src/main/java/uk/ac/shef/com3529/Calendar.java) using `java.time` to give you the expected answers. Suppose you had no such oracle. Which of your `DAYS.between` relations would also hold for `daysBetweenTwoDates`, and which would need to change? (Hint: what does it return when the second date comes before the first?) Then, as with the mutants in Week 8, plant a bug in a copy of `Calendar`, e.g. in `isLeapYear`. Which of your relations detect it?
- **Ask an LLM.** Give the one-shot prompt from the lecture's _A one-shot prompt for java.time_ slide to an LLM. How do its relations compare with yours? Test each of them as you did in Task 3. Did it suggest any that don't hold?

> [!IMPORTANT]
> An explained solution sheet will be made available to you after the lab session.
