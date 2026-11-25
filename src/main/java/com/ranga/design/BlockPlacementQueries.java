package com.ranga.design;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.TreeSet;

/**
 * There is an infinite number line starting at 0 and extending to the right. Queries are {@code [1, x]} (build an obstacle at position {@code x}) or {@code [2, x, sz]} (can a block of size {@code sz} be placed in the interval {@code [0, x]} so that it does not touch or cross an obstacle? It may end at an obstacle but must lie fully inside {@code [0, x]}). Return the answers to all queries of the second type, in order.
 *
 * <p><b>Algorithm Strategy (Sorted Obstacles plus a Segment Tree of Gaps):</b>
 * <ol>
 *   <li>Keep the obstacles in a {@code TreeSet} (with 0 as a permanent origin). For each obstacle {@code p}, its gap is the distance to the previous obstacle.</li>
 *   <li>Store each gap in a segment tree indexed by the obstacle's position, supporting a range maximum.</li>
 *   <li>When an obstacle {@code x} is added, set {@code gap[x] = x - prev} and update the next obstacle's gap to {@code next - x}.</li>
 *   <li>For a query {@code (x, sz)}: let {@code l} be the largest obstacle at or below {@code x}. A block fits if the largest gap among obstacles up to {@code l} is at least {@code sz}, or if the free tail {@code x - l} is at least {@code sz}.</li>
 * </ol>
 *
 * <p><b>Example walkthrough</b>
 * <ul>
 *   <li>For queries {@code [1,2], [2,3,3], [2,3,1], [2,2,2]}: after an obstacle at 2, a block of size 3 does not fit in {@code [0, 3]}, size 1 fits, and size 2 fits in {@code [0, 2]}.</li>
 *   <li>Result: {@code [false, true, true]}.</li>
 * </ul>
 *
 * <p><b>Complexity Analysis:</b>
 * <ul>
 *   <li><b>Time Complexity:</b> {@code O(q log q)} — Each query does a tree lookup and a segment tree update or query.</li>
 *   <li><b>Space Complexity:</b> {@code O(maxPosition)} — The segment tree.</li>
 * </ul>
 *
 * @author Ranga Reddy
 */
public class BlockPlacementQueries {

    public static void main(String[] args) {
        int[][][] inputs = {{{1, 2}, {2, 3, 3}, {2, 3, 1}, {2, 2, 2}}, {{1, 7}, {2, 7, 6}, {1, 2}, {2, 7, 5}, {2, 7, 6}}, {{2, 5, 5}, {2, 5, 6}}};
        for (int[][] queries : inputs) {
            System.out.println("Input  : queries = " + Arrays.deepToString(queries));
            System.out.println("Output : " + getResults(queries));
            System.out.println();
        }
    }

    public static List<Boolean> getResults(int[][] queries) {
        List<Boolean> answers = new ArrayList<>();
        int max = 1;
        for (int[] q : queries) {
            max = Math.max(max, q[1]);
        }
        int size = 1;
        while (size < max + 2) {
            size <<= 1;
        }
        int[] tree = new int[2 * size];
        TreeSet<Integer> obstacles = new TreeSet<>();
        obstacles.add(0);
        for (int[] q : queries) {
            int x = q[1];
            if (q[0] == 1) {
                Integer previous = obstacles.lower(x);
                Integer next = obstacles.higher(x);
                obstacles.add(x);
                update(tree, size, x, x - previous);
                if (next != null) {
                    update(tree, size, next, next - x);
                }
            } else {
                int last = obstacles.floor(x);
                int widest = Math.max(x - last, maxGap(tree, size, 0, last));
                answers.add(widest >= q[2]);
            }
        }
        return answers;
    }

    private static void update(int[] tree, int size, int position, int value) {
        int i = position + size;
        tree[i] = value;
        for (i >>= 1; i >= 1; i >>= 1) {
            tree[i] = Math.max(tree[2 * i], tree[2 * i + 1]);
        }
    }

    private static int maxGap(int[] tree, int size, int from, int to) {
        int result = 0;
        int lo = from + size;
        int hi = to + size + 1;
        while (lo < hi) {
            if ((lo & 1) == 1) {
                result = Math.max(result, tree[lo++]);
            }
            if ((hi & 1) == 1) {
                result = Math.max(result, tree[--hi]);
            }
            lo >>= 1;
            hi >>= 1;
        }
        return result;
    }
}
