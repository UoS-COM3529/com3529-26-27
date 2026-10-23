# COM3529 Practical Session 4: Test Doubles

> [!IMPORTANT]
> Remember to `git pull` before starting!

Take a look at the
[`uk.ac.shef.com3529.calculator`](../code/lib/src/main/java/uk/ac/shef/com3529/calculator)
package, paying attention to the
[`Calculator`](../code/lib/src/main/java/uk/ac/shef/com3529/calculator/Calculator.java)
class.

When constructed with a
[`Reader`](../code/lib/src/main/java/uk/ac/shef/com3529/calculator/Reader.java)
and a
[`Writer`](../code/lib/src/main/java/uk/ac/shef/com3529/calculator/Writer.java),
the `calculate` method reads in two numbers and an operator – one of `+` (plus),
`-` (minus), `*` (times), or `/` (divide), and writes the result. The input source and output destination depend on how the `Reader` and `Writer` interfaces are implemented. 

The
[`CommandLineCalculator`](../code/lib/src/main/java/uk/ac/shef/com3529/calculator/CommandLineCalculator.java)
passes an anonymous `Reader` that reads from the keyboard and a `Writer` (`System.out::println`) that prints to the console. With a terminal open in the `code` directory of the
repository, you can build and run this example:

```bash
./gradlew build
java -cp ./lib/build/classes/java/main uk.ac.shef.com3529.calculator.CommandLineCalculator
```

An example run looks as follows:

```
Enter a number:
500
Enter an operator (+, -, *, or /):
/
Enter a number:
5
500.0 / 5.0 = 100.0
```

## Your Goal

**The aim of this exercise is to write a comprehensive JUnit test suite for
[`Calculator`](../code/lib/src/main/java/uk/ac/shef/com3529/calculator/Calculator.java).**

For this, you'll need to write test doubles for the `Reader` and `Writer`
interfaces using [Mockito](https://site.mockito.org/) first and then using a manually-written _fake_.

### Using Mockito

This section gives you a quick walkthrough of the essential Mockito components, complementing what is contained in this week's lecture slides. Feel free to explore more online resources to learn more – the official [mockito.org](https://site.mockito.org/) website would be the easiest place to start.


To help you write tests for the `Calculator` class, you can use Mockito to create `Reader` and `Writer` test doubles. The following snippets show some useful operations; combine them with calls to `Calculator.calculate()` to test its behaviour:

#### Import statements

These are the Mockito import statements you will need for this lab:

```java
import static org.mockito.Mockito.*;
import org.mockito.InOrder;
```

#### Create mocks

```java
Reader reader = mock(Reader.class);
Writer writer = mock(Writer.class);
```

#### Provide inputs on successive calls

```java
when(reader.readDouble()).thenReturn(20.0, 10.0);
when(reader.readWord()).thenReturn("/");
```

That is, the `readDouble()` method will return 20.0 the first time it is called, and then 10.0 the second time. You can achieve the same
effect by chaining `thenReturn` calls, as follows:

```java
when(reader.readDouble()).thenReturn(20.0).thenReturn(10.0);
```

#### Simulate invalid numeric input

```java
when(reader.readDouble())
        .thenThrow(new InputMismatchException())
        .thenReturn(20.0, 10.0);
```

A `thenThrow` call can be chained with `thenReturn`.

#### Check that a message was written

```java
verify(writer).write("Please try again (enter a number):");
```

#### Check that output messages occurred in order

You can use the `InOrder` Mockito class to verify that a sequence of method calls take place in a certain order, for example:

```java
InOrder inOrder = inOrder(writer);
inOrder.verify(writer).write("Enter a number:");
inOrder.verify(writer).write("Enter an operator (+, -, *, or /):");
// Further verifications...
inOrder.verifyNoMoreInteractions(); // if no more interactions are expected
```

What other messages should you verify, and in what order? You can use these building blocks to write tests for valid input and for the calculator’s retry behaviour.

### Manually-written `FakeReader`

As discussed in the lecture, Mockito can create mocks and spies, but a fake is a lightweight implementation with working behaviour, so you’ll need to write `FakeReader` yourself.

Your task here is to write a `FakeReader` class within the `src/test/java/uk/ac/shef/com3529/calculator` directory. 

> [!IMPORTANT]
> Notice that the fake class is being placed in the `src/test` directory instead of in `src/main`. This is recommended practice: fakes support testing and should not live alongside production code.

You will implement `FakeReader` as a test double that implements the `Reader` interface using a pre-scripted queue of input lines instead of reading from `System.in` as the implementation given in `CommandLineCalculator` does.
- The constructor of the class takes the lines a user would type and creates a queue from them. Then, each call to `readDouble()` or `readWord()` consumes the next input from the queue.
- `readDouble()`: parses the input as a `double`. Non-numeric text should throw `InputMismatchException` (mimicking `Scanner.nextDouble()` in `CommandLineCalculator`).
- `readWord()`: simply returns the next input with surrounding whitespace stripped.
- `IllegalStateException` should be thrown if the `Calculator` tries to read more inputs than were supplied to its constructor.

As you can see, this will be fake rather than a mock because it has real, working behaviour, letting tests describe a scenario as plain user input, such as `new FakeReader("abc", "20", "flibble", "/", "10")`.

Next, write the following two additional tests in `CalculatorTest` using `FakeReader`:

- `shouldCalculateFromScriptedInputUsingFakeReader`: simply use the fake reader and check the happy-path result of `Calculator.calculate()`.
- `shouldRecoverFromRealisticBadInputUsingFakeReader`: provide problematic inputs through the fake reader to test recovery (retry behaviour).

## Have you tested the `Calculator` class enough?

To help you assess whether you have written enough tests for the `Calculator` class, in this part of the lab, we take a sneak peek into next week's topic: _code coverage_.

Without going into much detail just yet (we will in next week's lecture), we will use a very popular code coverage tool for Java called [JaCoCo](https://www.jacoco.org/jacoco/trunk/index.html).

You can use JaCoCo to measure the code coverage in `Calculator` when running `CalculatorTest` by issuing the following commands (on Windows you may not need the `./` prefix). 

> [!Tip]
> Make sure that you are in the `code` directory in your terminal shell before running these commands!


```shell
./gradlew clean
./gradlew compileJava
./gradlew test --tests uk.ac.shef.com3529.calculator.CalculatorTest
./gradlew jacocoTestReport
```

If everything goes well, the JaCoCo test report will have been generated within your Java project as `lib/build/reports/jacoco/test/html/index.html`. From here, you can load this `index.html` file in your favourite web browser. In the report, you should be able to navigate through all the classes in the `uk.ac.shef.com3529.calculator` package. For instance, if you navigate to the `Calculator` class, you should see a list of all the methods it contains, with an indication of how much coverage was achieved for each of them. If you click on any of the methods, e.g., `calculate()`, you should be able to see lines of code highlighted in green (fully covered), yellow (partially covered), and red (not covered at all).

### What can you do with this JaCoCo test report?

Use the test report as a guide to identify untested behaviour in `Calculator`, then write additional tests for those behaviours using mocks or your `FakeReader`.

> [!TIP]
> Similar coverage information as produced by JaCoCo can be gathered directly in your IDE, e.g., through the built-in "Run With Coverage" features provided by [VSCode](https://code.visualstudio.com/docs/debugtest/testing#_test-coverage) and [IntelliJ IDEA](https://www.jetbrains.com/help/idea/code-coverage.html#run_with_coverage).