# Lecture 7: Functions


Java 8 introduces several new important new features, including lambda expressions and the stream APIs.  We will spend the next few lectures exploring these new features and how it allows us to write more succinct code and hopefully, less buggy code.  The stream APIs also makes it possible for us to parallelize our code for execution on multiple cores/processors with ease.

## Abstraction Principles Revisited

Let's revisit the abstraction principles we first visited in Lecture 2.  It says that "Each significant piece of functionality in a program should be implemented in just one place in the source code. Where similar functions are carried out by distinct pieces of code, it is generally beneficial to combine them into one by abstracting out the varying parts."

We have seen this principles being applied in several ways.  

First, consider the methods to generate random inter-arrival time and random service time.  We can write them as methods below:

```Java
double generateInterArrivalTime() {
	return -Math.log(rng.nextDouble())/this.arrivalRate;
}

double generateServiceTime() {
	return -Math.log(rng.nextDouble())/this.serviceRate;
}
```

You can see that the three methods above have similar implementation.  They all generate an exponentially distributed random number, with different rate.  One could apply the abstraction principle and write the code as follows:

```Java
double randomExponentialValue(double rate) {
	return -Math.log(rng.nextDouble())/rate;
}

double generateServiceTime() {
	return randomExponentialValue(this.serviceRate);
}

double generateInterArrivalTime() {
	return randomExponentialValue(this.arrivalRate);
}
```

Now, if you need a new exponentially distributed random number, say for inter-switching time or break time (see Lab 5), you can just write a method that calls `randomExponentialValue()` method with the appropriate rate.

Here, the varying parts that we abstracted out is the variables.  

Second, consider the `CircleQueue` and `PointQueue` examples from Lecture 4.  The two classes implements similar methods with similar logic.  The only difference is the type.  

```Java
class CircleQueue {
  private Circle[] circles;
   :
  public CircleQueue(int size) {...}
  public boolean isFull() {...}
  public boolean isEmpty() {...}
  public void enqueue(Circle c) {...}
  public Circle dequeue() {...}
}

class PointQueue {
  private Point[] points;
   :
  public PointQueue(int size) {...}
  public boolean isFull() {...}
  public boolean isEmpty() {...}
  public void enqueue(Point p) {...}
  public Point dequeue() {...}
}
```

We then replace the two classes (and any future class that we might write to implement such queues) with a generic `Queue<T>`.

```Java
class Queue<T> {
  private T[] objects;
   :
  public Queue<T>(int size) {...}
  public boolean isFull() {...}
  public boolean isEmpty() {...}
  public void enqueue(T o) {...}
  public T dequeue() {...}
}
```

Here, the varying parts that we abstracted out is the type of the elements.  

Third, consider how we tell `ArrayList.sort()` to sort the items in the array in Lecture 5.  By passing in `NameComparator`, we can tell `ArrayList` to sort in alphabetical order, either in ascending order or descending order, or by the length of the strings, or any other ways we like.   An alternative would be to have our own `StringList` class and implements methods `sortAlphabeticallyAscending()`, `sortAlphabeticallyDescending()`, `sortByLengthAscending()`, `sortByLengthDescending()`, etc.  But all of these methods would be implementing the same sorting algorithms, the only part that is different is the comparison method to determine the order of the elements.

```Java
import java.util.*;

class NameComparator implements Comparator<String> {
  public int compare(String s1, String s2) {
    // return (s1.compareTo(s2));
    // return (s2.compareTo(s1));
    return (s2.length() - s1.length());
  }
}

class SortedList {
  public static void main(String[] args) {
    List<String> names = new ArrayList<String>();

    names.add(0, "Joffrey");
    names.add(1, "Cersei");
    names.add(2, "Meryn");
    names.add(3, "Walder");
		  :
			:

    names.sort(new NameComparator());
  }
}
```

Here, the varying parts that we abstracted out is a snippet of the code, or functionality, of the methods.  This idea is much more powerful than just abstracting out how we compare and sort.  We will see how it can lead to a significantly different ways of writing code.

## Functions

While we have been using the terms functions and methods (and occasionally, procedure) interchangeably, we will now use the term function to refer to methods with specific properties.  

A function, in mathematics, refer to a mapping from a set of inputs (_domain_) $X$ to a set of output values (_codomain_) $Y$.  We write $f: X \rightarrow Y$.  Every input in the domain must map to exactly one output but multiple inputs can map to the same output.  Not all values in the codomain needs to be mapped.  The set of elements in the codomain that is mapped is called the _image_.  

Functions in programming language is the same as functions in mathematics.  Given an input, the function computes and returns an output.  A _pure_ function does nothing else -- it does not print to the screen, write to files, throw exceptions, change other variables, modify the values of the arguments.  We say that a pure function does not cause any _side effect_.  

Here is an example of a pure functions:

```Java
int square(int i) {
  return i * i;
}

int add(int i, int j) {
  return i + j;
}
```

and some examples of non-pure functions:
```Java
int div(int i, int j) {
  return i / j;  // may throw an exception
}

int incrCount(int i) {
  return this.count + i; // assume that count is not final.
	                       // this may give diff results for the same i.
}

void incrCount(int i) {
  this.count += i; // does not return a value
	                 // and has side effects on count
}

int addToList(ArrayList queue, int i) {
  queue.add(i);  // has side effects on queue
}
```

In fact, in OO paradigm, we commonly need to write methods that update the fields of an instance or compute values using the fields of an instance.  Such methods are not pure functions.
While the notion of pure functions might seems restrictive, recall how many times your program has a bug that is related to incorrect side effects or unintended side effects?  If we design and write our program with pure functions as much as possible, we could significantly reduce the number of bugs.

In mathematics, we say that a mapping is a _partial function_ if not all elements in the domain are mapped.   A common programming error is to treat a partial function like a function -- for instance, the `div` method above is written as if it is defined for all int values, but it is not defined when `j` is 0.

Mathematically, a function takes in only one value and return one value (e.g., `square` above).  In programming, we often need to write functions that takes in more than one arguments (e.g., `add` above).  We will see how to reconcile this later.

Let's explore functions in Java 8 by looking at the `Function` interface, it is a generic interface with two type parameters, `Function<T, R>`, `T` is the type of the inpuT, `R` is the type of the Result.  It has one abstract method `R apply(T t)` that applies the function to a given argument.

Let's write a class that implements `Function`.

```Java
class Square implements Function<Integer, Integer> {
	Integer apply(Integer x) {
		return x*x;
	}
}
```

To use it, we can:
```
int x = new Square().apply(4);
```

So far, everything is as you have seen before, and is significantly more complex than just writing:

```
int x = square(4);
```

So, what is the use of this?  Consider now if we have a `List<Integer>` of integers, and we want to return another list where the elements is the square of the first list.  We can write a method:
```Java
List<Integer> squareList(List<Integer> list) {
	List<Integer> newList = new ArrayList<Integer>();
	for (Integer i: list) {
    newList.add(square(i));
	}
	return newList;
}
```
Creating a new list out of an existing list is actually a common pattern.  We might want to, say, create a list with the absolute values:
```Java
List<Integer> negativeList(List<Integer> list) {
	List<Integer> newList = new ArrayList<Integer>();
	for (Integer i: list) {
    newList.add(Math.abs(i));
	}
	return newList;
}
```
This is actually a common pattern.  Applying the abstraction principles, we can generalize the method to:
```Java
List<Integer> applyList(List<Integer> list, Function<Integer,Integer> f) {
	List<Integer> newList = new ArrayList<Integer>();
	for (Integer i: list) {
    newList.add(f.apply(f));
	}
	return newList;
}
```

and call:
```Java
applyList(list, new Square());
```
to return a list of squares.

If we do not want to create a new class just for this, we can, as before, use an anonymous class:
```Java
applyList(list, new Function<Integer,Integer>() { 
	Integer apply(Integer x) {
		return x * x;
	}
});
```

### Lambda Expression

The code is still pretty ugly, and there is much boiler plate code.  The key line is actually Line 3 above, `return x * x`.  Fortunately, Java 8 provides a clean way to write this:

```Java
applyList(list, (Integer x) -> { return x * x; });
applyList(list, x -> { return x * x; });
applyList(list, x -> x * x);
```

The expressions above, including `x -> x * x`, are _lambda expressions_.  You can recognize one by the use of `->`.   The left hand side lists the arguments (use `()` if there is no argument), while the right hand side is the computation.  We do not need the type in cases where Java can refer the type, or need the return statements and the curly brackets.

!!! note "lambda"
    Alonzo Church invented lambda calculus ($\lambda$-calculus) in 1936, before electronic computers, as a way to express computation.  In $\lambda$-calculus, all functions are anonymous.  The term lambda expression originated from there.

We can use lambda expressions just like any other values in Java.  We have seen above that we can pass a lambda expression to a method.  We can also assign lambda expression to a variable:
```Java
Function<Integer,Integer> square = x -> x * x;
square.apply(4);
```

### Method Reference
We can use lambda expression to implement `applyList` with `abs()` method in `Math`.
```Java
applyList(list, x -> Math.abs(x));
```

If we look carefully at `abs()`, however, it takes in an `int`, and returns an `int`.  So, it already fits the `Function<Integer,Integer>` interface (with autoboxing and unboxing).  As such, we can refer to the method with a method reference: `Math::abs`.  The code above can be simplified to:
```Java
applyList(list, Math::abs);
```

Again, we can assign method reference and pass them around like any other objects.
```Java
Function<Integer,Integer> f = Math::abs;
f.apply(-4);
```

### Composing Functions

The `Function` interface has two default methods:
```Java
default <V> Function<T,V>	andThen(Function<? super R,? extends V> after);
default <V> Function<V,R>	compose(Function<? super V,? extends T> before);
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
