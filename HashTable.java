import java.util.LinkedList;
import java.util.NoSuchElementException;

/**
 * A class that represents, creates, and stores a Hash Table that utilizes Separate Chaining to handle collisions.
 *
 * @param <T> Any type to store in the Hash Table
 * @author David Nguyen
 * @since 04/02/2023
 * @version 1.0
 */
public class HashTable<T> {

    // Field to keep track of the capacity of the hash table
    private int capacity;

    // Field to keep track of the total number of elements currently in the hash table
    private int numElements;

    // Field to store the hash table
    private LinkedList<HashNode<T>>[] hashTable;

    // Field to store the maximum load factor of this hash table
    private static final double maximumLoadFactor = 1.0;

    /**
     * A constructor that creates a Hash Table with a default capacity of 10.
     * Time Complexity: O(1)
     *
     */
    public HashTable() {
        // Initialize all variables and fields as appropriate
        this.capacity = 10;
        this.numElements = 0;
        this.hashTable = new LinkedList[10];
    }

    /**
     * A constructor that creates a Hash Table with the specified capacity.
     * Time Complexity: O(1)
     *
     * @param capacity Any specified capacity to create a Hash Table
     * @throws IllegalArgumentException When the capacity given is negative, throw an IllegalArgumentException
     */
    public HashTable(int capacity) {
        // When the capacity given is negative, throw an IllegalArgumentException
        if (capacity < 0) {
            throw new IllegalArgumentException("The entered capacity is negative, which is illegal. It must be greater than or above to 0");
        }
        // Initialize all variables and fields as appropriate
        else {
            this.capacity = capacity;
            this.numElements = 0;
            this.hashTable = new LinkedList[capacity];
        }
    }

    /**
     * A method that returns the value associated with the specified key.
     * Time Complexity: O(N) worst case where N is the number of elements in the table.
     *
     * @param key Any key to get the value with
     * @return The value associated with the specified key
     * @throws IllegalArgumentException If the key does not exist, throw an IllegalArgumentException
     */
    public T get(String key) {
        // A variable to keep track of the hash code of the given key
        int indexToGet = getHashCodeOfKey(key);
        // A variable that keeps track of the bucket of the key to search for it
        LinkedList<HashNode<T>> currentBucket = this.hashTable[indexToGet];
        // If the current bucket is null, throw an IllegalArgumentException as the key is likely not exist
        if (currentBucket == null) {
            throw new NoSuchElementException("The given key cannot be found in the hash table because the bucket is currently empty!");
        }
        // Variable that keeps track of the return value
        T returnValue = null;
        // A loop that loops through the current bucket to find the value associated with the given key
        for (HashNode<T> tempNode : currentBucket) {
            // Variable to keep track of the current key of the current node to be searched
            String currentKey = tempNode.getKey();
            // If the current key is the same as the given key, return it
            if (currentKey.equals(key)) {
                returnValue = tempNode.getValue();
            }
        }
        // Check one last time: if the return variable is null, throw an IllegalArgumentException
        if (returnValue == null) {
            throw new NoSuchElementException("The given key cannot be found in the hash table!");
        }
        // Return the value associated with the given key
        return returnValue;
    }

    /**
     * A method that stores the specified key-value pair in the hash table. Also rehashes the table if necessary.
     * Time Complexity: O(N) worst case, where N is the size of the hash table.
     *
     * @param key Any key to be added to the hash table
     * @param value Any value to be added to the hash table
     */
    public void put(String key, T value) {
        // Check if the value is an instance of Integer, call the appropriate helper method
        if (value instanceof Integer) {
            putInteger(key, value);
        }
        // Check if the value is NOT an instance of Integer, call the appropriate helper method
        else {
            putElse(key, value);
        }
        // Variable that keeps track of the current load factor of the table
        double currentLoadFactor;
        currentLoadFactor = (double) (this.numElements / this.hashTable.length);
        // If the current load factor is greater than or equal to 1.0, rehash the table
        if (currentLoadFactor >= this.maximumLoadFactor) {
            reHashTable();
        }
        else {
            ;
        }
    }

    /**
     * A method that removes a key-value pair associated with the specified key and returns the associated value.
     * Time Complexity: O(N) worst case, where N is the size of the hash table.
     *
     * @param key Any key to remove the key-value pair with
     * @return The value of the newly removed key-value pair
     * @throws NoSuchElementException If the key does not exist, throw an NoSuchElementException
     */
    public T remove(String key) {
        // Check if the value is an instance of Integer, call the appropriate helper method
        int hashCode = getHashCodeOfKey(key);
        // A variable that keeps track of the bucket of the key to search for it
        LinkedList<HashNode<T>> currentBucket = this.hashTable[hashCode];
        // If the current bucket is null, throw an IllegalArgumentException as the key is likely not exist
        if (currentBucket == null) {
            throw new NoSuchElementException("The hash table is empty!");
        }
        // Variable that keeps track of the return value
        T returnValue = null;
        // A loop that loops through the current bucket to find the value associated with the given key
        for (HashNode<T> hashNode : currentBucket) {
            // Variable to keep track of the current key of the current node to be searched
            String tempKey = hashNode.getKey();
            // If the current key is the same as the given key, remove it and decrement the number of elements
            if (tempKey.equals(key)) {
                currentBucket.remove(hashNode);
                this.numElements = this.numElements - 1;
                returnValue = hashNode.getValue();
                break;
            }
        }
        // Check one last time: if the return variable is null, throw an NoSuchElementException
        if (returnValue == null) {
            throw new NoSuchElementException("The given key is not found in the hash table!");
        }
        // Return the value associated with the given key
        return returnValue;
    }

    /**
     * A method that returns the number of elements currently in the hash table.
     * Time Complexity: O(1)
     *
     * @return The number of elements currently in the hash table.
     */
    public int size() {
        // Variable that keeps track of the number of elements currently in the hash table
        int numElements = this.numElements;
        return numElements;
    }

    /**
     * A helper class that creates a hash node in the hash table that contains a key, value pair.
     *
     * @param <T> Any generic type to store in the Hash Table
     */
    public static class HashNode<T>{

        // A field that stores the key for the hash node
        private String key;

        // A field that stores the value for the hash node
        private T value;

        // A variable that stores the next node reference of the current hash node
        private HashNode<T> nextNode;

        /**
         * A constructor that creates a hash node with the specified key, value pair.
         * Time Complexity: O(1)
         *
         * @param key Any key to create a hash node with
         * @param value Any value to create a hash node with
         */
        public HashNode(String key, T value) {
            this.key = key;
            this.value = value;
            this.nextNode = null;
        }

        /**
         * A method that returns the key of the hash node
         * Time Complexity: O(1)
         *
         * @return The key of the node
         */
        public String getKey() {
            return this.key;
        }

        /**
         * A method that returns the value of the hash node
         * Time Complexity: O(1)
         *
         * @return The value of the node
         */
        public T getValue() {
            return this.value;
        }

        /**
         * A method that sets the value of the hash node
         * Time Complexity: O(1)
         *
         * @param value The value of the node
         */
        public void setValue(T value) {
            this.value = value;
        }

    }

    /**
     * A method that helps get and compute the hash code of the given key
     * Time Complexity: O(1)
     *
     * @param key Any key to find the hash code
     * @return The hash code of the key
     */
    private int getHashCodeOfKey(String key) {
        // Variable to keep track of the key's hash code generated by the hashCode() function of String
        int tempCode = key.hashCode();
        // Variable to keep track of the key's hash code
        int hashCode = 0;
        // Compute the hash code specifically for this table
        hashCode = Math.abs(tempCode % this.hashTable.length);
        return hashCode;
    }

    /**
     * A method that helps and compute get the hash code of the given key when rehashing
     * Time Complexity: O(1)
     *
     * @param key Any key to find the hash code
     * @param newCapacity The new capacity of the newly rehashed table
     * @return The new hash code of the key
     */
    private int getHashCodeOfKeyWhenRehashed(String key, int newCapacity) {
        // Variable to keep track of the key's hash code generated by the hashCode() function of String
        int tempCode = key.hashCode();
        // Variable to keep track of the key's hash code
        int hashCode = 0;
        // Compute the hash code specifically for this table
        hashCode = Math.abs(tempCode % newCapacity);
        return hashCode;
    }

    /**
     * A helper method that stores the specified key-value pair in the hash table. Also rehashes the table if necessary.
     * Only used when the value is an instance of Integer.
     * Time Complexity: O(N) where N is the size of the table
     *
     * @param key Any key to be added to the hash table
     * @param value Any value to be added to the hash table
     */
    private void putInteger(String key, T value){
        // Variable to store the hash code of the given key
        int hashCode = getHashCodeOfKey(key);
        // Variable to store the bucket in which the given key currently is
        LinkedList<HashNode<T>> currentBucket = this.hashTable[hashCode];
        // If list is null, i.e. the key might not be in it, add a new bucket to the current table to account for the new node
        if (currentBucket == null) {
            currentBucket = new LinkedList<HashNode<T>>();
            this.hashTable[hashCode] = currentBucket;
        }
        /* A loop that loops through the current bucket if there is any matching key in the bucket to set it new value
         * to the given value. */
        for (HashNode<T> currentNode : currentBucket) {
            // Variable that stores the current key of the current node in the bucket
            String tempKey = currentNode.getKey();
            // If the key that is the same as the given key, it means that we have found the key-value pair. Update its value.
            if (tempKey.equals(key)) {
                // Check one more time to see if the values are instances of Integer
                if ((currentNode.getValue() instanceof Integer) && (value instanceof Integer)) {
                    // Variable to store the current node's value
                    int tempValue = (Integer) currentNode.getValue();
                    // Variable to store the value parsed in from the parameter
                    int tempValue1 = (Integer) value;
                    // Variable to store the value to be replaced with the existing key-value pair
                    int valueToReplace = tempValue + tempValue1;
                    // Cast it to Integer, then set the current node's value to this Integer instance
                    Integer newValue = new Integer(valueToReplace);
                    currentNode.setValue((T) newValue);
                }
                // If the values are not instances of Integer, simply replace its value with the new value
                else if (!(currentNode.getValue() instanceof Integer) && !(value instanceof Integer)) {
                    currentNode.setValue((T) value);
                }
                // Stops the function as necessary
                return;
            }
        }
        // Create a new hash node if no existing values is found in the bucket or table
        HashNode<T> newNode = new HashNode<T>(key, (T) value);
        // Then add that new node
        currentBucket.add(newNode);
        // Update the number of elements count by 1
        this.numElements = this.numElements + 1;
        // Variable to store the load factor of the current table to check for the need for rehashing
        double currentLoadFactor;
        currentLoadFactor = (double) (this.numElements / this.hashTable.length);
        // If current load factor is greater than or equal to 1.0, rehash it
        if (currentLoadFactor >= this.maximumLoadFactor) {
            reHashTable();
        }
        // Otherwise, stops the function as necessary
        else {
            return;
        }
    }

    /**
     * A helper method that stores the specified key-value pair in the hash table. Also rehashes the table if necessary.
     * Only used when the value is NOT an instance of Integer.
     * Time Complexity: O(N) where N is the size of the table
     *
     * @param key Any key to be added to the hash table
     * @param value Any value to be added to the hash table
     */
    private void putElse(String key, T value) {
        // Variable to store the hash code of the given key
        int hashCode = getHashCodeOfKey(key);
        // Variable to store the bucket in which the given key currently is
        LinkedList<HashNode<T>> currentBucket = this.hashTable[hashCode];
        // If list is null, i.e. the key might not be in it, add a new bucket to the current table to account for the new node
        if (currentBucket == null) {
            currentBucket = new LinkedList<HashNode<T>>();
            this.hashTable[hashCode] = currentBucket;
        }
        /* A loop that loops through the current bucket if there is any matching key in the bucket to set it new value
         * to the given value. */
        for (HashNode<T> tempNode : currentBucket) {
            // Variable that stores the current key of the current node in the bucket
            String tempKey = tempNode.getKey();
            // If value with the key same as the given key is found, simply replace its value with the new value
            if (tempKey.equals(key)) {
                tempNode.setValue((T) value);
                return;
            }
        }
        // Create a new hash node if no existing values is found in the bucket or table
        HashNode<T> newNode = new HashNode<T>(key, (T) value);
        // Then add that new node
        currentBucket.add(newNode);
        // Update the number of elements count by 1
        this.numElements = this.numElements + 1;
        // Variable to store the load factor of the current table to check for the need for rehashing
        double currentLoadFactor;
        currentLoadFactor = (double) (this.numElements / this.hashTable.length);
        // If current load factor is greater than or equal to 1.0, rehash it
        if (currentLoadFactor >= this.maximumLoadFactor) {
            reHashTable();
        }
        // Otherwise, stops the function as necessary
        else {
            return;
        }
    }

    /**
     * A method that rehash the table when called by increasing its size and update the positions of its values
     * Time Complexity: O(N) where N is the size of the table
     *
     */
    private void reHashTable() {
        // Variable to store the new capacity of the hash table
        int newCapacity = this.capacity * 2;
        // Variable to store the current copy of the hash table
        LinkedList<HashNode<T>>[] currentHashTable = this.hashTable;
        // Variable to store the new hash table with the new capacity
        LinkedList<HashNode<T>>[] newHashTable = new LinkedList[newCapacity];
        // Update the field capacity to the new capacity
        this.capacity = newCapacity;
        // A loop that updates the new table with new buckets as necessary
        for (int i = 0; i < newCapacity; i = i + 1) {
            newHashTable[i] = new LinkedList<HashNode<T>>();
        }
        // A loop that updates the new table with the existing nodes as necessary
        for (LinkedList<HashNode<T>> hashBucket : currentHashTable) {
            /* If the hash bucket is not null, calls another loop that would compute new hash codes for the key and add
             * nodes with the new positions into the table */
            if (hashBucket != null) {
                // A loop that would compute new hash codes for the key and add nodes with the new positions into the table
                for (HashNode<T> hashNode : hashBucket) {
                    // Variable to store a new hash code for each key when rehashed
                    int hashCode = getHashCodeOfKeyWhenRehashed(hashNode.getKey(), newCapacity);
                    newHashTable[hashCode].add(hashNode);
                }
            }
            // If the current bucket is not null, do nothing and ignore it
            else {
                ;
            }
        }
        // Update the current hash table to be the new hash table
        this.hashTable = newHashTable;
    }

}