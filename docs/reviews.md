# Review Questions

These set of questions are designed to illustrate certain rules and principles about Java.
You can easily find out the answer yourselves using `jshell` or write small programs.

It is more important to understand why -- there are some underlying principles/rules in Java that cause the compiler / code to behave the way it does.

Note that the code is terse by design, and is never meant to be a positive example of good Java code.


9. Can, or not? If can, print what?

    ```Java
    class A {
      void f() { System.out.println("A f"); }
    }

    class B extends A {
    }

    B b = new B();
    b.f();
    A a = b;
    a.f();
    ```

10. Can, or not? If can, print what?

    ```Java
    class A {
      void f() {
        System.out.println("A f");
      }
    }

    class B extends A {
      void f() {
        System.out.println("B f");
      }
    }

    B b = new B();
    b.f();
    A a = b;
    a.f();
    a = new A();
    a.f();
    ```

11. Can, or not? If can, print what?

    ```Java
    class A {
      void f() {
        System.out.println("A f");
      }
    }

    class B extends A {
      void f() {
        super.f();
        System.out.println("B f");
      }
    }

    B b = new B();
    b.f();
    A a = b;
    a.f();
    ```

12. Can, or not? If can, print what?

    ```Java
    class A {
      void f() {
        System.out.println("A f");
      }
    }

    class B extends A {
      void f() {
        this.f();
        System.out.println("B f");
      }
    }

    B b = new B();
    b.f();
    A a = b;
    a.f();
    ```


13. Can, or not? If can, print what?

    ```Java
    class A {
      void f() {
        System.out.println("A f");
      }
    }

    class B extends A {
      int f() {
        System.out.println("B f");
        return 0;
      }
    }

    B b = new B();
    b.f();
    A a = b;
    a.f();
    ```

14. Can, or not? If can, print what?

    ```Java
    class A {
      void f() {
        System.out.println("A f");
      }
    }

    class B extends A {
      void f(int x) {
        System.out.println("B f");
        return x;
      }
    }

    B b = new B();
    b.f();
    b.f(0);
    A a = b;
    a.f();
    a.f(0);
    ```

15. Can, or not?  If can, what will be printed?

	```Java
    class A {
      public void f() {
        System.out.println("A f");
      }
    }

    class B extends A {
      public void f() {
        System.out.println("B f");
      }
    }

	B b = new B();
	A a = b;
	a.f();
	b.f();
	```

16. Can, or not?  If can, what will be printed?

	```Java
    class A {
      private void f() {
        System.out.println("A f");
      }
    }

    class B extends A {
      public void f() {
        System.out.println("B f");
      }
    }

	B b = new B();
	A a = b;
	a.f();
	b.f();
	```

17. _Deleted question - duplicate of Q16_

18. Can, or not?  If can, what will be printed?

	```Java
    class A {
      static void f() {
        System.out.println("A f");
      }
    }

    class B extends A {
      public void f() {
        System.out.println("B f");
      }
    }

	B b = new B();
	A a = b;
	a.f();
	b.f();
	```

19. Can, or not?  If can, what will be printed?

	```Java
    class A {
      static void f() {
        System.out.println("A f");
      }
    }

    class B extends A {
      static void f() {
        System.out.println("B f");
      }
    }

	B b = new B();
	A a = b;
	A.f();
	B.f();
	a.f();
	b.f();
	```

20. Will the following code compile? Why?

	```Java
    class A {
	  public void f(int x) {}
	  public void f(boolean y) {}
	}
	```

21. Will the following code compile? Why?

	```Java
    class A {
	  public void f(int x) {}
	  public void f(int y) {}
	}
	```

22. Will the following code compile? Why?

	```Java
    class A {
	  private void f(int x) {}
	  public void f(int y) {}
	}
	```

23. Will the following code compile? Why?

	```Java
    class A {
	  public int f(int x) {
		  return x;
	  }
	  public void f(int y) {}
	}
	```

24. Will the following code compile?  Why?

	```Java
    class A {
	  public void f(int x, String s) {}
	  public void f(String s, int y) {}
	}
	```

25. Will the following code compile? Why?

	```Java
    class A {
	  public void f(int x) {}
	  public void f(int y) throws IOException {}
	}
	```

26. Will the following code compile?  If so, what will be printed?

    ```Java
	class A {
	  private int x = 0;
	}

	class B extends A {
	  public void f() {
		System.out.println(x);
	  }
	}

	B b = new B();
	b.f();
	```

27. Will the following code compile?  If so, what will be printed?

    ```Java
	class A {
	  private int x = 0;
	}

	class B extends A {
	  public void f() {
		System.out.println(super.x);
	  }
	}

	B b = new B();
	b.f();
	```


28. Will the following code compile?  If so, what will be printed?

    ```Java
	class A {
	  protected int x = 0;
	}

	class B extends A {
	  public void f() {
		System.out.println(x);
	  }
	}

	B b = new B();
	b.f();
	```

29. Will the following code compile?  If so, what will be printed?

    ```Java
	class A {
	  protected int x = 0;
	}

	class B extends A {
	  public int x = 1;
	  public void f() {
		System.out.println(x);
	  }
	}

	B b = new B();
	b.f();
	```

30. Will the following code compile?  If so, what will be printed?

    ```Java
	class A {
	  protected int x = 0;
	}

	class B extends A {
	  public int x = 1;
	  public void f() {
		System.out.println(super.x);
	  }
	}

	B b = new B();
	b.f();
	```

39. Will the following code compile? If so, what will be printed?

    ```Java
    List<Integer> list = new ArrayList<>();
    int one = 1;
    Integer two = 2;

    list.add(one);
    list.add(two);
    list.add(3);

    for (Integer num : list) {
      System.out.println(num);
    }
    ```

40. Will the following code compile? If so, what will be printed?

    ```Java
    List<Integer> list = new ArrayList<>();
    int one = 1;
    Integer two = 2;

    list.add(one);
    list.add(two);
    list.add(3);

    for (int num : list) {
      System.out.println(num);
    }
    ```

41. Will the following code compile? If so, what will be printed?

    ```Java
    List<Integer> list = Arrays.asList(1, 2, 3);

    for (Double num : list) {
      System.out.println(num);
    }
    ```

42. Will the following code compile? If so, what will be printed?

    ```Java
    List<Integer> list = Arrays.asList(1, 2, 3);

    for (double num : list) {
      System.out.println(num);
    }
    ```

43. Will the following code compile? If so, what will be printed?

    ```Java
    double d = 5;
    int i = 2.5;

    System.out.println(d);
    System.out.println(i);
    ```

44. Will the following code compile? If so, what will be printed?

    ```Java
    double d = (int) 5;
    int i = (double) 2.5;

    System.out.println(d);
    System.out.println(i);
    ```

45. Will the following code compile? If so, what will be printed?

    ```Java
    double d = (int) 5.5;
    int i = (int) 2.5;

    System.out.println(d);
    System.out.println(i);
    ```

46. Will the following code compile? If so, what will be printed?

    ```Java
    Double d = 5;
    Integer i = 2.5;

    System.out.println(d);
    System.out.println(i);
    ```

47. Will the following code compile? If so, what will be printed?

    ```Java
    Double d = (double) 5;
    Integer i = (int) 2.5;

    System.out.println(d);
    System.out.println(i);
    ```

48. Will the following code compile? If so, what will be printed?

    ```Java
    double d = (Integer) 5;
    int i = (Integer) 2;

    System.out.println(d);
    System.out.println(i);
    ```

49. Will the following code compile? If so, what will be printed?

    ```Java
    double d = (Double) 5;
    int i = (Integer) 2;

    System.out.println(d);
    System.out.println(i);
    ```

50. Will the following code compile? If so, what will be printed?

    ```Java
    List<Integer> list = new LinkedList<>();
    list.add(5);
    list.add(4);
    list.add(3);
    list.add(2);
    list.add(1);

    Iterator<Integer> it = list.iterator();
    while (it.hasNext()) {
      System.out.println(it.next());
    }
    ```

51. Will the following code compile? If so, what will be printed?

    ```Java
    ArrayList<Integer> list = new ArrayList<>();
    list.add(5);
    list.add(4);
    list.add(3);
    list.add(2);
    list.add(1);

    Collections.sort(list);

    for (int i : list) {
      System.out.println(i);
    }
    ```

52. Will the following code compile? If so, what will be printed?

    ```Java
    List<Integer> list = Arrays.asList(1, 2, 4, 4, 5);

    Collections.sort(list, new Comparator<>() {
      @Override
      public int compare(Integer i1, Integer i2) {
        return -i1.compareTo(i2);
      }
    });

    list.forEach(System.out::println);
    ```

53. Will the following code compile? If so, what will be printed?

    ```Java
    Set<Integer> set = new HashSet<>(Arrays.asList(5, 2, 4, 1, 4, 2));

    set.forEach(System.out::println);
    ```

54. Will the following code compile? If so, what will be printed?

    ```Java
    Map<Integer, String> map = new HashMap<>();
    map.put(2, "world");
    map.put(2, "cs2030");
    map.put(1, "hello");

    for (Map.Entry<Integer, String> entry : map.entrySet()) {
      System.out.println(entry.getKey() + ": " + entry.getValue());
    }
    ```

55. Will the following code compile? If so, what will be printed?

    ```Java
    Map<Integer, String> map = new HashMap<>();
    map.put(1, "bell");
    map.put(2, "curve");
    map.put(9001, "god");

    map.forEach((k, v) -> System.out.println(k + ": " + v));
    ```

56. Will the following code compile? If so, what will be printed?

    ```Java
    Map<Integer, String> map = new HashMap<>();
    map.put(2, "bell");
    map.put(1, "curve");
    map.put(9001, "god");

    map.forEach((k, v) -> System.out.println(k + ": " + v));
    ```

57. Will the following code compile? If so, what will be printed?

    ```Java
    Map<Integer, String> map = new HashMap<>();
    map.put(10, "bell");
    map.put(1, "curve");
    map.put(9001, "god");

    map.forEach((k, v) -> System.out.println(k + ": " + v));
    ```
