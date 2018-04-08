# Lab 4a: Simulator 2.0

Submission deadline: 2359, Friday, March 30, 2018.

## Prerequisites

This lab assumes that students:

- have an understanding of the customer/server system being simulated in Lab 1b

## Learning Objectives

After completing this lab, students should be able to write classes that are immutable and free from side effects.

## Setup

A skeleton code for Lab 4a is provided.  To setup Lab 4a, do the following.

- Login to `cs2030-i`
- Copy `~cs2030/lab4a` to `~/lab4a`

If you are still not familiar with how to do the above, please revisit the [UNIX](unix.md) guide.

## Goals

The skeleton code provided solves Lab 1b in OO style.  The design of the class, however, have been adapted for Lab 4a and 4b, where the goal is to re-implement the solution in a functional style.

In Lab 4a, your task is to change the classes provided so that they become (i) immutable, and (ii)  no side effects.  

You should use the provided skeleton code as the base for modification.  If you wish to modify your own solution to Lab 1b, please feel free to do so as an additional exercise, but do not submit that for Lab 4a/4b.

### Immutable

Recall that immutable does not mean an object cannot be modified, but rather, modifying the state of the object always cause a new version of the object with the updated state to be returned. 
As such you will see that the return type of all methods that changes the state of a class is an object of the class itself.  For instance,

```Java
  public Statistics serveOneCustomer() {
    :
  }
```

When we update the state inside `Statistics` by incrementing `totalNumOfServedCustomers` by 1, we should return a _new_ object with this value incremented, instead of changing the object calling `serveOneCustomer`.

The classes you need to make into immutable classes are:

- `PriorityQueue`, from the package `cs2030.util`.
- `Server`, `Shop, `SimState`, `Statistics`, from the package `cs2030.simulator`. 

You should be familiar with the classes, except for 
- `PriorityQueue`, which is our own version of immutable priority queue built on top of `java.util.PriorityQueue`;
- `SimState`, the simulation state, which encapsulates three things: the event queue, the statistics, and the shop (states of the servers).

!!! warning "import java.util.*;"
    While it is convenient to do a mass import with `import java.util.*` or similar expression, it increases the chances of name clashes.  In this case, we use the same name `PriorityQueue` as `java.util`.  So you should always import only specific classes that you want to use.  The `checkstyle` configuration on `cs2030-i` has been updated to check for this.


### No Side Effects

By making the classes immutable, updating the state of one object does not lead to side effects, as a new object is created with the updated state, the existing object remains unchanged.

The remaining side effects in the code after you make the classes immutable, are reading of arrival time from either a file or standard input, and printing of debugging statements to the standard output.  In functional-style programming, to keep our functions pure without side effects, we can quarantine these input/output statements to the main function.  

For Lab 4a, one way to do this, is to include all the string printed as _part of the simulation state_.  Instead of printing a string to standard output immediately, we append this string to the simulation state.  At the end of the simulation, we print out all strings (along with the average waiting time, number of customers served, and number of customers lost). 

By doing so, we achieve pure functional code in all parts of our program except the input and output operations in the `main` method.  


### Referential Transparency

By making your classes immutable and your code free from side effects (except in `main`), you achieve the property of _referential transparency_:  _Any expression can be replaced by the resulting value of that expression, without changing the property of the program_.

## Simulation Scenario

The scenario is the same as what you solved for Lab 1b (multiple servers, each server has at most one waiting customer).

- The shop has $k$ ($k \ge 1$) servers. 
- Each server has enough space for only one waiting customer.
- The servers are arranged in fixed order, from 1 to k.
- Once a customer arrives at the shop:
   - The customer scans the servers, from 1 to k, and approaches the first idle server he/she found to be served immediately.
   - If there is no idle server, the customer scans the server, from 1 to k, and waits at the first busy server without a waiting customer that he/she found.  
   - If every server is busy and already has a customer waiting, the customer leaves the shop.

## Grading

This is an ungraded lab.  But, submit it anyway as a record that you have attempted the lab.  

### Input and Output

The input file format is exactly the same as that from [Lab 1b](lab1b.md).  The first line of the input file now is an integer, specifying the number of servers in the shop.  The remaining lines contain a sequence of double values, each is the arrival time of a customer (in any order).  

Remember: you must not change the formatting of the _last line_ of output.

The test cases and outputs[^1] should be:

Test Case | Output |
-----|-------------|
1    | `0.000 1 0` |
2    | `0.000 4 0` |
3	 | `0.350 6 4` |
4    | `0.000 7 0` |
5    | `0.210 99 1` |
6    | `0.664 39 41` or `0.665 39 41` or `0.667 39 41` |

[^1]: After piping through `tail` (e.g., `java LabFourA < TESTDATA1.txt | tail -1`)

We removed Test Case 7 (which tested for the limit of 100 in event queue, which no longer applies as we are using PriorityQueue.)

The code given already provides the correct answer.  Checking against this answer just makes sure that you did not introduce new bugs while making your code immutable and side effects free.  

## Submission

When you are ready to submit your lab, on `cs2030-i`, run the script
```
~cs2030/submit4a
```

which will copy all files matching `*.java` (and nothing else) from your `~/lab4a` directory and its subdirectory on `cs2030-i` to an internal grading directory.  We will test compile and test run with a tiny sample input to make sure that your submission is OK.
