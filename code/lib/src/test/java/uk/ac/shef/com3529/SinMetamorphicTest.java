package uk.ac.shef.com3529;

import org.junit.jupiter.api.Test;

import java.util.Random;

import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 * Demonstrates metamorphic testing of Math.sin.
 *
 * We have no oracle telling us the "correct" value of sin(x) for an arbitrary x,
 * but we do know relations that must hold between the outputs of related inputs.
 * For each relation we:
 *   1. generate a random source test input x,
 *   2. derive a follow-up test input from x,
 *   3. run Math.sin on both, and
 *   4. check the two outputs satisfy the metamorphic relation.
 */
public class SinMetamorphicTest {

    private static final int NUM_TESTS = 10_000;

    // Range of random source inputs
    private static final double MIN_X = -1000.0;
    private static final double MAX_X = 1000.0;

    // Floating-point arithmetic is not exact, so allow a small tolerance
    private static final double TOLERANCE = 1e-9;

    // Fixed seed so that any failure is reproducible
    private static final long SEED = 3529;

    private double randomInput(Random random) {
        return MIN_X + (MAX_X - MIN_X) * random.nextDouble();
    }

    /** MR1 (periodicity): sin(x + 2π) = sin(x) */
    @Test
    public void sinIsPeriodic() {
        Random random = new Random(SEED);
        for (int i = 0; i < NUM_TESTS; i++) {
            double x = randomInput(random);            // source test input
            double followUpX = x + 2 * Math.PI;        // follow-up test input

            double sourceOutput = Math.sin(x);
            double followUpOutput = Math.sin(followUpX);

            assertEquals(sourceOutput, followUpOutput, TOLERANCE,
                    "sin(x + 2π) != sin(x) for x = " + x);
        }
    }

    /** MR2 (odd function): sin(-x) = -sin(x) */
    @Test
    public void sinIsOdd() {
        Random random = new Random(SEED);
        for (int i = 0; i < NUM_TESTS; i++) {
            double x = randomInput(random);
            double followUpX = -x;

            double sourceOutput = Math.sin(x);
            double followUpOutput = Math.sin(followUpX);

            assertEquals(-sourceOutput, followUpOutput, TOLERANCE,
                    "sin(-x) != -sin(x) for x = " + x);
        }
    }

    /** MR3 (supplementary angles): sin(π - x) = sin(x) */
    @Test
    public void sinOfSupplementaryAngleIsEqual() {
        Random random = new Random(SEED);
        for (int i = 0; i < NUM_TESTS; i++) {
            double x = randomInput(random);
            double followUpX = Math.PI - x;

            double sourceOutput = Math.sin(x);
            double followUpOutput = Math.sin(followUpX);

            assertEquals(sourceOutput, followUpOutput, TOLERANCE,
                    "sin(π - x) != sin(x) for x = " + x);
        }
    }
}
