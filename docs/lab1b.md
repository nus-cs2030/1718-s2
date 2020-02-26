# Lab 1b

Submission deadline: 2359, Sunday, February 18, 2018.

## Prerequisites

This lab assumes that students:

- have already attempted Lab 1a
- have an understanding of the customer/server system being simulated
- have already encapsulated the given code into their appropriate classes

## Learning Objectives

After completing this lab, students should appreciate the importance of proper encapsulation and how it can help software engineers deal with changing requirements. 

## Setup

There is no new skeleton code provided.  You are to build your Lab 1b solution based on your Lab 1a.  To setup Lab 1a, do the following.

- Login to `cs2030-i`
- Copy `~/lab1a` to `~/lab1b`
- Rename `LabOneA.java` to `LabOneB.java`
- Rename the class `LabOneA` to `LabOneB`
- Copy the test data (`TESTDATA1.txt` .. `TESTDATA7.txt`) from `~cs2030/lab1b` to `~/lab1b`.

If you are still not familiar with how to do the above, please revisit the [UNIX](unix.md) guide.

## Goals

For Lab 1b, you will be asked to make the following changes to the simulator:

- The shop owner decides to hire more than one servers to avoid losing customers.  So your simulation should now support $k$ ($k \ge 1$) servers.
- It still has enough space for only one waiting customer per server. 
- The servers are arranged in fixed order, from 1 to k.
- Once a customer arrives at the shop:
   - The customer scans the servers, from 1 to k, and approaches the first idle server he/she found to be served immediately.
   - If there is no idle server, the customer scans the server, from 1 to k, and waits at the first busy server without a waiting customer that he/she found.  
   - If every server is busy and already has an existing customer waiting, the customer leaves the shop.

As a result of this, you might realize that there is a better way to encapsulate the data and the behavior of the various entities in the program.  In which case, you may want to reorganize your classes, create new classes, etc.  Depending on how "changeable" your Lab 1a solution is, you may have ended up with more than a trivial change.

## Grading

This lab contributes 6 marks to your final grade (100 marks).

- 4 marks for proper abstraction and encapsulation of classes
- 1 marks for coding style
- 1 mark for correctness

### Input and Output

The input file format has changed.  The first line of the input file now is an integer, specifying the number of servers in the shop.  The remaining lines contain a sequence of double values, each is the arrival time of a customer (in any order).  

Remember: you must not change the formatting of the _last line_ of output:
```Java
System.out.printf("%.3f %d %d", ..")
```

Given an input, the output might not be deterministic, since if two events occur at exactly the same time, we break the ties arbitrarily.  For this reason, we will only test your code with test inputs where no two events occur at exactly the same time.

The test cases and outputs[^1] should be:

Test Case | Output |
-----|-------------|
1    | `0.000 1 0` |
2    | `0.000 4 0` |
3	 | `0.350 6 4` |
4    | `0.000 7 0` |
5    | `0.210 99 1` |
6    | `0.664 39 41` or `0.665 39 41` or `0.667 39 41` |
7    | `0.653 46 54` or `0.672 46 54` |

[^1]: After piping through `tail` (e.g., `java LabOneB < TESTDATA1.txt | tail -1`)

As usual, producing the correct output for test cases above does not guarantee that your code is correct.

## Submission

When you are ready to submit your lab, on `cs2030-i`, run the script
```
~cs2030/submit1b
```

which will copy all files matching `*.java` (and nothing else) from your `~/lab1b` directory on `cs2030-i` to an internal grading directory.  We will test compile and test run with a tiny sample input to make sure that your submission is OK.

You can submit multiple times, but only the most recent submission will be graded.

This is the only way we accept submission for your labs.  We are not able to accept lab submissions via email.

Please read our policies [policies](policies.md) on late submission and on plagiarism.
