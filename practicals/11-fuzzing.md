# Quick-start with a Grammar-Based Fuzzer

For this tutorial we will introduce you to the [Fandango Grammar-Based Fuzzer](https://github.com/fandango-fuzzer/fandango).

You will start by writing your own program that can be invoked from the command-line. You will then write a grammar for Fandango, to automatically test it.

## Setup

Start a fresh Python project. In your virtual environment, run the following commands:

`pip install fandango-fuzzer`

This will [install the fandango fuzzer](https://fandango-fuzzer.github.io/Setup.html). 

`pip install coverage`

This is a useful testing tool - it will enable us to track code coverage every time the fuzzer runs our code.

## Create your target program

You can be creative here. Create a target program that parses an input (e.g. a name and an age, or some mathematical expression), and does something with it!

One important constraint (for our fuzzing purposes) it should accept its input as a string via stdin.

You are more than welcome to vibe-code this (if anything, this might give you a program that doesn't quite give you what you want!). Here is an example prompt you could use:

`I would like to write a simple program in Python. It should accept, as a single string, a person's name and date of birth via stdin, and then return their age in terms of hours, minutes, and seconds. It should also provide their generation (e.g. "millenial", "gen-z", etc.)`

I'm calling my target `calculator.py`.

## Create a grammar for your input

The Grammar is fairly self-explanatory - as covered in the lecture. However, there is a good reference on how to create a grammar with examples on the [Fandango Docs page](https://fandango-fuzzer.github.io/Language.html).

For a grammar that reflects the code I have generated above, I work from the example given in the lecture:

```
<start> ::= <person_name> "," <year> "-" <month> "-" <day>
<person_name> ::= <first_name> " " <last_name>
<first_name> ::= <name>
<last_name> ::= <name>
<name> ::= <ascii_uppercase_letter><ascii_lowercase_letter>+
<year> ::= <digit>{4}
<month> ::= <digit>{2}
<day> ::= <digit>{2}
```

Save this in a file called `age_calculator.fan` (or whatever you choose).

## Generating Inputs from the grammar

Now you are ready to fuzz your code!

Let us start by inspecting the inputs that Fandango will attempt to run on our code:



Let us start by getting Fandango to create and run 10 test cases. In the terminal (using the .venv on which you ran the pip install command) type:

`fandango fuzz -f age_calculator.fan -n 50`

From this you should see the outputs that Fandango will generate. With the `-n 50` option we restrict it to 50 inputs. Without this the default is 500.

## Running the inputs on our code

Now we get Fandango to "route" the inputs straight through to our target program every time they are generated. This can be done as follows:

`fandango fuzz -f age_calculator.fan -n 50 --input-method=stdin python calculator.py`

Don't worry if it just gives you lots of parsing-errors from your program. The main thing is that it is invoking your Python code (even if it isn't getting far).

In my code (your code will probably be different), I get:

```
Input Error: time data '7951-57-11' does not match format '%Y-%m-%d'
Input Error: time data '7450-35-76' does not match format '%Y-%m-%d'
Input Error: time data '1200-89-97' does not match format '%Y-%m-%d'
Input Error: time data '7599-61-91' does not match format '%Y-%m-%d'
Input Error: time data '5493-62-60' does not match format '%Y-%m-%d'
Input Error: time data '6766-49-90' does not match format '%Y-%m-%d'
Input Error: time data '5694-62-29' does not match format '%Y-%m-%d'
Input Error: time data '6877-43-40' does not match format '%Y-%m-%d'
Input Error: time data '5255-22-21' does not match format '%Y-%m-%d'
Input Error: time data '2288-20-01' does not match format '%Y-%m-%d'
```

Now, this doesn't actually tell us much about how much of the code has been covered. For this, instead of invoking Python directly, we can do it via the `coverage` tool (installed above). We can do this with the following command:

`fandango fuzz -f age_calculator.fan -n 10 --input-method=stdin coverage run calculator.py`

Behind the scenes, this has collected coverage data for us. We can read the report as follows:

`coverage report -m`

In my case, this reads as follows:

```
Name            Stmts   Miss  Cover   Missing
---------------------------------------------
calculator.py      41     25    39%   6-11, 18-19, 24, 31-50, 55-57
---------------------------------------------
TOTAL              41     25    39%
```

39%! That's not great. What if we ramp up the number of tests to 500?

Before we run a new test suite, we have to reset the coverage tracker:

`coverage erase`

Now, let's run it with the 500 tests:

`fandango fuzz -f age_calculator.fan -n 500 --input-method=stdin coverage run calculator.py`

Running the coverage report, however, still gives me the same results (in this case).

## Improving your coverage

Our poor coverage suggests that the vast majority of our fuzzed inputs are not even valid to begin with, and the target program is simply rejecting them trivially.

Challenge 1: Refine your `.fan` file to make better inputs! Examine which lines of code have not yet been covered, and try to enhance the grammar to ensure that they are considered. Hint - you might want to [add constraints to the grammar](https://fandango-fuzzer.github.io/Constraints.html). 

Challenge 2: Pick one of the built-in Python APIs (e.g. the [Python HTML parser](https://docs.python.org/3/library/html.parser.html)) and see whether you can get Fandango to fuzz it!  