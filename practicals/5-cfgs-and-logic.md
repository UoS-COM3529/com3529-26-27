# COM3529 Practical Session 4: Control Flow and Logic Analysis

### Control Flow Graphs (CFGs)

Draw the control flow graph (CFG) for the [`Triangle.classify`](../code/lib/src/main/java/uk/ac/shef/com3529/Triangle.java#L11) method. 
   
One thing we did not consider in lectures is what happens, control flow-wise, when an exception is thrown. Include what you think is the control flow when an exception is throw in your graph.

### Branch and Path Coverage

Given the following two examples, analyse whether satisfying **Branch Coverage** and **Path Coverage** is possible or not. For Path Coverage, can you enumerate the possible paths through the code? Can you write JUnit tests to justify your answer?

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
    while (a != b) {
        if (a > b)
            a = a-b;
        else
            b = b-a;
    }
    return a;
}
```

### Logic Analysis and Testing

The code segment that returns when a triangle is isosceles in the [`Triangle.classify`](../code/lib/src/main/java/uk/ac/shef/com3529/Triangle.java) method could be re-written as follows:

```java
if ((side1 + side2 > side3) &&
      (side1 == side2 || side2 == side3) &&
      (side1 != side2 || side2 != side3)) {
    return Type.ISOCELES;
}
```

For the above branch predicate:

1. Enumerate each of the **_conditions_**.
2. Create the complete multiple condition coverage table with 2<sup>n</sup> test requirements.
3. Determine the test requirements for coverage with **Restricted MCDC**. Format this as a table, which shows the truth values of the conditions and the branch predicate in columns of the table (as done in the lecture). Which test requirements are infeasible, if any?
4. The same task as before, but this time with **Correlated MCDC**.
5. Use either your Restricted or Correlated MCDC test requirements to construct a JUnit test suite and run it against the original the [`Triangle`](../code/lib/src/main/java/uk/ac/shef/com3529/Triangle.java) class. Reflect about the relation betwen MCDC and code readability and refactorings.
