//TODO: finish this - add the delete method

import java.util.LinkedList;
import java.util.Queue;

/**
 * Based on the implementation presented in Princeton's course Algorithms pt 1,
 * on Coursera.
 * The algorithm has a direct correspondence to 2-3 trees, but the implementation
 * is more simple with a regular binary tree with one addition - left-leaning
 * links to child nodes. Nodes with such links correspond to 3-nodes in 2-3 
 * trees. For full details, see Chapter 3.3 in Algorithms, 4th ed.
 * *****************************************************************************
 */

 public class RedBlackTree<Key extends Comparable<Key>, Value> {

    // constants used for clarity
    private static final boolean RED = true;
    private static final boolean BLACK = false;

    // the root of this RedBlackTree
    private Node root;

    private class Node {
        Key key; 
        Value val;
        Node left, right; // references to this node's child nodes/subtrees
        int N; // # nodes in this subtree
        boolean color;

        Node(Key key,  Value val, int N, boolean color) {
            this.key = key;
            this.val = val;
            this.N = N;
            this.color = color;
        }
    }

    public boolean isEmpty() {
        return size(root) == 0;
    }

    private boolean isRed(Node x) {
        if (x == null) return false;
        return x.color = RED;
    }

    private Node rotateLeft(Node h) {
        Node x = h.right;
        h.right = x.left;
        x.left = h;
        x.color = h.color;
        h.color = RED;
        x.N = h.N;
        h.N = 1 + size(h.left) + size(h.right);
        return x;
    }

    private Node rotateRight(Node h) {
        Node x = h.left;
        h.left = x.right;
        x.right = h;
        x.color = h.color;
        h.color = RED;
        x.N = h.N;
        h.N = 1 + size(h.left) + size(h.right);
        return x;
    }

    private void flipColors(Node h) {
        h.color = RED;
        h.left.color = BLACK;
        h.right.color = BLACK;
    }

    private int size(Node x) {
        if (x == null) return 0;
        else return x.N;
    }

    public void put(Key key, Value val) {
        root = put(root, key, val);
        root.color = BLACK;
    }

    // see page 439 in Algs 4th ed & 33BalancedSearchTrees.pdf, esp. pp 32 & 37
    // The diagrams in 33BalancedSearchTrees.pdf p 32 provides better insight 
    // into why this works and why the order of the operations (if
    // (isRed(...)..)) is crucial (the second row shows the 3 possible scenarios
    // after insertion): The MOST COMPLICATED case is when the node to be
    // inserted is btw the two values in a 3-node (the rightmost diagram), SO
    // CHECK THAT FIRST [isRed(h.right) && !isRed(h.left)] - after a left
    // rotation, you have basically REDUCED it to the case in the middle, where
    // the key to be inserted is smaller - SO CHECK THAT NEXT [isRed(h.left) &&
    // isRed(h.left.left)]. Once you rotate h right, you have REDUCED IT AGAIN
    // to the SIMPLEST CASE [isRed(h.left) && isRed(h.right)], so call
    // flipColors(h) and move up the tree, repeating any or all of these three
    // operations as needed...
    private Node put(Node h, Key key, Value val) {
        if (h == null) return new Node(key, val, 1, RED);
        ////every time we add a node we just create a red link to its parents,
        // thus changing the two node into a three node
        int cmp = key.compareTo(h.key);
        if (cmp < 0) h.left = put(h.left, key, val);
        else if (cmp > 0) h.right = put(h.right, key, val);
        else h.val = val;
        if (isRed(h.right) && !isRed(h.left)) h = rotateLeft(h);
        if (isRed(h.left) && isRed(h.left.left)) h = rotateRight(h);
        if (isRed(h.left) && isRed(h.right)) flipColors(h);

        h.N = size(h.left) + size(h.right) + 1;
        return h;
    }

    // most other ops (get(), floor(), iteration, selection) are the same
    // as for elementary BST (ignoring color), but run faster because of
    // better balance

    public Value get(Key key) {
        Node x = root;
        while (x != null) {
            int cmp = key.compareTo(x.key);
            if (cmp < 0) x = x.left;
            else if (cmp > 0) x = x.right;
            else return x.val; // cmp == 0
        }
        return null;
    }

    // 3.3.39 Delete the minimum. Implement the deleteMin() operation for
    // red-black BSTs by maintaining the correspondence with the transformations
    // given in the text for moving down the left spine of the tree while
    // maintaining the invariant that the current node is not a 2-node. 
    // (see textbook p 455 ff) 
    // TODO: test this code and implement deleteMax() and delete()
    
    // public void deleteMin() {
    //     if(!isRed(root.left) && !isRed(root.right)) {
    //         root.color = RED;
    //     }
    //     root = deleteMin(root);
    //     if (!isEmpty()) root.color = BLACK;
    // }
    // private Node deleteMin(Node h) {
    //     if (h.left == null) return null;
    //     if (!isRed(h.left) && !isRed(h.left.left)) h = moveRedLeft(h);
    //     h.left = deleteMin(h.left);
    //     return balance(h);
    // }
    // private Node balance(Node h) {
    //     if (isRed(h.right)) h = rotateLeft(h);
    //     return h;
    // }
    // private Node moveRedLeft(Node h) {
    //     // Assuming that h is red and both h.left and h.left.left
    //     // are black, make h.left or one of its children red.
    //     flipColors(h);
    //     if (isRed(h.right.left)) {
    //         h.right = rotateRight(h.right);
    //         h = rotateLeft(h);
    //     }
    //     return h;
    // }
    /**
     * Returns the number of keys smaller than a given key
     */
    public int rank(Key key) {
        return rank(key, root);
    }

    // returns number of keys less than x.key in the subtree rooted at x
    private int rank(Key key, Node x) {
        if (x == null) return 0;
        int cmp = key.compareTo(x.key);
        if (cmp < 0) return rank(key, x.left);
        else if (cmp > 0) return 1 + size(x.left) + rank(key, x.right);
        else return size(x.left);
    }

    /**
     * Returns the key of given rank, or null if the tree is empty.
     * NB: rank -> the number of keys smaller than a given key
     */
    public Key select(int rank) {
        if (rank > size(root)) return null;
        assert select(root, rank) != null;
        return select(root, rank).key;
    }
    // returns Node containing key of given rank
    private Node select(Node x, int rank) {
        if (x == null) return null;
        int lsz = size(x.left);
        if (lsz > rank) return select(x.left, rank);
        else if (lsz < rank) return select(x.right, rank-lsz-1);
        else return x;
    }

    /**
     * @return key associated with the minimum value in the tree, or null if
     * the tree is empty
     */
    public Key min() {
        if (size(root) == 0) return null;
        return min(root).key;
    }
    private Node min(Node x) {
        if (x.left == null) return x;
        return min(x.left);
    }
    /**
     * @return key associated with the max value in the tree, or null if the
     * tree is empty
     */
    public Key max() {
        if (size(root) == 0) return null;
        return max(root).key;
    }
    private Node max(Node x) {
        if  (x.right == null) return x;
        return max(x.right);
    }
    /**
     * @return largest key <= to the given key, or null if no such key exists
     */
    public Key floor(Key key) {
        Node x = floor(root, key);
        if (x == null) return null;
        return x.key;
    }
    private Node floor(Node x, Key key) {
        if (x == null) return null;   
        int cmp = key.compareTo(x.key);
        if (cmp == 0) return x; // found exact match - that's the floor
        if (cmp < 0) return floor(x.left, key);
        Node t = floor(x.right, key);
        if (t != null) return t;
        else return x;
    }
     
    /**
     * @return smallest key >= to the given key, or null if no such key exists
     */
    public Key ceiling(Key key) {
        Node x = ceiling(root, key);
        if (x == null) return null;
        return x.key;
    }
    private Node ceiling(Node x, Key key) {
        if (x == null) return null;
        int cmp = key.compareTo(x.key);
        if (cmp == 0) return x;
        if (cmp > 0) return ceiling(x.right, key);
        Node t = ceiling(x.left, key);
        if (t != null) return t;
        else return x;
    }

    public Iterable<Key> keys() {
        return keys(min(), max());
    }
    public Iterable<Key> keys(Key lo, Key hi) {
        Queue<Key> queue = new LinkedList<>();
        keys(root, queue, lo, hi);
        return queue;
    }
    /* recursively enqueues all the keys from the left subtree(if any could fall
     * in the range), then enqueues the node at the root (if it falls in the 
     * range), then recursively enqueues all the keys from the right subtree
     * (if any fall in the range)
     */
    private void keys(Node x, Queue<Key> queue, Key lo, Key hi) {
        if (x == null) return;
        int cmplo = lo.compareTo(x.key);
        int cmphi = hi.compareTo(x.key);
        if (cmplo < 0) keys(x.left, queue, lo, hi);
        if (cmplo <= 0 && cmphi >= 0) queue.add(x.key);
        if (cmphi > 0) keys(x.right, queue, lo, hi);
    }
     public static void main (String args[]) {
        RedBlackTree<Integer, Integer> testTree1 = new RedBlackTree<>();
        for (int i = 1; i <= 10; i++) {
            testTree1.put(i, i);
        }
        System.out.println(testTree1.keys());
        Iterable<Integer> ls = testTree1.keys();
        for (Integer key : ls) {
            System.out.println("key->" + key + " val->" + testTree1.get(key));
        }
        System.out.println("min: " + testTree1.min());
        System.out.println("max: " + testTree1.max());
        System.out.println("rank of 5: " + testTree1.rank(5));
        System.out.println("get 1: " + testTree1.get(1));
        System.out.println("get 10: " + testTree1.get(10));
        System.out.println("get 5: " + testTree1.get(5));
        System.out.println("get 20: " + testTree1.get(20));    
        System.out.println("#####################");
        System.out.println("Now inserting in descending order...............");
        // now test inserting keys in descending order
        RedBlackTree<Integer, Integer>testTree3 = new RedBlackTree<>();
        for (int i = 5; i > 0; i--) {
            System.out.println("Inserting " + i + "...");
            testTree3.put(i, i);
            System.out.println("get(i): " + testTree3.get(i));
        }
        System.out.println(testTree3.keys());
        Iterable<Integer> ls3 = testTree3.keys();
        for (Integer key : ls3) {
            System.out.println("key->" + key + " get(key)->" + testTree3.get(key));
        }
        System.out.println("min: " + testTree3.min());
        System.out.println("max: " + testTree3.max());
        System.out.println("rank of 5: " + testTree3.rank(5));
        System.out.println("get 1: " + testTree3.get(1));
        System.out.println("get 10: " + testTree3.get(10));
        System.out.println("get 5: " + testTree3.get(5));
        System.out.println("get 20: " + testTree3.get(20));    
        System.out.println("#####################");
        System.out.println("Inserting in descending order, even nums.........");
        // insert in descending order, only even numbers
        RedBlackTree<Integer, Integer> testTree12 = new RedBlackTree<>();
        for (int i = 20; i >= 0; i -= 2) {
            System.out.println("Inserting " + i + "into the tree...");
            testTree12.put(i, i);
        }
        Iterable<Integer> ls2 = testTree12.keys();
        for (Integer key : ls2) {
            System.out.println("key->" + key + " val->" + testTree12.get(key));
        }
        System.out.println("min: " + testTree12.min());
        System.out.println("max: " + testTree12.max());
        System.out.println("rank of 5: " + testTree12.rank(5));
        System.out.println("rank of 4: " + testTree12.rank(4));
        System.out.println("floor of 4: " + testTree12.floor(4));
        System.out.println("floor of 19: " + testTree12.floor(19));
        System.out.println("ceiling of 0: " + testTree12.ceiling(0));
        System.out.println("ceiling of 17: " + testTree12.ceiling(17));
        System.out.println("get 0: " + testTree12.get(0));
        System.out.println("get 20: " + testTree12.get(20));
        System.out.println("get 10: " + testTree12.get(10));
        System.out.println("get 11: " + testTree12.get(11));
    }
 }