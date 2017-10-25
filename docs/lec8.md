# Lecture 8: Lambdas and Streams

## Learning Objectives

After this lecture, students should be familiar with:

- how to write functions with multiple arguments using curried functions 
- how to compose functions
- general functional interface with single abstract method (SAM)
- the concept of closure and its relation to lambda expression
- the concept of eager evaluation vs. lazy evaluation
- the concept of function as delayed data and its application in defining an infinite list
- Java `Stream` class and its operations
- using the stream operations to write declarative-style code, avoiding loops and branches

We continue where we left off in Lecture 7.

## Lambdas (continued)

### Composing Functions

The `Function` interface has two default methods:
```Java
default <V> Function<T,V> andThen(Function<? super R,? extends V> after);
default <V> Function<V,R> compose(Function<? super V,? extends T> before);
```

for composing two functions.  The term _compose_ here is used in the mathematical sense (i.e., the $\cdot$ operator in $f \cdot g$).

These two methods, `andThen` and `compose`, return another function, and they are generic methods, as they have a type parameter `<V>`.  Suppose we want to write a function that returns the square root of the absolute value of an int, we can write:
```Java
double SquareRootAbs(int x) {
  return Math.sqrt(Math.abs(x));
}
```

or, we can write either
```Java
Function<Integer,Integer> abs = Math::abs;
Function<Integer,Double> sqrt = Math::sqrt;
abs.andThen(sqrt)
```

or 
```Java
sqrt.compose(abs)
```

But isn't writing the plain old method `SquareRootAbs()` clearer?  Why bother with `Function`?  The difference is that, `SquareRootAbs()` has to be written before we compile our code, and is fixed once we compile.  Using the `Function` interface, we can compose functions at _run time_, dynamically as needed!  Here is an example that you might be familiar with, from Lab 5:

```Java
Function<Customer, Queue> findQueueToSwitchTo;
if (numOfQueue > 1) {
  findQueueToSwitchTo = findShortestQueue.andThen(checkIfFewerInFront); 
} else { // only one queue
  findQueueToSwitchTo = Customer::getQueue;  // no need to do anything
}
```

So instead of relying on the logic that the shortest queue is the same as the only queue and there is always the same number of customer in front if the customer is already is in the shortest queue, we just redefine the function that finds the queue to switch to to return the only queue.

### Other Functions

Java 8 package `java.util.function` provides other useful interfaces, including:

- [`Predicate<T>`](https://docs.oracle.com/javase/8/docs/api/java/util/function/Predicate.html) with a `boolean test(T t)` method 
- [`Supplier<T>`](https://docs.oracle.com/javase/8/docs/api/java/util/function/Supplier.html) with a `T get()` method
- [`Consumer<T>`](https://docs.oracle.com/javase/8/docs/api/java/util/function/Consumer.html) with a `void accept(T t)` method
- [`BiFunction<T,U,R>`](https://docs.oracle.com/javase/8/docs/api/java/util/function/BiFunction.html) with a `R apply(T t, U u)` method

Other variations that involves primitive types are also provided.

### Curried Functions

Functions have an _arity_.  The `Function` interface is for unary functions that take in a single argument; the `BiFunction` inteface for binary functions, with two arguments.  But we can have functions that take more than two arguments.  We can, however, build functions that take in multiple arguments with only unary functions.   Let's look at this mathematically first.  Consider a binary function $f: (X, Y) \rightarrow Z$.  We can introduce $F$ as a set of all functions $f': Y \rightarrow Z$, and rewrite $f$ as $f: X \rightarrow F$, of $f: X \rightarrow Y \rightarrow Z$.

A trivial example for this is the `add` method that adds two `int` values. 
```Java
int add(int x, int y) {
  return x + y;
}
```

This can be written as
```Java
Function<Integer, Function<Integer, Integer>> add = x -> y -> (x + y);
```

To calcualte 1 + 1, we call
```Java
add.apply(1).apply(1);
```

Let's break it down a litte, `add` is a function that takes in an `Integer` object and returns a unary `Function` over `Integer`.  So `add.apply(1)` returns the function `y -> 1 + y`.  We could assign this to a variable:
```
Function<Integer,Integer> incr = add.apply(1);
```

Here is the place where you need to change how you think: `add` is not a function that takes two arguments and return a value.  It is a _higher-order function_ that takes in a single argument, and return another function.

The technique that translates a general $n$-ary function to a sequence of $n$ unary functions is called _currying_.  After currying, we have a sequence of _curried_ functions.  

!!! note "Curry"
    Currying is not related to food, but rather is named after computer scientist Haskell Curry, who popularized the technique.
    
Again, you might question why do we need this?  We can simply call `add(1, 1)`, instead of `add.apply(1).apply(1)`?  Well, the verbosity is the fault of Java instead of functional programming techniques.  Other languages like Haskell or Scala have much simpler syntax (e.g., `add 1 1` or `add(1)(1)`).  

If you get past the verbosity, there is another reason why currying is cool.  Consider `add(1, 1)` -- we have to have both arguments available at the same time to compute the function.  With currying, we no longer have to.  We can evaluate the different arguments at different time (as `incr` example above).  This feature is useful in cases where some arguments are not available until later.  We can _partially apply_ a function first.  This is also useful if one of the arguments does not change often, or is expensive to compute.  We can save the partial results as a function and continue applying later.

Again, using Lab 5 as example, you can have several functions defined:
```Java
Function<Double, Function<Double,Double>> generateExponetialVariable = 
    rate -> randDouble -> -Math.log(randDouble)/rate;
Function<Double,Double> generateServiceTime = 
    generateExponentialVariable.apply(param.lambda);
Function<Double,Double> generateInterArrivalTime = 
    generateExponentialVariable.apply(param.mu);
  :
```

Instead of keeping around the parameters, you could keep the functions to generate the random time as fields, and invoked them:
```Java
this.generateServiceTime.apply(rng.nextDouble());
```

### Functional Interface

We are not limited to using lambda expression for the interfaces defined in `java.util.function`.  We can use lambda expression as a short hand to a class that implements a interface with a single abstract method -- there has to be only one abstract method so that the compiler knows which method the lambda implements.  This is more commonly known as SAM interface.  
Note that a SAM interface can be mulitple methods, but only one can be abstract (others can have default implementation).

For instance, Java has the following interface:
```
interface Runnable {
  void run();
}
```

There is only one method, and it is abstract (no default implementation).  So it is a valid SAM interface.

We can annotate a class with `@FunctionalInterface` to hint our intention to the compiler and to let the compiler helps us to catch unintended error (such as when we add a second abstract method to the interface).

We can define our own interface as well.  For instance, in Lab 5, we can define:
```
@FunctionalInterface
interface FindQueueStrategy {
  CustomerQueue findQueue(Shop shop);
}

@FunctionalInterface
interface JoinQueueStrategy {
  void joinQueue(CustomerQueue queue, Customer c);
}
```

Now, we can avoid three `Customer` subclasses.  We just need to instantiate a new `Customer` with different strategies:

We can set `FindQueueStrategy` to either `shop -> shop.getShortestQueue()` or `shop -> shop.getRandomQueue()` and set the JoinQueueStrategy to either `(q, customer) -> q.addToBack(customer)` or `(q, customer) -> q.addToFront(customer)`.

### Lambda as Closure

Just like a local class an anonymous classes, a lambda can capture the variables of the enclosing scope.  For instance, if you do not wish to generate the service time of a customer at the time of arrival, you can pass in a `Supplier` to `Customer` instead:
```
Customer c = new Customer(() -> -Math.log(rng.nextDouble())/rate);
```

Here, `rng` and `rate` are variables captured from the enclosing scope.

And just like in local and anonymous classes, a captured variable must be either explicitly declared as `final` or is effectively final.

A lambda expression therefore store more than just the function to invoke -- it also stores the data from the environment where it is defined.  We call such construct which store a function together with the enclosing environments a _closure_. 

### Function as Delayed Data

Consider a function that produces new value or values.  We can consider the function as a promise to give us the given data sometime later, when needed.  For instance:
```
() -> -Math.log(rng.nextDouble())/rate)
```

is not the value of a service time, but rather, a supplier of the service time.  When we need a service time, we just invoke the supplier.

What's the big deal?  Not so much in the simple example above.  But consider the case where the function is an expensive one.
We can then delay the execution of the expensive function until it is absolutely needed.  This allows us to do things that we couldn't before, for instance, create and manipulate an infinite list!

### An Infinite List

How can we represent an infinite list?  If we store the values of each element in the list, then we will run out of memory pretty soon.  If we try to manipulate every element in the list, then we will enter an infinite loop.  

The trick to building an infinite list, is to treat the elements in the list as _delayed data_, and store a function that generates the elements, instead of the elements itself.

We can think of an infinite list as two functions, the first is a function that generates the first element, and the second is a function that generates the rest of the list.

```Java
class InfiniteList<T> {
  private Supplier<T> head;
  private Supplier<InfiniteList<T>> tail;

  public static InfiniteList<T> generate(Supplier<T> supply) {
    return new InfiniteList(supply,
	  () -> InfiniteList.generate(supply));
  }
}
```

There you go!  We now have an infinite list defined by the supply function.  

A list defined this way is lazily evaluated.  We won't get the elements until we need it -- this is in constrast to the eager `LambdaList` you write in Lab 6.

Let's see how to use this list.  Consider the `findFirst` method:

```Java
public T findFirst(Predicate<T> predicate) {
  T first = this.head.get();
  if (predicate.test(first)) {
    return first;
  } 
  InfiniteList<T> list = this.tail.get();
  while (true) {
	T next = list.head.get();
	if (predicate.test(next)) {
	  return next;
	}
	list = list.tail.get();
  }
}
```

!!! Note "Simpler code"
    The code shown in the lecture above could be simplified to:
	```Java
	public T findFirst(Predicate<T> predicate) {
	  InfiniteList<T> list = this;
	  while (true) {
		T next = list.head.get();
		if (predicate.test(next)) {
		  return next;
		}
		list = list.tail.get();
	  }
	}
	```


In the method above, we repeatedly invoke the supplier, until we find an element that matches the predicate.  This way, we never had to generate every element in the list just to find the first element that matches.

!!! Note "iterate"
    In class, I also showed the `iterate` method to generate a list:
	```Java
	  public static <T> InfiniteList<T> iterate(T init, Function<T, T> next) {
		return new InfiniteList<T>(
			() -> init, 
			() -> InfiniteList.iterate(next.apply(init), next)
		  );
	  }
	```

## Stream

Such a list, possibly infinite, that is lazily evaluated on demand is also known as a _stream_.  Java 8 provides a set of useful and powerful methods on streams, allowing programmers to manipulate data very easily.  Java 9 adds a couple of useful methods, `takeWhile` and `dropWhile`, which is also invaluable.  To take full advantage of streams, we will be using Java 9, not Java 8 for the rest of this class.  

### Stream operations

A few things to note before I show you how to use streams.  First, the operations on streams can be classified as either _intermediate_ or _terminal_.  An _intermediate_ operation returns another stream.  For instance, `map`, `filter`, `peek` are examples of intermediate operations.  An intermediate operation does not cause the stream to be evaluated.  A terminal operation, on the other hand, force the streams to be evaluated.  It does not return a stream.  `reduce`, `findFirst`, `forEach` are examples of terminal operation.  A typical way of writing code that operate on streams is to chain a series of intermediate operation together, ending with a terminal operation.  

Second, a stream can only be consumed once.  We cannot iterate through a stream mulitple times.  We have to create the stream again if we want to do that:

```Java
Stream<Integer> s = Stream.of(1,2,3);
s.count();
s.count(); // <- error
```

In the example above, we use the `of` static method with variable number of arguments to create a stream.  We can also create a stream by:

- converting an array to stream using `Arrays.stream` method
- converting a collection to stream using `stream` method
- reading from a file using `Files.lines` method
- using the `generate` method (provide a `Supplier`) or `interate` method (providing the initial value and incremental operation). 

You have seen many of the stream operations before, in Lab 6, including `map`, `reduce`, `filter`, `findFirst`, `peek`, and `forEach`.  Even though they are in the context of an eagerly evaluated list, the semantics are the same.  Here are a few more useful ones.

- `flatMap` is just like `map`, but it takes in a function that produces another stream (instead of another element), and it `flattens` the stream by inserting the elements from the stream produced into the stream.

Let see an example.  The lambda below takes a string and return a stream of `Integer` objects:

```Java
x -> x.chars().boxed()
```

We can create a stream of strings using the static `of` method from `Stream`:

```Java
Stream.of("live", "long", "and", "prosper")
```

If we chain the two together, using `map`, however, we will produce a stream of stream of `Integer`.

```Java
Steam.of("live", "long", "and", "prosper")
    .map(x -> x.chars().boxed())
```

To produce a stream of `Integer`s, we use `flatMap()`:

```Java
Stream.of("live", "long", "and", "prosper")
    .flatMap(x -> x.chars().boxed())
```

- `sorted` is an intermediate operation that returns a stream with the elements in the stream sorted.  Without argument, it sorts according to the natural order.  You can also passed in a `Comparator` to tell `sorted` how to sort.

- `distinct` is another intermediate operation that returns a stream with only distinct elements in the stream. 

`distinct` and `sorted` are stateful operations -- it needs to keep track of states in order to perform the operation.  `sorted` in particular, need to know every elements in the stream before it can output the result.  They are also known as `bounded` operations, since call them on an infinite stream is a very bad idea!

Here is how we print out the unique characters of a given sequence of streams in sorted order
```Java
Stream.of("live", "long", "and", "prosper")
    .flatMap(x -> x.chars().boxed())
	.distinct()
	.sorted()
	.map(x -> new Character((char)x.intValue()))
	.forEach(System.out::print);
```

There are several intermediate operations that convert from infinite stream to finite stream.  

- *`limit`* takes in an `int` $n$ and return a stream containing the first $n$ elements of the stream;
- *`takeWhile`* takes in a predicate and return a stream containing the elements of the stream, until the predicate becomes false.  The resulting stream might still be infinite if the predicate never becomes false.

Here are more useful terminal operations:

- *`noneMatch`* return true if none of the elements pass the given predicate.
- *`allMatch`* return true if every element passes the given predicate.
- *`anyMatch`* return true if no elements passes the given predicate.

### Example 1: Is this a prime?

Consider the method below, which checks if a given `int` is a prime:

```Java
boolean isPrime(int x) {
  for (i = 2; i <= x-1; i++) {
	if (x % i == 0) {
	  return false;
	}
  }
  return true;
}
```

The code coudln't be simpler -- or can it?  With streams, we can write it as:
```Java
boolean isPrime(int x) {
  return IntStream.range(2, x-1)
      .matchNone(x % i == 0);
}
```

!!! Warning "Bug"
    There is a bug in the earlier code where the code showed `IntStream.range(2,x-1)`.  `range(m,n)` returns a stream from `m` to `n-1`.

`IntStream` is a special `Stream` for primitive type `int`, the `range` method generates a stream of `int` in a given range (inclusive)

What if we want to print out the first 500 prime numbers, starting from 2?  Normally, we would do the following:
```Java
void fiveHundredPrime() {
  int count = 0;
  int i = 2;
  while (count < 500) {
	if (isPrime(i)) {
	  System.out.println(i);
	  count++;
	}
	i++;
  }
}
```

The code is still considered simple, and understandable for many, but I am sure some of us will encounter a bug the first time we write this (either forgot to increment counter, or put the increment in the wrong place).  If you look at the code, there are a couple of compnents:

- Lines 3 and 9 deal with iterating through different numbers for primality testing
- Line 4 is the test
- Lines 2, 4, and 7, deal with limiting the output to 500 primes
- Line 5 is the action to perform on the prime

With streams, we can write it like the following:
```Java
IntStream.iterate(2, x -> x+1)
    .filter(x -> isPrime(x))
	  .limit(500)
	  .forEach(System.out::println);
```

Notice how each of the four components matches neatly with one operation on stream!  

With stream, we no longer have to write loops -- as you have done in Lab 6, we have move the iterations to within each operation in stream.  We no longer need to maintain states and counters, they are done within each operation as needed as well.  This has another powerful implication: our code become more _declarative_, we only need to concern about what we want at each step, much less about how to do it.


You should take a look at the methods provided by the `Stream` class, and read through the APIs, a few times, they formed fundamental building blocks for writing data processing code in functional style in Java.

### Example 2: Simulator's run

Let's convert our code from our lab into stream.  Consider this:

```Java
  public void run(Simulation sim) {
      schedule(sim.firstEvents());
      while (!events.isEmpty()) {
        Event e = this.events.poll();
        if (e.happensBefore(sim.expireTime())) {
          e.log();
          Event[] newEvents = sim.handle(e);
          if (newEvents != null) {
            schedule(newEvents);
          }
        } else {
          break;
        }
      }
      System.out.println(sim.stats);
```

Again, you can see that the main loop is doing several things at one time: (i) it checks if there is still events in the queue, in Line 3, (ii) it checks if we have reached the end of the simulation in Line 5 and Line 12, (iii) it logs the event for debugging in Line 6, (iv) it runs and generates new events in Line 7, then (v) adds to the events in Lines 8-9.

Let's try to rewrite this using stream.  First, we need to change our event generation to return a stream of events.  Instead of:

```
return new Event[] { event1, event2 };
```

We can now say:

```
return Stream.of(event1, event2);
```

which is IMHO cleaner and more readable.  We also get to avoid `null` (which is never a bad thing) by returning `Stream.empty()` instead of `null` if we are not going to generate a new event.

The `schedule` method becomes:
```Java
  public void schedule(Stream<Event> stream) {
    this.events.addAll(stream.collect(Collectors.toList()));
  }
```

We will still use `PriorityQueue` for events, and so `addAll` expects a collection.  The `collect` method simply converts a stream to a collection.

With the above changes, we can now rewrite the while loop in `run` using stream:
```Java
    Stream.generate(this.events::poll)
        .takeWhile(event -> event != null)
        .filter(event -> event.happensBefore(sim.expireTime())) 
        .peek(event -> event.log())
        .map(event -> sim.handle(event))
        .forEach(eventStream -> this.schedule(eventStream));
```

Once you get used to writing code with streams, it might be hard to go back to writing loops and bracnhes again!
