package com.ranga.graph;

import java.util.Arrays;

/**
 * There are {@code n} computers numbered {@code 0} to {@code n - 1} connected by Ethernet cables {@code connections[i] = [a, b]}. In one operation you can take a cable between two directly connected computers and place it between any pair of computers. Return the minimum number of operations needed to connect all the computers, or {@code -1} if it is impossible.
 *
 * <p><b>Algorithm Strategy (Count Components with Union-Find):</b>
 * <ol>
 *   <li>Connecting {@code n} computers needs at least {@code n - 1} cables. With fewer, return -1.</li>
 *   <li>Union the computers of every cable and count the number of connected components.</li>
 *   <li>Joining {@code c} components needs {@code c - 1} cables, and there are enough spare cables because {@code connections.length >= n - 1}.</li>
 *   <li>The answer is {@code components - 1}.</li>
 * </ol>
 *
 * <p><b>Example walkthrough</b>
 * <ul>
 *   <li>For {@code n = 4} and {@code [[0,1],[0,2],[1,2]]}: the cables connect 0, 1, 2, and computer 3 is alone, so there are 2 components.</li>
 *   <li>Result: {@code 1} (move the redundant cable).</li>
 * </ul>
 *
 * <p><b>Complexity Analysis:</b>
 * <ul>
 *   <li><b>Time Complexity:</b> {@code O(n + e * alpha(n))} — Union-Find with path compression.</li>
 *   <li><b>Space Complexity:</b> {@code O(n)} — The parent array.</li>
 * </ul>
 *
 * @author Ranga Reddy
 */
public class NumberOfOperationsToMakeNetworkConnected {

    public static void main(String[] args) {
        int[] ns = {4, 6, 6, 5};
        int[][][] cables = {{{0, 1}, {0, 2}, {1, 2}}, {{0, 1}, {0, 2}, {0, 3}, {1, 2}, {1, 3}}, {{0, 1}, {0, 2}, {0, 3}, {1, 2}}, {{0, 1}, {0, 2}, {3, 4}, {2, 3}}};
        for (int i = 0; i < ns.length; i++) {
            System.out.println("Input  : n = " + ns[i] + ", connections = " + Arrays.deepToString(cables[i]));
            System.out.println("Output : " + makeConnected(ns[i], cables[i]));
            System.out.println();
        }
    }

    public static int makeConnected(int n, int[][] connections) {
        if (connections == null || connections.length < n - 1) {
            return -1;
        }
        int[] parent = new int[n];
        for (int i = 0; i < n; i++) {
            parent[i] = i;
        }
        int components = n;
        for (int[] c : connections) {
            int a = find(parent, c[0]);
            int b = find(parent, c[1]);
            if (a != b) {
                parent[a] = b;
                components--;
            }
        }
        return components - 1;
    }

    private static int find(int[] parent, int x) {
        while (parent[x] != x) {
            parent[x] = parent[parent[x]];
            x = parent[x];
        }
        return x;
    }
}
