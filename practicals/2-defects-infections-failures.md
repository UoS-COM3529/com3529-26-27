# COM3529 Practical Session 2 – Defects, Infections and Failures

This practical gives you the opportunity to reinforce your understanding of some of the key concepts from the [_How do Software Failures Happen?_](../slides/1-introduction.pdf) section of our first lecture: _defects_, _infection_, and _failure_.

We will be testing the `DIF` class from the Java (+Gradle) project
that lives in the code directory of this repository: [`code/lib/src/main/java/uk/ac/shef/com3529/DIF.java`](../code/lib/src/main/java/uk/ac/shef/com3529/DIF.java).

The class contains four [static methods](https://docs.oracle.com/javase/tutorial/java/javaOO/classvars.html): `findLast`, `countPositive`, `lastZero`, and
`oddOrPos`.

Each method has a **defect**. You will need to write JUnit tests that reveal each defect and
establish a **fix**.

Specifically, for each method in `DIF.java`, you will need to complete the
following tasks. The tests you write should be added to a new test class called
`MyDIFTests.java` (NB: In the task descriptions below, `[methodName]` should be replaced by the name of the method you are writing the test for.)

1. (a) What and where is the defect?

   (b) Under what condition(s) do inputs to the method cause it to fail?

   (c) Write **ONE** JUnit test that causes the method to fail. This should be
   named `[methodName]_failure`. (Note that the test should also fail - i.e.,
   the test should have an assertion for the _correct_ behaviour of the method.)

2. (a) Is it possible for inputs to the method to _not_ execute the defect? If
   so, describe the condition(s) necessary for the inputs to the method that
   would cause this to happen.

   (b) If possible (as per your answer to part (a)), write a JUnit test
   that demonstrates the scenario where the defect is _not_ executed. This should be
   named `[methodName]_defectNotExecuted`.

3. (a) Is it possible for an input to execute the defect but _not_ infect the
   program's state? If so, describe the condition(s) necessary for the inputs to
   the method that would cause this to happen.

   (b) If possible (as per your answer to part (a)), write a JUnit test
   that demonstrates this _no-infection_ scenario. Name it
   `[methodName]_defectExecuted_noInfection`.

4. (a) Is it possible for an input to cause an infection but _not_ cause the
   method to fail? (Note, program statements being executed when they shouldn't
   count as an infection.) If so, describe the condition(s) necessary for the
   inputs to the method that would cause this to happen.

   (b) If possible (as per your answer to part (a)), write a JUnit test case
   that demonstrates this _infection-without-failure_ scenario. Name this test `[methodName]_defectExecuted_infectionCaused_noFailure`.

5. Fix the defect and add the fixed method to a class called `MyDIF.java`.
   (Ensure the test you wrote as part of Question 1 passes when run with the
   fixed version of the method.)

> [!IMPORTANT]
> The repository already includes suggested solutions for this task. We urge you to refrain from just looking up these solutions without attempting the tasks by yourself first. An explained solution sheet will be made available to you after the lab session.