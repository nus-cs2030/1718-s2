# Lab 4b: Simulator 2.1

Submission deadline: 2359, Friday, April 6, 2018.

## Prerequisites

This lab assumes that students are

- familiar with the monads `Stream` and `Optional`;
- familiar with using lambda expressions as higher-order functions.

## Learning Objectives

After completing this lab, students should:

- be comfortable with replacing loops with streams.
- be comfortable with replacing variables that could be `null` with `Optional` 
- be comfortable with using lambda expressions to customize the behavior of a class (instead of using polymorphism)
- be able to write more general methods by passing in lambda expressions

## Setup

You will build on top of your Lab 4a solution.  To setup Lab 4b, do the following:

- copy `~/lab4a` to `~/lab4b`.
- rename `LabFourA.java` to `LabFourB.java`
- rename the class `LabFourA` to `LabFourB`

No new test data nor skeleton code is given.

## Task

For Lab 4b, you are as several specific changes to make in the Lab 4a solution.

### Replace Polymorphism with Lambda Expressions

As you have seen in [Lecture 9](lec09.md), we can simplify the code written in OO-fashion, by replacing polymorphism with lambda expressions.

In this lab, your first task is to replace `ArrivalEvent` and `DoneEvent` with lambda expressions that you can store inside the `Event` class as a field.

You may find that you need to use variable capture to carry around variables that are used in the lambda expression.  You need to do this carefully since the objects in your program are now immutable, so a reference that your lambda expression captures might not be referring to the latest version of the object!

### Replace Loops with Streams

Your second task is to replace _all_ while loops and for loops (and possible loops that disguised as recursion) with stream operations (including that in `LabFourB.java`).  The only loop that can exist in your code after this lab is the while loop inside `run` of `Simulator`:
```Java
    while (p.first != null) {
      :
    }
```

In doing so, you may notice that the methods `findIdleServer` and `findServerWithNoWaitingCustomer` in `Shop` can be refactored and simplified, if you abstract out the common parts of the code and represent the difference part with a `Predicate`.  Go ahead and change that as well.

!!! note "Hint"
    Remember to replace the loops in `LabFourB.java` as well.  The `Scanner` class has a [`tokens()`](https://docs.oracle.com/javase/9/docs/api/java/util/Scanner.html#tokens--) method that returns a stream of delimiter-separated tokens from a scanner.

### No More `null`!  No More `null`!

Your final task for this lab is to use `Optional` for all variables that could be `null`.  Such variables include those representing a customer, a server, an event, the scanner, etc. 

As we mentioned, the `get()` method of `Optional` in Java defeats the purpose of `Optional` since it could raise an exception.  As such, you should use `get()` (and the corresponding `isPresent` checks) sparingly. 

### Coding Style and Documentation

Remember that you should still

- follow the [CS2030 Coding Style](style.md)
- clearly document your code with [`javadoc`](javadoc.md)

## Grading

This lab contributes another 6 marks to your final grade:

- Making every class immutable and side-effect free (1 marks)
- Replacing polymorphism with lambda expression and using variable capture correctly (1 marks)
- Use `Optional` for any variable that might be `null` (2 marks)
- Replacing all loops with streams (except `run()` in `Simulator`) (2 marks)

You can get up to 1 mark deduction for violation of style; up to another 1 mark for lack of javadoc documentation.  

## Submission

When you are ready to submit your lab, on `cs2030-i`, run the script
```
~cs2030/submit4b
```

which will copy the files matching `*.java` (and nothing else) from your `~/lab4b` directory (and its subdirectory `cs2030/simulator` and `cs2030/util` on `cs2030-i` to an internal grading directory.  We will test compile and test run with a tiny sample input to make sure that your submission is OK.

You can submit multiple times, but only the most recent submission will be graded.
