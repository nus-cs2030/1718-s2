# Java: Install/Compile/Run

## Java Development Kit (JDK)

The Java Development Kit, or JDK, is a development environment for building Java applications.  The environment provides a virtual machine to execute compiled Java code (JVM), a collection of classes and libraries, and a set of tools to support development (including a compiler (`javac`), a debugger (`jdb`), an interactive shell (`jshell`)) etc.

There are several variations of JDK available.  For instance, [OpenJDK](http://openjdk.java.net) is a free and open source version of JDK.  [GNU](http://www.gnu.org) offers a compiler in Java ([`gcj`](https://en.wikipedia.org/wiki/GNU_Compiler_for_Java)) and Java core libraries in Gnu classpath.
Eclipse offers its own version of Java compiler[^1].  These variations are mostly the same, but for the purpose of this module, we will use the [official Oracle version](http://www.oracle.com/technetwork/java/index.html).

[^1]: See: [What is the difference between javac and the Eclipse compiler?](https://stackoverflow.com/questions/3061654/what-is-the-difference-between-javac-and-the-eclipse-compiler)

There are different editions of Java.  The main ones are Java SE (standard edition), Java EE (enterprise edition), and Java ME (micro edition).  We will be using [Java SE](http://www.oracle.com/technetwork/java/javase/overview/index.html).

The latest version of Java SE is Java 9.0.1.
Java 8 is the earliest version of Java that will work with this module, as many concepts we will cover are only introduced in Java 8.  
To use `jshell`, however, you need Java 9.

## Installing JDK or Java SE 9

You can [download the latest version of Java SE 9](http://www.oracle.com/technetwork/java/javase/downloads/index.html) from Oracle and follow its [installation instructions](https://docs.oracle.com/javase/9/install/overview-jdk-9-and-jre-9-installation.htm#JSJIG-GUID-8677A77F-231A-40F7-98B9-1FD0B48C346A).

## Compiling

Now that you've installed Java on your machine, here's an example of how you can compile and run some Java code.

### Java source files

Create a new Java source file and put it in a new folder (e.g. `CS2030`).

```java
class HelloWorld {
  public static void main(String[] args) {
    System.out.println("Hello, world!");
  }
}
```

By convention, the file should be named `HelloWorld.java`, following the _UpperCamelCase_ name of the class. At this point, our `CS2030` folder only contains that one file.

```
CS2030 $ ls
HelloWorld.java
```

### Java class files

We can go ahead and compile our Java program by running the `javac HelloWorld.java` command. This creates the corresponding Java class file, `HelloWorld.class`.

```
CS2030 $ javac HelloWorld.java
CS2030 $ ls
HelloWorld.class HelloWorld.java
```

We can now execute it with `java HelloWorld`. Remember to omit the `.class` extension when doing this!

```
CS2030 $ javac HelloWorld.java
CS2030 $ java HelloWorld
Hello, world!
```

Success! 🎉

!!! note "What actually happens under the hood? Is Java an interpreted or compiled language?"
    This can get a little mind-boggling at first, but this [diagram](https://stackoverflow.com/questions/1326071/is-java-a-compiled-or-an-interpreted-programming-language/36394113#36394113) summarizes it quite well.
