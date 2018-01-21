# Accessing CS2030 Lab Programming Environment

## The Environment

The school has created a VM (virtual machine) for CS2030, with hostname `cs2030-i.comp.nus.edu.sg`.  The VM is running CentOS, one of the Linux distributions.
This will be the official programming environment for CS2030 for all your lab assignments.

I have created your accounts on the VM based on your SoC UNIX account.  You can login with your SoC UNIX username (not your NUSNET username, unless you intentionally set the two to be the same) and password.

## SoC VPN

THe VM can only be accessed from within the School of Computing networks.  If you want to access it from outside, you need to setup a Virtual Private Network (VPN) (See [instruction here](https://docs.comp.nus.edu.sg/node/5065)).  The staff at `helpdesk@comp.nus.edu.sg` or the IT helpdesk in COM1, Level 1, will be able to help with you setting up if needed.

## Tunneling through Sunfire

Alternatively, you can also tunnel through SoC's Sunfire. Sunfire is configured to allow your connection if it's originating from a local telco. (See [more details here](https://docs.comp.nus.edu.sg/node/1824)). 
Connect to sunfire `sunfire.comp.nus.edu.sg` via your favourite terminal. After logging in, run the command `ssh cs2030-i` to connect to the CS2030 VM. Refer to instructions below on how to connect via SSH.

## SSH

### For UNIX-based OS

If you use either macOS, Windows 10 (requires [Linux Subsystem on Windows](https://docs.microsoft.com/en-us/windows/wsl/install-win10)), or Linux, you should have the command line `ssh` installed.  

Run:
```
ssh <username>@cs2030-i.comp.nus.edu.sg
```

Replace `<username>` with your SoC UNIX username, for instance, I would do:
```
ssh ooiwt@cs2030-i.comp.nus.edu.sg
```

After the command above, following the instructions on screen.  The first time you ever connect to `cs2030-i.comp.nus.edu.sg`, you will be warned that you are connecting to a previously unknown host.  Said `yes`, and you will be prompted with your SoC UNIX password.

### For Windows 7 or 8 (or Windows 10 without Linux Subsystem)

The desktop computers in Programming Lab 6 (PL6) runs Windows 7.  If you are using these computers, or your own computers with older versions of Windows, you need to use programs like [`PuTTY`](https://www.chiark.greenend.org.uk/~sgtatham/putty/latest.html) to access the VM.


## Basic UNIX Commands

Once you logged into the VM, you will be prompted to enter a command with a prompt that looks like this:
```
happytan@cs2030-i:~[xxx]$
```
This interface is provided by a UNIX shell -- not unlike `jshell`, this shell sits in a loop and wait for users to enter a command, then it interprets and executes the command.  There are many versions of shells, the default shell for our VM is `bash`[^1].

[^1]: I run `fish` on my macOS, as you might have noticed during the in-class demos.  You can use any shell you like, if you know what you are doing.  Otherwise, `bash` is a popular one.

_The following are adapted for CS2030 from [the instructions created by Aaron Tan](http://www.comp.nus.edu.sg/~cs1020/4_misc/cs1010_lect/2014/intro_lab/gettingStarted.html). Bugs are mine._  

The power of UNIX stems from the many commands it offers. The following are a few commonly used commands. This list is by no means exhaustive and you are urged to explore on your own. Note that UNIX commands are case-sensitive.

In the examples below, bold words are commands which you are expected to enter. All commands are to be entered after the UNIX (local or `sunfire` or `cs2030-i`) prompt of the form

```
happytan@cs2030-i:~[xxx]$
```

`~` indicates that you are currently in your home directory, `xxx` is a number indicating the number of commands that have been entered.  The following examples assumes that user `happytan` is logged into cs2030-i; however you can do it on your local UNIX platform too.

It might be good to understand the directory structure in UNIX, a multi-user system. The directory tree is shown below:

![UNIX Directory Tree](figures/unix_directory_tree.jpg)

Each user has his/her own home directory, which is where he/she will be automatically placed when he/she logs into the system. The above figure shows where the home directory of user `happytan` resides in the directory tree. The user `happytan` may create files or directories in his/her home directory, but not elsewhere unless permission is given.

### Directory commands

- `pwd`: Print current Working Directory to show you which directory you are currently in
```
happytan@cs2030-i:~[xxx]$ pwd
/home/h/happytan
```

- `ls`: LiSt files in your current directory
```
happytan@cs2030-i:~[xxx]$ ls
happytan@cs2030-i:~[xxx]$
```
   If you do not have any regular files in your home directory, as you should when you first login, you should immediately return to the shell prompt.  

!!! note "Rule of Silence"
        UNIX follows the _rule of silence_: programs should not print unnecessary output, to allow other programs and users to easily parse the output from one program.  So, if `ls` has nothing to list, it will list nothing (as oppose to, say, printing "This is an empty directory.")

- `mkdir`: MaKe a subDIRectory in current directory
```									 	
happytan@cs2030-i:~[xxx]$ mkdir lab01
happytan@cs2030-i:~[xxx]$ ls
lab01
happytan@cs2030-i:~[xxx]$ ls -F
lab01/
```
Here, you create a directory called `lab01`.  Now, when you `ls`, you can see the directory listed.
You may also use `ls -F` for more information (`-F` is one of the many _options_/_flags_ available for the `ls` command. To see a complete list of the options, refer to the man pages, i.e., `man ls`.)

    The slash `/` beside the filename tells you that the file is a directory (aka folder in Windows lingo). A normal file does not have a slash beside its name when "ls -F" is used.

    You may also use the `ls -l` command (hyphen el, not hyphen one) to display almost all the file information, include the size of the file and the date of modification.

!!! tip "Command history"
    UNIX maintains a history of your previously executed UNIX commands, and you may use the up and down arrows to go through it. Press the up arrow until you find a previously executed UNIX command. You may then press Enter to execute it, or edit the command before executing it. This is handy when you need to repeatedly executed a long UNIX command.

- `cd`:	Change Directory from current directory to another
```
happytan@cs2030-i:~[xxx]$ cd lab01
happytan@cs2030-i:~/lab01[xxx]$
```
Note that the prompt changes to `~/lab01` to indicate that you are now in the `lab01` directory below your `HOME` directory.

    Entering `cd` alone brings you back to your `HOME` directory, i.e.,. the directory in which you started with when you first logged into the system.
```
happytan@cs2030-i:~/lab01[xxx]$ cd
happytan@cs2030-i:~[xxx]$
```

- `rmdir`:	to ReMove a subDIRectory in current directory -- note that a directory must be empty before it can be removed.
```
happytan@cs2030-i:~[xxx]$ rmdir lab01
happytan@cs2030-i:~[xxx]$ ls -F
happytan@cs2030-i:~[xxx]$ mkdir lab01
happytan@cs2030-i:~[xxx]$ ls -F
lab01/
```

### File commands
- `cp`:  CoPy files
```
happytan@cs2030-i:~/lab01[xxx]$ cp ~cs2030/lab01/Circle.java .
happytan@cs2030-i:~/lab01[xxx]$ ls
Circle.java
```
The command above copy the files Circle.java from the HOME of user `cs2030`, under directory `lab01`, to the current directory.

If you want to copy the whole directory, use `-r` flag, where `r` stands for recursive copy.

```
happytan@cs2030-i:~/lab01[xxx]$ cp -r ~cs2030/lab01 .
```

The directory `lab01` and everything under it will be copied.

- `mv`:	MoVe files from one directory to another; can also be used to rename files.
```
happytan@cs2030-i:~/lab01[xxx]$ mv Circle.java Test.java
happytan@cs2030-i:~/lab01[xxx]$ ls
Test.java
```

!!! tip "Filename completion"
    If you have a very long file name, you may use UNIX's filename completion feature to reduce typing. For instance, you may type:
    ```
    happytan@cs2030-i:~/lab01[xxx]$ mv C
    ```
    and press the tab key, and UNIX will complete the filename for you if there is only one filename with the prefix "C". Otherwise, it will fill up the filename up to point where you need to type in more characters for disambiguation.


- `rm`: ReMove files. Be careful with this command -- files deleted cannot be restored (unless they have been backed up during the normal backup cycle).
```
happytan@cs2030-i:~/lab01[xxx]$ rm Test.java
rm: remove 'Test.java'? y
happytan@cs2030-i:~/lab01[xxx]$ ls
happytan@cs2030-i:~/lab01[xxx]$
```

### Command to display text files
- `cat`: to string together or display (CATenate) the contents of files onto the screen
```
happytan@cs2030-i:~/lab01[xxx]$ cat Circle.java
```
- `less` - variant of `cat` (includes features to read each page leisurely)
```
happytan@cs2030-i:~/lab01[xxx]$ less Circle.java
```
In `less`, use `<space>` to move down one page, `b` to move Back up one page, and `q` to Quit from "less".

An online help facility is available in UNIX via the `man` command (`man` stands for MANual). To look for more information about any UNIX command, for example, `ls`, type `man ls`. Type `man man` and refer to Man Pages to find out more about the facility. To exit `man`, press `q`.

Now that you are familiar with how the UNIX shell works, I won't show the command prompt any more in the rest of this article.

### UNIX File Permission
It is important to guide our files properly on a multi-user system where users share the same file system.  UNIX has a simple mechanism to for ensuring that: every file and directory has nine bits of access permission, corresponds to three access operations, read (`r`), write (`w`), and execute (`x`), for four classes of users, the user who owns of the file (`u`), users in the same group as the owner (`g`), all other users (`o`), and all users (`a`) (union of all three classes before)

When you run `ls -l`, you will see the permission encoded as strings that look like `-rw-------` or `drwx--x--x` besides other file information.   

- The first character indicates if the file is a directory (`d`) or not (`-`).  
- The next three characters are the permission for the owner.  `rwx` means that the owner can do all three: reading, writing, and executing, `rw-` means that the owner can read and write, but cannot execute.
- The next three characters are the permission for the users in the same group.
- The last three characters are the permission for the users in the other groups.

To change permission, we use the `chmod` command.  Let's say that we want to remove the read and write permission from all other users in the group.  You can run:
```
chmod g-rw <file>
```

where `<file>` is the name of the file whose permission you want to change.  This would change the permission from `-rw-rw-rw-` to `-rw----rw-`, or from `-rwxr--r--` to `-rwx---r--`.

To add executable permission to everyone, you can run:
```
chmod a+x <file>
```

This would change the permission from `-rw-rw-rw-` to `-rwx--xrwx`, or from `-rwxr--r--` to `-rwx--xr-x`, and so on.  You get the idea.

Another way to change the permission is set the permission directly, instead of adding with `+` and removing with `-`.  To do this, one convenient way is to treat the permission for each class of user as a 3-bit binary number between 0 to 7.  So, `rwx` is 7, `rw-` is 6, `-w-` is 2, `---` is 0, etc.  

To set the permission of a file to `-r--r--r--` (readable by everyone), run:
```
chmod 444 <file>
```

To set the permission to `-rw-------`, run:
```
chmod 600 <file>
```

and so on.

It is important to ensure that your code is not readable and writable by other students, especially for graded lab exercises.

### Secure Copy (`scp`)

Secure copy, or `scp`, is one way to transfer files from your local computer to `cs2030-i`.  If you choose not to use `emacs` or `vim`[^2] and write your code on `cs2030-i`, you can write your code on your local computer, and transfer them.  Let's say that you are in the directory with a bunch of java files you want to transfer, and you want them transferred into directory `test` that you have created, do the following:

```
scp *.java happytan@cs2030-i:~/test
```

!!! warning
    If you have files with the same name in the remote directory, the files will be overwritten without warning.  I have lost my code a few times due to `scp`.  

The expression `*.java` is a regular expression that means all files with filename ending with `.java`.  You can copy specific files as well.  For instance,

```shell
scp Circle.java Point.java happytan@cs2030-i:~/test
```

`scp` supports `-r` (recursive copy) as well.

### Setting up SSH Keys

Once you are comfortable with UNIX, you can set up a pair of public/private keys for authentication.  

You can use
```
ssh-keygen -t rsa
```

to generate a pair of keys on your local computer.  Keep the private key `id_rsa` on your local machine in the hidden `~/.ssh` directory, and copy the public key `id_rsa.pub` to your home directory on VM `cs2030-i`.  On `cs2030-i`, run
```
cat id_rsa.pub >> ~/.ssh/authorized_keys
```

Make sure that the permission for `.ssh` both on local machine and on VM is set to `700` and the files `id_rsa` on local machine and `authorized_keys` on remote machine is set to `600`.  Once setup, you need not enter your password every time you run `ssh` or `scp`.  


[^2]: My personal opinion is that, you should really master one of these two time-tested source code editor if you want a career in software development.
