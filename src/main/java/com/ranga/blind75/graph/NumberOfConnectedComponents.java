package com.ranga.blind75.graph;

import java.util.Arrays;

/**
 * Given {@code n} nodes labeled {@code 0} to {@code n - 1} and a list of undirected edges, return the number of connected components in the graph.
 *
 * <p><b>Algorithm Strategy (Union-Find (Disjoint Set)):</b>
 * <ol>
 *   <li>Start with {@code n} components: every node is in its own set, so {@code components = n}.</li>
 *   <li>For every edge, find the roots of the two endpoints.</li>
 *   <li>If the roots differ, the edge merges two components: union the sets and decrement {@code components}.</li>
 *   <li>After all edges are processed, {@code components} is the answer.</li>
 * </ol>
 *
 * <p><b>Example walkthrough</b>
 * <ul>
 *   <li>For {@code n = 5} and edges {@code [[0,1],[1,2],[3,4]]}: start with 5 components; edges 0-1, 1-2 and 3-4 each merge two components.</li>
 *   <li>Result: {@code 5 - 3 = 2}.</li>
 * </ul>
 *
 * <p><b>Complexity Analysis:</b>
 * <ul>
 *   <li><b>Time Complexity:</b> {@code O(n + E * alpha(n))} — Nearly linear with path compression.</li>
 *   <li><b>Space Complexity:</b> {@code O(n)} — The parent array.</li>
 * </ul>
 *
 * @author Ranga Reddy
 */
public class NumberOfConnectedComponents {

    public static void main(String[] args) {
        int[] ns = {5, 5, 3};
        int[][][] edges = {
                {{0, 1}, {1, 2}, {3, 4}},
                {{0, 1}, {1, 2}, {2, 3}, {3, 4}},
                {}
        };
        for (int i = 0; i < ns.length; i++) {
            System.out.println("Input  : n = " + ns[i] + ", edges = " + Arrays.deepToString(edges[i]));
            System.out.println("Output : " + countComponents(ns[i], edges[i]));
            System.out.println();
        }
    }

    public static int countComponents(int n, int[][] edges) {
        int[] parent = new int[n];
        for (int i = 0; i < n; i++) {
            parent[i] = i;
        }

        int components = n;
        for (int[] edge : edges) {
            int rootA = find(parent, edge[0]);
            int rootB = find(parent, edge[1]);
            if (rootA != rootB) {
                parent[rootA] = rootB;
                components--;
            }
        }
        return components;
    }

    private static int find(int[] parent, int node) {
        while (parent[node] != node) {
            parent[node] = parent[parent[node]];    // path compression (halving)
            node = parent[node];
        }
        return node;
    }
}
