import edu.princeton.cs.algs4.StdIn;
import edu.princeton.cs.algs4.StdOut;

import java.util.Iterator;
import java.util.NoSuchElementException;

// Random queue implementation using a resizing array.
public class ResizingArrayRandomQueue<Item> implements Iterable<Item> {
    // Fields
    private Item[] resizingArr; // Collection of Items
    private int amountOfItems; // Amount of Items currently in the queue

    // Constructs an empty queue of size 2
    public ResizingArrayRandomQueue() {
        this.resizingArr = (Item[]) new Object[2]; //Creates an Array of Items with initial size of 2
        this.amountOfItems = 0; // sets the amountOfItems field to 0
    }

    // Is the queue empty?
    public boolean isEmpty() {
        return amountOfItems == 0; // if amountOfItems is 0, then the return value will be true
    }

    // The number of items on the queue.
    public int size() {
        return amountOfItems; // amountOfItems holds number of items of queue
    }

    // Add item to the queue.
    public void enqueue(Item item) {
        // if user tries to add a null item, throws NullPointerException
        if (item == null)
            throw new NullPointerException();
        // if resizingArr is at full capacity, its size is doubled
        if (resizingArr.length == amountOfItems)
            this.resize(amountOfItems * 2);

        resizingArr[amountOfItems] = item; // Instantiates next free index of resizingArr with new Item instance
        amountOfItems++; // increments amountOfItems
    }

    // Helper method for resizing the underlying array.
    private void resize(int max) {
        Item[] temp = (Item[]) new Object[max];
        for (int i = 0; i < amountOfItems; i++) {
            if (resizingArr[i] != null) {
                temp[i] = resizingArr[i];
            }
        }
        resizingArr = temp;
    }


    // Remove and return a random item from the queue.
    public Item dequeue() {
        // if user tries to dequeue from an empty queue, throws NoSuchElementException
        if (amountOfItems == 0)
            throw new NoSuchElementException();
        int dequeuedItemIndex = ((int) (Math.random() * amountOfItems)); // computes value of random index
        Item removeMe = resizingArr[dequeuedItemIndex]; // Instantiates a new instance of item with element present at given random index
        resizingArr[dequeuedItemIndex] = resizingArr[amountOfItems - 1]; // Places value of last index element at removeMe index
        resizingArr[amountOfItems - 1] = null; // Erases last element from end of collection
        amountOfItems--; // Decrements amountOfItems
        return removeMe; // removed element is returned
    }

    // Return a random item from the queue, but do not remove it.
    public Item sample() {
        // if user tries to sample from an empty queue, throws NoSuchElementException
        if (amountOfItems == 0)
            throw new NoSuchElementException();
        int randomItemIndex = ((int) (Math.random() * amountOfItems)); // computes value of random index
        return resizingArr[randomItemIndex]; // returns element at specified index point
    }

    // An independent iterator over items in the queue in random order.
    public Iterator<Item> iterator() {
        return new RandomQueueIterator();
    }

    // An iterator, doesn't implement remove() since it's optional.
    private class RandomQueueIterator implements Iterator<Item> {
        Item[] items; // stores items of resizingArr
        int current; // holds the current item in items

        RandomQueueIterator() {
            items = (Item[]) new Object[amountOfItems]; // instantiates items with an array the same size as resizingArr
            for (int i = 0; i < amountOfItems; i++) { // loops through each item in resizingArr
                items[i] = resizingArr[i]; // copies items of resizingArr into same indices of items
            }
            for (int i = 0; i < amountOfItems; i++) { // loops through each item in items swapping their values with an item at a random index point
                int randomItemIndex = ((int) (Math.random() * amountOfItems)); // computes value of random index
                Item randomItem = items[randomItemIndex]; // temporarily stores a randomly selected item in items
                items[randomItemIndex] = items[i]; // reassigns randomly selected index's value to current item in loop
                items[i] = randomItem; // reassigns current item in loop's value to value of randomly selected index
            }
            current = 0; // initializes current to first index in array items
        }

        // Returns true if iterator has more items to iterate and false if it does not
        public boolean hasNext() {
            return current != (amountOfItems); // current should be equal to last index if there are no more items to iterate
        }

        public void remove() {
            throw new UnsupportedOperationException();
        }

        // Returns item at index current and increments current by 1
        public Item next() {
            // throws new NoSuchElementException if there are no more items to iterate through
            if (current == amountOfItems)
                throw new NoSuchElementException();
            Item currentItem = items[current]; // temporarily stores value of current index's item
            current++; // increments value of current by one
            return currentItem; // returns value of currentItem
        }
    }

    // A string representation of the queue.
    public String toString() {
        StringBuilder s = new StringBuilder();
        for (Item item : this) {
            s.append(item + " ");
        }
        return s.toString().substring(0, s.length() - 1);
    }

    // Test client. [DO NOT EDIT]
    public static void main(String[] args) {
        ResizingArrayRandomQueue<Integer> q =
                new ResizingArrayRandomQueue<Integer>();
        while (!StdIn.isEmpty()) {
            q.enqueue(StdIn.readInt());
        }
        int sum1 = 0;
        for (int x : q) {
            sum1 += x;
        }
        int sum2 = sum1;
        for (int x : q) {
            sum2 -= x;
        }
        int sum3 = 0;
        while (q.size() > 0) {
            sum3 += q.dequeue();
        }
        StdOut.println(sum1);
        StdOut.println(sum2);
        StdOut.println(sum3);
        StdOut.println(q.isEmpty());
    }
}
