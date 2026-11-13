package com.ranga.graph;

import java.util.Arrays;

/**
 * Given a connected, undirected, weighted graph with {@code n} vertices numbered {@code 0..n-1} and an edge list {@code [u, v, weight]}, return the total weight of a minimum spanning tree (MST): the cheapest set of {@code n - 1} edges that connects every vertex. Return {@code -1} if the graph is not connected.
 *
 * <p><b>Algorithm Strategy (Kruskal with Union-Find):</b>
 * <ol>
 *   <li>Sort all edges by weight, cheapest first.</li>
 *   <li>Keep a union-find structure over the vertices. Go through the sorted edges and take an edge only if its endpoints are in different sets (taking it cannot create a cycle), then union the sets.</li>
 *   <li>The cut property justifies the greedy choice: the cheapest edge crossing any partition of the vertices belongs to some MST, and the cheapest edge between two different components is such an edge.</li>
 *   <li>Stop after {@code n - 1} edges are taken. If fewer were found, the graph is disconnected and there is no spanning tree.</li>
 * </ol>
 *
 * <p><b>Example walkthrough</b>
 * <ul>
 *   <li>For 4 vertices and edges {@code (0,1,1), (1,2,2), (0,2,3), (2,3,4), (1,3,5)}: sorted, the first two edges are taken (weights 1 and 2).</li>
 *   <li>{@code (0,2,3)} closes a cycle and is skipped; {@code (2,3,4)} connects vertex 3. Total weight {@code 1 + 2 + 4 = 7}.</li>
 * </ul>
 *
 * <p><b>Complexity Analysis:</b>
 * <ul>
 *   <li><b>Time Complexity:</b> {@code O(E log E)} — Sorting the edges dominates; union-find operations are almost constant.</li>
 *   <li><b>Space Complexity:</b> {@code O(V)} — The union-find parent array (plus the sorted copy of the edges).</li>
 * </ul>
 *
 * @author Ranga Reddy
 */
public class MinimumSpanningTree {

    public static void main(String[] args) {
        int[][] edges = {{0, 1, 1}, {1, 2, 2}, {0, 2, 3}, {2, 3, 4}, {1, 3, 5}};
        System.out.println("Input  : n = 4, edges = " + Arrays.deepToString(edges));
        System.out.println("Output : " + minimumSpanningTree(4, edges));
        System.out.println();

        int[][] disconnected = {{0, 1, 1}, {2, 3, 1}};
        System.out.println("Input  : n = 4, edges = " + Arrays.deepToString(disconnected));
        System.out.println("Output : " + minimumSpanningTree(4, disconnected));
    }

    public static int minimumSpanningTree(int n, int[][] edges) {
        int[][] sorted = edges.clone();
        Arrays.sort(sorted, (a, b) -> Integer.compare(a[2], b[2]));
        int[] parent = new int[n];
        for (int i = 0; i < n; i++) {
            parent[i] = i;
        }
        int total = 0;
        int taken = 0;
        for (int[] edge : sorted) {
            int a = find(parent, edge[0]);
            int b = find(parent, edge[1]);
            if (a != b) {
                parent[a] = b;
                total += edge[2];
                taken++;
            }
        }
        return taken == n - 1 ? total : -1;
    }

    private static int find(int[] parent, int x) {
        while (parent[x] != x) {
            parent[x] = parent[parent[x]];
            x = parent[x];
        }
        return x;
    }
}
