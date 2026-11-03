package com.ranga.graph;

import java.util.Arrays;

/**
 * A tree with {@code n} nodes labeled {@code 1} to {@code n} had one extra edge added, so the graph now has exactly one cycle. Given the {@code n} edges, return an edge that can be removed so that the remaining graph is a tree. If there are several answers, return the one that occurs last in the input.
 *
 * <p><b>Algorithm Strategy (Union-Find: The Edge that Closes a Cycle):</b>
 * <ol>
 *   <li>Start with every node in its own set.</li>
 *   <li>Process the edges in the given order. For edge {@code [u, v]} find the roots of both endpoints.</li>
 *   <li>If the roots are equal, {@code u} and {@code v} are already connected, so this edge closes the cycle: it is redundant, return it.</li>
 *   <li>Otherwise union the two sets. Since the edges are processed in order, the first edge that closes a cycle is the last edge of that cycle in the input, which is the required answer.</li>
 * </ol>
 *
 * <p><b>Example walkthrough</b>
 * <ul>
 *   <li>For {@code [[1,2],[1,3],[2,3]]}: edges 1-2 and 1-3 join {1, 2, 3}; the edge 2-3 connects two nodes of the same set.</li>
 *   <li>Result: {@code [2, 3]}.</li>
 * </ul>
 *
 * <p><b>Complexity Analysis:</b>
 * <ul>
 *   <li><b>Time Complexity:</b> {@code O(n * alpha(n))} — Nearly linear with path compression.</li>
 *   <li><b>Space Complexity:</b> {@code O(n)} — The parent array.</li>
 * </ul>
 *
 * @author Ranga Reddy
 */
public class RedundantConnection {

    public static void main(String[] args) {
        int[][][] inputs = {
                {{1, 2}, {1, 3}, {2, 3}},
                {{1, 2}, {2, 3}, {3, 4}, {1, 4}, {1, 5}},
                {{1, 2}, {1, 2}},
                {{1, 4}, {3, 4}, {1, 3}, {1, 2}, {4, 5}}
        };
        for (int[][] edges : inputs) {
            System.out.println("Input  : edges = " + Arrays.deepToString(edges));
            System.out.println("Output : " + Arrays.toString(findRedundantConnection(edges)));
            System.out.println();
        }
    }

    public static int[] findRedundantConnection(int[][] edges) {
        int[] parent = new int[edges.length + 1];
        for (int i = 0; i < parent.length; i++) {
            parent[i] = i;
        }

        for (int[] edge : edges) {
            int rootA = find(parent, edge[0]);
            int rootB = find(parent, edge[1]);
            if (rootA == rootB) {
                return edge;                        // already connected: this edge closes a cycle
            }
            parent[rootA] = rootB;
        }
        return new int[0];
    }

    private static int find(int[] parent, int node) {
        while (parent[node] != node) {
            parent[node] = parent[parent[node]];    // path compression (halving)
            node = parent[node];
        }
        return node;
    }
}
