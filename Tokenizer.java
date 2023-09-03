import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

import java.util.ArrayList;

/**
 * A class that represents and performs the functions of a Tokenizer
 *
 * @author David Nguyen
 * @since 04/02/2023
 * @version 1.0
 */
public class Tokenizer {

    // Field that stores the normalized word list generated from a given string
    private ArrayList<String> normalizedWordList;

    /**
     * A constructor that creates a Tokenizer that reads a ‘.txt’ file from the specified file directory and creates a list of
     * all normalized words from the file.
     * Time Complexity: O(N) where N is the number of words in the given .txt file
     *
     * @param file Any file to be read from the specified file directory
     */
    public Tokenizer(String file) {
        // Creates a new instance of ArrayList to store the normalized word list
        this.normalizedWordList = new ArrayList<String>();
        /* To make sure that we do not leave any exception unchecked (as reading a file may cause an IOException) a try-catch
         * block is implemented: */
        try {
            // Creates a new instance of ArrayList to store the normalized word list
            this.normalizedWordList = new ArrayList<String>();
            // Creates a new BufferedReader instance & FileReader instance to read the .txt file
            BufferedReader bufferedReader = new BufferedReader(new FileReader(file));
            // Variable that keeps track of every line in the file
            String individualLine = new String();
            // A loop that reads every line or individual word from the given file and converts them into normalized words
            while ((individualLine = bufferedReader.readLine()) != null) {
                // It will split the strings so that the resulting words do not contain any \n \t \r or ' ' characters
                String[] arrayOfWords = individualLine.split("[ \n\t\r]+");
                // A loop that will go through the newly created array to parsed them to the normalized word list
                for (int i = 0; i < arrayOfWords.length; i = i + 1) {
                    // Variable that keeps track of each of every word from the newly created array
                    String individualWord = arrayOfWords[i];
                    // Variable that keeps track of each normalized word from the newly created array
                    String normalizedWord = normalizeStringOrWord(individualWord);
                    // Variable that keeps track of whether each of the normalized word is empty or not
                    boolean checkStringEmpty = normalizedWord.length() == 0;
                    // If it's empty, do NOT add it to the normalized words list
                    if (checkStringEmpty == true) {
                        ;
                    }
                    // Otherwise, add it to the normalized word list
                    else {
                        this.normalizedWordList.add(normalizedWord);
                    }
                }
            }
            bufferedReader.close();
        }
        /* If any IOException is caught during the execution of the above steps, print the message of the exception and
         * its cause. Additionally, terminate the above process. */
        catch (IOException exception) {
            System.out.println(exception.getMessage());
            System.out.println(exception.getCause());
            exception.printStackTrace();
        }
    }

    /**
     * A constructor that creates a Tokenizer by reading in an input sequence of Strings and parses all normalized words
     * from the sequence as described in the class description, still maintaining the same order.
     * Time Complexity: O(N) where N is the number of words in the given strings array
     *
     * @param text Any array of strings to be parsed into the Tokenizer to be normalized
     */
    public Tokenizer(String[] text) {
        // Creates a new instance of ArrayList to store the normalized word list
        this.normalizedWordList = new ArrayList<String>();
        /* To make sure that we do not leave any exception unchecked (as reading a file may cause an IOException) a try-catch
         * block is implemented: */
        try {
            // Variable to keep track of the individual lines to be read from the text
            String readLines = new String();
            // A loop that reads every word or line in the strings array and normalize it
            for (int i = 0; i < text.length; i++) {
                // The variable will temporarily store the individual lines from the text
                readLines = text[i];
                // It will split the strings so that the resulting words do not contain any \n \t \r or ' ' characters
                String[] splittedString = readLines.split("[\t\r\n ]+");
                // A loop that will go through the newly created array to parsed them to the normalized word list
                for (int j = 0; j < splittedString.length; j++) {
                    // Variable that keeps track of each of every word from the newly created array
                    String individualWord = splittedString[j];
                    // Variable that keeps track of each normalized word from the newly created array
                    String normalizedWord = normalizeStringOrWord(individualWord);
                    // Variable that keeps track of whether each of the normalized word is empty or not
                    boolean checkStringEmpty = normalizedWord.length() == 0;
                    // If it's empty, do NOT add it to the normalized words list
                    if (checkStringEmpty == true) {
                        ;
                    }
                    // Otherwise, add it to the normalized word list
                    else {
                        this.normalizedWordList.add(normalizedWord);
                    }
                }
            }
        }
        /* If any exception is caught during the execution of the above steps, print the message of the exception and
         * its cause. Additionally, terminate the above process. */
        catch (Exception exception) {
            System.out.println(exception.getMessage());
            System.out.println(exception.getCause());
            exception.printStackTrace();
        }
    }

    /**
     * A method that will return an ArrayList containing all the normalized words parsed when creating this Tokenizer object.
     * Time Complexity: O(1)
     *
     * @return An ArrayList containing all the normalized words parsed when creating this Tokenizer object.
     */
    public ArrayList<String> wordList() {
        // Variable that keeps track of the normalized words
        ArrayList<String> returnList = new ArrayList<String>(this.normalizedWordList);
        return returnList;
    }

    /**
     * A helper method that normalize a string or a word.
     * Time Complexity: O(N) where N is the total number of characters in the given string
     *
     * @param string Any given sentence or word to be normalized
     * @return The normalized string
     */
    private String normalizeStringOrWord(String string) {
        // A StringBuilder instance to construct an output, normalized string
        StringBuilder output = new StringBuilder();
        // A character variable to keeps track of each character in the given string or word
        char character;
        // A loop that loops through each character of the given string to normalize them
        for (int i = 0; i < string.length(); i++) {
            character = string.charAt(i);
            // If the character is a letter or a digit, normalized it
            if (Character.isLetter(character) || Character.isDigit(character)) {
                // Variable to keeps track of the lowercased character
                char tempChar = Character.toLowerCase(character);
                output.append(tempChar);
            }
            else {
                ;
            }
        }
        return output.toString();
    }

}