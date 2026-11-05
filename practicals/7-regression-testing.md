# Regression Testing

We will use the following scenario.

Let us suppose that a program $p$ under test has ten branches, denoted $b_1 \ldots b_{10}$.

Further, suppose that there is a regression test suite $T$ that contains six test cases, $t_1 \ldots t_6$ such that:

- The execution with $t_1$ leads to the following branches being covered: $b_1, b_3, b_7$.
- The execution with $t_2$ leads to the following branches being covered: $b_2, b_5, b_7$.
- The execution with $t_3$ leads to the following branches being covered: $b_1, b_4, b_8, b_{10}$.
- The execution with $t_4$ leads to the following branches being covered: $b_2, b_6, b_9, b_{10}$.
- The execution with $t_5$ leads to the following branches being covered: $b_2, b_5, b_8, b_9, b_{10}$.
- The execution with $t_6$ leads to the following branches being covered: $b_1, b_4, b_7$.

## Task 1 - Build a Hit Matrix

Build a Hit-Matrix that corresponds to the above regression test coverage. These are the tables that are used in the slide pack, which map code blocks to the tests that covered them.

## Task 2 - Test Suite Minimisation

Apply the Greedy Algorithm to the above scenario to produce a reduced test suite that still achieves 100\% branch coverage. Consider whether there is more than one possible test suite that could be returned by the Greedy Algorithm.


## Task 3 - Prioritising tests

Apply the Greedy Algorithm to the above scenario to produce an execution order for the test cases. If there is more than one possible result, then list them all.

## Task 4 - Analysing the prioritisation

Consider the ordering you produced in the previous task. If the greedy algorithm can produce more than one ordering then choose one to be used in the following. Given an ordering of the six test cases, we can look at the coverage provided by the first $n$ test cases, where $1 \leq n \leq 6$.

1. For what lengths (values of $n$) does the ordering produce the same branch coverage?
2. What is the value of $n$ that leads to the largest difference in coverage, and how large is this difference?

## Task 5 - Test Selection

Consider the method [`StringUtils.duplicateLetters`](../code/lib/src/main/java/uk/ac/shef/com3529/stringutils/StringUtils.java), for which you are given the following test suite.

```java
public class AlternativeStringUtilsTest {

    @Test
    public void shouldReturnEmptySet() {
        Set<Character> resultSet = duplicateLetters("");
        assertFalse(resultSet.contains('s'));
    }

    @Test
    public void shouldNotReturnNonRepeatedChar() {
        Set<Character> resultSet = duplicateLetters("bugs");
        assertFalse(resultSet.contains('s'));
    }

    @Test
    public void shouldReturnRepeatedChar() {
        Set<Character> resultSet = duplicateLetters("software testing");
        assertTrue(resultSet.contains('t'));
    }
}
```

Apply a test selection algorithm to decide which test to execute given the statement on line 25 was accidentally changed from `duplicates.add(si);` to `duplicates.clear();`. Which test(s) should be selected?

## Task 6 - Measuring Prioritisation with APFD

In Task 4 you compared orderings by looking at coverage after each prefix. The APFD metric from the slides gives us a single number that summarises how quickly an ordering achieves its coverage. Here, we treat each *branch* as the thing to be "detected": a branch is detected as soon as a test covers it. (Used this way, the metric is sometimes called APBC, the Average Percentage of Branch Coverage.)

For an ordering of $n$ tests and $m$ branches:

$$APFD = 1 - \frac{TF_1 + TF_2 + \ldots + TF_m}{nm} + \frac{1}{2n}$$

where $TF_i$ is the position (starting from 1) of the first test in the ordering that covers branch $b_i$.

1. Using your hit matrix from Task 1, find $TF_i$ for each of the ten branches for the greedy ordering $t_5 t_3 t_4 t_2 t_1 t_6$, and calculate its APFD.
2. Do the same for the "unprioritised" ordering $t_1 t_2 t_3 t_4 t_5 t_6$. Which ordering covers the branches more quickly?
3. In Task 3 you found that the greedy algorithm can produce several orderings, depending on how ties are broken. Calculate the APFD for at least one other greedy ordering. Does the choice of tie-break matter?
4. Can you find an ordering with a higher APFD than any of the greedy orderings? *Hint: when choosing the next test, count only the branches it covers that are not already covered.* Why does the plain greedy algorithm miss this ordering?
5. What is the highest APFD that any ordering of 6 tests could achieve in theory? Why can it never reach 1?
