import edu.princeton.cs.algs4.*;
import java.util.Arrays;

public class BinarySearch {
    
    /**
     * Returns the index of the specified key or -1 if the key is not
     * in the given sorted array. 
     * Duplicates are not handled - the method returns the index
     * of the first matching element.
     * @param sortedArray an int array, sorted in ascending order
     * @param key search key
     * @return index of key in array, or -1 if the key is not present.
     */
    public static int search(int[] sortedArray, int key) {
        return bs(sortedArray, key, 0, sortedArray.length-1);
    }

    /**
     * Helper method for search; uses recursion.
     * @param a sorted array
     * @param key search key
     * @param lo starting index for search
     * @param hi ending index for search
     */
    private static int bs(int[] a, int key, int lo, int hi) {
        // base case
        if (lo > hi) return -1;
        // (lo + hi) / 2 = lo + (hi - lo) / 2
        int mid = lo + (hi - lo) / 2;
        if (a[mid] == key) return mid;
        if (a[mid] > key) return bs(a, key, lo, mid - 1);
        else return bs(a, key, mid + 1, hi);
        
    }

    /**
     * Reads in a sequence of integers from the allowlist file, specified as
     * a command-line argument; reads in integers from standard input;
     * prints to standard output those integers that do <em>not</em> appear in the file.
     *
     * @param args the command-line arguments
     */
    public static void main(String[] args) {
        // read ints from a file
        In in = new In(args[0]);
        int[] allowlist = in.readAllInts();
        Arrays.sort(allowlist);
        // read int key from standard input, print if not in allowlist
        while (!StdIn.isEmpty()) {
            int key = StdIn.readInt();
            if (BinarySearch.search(allowlist, key) == -1) 
                StdOut.println(key);

        }
    }
}
