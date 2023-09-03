import org.junit.Test;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.NoSuchElementException;

import static org.junit.Assert.*;

public class Tester {

    @Test
    public void testTokenizerEmpty() throws IOException {
        Tokenizer tokenizer1 = new Tokenizer(new String[0]);
        assertEquals(new ArrayList<>(), tokenizer1.wordList());
        Tokenizer tokenizer2 = new Tokenizer("empty.txt");
        assertEquals(new ArrayList<>(), tokenizer2.wordList());
    }

    @Test
    public void testTokenizerFile() throws IOException {
        Tokenizer tokenizer1 = new Tokenizer("file1.txt");
        ArrayList<String> arrayList = new ArrayList<>(Arrays.asList("im", "going", "to", "eat", "twentyfive", "pancakes", "helo"));
        assertEquals(arrayList, tokenizer1.wordList());
    }

    @Test
    public void testTokenizerArray() {
        Tokenizer tokenizer1 = new Tokenizer(new String[] {"im", "going", "to", "eat", "twentyfive", "pan*cakes", "helo", "", "  ", "\t", ":))"});
        ArrayList<String> arrayList = new ArrayList<>(Arrays.asList("im", "going", "to", "eat", "twentyfive", "pancakes", "helo"));
        assertEquals(arrayList, tokenizer1.wordList());
    }

    //HashTable
    @Test(expected = IllegalArgumentException.class)
    public void testHashTableConstructorException() {
        new HashTable<Integer>(-2);
    }

    @Test(expected = NoSuchElementException.class)
    public void testGetExceptionEmptyTable() {
        HashTable<Integer> hashTable = new HashTable<>();
        hashTable.get("abc");
    }

    @Test
    public void testPut() {
        HashTable<Integer> integerHashTable = new HashTable<>(7);
        integerHashTable.put("one", 12);
        integerHashTable.put("three", 34);
        integerHashTable.put("hi", 46);
        assertEquals(12, (int) integerHashTable.get("one"));
        assertEquals(34, (int) integerHashTable.get("three"));
        assertEquals(3, integerHashTable.size());
        integerHashTable.put("one", 28);
        assertEquals(3, integerHashTable.size());
        assertEquals(40, (int) integerHashTable.get("one"));

        HashTable<String> stringHashTable = new HashTable<>(7);
        stringHashTable.put("one", "one");
        stringHashTable.put("three", "three");
        stringHashTable.put("hi", "hi");
        assertEquals("one", stringHashTable.get("one"));
        assertEquals("three", stringHashTable.get("three"));
        assertEquals(3, stringHashTable.size());
        stringHashTable.put("one", "two");
        assertEquals(3, stringHashTable.size());
        assertEquals("two", stringHashTable.get("one"));
    }

    @Test(expected = NoSuchElementException.class)
    public void testGetNonEmptyTable() {
        HashTable<Integer> integerHashTable = new HashTable<>(7);
        integerHashTable.put("one", 12);
        integerHashTable.put("three", 34);
        integerHashTable.put("hi", 46);
        integerHashTable.get("two");
    }

    @Test
    public void testPutMoreThanCapacity() {
        HashTable<Integer> integerHashTable = new HashTable<>(7);
        for(int i = 0; i < 8; i ++) {
            integerHashTable.put("" + i, i);
        }
        assertEquals(8, integerHashTable.size());
        for(int i = 0; i < 8; i ++) {
            assertEquals(i, (int) integerHashTable.get("" + i));
        }
    }

    @Test(expected = NoSuchElementException.class)
    public void testRemoveEmptyTable() {
        HashTable<Integer> integerHashTable = new HashTable<>(7);
        integerHashTable.remove("one");
        assertEquals(0, integerHashTable.size());
    }

    @Test(expected = NoSuchElementException.class)
    public void testRemoveNonEmptyTable() {
        HashTable<Integer> integerHashTable = new HashTable<>(7);
        integerHashTable.put("one", 12);
        integerHashTable.put("three", 34);
        integerHashTable.put("hi", 46);
        integerHashTable.remove("two");
        assertEquals(3, integerHashTable.size());
    }

    @Test(expected = NoSuchElementException.class)
    public void testRemove() {
        HashTable<Integer> integerHashTable = new HashTable<>(7);
        integerHashTable.put("one", 12);
        integerHashTable.put("three", 34);
        integerHashTable.put("hi", 46);
        assertEquals(46, (int) integerHashTable.remove("hi"));
        assertEquals(2, integerHashTable.size());
        integerHashTable.get("hi");
    }

    @Test
    public void testPutAfterRemove() {
        HashTable<Integer> integerHashTable = new HashTable<>(7);
        integerHashTable.put("one", 12);
        integerHashTable.put("three", 34);
        integerHashTable.put("hi", 46);
        integerHashTable.remove("hi");
        integerHashTable.put("hi", 15);
        assertEquals(3, integerHashTable.size());
        assertEquals(15, (int) integerHashTable.get("hi"));
    }

    // WordStat
    @Test
    public void testConstructor() throws IOException {
        WordStat wordStat = new WordStat("file2.txt");
        WordStat wordStatEmpty = new WordStat("empty.txt");
    }

    @Test
    public void testWordCount() throws IOException {
        WordStat wordStat = new WordStat("file2.txt");
        assertEquals(5, wordStat.wordCount("mouse"));
        assertEquals(3, wordStat.wordCount("music"));
        assertEquals(0, wordStat.wordCount("musc"));
        assertEquals(1, wordStat.wordCount("bruh"));
        assertEquals(1, wordStat.wordCount("keyboard"));
    }

    @Test
    public void testWordCountEmpty() throws IOException {
        WordStat wordStatEmpty = new WordStat("empty.txt");
        assertEquals(0, wordStatEmpty.wordCount("mouse"));
    }

    @Test
    public void testWordRank() throws IOException {
        WordStat wordStat = new WordStat("file2.txt");
        assertEquals(1, wordStat.wordRank("mouse"));
        assertEquals(2, wordStat.wordRank("music"));
        assertEquals(3, wordStat.wordRank("key"));
        assertEquals(4, wordStat.wordRank("keyboard"));
        assertEquals(4, wordStat.wordRank("bruh"));
        assertEquals(0, wordStat.wordRank("musc"));
    }

    @Test
    public void testWordRankEmpty() throws IOException {
        WordStat wordStatEmpty = new WordStat("empty.txt");
        assertEquals(0, wordStatEmpty.wordRank("mouse"));
    }

    @Test
    public void testMostCommonWords() throws IOException {
        WordStat wordStat = new WordStat("file2.txt");
        assertEquals(new String[] {"mouse", "music"}, wordStat.mostCommonWords(2));
        assertEquals(new String[] {"mouse", "music", "key", "keyboard"}, wordStat.mostCommonWords(4));
        assertEquals(new String[] {}, wordStat.mostCommonWords(0));
    }

    @Test(expected = IllegalArgumentException.class)
    public void testMostCommonWordsException() throws IOException {
        WordStat wordStat = new WordStat("file2.txt");
        wordStat.mostCommonWords(-2);
    }

    @Test
    public void testMostCommonWordsEmpty() throws IOException {
        WordStat wordStatEmpty = new WordStat("empty.txt");
        assertEquals(new String[] {}, wordStatEmpty.mostCommonWords(2));
    }

    @Test
    public void testLeastCommonWords() throws IOException {
        WordStat wordStat = new WordStat("file2.txt");
        assertEquals(new String[] {"bruh", "sentence"}, wordStat.leastCommonWords(2));
        assertEquals(new String[] {"bruh", "sentence", "ide", "macbook"}, wordStat.leastCommonWords(4));
        assertEquals(new String[] {}, wordStat.leastCommonWords(0));
    }

    @Test(expected = IllegalArgumentException.class)
    public void testLeaseCommonWordsException() throws IOException {
        WordStat wordStat = new WordStat("file2.txt");
        wordStat.mostCommonWords(-2);
    }

    @Test
    public void testLeastCommonWordsEmpty() throws IOException {
        WordStat wordStatEmpty = new WordStat("empty.txt");
        assertEquals(new String[] {}, wordStatEmpty.mostCommonWords(2));
    }

    @Test
    public void testMostCommonCollocations() throws IOException {
        WordStat wordStat = new WordStat("file2.txt");
        assertEquals(new String[] {"key", "music"}, wordStat.mostCommonCollocations(2, "mouse", true));
        assertEquals(new String[] {"key", "keyboard"}, wordStat.mostCommonCollocations(2, "mouse", false));
        assertEquals(new String[] {}, wordStat.mostCommonCollocations(0, "mouse", false));
    }

    @Test(expected = IllegalArgumentException.class)
    public void testMostCommonCollocationsNegativeK() throws IOException {
        WordStat wordStat = new WordStat("file2.txt");
        wordStat.mostCommonCollocations(-2, "abc", true);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testMostCommonCollocationsNoWord() throws IOException {
        WordStat wordStat = new WordStat("file2.txt");
        wordStat.mostCommonCollocations(2, "abcd", true);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testMostCommonCollocationsEmpty() throws IOException {
        WordStat wordStat = new WordStat("empty.txt");
        wordStat.mostCommonCollocations(2, "abcd", true);
    }
}
