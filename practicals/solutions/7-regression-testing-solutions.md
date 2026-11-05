# Regression Testing - Answers

We will use the following scenario.

Let us suppose that a program $p$ under test has ten branches, denoted $b_1 \ldots b_{10}$.

Further, suppose that there is a regression test suite $T$ that contains six test cases, $t_1 \ldots t_6$ such that:

- The execution with $t_1$ leads to the following branches being covered: $b_1, b_3, b_7$.
- The execution with $t_2$ leads to the following branches being covered: $b_2, b_5, b_7$.
- The execution with $t_3$ leads to the following branches being covered: $b_1, b_4, b_8, b_{10}$.
- The execution with $t_4$ leads to the following branches being covered: $b_2, b_6, b_9, b_{10}$.
- The execution with $t_5$ leads to the following branches being covered: $b_2, b_5, b_8, b_9, b_{10}$.
- The execution with $t_6$ leads to the following branches being covered: $b_1, b_4, b_7$.

## Task 1

Build a Hit-Matrix that corresponds to the above regression test coverage. These are the tables that are used in the slide pack, which map code blocks to the tests that covered them.

| Test\Branch | $b_1$ | $b_2$ | $b_3$ | $b_4$ | $b_5$ | $b_6$ | $b_7$ | $b_8$ | $b_9$ | $b_{10}$ |
| :-------- | :---: | :---: | :---: | :---: | :---: | :---: | :---: | :---: | :---: | :----: |
| $t_1$     |   1   |   0   |   1   |   0   |   0   |   0   |   1   |   0   |   0   |    0     |
| $t_2$     |   0   |   1   |   0   |   0   |   1   |   0   |   1   |   0   |   0   |    0     |
| $t_3$     |   1   |   0   |   0   |   1   |   0   |   0   |   0   |   1   |   0   |    1     |
| $t_4$     |   0   |   1   |   0   |   0   |   0   |   1   |   0   |   0   |   1   |    1     |
| $t_5$     |   0   |   1   |   0   |   0   |   1   |   0   |   0   |   1   |   1   |    1     |
| $t_6$     |   1   |   0   |   0   |   1   |   0   |   0   |   1   |   0   |   0   |    0     |

## Task 2 - Test Suite Minimisation

Apply the Greedy Algorithm to the above scenario to produce a reduced test suite that still achieves 100\% branch coverage. Consider whether there is more than one possible test suite that could be returned by the Greedy Algorithm.

### Sample Solution

The greedy algorithm will follow these steps:
1. Start with the empty set $\emptyset$
2. Add the test with highest number of covered branches: $\{t_5\}$ ($t_5$ covers five branches)
3. Next, there are two choices with the highest number of covered branches (four): $t_3$ and $t_4$, so we pick either of them, say $t_3$: $\{t_5, t_3\}$
4. The next obvious candidate is now $t_4$ as it is the only test covering 4 branches: $\{t_5, t_3, t_4\}$
5. We now have three tests that cover three branches each ($t_1$, $t_2$ and $t_6$), so we pick one of them, say $t_1$:  $\{t_5, t_3, t_4, t_1\}$. At this step, we get lucky because $t_1$ covers precisely the only remaining uncovered branches $b_3$ and $b_7$. Therefore the algorithm stops and the reduced test suite is $\{t_5, t_3, t_4, t_1\}$. Had we picked for example $t_2$ or $t_6$ in this step, we would have had to continue for one or two more iterations to achieve full coverage.

The same algorithm can be applied in a more analytical way as follows. First, find out the number of branches covered by each test case: 3, 3, 4, 4, 5, 3 for $t_1..t_6$, respectively. Thus, there are 12 possible orders in which test cases might be considered, which can be represented by the following:

$\{t_5\}\{t_3t_4, t_4t_3\}$
$\{t_1 t_2 t_6, t_1 t_6 t_2, t_2 t_1 t_6, t_2 t_6 t_1, t_6 t_1 t_2, t_6 t_2 t_1\}$

Now consider the possible reduced test suites. For all orders, we include $t_5, t_3, t_4$. These cover all branches except for: $b_3$ and $b_7$. Further, $b_3$ is only covered by test case $t_1$, which also covers $b_7$, and so the algorithm will stop once $t_1$ has been added. We therefore obtain the following possible reduced test suites.

- $\{t_5, t_3, t_4, t_1\}$ (smallest)
- $\{t_5, t_3, t_4, t_2, t_1\}$
- $\{t_5, t_3, t_4, t_6, t_1\}$
- $\{t_5, t_3, t_4, t_2, t_6, t_1\}$


## Task 3 - Prioritising tests

Apply the Greedy Algorithm to the above scenario to produce an execution order for the test cases. If there is more than one possible result, then list them all.

### Sample Solution

The greedy algorithm aims to maximise coverage as quickly as possible, so it would start by picking $t_5$, which covers the highest number of goals:

$\{t_5\}$

Then, since $t_3$ and $t_4$ cover the same number of goals, either of their permutations would come next:

$\{t_5\}\{t_3t_4 | t_4t_3\}$

Finally, since $t_1$, $t_2$, and $t_6$ cover three goals each, either of their permutations could follow. All possible valid orderings can therefore be represented as:

$\{t_5\}\{t_3t_4 | t_4t_3\}
\{t_1 t_2 t_6 | t_1 t_6 t_2 | t_2 t_1 t_6 | t_2 t_6 t_1 | t_6 t_1 t_2 | t_6 t_2 t_1\}$

For example, a concrete valid order of execution would be: $t_5 t_3 t_4 t_2 t_1 t_6$

## Task 4 - Analysing the prioritisation

Consider the ordering you produced in the previous task. If the greedy algorithm can produce more than one ordering then choose one to be used in the following. Given an ordering of the six test cases, we can look at the coverage provided by the first $n$ test cases, where $1 \leq n \leq 6$.

1. For what lengths (values of $n$) does the ordering produce the same branch coverage?
2. What is the value of $n$ that leads to the largest difference in coverage, and how large is this difference?

#### Sample Solution

Consider the following order: $t_5 t_3 t_4 t_2 t_1 t_6$.

The branch coverage provided by prefixes (i.e., incrementally) is given by the following:

- $t_5$: 5 branches
- $t_5 t_3$: 7 branches
- $t_5 t_3 t_4$: 8 branches
- $t_5 t_3 t_4 t_2$: 9 branches
- $t_5 t_3 t_4 t_2 t_1$: 10 branches
- $t_5 t_3 t_4 t_2 t_1 t_6$: 10 branches

At length 5 and length 6 all 10 branches are covered.

The biggest increase in coverage happens at length 2, with the increase of two covered goals.

## Task 5 - Test Selection

Recall method [`SpringUtils.duplicateLetters`](../code/lib/src/main/java/uk/ac/shef/com3529/stringutils/StringUtils.java) from the first week of the semester, for which you are given the following test suite.

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

#### Sample Solution

First, we need to draw the CFG for the original program, and then the one for the modified one:

<img src="7-regression-testing-files/stringutils-cfg.drawio.png" alt="CFGs" width="500"/>

The difference in node 8 is illustrated with a dashed outline.

Next, we build the edge traversal sequences for each of our tests.

- `shouldReturnEmptySet`: (entry, 1-2), (1-2, 3), (3, return)
- `shouldNotReturnNonRepeatedChar`: (entry, 1-2), (1-2, 3), (3, 4), (4, 5), (5, 6), (6, 7), (7, 9), (9, 5), (5, 10), (10, 3), (3, return)
- `shouldReturnRepeatedChar`: (entry, 1-2), (1-2, 3), (3, 4), (4, 5), (5, 6), (6, 7), (7, 8), (7, 9), (8, 9), (9, 5), (5, 10), (10, 3), (3, return)

We then perform a parallel traversal of the two CFGs, where we observe a difference in the target node when looking at node 7: the statement in node 8 has changed. Therefore, we search the edge traversal sequences of our tests to identify a test that exercises edge (7, 8). The only candidate is `shouldReturnRepeatedChar`.

We complete the parallel traversal without spotting any other differences.

The output of the test selection algorithm therefore contains a single test `shouldReturnRepeatedChar`.

It may seem like a trivial algorithm with such a small example. Browse the module's [source code](../code/lib/src/main/java/) directory and try to find a more challenging example where you can apply the same technique.

## Task 6 - Measuring Prioritisation with APFD

In Task 4 you compared orderings by looking at coverage after each prefix. The APFD metric from the slides gives us a single number that summarises how quickly an ordering achieves its coverage. Here, we treat each *branch* as the thing to be "detected": a branch is detected as soon as a test covers it. (Used this way, the metric is sometimes called APBC, the Average Percentage of Branch Coverage.)

For an ordering of $n$ tests and $m$ branches:

$$APFD = 1 - \frac{TF_1 + TF_2 + \ldots + TF_m}{nm} + \frac{1}{2n}$$

where $TF_i$ is the position (starting from 1) of the first test in the ordering that covers branch $b_i$.

1. Using your hit matrix from Task 1, find $TF_i$ for each of the ten branches for the greedy ordering $t_5 t_3 t_4 t_2 t_1 t_6$, and calculate its APFD.
2. Do the same for the "unprioritised" ordering $t_1 t_2 t_3 t_4 t_5 t_6$. Which ordering covers the branches more quickly?

### Sample Solution

Throughout, $n = 6$ and $m = 10$, so $nm = 60$ and $\frac{1}{2n} = \frac{1}{12}$.

#### Part 1 - Greedy ordering $t_5 t_3 t_4 t_2 t_1 t_6$

For each branch, find the first test in the ordering that covers it:

| Branch | $b_1$ | $b_2$ | $b_3$ | $b_4$ | $b_5$ | $b_6$ | $b_7$ | $b_8$ | $b_9$ | $b_{10}$ |
| :-- | :-: | :-: | :-: | :-: | :-: | :-: | :-: | :-: | :-: | :-: |
| First covered by | $t_3$ | $t_5$ | $t_1$ | $t_3$ | $t_5$ | $t_4$ | $t_2$ | $t_5$ | $t_5$ | $t_5$ |
| $TF_i$ | 2 | 1 | 5 | 2 | 1 | 3 | 4 | 1 | 1 | 1 |

$$APFD = 1 - \frac{21}{60} + \frac{1}{12} \approx 0.733$$

#### Part 2 - Unprioritised ordering $t_1 t_2 t_3 t_4 t_5 t_6$

| Branch | $b_1$ | $b_2$ | $b_3$ | $b_4$ | $b_5$ | $b_6$ | $b_7$ | $b_8$ | $b_9$ | $b_{10}$ |
| :-- | :-: | :-: | :-: | :-: | :-: | :-: | :-: | :-: | :-: | :-: |
| First covered by | $t_1$ | $t_2$ | $t_1$ | $t_3$ | $t_2$ | $t_4$ | $t_1$ | $t_3$ | $t_4$ | $t_3$ |
| $TF_i$ | 1 | 2 | 1 | 3 | 2 | 4 | 1 | 3 | 4 | 3 |

$$APFD = 1 - \frac{24}{60} + \frac{1}{12} \approx 0.683$$

The greedy ordering is better: it covers the branches more quickly, as we would hope.
