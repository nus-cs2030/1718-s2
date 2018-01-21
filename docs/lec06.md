# Lecture 6: Nested Classes, Enum

## Learning Outcomes

- Understand the need to override `hashCode` every time `equals` is overriden and how to use `Arrays`' `hashCode` method to compute a hash code
- Understand static vs non-static nested class, local class, and anonymous class and when to use / not to use one. 
- Understand the rules about final / effectively final variable access of local class and anonymous class
- Aware of the limitation when declaring a new anonymous class
- Understand the concept of variable capture
- Be aware that `enum` is a special case of a `class` and share many features as a class -- not just constants can be defined.
- Understand how `enum` is expanded into a subclass of `Enum`
- Know that enum constants can have customized fields and methods.

## Unfinished Business: Hash Code
Last week, we have seen how `HashMap` allows we to store a key-value pair and lookup a value with the key, and how `HashSet` stores item with no duplicates. 

Internally, to implement `put`, `HashMap` calls key's `hashCode` to return a `int`, which it uses to determine which "bucket" to store the (key, value) pair.  When `get`, `HashMap` again calls `hashCode` on the given key to determine which bucket, then it looks for the key in the bucket.  This process, called _hashing_, circumvents the need to look through every pair in the map to find the right key.

You will learn more about hashing and hash tables in CS2040.

But, what is important here is that, two keys (two objects, in general) which are the same (`equals()` returns `true`), must have the same `hashCode()`.  Otherwise, `HashMap` would fail!

So it is important to ensure that if `o1.equals(o2)`, then `o1.hashCode() == o2.hashCode()`.  Note that the reverse does not have to be true -- two objects with the same hash code does not have to be equals.

This property is also useful for implementing `equals()`.  For a complex object, comparing every field for equality can be expensive.  If we can compare the hash code first, we could filter out objects with different hash code (since they cannot be equal).  We only need to compare field by field if the hash code is the same.

Ditto for implementation of `HashSet` -- to checks if an element to add already exists, `HashSet` uses hash code, instead of going through all the elements and compare one by one.

Let's see some example:

```Java
String s1 = "hello";
String s2 = new String("hello");
String s3 = "goodbye";
s1.hashCode();
s2.hashCode();
s3.hashCode();
```

Lines 4-5 both return 99162326, an integer value that is calculated using a mathematical function, called _hash function_, so that two strings with the same content returns the same value.  Line 6 returns 207022353.

We can see the problem when we don't define `hashCode()`.  Take our `Point` class from before:

```Java
jshell> Point p = new Point(0,0);
p ==> (0.0,0.0)

jshell> Point q = new Point(0,0);
q ==> (0.0,0.0)

jshell> p.equals(q);
$5 ==> true

jshell> HashSet<Point> set = new HashSet<>();
set ==> []

jshell> set.add(p);
$7 ==> true

jshell> set.add(q);
$8 ==> true

jshell> set
set ==> [(0.0,0.0), (0.0,0.0)]
```

You can see that we are adding two points that are equals into the set.  To fix this, we need to write our own `hashCode()`.

Calculating good hash code is an involved topic, and is best left to the expert (some of you might become expert in this), but for now, we can rely on the static `hashCode` methods in the `Arrays` class to help us.  Let's add the following to `Point`:

```Java
public int hashCode() {
  double[] a = new double[2];
  a[0] = this.x;
  a[1] = this.y;
  return Arrays.hashCode(a);
}
```

Now, `set.add(q)` above returns false -- we can no longer add point (0,0) twice in to the set.

## Revisit: Wildcards

A generic type can be instantiated with a wildcard `?` as type argument:

```Java
ArrayList<?> l = new ArrayList<Integer>();
```

You can think of `ArrayList<?>` as a short form for `ArrayList<? extends Object>`, which is different from `ArrayList<Object>`:

```Java
ArrayList<Object> l = new ArrayList<Integer>();
```

The above will result in an error.

The first time I showed you the wildcard type is in the signature of the `Collection<E>` interface:

```Java
public boolean containsAll(Collection<?> c);
```

The implication of this declaration is that we can pass in collection of any object types.  This provide lots of flexibility, but one could argue that there is probably too much flexibility!  One could pass in a collection of `String` objects to check if a collection of `Integer` contains these `String` objects -- the code will say no, but it seems silly to allow this.

To understand why Java designer goes with the above, instead of a less silly:

```Java
public boolean containsAll(Collection<? extends E> c);
```

recall that generics is introduced only after Java 5.  There are possibly a lot of legacy code that expects `containsAll` to take a collection of `Object` objects (since before generics is introduced, the only way to write generic class is to use `Object` like I showed you).  To keep Java backward compatible, a little bit of silliness is worth it!

## Nested Class

There are four kinds of nested classes in Java.  You have seen a static nested class, used inappropriately in Lab 2.  Let's see where are some good use cases to use nested classes.

Nested classes are use to group logically relevant classes together.  Typically, a nested class is tightly coupled with the container class, and would have no use outside of the container class.  The nested classes are used to encapsulate information within class, for instance, when implementation of a class becomes too complex.

Take the `HashMap<K,V>` class for instance.  `HashMap<K,V>` contains several nested classes, including `KeyIterator<K>`, `ValueIterator<V>` which implements a `Iterator<E>` interface for iterating through the keys and the values in the map respectively, and an `Node<K,V>` class, which encapsulates a key-value pair in the map.  These classes are declared `private`, since they are only used within the `LinkedList` class.

Nested class can be either static or non-static .  Just like static fields and static methods, a _static nested class_ is associated with the containing _class_, NOT _instance_.  So, it can only access static fields and static methods of the containing class.  A _non-static nested class_, on the other hand, can access all fields and methods of the containing class.  A _non-static nested class_ is also known as a _inner class_.

Note that a nested class can have read/write access even to the private fields and members of containing class.  Thus, you should really have a nested class only if the nested class belongs to the same encapsulation.  Otherwise, the containing class have a leaky abstraction barrier. 

## Local Class

We can declare a class within a function as well.  One example is the `EventComparator` class in Lab 3.  You might have felt silly to write a top-level class (in another file named `EventComparator.java`) just to compare two events, and you are right!

We can actually just define the `EventComparator` class when we need it, in the method that creates the event queue.

```Java
class EventComparator implements Comparator<Event> {
  public int compare(Event e1, Event e2) {
	return e1.compareTo(e2);
  }
}
events = new PriorityQueue<Event>(new EventComparator());
```

Note that I am not putting two code snippets from different part of the code together, as I sometimes do.  I am literally declaring the class inside the method where I initialize the `events` variable!

Classes declared inside a method (to be more precise, inside a block of code between `{` and `}`) is called a local class.  Just like a local variable, a local class is scoped within the method.  Like a nested class, a local class has access to the variables of the enclosing class.

Recall that when a method returns, all local variables of the methods are removed from the stack.  But, an instance of that local class might still exist.  For this reason, even though a local class can access the local variables in the enclosing method, the local class makes _a copy of local variables_ inside itself.  We say that a local class _captures_ the local variables.   It would be confusing if the copy of the variables inside the local class has a different value than the one outside.   To avoid the confusion, Java only allow a local class to access variables that are explicitly declared `final` or implicitly final (or _effectively_ final).  An implicitly final variable is one that does not change after initialization.  

Consider the following code:
```Java
boolean descendingOrder = false;
class EventComparator implements Comparator<Event> {
  public int compare(Event e1, Event e2) {
	if (descendingOrder) {
		return e2.compareTo(e1);
	} else {
		return e1.compareTo(e2);
	}
  }
}
descendingOrder = true;
events = new PriorityQueue<Event>(new EventComparator());
```

In what order will the event be sorted?  Luckily, the designers of Java save us from such hair-pulling situation and disallow such code -- `descendingOrder` is not effectively final so the code will not compile.

I do not see a good use case for local class -- if you have information and behavior inside a block of code that is so complex that you need to encapsulate it within a local class, it is time to rethink your design!

What about the use case of `EventComparator` above?  Well, if the class is short enough and is only used once, then it is a good use case for _anonymous class_.

## Anonymous Class

An anonymous class is one where you declare a class and instantiate it in a single statement.  We do not even have to give it a name!
```Java
events = new PriorityQueue<Event>(new Comparator<Event>() {
  public int compare(Event e1, Event e2) {
	return e1.compareTo(e2);
  }
});
```

The example above removes the need to declare just for the purpose of comparing two events.  An anonymous class has the following format: `new X (arguments) { body }`, where:

- _X_ is a class that the anonymous class extends or an interface that the anonymous class implements.  X cannot be empty.  This syntax also implies an anonymous class cannot extend another class and implement an interface at the same time.  Furthermore, an anonymous class cannot implements more than one interface. 
- _arguments_ is the arguments that you want to pass into the constructor of the anonymous class.  If the anonymous class is extending an interface, then there is no constructor, but we still need the two parenthesis `()`.
- _body_ is the body of the class as per normal, except that we cannot have constructor for anonymous class.

The syntax might look overwhelming at the beginning, but we can also write it as:
```Java
Comparator<Event> cmp = new Comparator<Event>() {
  public int compare(Event e1, Event e2) {
	return e1.compareTo(e2);
  }
}
events = new PriorityQueue<Event>(cmp);
```

Line 1 above looks just like what we do when we instantiate a class, except that we are instantiating an interface with a `{ .. }` body.

An anonymous class is just like a local class, it captures the variables of the enclosing scope as well -- the same rules to variable access as local class applies.

## Enum

An `enum` is a special type of class in Java.  Variable of an enum type can only be one of the predefined constants.  Using enum has one advantage over the use of `int` for predefined constant -- it is type safe!  Consider how we have been defining different event types in Lab 2.

```Java
  public static final int CUSTOMER_ARRIVE = 1;
  public static final int CUSTOMER_DONE = 2;
```

But, we cannot prevent someone from creating an event `new Event(time, 100)`, passing in an invalid event type (type 100).  

If we define the event type as enum, then we can write like this:

```Java
enum EventType {
  CUSTOMER_ARRIVE, 
  CUSTOMER_DONE
}
```

and the field `eventType` in `Event` now has a type `EventType` instead of `int`:
```Java
class Event {
  private double time; 
  private EventType eventType; 
}
```

Trying to assign anything other than the two predefined event type to `eventType` would result in compilation error.

Each constant of an enum type is actually an instance of the enum class and is a field in the enum class declared with `public static final`.  

### Enum's Fields and Methods 
Since enum in Java is a class, we can define constructors, methods, and fields in enums.  

```Java
enum Color {
  BLACK(0, 0, 0),
  WHITE(1, 1, 1),
  RED(1, 0, 0),
  BLUE(0, 0, 1),
  GREEN(0, 1, 0),
  YELLOW(1, 1, 0),
  PURPLE(1, 0, 1);

  private final double r;
  private final double g;
  private final double b;

  Color(double r, double g, double b) {
    this.r = r;
    this.g = g;
    this.b = b;
  }

  public double luminance() {
    return (0.2126 * r) + (0.7152 * g) + (0.0722 * b);
  }

  public String toString() {
    return "(" + r + ", " + g + ", " + b + ")";
  }
}
```

In the example above, we represent a color with its RGB component.  Enum values should only constants, so `r`, `g`, `b` are declared as `final`.  We have a method that computes the luminance (the "brightness") of a color, and a `toString()` method.  

The enum values are now written as `BLACK(0, 0, 0)`, with arguments passed into constructor.

### Custom Methods for Each Enum

Enum in Java is more powerful than the above -- we can define custom methods for each of the enum constant, by writing _constant-specific class body_.  If we do this, then each constant becomes an anonymous class that extends the enclosing enum.   

Consider the event type enum.  We are going to diverge from your Labs 2 and 3 now[^1], so that I can bring you the following eample:

[^1]: This may or may not be the best way to solve Lab 3. 

```Java
enum EventType {
  CUSTOMER_ARRIVE {
	// customer arrives at uniformly random interval [0, 0.5]
	double timeToNextEvent() {
	  return rng.nextDouble()*0.5;
	}
  },
  CUSTOMER_DONE {
	// customer completes service at exponential random interval with mu = 1.5
	double timeToNextEvent() {
	  return -Math.log(rng.nextDouble())/1.5;
	}
  };
  private static Random rng = new Random(1);
  abstract double timeToNextEvent();
}
```

In the code above, `EventType` is an abstract class -- `timeToNextEvent` is defined as `abstract` with no implementatino.  Each enum constant has its own implementation for calculation of time to next event.

Now, each event has its own method to generate the time to the next event of that type, and we can call
```Java
EventType.CUSTOMER_DONE.timeToNextEvent()
```
to get the time to the next event of that particular type!


### The Class `Enum`

`enum` is a special type of class in Java.  All `enum` inherits from the class `Enum` implicitly.  Since `enum` is a class, we can extend `enum` from interfaces as per normal class.  Unfortunately, `enum` cannot extend another class, since it already extends from `Enum`.

One impllicitly declared method in `enum` is a static method:

```Java
public static E[] values();
```

We can call `EventType.values()` or `Color.values()` to return an array  of event types or an array of colors.  `E` is a type parameter, corresponding to the enum type (either `EventType`, `Color`, etc).  To maintain flexibility and type safety, the class `Enum` which all enums inherit from has to be a generic class with `E` as a type paramter.

Considering `EventType`,
```Java
enum EventType {
  CUSTOMER_ARRIVE, 
  CUSTOMER_DONE
}
```

is actually 
```Java
public final class EventType extends Enum<EventType> {
  public static final EventType[] values { .. }
  public static EventType valueOf(String name) { .. }

  public static final EventType CUSTOMER_ARRIVE;
  public static final EventType CUSTOMER_DONE;
    :

  static {
	CUSTOMER_ARRIVE = new EventType();
	CUSTOMER_DONE = new EventType();
	  :
  }
}
```

Even though we can't extend from `Enum` directly, Java wants to ensure that `E` must be a subclass of `Enum` (so that we can't do something non-sensical like `Enum<String>`.  Furthermore, some methods from `Enum` (such as `compareTo()`) are inherited to the enum class, and these methods involved generic type `E`.  To ensure that the generic type `E` actually inherits from `Enum<E>`, Java defines the class `Enum` to have bounded generic type `Enum<E extends Enum<E>>`.

The expansion of enum `EventType` to a class above also illustrates a few points:

- `enum` are finals.  We cannot inherit from enum (those with constant-specifc body are exceptions).
- A class in Java can contains fields of the same class.
- The block marked by `static { .. }` are _static initializers_, they are called when the class is first used.  They are the counterpart to constructors for objects, and are useful for non-trivial initialization of static fields in a class.

## Enum-related Collections

Java Collection Frameworks provide two useful classes `EnumSet` and `EnumMap` -- they can be viewed as special cases of `HashSet` and `HashMap` respectively -- the only different is that we can only put enum values into `EnumSet` and enum-type keys into `EnumMap`. 
