# Java: Install/Compile/Run 

## Java Development Kit (JDK)

The Java Development Kit, or JDK, is a development environment for building Java applications.  The environment provides a virtual machine to execute compiled Java code (JVM), a collection of classes and libraries, and a set of tools to support development (including a compiler (`javac`), a debugger (`jdb`), an interactive shell (`jshell`)) etc.

There are several variations of JDK available.  For instance, [OpenJDK](http://openjdk.java.net) is a free and open source version of JDK.  [GNU](http://www.gnu.org) offers a compiler in Java ([`gcj`](https://en.wikipedia.org/wiki/GNU_Compiler_for_Java)) and Java core libraries in Gnu classpath.
Eclipse offers its own version of Java compiler[^1].  These variations are mostly the same, but for the purpose of this module, we will use the [official Oracle version](http://www.oracle.com/technetwork/java/index.html).

[^1]: See: [What is the difference between javac and the Eclipse compiler?](https://stackoverflow.com/questions/3061654/what-is-the-difference-between-javac-and-the-eclipse-compiler)

There are different editions of Java.  The main ones are Java SE (standard edition), Java EE (enterprise edition), and Java ME (micro edition).  We will be using [Java SE](http://www.oracle.com/technetwork/java/javase/overview/index.html).

The latest version of Java SE is Java 8 (equivalent to version 1.8).
This is also the earliest version of Java that will work with this module, as many concepts we will cover are only introduced in Java 8.  

To use `jshell`, however, you need Java 9, which you can [download and install from here](http://jdk.java.net/9/).

## Installing JDK 9

There are multiple ways to setup JDK on various OS.  The following seems to be the simplest possible way to get started.

### On macOS

Download the `.dmg` file corresponding to JDK 9 from http://jdk.java.net/9/ and double click the `.dmg` file.  Follow instruction from there.

For a 6-step description of the above, see [the installation instruction from Oracle.](https://docs.oracle.com/javase/9/install/installation-jdk-and-jre-macos.htm#JSJIG-GUID-F575EB4A-70D3-4AB4-A20E-DBE95171AB5F)

### On Ubuntu / Linux Mint / Debian

We are going to use `apt`, a package management program, in Linux to install JDK.  Run the following in your terminal:

```
sudo add-apt-repository ppa:webupd8team/java
sudo apt-get update
sudo apt-get install oracle-java9-installer
```

Line 1 above adds a special package repository (also known as Personal Package Archive (PPA)) to `apt`.  Line 2 instructs `apt` to download the list of packages available from all repositories configured.  One of this packages should be `oracle-java9-installer`.  Line 3 installs the `oracle-java9-installer`.  Note that this install the installer, not Java 9.  After the installer is installed, the installer is executed.  You will be asked questions about license agreements, and then the installer proceeds to download and install Java 9.

For a more detailed instructions, see [the guide from the maintainer of the PPA, webupd8.org](http://www.webupd8.org/2015/02/install-oracle-java-9-in-ubuntu-linux.html). 

### On Windows 10

Follow the [instructions to install Bash on Ubuntu on Windows](https://www.howtogeek.com/249966/how-to-install-and-use-the-linux-bash-shell-on-windows-10/), then install as per the instructions for Ubuntu above.

### Other Methods

Oracle publishes an [installation guide](https://docs.oracle.com/javase/9/install/toc.htm) for JDK 9, including installing it on native Windows (not through Ubuntu emulator).  I find it much more troublesome and complicated.  Unfortunately, if you are using an earlier version of Windows, you will have to follow these instructions.

## Compiling

Coming soon.
