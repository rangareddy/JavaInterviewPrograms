package com.ranga.graph;

import java.util.Arrays;

/**
 * Given {@code n} nodes labeled {@code 0} to {@code n - 1} and a list of undirected edges, check whether the edges form a valid tree: connected and without cycles.
 *
 * <p><b>Algorithm Strategy (Union-Find (Disjoint Set)):</b>
 * <ol>
 *   <li>A tree with {@code n} nodes has exactly {@code n - 1} edges. If the number of edges is different, return {@code false} immediately.</li>
 *   <li>Create a union-find structure where every node starts in its own set.</li>
 *   <li>For each edge, find the roots of both endpoints. If they are the same, the edge closes a cycle, so return {@code false}; otherwise union the two sets.</li>
 *   <li>With exactly {@code n - 1} edges and no cycle, the graph must also be connected, so return {@code true}.</li>
 * </ol>
 *
 * <p><b>Example walkthrough</b>
 * <ul>
 *   <li>For {@code n = 5} and edges {@code [[0,1],[0,2],[0,3],[1,4]]}: 4 edges, each joins two different sets, so no cycle: {@code true}.</li>
 *   <li>For {@code [[0,1],[1,2],[2,3],[1,3],[1,4]]} the edge {@code [1,3]} joins nodes that are already connected: {@code false}.</li>
 * </ul>
 *
 * <p><b>Complexity Analysis:</b>
 * <ul>
 *   <li><b>Time Complexity:</b> {@code O(n * alpha(n))} — Nearly linear with path compression ({@code alpha} is the inverse Ackermann function).</li>
 *   <li><b>Space Complexity:</b> {@code O(n)} — The parent array.</li>
 * </ul>
 *
 * @author Ranga Reddy
 */
public class GraphValidTree {

    public static void main(String[] args) {
        int[] ns = {5, 5, 4};
        int[][][] edges = {
                {{0, 1}, {0, 2}, {0, 3}, {1, 4}},
                {{0, 1}, {1, 2}, {2, 3}, {1, 3}, {1, 4}},
                {{0, 1}, {2, 3}}
        };
        for (int i = 0; i < ns.length; i++) {
            System.out.println("Input  : n = " + ns[i] + ", edges = " + Arrays.deepToString(edges[i]));
            System.out.println("Output : " + validTree(ns[i], edges[i]));
            System.out.println();
        }
    }

    public static boolean validTree(int n, int[][] edges) {
        if (edges.length != n - 1) {
            return false;
        }

        int[] parent = new int[n];
        for (int i = 0; i < n; i++) {
            parent[i] = i;
        }
        for (int[] edge : edges) {
            int rootA = find(parent, edge[0]);
            int rootB = find(parent, edge[1]);
            if (rootA == rootB) {
                return false;           // this edge closes a cycle
            }
            parent[rootA] = rootB;
        }
        return true;
    }

    private static int find(int[] parent, int node) {
        while (parent[node] != node) {
            parent[node] = parent[parent[node]];    // path compression (halving)
            node = parent[node];
        }
        return node;
    }
}
