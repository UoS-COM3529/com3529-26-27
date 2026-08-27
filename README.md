# COM3529 – Software Testing and Analysis <br /> Autumn Semester 2026-2027

Welcome! This repository contains code examples, practical sheets, and slides to support the Autumn Semester module **COM3529 - Software Testing and Analysis** at the University of Sheffield.

## Teaching Staff

<img src="misc/images/jose.jpg" width="150" style="border-radius: 10%; margin-right: 15px; float: left;">**[José Rojas](https://jmrojas.github.io)**<br> Lecturer  
Weeks 2–5, 8  
<br clear="left">

<img src="misc/images/neil.jpg" width="150" style="border-radius: 10%; margin-right: 15px; float: left;">**[Neil Walkinshaw](https://neilwalkinshaw.github.io/)** <br> Lecturer  
Weeks 1, 6–7, 9–10  
<br clear="left">

<img src="https://placehold.co/150x150?text=TA" width="150" style="border-radius: 10%; margin-right: 15px; float: left;">**TBC** <br> Teaching Assistant  
Practical Sessions 
<br clear="left">

## Schedule and Materials

**Lectures:** Monday 10:00 (1h) in Lecture Theatre 1, [Sir Henry Stephenson Building](https://sites.google.com/sheffield.ac.uk/pooledroomdirectory/teaching-spaces/sir-henry-stephenson-building/107-stephenson-lt-01)<br>
**Practical Sessions:** Friday 11:00 (1h) in Computer Room 5, [The Diamond](https://www.sheffield.ac.uk/engineering/diamond-engineering/floor-plans)


| Week | Lecture | Practical Session |
|-:|-|-|
|1 | **[Introduction](slides/README.md)** [🎥](https://echo360.org.uk/section/ac117105-4843-42ab-afc3-cc3522e628f7/public)<ul><li>Beizer's Maturity Model</li><li>Why finding all bugs is impossible</li><li>How software failures happen (the RIPR model)</li></ul>| [Getting Started](practicals/1-getting-started.md) ([Solutions](practicals/solutions/README.md)) |
|2 | **[Testing Big and Small – Part 1](slides/README.md)** [🎥](https://echo360.org.uk/section/ac117105-4843-42ab-afc3-cc3522e628f7/public)<ul><li>Test Automation</li><li>Testing Scope (The Test Triangle)</li></ul> | [Defects, Infections and Failures](practicals/README.md) ([Solutions](practicals/solutions/README.md))|
|3 | **Testing Big and Small – Part 2** [🎥](https://echo360.org.uk/section/ac117105-4843-42ab-afc3-cc3522e628f7/public)<ul><li>[Unit Testing](slides/README.md)<ul><li>Avoiding Brittle Tests</li><li>Writing Clear Unit Tests</li></ul> | [Better Unit Testing](practicals/README.md) ([Solutions](practicals/solutions/README.md)) |
|4 | **Testing Big and Small Part 3** [🎥](https://echo360.org.uk/section/ac117105-4843-42ab-afc3-cc3522e628f7/public)<ul><li>[Test Doubles](slides/README.md) [🎥](https://echo360.org.uk/section/ac117105-4843-42ab-afc3-cc3522e628f7/public)</li><li>[Larger Tests](slides/README.md) [🎥](https://echo360.org.uk/section/ac117105-4843-42ab-afc3-cc3522e628f7/public)</li></ul>**[Coverage Criteria, Introduction](slides/README.md)**<ul><li>Introduction to Coverage Criteria</li></ul> | [Test Doubles](practicals/README.md) ([Solutions](practicals/solutions/README.md)) and [Test Coverage](practicals/README.md) ([Solutions](practicals/solutions/README.md))|
|5 | **[Coverage Criteria, Part 2](slides/README.md)** [🎥](https://echo360.org.uk/section/ac117105-4843-42ab-afc3-cc3522e628f7/public)<ul><li>Control Flow Analysis and Testing</li><li>Logic Analysis and Testing</li><li>Conclusions on Coverage</li></ul> | [Control Flow Graphs and Logic Testing](practicals/README.md) ([Solutions](practicals/solutions/README.md)) |
|6 | [**Regression Testing**](slides/README.md) [🎥](https://echo360.org.uk/section/ac117105-4843-42ab-afc3-cc3522e628f7/public) <ul><li>Test minimisation</li><li>Test prioritisation</li><li>Test selection</li></ul>| [Regression Testing in Practice](practicals/README.md) ([Solutions](practicals/solutions/README.md)) |
|7 | **[Model-Based Testing](slides/README.md)** [🎥](https://echo360.org.uk/section/ac117105-4843-42ab-afc3-cc3522e628f7/public) | [MBT Exercises](practicals/README.md) ([Solutions](practicals/solutions/README.md)) |
|8 | **[Mutation Testing](slides/README.md)** [🎥](https://echo360.org.uk/section/ac117105-4843-42ab-afc3-cc3522e628f7/public) | [Code Defenders](slides/README.md) ([Solutions](practicals/solutions/README.md)) |
|9 | **[Search-Based Test Generation](slides/README.md)** [🎥](https://echo360.org.uk/section/ac117105-4843-42ab-afc3-cc3522e628f7/public) | [The EvoSuite Tool](practicals/README.md) ([Solutions](practicals/solutions/README.md)) |
|10| **[Fuzzing](slides/README.md)** [🎥](https://echo360.org.uk/section/ac117105-4843-42ab-afc3-cc3522e628f7/public) | [Your First Fuzzer](practicals/README.md) ([Solutions](practicals/solutions/README.md)) |

> 🎥 _Lecture recordings will be made available after each lecture has taken place._

### About Practical Sessions

These sessions are a mix of coding and paper/pen exercises that directly relate to exam questions. You will encounter similar questions in the exam, and **some exam questions may directly test content covered in practical sessions**.

**What to bring:** Bring your own laptop with Java 11 or higher installed. If you don't have a laptop, you can use the laptop loan facility. For setup help, see the **Getting Started** slides from week 1.

## Assessment

COM3529 is assessed 100% by an exam in the [Autumn exam period](https://sheffield.ac.uk/new-students/standard-exam-dates-venues).


**What to expect:** You will be asked questions testing your understanding of the concepts covered in lectures and practical sessions. The exam may also feature questions requiring you to write Java code that is **syntactically correct** and JUnit tests that call methods in libraries studied in the module (e.g., assertion and mocking libraries).

**How to prepare:** Consulting [past exam papers](https://www.dcs.shef.ac.uk/intranet/teaching/campus/pastpapers.html) when revising will give you a good sense of the format, difficulty, and types of questions you will be asked. Engage actively with practical sessions, as questions may draw directly from concepts covered there.