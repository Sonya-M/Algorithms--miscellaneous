// max-oriented binary heap

public class BinaryHeap {

    // parent of node at index k = k/2
    // children of node at k = k * 2 and k * 2 + 1;

    private int n;
    private int maxSize;
    private int[] bHeap;

    // constructor with size for simplicity
    public BinaryHeap(int capacity) {
        maxSize = capacity + 1; // indices start at 1 and go to n, 0 ignored
        n = 0;
        bHeap = new int[maxSize];
    }

    public boolean isEmpty() {
        return n == 0;
    }

    // swaps elements in array a at given index positions (i1 and i2)
    private static void swap(int[] a, int i1, int i2) {
        int temp = a[i1];
        a[i1] = a[i2];
        a[i2] = temp;
    }

    private void swim(int k) {
        while (k > 1 && bHeap[k / 2] < bHeap[k]) { // while node has parent and
            // that parent is less than child
            swap(bHeap, k / 2, k);
            k /= 2;
        }
    }

    public int size() {
        return n;
    }

    public void insert(int x) {
        bHeap[++n] = x;
        swim(n);
    }

    private void sink(int k) {
        while (2 * k <= n) { // while node at k has child(ren)
            int j = 2 * k; // index of child
            if (j < n && bHeap[j] < bHeap[j + 1]) j++; // if node at k has
            // two children and the first is less than the second, choose 2nd
            if (bHeap[k] >= bHeap[j]) break; // parent >= child
            swap(bHeap, k, j);
            k = j; // go down/sink one level
        }
    }

    public int delMax() {
        int max = bHeap[1]; // indices star at 1, root has max
        swap(bHeap, 1, n--);
        sink(1); // sink the new root until heap order is restored
//        bHeap[n + 1] = a; // would be needed for objects to avoid loitering
        return max;
    }

    public static void main(String[] args) {
        char[] charArray = {'t', 'g', 'i', 'p', 'r', 'e', 'a', 'n', 'h', 'o'};
        int[] intArray = new int[charArray.length];
        for (int i = 0; i < charArray.length; i++) {
            intArray[i] = charArray[i];
        }
        BinaryHeap bh = new BinaryHeap(intArray.length);
        for (int next : intArray) {
            bh.insert(next);
        }
        while (!bh.isEmpty()) {
            System.out.println((char) bh.delMax());
        }
    }
}
