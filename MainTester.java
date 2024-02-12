import org.junit.Before;
import org.junit.Test;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.NoSuchElementException;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.fail;
import static org.junit.Assert.assertThrows;

/**
 * A test class for all classes and methods in this HOMEWORK 4
 *
 * @author David Nguyen
 * @since 04/02/2023
 * @version 1.0
 */
public class MainTester {

    // TESTING THE CONSTRUCTORS AND METHODS OF THE TOKENIZER CLASS:

    // Represents a text array
    private String[] testArray;

    // Represents a text file
    private File testFile;

    // Represents an expected output
    private ArrayList<String> expectedOutput;

    /**
     * Sets up the Tokenizer for testing
     * @throws IOException when the given file is invalid.
     */
    @Before
    public void setUp() throws IOException {
        testArray = new String[] {
                "I'm going to eat twenty-five pancakes.",
                "I've got 2 cats and 1 dog!",
                "This sentence has %^& special characters.",
                "This_isn't_separated_by_spaces.",
                " :) "
        };
        testFile = File.createTempFile("testfile", ".txt");
        FileWriter fileWriter = new FileWriter(testFile);
        for (String line : testArray) {
            fileWriter.write(line);
            fileWriter.write("\n");
        }
        fileWriter.close();
        expectedOutput = new ArrayList<String>(Arrays.asList(
                "im", "going", "to", "eat", "twentyfive", "pancakes",
                "ive", "got", "2", "cats", "and", "1", "dog",
                "this", "sentence", "has", "special", "characters",
                "thisisntseparatedbyspaces"
        ));
    }

    /**
     * A method that tests the File constructor of the Tokenizer class
     * @throws IOException when the given file is invalid.
     */
    @Test
    public void testTokenizerFile() throws IOException {
        Tokenizer tokenizer = new Tokenizer(testFile.getPath());
        ArrayList<String> wordList = tokenizer.wordList();
        assertEquals(expectedOutput, wordList);
    }

    /**
     * A method that tests the String Array constructor of the Tokenizer class
     */
    @Test
    public void testTokenizerStringArray() {
        Tokenizer tokenizer = new Tokenizer(testArray);
        ArrayList<String> wordList = tokenizer.wordList();
        assertEquals(expectedOutput, wordList);
    }

    /**
     * A method that tests an edge case: Empty Input array
     */
    @Test
    public void testEmptyInput() {
        Tokenizer tokenizer1 = new Tokenizer(new String[]{});
        ArrayList<String> wordList1 = tokenizer1.wordList();
        assertEquals(new ArrayList<String>(), wordList1);
        try {
            File emptyFile = File.createTempFile("emptyfile", ".txt");
            Tokenizer tokenizer2 = new Tokenizer(emptyFile.getPath());
            ArrayList<String> wordList2 = tokenizer2.wordList();
            assertEquals(new ArrayList<String>(), wordList2);
        }
        catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Test the wordList() method
     */
    @Test
    public void testWordList() {
        // Case 1: Testing with an array input of multiple words with special characters
        String[] text = {"I'm going to eat twenty-five pancakes."};
        Tokenizer tokenizer = new Tokenizer(text);
        ArrayList<String> actual = tokenizer.wordList();
        assertEquals(6, actual.size());
        assertTrue(actual.contains("im"));
        assertTrue(actual.contains("going"));
        assertTrue(actual.contains("to"));
        assertTrue(actual.contains("eat"));
        assertTrue(actual.contains("twentyfive"));
        assertTrue(actual.contains("pancakes"));
        // Case 2: Testing with a file input of multiple words with special characters
        String file = "TestTextFile.txt";
        Tokenizer tokenizer1 = new Tokenizer(file);
        String expected = "[im, going, to, eat, twentyfive, pancakes]";
        ArrayList<String> actual1 = tokenizer.wordList();
        assertEquals(expected, actual1.toString());
        // Case 3: Testing with an input that is empty and has no words in it
        String[] text2 = {""};
        String expected2 = "[]";
        Tokenizer tokenizer2 = new Tokenizer(text2);
        ArrayList<String> actual2 = tokenizer2.wordList();
        assertEquals(expected2, actual2.toString());
    }

    /**
     * Test the runtime of the wordList() method
     */
    @Test
    public void testWordListRuntime() {
        Tokenizer tokenizer = new Tokenizer(testArray);
        long startTime = System.nanoTime();
        ArrayList<String> wordList = tokenizer.wordList();
        long endTime = System.nanoTime();
        long elapsedTime = endTime - startTime;
        assertTrue("wordList() runtime should be O(1)", elapsedTime <= 1000000); // 1ms as an example threshold
    }

    /**
     * Performs an extra test on the constructor with file:
     */
    @Test
    public void extraTestConstructorWithFile() {
        /* This file will consist of all kinds of characters, including special ones, as follows:
         * "I'm going to eat twenty-five pancakes." */
        String file = "TestTextFile.txt";
        String expectedOutput = "[im, going, to, eat, twentyfive, pancakes]";
        Tokenizer tokenizer = new Tokenizer(file);
        ArrayList<String> actualOutput = tokenizer.wordList();
        assertEquals(expectedOutput, actualOutput.toString());
    }

    /**
     * Performs an extra test on the constructor with empty file:
     */
    @Test
    public void testConstructorWithEmptyFile() {
        String file = "TestTextFile2.txt";
        String expected = "[]";
        Tokenizer tokenizer = new Tokenizer(file);
        ArrayList<String> actual = tokenizer.wordList();
        assertEquals(expected, actual.toString());
    }

    /**
     * Performs an extra test on the constructor with empty array:
     */
    @Test
    public void testConstructorWithEmptyArray() {
        String[] text = {""};
        String expected = "[]";
        Tokenizer tokenizer = new Tokenizer(text);
        ArrayList<String> actual = tokenizer.wordList();
        assertEquals(expected, actual.toString());
    }

    // -------------------------------------------------------------------------------------------------------------//

    // TESTING THE CONSTRUCTORS AND METHODS OF THE HASHTABLE<T> CLASS:

    // Represents a test table
    private HashTable<String> testTable;

    /**
     * Sets up the table for testing
     */
    @Before
    public void setUpHashTable() {
        testTable = new HashTable<String>();
    }

    /**
     * Tests the constructor with default capacity
     */
    @Test
    public void testConstructorDefaultCapacity() {
        assertTrue(testTable.size() == 0);
    }

    /**
     * Test the constructor with negative capacity
     */
    @Test(expected = IllegalArgumentException.class)
    public void testConstructorNegativeCapacity() {
        new HashTable<String>(-1);
    }

    /**
     * Test the put() and get() methods
     */
    @Test
    public void testPutAndGet() {
        testTable.put("key1", "value1");
        assertEquals("value1", testTable.get("key1"));
    }

    /**
     * Test the get() method when empty
     */
    @Test
    public void testGetWhenEmpty() {
        HashTable<Integer> hashTable = new HashTable<>();
        boolean thrown = false;
        try {
            hashTable.get("test");
            System.out.println("NoSuchElementException should be thrown");
        }
        catch (NoSuchElementException e) {
            thrown = true;
        }
        assertTrue(thrown);
    }

    /**
     * Test the get() method with non-existent key
     */
    @Test
    public void testGetNonExistingKey() {
        HashTable<Integer> table = new HashTable<Integer>();
        table.put("banana", 2);
        table.put("pineapple", 3);
        assertThrows(NoSuchElementException.class, () -> table.get("apple"));
    }

    /**
     * Test the put() method with an existing key
     */
    @Test
    public void testPutExistingKey() {
        // Case 1: Specified value is an instance of Integer
        HashTable<Integer> table = new HashTable<Integer>();
        table.put("apple", 1);
        table.put("banana", 2);
        table.put("pineapple", 3);
        table.put("apple", 4);
        assertEquals(Integer.valueOf(2), table.get("banana"));
        assertEquals(Integer.valueOf(3), table.get("pineapple"));
        assertEquals(Integer.valueOf(5), table.get("apple"));
        assertEquals(3, table.size());
        // Case 2: Specified value is not an instance of Integer
        HashTable<String> table1 = new HashTable<String>();
        table1.put("apple", "One");
        table1.put("banana", "Two");
        table1.put("pineapple", "Three");
        table1.put("apple", "four");
        assertEquals(3, table1.size());
        assertEquals(new String("Two"), table1.get("banana"));
        assertEquals(new String("Three"), table1.get("pineapple"));
        assertEquals(new String("four"), table1.get("apple"));
    }

    /**
     * Test the put() method with collision
     */
    @Test
    public void testPutWithCollision() {
        HashTable<Integer> table = new HashTable<Integer>(1);
        table.put("a", 1);
        table.put("b", 2);
        assertEquals(1, (int) table.get("a"));
        assertEquals(2, (int) table.get("b"));
    }

    /**
     * Test the get() method with non-existent key
     */
    @Test(expected = NoSuchElementException.class)
    public void testGetNonExistentKey() {
        testTable.get("nonExistentKey");
    }

    /**
     * Test the put() and update() methods with integers
     */
    @Test
    public void testPutAndUpdateWithInteger() {
        HashTable<Integer> intTable = new HashTable<>();
        intTable.put("key1", 1);
        intTable.put("key1", 2);
        assertEquals(Integer.valueOf(3), intTable.get("key1"));
    }

    /**
     * Test the put() and update() methods with non-integers
     */
    @Test
    public void testPutAndUpdateWithNonInteger() {
        testTable.put("key1", "value1");
        testTable.put("key1", "value2");
        assertEquals("value2", testTable.get("key1"));
    }

    /**
     * Test remove() method
     */
    @Test
    public void testRemove() {
        testTable.put("key1", "value1");
        assertEquals("value1", testTable.remove("key1"));
        assertTrue(testTable.size() == 0);
        testTable.put("key2", "value2");
        testTable.put("key3", "value3");
        assertTrue(testTable.size() == 2);
        assertEquals("value3", testTable.remove("key3"));
        assertTrue(testTable.size() == 1);
    }

    /**
     * Test remove() non-existent key
     */
    @Test(expected = NoSuchElementException.class)
    public void testRemoveNonExistentKey() {
        testTable.remove("nonExistentKey");
    }

    /**
     * Test remove() when empty
     */
    @Test
    public void testRemoveEmpty() {
        HashTable<Integer> table = new HashTable<Integer>();
        boolean thrown = false;
        try {
            table.remove("test");
        }
        catch (NoSuchElementException e) {
            thrown = true;
        }
        assertTrue(thrown);
    }

    /**
     * Test the size() method
     */
    @Test
    public void testSize() {
        assertTrue(testTable.size() == 0);
        testTable.put("key1", "value1");
        testTable.put("key2", "value2");
        assertTrue(testTable.size() == 2);
    }

    /**
     * Test the rehash() method
     */
    @Test
    public void testRehash() {
        for (int i = 0; i < 100; i++) {
            testTable.put("key" + i, "value" + i);
        }
        for (int i = 0; i < 100; i++) {
            assertEquals("value" + i, testTable.get("key" + i));
        }
    }

    /**
     * Test with the collisions and rehashing:
     */
    @Test
    public void extraTestCollisionsAndRehash() {
        int capacity = 10;
        HashTable<String> customTable = new HashTable<>(capacity);
        for (int i = 0; i < capacity + 1; i++) {
            customTable.put("key" + i, "value" + i);
        }
        assertEquals(capacity + 1, customTable.size());
        for (int i = 0; i < capacity + 1; i++) {
            assertEquals("value" + i, customTable.get("key" + i));
        }
    }

    // -------------------------------------------------------------------------------------------------------------//

    // TESTING CONSTRUCTORS & METHODS OF WORDSTAT CLASS:

    // Represents a wordStat instance from file
    private WordStat wordStatFromFile;

    // Represents a wordStat instance from text
    private WordStat wordStatFromText;

    // Represents a sample text
    private String[] sampleText = {"hello", "world", "hello", "world", "hello", "world", "hello", "david"};

    /**
     * Prepare the instance for calculations and testings
     */
    @Before
    public void setUpWordStat() {
        wordStatFromText = new WordStat(sampleText);
    }

    /**
     * Test the constructor with file
     */
    @Test
    public void testFileConstructorWordStat() {
        WordStat ws = new WordStat("TestTextFile4.txt");
        assertEquals(2, ws.wordCount("this"));
        assertEquals(2, ws.wordCount("is"));
        assertEquals(2, ws.wordCount("a"));
        assertEquals(2, ws.wordCount("test"));
        assertEquals(1, ws.wordCount("only"));
    }

    /**
     * Testing the constructor with empty file
     */
    @Test
    public void testEmptyFileConstructorWordStat() {
        WordStat ws = new WordStat("TestTextFile2.txt");
        assertEquals(0, ws.wordCount("this"));
        assertEquals(0, ws.wordCount("is"));
        assertEquals(0, ws.wordCount("a"));
        assertEquals(0, ws.wordCount("test"));
        assertEquals(0, ws.wordCount("only"));
    }

    /**
     * Test the wordCount() method
     */
    @Test
    public void testWordCount() {
        assertEquals(4, wordStatFromText.wordCount("hello"));
        assertEquals(3, wordStatFromText.wordCount("world"));
        assertEquals(1, wordStatFromText.wordCount("david"));
        assertEquals(0, wordStatFromText.wordCount("notfound"));
    }

    /**
     * Test the wordRank() method
     */
    @Test
    public void testWordRank() {
        assertEquals(1, wordStatFromText.wordRank("hello"));
        assertEquals(2, wordStatFromText.wordRank("world"));
        assertEquals(3, wordStatFromText.wordRank("david"));
        assertEquals(0, wordStatFromText.wordRank("notfound"));
    }

    /**
     * Extra test with the wordCount() method
     */
    @Test
    public void extraTestWordCount() {
        String[] text = new String[]{
                "This", "is", "a", "test", "string", "with", "test",
                "words", "in", "it", "words", "appear", "multiple", "times"
        };
        WordStat wordStat = new WordStat(text);
        assertEquals(2, wordStat.wordCount("test"));
        assertEquals(1, wordStat.wordCount("string"));
        assertEquals(0, wordStat.wordCount("not_in_text"));
    }

    /**
     * Extra test with the wordRank() method
     */
    @Test
    public void extraTestWordRank() {
        String[] text = new String[]{"This is a test string", "In this string, test words appear multiple times", "words"};
        WordStat wordStat = new WordStat(text);
        assertEquals(1, wordStat.wordRank("words"));
        assertEquals(1, wordStat.wordRank("test"));
        assertEquals(0, wordStat.wordRank("not_in_text"));
        assertEquals(1, wordStat.wordRank("string"));
        assertEquals(1, wordStat.wordRank("this"));
        assertEquals(5, wordStat.wordRank("a"));
        assertEquals(5, wordStat.wordRank("multiple"));
        assertEquals(0, wordStat.wordRank("xxx"));
    }

    /**
     * Test the mostCommonWords() method
     */
    @Test
    public void testMostCommonWords() {
        String[] expected = {"hello", "world"};
        assertArrayEquals(expected, wordStatFromText.mostCommonWords(2));
    }

    /**
     * Test the mostCommonWords() method with negative K
     */
    @Test(expected = IllegalArgumentException.class)
    public void testMostCommonWordsNegativeK() {
        wordStatFromText.mostCommonWords(-1);
    }

    /**
     * Extra test the mostCommonWords() method
     */
    @Test
    public void extraTestMostCommonWords() {
        // Case 1: Test if k is valid and smaller than total number of words in the given array
        String[] text = new String[]{"This is a test string of words", "In this string, test words appear multiple times"};
        WordStat wordStat = new WordStat(text);
        String[] expected = new String[]{"this", "test", "string", "words"};
        String[] actual = wordStat.mostCommonWords(4);
        assertEquals(expected, actual);
        // Case 2: Another test if k is valid and smaller than total number of words in the given array
        String[] text1 = new String[]{"This is a test string", "In this string, test words"};
        WordStat wordStat1 = new WordStat(text1);
        String[] expected1 = new String[]{"this","test","string","is","a","in"};
        String[] actual1 = wordStat1.mostCommonWords(6);
        assertEquals(expected1, actual1);
        // Case 3: If k is equal to the total number of words in the given array
        String[] text2 = new String[]{"This is a test string", "In this string, test words"};
        WordStat wordStat2 = new WordStat(text2);
        String[] expected2 = new String[]{"this","test","string","is","a","in","words"};
        String[] actual2 = wordStat2.mostCommonWords(10);
        assertEquals(expected2, actual2);
        // Case 4: If k is larger than the total number of words in the given array:
        String[] text3 = new String[]{"This is a test string", "In this string, test words"};
        WordStat wordStat3 = new WordStat(text3);
        String[] expected3 = new String[]{"this","test","string","is","a","in","words"};
        String[] actual3 = wordStat3.mostCommonWords(12);
        assertEquals(expected3, actual3);
        // Case 5: If k is equal to 0:
        String[] text4 = new String[]{"This is a test string", "In this string, test words"};
        WordStat wordStat4 = new WordStat(text4);
        String[] expected4 = new String[]{};
        String[] actual4 = wordStat4.mostCommonWords(0);
        assertEquals(expected4, actual4);
        // Case 6: If k is invalid (< 0):
        String[] text5 = new String[]{"This is a test string", "In this string, test words"};
        WordStat wordStat5 = new WordStat(text5);
        boolean thrown = false;
        try {
            wordStat5.mostCommonWords(-1);
        }
        catch (IllegalArgumentException e) {
            thrown = true;
        }
        assertTrue(thrown);
    }

    /**
     * Test the leastCommonWords() method:
     */
    @Test
    public void testLeastCommonWords() {
        String[] expected = {"david"};
        assertArrayEquals(expected, wordStatFromText.leastCommonWords(1));
    }

    /**
     * Test the leastCommonWords() method with negative K:
     */
    @Test(expected = IllegalArgumentException.class)
    public void testLeastCommonWordsNegativeK() {
        wordStatFromText.leastCommonWords(-1);
    }

    /**
     * Extra test the leastCommonWords() method:
     */
    @Test
    public void extraTestLeastCommonWords() {
        // Case 1: If k is > 0 and less than number of words in the given string
        String[] text = new String[]{"This is a test string of words", "In this string, test words appear multiple times"};
        WordStat wordStat = new WordStat(text);
        String[] expected = new String[]{"times", "multiple", "appear", "in"};
        String[] actual = wordStat.leastCommonWords(4);
        assertEquals(expected, actual);
        // Case 2: If k is > 0 and less than number of words in the given string
        String[] text1 = new String[]{"This is a test string", "In this string, test words"};
        WordStat wordStat1 = new WordStat(text1);
        String[] expected1 = new String[]{"words","in","a","is","string","test"};
        String[] actual1 = wordStat1.leastCommonWords(6);
        assertEquals(expected1, actual1);
        // Case 3: If k is equal to the total number of words in the given string
        String[] text2 = new String[]{"This is a test string", "In this string, test words"};
        WordStat wordStat2 = new WordStat(text2);
        String[] expected2 = new String[]{"words","in","a","is","string","test","this"};
        String[] actual2 = wordStat2.leastCommonWords(10);
        assertEquals(expected2, actual2);
        // Case 4: If k is larger than the total number of words in the given string:
        String[] text3 = new String[]{"This is a test string", "In this string, test words"};
        WordStat wordStat3 = new WordStat(text3);
        String[] expected3 = new String[]{"words","in","a","is","string","test","this"};
        String[] actual3 = wordStat3.leastCommonWords(12);
        assertEquals(expected3, actual3);
        // Case 5: If k is equal to 0:
        String[] text4 = new String[]{"This is a test string", "In this string, test words"};
        WordStat wordStat4 = new WordStat(text4);
        String[] expected4 = new String[]{};
        String[] actual4 = wordStat4.leastCommonWords(0);
        assertEquals(expected4, actual4);
        // Case 6: If k is invalid (< 0):
        String[] text5 = new String[]{"This is a test string", "In this string, test words"};
        WordStat wordStat5 = new WordStat(text5);
        boolean thrown = false;
        try {
            wordStat5.leastCommonWords(-1);
        }
        catch (IllegalArgumentException e) {
            thrown = true;
        }
        assertTrue(thrown);
    }

    /**
     * Test the mostCommonCollocations() method:
     */
    @Test
    public void testMostCommonCollocations() {
        String[] expectedPrecede = {"hello"};
        assertArrayEquals(expectedPrecede, wordStatFromText.mostCommonCollocations(1, "world", true));
        String[] expectedFollow = {"world"};
        assertArrayEquals(expectedFollow, wordStatFromText.mostCommonCollocations(1, "hello", false));
    }

    // Represents another text to perform extra tests on
    private String[] text = {"hello", "world", "hello", "hello", "java", "world", "world", "java", "goodbye"};

    /**
     * Test the most common collocations method with negative K
     */
    @Test(expected = IllegalArgumentException.class)
    public void testMostCommonCollocationsNegativeK() {
        wordStatFromText.mostCommonCollocations(-1, "world", true);
    }

    /**
     * Test the most common collocations method with non-existent base word
     */
    @Test(expected = IllegalArgumentException.class)
    public void testMostCommonCollocationsNonExistentBaseWord() {
        wordStatFromText.mostCommonCollocations(1, "notfound", true);
    }

    /**
     * Exxtra test the most common collocations method
     */
    @Test
    public void extraTestMostCommonCollocations() {
        WordStat wordStat = new WordStat(text);
        String[] expectedPrecede = {"world"};
        String[] expectedFollow = {"world"};
        assertArrayEquals(expectedPrecede, wordStat.mostCommonCollocations(1, "hello", true));
        assertArrayEquals(expectedFollow, wordStat.mostCommonCollocations(1, "hello", false));
    }

    /**
     * Extra test the most common collocations method with negative K
     */
    @Test(expected = IllegalArgumentException.class)
    public void testMostCommonCollocationsWithNegativeK() {
        WordStat wordStat = new WordStat(text);
        wordStat.mostCommonCollocations(-1, "hello", true);
    }

    /**
     * Extra test the most common collocations method with non-existent base word
     */
    @Test(expected = IllegalArgumentException.class)
    public void testMostCommonCollocationsWithNonexistentBaseWord() {
        WordStat wordStat = new WordStat(text);
        wordStat.mostCommonCollocations(1, "nonexistent", true);
    }

    // ----------------------------------------------------------------------------------------------------------- //

    // TESTING THE METHODS & CONSTRUCTORS OF THE PRIORITYQUEUE<K EXTENDS COMPARABLE<? SUPER V>, V> CLASS:

    // Represents a test priority queue
    private PriorityQueue<Integer, String> priorityQueue;

    /**
     * Sets up the priority queue
     */
    @Before
    public void setUpPriorityQueue() {
        priorityQueue = new PriorityQueue<>();
    }

    /**
     * Test all the constructors of this class
     */
    @Test
    public void testConstructors() {
        Integer[] keys = new Integer[]{3, 2, 1};
        String[] values = new String[]{"Three", "Two", "One"};
        PriorityQueue<Integer, String> pq = new PriorityQueue<>(keys, values);
        assertEquals(3, pq.size());
    }

    /**
     * Test if the constructors of this class will thrown an exception
     */
    @Test(expected = IllegalArgumentException.class)
    public void testConstructorThrowsIllegalArgumentException() {
        Integer[] keys = new Integer[]{1, 2};
        String[] values = new String[]{"One"};
        PriorityQueue<Integer, String> pq = new PriorityQueue<>(keys, values);
    }

    /**
     * Test add() method
     */
    @Test
    public void testAdd() {
        priorityQueue.add(3, "Three");
        priorityQueue.add(1, "One");
        priorityQueue.add(2, "Two");
        assertEquals(3, priorityQueue.size());
    }

    /**
     * Test update() method
     */
    @Test
    public void testUpdate() {
        priorityQueue.add(3, "Three");
        priorityQueue.add(1, "One");
        priorityQueue.add(2, "Two");
        priorityQueue.update(4, "Two");
        assertEquals("Two", priorityQueue.peek());
    }

    /**
     * Test if update() method will throw a No Such Element Exception:
     */
    @Test(expected = NoSuchElementException.class)
    public void testUpdateThrowsNoSuchElementException() {
        priorityQueue.update(4, "Two");
    }

    /**
     * Test peek() method
     */
    @Test
    public void testPeek() {
        priorityQueue.add(3, "Three");
        priorityQueue.add(1, "One");
        priorityQueue.add(2, "Two");
        assertEquals("Three", priorityQueue.peek());
    }

    /**
     * Test if peek() method will throw a No Such Element Exception:
     */
    @Test(expected = NoSuchElementException.class)
    public void testPeekThrowsNoSuchElementException() {
        priorityQueue.peek();
    }

    /**
     * Test peek(int k) method
     */
    @Test
    public void testPeekK() {
        priorityQueue.add(3, "Three");
        priorityQueue.add(1, "One");
        priorityQueue.add(2, "Two");
        Object[] values = priorityQueue.peek(2);
        assertArrayEquals(new String[]{"Three", "Two"}, values);
    }

    /**
     * Test peek(int k) method with k more than queue's size
     */
    @Test
    public void testPeekMoreThanSize() {
        priorityQueue.add(1, "One");
        priorityQueue.add(3, "Three");
        priorityQueue.add(5, "Five");
        priorityQueue.add(4, "Four");
        priorityQueue.add(2, "Two");
        try {
            priorityQueue.peek(6);
            fail("Expected NoSuchElementException");
        }
        catch (IllegalArgumentException e) {
            // Expected exception
        }
    }

    /**
     * Test peek() with duplicated nodes
     */
    @Test
    public void testPeekWithDuplicates() {
        priorityQueue.add(1, "One");
        priorityQueue.add(3, "Three");
        priorityQueue.add(5, "Five");
        priorityQueue.add(5, "Five-A");
        priorityQueue.add(4, "Four");
        priorityQueue.add(2, "Two");

        String[] expected = {"Five", "Five-A", "Four"};
        assertArrayEquals(expected, priorityQueue.peek(3));
    }

    /**
     * Test peek() with zero inputs
     */
    @Test
    public void testPeekZero() {
        priorityQueue.add(1, "One");
        priorityQueue.add(3, "Three");
        priorityQueue.add(5, "Five");
        priorityQueue.add(4, "Four");
        priorityQueue.add(2, "Two");

        String[] expected = {};
        assertArrayEquals(expected, priorityQueue.peek(0));
    }

    /**
     * Test peek() with negative input
     */
    @Test(expected = IllegalArgumentException.class)
    public void testPeekNegative() {
        priorityQueue.add(1, "One");
        priorityQueue.add(3, "Three");
        priorityQueue.add(5, "Five");
        priorityQueue.add(4, "Four");
        priorityQueue.peek(-9);
    }

    /**
     * Test peek() with empty queue
     */
    @Test(expected = IllegalArgumentException.class)
    public void testPeekEmpty() {
        priorityQueue.peek(5);
    }

    /**
     * Test poll() method
     */
    @Test
    public void testPoll() {
        priorityQueue.add(3, "Three");
        priorityQueue.add(1, "One");
        priorityQueue.add(2, "Two");
        assertEquals("Three", priorityQueue.poll());
        assertEquals(2, priorityQueue.size());
    }

    /**
     * Test poll() method with exception
     */
    @Test(expected = NoSuchElementException.class)
    public void testPollThrowsNoSuchElementException() {
        priorityQueue.poll();
    }

    /**
     * Test poll(V value) method
     */
    @Test
    public void testPollWithValue() {
        priorityQueue.add(3, "Three");
        priorityQueue.add(1, "One");
        priorityQueue.add(2, "Two");
        assertEquals(Integer.valueOf(2), priorityQueue.poll("Two"));
        assertEquals(2, priorityQueue.size());
    }

    /**
     * Test poll(V value) method with exception
     */
    @Test(expected = NoSuchElementException.class)
    public void testPollWithValueThrowsNoSuchElementException() {
        priorityQueue.poll("Two");
    }

    /**
     * Test size() method
     */
    @Test
    public void testSizePriorityQueue() {
        assertEquals(0, priorityQueue.size());
        priorityQueue.add(3, "Three");
        priorityQueue.add(1, "One");
        priorityQueue.add(2, "Two");
        assertEquals(3, priorityQueue.size());
    }

    // ------------------------------------------------------------------------------------------------------------- //

    // TESTING METHODS & CONSTRUCTORS OF THE CMDBPROFILE CLASS:

    // represents a test profile
    private CMDbProfile testProfile;

    /**
     * Sets up the profile for testing
     */
    @Before
    public void setUpCMDbProfile() {
        testProfile = new CMDbProfile("JohnDoe");
    }

    /**
     * Test constructor of the profile
     */
    @Test
    public void testConstructor() {
        assertEquals("JohnDoe", testProfile.getUserName());
    }

    /**
     * Test changeUserName(String userName) method
     */
    @Test
    public void testChangeUserName() {
        testProfile.changeUserName("JaneDoe");
        assertEquals("JaneDoe", testProfile.getUserName());
    }

    /**
     * Test rate() method
     */
    @Test
    public void testRate() {
        assertTrue(testProfile.rate("Inception", 9));
        assertFalse(testProfile.rate("Interstellar", 11));
        assertFalse(testProfile.rate("The Godfather", 0));
    }

    /**
     * Test changeRating() method
     */
    @Test
    public void testChangeRating() {
        testProfile.rate("Inception", 9);
        assertTrue(testProfile.changeRating("Inception", 8));
        assertFalse(testProfile.changeRating("NonexistentMovie", 6));
        assertFalse(testProfile.changeRating("Inception", 0));
        assertFalse(testProfile.changeRating("Inception", 11));
    }

    /**
     * Test removeRating() method
     */
    @Test
    public void testRemoveRating() {
        testProfile.rate("Inception", 9);
        assertTrue(testProfile.removeRating("Inception"));
        assertFalse(testProfile.removeRating("NonexistentMovie"));
    }

    /**
     * Test favorite() method
     */
    @Test
    public void testFavoriteCMDbProfile() {
        CMDbProfile cmDbProfile = new CMDbProfile("Sonw");
        assertNull(cmDbProfile.favorite());
        cmDbProfile.rate("Inception", 9);
        cmDbProfile.rate("The Godfather", 10);
        cmDbProfile.rate("Interstellar", 8);
        String[] expected1 = {"The Godfather"};
        assertArrayEquals(expected1, cmDbProfile.favorite());
        cmDbProfile.rate("The Shawshank Redemption", 10);
        String[] expected2 = {"The Godfather", "The Shawshank Redemption"};
        assertArrayEquals(expected2, cmDbProfile.favorite());
    }

    /**
     * Test favorite(int k) method
     */
    @Test
    public void testFavoriteK() {
        assertArrayEquals(new String[0], testProfile.favorite(3));
        testProfile.rate("Inception", 9);
        testProfile.rate("The Godfather", 10);
        testProfile.rate("Interstellar", 8);
        testProfile.rate("The Shawshank Redemption", 10);
        String[] expected1 = {"The Godfather", "The Shawshank Redemption", "Inception"};
        assertArrayEquals(expected1, testProfile.favorite(3));
        String[] expected2 = {"The Godfather", "The Shawshank Redemption"};
        assertArrayEquals(expected2, testProfile.favorite(2));
        String[] expected3 = {"The Godfather"};
        assertArrayEquals(expected3, testProfile.favorite(1));
    }

    /**
     * Test profileInformation() method
     */
    @Test
    public void testProfileInformation() {
        testProfile.rate("Inception", 9);
        testProfile.rate("The Godfather", 10);
        String expected = "JohnDoe (2)\nFavorite Movie: The Godfather";
        assertEquals(expected, testProfile.profileInformation());
    }

    /**
     * Test profileInformation() method with no ratings
     */
    @Test
    public void testProfileInfoWithNoRatings() {
        CMDbProfile profile1 = new CMDbProfile("John Doe");
        String expected = "John Doe (0)\nFavorite Movie: NO RATINGS YET!";
        assertEquals(expected, profile1.profileInformation());
    }

    /**
     * Test equals(Object o) method
     */
    @Test
    public void testEquals() {
        CMDbProfile profile2 = new CMDbProfile("JohnDoe");
        CMDbProfile profile3 = new CMDbProfile("JaneDoe");
        assertTrue(testProfile.equals(profile2));
        assertFalse(testProfile.equals(profile3));
        assertFalse(testProfile.equals(new Object()));
    }

    // ------------------------------------------------------------------------------------------------------------- //

    // TESTING THE METHODS & CONSTRUCTORS OF THE CMDBGROUP CLASS:

    // Represents a test group
    private CMDbGroup cmdBGroup;

    // Represents the member of this test groups
    private CMDbProfile[] members;

    /**
     * Sets up the group for testing
     */
    @Before
    public void setUpCMDbGroup() {
        cmdBGroup = new CMDbGroup();
        members = new CMDbProfile[]{
                new CMDbProfile("user1"),
                new CMDbProfile("user2"),
                new CMDbProfile("user3")
        };
    }

    /**
     * Test the constructor of this group class
     */
    @Test
    public void testConstructorCMDbGroup() {
        assertNotNull(cmdBGroup);
    }

    /**
     * Test registeredUsers() method
     */
    @Test
    public void testRegisteredUsers() {
        HashTable<CMDbProfile> registeredUsers = CMDbGroup.registeredUsers();
        assertNotNull(registeredUsers);
    }

    /**
     * Test group() method
     */
    @Test
    public void testGroup() {
        cmdBGroup.addMember(members);
        String[] group = cmdBGroup.group();
        assertArrayEquals(new String[]{"user1", "user2", "user3"}, group);
    }

    /**
     * Test add a single member to group
     */
    @Test
    public void testAddSingleMember() {
        cmdBGroup.addMember(members[0]);
        assertArrayEquals(new String[]{"user1"}, cmdBGroup.group());
    }

    /**
     * Test add an array of members to group
     */
    @Test
    public void testAddArrayOfMembers() {
        cmdBGroup.addMember(members);
        assertArrayEquals(new String[]{"user1", "user2", "user3"}, cmdBGroup.group());
    }

    /**
     * Test add an empty array of members to group
     */
    @Test
    public void testAddEmptyArrayOfMembers() {
        CMDbProfile[] members1 = new CMDbProfile[]{};
        cmdBGroup.addMember(members1);
        assertArrayEquals(new String[]{}, cmdBGroup.group());
    }

    /**
     * Test favorite() method with no members
     */
    @Test
    public void testFavoriteNone() {
        cmdBGroup.addMember(members);
        assertNull(CMDbGroup.favorite("unregisteredUser"));
    }

    /**
     * Test favorite() method
     */
    @Test
    public void testFavorite() {
        CMDbProfile member1 = new CMDbProfile("user1");
        CMDbProfile member2 = new CMDbProfile("user2");
        member1.rate("Movie1", 10);
        member2.rate("Movie2", 9);
        member1.rate("Movie1", 9);
        cmdBGroup.addMember(member1);
        cmdBGroup.addMember(member2);
        assertEquals("Movie1", cmdBGroup.favorite("user1"));
    }

    /**
     * Test favorite() method with invalid input
     */
    @Test
    public void testFavoriteInvalid() {

        assertNull(cmdBGroup.favorite(null));
    }

    /**
     * Extra test favorite() method with invalid input
     */
    @Test
    public void testFavoriteInvalid1() {
        assertNull(cmdBGroup.favorite(new String()));
    }

    /**
     * Test groupFavorites() method
     */
    @Test
    public void testGroupFavorites() {
        cmdBGroup.addMember(members);
        // Add sample movie ratings for members
        members[0].rate("The Shawshank Redemption", 10);
        members[0].rate("The Godfather", 10);
        members[1].rate("The Shawshank Redemption", 10);
        members[1].rate("The Godfather", 10);
        members[2].rate("Cars 2", 10);
        String[] groupFavorites = cmdBGroup.groupFavorites();
        assertArrayEquals(new String[]{"The Shawshank Redemption", "The Godfather"}, groupFavorites);
    }

    /**
     * Test groupFavorites() method with no members
     */
    @Test
    public void testGroupFavoritesNoMember() {
        String[] groupFavorites = cmdBGroup.groupFavorites();
        assertArrayEquals(new String[] {}, groupFavorites);
    }

    /**
     * Test randomMovie(int k) method
     */
    @Test
    public void testRandomMovie() {
        cmdBGroup.addMember(members);
        members[0].rate("The Shawshank Redemption", 10);
        members[0].rate("The Godfather", 10);
        members[1].rate("Pulp Fiction", 10);
        members[1].rate("Fight Club", 10);
        members[2].rate("Inception", 10);
        members[2].rate("Interstellar", 10);
        String randomMovie = cmdBGroup.randomMovie(6);
        assertNotNull(randomMovie);
    }

    // Represents a test group
    private CMDbGroup group1;

    // Represents a member of this group
    private CMDbProfile user1;

    // Represents a member of this group
    private CMDbProfile user2;

    /**
     * Sets up the extra tests for this class
     */
    @Before
    public void setUpExtraCMDBGroupTest() {
        group1 = new CMDbGroup();
        user1 = new CMDbProfile("User1");
        user2 = new CMDbProfile("User2");
        user1.rate("Movie1", 10);
        user1.rate("Movie2", 9);
        user2.rate("Movie3", 8);
        user2.rate("Movie1", 10);
    }

    /**
     * Test add() methods - extra:
     */
    @Test
    public void extraTestAddMember() {
        group1.addMember(user1);
        String[] expected = {"User1"};
        assertArrayEquals(expected, group1.group());
    }

    /**
     * Test add() methods - extra:
     */
    @Test
    public void extraTestAddMembers() {
        CMDbProfile[] members = {user1, user2};
        group1.addMember(members);
        String[] expected = {"User1", "User2"};
        assertArrayEquals(expected, group1.group());
    }

    /**
     * Test favorite() method - extra:
     */
    @Test
    public void extraTestFavoriteCMDbGroup() {
        group1.addMember(user1);
        assertEquals("Movie1", CMDbGroup.favorite("User1"));
        assertNull(CMDbGroup.favorite("User3"));
    }

    /**
     * Test groupFavorites() method - extra:
     */
    @Test
    public void extraTestGroupFavorites() {
        group1.addMember(user1);
        group1.addMember(user2);
        String[] expected = {"Movie1"};
        assertArrayEquals(expected, group1.groupFavorites());
    }

    /**
     * Test randomMovie() method - extra:
     */
    @Test
    public void testRandomMovieNull() {
        String randomMovie = cmdBGroup.randomMovie(6);
        assertNull(randomMovie);
        String randomMovie1 = cmdBGroup.randomMovie(1);
        assertNull(randomMovie1);
    }

    /**
     * Test randomMovie() method - with negative K:
     */
    @Test
    public void testRandomMovieInvalidK() {
        assertNull(cmdBGroup.randomMovie(-99));
    }

    // ALL METHODS & CONSTRUCTORS OF ALL SIX CLASSES HAVE BEEN SUCCESSFULLY TESTED AND EDGE CASES ARE ALSO COVERED //

}