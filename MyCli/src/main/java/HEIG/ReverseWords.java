/**
 * Author: Sergey Komarov, sergey.komarov@heig-vd.ch
 * Course: DAI
 */
package HEIG;
import picocli.CommandLine;
import picocli.CommandLine.Command;
import picocli.CommandLine.Option;
import picocli.CommandLine.Parameters;

import java.io.*;
import java.math.BigInteger;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.StandardOpenOption;
import java.security.MessageDigest;
import java.util.concurrent.Callable;


@Command (name = "reverse_replace", mixinStandardHelpOptions = true, version = "reverseWords 1.0",
        description = "Reverses the words of an input file and writes the output to an output file with the reverse command, or " +
                "replaces all occurences of a character by another in an input file and writes the output to an output file with the replace command.")
public class ReverseWords implements Callable<Integer> {

    /**
     * HERE COMES THE OPTIONS BLOCK
     */

    @Option(names = {"-i"}, description = "Input file path", required = true)
    private File inputFile;

    @Option(names = {"-o"}, description = "Output file path", required = false)
    private File outputFile;

    @Option(names = {"-ie"}, description = "Input file encoding, default UTF-8")
    private String inputEncoding = "UTF-8";

    @Option(names = {"-oe"}, description = "Output file encoding, default UTF-8")
    private String outputEncoding = "UTF-8";





    @Command(name = "replace", mixinStandardHelpOptions = true, version = "replace 1.0",
            description = "Replaces all occurences of a character by another in an input file and writes the output to an output file.")
    public Integer replace(@Parameters(index = "0", description = "Character to replace") String charToReplace,
                          @Parameters(index = "1", description = "Character to replace with") String charToReplaceWith) throws IOException {

        if(charToReplace.length() != 1 || charToReplaceWith.length() != 1){
            System.out.println("Please enter valid characters to replace and replace with.");
            return 1;
        }

        String input = "";
        if(inputFile == null && outputFile == null){
            System.out.println("Please enter a valid input and output file, use -h for help.");
            return 1;
        }

        if(inputFile != null && inputFile.exists()) {
            //Read the input file
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

        String output = replaceChar(input, charToReplace.charAt(0), charToReplaceWith.charAt(0));

        //Write the output to the output file using a buffered writer
        //If there is a valid output file:
        if(outputFile != null) {
            BufferedWriter writer = new BufferedWriter(new FileWriter(outputFile, Charset.forName(outputEncoding))) ;
            writer.write(output);
            writer.close();
            System.out.println("Output file written successfully.");

            //If there is no valid output file,  we write to console
        }else {
            System.out.println("No valid output file specified, printing to console. To avoid this use -o option.\n" + "" +
                    "#####################################################");
            System.out.println(output);
        }
        return 0;
    }

    @Command(name = "reverse", mixinStandardHelpOptions = true, version = "reverse 1.0",
            description = "Reverses the content of an input file and writes the output to an output file.")
    public Integer reverse() throws IOException {
            String input = "";
            if(inputFile == null && outputFile == null){
                System.out.println("Please enter a valid input and output file, use -h for help.");
                return 1;
            }

            if(inputFile != null && inputFile.exists()) {
                //Read the input file
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

            String output = reverseWords(input);

            //Write the output to the output file using a buffered writer
            //If there is a valid output file:
            if(outputFile != null) {
                BufferedWriter writer = new BufferedWriter(new FileWriter(outputFile, Charset.forName(outputEncoding))) ;
                writer.write(output);
                writer.close();
                System.out.println("Output file written successfully.");

                //If there is no valid output file,  we write to console
            }else {
                System.out.println("No valid output file specified, printing to console. To avoid this use -o option.\n" + "" +
                        "#####################################################");
                System.out.println(output);
            }
            return 0;
    }




    /**
     * Overriding the call method of the Callable interface, here we
     * implement what our command actually does, i.e. takes an input file,
     * reverses its content and writes it to an output file.
     * @return 0 if the command was executed successfully, 1 otherwise
     * @throws Exception
     */
    @Override
    public Integer call() throws Exception {
        System.out.println("Please specify a valid command, use -h for help.");
        return 0;
    }


    /**
     * This method replaces all occurences of a character by another in a given text
     * @param text the text to replace characters in
     * @param charToReplace the character to replace
     * @param charToReplaceWith the character to replace with
     * @return output, the text with the characters replaced
     */
    private String replaceChar(String text, char charToReplace, char charToReplaceWith) {
        String output = "";
        for (int i = 0; i < text.length(); i++) {
            if (text.charAt(i) == charToReplace) {
                output += charToReplaceWith;
            } else {
                output += text.charAt(i);
            }
        }
        return output;
    }



    /**
     * This method reverses a given word
     * @param word, the word to reverse
     * @return reversedWord, the word written backwards
     */
    private String reverseWord(String word) {
        String reversedWord = "";
        for (int i = word.length() - 1; i >= 0; i--) {
            reversedWord += word.charAt(i);
        }
        return reversedWord;
    }

    /**
     * This method reverses the words of a given text
     * @param text, the text to reverse
     * @return reversedText, the text written backwards, with the words in it also written backwards
     */
    private String reverseWords(String text) {
        String reversedText = "";
        String[] words = text.split("\\s+");
        for (int i = words.length - 1; i >= 0; i--) {
            reversedText += reverseWord(words[i]) + " ";
        }
        return reversedText;
    }



    public static void main(String... args) {
        int startTime = (int) System.currentTimeMillis();
        int exitCode = new CommandLine(new ReverseWords()).execute(args);
        System.out.println("Command exited with exit code: " + exitCode +", executed in " + ((int) System.currentTimeMillis() - startTime) + "ms.");
        System.exit(exitCode);
    }
}
