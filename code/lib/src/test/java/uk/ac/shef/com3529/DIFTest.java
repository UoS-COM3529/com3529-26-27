package uk.ac.shef.com3529;

import org.junit.jupiter.api.*;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.IsEqual.equalTo;
import static org.junit.jupiter.api.Assertions.assertThrows;

// import static uk.ac.shef.com3529.DIF.*; // Uncomment to test against DIF
import static uk.ac.shef.com3529.DIFFixed.*; // Uncomment to test against DIFFixed

public class DIFTest {

    @Test
    public void findLast_failure() {
        int[] arr = {0};
        assertThat(findLast(arr, 0), equalTo(0));
    }

    @Test
    public void findLast_defectNotExecuted() {
        assertThrows(NullPointerException.class, () -> {
            findLast(null, 0);
        });
    }

    @Test
    public void findLast_defectExecuted_noInfection() {
        int[] arr = {0, 1};
        assertThat(findLast(arr, 1), equalTo(1));
    }

    @Test
    public void countPositive_failure() {
        int[] arr = {0};
        assertThat(countPositive(arr), equalTo(0));
    }

    @Test
    public void countPositive_defectNotExecuted() {
        int[] arr = {};
        assertThat(countPositive(arr), equalTo(0));
    }

    @Test
    public void countPositive_defectExecuted_noInfection() {
        int[] arr = {1};
        assertThat(countPositive(arr), equalTo(1));
    }

    @Test
    public void lastZero_failure() {
        int[] arr = {0, 0};
        assertThat(lastZero(arr), equalTo(1));
    }

    @Test
    public void lastZero_defectNotExecuted() {
        assertThrows(NullPointerException.class, () -> {
            lastZero(null);
        });
    }

    @Test
    public void lastZero_defectExecuted_noInfection() {
        int[] arr = {1};
        assertThat(lastZero(arr), equalTo(-1));
    }

    @Test
    public void lastZero_defectExecuted_infectionCaused_noFailure() {
        int[] arr = {0, 1};
        assertThat(lastZero(arr), equalTo(0));
    }

    @Test
    public void oddOrPos_failure() {
        int[] arr = {-1};
        assertThat(oddOrPos(arr), equalTo(1));
    }

    @Test
    public void oddOrPoss_defectNotExecuted() {
        int[] arr = {};
        assertThat(oddOrPos(arr), equalTo(0));
    }

    @Test
    public void oddOrPos_defectExecuted_noInfection() {
        int[] arr = {1};
        assertThat(oddOrPos(arr), equalTo(1));
    }
}
