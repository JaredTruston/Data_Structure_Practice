import edu.princeton.cs.algs4.StdIn;
import edu.princeton.cs.algs4.StdOut;

// Takes a command-line integer k; reads in a sequence of strings from
// standard input; and prints out exactly k of them, uniformly at random.
// Note that each item from the sequence is printed out at most once.
public class Subset {
    public static void main(String[] args) {
        int k = Integer.parseInt((args[0])); // holds the number of strings to be printed
        // Constructs a new ResizingArrayRandomQueue object to hold each of the string instances
        ResizingArrayRandomQueue<String> stringsReadFromStandardInput = new ResizingArrayRandomQueue<String>();
        StdOut.println("Type the complete list of strings below (separated by spaces): "); // Prompts user to enter the complete sequence of Strings to choose from
        while (!StdIn.isEmpty()) { // Enqueues each of the strings printed to command line in the sequence until command line is empty
            stringsReadFromStandardInput.enqueue(StdIn.readString());
        }

        // throws an ArrayIndexOutOfBoundsException if the value of k exceeds the number of strings in the collection
        if (stringsReadFromStandardInput.size() < k) {
            throw new ArrayIndexOutOfBoundsException("The input value of the number of strings exceeds the length of the" +
                    " original collection of strings");
        } else {
            // prints out random strings within the collection (avoids repeats via dequeue method)
            while (!(k == 0)) {
                StdOut.println(stringsReadFromStandardInput.dequeue());
                k--;
            }
        }
    }
}
