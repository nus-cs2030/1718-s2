# Exercise 7

Here are some exercises to get ready for the final exam.  They are rejected ideas for final exam questions :)

## Question 1: Infinite lists

Implement a method called `interleave` that takes in two `InfiniteList<T>` objects, and produce another `InfiniteList<T>` with elements in the two lists interleave.

For instance,

```
list1 = InfiniteList.generate(() -> 1);
list2 = InfiniteList.generate(() -> 2);
interleave(list1, list2).limit(6).toArray(); // returns [1, 2, 1, 2, 1, 2]
```

The method `interleave` must be lazily evaluated.  You can assume that the constructor

```
InfiniteList<T>(Supplier<T> headSupplier, Supplier<InfiniteList<T>> tailSupplier)
```

is available.

## Question 2: Completable future

`a()`, `b()`, and `c()` are three methods that takes in no arguments and returns nothing (void).  We want to run them asynchronously, such that `a()` and `b()` run first, in any order, concurrently.  But `c()` can only run after either one of `a()` or `b()` completes.

Using the class `CompletableFuture`, write snippets of code to show how this can be done.  The [APIs for `CompletableFuture` is provided](https://docs.oracle.com/javase/8/docs/api/java/util/concurrent/CompletableFuture.html)

## Question 3: Lambdas

Java implements lambda expressions as anonymous classes.  Suppose we have the following lambda expression `Function<String,Integer>`:

```
str -> str.indexOf(' ')
```

Write the equivalent anonymous class for the expression above.

## Question 4: Currying

Consider the lambda expression:

```
x -> y -> z -> f(x,y,z)
```

where `x`, `y`, `z` are of some type `T` and `f` returns a value of type `R`.

(a) What is the type of type of the lambda expression above?

(b) Suppose that the lambda expression above is assigned to a variable `exp`.  Given three variables `x`, `y`, and `z`, show how you can evaluate the lambda expression with `x`, `y`, `z` to obtain `f(x,y,z)`.

## Question 5: Functor/Monad Laws

Suppose we have a snippet of code as follows, 

```
  Double d = foo(i);
  String s = bar(d);
```

We can write it either as:

```
stream.map(i -> foo(i)).map(d -> bar(d));
```

or 

```
stream.map(i -> bar(foo(i)))
```

We can be assure that the expressions above are the same because stream is a functor.  Why?  Explain by indicating which law ensures the behavior above is true.
