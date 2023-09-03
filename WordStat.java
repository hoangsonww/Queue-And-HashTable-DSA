import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedList;
import java.util.NoSuchElementException;

/**
 * A class that normalizes and then computes word statistics from given word lists.
 *
 * @author David Nguyen
 * @since 04/02/2023
 * @version 1.0
 */
public class WordStat {

    // A field that stores a hash table that has words in the orders and positions that imply their frequencies
    private HashTable<Integer> wordFreqTable;

    private HashTable<Integer> wordRankTable;

    // A field that stores the sorted normalized word list
    private ArrayList<String> sortedNormalizedWordsList;

    /* A variable that will be used as a temporary storage for the parsed-in text or file of the constructors and will
     * then also store the normalized list of words as an array */
    private String[] normalizedWordsArray;

    /**
     * A constructor that creates and prepares a WordStat instance to efficiently compute the word statistics later on.
     * This constructor will take in a .txt file.
     * Time Complexity: O(W log W) average case, O(W) worst case, where W is the number of words in the specified file
     *
     * @param file Any file to calculate word statistics from.
     */
    public WordStat(String file) {
        // Variable of type Tokenizer to helps prepare the file for calculations
        Tokenizer tokenizerInstance = new Tokenizer(file);
        // Initialize the normalized words array to be the output from the Tokenizer instance
        this.normalizedWordsArray = tokenizerInstance.wordList().toArray(new String[0]);
        // Call a helper method to help prepare this instance of word stat for calculations
        initializeWordStatInstance();
    }

    /**
     * A constructor that creates and prepares a WordStat instance to efficiently compute the word statistics later on.
     * This constructor will take in an array of strings.
     * Time Complexity: O(W log W) average case, O(W) worst case, where W is the number of words in the specified file
     *
     * @param text Any array of strings to calculate word statistics from.
     */
    public WordStat(String[] text) {
        // Initialize the normalized words array to be the given text
        this.normalizedWordsArray = text;
        // Call a helper method to help prepare this instance of word stat for calculations
        initializeWordStatInstance();
    }


    /**
     * Initializes the WordStatInstance by processing the words in the given normalizedWordsArray and populating the
     * wordHashTable and sortedNormalizedWordsList data structures. These structures are used for subsequent statistical
     * analysis of the words.
     * Time Complexity: O(W log W) average case, O(W) worst case, where W is the number of words in the specified file or
     * text array
     *
     */
    private void initializeWordStatInstance() {
        // Variable that stores a Tokenizer instance using the normalizedWordsArray
        Tokenizer tokenizerInstance = new Tokenizer(this.normalizedWordsArray);
        // Variable that stores a newly generated list of words from the tokenizer and store it back in normalizedWordsArray
        String[] tempNormalizedList = tokenizerInstance.wordList().toArray(new String[0]);
        // Initialize this normalizedWordsArray field to be the normalized word list from the Tokenizer
        this.normalizedWordsArray = tempNormalizedList;
        // Initialize the wordHashTable as a new HashTable with Integer values to store frequencies of words
        this.wordFreqTable = new HashTable<Integer>();
        // Initialize the sortedNormalizedWordsList as a new ArrayList of String
        this.sortedNormalizedWordsList = new ArrayList<String>();
        // Initialize the wordRankTable as a new HashTable with Integer values to store ranks of words
        this.wordRankTable = new HashTable<Integer>();
        // A loop that iterates through the words in the normalizedWordsArray to prepare this instance of WordStat for computations
        for (String word : normalizedWordsArray) {
            // Check if the word is already in the wordHashTable. If so, put it into the hash table in its appropriate position
            if (hashTableContainsKey(this.wordFreqTable, word) == true) {
                // Put new word into the hash table in its appropriate position
                this.wordFreqTable.put(word, 1);
            }
            else {
                // Put the word to the wordHashTable with a frequency of 1 to indicate its relative frequency
                this.wordFreqTable.put(word, 1);
                // Put the word to the sortedNormalizedWordsList as well
                this.sortedNormalizedWordsList.add(word);
            }
        }
        // Try sorting the sortedNormalizedWordsList in descending order of their frequencies
        try {
            Collections.sort(this.sortedNormalizedWordsList, (index1, index2) ->
                    this.wordFreqTable.get(index2) - this.wordFreqTable.get(index1));
        }
        catch (NoSuchElementException exception) {
            ;
            // Do nothing if the NoSuchElementException occurs
        }
        /* Now take the sortedNormalizedWordsList and populate the wordRankTable with the appropriate ranks of the words */
        // Variable to store the current rank of the current word being processed
        int currentRank = 1;
        // Variable to store the current frequency of the current word being processed
        int currentFrequency = -1;
        // Loops through the sortedNormalizedWordList and populates the wordRankTable with the appropriate ranks of the words
        for (int index = 0; index < this.sortedNormalizedWordsList.size(); index++) {
            // Variable to store the current word being processed at the current index
            String currentWord = this.sortedNormalizedWordsList.get(index);
            // Variable to store the frequency of the current word being processed
            int currentWordFrequency = this.wordFreqTable.get(currentWord);
            // Check if the current word frequency is different from the current frequency. If yes, update the current rank.
            if (currentWordFrequency != currentFrequency) {
                currentRank = index + 1;
                currentFrequency = currentWordFrequency;
            }
            // Otherwise, do nothing.
            else {
                ;
            }
            // Now reset the rank values of the words in the wordRankTable and update it with the new, appropriate rank value
            try {
                // Variable to store the current, existing rank of the current word being processed
                int existingRank = this.wordRankTable.get(currentWord);
                // Reset the rank value
                this.wordRankTable.put(currentWord, -existingRank);
            }
            catch (NoSuchElementException exception) {
                ;
                // Do nothing if the NoSuchElementException occurs, it's expected
            }
            // Update the rank value with the appropriate rank
            this.wordRankTable.put(currentWord, currentRank);
        }
    }

    /**
     * A method that returns the number of times the specified word appears in the normalized text.
     * Time Complexity: O(1) average case, O(W) worst case, where W is the number of words in the normalized text.
     *
     * @param word Any specific word to calculate the word count for
     * @return The number of times the specified word appears in the normalized text.
     */
    public int wordCount(String word) {
        // Try-catch block to catch the NoSuchElementException if the given word is not in the hash table
        try {
            // Variable that stores the word count of the given word
            int wordCountOfGivenWord = this.wordFreqTable.get(word);
            // Return the word count of the given word
            return wordCountOfGivenWord;
        }
        catch (NoSuchElementException exception) {
            // Return 0 if the word is not in the hash table
            return 0;
        }
    }

    /**
     * A method that calculates the rank of the specified word in the given normalized text or file.
     * Time Complexity: O(1) average case, O(W) worst case, where W is the number of words in the normalized text.
     *
     * @param word Any specific word to calculate the word count for.
     * @return The number of times the specified word appears in the normalized text.
     */
    public int wordRank(String word) {
        // Try-catch block to catch the NoSuchElementException if the given word is not in the hash table
        try {
            // Check if the word is in the wordFreqTable
            if (!hashTableContainsKey(this.wordFreqTable, word)) {
                // If no such word is in the table, return 0
                return 0;
            }
            // Access the rank directly from the wordRankTable with constant time complexity
            int rankOfWord = this.wordRankTable.get(word);
            // Return the rank of the given word
            return rankOfWord;
        }
        catch (NoSuchElementException exception) {
            // Return 0 if the word is not in the hash table
            return 0;
        }
    }

    /**
     * A method that returns a String array of the k most common words in the normalized text, in decreasing order of
     * their frequencies.
     * Time Complexity: O(k) worst case, where k is the given k.
     *
     * @param k The k most common words to find from the normalized text.
     * @return A String array of the k most common words in the normalized text, in decreasing order of their frequency.
     * @throws IllegalArgumentException If k is negative, throw an IllegalArgumentException.
     */
    public String[] mostCommonWords(int k) {
        // Check if k is negative. If so, throw an IllegalArgumentException.
        if (k < 0) {
            throw new IllegalArgumentException("The given K value is INVALID! It must be greater than or equal to 0!");
        }
        // Else, let the method continue as usual
        else {
            ;
        }
        // Variable that stores the number of words currently in the normalized word list, as it might be smaller than the given k!
        int numberOfWordsCurrentlyInList = Math.min(k, this.sortedNormalizedWordsList.size());
        // Variable that stores the most common words from the normalized words
        String[] mostCommonWords = new String[numberOfWordsCurrentlyInList];
        // Loops through the current normalized word list (sorted) and simply get the most common words from it
        for (int index = 0; index < numberOfWordsCurrentlyInList; index = index + 1) {
            mostCommonWords[index] = this.sortedNormalizedWordsList.get(index);
        }
        // Return the array of most common words, in descending order of their frequencies.
        return mostCommonWords;
    }

    /**
     * A method that returns a String array of the k least common words in the normalized text, in decreasing order of
     * their frequencies.
     * Time Complexity: O(k) worst case, where k is the given k.
     *
     * @param k The k least common words to find from the normalized text.
     * @return A String array of the k least common words in the normalized text, in increasing order of their frequency.
     * @throws IllegalArgumentException If k is negative, throw an IllegalArgumentException.
     */
    public String[] leastCommonWords(int k) {
        // Check if k is negative. If so, throw an IllegalArgumentException.
        if (k < 0) {
            throw new IllegalArgumentException();
        }
        // Else, let the method continue as usual
        else {
            ;
        }
        // Variable that stores the number of words currently in the normalized word list, as it might be smaller than the given k!
        int numberOfWordsCurrentlyInList = Math.min(k, this.sortedNormalizedWordsList.size());
        // Variable that stores the least common words from the normalized words
        String[] leastCommonWords = new String[numberOfWordsCurrentlyInList];
        // Loops through the current normalized word list (sorted) and simply get the least common words from it
        for (int index = 0; index < numberOfWordsCurrentlyInList; index = index + 1) {
            /* Variable that stores the index to get the words from. Have to make sure that the returned array is in
             * decreasing order of their frequencies */
            int indexToGet = this.sortedNormalizedWordsList.size() - 1 - index;
            leastCommonWords[index] = this.sortedNormalizedWordsList.get(indexToGet);
        }
        // Return the array of most common words, in descending order of their frequencies.
        return leastCommonWords;
    }

    /**
     * A method that returns the k most common collocations that precede the first instance of baseWord in the
     * normalized text if precede is true, or the k most that follow the first instance of baseWord if precede is false.
     * Time Complexity: O(W^2), where W is the number of words in the normalized text
     *
     * @param k The k most common collocations from this base word to find from the normalized text.
     * @param baseWord Any variable of type string to serve as a base for searching.
     * @param precede If true, find words precede the base word, otherwise, find words that follow the base word.
     * @return The k most common collocations from the normalized text
     * @throws IllegalArgumentException If k is negative or base word does not exist, throw an IllegalArgumentException.
     */
    public String[] mostCommonCollocations(int k, String baseWord, boolean precede) {
        // Variable that stores the collocations from the base word. The collocations are put based on their frequencies.
        HashTable<Integer> frequenciesOfCollocationsTable = new HashTable<Integer>();
        // Variable that stores the most common collocations list to be returned later on.
        ArrayList<String> mostCommonCollocationsArrayList = new ArrayList<String>();
        // If k is negative, throw an IllegalArgumentException.
        if (k < 0) {
            throw new IllegalArgumentException("The given K is INVALID! It must be greater than or equal to 0");
        }
        // Otherwise, do nothing and let the method continues
        else {
            ;
        }
        // If base word does not exist, throw an IllegalArgumentException.
        if (!hashTableContainsKey(this.wordFreqTable, baseWord)) {
            throw new IllegalArgumentException("The given base word is not in the text!");
        }
        // Otherwise, do nothing and let the method continues
        else {
            ;
        }
        // Variable that stores the current length of the normalized words array
        int normalizedWordsArrayLength = this.normalizedWordsArray.length;
        // Loop through the normalized words array to find the base word and its collocations
        for (int index = 0; index < normalizedWordsArrayLength; index = index + 1) {
            // Variable that stores whether the base word is found in the normalized words array
            boolean foundBaseWord = this.normalizedWordsArray[index].equals(baseWord);
            // If yes, split into 2 cases: If precede is true or if precede is false
            if (foundBaseWord == true) {
                // Variable to store the index of the collocation of the base word
                int indexOfCollocation;
                // If precede is true, find backward
                if (precede == true) {
                    indexOfCollocation = index - 1;
                }
                // If precede is false, find forward
                else {
                    indexOfCollocation = index + 1;
                }
                // Now, there are two other cases: If the hash table contains or does not contain the collocation
                if (indexOfCollocation >= 0 && indexOfCollocation < this.normalizedWordsArray.length) {
                    // Variable that stores the collocation of the current base word
                    String collocation = normalizedWordsArray[indexOfCollocation];
                    /* If the hash table contains the key, put it into the frequencies of collocations table at their
                     * appropriate positions. */
                    if (hashTableContainsKey(frequenciesOfCollocationsTable, collocation) == true) {
                        frequenciesOfCollocationsTable.put(collocation, frequenciesOfCollocationsTable.get(collocation) + 1);
                    }
                    // Else, put it into the first position in the collocations table and add it to the most common collocations list
                    else {
                        frequenciesOfCollocationsTable.put(collocation, 1);
                        mostCommonCollocationsArrayList.add(collocation);
                    }
                }
            }
            // If base word is not found, then do nothing as this case has already been covered
            else {
                ;
            }
        }
        // Try-catch block to sort the frequencies of collocations table in the descending order of their frequency
        try {
            Collections.sort(mostCommonCollocationsArrayList, (index1, index2) ->
                    frequenciesOfCollocationsTable.get(index2) - frequenciesOfCollocationsTable.get(index1));
        }
        // Catch a NoSuchElementException, ignore it and do nothing
        catch (NoSuchElementException exception) {
            ;
        }
        // Variable that stores the number of collocations that will be returned
        int numberOfCollocationsToReturn = Math.min(k, mostCommonCollocationsArrayList.size());
        // Variable that stores the most common collocations array that will be returned
        String[] mostCommonCollocationsArray = new String[numberOfCollocationsToReturn];
        // A loop that copies all elements from the most common collocations array list to the above array
        for (int index = 0; index < numberOfCollocationsToReturn; index = index + 1) {
            mostCommonCollocationsArray[index] = mostCommonCollocationsArrayList.get(index);
        }
        // Return the most common collocations array
        return mostCommonCollocationsArray;
    }

    /**
     * A method that checks whether the hash table contains the given, specified key.
     * Time Complexity: O(1) worst case.
     *
     * @param hashTable The hash table to check with
     * @param key The key to find in the given hash table
     * @return True or False depending on whether the hash table contains the given key or not.
     */
    private boolean hashTableContainsKey(HashTable<Integer> hashTable, String key) {
        // Variable that stores the key to be checked
        String keyToCheck = key;
        // Try to find it in the hash table. If it's found, return true
        try {
            hashTable.get(keyToCheck);
            return true;
        }
        // Otherwise, return false when catches an exception saying that no such key exists
        catch (NoSuchElementException e) {
            return false;
        }
    }

}