package HEIG;

import picocli.CommandLine;
import picocli.CommandLine.Command;
import picocli.CommandLine.Option;
import picocli.CommandLine.Parameters;

import java.io.*;
import java.math.BigInteger;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.security.MessageDigest;
import java.util.concurrent.Callable;


//This class implements a picocli command that reverses the words of an input file and writes the output to
//an output file.

@Command (name = "reverse", mixinStandardHelpOptions = true, version = "reverseWords 1.0",
        description = "Reverses the words of an input file and writes the output to an output file.")
public class ReverseWords implements Callable<Integer> {

    @Option(names = {"-i"}, description = "Input file path", required = true)
    private File inputFile;

    @Option(names = {"-o"}, description = "Output file path", required = true)
    private File outputFile;

    @Option(names = {"-ie"}, description = "Input file encoding, default UTF-8")
    private String inputEncoding = "UTF-8";

    @Option(names = {"-oe"}, description = "Output file encoding, default UTF-8")
    private String outputEncoding = "UTF-8";


    //This method reverses the words of an input file and writes the output to an output file.
    //It is called when the command is executed.
    @Override
    public Integer call() throws Exception {
        String input = "";

        if(inputFile == null && outputFile == null){
            System.out.println("Please enter a valid input and output file, use -h for help.");
            return 1;
        }

        if(inputFile != null) {
            //Read the input file using a BufferedReader and FileReader
            BufferedReader reader = new BufferedReader(new FileReader(inputFile, Charset.forName(inputEncoding)));
            //Read the input file line by line
            String line = reader.readLine();
            input = "";
            while (line != null) {
                input += line + "\n";
                line = reader.readLine();
            }
            reader.close();
            System.out.println("Input file read successfully.");
        }else {
            System.out.println("Please enter a valid input file, use -i option.");
            return 1;
        }

        //Reverse the words of the input file
        String output = reverseWords(input);

        //Write the output to the output file using a buffered writer
        if(outputFile != null) {
            BufferedWriter writer = new BufferedWriter(new FileWriter(outputFile, Charset.forName(outputEncoding)));
            writer.write(output);
            writer.close();
            System.out.println("Output file written successfully.");
        }else {
            System.out.println("No valid output file specified, printing to console. To avoid this use -o option.");
            System.out.println(output);
        }
        return 0;
    }

    //This method reverses the letter order of a word
    private String reverseWord(String word) {
        String reversedWord = "";
        for (int i = word.length() - 1; i >= 0; i--) {
            reversedWord += word.charAt(i);
        }
        return reversedWord;
    }

    //This method reverses all the words of a text as well as the order of the words
    private String reverseWords(String text) {
        String reversedText = "";
        String[] words = text.split("\\s+");
        for (int i = words.length - 1; i >= 0; i--) {
            reversedText += reverseWord(words[i]) + " ";
        }
        return reversedText;
    }



    public static void main(String... args) {
        int exitCode = new CommandLine(new ReverseWords()).execute(args);
        System.exit(exitCode);
    }


}
