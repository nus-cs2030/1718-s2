# CS2030 Java Style Guide

## Why Coding Style is Important

One of the goals of CS2030 is to move you away from the mindset that you are writing code that you will discard after it is done (e.g., in CS1010 labs) and you are writing code that noone else will read except you.  CS2030 prepares you to work in a software engineering teams in many ways, and one of the ways is to enforce a consistent coding style.

If everyone on the team follows the same style, the intend of the programmer can become clear (e.g., is this a class or a field?), the code is more readable and less bug prone (e.g., the [Apple `goto fail` bug](https://www.wired.com/2014/02/gotofail/)).  Empirical studies support this:

!!! quote
    "It is not merely a matter of aesthetics that programs should be written in a particular style. Rather there is a psychological basis for writing programs in a conventional manner: programmers have strong expectations that other programmers will follow these discourse rules. If the rules are violated, then the utility afforded by the expectations that programmers have built up over time is effectively nullified. The results from the experiments with novice and advanced student programmers and with professional programmers described in this paper provide clear support for these claims."

	Elliot Soloway and Kate Ehrlich. "Empirical studies of programming knowledge." IEEE Transactions on Software Engineering 5 (1984): 595-609.

Many major companies enforce coding styles, and some have published them.  For CS2030, we base our (simplified) coding style on [Google's Java Coding Style](https://google.github.io/styleguide/javaguide.html).  You should bookmark the link because you need to come back to it again and again.

## CS2030 Coding Style

1. No tab
	Use only whitespace.  

	For `vim` users, you can add the following line in your `~/.vimrc` file:
	```
	set expandtab
	```
	So that when you press <tab> it is expanded to whitespace.

	Most other source code editors have similar configuration.  

2. Exactly one blank line after import statements and exactly one top-level (i.e., non-nested) class.

3. Each top-level class resides in a source file of its own.

4. When a class has overladed methods (e.g., multiple constructors or methods of the same name), they appear sequentially with no other code in between.

5. Braces are always used (even if the body is empty or contains a single statement}

6. Use "Egyptian brackets": 

	- Opening brace have no line break before; but has line break after
	- Closing brace has a line break before; and has a line break after (except when there is `else` or comma following a closing brace.

	Example:
	```Java
	   if (x == 0) {
		 x++;
	   } 
	```

	is good.


	```Java
	   if (x == 0) { x++; }
	   if (x == 0) 
	   {
		 x++;
	   }
	   if (x == 0) 
	   {
		 x++; }
	```

	are not good.

7. Block indentation is exactly two spaces.

	```Java
	if (x == 0) { 
	  x++;
	  for (i = 0; i < x; i++) {
		x += i;
	  }
	}
	```

	For `vim` users, in `~/.vimrc`, add the following:
	```
	set tabstop=2
	set shiftwidth=2
	set autoindent
	set smartindent
	```

	To help you with indentation.
	
	Most other source code editors have similar configuration.  

8. Each statement is followed by a line break, no matter how short the statement is.

	```Java
	  x++; i++;
	```
	is bad.
	```Java
	  x++; 
	  i++;
	```
	is good.

9. Each line is limited to 80 characters in length.  You can break a long
line into multiple lines to enhance readability, this is called _line wrapping_.  When you do so, each continuation line is indented at least 4 spaces from the original line.

	```Java
	System.out.println("Daenerys of the House Targaryen, the First of Her Name, The Unburnt, Queen of the Andals, the Rhoynar and the First Men, Queen of Meereen, Khaleesi of the Great Grass Sea, Protector of the Realm, Lady Regnant of the Seven Kingdoms, Breaker of Chains and Mother of Dragon");
	```

	is bad.

	```Java
	System.out.println("Daenerys of the House Targaryen, the First of" +
	" Her Name, The Unburnt, Queen of the Andals, the Rhoynar and the" +
	" First Men, Queen of Meereen, Khaleesi of the Great Grass Sea, P" +
	"rotector of the Realm, Lady Regnant of the Seven Kingdoms, Break" +
	"er of Chains and Mother of Dragon");
	```

	is also bad.

	```Java
	System.out.println("Daenerys of the House Targaryen," + 
		"the First of Her Name, The Unburnt, Queen of the Andals," +
		"the Rhoynar and the First Men, Queen of Meereen," +
		"Khaleesi of the Great Grass Sea, Protector of the Realm," +
		"Lady Regnant of the Seven Kingdoms, Breaker of Chains and" +
		"Mother of Dragon");
	```
	is ok.

10. There should be a blank line between constructors, methods, nested classes and static initializers.  Blank lines can be used between fields to create logical groupings.

11. White space should separate Java keywords from parenthesis and braces, and be added on both sides of binary operators (`+`, `-`, `/`, etc) as well as `:` in enhanced for.  Space should also appears before and after `//` comments

	```Java
	if(x==0){ 
	  x++;//to make sure x is at least one.
	  for(i=0;i<x;i++){
		x+=i;
	  }
	}
	```

	is bad.

	```Java
	if (x == 0) { 
	  x++; // to make sure x is at least one.
	  for (i = 0; i < x; i++) {
		x += i;
	  }
	}
	```

	is good.

12. One variable per declaration.  

	```Java
	int x, y;
	```

	bad.

	```Java
	int x;
	int y;
	```

	good!

13. No C-style array declaration

	```Java
	String args[];
	```

	not good.

	```Java
	String[] args;
	```

	good!


14. Switch statement always include a `default` case.

15. One annotation per line.  Always use `@Override`.

	```Java
	@Override
	public boolean equals(Object o) {
	  :
	}
	```

16. Indent comments at the same level as the surrounding code.  For multiple comments, align `*` with the previous line.

	```Java
	/*
	* Not a good style
	*/
	/*
	 * Good style
	 */
	```

17. Class modifier appears in the following order:

	```Java
	public protected private abstract default static final transient volatile synchronized native strictfp
	```

	Example:
	```Java
	static public void main(String[] args)
	```
	is bad.
	```Java
	public static void main(String[] args)
	```
	is good!

18. Class names are writte in UpperCamelCase, method names and field names in lowerCamelCase, constant names in ALL_CAPS_SNAKE_CASE.  Type parameters in single capital letter.

19. Caught exceptions should not be ignored.

20. Static fields and methods must be accessed with class name.
