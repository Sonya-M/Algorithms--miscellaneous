
/**
 * Graph exercise for Princeton's course Algorithms pt 2:
 * Bipartite means that you can divide the vertices into 2 subsets so that every edge connects a vertex in one subset to a vertex in another. 
 * Is a graph bipartite (Two-colorability: Can the vertices of a given graph be
 * assigned one of two colors in such a way that no edge connects vertices of
 * the same color?)
 * DFS solution: label vertices in such a way that, if the
 * G is bipartite, all edges go from V in one set to V in another
 * If a graph has a bipartition, return one;
 * if not, return an odd-length cycle.
 * Should take O(V + E)
 */

import edu.princeton.cs.algs4.Graph;

public class GraphsBipartite {

}