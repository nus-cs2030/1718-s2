# Review Questions

Here are some exercises to get ready for the final exam.  They are rejected ideas for final exam questions from last semester and this semester. :)

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

## Question 4: Stream of Functions

In mathematics, an _iterated function_ is a function that is composed with itself some number of times.  We denote $f^n$ = $f \cdot f \cdot ... f$ as function $f$ composed with itself $n$ times.  For instance, if $f$ is $\frac{1}{1 + x}$, then $f^3$ is $\frac{1}{1 + \frac{1}{1 + \frac{1}{1 + x}}}$.  

(a) Write a method that, given a `Function<T,T> f`, generate a stream of iterated functions of $f$.  The i-th element in the stream is $f^i$.

(b) Write a method that, given a stream of iterated functions $f$, $f^2$, ..., and a value `t` of type `T`, return a stream of values where each element is the result of applying the corresponding iterated function on `t`.

## Question 5: OO

(a)
The following code is not written using inheritance nor polymorphism.  Rewrite it so that it properly uses inheritance / polymorphism, eliminating the need for `StoneType` and the field `type`.

```Java
enum StoneType {
  TIME, SPACE, POWER, MIND
};

class InfinityStone {
  StoneType type;
  Color c;

  InifinityStone(StoneType type) {
	this.type = type;
	if (type == StoneType.TIME) {
	  c = Color.GREEN;
	} else if (type == StoneType.SPACE) {
	  c = Color.BLUE;
	} else if (type == StoneType.POWER) {
	  c = Color.PURPLE;
	} else if (type == StoneType.MIND) {
	  c = Color.YELLOW;
	}
  }

  void activate() {
	if (type == StoneType.TIME) {
	  warpTime();
	} else if (type == StoneType.SPACE) {
	  controlSpace();
	} else if (type == StoneType.POWER) {
	  manipulateEnergy();
	} else if (type == StoneType.MIND) {
	  controlMind();
	}
  }
}

// example of how the class will be used:
InfinityStone tesseract = new InifinityStone(StoneType.SPACE);
tesseract.activate();
```

(b)
Suppose we want to add two new stone types REALITY and SOUL.  Explain how you would do it with the original version and the OO version -- contrast how much existing code you need to modify vs. how much code you would need to add.  

p/s: Adding new code is preferable over modifying existing code, since the latter is more bug prone.

## Question 6: Bad Practices

Each of the following code illusrates a very bad programming practice.  For each, comments on why it is bad.

(a) "Pokemon Catch" 

```Java
	try {
	  doSomething();
	} catch (Exception e) {

	}
```

(b) Switching between strings

```Java
   switch(customer.getType()) {
	 case "Normal": 
	   joinQueueNormal();
	 case "Greedy": 
	   joinQueueGreedy();
     default:
	   joinQueueRandom();
   } 
```
	   
(c ) 

```Java
void getCustomerType() {
  if (customer.isNormal()) {
    throw new NormalCustomerException();
  } else if (customer.isGreedy()) {
    throw new GreedyCustomerException();
  }
}

  :
  :

try {
  getCustomerType();
  joinQueueRandom();
} catch (NormalCustomerException e) {
  joinQueueNormal();
} catch (GreedyCustomerException e) {
  joinQueueGreedy();
}
```

(d)

```Java
// customers, servers, queues are arrays of Customer, 
// Server, and Queue respectively.
Customer[] customers;
Server[] servers;
Queue[] queues;
  :
  :

void handleCustomer(int q, int s, int c) {
  // if servers[s] is busy, add customers[c] into queues[q]
  // otherwise servers[s] serves customers[c]
    :
}
```

## Question 7: Ask, Don't Tell

Suggest how we can improve the design of the classes below.  Only relevant part of the code are shown for brevity.

```Java
class LabSubmission {
  private Student s;
  private int marks;
  
  public Student getStudent() {
	return s;
  }

  public int getMarks() {
	return marks;
  }

    :
	:
}

class Gradebook {
  List<LabSubmission> submissions;

  void print() {
	for (LabSubmission s : submissions) {
	  if (s.getMarks() > 3) {
        System.out.printf(s.getStudent() + " A");
	  } else if (s.getMarks() > 2) {
        System.out.printf(s.getStudent() + " B");
	  } else if (s.getMarks() > 1) {
        System.out.printf(s.getStudent() + " C");
	  } else {
        System.out.printf(s.getStudent() + " D");
	  }
	}
  }
}
```
