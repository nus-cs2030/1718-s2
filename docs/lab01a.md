# Lab 1a

Submission deadline: 2359, Friday, February 9, 2018.

## Prerequisites

Assume that students are already familiar with:

- the [CS2030 laboratory environment](unix.md)
- how to [compile and run Java programs](java.md)
- comfortable with Java syntax
- looking up Java API documentation
- understand the concepts of encapsulation and using encapsulated objects

## Learning Objectives

After completing this lab, students should:

- be more comfortable with looking at a complex problem and identify data and procedures that should be kept within an abstraction barrier.  In other words, be more comfortable with creating own encapsulated class.
- be more comfortable with basic Java syntax and semantics, particularly when creating classes from scratch.
- be comfortable with following a given coding convention.

## Setup

Login to `cs2030-i`, copy the files from `~cs2030/lab1a` to your local directory under your home `~/lab1a`.  You should see one java file (`LabOneA.java`) and a few data files (`TESTDATA1.txt`, `TESTDATA2.txt`, ..., `TESTDATA5.txt`)

## Goals

`LabOneA.java` implements a working discrete event simulator that simulates customers being served by a server.  It is written in procedural style with _no encapsulation_.  The goal, in this lab, is to rewrite this simulator with OO style, by properly using encapsulation to create abstraction barriers to the various variables and methods.  Here and some hints:

- Think about the problem that you are solving: what are the nouns?  These are good candidates for classes. 
- For each class, what are the attributes/properties relevant to the class?  These are good candidates for fields in the class.
- For each class, what are their responsibilities?  What can they _do_?  These are good candidates for methods in the class.
- How do the objects of each class interact?  These are good candidates for public methods.

Note that the goal of this lab and, and of CS2030 in general, is _NOT_ to solve the problem with the cleverest and the shortest piece of code possible.  For instance, you might notice that you can solve Lab 1a with only a few variables and an array.  But such solution is hard to extend and modify.  In CS2030, our goal is to produce a software that can easily evolve and be modified, with a reduced risk of introducing bugs while doing so.

Note that Lab 1a is the first of a series of labs, where we introduce new requirements or modify existing ones in every lab (not [unlike what software engineers face in the real world](https://accu.org/index.php/journals/319)).  We will slowly evolve the simulator into something more general and will simulate a more complex server and customer behaviors in our simulation.

Thus, making sure that your code will be able to adapt to new problem statements is the key.  Trying to solve the lab without considering this (such as coming up with a solution that computes the average waiting time in only tens of lines of code) and you will likely find yourself painted into a corner and have to re-write much of your solution to handle the new requirement.

## Grading

This lab is not graded.  But, it will be extended to Lab 1b, which is graded.  Coming out with a good encapsulation will make your Lab 1b much easier to solve. 
You may be tempted to wait for Lab 1b to be released and solve Lab 1a and 1b together -- unless you are very sure you can handle it, do not do this.  The purpose of Lab 1a is to give you enough time to ponder 
over different designs and get feedback from your Lab TAs.

## Discrete Event Simulator

A discrete event simulator is a software that simulates a system (often modeled after the real world) with events and states.  An event occurs at a particular time, and each event alters the states of the system and may generate more events.  A discrete event simulator can be used to study many complex real-world systems.  The term _discrete_ refers to the fact that, the states remain unchanged between two events, and therefore, the simulator can _jump_ from the time of one event to another, instead of following the clock in real time.  The simulator typically keeps track of some statistics to measure the performance of the system.

In this lab, we start with simulating a specific situation:

- We have a shop with a _server_ (a person providing service to the customer).
- The server can serve one customer at a time.
- We assume for now that the server takes constant time to serve a customer.
  The time taken to serve is called _service time_.
- When a customer arrives:
    - if the server is idle (not serving any customer), then the server serves the customer immediately (no waiting).
    - if the server is serving another customer, then the customer that just arrives waits.
    - if the server is serving one customer, and another customer is waiting, then the customer that just arrives just leave (no waiting) and go elsewhere.  In other words, there is at most one waiting customer.
- When the server is done serving a customer:
    - the served customer leaves.
	- if there is another customer waiting, the server starts serving the waiting customer immediately.
	- if there is no waiting customer, then server becomes idle again.

We are interested in the following.  Given a sequence of customer arrivals (time of each arrival is given):

- What is the average waiting time for customers that have been served?
- How many customers are served?
- How many customers left without being served?

In your Lab 1a, you are given a simple discrete event simulator to answer the questions above.  There are two classes: `Simulator` and `Event`.

### Class `Event`

The event class is written in a procedural style, not unlike a `struct` in C.  All members are public, and there is no method.  Each `Event` keeps track of two information: the `time` the event occurs, and `eventType`, which signifies what type of events is this.  Instead of using time like 9:45 pm, we simply and represent time as a double value.

```Java
  static class Event {
    public double time; // The time this event will occur
    public int eventType; // The type of event, indicates what should happen when an event occurs. 
  }
```

We handle two types of events for this particular scenario: an event of type `CUSTOMER_ARRIVE` means that a customer arrives during this event; while an event of type `CUSTOMER_DONE` means that the customer is done being served.  `CUSTOMER_ARRIVE` events are created based on the given input.  `CUSTOMER_DONE` events are created and scheduled to occur sometime in the future when a customer is being served.

```Java
  public static final int CUSTOMER_ARRIVE = 1;
  public static final int CUSTOMER_DONE = 2;
```

### Class `Simulator`

The simulator class is again written in procedural style.  All members are public, and there is no method.

The `Simulator` class contains two configuration parameters, `MAX_NUMBER_OF_EVENTS` indicates the maximum number of events that the simulator can store at one time; `SERVICE_TIME` indicates the time the server takes to serve a customer.
```Java
    public static final int MAX_NUMBER_OF_EVENTS = 100; // Maximum number of events
    public static final double SERVICE_TIME = 1.0; // Time spent serving a customer
```

The `events` is an array of `Event` that store all events scheduled for the future in the simulator.
```Java
    public Event[] events; // Array of events, order of events not guaranteed.
    public int numOfEvents; // The number of events in the event array.
```

The simulator needs to keep track of three states:

- is a customer being served?
- is a customer waiting?
- if a customer is waiting, when did he start waiting?

These states are represented as:

```Java
    public boolean customerBeingServed; // is a customer currently being served?
    public boolean customerWaiting; // is a customer currently waiting?
    public double timeStartedWaiting; // the time the current waiting customer started waiting
```

Remember we are interested in the following statistics:

- What is the average waiting time for customers that have been served?
- How many customers are served?
- How many customers left without being served?

which can be computed from the following members:

```Java
    public double totalWaitingTime; // total time everyone spent waiting
    public int totalNumOfServedCustomer; // how many customer has waited
    public int totalNumOfLostCustomer; // how many customer has been lost
```

Finally, for debugging purposes, the simulator assigns unique IDs 1, 2, 3, ... to the customers, in the order of their arrivals.  It then keeps track of the ID of the customer being served (if any) and the customer waiting to be served.

```Java
    public int lastCustomerId; // starts from 0 and increases as customer arrives.
    public int servedCustomerId; // id of the customer being served, if any
    public int waitingCustomerId; // id of the customer currently waiting, if any
```

### Interaction between `Simulator` and `Event`
We create a `Simulator` by calling the method:
```Java
  static Simulator createSimulator() {..}
```
and an `Event` by calling the method, specifying `when` the event will occur, and the `type` of the event.
```Java
  static Event createEvent(double when, int type)  {..}
```

We can schedule the event `e` to be executed by simulator `sim` by calling:
```Java
  static boolean scheduleEventInSimulator(Event e, Simulator sim) {..}
```
This method will return `true` if the event is scheduled successfully, `false` if the simulator run out of space to store the event (i.e., `MAX_NUMBER_OF_EVENTS` is reached).

We always execute the events in increasing sequence of their time.  Once the simulator starts running, it repeatedly finds the next event with the smallest timestamp (i.e., earliest event), removes it from the list of events, and execute the event.  The simulator stops when there is no more event to run.

```Java
  static void runSimulator(Simulator sim) {
    while (sim.numOfEvents > 0) {
      Event e = getNextEarliestEvent(sim);
      simulateEvent(sim, e);
    }
  }
```

Here, `e = getNextEarliestEvent(sim)` removes and returns the earliest event in the simulator, and `simulateEvent(sim, e)` update the states of the simulator according to the type of the event `e`.

### Simulated System

The logic of the system being simulator (i.e., the behavior of customers and server) is implemented in `simulateEvent`.  There are four methods being called from here:

- `serveCustomer(sim, time, id)`: called to start serving a customer with ID `id`
- `makeCustomerWait(sim, time, id)`: called to make the customer with ID `id` wait
- `customerLeaves(sim, time, id)`: called when the customer with ID `id` who just arrived leaves immediately (as someone else is waiting)
- `servedWaitingCustomer(sim, time)`: called to start serving the customer that is currently waiting.

You should read through `LabOneA.java` and clarify if you are not sure about any part of the given code.

### Input and Output

The input consists of a sequence of double values, each is the arrival time of a customer (in any order).  We can read from standard input (if no command line argument is given)
```
java LabOneA
java LabOneA  < TESTDATA1.txt
```
or read from a given filename
```
java LabOneA TESTDATA1
```

Given an input, the output might not be deterministic, since if two events occur at exactly the same time, we break the ties arbitrarily.  For this reason, we will only test your code with input where no two events occur at exactly the same time.

The test cases and outputs[^1] should be:

Test Case | Output |
-----|-------------|
1    | 0.000 1 0   |
2    | 0.000 4 0   |
3	 | 0.450 2 8   |
4    | 0.614 7 0   |
5    | 0.405 76 24 |

[^1]: After piping through `tail` (e.g., `java LabOneA < TESTDATA1.txt | tail -1`)

Note that the skeleton we gave to you already produces the output above.  You just need to ensure that after changing the code to OO style, the output remains the same.  Of course, as usual, producing the correct output for five test cases does not guarantee that your code is correct.

## Your Task

The given `LabOneA.java` is written in C style, no minimal encapsulation.  As you read through the code, you should appreciate how messy and difficult to understand the code is.

Your mission, in Lab 2, is to rewrite the code using encapsulation, applying OO paradigm, properly maintain the abstraction barrier when the objects interact.  See the Section on Goals above.  Here are some rules:

- You can add as many classes as you like.  Each class must be in its own `.java` file

- The `main` method should remain in a class named `LabOneA`.  We must be able to run your code with:
```
javac *.java
java LabOneA < TESTDATA1.txt
```

- You must not change the formatting of the _last line_ of output (`System.out.printf("%.3f %d %d", ..")`).  We rely on it to check for correctness of your logic.

- Your code should follow the [CS2030 Coding Style](style.md)


## Submission

When you are ready to submit your lab, on `cs2030-i`, run the script
```
~cs2030/submit1a
```

which will copy all files matching `*.java` (and nothing else) from your `~/lab1a` directory on `cs2030-i` to an internal grading directory.  We will test compile and test run with a tiny sample input to make sure that your submission is OK.

We will run `checkstyle` to check your code against the CS2030 Java Coding Convention upon submission. 

This lab is not graded.  But do submit anyway so that we have a record that you have done the lab.

To get individual feedback on your lab solution, please talk to your lab TAs.

## Extra Java Stuff

You are exposed to three new Java syntax/class in this Lab:

- Nested classes: In the code given to you, we define `Simulator` and `Event` within the class `LabOneA`.  This is called _nested class_ in Java.  Usually, this is useful if we need to create a class that is only useful to another class.  We can group logically relevant classes together.
- `assert` keyword: `assert` works like in C and Python.  You use `assert` to check for conditions that have to be true in your code.  If an assertion fails, the program will bail, informing you what went wrong.  This is useful to catch bugs quickly.  Use this by passing a `-ea` (enable assertions) flag when running a Java program e.g. `java -ea LabOneA TESTDATA1.txt`
- [`FileReader`](http://docs.oracle.com/javase/8/docs/api/?java/io/FileReader.html): a useful class for reading a stream of characters from a file.

