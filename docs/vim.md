# Vim Tips

I collected below some tips on `vim` that I find helpful.

## Configuration

You can configure your `vim` by putting your configuration options and scripts in the `~/.vimrc` file (a hidden file named `.vimrc` in your home directory).  This file will be loaded whenever you starts `vim`.

## Help

In `vim,` the command `:help <topic>` shows help about a particular topic in `vim`.  Example, `:help backup`.

## Backup Files

You can ask `vim` to automatically backup files that you edit.  This has been a life saver for me in multiple  occasions.

In your `~/.vimrc` file, 

```
set backup=on
```

will cause a copy of your file to be save with suffix `~` appended to its name everytime you save.

I prefer not to clutter my working directory, so I set

```
set backupdir=~/.backup
```

and create a directory named `~/.backup` to store my backup files.

The settings above are the default in your `cs2030-i` account.  So if you made changes to a file that you regreted on `cs2030-i`, or if accidentally deleted a file, you can check under `~/.backup` to see if the backup can save you.

## Undo

Since we are on the topic of correcting mistakes, `u` in command mode undo your changes.  Prefix it with a number $n$ to undo $n$ times.  If you regreted your undo, `<CTRL-R>` will redo.

## Syntax Highlighting

If for some reasons, syntax highlighting is not on by default, add this to your `~/.vimrc`:

```
syntax on
```

## Ruler and Numbers

If you prefer to show the line number you are on and the column number you are on, adding the commands to `~/.vimrc`

```
set ruler
```

will display the line number and the column number on the lower right corner.  

You can also add
```
set number
```

to label each line with a line number.

## Jumping to a Line

If the compiler tells you there is an error on Line $x$, you can issue `:<x>` to jump to Line $x$.  For instance, `:40` will go to Line 40.

## Navigation

- `w`   jump to the beginning of the next word
- `b`   jump to the beginning of the previous word (reverse of `w`)
- `e`   jump to the end of the word (or next word when pressed again)
- `f` + char: search forward in the line and sit on the next matching char
- `t` + char:  search forward in the line and sit on one space before the matching char
- <CTRL-d> jump forward half page
- <CTRL-u> jump backward half page
- `$` jump to end of line
- `0` jump to the beginning of the line
- `%` jump between matching parentheses

## Navigation + Editing

`vim` is powerful because you can combine _operations_ with _navigation_.  For instance `c` to change, `d` to delete, `y` to yank (copy).  Since `w` is the navigation command to move over the current word, combining them we get:

- `cw` change the current word (delete the current word and enter insert mode)
- `dw` delete the current word
- `yw` yank the current word (copy word into buffer)

Can you guess what `df)`, `dt)`, `c$`, `y0` do?

If you repeat the operation `c`, `d`, and `y`, it applies to the whole line, so:

- `cc` change the whole line
- `dd` delete the whole line
- `yy` yank the whole line

You can add a number before an operation to specify how many times you want to repeat an operation.  So `5dd` deletes 5 lines, `5dw` deletes 5 words, etc.

See the article [Operator, the True Power of `Vim`](http://whileimautomaton.net/2008/11/vimm3/operator) for more details.

## Other Editing Operations

- `A` jump to end of line and enter insert mode
- `o` open next line and enter insert mode
- `O` open previous line and enter insert mode

## Search and Replace in `vim`


```
:%s/oldWord/newWord/gc 
```

`:` enters the command mode.  `%` means apply to the whole document, `s` means substitute, `g` means global (otherwise, only the first occurance of each line is replaced). `c` is optional -- adding it cause `vim` to confirm with you before each replacement  

## Commenting blocks of code

Sometimes we need to comment out a whole block of code in Java for testing purposes. There are several ways to do it in `vim`:

- Place the cursor on the first line of the block of code you want to comment.
- `0` to jump to the beginning of the line
- `<CTRL-v>` enter visual block mode
- Use arrow key to select the block of code you want to comment. 
- `I` to insert at the beginning of the line (here, since we already selected the block, we will insert at the beginning of every selected)
- `//` to insert the Java comment character (you will see it inserted in the current line, but don't worry)
- `<ESC> <ESC>` to escape from the visual code and to insert the comment character for the rest of the lines.

To uncomment, 

- Place the cursor on the first line of the block of code you want to comment.
- `0` to jump to the beginning of the line
- `<CTRL-v>` enter block visual mode
- Use arrow key to select the columns of text containing `//`
- `x` to delete them

## Shell Command

If you need to issue a shell command quickly, you don't have to exit `vim`, run the command, and launch `vim` again.  You can use `!`, 

```
:!<command>
```

will issue the command to shell.  E.g.,

```
:!ls
```

You can use this to compile your current file, without exiting `vim`.

```
:!javac %
```

## Abbreviation

You can use the command `ab` to abbreviate frequently typed commands.  E.g., in your `~/.vimrc`, 

```
ab Sop System.out.println("
```

Now, when you type `Sop `, it will be expanded into `System.out.println(" `

## Auto-Completion

You can `<CTRL-P>` to auto-complete.  By default, the auto-complete dictionary is based on text in your current editing buffers.  This is a very useful keystroke saver for long function and variable names.

## Auto-Indent the Whole File

You can `gg=G` in command mode to auto-indent the whole file.  `gg` is the command to go to the beginning of the file.  `=` is the command to indent.  `G` is the command to go to the end of the file.

## Swapping Lines

Sometimes you want to swap the order of two lines of code, in command mode, `ddp` will do the trick.  `dd` deletes the current line, `p` paste it after the current line, in effect swapping the order of the two lines.

## Goto File

Place your cursor on the name of a class (e.g., `Event`), then in command mode, issue the `gf` command (goto file).  `vim` will open `Event.java`.  You can set the `path` to load files from directories other than the current directory.   "Ctrl-^" will get out and back to the previous file.

## Syntax and Style Checker

I use `syntastic` to check for style and syntax whenever I save a file.  [`syntastic`](https://github.com/vim-syntastic/syntastic) is a `vim` plugin. 

My `.vimrc` configuration file contains the following:

```
"For syntastic
set laststatus=2
set statusline+=%#warningmsg#
set statusline+=%{SyntasticStatuslineFlag()}
set statusline+=%*

let g:syntastic_always_populate_loc_list = 1
let g:syntastic_auto_loc_list = 1
let g:syntastic_check_on_open = 1
let g:syntastic_check_on_wq = 0
let g:syntastic_java_checkers = [ "checkstyle", "javac" ]
let g:syntastic_java_checkstyle_classpath = "~cs2030/bin/checkstyle-8.2-all.jar"
let g:syntastic_java_checkstyle_conf_file = "~cs2030/bin/cs2030_checks.xml"
```

The last two lines refer to [`checkstyle`](http://checkstyle.sourceforge.net) tool and its configuration file available from `~cs2030/bin` on the host `cs2030-i.comp.nus.edu.sg`.

## Splitting `vim`'s Viewport

- `:sp file.java` splits the `vim` window horizontally
- `:vsp file.java` splits the `vim` window vertically
- `Ctrl-w Ctrl-w` moves between the different `vim` viewports
