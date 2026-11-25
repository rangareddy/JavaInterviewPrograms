package com.ranga.interval;

import java.util.Arrays;

/**
 * Balloons are given as intervals {@code points[i] = [xstart, xend]} on a wall. An arrow shot vertically at {@code x} bursts every balloon with {@code xstart <= x <= xend}. Return the minimum number of arrows needed to burst all balloons.
 *
 * <p><b>Algorithm Strategy (Sort by End, Shoot at the Earliest End):</b>
 * <ol>
 *   <li>Sort the balloons by their end coordinate (compare with {@code Integer.compare} to avoid overflow).</li>
 *   <li>Shoot the first arrow at the end of the first balloon; every balloon that starts at or before that point is burst by it.</li>
 *   <li>Walk the sorted list; when a balloon starts after the position of the last arrow, shoot a new arrow at its end.</li>
 *   <li>Count the arrows.</li>
 * </ol>
 *
 * <p><b>Example walkthrough</b>
 * <ul>
 *   <li>For {@code [[10,16],[2,8],[1,6],[7,12]]}: sorted by end {@code [1,6],[2,8],[7,12],[10,16]}. Shoot at 6 (bursts the first two), then at 12 (bursts the last two).</li>
 *   <li>Result: {@code 2}.</li>
 * </ul>
 *
 * <p><b>Complexity Analysis:</b>
 * <ul>
 *   <li><b>Time Complexity:</b> {@code O(n log n)} — Sorting dominates.</li>
 *   <li><b>Space Complexity:</b> {@code O(1)} — Apart from the sort.</li>
 * </ul>
 *
 * @author Ranga Reddy
 */
public class MinimumNumberOfArrowsToBurstBalloons {

    public static void main(String[] args) {
        int[][][] inputs = {{{10, 16}, {2, 8}, {1, 6}, {7, 12}}, {{1, 2}, {3, 4}, {5, 6}, {7, 8}}, {{1, 2}, {2, 3}, {3, 4}, {4, 5}}, {{-2147483646, -2147483645}, {2147483646, 2147483647}}, {}};
        for (int[][] points : inputs) {
            System.out.println("Input  : points = " + Arrays.deepToString(points));
            System.out.println("Output : " + findMinArrowShots(points));
            System.out.println();
        }
    }

    public static int findMinArrowShots(int[][] points) {
        if (points == null || points.length == 0) {
            return 0;
        }
        int[][] sorted = points.clone();
        Arrays.sort(sorted, (a, b) -> Integer.compare(a[1], b[1]));
        int arrows = 1;
        int position = sorted[0][1];
        for (int i = 1; i < sorted.length; i++) {
            if (sorted[i][0] > position) {
                arrows++;
                position = sorted[i][1];
            }
        }
        return arrows;
    }
}
