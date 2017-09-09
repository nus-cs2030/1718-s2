# Review Questions

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
