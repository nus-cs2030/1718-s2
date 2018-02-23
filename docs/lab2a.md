# Lab 2a: Simulator v1.2

Submission deadline: 2359, Friday, March 2, 2018.

## Prerequisites

This lab assumes that students:

- are already familiar with [simple UNIX commands](unix.md) to copy files.
- have already attempted Lab 1b
- have an understanding of the customer/server system being simulated
- have already encapsulated the given code into their appropriate classes

## Learning Objectives

After completing this lab, students should:

- be familiar with how to package a program the into a `jar` application
- be familiar with [`javadoc` syntax](javadoc.md) and comfortable with documenting the code with `javadoc`
- be comfortable reading a Java Collection Framework documentation and use one of the classes provided
- appreciate the usefulness of Java Collection Framework by seeing how much shorter and cleaner the resulting code is
- be familiar with the `RandomGenerator` class provided
- be ready for the next graded lab, Lab 2b.

## Setup

Sample code that solves [Lab 1b](lab1b.md) is provided (under `~cs2030/lab1b/sample` on the VM `cs2030-i`).   You are free to build on top of the given code, or build on top of your own Lab 1b solution.

- Login to `cs2030-i`
- Copy `~cs2030/lab2a` to `~/lab2a`

You should see a file `RandomGenerator.java`, four test files (`TESTDATA1.txt` to `TESTDATA4.txt`) and four trace files (`OUTPUT1.txt` to `OUTPUT2.txt`).  You should copy either your solution or our sample solution for Lab 1b to `~/lab2a`.

## Goals

For Lab 2a, there are two sets of tasks.  The first involves learning more about the tools that Java Development Kit (JDK) provides to help us write and generate documentation, and to package our application into a single executable binary.  The second involves changing the code to use Java Collection Framework as well as to generate random events.

## Writing and Generating Javadoc

From Lab 2a onwards, you are required to document your classes and methods with Javadoc comments.  You can see examples from the skeleton code given earlier.  For more details, see the [javadoc](javadoc.md) guide.

You should document all your methods and classes (including private ones).

## Packaging and Creating JAR

So far, our application consists of a set of class files.  For others to run the application, they need a copy of all the class files.  One could zip up all the class files and share the zip file, but still, the others have to figure out which is that main class contains the `main` method.

Java has a tool that combines all the class files in one Java ARchive (JAR) file.  We can include a manifest (default name is `manifest.txt`) in the JAR which specifies which is the main class with the `main` method.  

Once the jar is created, we can disseminate the jar file to others to run.  You have seen an example -- the `checkstyle` tool under `~cs2030/bin` is a `jar` file!

### Creating a package

Recall that Java has a higher-level of abstraction barrier called `package`.  So far, our classes have been included in the default package.  In this lab, you will put your classes into a package called `cs2030.simulator`.  You can achieve this by adding the line 

```Java
package cs2030.simulator;
```

on top of every `.java` file.

The package name is typically written in a hierarchical manner using the "." notations.  The name also implies the location of the `.java` files and the `.class` files.  For this reason, you can no longer store the `.java` files under `~/lab2a` directly.  Instead, you should put them in the directory `~/lab2a/cs2030/simulator`. 

### Creating and Executing a JAR

To create a jar, we first need to create a `manifest.txt` file to tell JAR what is the main class.   To do this, create a new text file named `manifest.txt` under `~/lab2a` and add the following lines:

```
Main-Class: cs2030.simulator.LabTwoA
```

!!! tip "Common Error"
    The whitespace after `:` is required.  Also, make sure that the line above
	ends with a new line.  

Now, to compile, create a jar file, and run, here is the typical workflow.  

In `~/lab2a`, run:

```Bash
javac cs2030/simulator/*.java
``` 

to compile the classes, followed by

```Bash
jar cvfm lab2a.jar manifest.txt cs2030/simulator/*class
```

to create a JAR file called `lab2a.jar` containing `manifest.txt` and the class files.  The flags `cvfm` stands for create (`c`), be verbose (`v`), use the file name (`f`) `lab2a.jar`, and use the manifest (`m`) `manifest.txt`.

Now that you have a `lab2a.jar` file, you can run it with:

```Bash
java -cp . -jar lab2a.jar TESTDATA1.txt 
```

## Priority Queuing

The next change you need to do in this assignment is to use one of the Java Collection classes to manage the events.  In `LabOneB.java`, we kept all the events in an array, and scanned through it to find the event with the smallest (i.e., earliest) timestamp.  This is not efficient, since scanning through all the events incurs a running time that increases linearly with the number of events[^2].

[^2]: For those who are taking CS2040, we say this is $O(n)$ time.  A heap-based priority queue, on the other hand, takes $O(log n)$ time.

Java Collection provides a class that is perfect for our use: [`PriorityQueue<E>`](https://docs.oracle.com/javase/8/docs/api/java/util/PriorityQueue.html).  A `PriorityQueue` keeps a collection of elements, the elements are given certain priority.  Elements can be added with [`add(E e)`](https://docs.oracle.com/javase/8/docs/api/java/util/PriorityQueue.html#add-E-) method.  To retrieve and remove the element with the highest priority, we use the [`poll()`](https://docs.oracle.com/javase/8/docs/api/java/util/PriorityQueue.html#poll--) method, which returns an object of type `E`, or `null` is the queue is empty.

In our case, the event with the smallest timestamp has the highest priority.  To tell the `PriorityQueue<E>` class how to order the events so that smaller timestamp has higher priority, we use the [`PriorityQueue<E>` constructor](https://docs.oracle.com/javase/8/docs/api/java/util/PriorityQueue.html#PriorityQueue-java.util.Comparator-) that takes in a `Comparator` object, just like we see in [Lecture 6](lec06.md).

If you design is right, you should only change the code in four places: (i) initialize list of events, (ii) schedule an event, (iii) get the next event, (iv) checking if there is still an event.

(Hint: You should be able to implement a `Comparator` without getter `getTime()`)

**You should implement this change first**, since you can do a sanity check of your correctness against the result of Lab 1b using the test data _from_ Lab 1b.

## Randomized Arrival and Service Time

Next, we are going to change how the arrival time and service time is specified, so that we can easily simulate different settings (e.g., a more efficient server with faster service time, more arrivals during weekends, etc).

### Random

First, an introduction to random number generation.  A random number generator is an entity that spews up one random number after another.  We, however, cannot generate a truly random number algorithmically.  We can only generate a _pseudo_ random number.  A pseudo-random number generator can be initialized with a _seed_.  A pseudo-random number generator, when initialized with the same _seed_, always produces the same sequence of (seemingly random) numbers.

Java provides a class `java.util.Random` that encapsulates a pseudo-random number generator.  We can create a random number generator with a seed:

```Java
Random rng = new Random(1);
```

We can then call `rng.nextDouble()` repeatedly to generate random numbers between 0 and 1.

In the demo below, we see that creating a `Random` object with the same seed of 2 towards the end leads to the same sequence of random doubles being generated.

<script type="text/javascript" src="https://asciinema.org/a/137475.js" id="asciicast-137475" async></script>

Using a fixed seed is important for testing, since the execution of the program will be deterministic, even when random numbers are involved.

### The `RandomGenerator` class

We have written a `RandomGenerator` class that encapsulates different random number generators for use in our simulator.  Each random number generator generates a different stream of random numbers.  The constructor for `RandomGenerator` takes in three parameters:

- `int seed` is the base seed for the random number generators.  Each random number generator uses a different seed derived from this argument.
- `double lambda` is the arrival rate (see below).
- `double mu` is the service rate (see below).

### Arrival Time

In Lab 1, the arrival time is given in the input text file.  This approach is less flexible and requires another program to generate the input file.  Further, the original code creates _all_ the arrival events before the simulation starts, and therefore limits the total number of arrivals to the size of the initial array `events`.

We are going to improve this part of the program, by generating the arrival one after another.  To do this, we need to generate a _random inter-arrival time_.  The inter-arrival time is usually modeled as an exponential random variable, characterized by a single parameter $\lambda$ (`lambda`), known as _arrival rate_.

Mathematically, the inter-arrival time can be generated with $-\ln(U)/\lambda$, where $U$ is a random variable between 0 and 1[^1].

[^1]: If you are not familiar with exponential distribution and random variable, do not worry, the code is being given to you.  These concepts are covered in ST2334.

- Every time an arrival event is processed, it generates another arrival event and schedules it.
- If there are still more customer to simulator, we generate the next arrival event with a timestamp of $T$ + now, where $T$ is generated with the method `genInterArrivalTime(lambda)` of the class `RandomGenerator`.

- When we first start the simulator, we need to generate the first arrival event with timestamp 0.

### Service Time

In Lab 1, the service time is constant, which is not always realistic. We are going to model the service time as an exponential random variable, characterized by a single parameter, _service rate_ $\mu$ (`mu`).  We can generate the service time with the method `genServiceTime(mu)` from the class `RandomGenerator`.

- Every time a customer is being served, we generate a "done" event and schedule it (just like we did it in Lab 1).
- The "done" event generated will have a timestamp of $T$ + now, where $T$ is _no longer constant `SERVICE_TIME`_, but instead is generated with the method `genServiceTime` from the class `RandomGenerator`.

!!! note "ADDITIONAL REQUIREMENT"
	How long it takes to service a customer depends on the customer (what service is required or how many items is in the shopping cart).  Hence, in this lab, we would like the service time to be a property associated with the customer.  In other words, the service time should be a member of the `Customer` class and is initialized when the customer arrives.

Note that _we should only have a single random number generator_ in the simulation. (hint: what access modifier should we use?)

## Grading

This lab is ungraded.  But, you should complete it and submit it anyway for our records.  Completing this lab will get your ready for Lab 2b.

### Input and Output

The input file format has changed.  The input file for Lab 2a contains the following:

- The first line is an integer, which is the base seed to the `RandomGenerator` object
- The second line is an integer, which is the number of servers
- The third line is an integer, which is the number of customers (i.e, the number of arrival events) to simulate
- The fourth line is a double, which is the parameter $lambda$
- The last line is a double, which is the parameter $mu$

Remember: you must not change the formatting of the _last line_ of output:
```Java
System.out.printf("%.3f %d %d", ..")
```

Given an input, the output might not be deterministic, since if two events occur at exactly the same time, we break the ties arbitrarily.  For this reason, we will only test your code with test inputs where no two events occur at exactly the same time.

The test cases and outputs[^1] should be:

Test Case | Output |
-----|-------------|
1    | `0.000 5 0` |
2    | `0.217 8 2` |
3	 | `1.188 14 6` |
4    | `5.263 667 333` |

[^1]: After piping through `tail` (e.g., `jar -cp . lab2a.jar < TESTDATA1.txt | tail -1`)

As usual, producing the correct output for test cases above does not guarantee that your code is correct.

We have also included traces of the simulation for each of the test cases, which you can find in the files `OUTPUT1.txt` to `OUTPUT4.txt`.  This should help you debug should your output is different from ours.

## Submission

When you are ready to submit your lab, on `cs2030-i`, run the script
```
~cs2030/submit2a
```

which will copy all files matching `*.java` (and nothing else) from your `~/lab2a/cs2030/simulator` directory on `cs2030-i` to an internal grading directory.  We will test compile and test run with a tiny sample input to make sure that your submission is OK.  We will also check if your code generates javadoc with any warning (should have no warning) and follows the CS2030 Java style guide.

You can submit multiple times, but only the most recent submission will be stored.
