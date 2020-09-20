import edu.princeton.cs.algs4.StdOut;
import edu.princeton.cs.algs4.StdRandom;

import java.util.Iterator;

// Deque implementation using a linked list.
public class LinkedDeque<Item> implements Iterable<Item> {
    private int n; //Counter for size of deque/number of nodes
    private Node first;
    private Node last;

    // Helper doubly-linked list class.
    private class Node {
        private Item item;
        private Node next;
        private Node prev;

        Node(Item i) {
            this.item = i;
        }
    }

    // Construct an empty deque.
    public LinkedDeque() {
        n = 0; // Setting the deque size to 0 to begin with
        first = null;
        last = null;
    }

    // Is the dequeue empty?
    public boolean isEmpty() {
        return n == 0; // Returns true if deque is empty, false if not
    }

    // The number of items on the deque.
    public int size() {
        return n;
    }

    // Add item to the front of the deque.
    public void addFirst(Item item) {
        if (item == null) {
            throw new java.lang.NullPointerException();
        }
        Node newFNode = new Node(item);
        if (n == 0) { // If the deque is empty, the new node becomes both first and last and points to null both ways
            first = newFNode;
            first.next = null;
            first.prev = null;
            last = newFNode;
        } else {
            Node temp = first; // Saving the old first as a temporary node
            first = newFNode; // Updating first to be the new node
            temp.prev = newFNode; // Updating the old first's prev pointer to point to the new first
            newFNode.next = temp; //Updating the new first's next pointer to point to the old first
        }
        n++; // Increasing the deque size counter as we added a node
    }

    // Add item to the end of the deque.
    public void addLast(Item item) {
        if (item == null) {
            throw new java.lang.NullPointerException();
        }
        Node newLNode = new Node(item);
        if (n == 0) { // If the deque is empty, the new node becomes both last and first and points to null both ways
            last = newLNode;
            last.prev = null;
            last.next = null;
            first = newLNode;
        } else {
            Node temp = last; // Saving the old first as a temporary node
            last = newLNode; // Updating last to be the new node
            temp.next = newLNode; // Updating the old last's next pointer to point to the new last
            newLNode.prev = temp; // Updating the new node's prev pointer to point to the old last
        }
        n++; // Increasing the deque size counter as we added a node

    }

    // Remove and return item from the front of the deque.
    public Item removeFirst() {
        if (n <= 0) {
            throw new java.util.NoSuchElementException();
        }
        Item tempFItem = first.item;
        if (n == 1) { // If there is only one node in the deque, change first and last to nulls
            first = null;
            last = null;
        } else {
            Node temp = first; // Saving the old first as a temporary variable
            first = temp.next; // Setting the new first to be the old last's next
            first.prev = null; // Update the new first's prev pointer to null
        }
        n--; // Decreasing the deque size counter as we removed an element
        return tempFItem; // Returning the item of the node we removed
    }

    // Remove and return item from the end of the deque.
    public Item removeLast() {
        if (n <= 0) {
            throw new java.util.NoSuchElementException();
        }
        Item tempLItem = last.item;
        if (n == 1) {
            last = null;
            first = null;
        } else {
            Node temp = last; // Saving the old last as a temporary variable
            last = temp.prev; // Setting the new last to be the old last's prev
            last.next = null; // Update the new last's next pointer to null
        }
        n--; // Decreasing the deque size counter as we removed an element
        return tempLItem; // Returning the item of the node we removed
    }

    // An iterator over items in the queue in order from front to end.
    public Iterator<Item> iterator() {
        return new DequeIterator(); // Creating an iterator object and returning it
    }

    // An iterator, doesn't implement remove() since it's optional.
    private class DequeIterator implements Iterator<Item> {
        Node current;

        DequeIterator() {
            current = first; // Setting the iterator's current node to point to first
        }

        public boolean hasNext() {
            return current.next != null; // If the current's next is null, return false
        }

        public void remove() {
            throw new UnsupportedOperationException();
        }

        public Item next() {
            if (current == null) {
                throw new java.util.NoSuchElementException();
            }
            Node temp = current; // Saving the current node as a temporary
            current = temp.next; // Updating current to be the old current's next
            return temp.item; // Returning the item of the old current node
        }
    }

    // A string representation of the deque.
    public String toString() {
        StringBuilder s = new StringBuilder();
        for (Item item : this) {
            s.append(item + " ");
        }
        return s.toString().substring(0, s.length() - 1);
    }

    // Test client. [DO NOT EDIT]
    public static void main(String[] args) {
        LinkedDeque<Character> deque = new LinkedDeque<Character>();
        String quote = "There is grandeur in this view of life, with its "
                + "several powers, having been originally breathed into a few "
                + "forms or into one; and that, whilst this planet has gone "
                + "cycling on according to the fixed law of gravity, from so "
                + "simple a beginning endless forms most beautiful and most "
                + "wonderful have been, and are being, evolved. ~ "
                + "Charles Darwin, The Origin of Species";
        int r = StdRandom.uniform(0, quote.length());
        for (int i = quote.substring(0, r).length() - 1; i >= 0; i--) {
            deque.addFirst(quote.charAt(i));
        }
        for (int i = 0; i < quote.substring(r).length(); i++) {
            deque.addLast(quote.charAt(r + i));
        }
        StdOut.println(deque.isEmpty());
        StdOut.printf("(%d characters) ", deque.size());
        for (char c : deque) {
            StdOut.print(c);
        }
        StdOut.println();
        double s = StdRandom.uniform();
        for (int i = 0; i < quote.length(); i++) {
            if (StdRandom.bernoulli(s)) {
                deque.removeFirst();
            } else {
                deque.removeLast();
            }
        }
        StdOut.println(deque.isEmpty());
    }
}
