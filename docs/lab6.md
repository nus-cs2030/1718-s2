# Lab 6: Querying Web API with Async Calls

Submission deadline: 2359, Friday, April 20, 2018.

(No late penalty imposed until 2359, April 22, 2018)

## Setup 

The skeleton code from Lab 6 is available on `cs2030-i` under the directory `~cs2030/lab6`.   

Inside, the directory, you will find two other files: `sample.txt` shows sample inputs and outputs; `bus-stops.txt` gives a list of bus stop IDs with names.  Both files are for your information only.

## Finding Bus Routes

The given program, `LabSix`, takes in two arguments.  The first is the ID of a bus stop (you can look up this ID at a physical bus stop, or look at the file `bus-stops.txt` given in `~cs2030/lab6`. The second is a search string, which will be used to naively match the name of another bus stop.  Multiple matches are possible.  The program will then output the list of bus services running between the bus stop with the given ID and the bus stop with matching name.

For instance, suppose you are in Clementi Interchange (ID 17009), and you want to go somewhere in NUS.  Running the query:

```
java LabSix 17009 NUS
```

will produce the following output:
```
- Take 156 to:
  - 41011 NUS Bt Timah Campus
- Take 96 to:
  - 16169 NUS Raffles Hall
  - 16199 NUS Fac Of Design & Env
  - 16149 NUS Fac Of Architecture
  - 16159 NUS Fac Of Engrg
- Take 196 to:
  - 17191 NUS High Sch
```

The order of the bus services in the output does not matter; the order of bus stops does not matter as well.

The skeleton code given is already functional, but it uses _synchronous calls_ to make query to a Web API for bus information.  

### The Web API

A Web API is an API where you invoke through HTTP protocol by specifying the parameters through the URL.  We have setup a Web API with the following interface:

`https://cs2030-bus-api.herokuapp.com/bus_services/<bus service id>` returns a list of bus stops served by a given bus.  Each bus stop occupies a line.  Each line contains the bus stop ID and its name, separated by a comma `,`.
[Example](https://cs2030-bus-api.herokuapp.com/bus_services/95)

`https://cs2030-bus-api.herokuapp.com/bus_stops/<bus stop id>` returns two lines.  The first line is the name of the bus stop.  The second line is a list of bus service IDs that serves a given bus stop.  [Example](https://cs2030-bus-api.herokuapp.com/bus_stops/18331)

The basic code to query the Web API is provided in the skeleton code.  See `BusStop.java` and `BusService.java`.

The results of the query are encapsulated in `BusRoutes`.  

The main logic, which you need to modify, is implemented in `BusSg.java`.  The lines that you need to convert into asynchronous calls have been marked with `TODO`.

## Task

The goal of this lab is to change the code from synchronous to asynchronous, using `CompletableFuture`.  All methods that involve querying the Web API should be asynchronous, since they are typically slow.  In this lab, besides the network latency, we also introduce some artificial latency in the code with `Thread.sleep`.

The code that queries the Web API would throw an exception if it fails to query the API (e.g., if there is no Internet connection).  In your code, you should handle the exception using the `handle` method of `CompletableFuture`, returning an empty set if an exception is thrown and printing an appropriate message to `System.err`.

Before you begin modifying the code in `BusSg`, you should read the code given in `BusStop.java`, `BusService.java`, `BusRoutes.java`, `BusSg.java`, and `LabSix.java` to understand how the code answer the query.

As usual, you should also:

- follows the [CS2030 Coding Style](style.md)
- clearly documented with [`javadoc`](javadoc.md)

## Grading

This lab contributes another 6 marks to your final grade (100 marks).  

You get:

- 1 marks for calling `getBusServices()` asynchronously
- 4 marks for calling `findStopsWith(name)` asynchronously and correctly in a loop
- 1 marks for handling exceptions with `handle()` 

You can get -0.5 mark deduction for serious violation of style.  

## Submission

When you are ready to submit your lab, on `cs2030-i`, run the script
```
~cs2030/submit6
```

which will copy all files matching `*.java` (and nothing else) from your `~/lab6` directory on `cs2030-i` to an internal grading directory.  We will test compile and test run with a tiny sample input to make sure that your submission is OK.

You can submit multiple times, but only the most recent submission will be graded.

!!! Warning 
    Make sure your code are in the right place -- it must be in subdirectory named `lab6`, directly under your home directory, in other words `~/lab6`.  If you place it anywhere else, it will not get submitted.
