# COM3529 Practical Session 5: Control Flow and Logic Analysis

## Control Flow Graphs (CFGs)

Draw the control flow graph (CFG) for the two methods in the [`Triangle`](../code/lib/src/main/java/uk/ac/shef/com3529/Triangle.java) class.

The two methods implemented in this class ([`classify`](../code/lib/src/main/java/uk/ac/shef/com3529/Triangle.java#L13) and [`generateRandomTriangle`](../code/lib/src/main/java/uk/ac/shef/com3529/Triangle.java#L50)) present two challenges we did not cover explicitly in this week's lecture:
   
1. What happens –control flow-wise– when an exception is thrown. Include what you think is the control flow when an exception is thrown in your graph. You only need to consider explicit `throw` statements.
2. How control flows through a `switch` statement. Knowing that a switch statement is similar to an `if/else if` chain where each condition checks that same expression against a different value and the default branch is like the final else, how would you depict this in your control flow graph?

## Branch and Path Coverage

Given the following two examples, analyse whether satisfying **Branch Coverage** and **Path Coverage** is possible or not. For Path Coverage, enumerate all the possible paths through the code? Is Bounded Path Coverage appropriate for either of the methods?

```java
int misc(int x, int y, int z) {
    int r = 0;
    if (x > 0) {
        r = 10;
    }
    if (y > 0) {
        r = 40;
    }
    if (z > 0) {
        r = 50;
    }
    return r;
}
```

```java
int gcd(int a, int b) { 
    if (a <= 0 || b <= 0) {
        throw new IllegalArgumentException("a and b must be positive");
    }
    while (a != b) {
        if (a > b)
            a = a-b;
        else
            b = b-a;
    }
    return a;
}
```

## Logic Analysis and Testing

The following predicate shows an alternative way to identify an isosceles triangle to the one implemented in the [`Triangle.classify`](../code/lib/src/main/java/uk/ac/shef/com3529/Triangle.java#L13) method. Assume `side1`, `side2`, and `side3` are positive integers sorted so that `side1 <= side2 <= side3`.

```java
if ((side1 + side2 > side3) &&
            ((side1 == side2 && side2 != side3) ||
                (side1 != side2 && side2 == side3))) {
    return Type.ISOSCELES;
}
...
```

For this branch predicate:

1. Enumerate each of the **_atomic conditions_** in the predicate (as discussed in the lecture).
2. Create a test suite that satisfies Condition Coverage.
3. Create the complete Multiple Condition Coverage truth table with 2<sup>n</sup> test requirements and identify any unfeasible cases.
4. Starting from the full Multiple Condition Coverage truth table from the previous task, determine whether feasible test cases can satisfy **MCDC**; if so, derive a test suite, and if not, explain why.
5. Use your MCDC test requirements from the previous task to develop a set of JUnit tests for the isosceles triangle type and run it against the original [`Triangle.classify`](../code/lib/src/main/java/uk/ac/shef/com3529/Triangle.java#L13) method. Reflect on the relationship between MCDC, line/branch coverage, and test code readability and maintainability.
    > [!Tip]
    > Notice that in this exercise you are deriving test cases from a refactored version of the existing code.

