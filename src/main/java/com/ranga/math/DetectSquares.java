package com.ranga.math;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Design a data structure that accepts a stream of 2D points (duplicates allowed) and, for a query point, counts the number of ways to choose three stored points that form, together with the query, an axis-aligned square with positive area. Implement {@code add(int[] point)} and {@code count(int[] point)}.
 *
 * <p><b>Algorithm Strategy (Hash Map of Point Counts):</b>
 * <ol>
 *   <li>Store the number of times each point was added in a map keyed by its coordinates.</li>
 *   <li>{@code add} increments the count of the point.</li>
 *   <li>For {@code count(qx, qy)}, every distinct stored point {@code (x, y)} is a candidate for the corner diagonally opposite the query. It qualifies when {@code |x - qx| == |y - qy|} and the distance is positive.</li>
 *   <li>The other two corners are then {@code (qx, y)} and {@code (x, qy)}. The number of squares through this diagonal is the product of the counts of the three points (duplicates multiply).</li>
 *   <li>Sum the products over all candidate diagonal points.</li>
 * </ol>
 *
 * <p><b>Example walkthrough</b>
 * <ul>
 *   <li>After adding {@code (3,10)}, {@code (11,2)} and {@code (3,2)}, the query {@code (11,10)} finds one square: the count is {@code 1}. Query {@code (14,8)} finds {@code 0}.</li>
 *   <li>After adding {@code (11,2)} again, the query {@code (11,10)} returns {@code 2}.</li>
 * </ul>
 *
 * <p><b>Complexity Analysis:</b>
 * <ul>
 *   <li><b>Time Complexity:</b> {@code add O(1), count O(p)} — {@code p} is the number of distinct stored points.</li>
 *   <li><b>Space Complexity:</b> {@code O(p)} — The map of point counts.</li>
 * </ul>
 *
 * @author Ranga Reddy
 */
public class DetectSquares {

    private final Map<Long, Integer> counts = new HashMap<>();
    private final List<int[]> distinctPoints = new ArrayList<>();

    public static void main(String[] args) {
        DetectSquares squares = new DetectSquares();
        int[][] added = {{3, 10}, {11, 2}, {3, 2}};
        for (int[] point : added) {
            squares.add(point);
        }
        System.out.println("Input  : add " + Arrays.deepToString(added) + ", count [11, 10]");
        System.out.println("Output : " + squares.count(new int[]{11, 10}));
        System.out.println();

        System.out.println("Input  : count [14, 8]");
        System.out.println("Output : " + squares.count(new int[]{14, 8}));
        System.out.println();

        squares.add(new int[]{11, 2});
        System.out.println("Input  : add [11, 2], count [11, 10]");
        System.out.println("Output : " + squares.count(new int[]{11, 10}));
    }

    public void add(int[] point) {
        long key = key(point[0], point[1]);
        if (!counts.containsKey(key)) {
            distinctPoints.add(point.clone());
        }
        counts.merge(key, 1, Integer::sum);
    }

    public int count(int[] point) {
        int qx = point[0];
        int qy = point[1];
        int total = 0;
        for (int[] diagonal : distinctPoints) {
            int dx = Math.abs(diagonal[0] - qx);
            int dy = Math.abs(diagonal[1] - qy);
            if (dx == 0 || dx != dy) {
                continue;
            }
            // the diagonal corner is (x, y); the other two corners share a coordinate with the query
            int first = counts.getOrDefault(key(qx, diagonal[1]), 0);
            int second = counts.getOrDefault(key(diagonal[0], qy), 0);
            total += counts.get(key(diagonal[0], diagonal[1])) * first * second;
        }
        return total;
    }

    private static long key(int x, int y) {
        return ((long) x << 32) | (y & 0xffffffffL);
    }
}
