import java.util.Arrays;
import java.util.concurrent.ThreadLocalRandom;

public class SortingAlgs {

    // swaps elements in array a at given index positions (i1 and i2)
    private static void swap(int[] a, int i1, int i2) {
        int temp = a[i1];
        a[i1] = a[i2];
        a[i2] = temp;
    }

    /* for max efficiency, the auxiliary array aux should be initialized
     *  in the wrapper sort method, before any recursive calls are made */
    private static void merge(int[] a, int[] aux, int lo, int mid, int hi) {
        assert isSorted(a, lo, mid + 1);
        assert isSorted(a, mid + 1, hi);
        for (int k = lo; k < hi; k++) {
            aux[k] = a[k];
        }
        int i = lo, j = mid + 1;
        for (int k = lo; k < hi; k++) {
            if (i > mid) a[k] = aux[j++];
            else if (j >= hi) a[k] = aux[i++];
            else if (aux[j] < aux[i]) a[k] = aux[j++];
            else a[k] = aux[i++];
        }

        assert isSorted(a, lo, hi);
    }


    // sorts the given array from index lo (inclusive) to hi (exclusive)
    private static void mergeSort(int[] a, int[] aux, int lo, int hi) {
        // base case:
        if (lo >= (hi - 1)) return; // hi - 1 because hi is exclusive
        // divide
        int mid = lo + (hi - 1 - lo) / 2; // hi - 1 because hi is exclusive
//        System.out.println("rec mergeSort: lo = " + lo + ", hi = " + hi + ", mid = " + mid);
//        System.out.println("calling mergeSort with lo = " + lo + " and hi = " + (mid + 1));
        mergeSort(a, aux, lo, mid + 1); // mid + 1 becomes the next hi, which is exclusive
        mergeSort(a, aux, mid + 1, hi);
        // and conquer
        merge(a, aux, lo, mid, hi); // in merge, only hi is exclusive, not mid
    }

    public static void mergeSort(int[] a) {
        int[] aux = new int[a.length]; // initialize the array before recursive calls for max efficiency
        mergeSort(a, aux, 0, a.length);
    }

    /**
     * Checks whether the part of the array from lo to hi (exclusive) is sorted
     *
     * @param a  int array
     * @param lo starting index (inclusive)
     * @param hi end index (exclusive)
     * @return true if array is sorted, false otherwise
     */
    private static boolean isSorted(int[] a, int lo, int hi) {
        for (int i = lo + 1; i < hi; i++) {
            if (a[i] < a[i - 1]) return false;
        }
        return true;
    }

    /**
     * 3-way quicksort (see Algs4)
     *
     * @param a  int array
     * @param lo start index, inclusive
     * @param hi end index, INCLUSIVE
     */
    private static void threeWayQuickSort(int[] a, int lo, int hi) {
        if (hi <= lo) return;
        int lt = lo, gt = hi;
        int pivot = a[lo];
        int i = lo;
        while (i <= gt) {
            if (a[i] < pivot) swap(a, lt++, i++);
            else if (a[i] > pivot)
                swap(a, i, gt--); // i can still be greater than pivot, so is not incremented
            else i++; // !!!
        }
        threeWayQuickSort(a, lo, lt - 1);
        threeWayQuickSort(a, gt + 1, hi); // i has crossed gt
    }

    public static void threeWayQuickSort(int[] a) {
        threeWayQuickSort(a, 0, a.length - 1);
    }

    private static void knuthShuffle(int[] a) {
        for (int i = 1; i < a.length; i++) {
            swap(a, i, ThreadLocalRandom.current().nextInt(i + 1)); //
        }
    }

    // basic strategy: (see also BinaryHeap.java)
    // first pass:
    // heapify the array in-place: impose binary heap order, starting from right
    // to left, using sink (O(n))
    // second pass:
    // sort the array by removing the next max/root, one at a time,
    // leaving it in the given array, but shrinking the size of virtual heap
    public static void heapSort(int[] a) {
        int n = a.length;
        // first pass: heapify
        for (int k = n / 2; k >= 1; k--) { // start from parent of the last
            // element/ last two elements of the array, construct heap
            // bottom-up (O(n) instead of n log(n) when going from left to right
            sink(a, k, n);
        }
        // second pass: sortdown
        while (n > 1) {
            swap(a, 0, --n); // swap uses regular indexing, [0, a.length-1]
            sink(a, 1, n);
        }
    }

//    private void sink(int k) {
//        while (2 * k <= n) { // while node at k has child(ren)
//            int j = 2 * k; // index of child
//            if (j < n && bHeap[j] < bHeap[j + 1]) j++; // if node at k has
//            // two children and the first is less than the second, choose 2nd
//            if (bHeap[k] >= bHeap[j]) break; // parent >= child
//            swap(bHeap, k, j);
//            k = j; // go down/sink one level
//        }
//    }

    // remember that indices in binary heap start from 1
    private static void sink(int[] a, int k, int n) {
        // parent node at index k
        while (2 * k <= n) { // while node at k has child(ren)
            int child = 2 * k;
            // when comparing/swapping elements in a, must use regular indexing,
            // so decrement each index by 1
            // This leads to terrible readability... Better implement helper
            // methods to get child and parent indices, but that's probably
            // more overhead...
            if (child < n && a[child - 1] < a[child]) child++; // chose child
            // with larger val
            if (a[k - 1] >= a[child - 1]) break; // parent >= larger child
            swap(a, k - 1, child - 1); // otherwise swap
            k = child; // go down/sink one level
        }
    }

    /**
     * Constructs and returns an int array of given size, with values
     * [0, maxVal)
     *
     * @param size   size of the new array
     * @param maxVal max value of element in array
     * @return int array with random values [0, maxVal)
     */
    private static int[] randArray(int size, int maxVal) {
        int[] a = new int[size];
        for (int i = 0; i < size; i++) {
            a[i] = ThreadLocalRandom.current().nextInt(maxVal);
        }
        return a;
    }

    public static void main(String[] args) {
        int size = 100;
//        int[] a = {3, 4, 6, 2, 1, 8, 9};
//        int[] a = {2, 1, 2, 3, 4, 5};
//        System.out.println(isSorted(a, 0, a.length));
//      create random array
//        int[] a = new int[size];
//        for (int i = 0; i < size; i++) {
//            a[i] = ThreadLocalRandom.current().nextInt(0, 100);
//        }
//        System.out.println(Arrays.toString(a));
////        mergeSort(a);
//        threeWayQuickSort(a);
//        assert isSorted(a, 0, a.length);
//        System.out.println(Arrays.toString(a));
        int[] testArray = randArray(11, 10);
        System.out.println("Unsorted array: " + Arrays.toString(testArray));
        heapSort(testArray);
        assert (isSorted(testArray, 0, testArray.length));
        System.out.println("Sorted array: " + Arrays.toString(testArray));

    }
}
