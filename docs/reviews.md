# Review Questions

These set of questions are designed to illustrate certain rules and principles about Java.
You can easily find out the answer yourselves using `jshell` or write small programs.

It is more important to understand why -- there are some underlying principles/rules in Java that cause the compiler / code to behave the way it does.

Note that the code is terse by design, and is never meant to be a positive example of good Java code.

1. Can, or not?

    ```Java
    int i;
    double d;
    i = d;
    d = i;
    i = (int) d;
    d = (double) i;
    ```

2. Can, or not?

    ```Java
    int i;
    boolean b;
    i = b;
    b = i;
    i = (int) b;
    b = (boolean) i;
    ```

3. Can, or not?

    ```Java
    class A {
    }

    class B extends A {
    }

    A a = new B();
    B b = new A();
    ```

4. Can, or not?

    ```Java
    class A {
    }

    class B extends A {
    }

    A a = new A();
    B b = new B();
    b = (B)a;
    a = (A)b;
    ```

5. Can, or not?

    ```Java
    interface I {
    }

    class A implements I {
    }

    I i1 = new I();
    I i2 = new A();
    A a1 = i2;
    A a2 = (A)i2;
    ```

6. Can, or not?

    ```Java
    interface I {
    }

    interface J extends I {
    }

    class A implements J {
    }

    A a = new A();
    I i = a;
    J j = a;
    i = j;
    j = i;
    j = (J)i;
    a = i;
    a = j;
    a = (A)i;
    a = (A)j;
    ```

7. Can, or not?

    ```Java
    interface I {
    }

    interface J {
    }

    class A implements I, J {
    }

    A a = new A();
    I i = a;
    J j = a;
    i = j;
    j = i;
    j = (J)i;
    I = (I)j;
    a = i;
    a = j;
    a = (A)i;
    a = (A)j;
    ```

8. Can, or not?

    ```Java
    class A {
    }

    class B extends A {
    }

    class C extends A {
    }

    B b = new B();
    A a = b;
    C c = b;
    A a = (A)b;
    C c = (C)b;
    ```

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

17. Can, or not?  If can, what will be printed?

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
