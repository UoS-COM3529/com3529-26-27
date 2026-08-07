# COM3529 – Software Testing and Analysis <br /> Spring Semester 2026

Welcome! This repository code examples, practical sheets, and slides to support the Spring Semester module COM3529 "Software Testing and Analysis" at the University of Sheffield.

Jump to:
- [COM3529 – Software Testing and Analysis  Spring Semester 2026](#com3529-software-testing-and-analysis--spring-semester-2026)
  - [1. Teaching Staff](#1-teaching-staff)
  - [2. Schedule and Materials](#2-schedule-and-materials)
    - [Lectures](#lectures)
    - [Practical Sessions (Labs)](#practical-sessions-labs)
  - [3. Assessment](#3-assessment)

## 1. Teaching Staff

  |<img src="misc/images/jose.jpg" width="150"/> | <img src="misc/images/neil.jpg" width="150"/> |
  |:-:|:-:|
  | **[José Rojas](https://jmrojas.github.io)** <br /> Lecturer <br /> Weeks 2&ndash;5 and 8 | **[Neil Walkinshaw](https://neilwalkinshaw.github.io/)** <br /> Lecturer <br /> Weeks 1, 6&ndash;7 and 9&ndash;10

  |<img src="misc/images/zalan.jpeg" width="150"/> | <img src="misc/images/nathan.jpeg" width="150"/> |
  |:-:|:-:|
  |**Zalán Lévai** <br /> Teaching Assistant | **Nathan Shaw** <br /> Teaching Assistant |



## 2. Schedule and Materials

<!-- [Defects, Infections and Failures](practicals/2-defects-infections-failures.md) | -->

| Week | Lecture | Practical Session |
|-:|-|-|
|1 | **[Introduction](slides/1-introduction.pdf)** [🎥](https://echo360.org.uk/lesson/G_612ccf4c-815b-4825-91ca-7f71c408f077_dadfa417-3e3c-4791-979c-fd98689e289f_2026-02-09T14:00:00.000_2026-02-09T14:52:00.000/classroom)<ul><li>Beizer's Maturity Model</li><li>Why finding all bugs is impossible</li><li>How software failures happen (the RIPR model)</li></ul>| [Getting Started](practicals/1-getting-started.md) ([Solutions](practicals/1-getting-started-solution.md)) |
|2 | **[Testing Big and Small – Part 1](slides/2-test-automation-and-scope.pdf)** [🎥](https://echo360.org.uk/lesson/G_8e241018-51c3-40ed-8dc8-1573b8b242b2_dadfa417-3e3c-4791-979c-fd98689e289f_2026-02-16T14:00:00.000_2026-02-16T14:52:00.000/classroom)<ul><li>Test Automation</li><li>Testing Scope (The Test Triangle)</li></ul> | [Defects, Infections and Failures](practicals/2-defects-infections-failures.md) ([Solutions](practicals/2-defects-infections-failures-solutions.md))|
|3 | **Testing Big and Small – Part 2** [🎥](https://echo360.org.uk/lesson/G_7cd852a5-8fde-4e2c-b8f9-dbc53601e8ea_dadfa417-3e3c-4791-979c-fd98689e289f_2026-02-23T14:00:00.000_2026-02-23T14:52:00.000/classroom)<ul><li>[Unit Testing](slides/3-unit-testing.pdf)<ul><li>Avoiding Brittle Tests</li><li>Writing Clear Unit Tests</li></ul> | [Better Unit Testing](practicals/3-better-unit-testing.md) ([Solutions](practicals/3-better-unit-testing-solutions.md)) |
|4 | **Testing Big and Small Part 3** [🎥](https://echo360.org.uk/lesson/G_21f16677-5321-45ad-99aa-21b1347f760e_dadfa417-3e3c-4791-979c-fd98689e289f_2026-03-02T14:00:00.000_2026-03-02T14:52:00.000/classroom)<ul><li>[Test Doubles](slides/4-test-doubles.pdf) [🎥](https://echo360.org.uk/media/6f77ff19-8a59-4a8d-96ff-f808cb0b22cd/public)</li><li>[Larger Tests](slides/5-larger-tests.pdf) [🎥](https://echo360.org.uk/lesson/9f6534d9-7c98-4358-a8a1-21c355480d33/classroom)</li></ul>**[Coverage Criteria, Introduction](slides/6-coverage-criteria.pdf)**<ul><li>Introduction to Coverage Criteria</li></ul> | [Test Doubles](practicals/4-test-doubles.md) ([Solutions](practicals/4-test-doubles-solutions.md)) and [Test Coverage](practicals/4-coverage.md) ([Solutions](practicals/4-coverage-solutions.md))|
|5 | **[Coverage Criteria, Part 2](slides/7-control-flow-and-logic-analysis.pdf)** [🎥](https://echo360.org.uk/lesson/G_737efb39-05d8-4ffc-8dd4-81e81a0e81e2_dadfa417-3e3c-4791-979c-fd98689e289f_2026-03-09T14:00:00.000_2026-03-09T14:52:00.000/classroom)<ul><li>Control Flow Analysis and Testing</li><li>Logic Analysis and Testing</li><li>Conclusions on Coverage</li></ul> | [Control Flow Graphs and Logic Testing](practicals/5-cfgs-and-logic.md) ([Solutions](practicals/5-cfgs-and-logic-solutions.md)) |
|6 | [**Regression Testing**](slides/8-regression-testing.pdf) <ul><li>Test minimisation</li><li>Test prioritisation</li><li>Test selection</li></ul>| [Regression Testing in Practice](practicals/7-regression-testing.md) ([Solutions](practicals/7-regression-testing-solutions.md)) |
|7 | **[Model-Based Testing](slides/9-Model_based_testing.pdf)** | [MBT Exercises](practicals/8-MBT.md) ([Solution](practicals/8-MBT-solutions.md)) |
| Easter Vacation | | |
|8 | **[Mutation Testing](slides/10-Mutation_testing.pdf)** | [Code Defenders](slides/10-Mutation_testing.pdf) |
|9 | **[Search-Based Test Generation](slides/11-search_based_software_testing.pdf)** | [The EvoSuite Tool](practicals/10-evosuite-demo/evosuite-demo.md) |
|10| **[Fuzzing](slides/12-Fuzzing.pdf)** | [Your First Fuzzer](practicals/11-fuzzing.md) ([Solution](practicals/)) |

### Lectures

Lectures will be held on **Monday 10:00 (1h) in Lecture Theatre 1 of the [Sir Henry Stephenson Building](https://sites.google.com/sheffield.ac.uk/pooledroomdirectory/teaching-spaces/sir-henry-stephenson-building/107-stephenson-lt-01)**.

### Practical Sessions (Labs)

Practical Sessions (Labs) will be held on **Friday 11:00 (1h) in Computer Room 5 of [The Diamond](https://www.sheffield.ac.uk/engineering/diamond-engineering/floor-plans)**. These sessions will be a mix of coding and paper/pen exercises that are designed to help you prepare for the exam, based on that week's lecture materials. You'll need to bring your own laptop to the session, and have Java 11 or better installed on it. More information can be found in the **Getting Started** slides from week 1's practical session. You can use the laptop loan facility if you wish.


## 3. Assessment

COM3529 is assessed 100% by an exam, to be scheduled in the [spring exam period](https://sheffield.ac.uk/new-students/standard-exam-dates-venues).

**The practical sessions are designed to prepare you with exercises that will be similar to questions asked in the exam**.

The exam may feature questions **requiring you to write Java code**. This code
needs to be **syntactically correct** and may involve writing JUnit tests that
call methods in libraries studied in the module (e.g., mocking libraries).

When revising for the exam, you are encouraged to consult [past exam papers](https://www.dcs.shef.ac.uk/intranet/teaching/campus/pastpapers.html).
