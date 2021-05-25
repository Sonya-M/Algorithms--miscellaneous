
/**
 * Nonrecursive depth-first search.
 * *******************************
 * Exercise specs:
 * Implement depth-first search in an undirected graph without using recursion.
 * Vertices are represented as ints, which can be associated with more complex
 * data types using a symbol table
 */

import edu.princeton.cs.algs4.Graph;
import edu.princeton.cs.algs4.Stack;
import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.StdOut;

public class DepthFirstUsingStack {

    // private ivars

    // marked[v] -> is there a path from source s to vertex v:
    private boolean[] marked;
    // edgeTo[v] -> last edge on s-v path, one "hop" away:
    private int[] edgeTo;
    // source vertex:
    private final int s;
    // stack used instead of an implicit function stack
    private Stack<Integer> searchStack;
    private Graph G;

    /**
     * computes a path btw s and every other vertex in graph G.
     * 
     * @param G graph
     * @param s source vertex
     */
    public DepthFirstUsingStack(Graph G, int s) {
        // initializes ivars
        this.G = G;
        this.s = s;
        edgeTo = new int[G.V()];
        marked = new boolean[G.V()];
        searchStack = new Stack<Integer>();
        dfs(s);
    }

    // depth-first search using an explicit Stack
    // TODO: needs more testing!
    private void dfs(int v) {
        searchStack.push(v);
        int nextV = searchStack.pop();
        marked[nextV] = true;
        for (int w : G.adj(v)) {
            if (!marked[w]) {
                edgeTo[w] = v;
                dfs(w);
            }
        }
    }

    public boolean hasPathTo(int v) {
        return marked[v];
    }

    public Iterable<Integer> pathTo(int v) {
        if (!hasPathTo(v))
            return null;
        Stack<Integer> path = new Stack<Integer>();
        for (int i = v; i != s; i = edgeTo[i])
            path.push(i);
        path.push(s);
        return path;
    }

    public static void main(String[] args) {
        In in = new In(args[0]);
        Graph G = new Graph(in);
        int s = Integer.parseInt(args[1]);
        DepthFirstUsingStack dfs = new DepthFirstUsingStack(G, s);

        for (int v = 0; v < G.V(); v++) {
            if (dfs.hasPathTo(v)) {
                StdOut.printf("%d to %d:  ", s, v);
                for (int x : dfs.pathTo(v)) {
                    if (x == s)
                        StdOut.print(x);
                    else
                        StdOut.print("-" + x);
                }
                StdOut.println();
            }

            else {
                StdOut.printf("%d to %d:  not connected\n", s, v);
            }

        }
    }
}
