package com.ranga.graph;

import java.util.Arrays;

/**
 * There are {@code n} cities. Given an {@code n x n} matrix {@code isConnected} where {@code isConnected[i][j] = 1} if city {@code i} and city {@code j} are directly connected, and 0 otherwise, return the number of provinces. A province is a group of directly or indirectly connected cities with no connection to cities outside the group.
 *
 * <p><b>Algorithm Strategy (Union-Find over Connected Cities):</b>
 * <ol>
 *   <li>Start with {@code n} provinces, one per city, each city being its own parent.</li>
 *   <li>For every pair {@code i < j} with {@code isConnected[i][j] == 1}, find the roots of both cities.</li>
 *   <li>If the roots differ, merge them and decrease the province count by one.</li>
 *   <li>Return the count (use path compression so {@code find} stays almost constant).</li>
 * </ol>
 *
 * <p><b>Example walkthrough</b>
 * <ul>
 *   <li>For {@code [[1,1,0],[1,1,0],[0,0,1]]}: cities 0 and 1 merge, city 2 stays alone.</li>
 *   <li>Result: {@code 2}.</li>
 * </ul>
 *
 * <p><b>Complexity Analysis:</b>
 * <ul>
 *   <li><b>Time Complexity:</b> {@code O(n^2 * alpha(n))} — Every matrix cell is inspected once.</li>
 *   <li><b>Space Complexity:</b> {@code O(n)} — The parent array.</li>
 * </ul>
 *
 * @author Ranga Reddy
 */
public class NumberOfProvinces {

    public static void main(String[] args) {
        int[][][] inputs = {{{1, 1, 0}, {1, 1, 0}, {0, 0, 1}}, {{1, 0, 0}, {0, 1, 0}, {0, 0, 1}}, {{1}}, {}};
        for (int[][] matrix : inputs) {
            System.out.println("Input  : isConnected = " + Arrays.deepToString(matrix));
            System.out.println("Output : " + findCircleNum(matrix));
            System.out.println();
        }
    }

    public static int findCircleNum(int[][] isConnected) {
        if (isConnected == null || isConnected.length == 0) {
            return 0;
        }
        int n = isConnected.length;
        int[] parent = new int[n];
        for (int i = 0; i < n; i++) {
            parent[i] = i;
        }
        int provinces = n;
        for (int i = 0; i < n; i++) {
            for (int j = i + 1; j < n; j++) {
                if (isConnected[i][j] == 1) {
                    int a = find(parent, i);
                    int b = find(parent, j);
                    if (a != b) {
                        parent[a] = b;
                        provinces--;
                    }
                }
            }
        }
        return provinces;
    }

    private static int find(int[] parent, int x) {
        while (parent[x] != x) {
            parent[x] = parent[parent[x]];
            x = parent[x];
        }
        return x;
    }
}
