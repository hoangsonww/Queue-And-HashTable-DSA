import java.util.ArrayList;
import java.util.NoSuchElementException;

/**
 * A method that represents, creates, and stores a Priority Queue
 *
 * @param <K> Any types that extends Comparable with a super type of K
 * @param <V> Any types to be parsed into the priority queue
 * @author David Nguyen
 * @since 04/02/2023
 * @version 1.0
 */
public class PriorityQueue<K extends Comparable<? super K>,V> {

    // A field that stores the current priority, max-Heap
    private ArrayList<QueueNode<K,V>> priorityHeap;

    // A field that stores the current size of the queue (i.e. the number of elements currently in the queue)
    private int queueSize;

    /**
     * A constructor that creates this instance of Priority Queue that takes no parameters.
     * Time Complexity: O(1)
     *
     */
    public PriorityQueue() {
        this.priorityHeap = new ArrayList<QueueNode<K, V>>();
        this.queueSize = 0;
    }

    /**
     * A constructor that creates an instance of Priority Queue with the specified key and value arrays.
     * Time Complexity: O(N), where N is the length of the specified arrays.
     *
     * @param keys Any array of keys
     * @param values Any array of values
     * @throws IllegalArgumentException If the specified arrays differ in length, throw an IllegalArgumentException.
     */
    public PriorityQueue(K[] keys, V[] values) {
        // If the specified arrays differ in length, throw an IllegalArgumentException.
        if (keys.length != values.length) {
            throw new IllegalArgumentException("Keys and values arrays have different lengths");
        }
        // Else, do nothing
        else {
            ;
        }
        // Initialize this priority, max-heap
        this.priorityHeap = new ArrayList<QueueNode<K,V>>();
        // Initialize the size of this queue
        this.queueSize = keys.length;
        // A loop that loops through the values and keys in the given arrays to add it into the priority heap
        for (int index = 0; index < this.queueSize; index = index + 1) {
            this.priorityHeap.add(new QueueNode<K, V>(keys[index], values[index]));
        }
        // Variable that stores the index for heap fixing (max-Heapifying)
        int fixingHeapIndex = (this.queueSize / 2) - 1;
        // A loop that loops through the entire queue to fix the current max heap
        for (int index = fixingHeapIndex; index >= 0; index = index - 1) {
            fixMaxHeap(index);
        }
    }

    /**
     * A private constructor that creates an instance of PriorityQueue<K extends Comparable<? super K>,V> by taking in
     * another priority queue.
     * Time Complexity: O(1)
     *
     * @param priorityQueue Any priority queue to create a new priority queue with
     */
    private PriorityQueue (PriorityQueue priorityQueue) {
        // Initialize the necessary fields
        this.priorityHeap = priorityQueue.priorityHeap;
        this.queueSize = priorityQueue.queueSize;
    }

    /**
     * A method that adds the specified key-value pair to the priority queue.
     * Time Complexity: O(log N) average case, O(N) worst case, where N is the size of the priority queue.
     *
     * @param key Any key to be added to the priority queue
     * @param value Any value to be added to the priority queue
     */
    public void add(K key, V value) {
        // Add the new queueNode with the specified key and value to the queue
        this.priorityHeap.add(new QueueNode<K, V>(key, value));
        // Variable to store the current index for adding
        int index = this.queueSize;
        // Increase the queue size by 1
        this.queueSize = this.queueSize + 1;
        // Loops through the list to fix the heap as necessary (max-heapify)
        while (index > 0 &&
                this.priorityHeap.get(findIndexOfParent(index)).getKey().compareTo(this.priorityHeap.get(index).getKey()) < 0) {
            swap(index, findIndexOfParent(index));
            index = findIndexOfParent(index);
        }
    }

    /**
     * A method that updates a key corresponding to an element in the priority queue with the specified value, such that
     * it has the new specified key.
     * Time Complexity: O(N), where N is the size of the priority queue.
     *
     * @param key Any key to update with
     * @param value Any value to update with
     * @throws IllegalArgumentException If the specified value does not exist, throw a NoSuchElementException.
     */
    public void update(K key, V value) {
        // Variable to finds the index to update the key-value pairs
        int indexToUpdate = -1;
        // Loops through the queue to find for the specific key-value pair
        for (int tempIndex = 0; tempIndex < this.queueSize; tempIndex = tempIndex + 1) {
            // Variable to keeps track of whether the specific key-value pair exists in the queue
            boolean checkForEquality = this.priorityHeap.get(tempIndex).getValue().equals(value);
            // If so, update the indexToUpdate variable to the current loop index and break the loop
            if (checkForEquality == true) {
                indexToUpdate = tempIndex;
                break;
            }
            // Else, do nothing and let the method continue as normal
            else {
                ;
            }
        }
        // Now, check the indexToUpdate. If it's still -1, it means that the specified value does not exist in the queue.
        // Also throws a NoSuchElementException.
        if (indexToUpdate == -1) {
            throw new NoSuchElementException("Value not found in the priority queue");
        }
        // Set the key at the indexToUpdate variable to be the specified key
        this.priorityHeap.get(indexToUpdate).setKey(key);
        // Loops through the list to fix the heap as necessary (max-heapify)
        while (indexToUpdate > 0 &&
                this.priorityHeap.get(findIndexOfParent(indexToUpdate)).getKey().compareTo(this.priorityHeap.get(indexToUpdate).getKey()) < 0) {
            swap(indexToUpdate, findIndexOfParent(indexToUpdate));
            indexToUpdate = findIndexOfParent(indexToUpdate);
        }
    }

    /**
     * A method that returns the value corresponding to the greatest key from the priority queue.
     * Time Complexity: O(1)
     *
     * @return the value corresponding to the greatest key from the priority queue.
     * @throws NoSuchElementException If the priority queue is empty, throw an NoSuchElementException.
     */
    public V peek() {
        // If the priority queue is empty, throw an NoSuchElementException
        if (this.queueSize == 0) {
            throw new NoSuchElementException("Priority queue is empty");
        }
        // Do thing if otherwise and let the method continue normally
        else {
            ;
        }
        // Variable to store the current greatest key from the priority queue.
        V returnValue = this.priorityHeap.get(0).getValue();
        // Return it
        return returnValue;
    }

    /**
     * A method that returns an array containing the k values corresponding to the greatest k keys from the priority queue.
     * Time Complexity: O(k log N), where N is the size of the priority queue.
     * Space Complexity: O(k).
     *
     * @param k The k to find the values with
     * @return An array containing the k values corresponding to the greatest k keys from the priority queue.
     * @throws IllegalArgumentException If k is greater than the priority queue's size or less than 0.
     */
    public V[] peek(int k) {
        // If k is greater than the priority queue's size or less than 0, throw an IllegalArgumentException.
        if (k > this.priorityHeap.size()) {
            throw new IllegalArgumentException("k must be less than or equal to the priority queue's size");
        }
        // Do thing if otherwise and let the method continue normally
        else {
            ;
        }
        // If k is greater than the priority queue's size or less than 0, throw an IllegalArgumentException.
        if (k < 0) {
            throw new IllegalArgumentException("k must be greater than or equal to 0!");
        }
        // Do thing if otherwise and let the method continue normally
        else {
            ;
        }
        // Variable to store the temporary priority queue for future reference
        PriorityQueue<K, V> tempPriorityQueue = new PriorityQueue<K, V>(this);
        // Variable to store the result array
        Object[] tempResult = new Object[k];
        // Loop that polls the k-largest elements from the priority queue
        for (int index = 0; index < k; index = index + 1) {
            tempResult[index] = tempPriorityQueue.poll();
        }
        // Type-cast the above array and return it
        V[] resultArray = (V[]) tempResult;
        return resultArray;
    }

    /**
     * A method that removes the element corresponding to the greatest key from the priority queue, and returns its
     * corresponding value.
     * Time Complexity: O(log N), where N is the size of the priority queue.
     *
     * @return The element corresponding to the greatest key from the priority queue.
     * @throws NoSuchElementException If no elements exist, throw a NoSuchElementException.
     */
    public V poll() {
        // If no elements exist, throw a NoSuchElementException.
        if (this.queueSize == 0) {
            throw new NoSuchElementException("Priority queue is empty");
        }
        // Else, do nothing.
        else {
            ;
        }
        // Variable to store the max value of the queue
        V maxValue = this.priorityHeap.get(0).getValue();
        // Variable to store the node to be removed
        QueueNode<K, V> nodeToRemove = this.priorityHeap.get(this.queueSize - 1);
        // Now removed it from the queue and decrement the queue's size
        this.priorityHeap.set(0, nodeToRemove);
        this.queueSize = this.queueSize - 1;
        // Then, fix the max heap as necessary and return the key associated with the given value
        fixMaxHeap(0);
        return maxValue;
    }

    /**
     * A method that removes an element from the priority queue corresponding to the specified value and returns its
     * corresponding key.
     * Time Complexity: O(N), where N is the size of the priority queue.
     *
     * @param value Any value to find the element with.
     * @return The value's corresponding key.
     * @throws NoSuchElementException If the specified value does not exist, throw a NoSuchElementException.
     */
    public K poll(V value) {
        // Variable that stores the index of the node to be removed
        int index = -1;
        // Loops through the queue to find if such value exists in the queue
        for (int tempIndex = 0; tempIndex < this.queueSize; tempIndex = tempIndex + 1) {
            // Variable that stores whether the specified value exists in the queue
            boolean checkForEquality = this.priorityHeap.get(tempIndex).getValue().equals(value);
            // If yes, set the index of the node to be removed to the current index and break the loop
            if (checkForEquality == true) {
                index = tempIndex;
                break;
            }
            // Else, do nothing and ignore it
            else {
                ;
            }
        }
        // If index is still -1, throw a NoSuchElementException as the specified value does not exist.
        if (index == -1) {
            throw new NoSuchElementException("Value not found in the priority queue");
        }
        // Else, do nothing and ignore it
        else {
            ;
        }
        // Variable to store the key got from the heap
        K key = this.priorityHeap.get(index).getKey();
        // Variable to store the node to be removed
        QueueNode<K, V> nodeToRemove = this.priorityHeap.get(this.queueSize - 1);
        // Remove the node as necessary and decrement the queue's size
        this.priorityHeap.set(index, nodeToRemove);
        this.queueSize = this.queueSize - 1;
        // Then, fix the max heap as necessary and return the key associated with the given value
        fixMaxHeap(index);
        return key;
    }

    /**
     * A method that returns the number of elements currently being stored in the priority queue.
     * Time Complexity: O(1)
     *
     * @return The number of elements currently being stored in the priority queue.
     */
    public int size() {
        // Variable to store the number of elements currently being stored in the priority queue.
        int returnVal = this.queueSize;
        return returnVal;
    }

    /**
     * A private class to store and create a node in the priority queue.
     *
     * @param <K> Any type that extends Comparable.
     * @param <V> Any value.
     * @author David Nguyen
     * @since 04/02/2023
     * @version 1.0
     */
    private class QueueNode<K, V> {

        // A field that stores the key of the node
        private K key;

        // A field that stores the value of the node
        private V value;

        /**
         * A constructor to create a Queue Node with the specified key and value pair.
         * Time Complexity: O(1)
         *
         * @param key Key to create a node with.
         * @param value Value to create a node with.
         */
        public QueueNode(K key, V value) {
            // Initialize the fields
            this.value = value;
            this.key = key;
        }

        /**
         * A method that gets the key of this queue node
         * Time Complexity: O(1)
         *
         * @return The key of this queue node
         */
        public K getKey() {
            return this.key;
        }

        /**
         * A method that sets the key of this queue node
         * Time Complexity: O(1)
         *
         * @param key The key to be set
         */
        public void setKey(K key) {
            this.key = key;
        }

        /**
         * A method that gets the value of this queue node.
         * Time Complexity: O(1)
         *
         * @return The value of this queue node
         */
        public V getValue() {
            return value;
        }

    }

    /**
     * A method that helps find the index of the parent of this index.
     * Time Complexity: O(1)
     *
     * @param index The index from which the parent will be found.
     * @return The index of the parent of this index.
     */
    private int findIndexOfParent(int index) {
        // Variable that stores the index of the parent of this index and return it.
        int indexOfParent = (index - 1) / 2;
        return indexOfParent;
    }

    /**
     * A method that helps find the index of the left child of this index.
     * Time Complexity: O(1)
     *
     * @param index The index from which the left child will be found.
     * @return The index of the left child of this index.
     */
    private int findIndexOfLeftChild(int index) {
        // Variable that stores the index of the left child of this index and return it.
        int indexOfLeftChild = (2 * index) + 1;
        return indexOfLeftChild;
    }

    /**
     * A method that helps find the index of the right child of this index.
     * Time Complexity: O(1)
     *
     * @param index The index from which the right child will be found.
     * @return The index of the right child of this index.
     */
    private int findIndexOfRightChild(int index) {
        // Variable that stores the index of the right child of this index and return it.
        int indexOfRightChild = (2 * index) + 2;
        return indexOfRightChild;
    }

    /**
     * A method that swaps the nodes at the two given indices.
     * Time Complexity: O(1)
     *
     * @param index1 First index to be swapped
     * @param index2 Second index to be swapped
     */
    private void swap(int index1, int index2) {
        // Variable that stores the temporary node to be swapped
        QueueNode<K, V> tempNode = this.priorityHeap.get(index1);
        // Swaps the nodes at the two indices
        this.priorityHeap.set(index1, this.priorityHeap.get(index2));
        this.priorityHeap.set(index2, tempNode);
    }

    /**
     * A method that fixes the max-heap.
     * Time Complexity: O(log N) where N is the number of words in the priority queue.
     *
     * @param index Any given index to fix the heap with
     */
    private void fixMaxHeap(int index) {
        // Variable that stores the index of the left child of the node at the current index
        int indexOfLeftChild = findIndexOfLeftChild(index);
        // Variable that stores the index of the right child of the node at the current index
        int indexOfRightChild = findIndexOfRightChild(index);
        // Variable that stores the current index
        int currentIndex = index;
        // If left child of this node is greater than itself, swap it
        if ((indexOfLeftChild < this.priorityHeap.size()) &&
                (this.priorityHeap.get(indexOfLeftChild).getKey().compareTo(this.priorityHeap.get(index).getKey()) > 0)) {
            currentIndex = indexOfLeftChild;
        }
        // If right child of this node is greater than itself, swap it
        if ((indexOfRightChild < this.priorityHeap.size()) &&
                (this.priorityHeap.get(indexOfRightChild).getKey().compareTo(this.priorityHeap.get(currentIndex).getKey()) > 0)) {
            currentIndex = indexOfRightChild;
        }
        // If the current index is different from the index variable above, the heap isn't in order. Fix it as necessary
        if (currentIndex != index) {
            swap(index, currentIndex);
            fixMaxHeap(currentIndex);
        }
        // Else, do nothing and let the method continue normally
        else {
            ;
        }
    }

}