# MyCLI

## Welcome! This repository contains my implementation of a CLI with a faboulous purpose: reverse text, and replace character occurences!

The first CLI command, **reverse**, takes as input a text file and reverses it's content, basically writing it backwards.
The other command, **replace**, allows to replace any character in the input file by another.



## Getting started

To build this project, clone this repository, and build it inside the directory with the following terminal command:
```
mvn clean package
```
provided if you have maven installed.


This will create a .jar file, which you can then run with the following command (assuming you want to use the reverse command): 
```
java -jar target/ReverseReplace.jar -i <input_file_path> -o <output_file_path> -ie
 <input_encoding> -oe <output_encoding> reverse
```

Please not that the -i parameter is mandatory, while the other ones are optional. If you are not sure as to how to use the command, run it with -h.

For the **replace** command, the syntax is as follows:
```
java -jar java -jar target/ReverseReplace.jar -i <input_file_path> -o <output_file_path> -ie
 <input_encoding> -oe <output_encoding> replace <char_to_replace> <char_to_replace_by>
```

### Going further

Clearly the command is cumbersome to run under the current formulation. However, you may simplify it's usage via a simple trick in the terminal:
```
alias reverse_replace='java -jar <PATH_TO_.JAR_FILE>'
```
where your replace <PATH_TO_.JAR_FILE> by the actual path to the .jar file generated upon building.

Now you will be able to run the CLI simply by using the **reverse_replace** command, as such for example:
```
reverse_replace -i input.txt -o output.txt reverse
```
To reverse a text given in file input.txt, or
```
reverse_replace -i input.txt -o output.txt replace a b
```
To replace all occurences of the character 'a' by 'b' in input.txt

MORE GENERALLY, all input/output files and encoding schemes are given via options (-i, -o, -ie, -oe), while characters for the replace command are given via parameters, so those must go after the command name in the terminal.


## Testing

This repository provides a file called test_input.txt that contains text that is not necessarily recognized by all Character encoding formats. Feel free to play around with it, or simply create your own text file and pass it to the command via -i.

This repository also includes a pre-compiled .jar of the CLI, in case you encounter issues with the whole maven stuff. Just use the .jar file in /target to test the command if you encounter any problems.





