import java.util.concurrent.ThreadLocalRandom;

/**
 * This is an implementation of mergesort without recursion, based on the one 
 * presented in Princeton's course Algorithms 1.
 * Iterating thru the array and merging it's subarrays, using the standard merge
 * algorithm; The size of the subarrays starts at 1 and then doubles after each
 * iteration.
 */

public class Mergesort_bottom_up {
    
    public static void sort(int[] arr) {
        // copy the array once, then use indexing for merge
        int[] copy = new int[arr.length];
        for (int i = 0; i < arr.length; i++) {
            copy[i] = arr[i];
        }
        mergesort(arr, copy);
    }

    private static void mergesort(int[] arr, int[] copy) {
        for (int sz = 1; sz < arr.length; sz *= 2) {
            for (int i = 0; i < arr.length-sz; i += 2*sz) {
                merge(arr, copy, i, i+sz-1, 
                        i+sz, Math.min(i+sz+sz-1, arr.length-1));
            }

        }
    }

    // assums subarrays are sorted
    // lo and hi indices both INCLUSIVE!
    private static void merge(int[] arr, int[] copy, int lo1, int hi1, int lo2, 
                        int hi2) {
        if (lo1 >= hi2) return; // base case, one element to merge
        int i = lo1;
        int j  = lo2;
        int k = lo1; // current index of copy
        while (i <= hi1 && j <= hi2) {
            while (i <= hi1 && arr[i] < arr[j]) {
                // System.out.println("Copying " + arr[i] + " to copy index " + k);
                copy[k++] = arr[i++];   
            }
            while (j <= hi2 && arr[j] <= arr[i]) { // one must have `<=` for duplicates
                // System.out.println("Copying " + arr[j] + " to copy index " + k);
                copy[k++] = arr[j++];                
            }
        }
        // System.out.println("K is now " + k);
        while (i <= hi1) copy[k++] = arr[i++];
        while (j <= hi2) copy[k++] = arr[j++];
        // copy from copy to original arr
        for (int m = lo1; m <= hi2; m++) {
            arr[m] = copy[m];
        }
    }


    // prints the array of max length 100; if the number exeeds the max,
    // prints the first max elements
    private static String printArray(int[] arr) {
        int max = 100;
        String result = "[ ";
        // for (int el : arr) {
        //     result += el + " ";
        // }
        for (int i = 0; i < arr.length && i < max; i++) {
            result += arr[i] + " ";
        }
        result += "]";
        return result;
    }

    public static void main(String[] args) {
        // test merge
        // int[] arr = {4, 5, 7, 1, 2, 3, 4};
        // int[] copy = new int[arr.length];
        // merge(arr, copy, 0, 2, 3, 6);
        // System.out.println(printArray(arr));

        // test sort with borderline cases - empty array, array of length 1,
        // array with all duplicates
        int[] arr2 = {};
        sort(arr2);
        System.out.println(printArray(arr2));
        int[] arr3 = {1};
        sort(arr3);
        System.out.println(printArray(arr3));
        int[] arrOfDups = {2, 2, 2, 2, 2};
        sort(arrOfDups);
        System.out.println(printArray(arrOfDups));
        int[] arrOfDups2 = {2, 2, 2, 2};
        sort(arrOfDups2);
        System.out.println(printArray(arrOfDups2));


        // test sort on array with odd number of elements:
        int[] arr = {0, 4, 9, 11, 3, 4, 5, 0, 1, 2, 3};
        sort(arr);
        System.out.println(printArray(arr));

        // test sort on array with even number of elements, in descending order
        int[] arrDesc = {6, 5, 4, 3, 2, 1};
        sort(arrDesc);
        System.out.println(printArray(arrDesc));

        // check performance on a large array:
        int size = 10000000;
        int[] largeA = new int[size];
        for (int i = 0; i < size; i++) {
            largeA[i] = ThreadLocalRandom.current().nextInt(10000000);
        }
        long start = System.nanoTime();
        sort(largeA);
        long elapsed = System.nanoTime() - start;
        System.out.println("Time for sorting an array of size " + size + " is "
                            + elapsed);
        // print the beginning of the array (see printArray for max) 
        System.out.println(printArray(largeA));

    }

}
