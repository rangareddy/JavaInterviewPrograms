package com.ranga.graph;

import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

/**
 * On a 2D plane, {@code n} stones are placed at integer coordinates, at most one per point. A stone can be removed if another stone remains in the same row or the same column. Return the largest number of stones that can be removed.
 *
 * <p><b>Algorithm Strategy (Connected Components of Rows and Columns):</b>
 * <ol>
 *   <li>Treat each row and each column as a node. A stone at {@code (x, y)} joins the row {@code x} with the column {@code y}.</li>
 *   <li>Use Union-Find: for every stone, union {@code x} with {@code y + OFFSET} (the offset keeps column ids apart from row ids).</li>
 *   <li>In every connected group, all stones but one can be removed, by taking them from the leaves inward.</li>
 *   <li>The answer is {@code stones - number of connected groups}.</li>
 * </ol>
 *
 * <p><b>Example walkthrough</b>
 * <ul>
 *   <li>For the six stones {@code [0,0],[0,1],[1,0],[1,2],[2,1],[2,2]} everything is connected through shared rows and columns, so there is one group.</li>
 *   <li>Result: {@code 6 - 1 = 5}.</li>
 * </ul>
 *
 * <p><b>Complexity Analysis:</b>
 * <ul>
 *   <li><b>Time Complexity:</b> {@code O(n * alpha(n))} — Nearly linear with path compression.</li>
 *   <li><b>Space Complexity:</b> {@code O(n)} — The parent map.</li>
 * </ul>
 *
 * @author Ranga Reddy
 */
public class MostStonesRemovedWithSameRowOrColumn {

    public static void main(String[] args) {
        int[][][] inputs = {{{0, 0}, {0, 1}, {1, 0}, {1, 2}, {2, 1}, {2, 2}}, {{0, 0}, {0, 2}, {1, 1}, {2, 0}, {2, 2}}, {{0, 0}}, {}};
        for (int[][] stones : inputs) {
            System.out.println("Input  : stones = " + Arrays.deepToString(stones));
            System.out.println("Output : " + removeStones(stones));
            System.out.println();
        }
    }

    public static int removeStones(int[][] stones) {
        if (stones == null || stones.length == 0) {
            return 0;
        }
        Map<Integer, Integer> parent = new HashMap<>();
        for (int[] stone : stones) {
            union(parent, stone[0], stone[1] + 10001);
        }
        Set<Integer> roots = new HashSet<>();
        for (int node : parent.keySet()) {
            roots.add(find(parent, node));
        }
        return stones.length - roots.size();
    }

    private static int find(Map<Integer, Integer> parent, int x) {
        parent.putIfAbsent(x, x);
        int root = x;
        while (parent.get(root) != root) {
            root = parent.get(root);
        }
        while (parent.get(x) != root) {
            int next = parent.get(x);
            parent.put(x, root);
            x = next;
        }
        return root;
    }

    private static void union(Map<Integer, Integer> parent, int a, int b) {
        parent.put(find(parent, a), find(parent, b));
    }
}
