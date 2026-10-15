# COM3529 Practical Session 3: Better Unit Testing

> [!IMPORTANT]
> Remember to `git pull` before starting!

In the `code` directory, the package [`uk.ac.shef.com3529.forum`](../code/lib/src/main/java/uk/ac/shef/com3529/forum/) represents an online forum, with users and posts. There are three classes to represent each of these aspects – [`Forum`](../code/lib/src/main/java/uk/ac/shef/com3529/forum/Forum.java), [`User`](../code/lib/src/main/java/uk/ac/shef/com3529/forum/User.java), and [`Post`](../code/lib/src/main/java/uk/ac/shef/com3529/forum/Post.java) respectively. _Spend some time familiarising yourself with these classes and how they interact._

To test the `Forum` class, a JUnit test suite has been written in 
[`ForumTest`](../code/lib/src/test/java/uk/ac/shef/com3529/forum/ForumTest.java). However, the tests suffer from a number of problems, including:

* Potential brittleness,
* Lack of clarity,
* Lack of conciseness,
* A focus on testing implementation, as opposed to behaviour.

## Your Tasks

Based on what you have learnt in lectures, take each test in `ForumTest` and:

### Task 1

Identify each of the potential issues with it, stating why it is/could be a problem.

### Task 2

Rewrite the test to remedy the problems you have identified.

### Task 3

Reflect on whether and how the new test addresses the issues you identified in (1) and in whether in fixing the existing issues, you accidentally introduced new ones.
