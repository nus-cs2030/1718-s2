# Exercise 6

This is an ungraded exercise.

1. Implement the `map` method of `Optional`:
		
		public<U> Optional<U> map(Function<? super T, ? extends U> mapper) {
			 :
		} 

    Convince yourself it is a functor.

2. Implement a collector that takes in a stream of doubles and return the average value. 

    Show the supplier, accumulator, combiner, and finisher methods.
