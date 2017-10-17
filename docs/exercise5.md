# Exercise 5

Solve each of this problem using Java 9 streams.  We will discuss the solutions at the beginning of Lab 7.

1. 
    - Write a method `factors` with signature `LongStream factors(long x)` that takes in an `long x` and return an `LongStream` consisting of the factors of `x`.  For instance, factors(6) should return the stream 1, 2, 3, 6.
    
    - Write a method `primeFactors` with signature `LongStream primeFactors(long x)` that takes in an `long x` and return an `LongStream` consisting of the prime factors of `x` (a prime factor is a factor that is a prime number, excluding 1).  For instance, prime factors of 6 is 2 and 3.

    - Write a method `omega` with signature `LongStream omega(int n)` that takes in an `int n` and return a `LongStream` containing the first $n$ [omega numbers](https://oeis.org/A001221).  The $i$-th omega number is the number of distinct prime factors for the number $i$.  The first 10 omega numbers are 0, 1, 1, 1, 1, 2, 1, 1, 1, 2, 1.

2. Write a method `product` that takes in two `List` objects `list1` and `list2`, and produce a `Stream` containing elements combining each element from `list1` with every element from `list2` using a given `BiFunction`.  This operation is similar to a Cartesian product.

    For instance,

    ```Java
    ArrayList<Integer> list1 = new ArrayList<>();
    ArrayList<Integer> list2 = new ArrayList<>();
    Collections.addAll(list1, 1, 2, 3, 4);
    Collections.addAll(list2, 10, 20);
    product(list1, list2, (str1, str2) -> str1 + str2)
        .forEach(System.out::println);
    ```

    gives the output:
    ```
    11
    21
    12
    22
    13
    23
    14
    24
    ```

    The signature for `product` is
    ```Java
      public static <T,U,R> Stream<R> product(List<T> list1, List<U> list2, 
          BiFunction<? super T, ? super U, R> f)
    ```

3. Write a method that returns the first $n$ Fibonacci numbers as a `Stream<BigInteger>`.  For instance, the first 10 Fibonacci numbers are 1, 1, 2, 3, 5, 8, 13, 21, 34, 55.  It would be useful to write a new `Pair<T, U>` class that keeps two items around in the stream.  We use the `BigInteger` class to avoid overflow.
