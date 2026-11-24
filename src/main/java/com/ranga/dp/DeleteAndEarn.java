package com.ranga.dp;

import java.util.Arrays;

/**
 * Given an integer array {@code nums}, you may repeatedly pick a number {@code x}, earn {@code x} points, and then delete every element equal to {@code x - 1} or {@code x + 1}. Return the maximum number of points you can earn.
 *
 * <p><b>Algorithm Strategy (House Robber on Value Sums):</b>
 * <ol>
 *   <li>Taking a value {@code v} earns {@code v * count(v)} and forbids {@code v - 1} and {@code v + 1}.</li>
 *   <li>Build {@code points[v] = v * count(v)} for every value up to the maximum.</li>
 *   <li>This is the House Robber problem over {@code points}: you cannot take two adjacent values.</li>
 *   <li>Use {@code take} and {@code skip} running totals: {@code newTake = skip + points[v]}, {@code newSkip = max(skip, take)}.</li>
 * </ol>
 *
 * <p><b>Example walkthrough</b>
 * <ul>
 *   <li>For {@code [2, 2, 3, 3, 3, 4]}: points are 2 -> 4, 3 -> 9, 4 -> 4.</li>
 *   <li>Taking 3 (9) beats taking 2 and 4 (8), so the best is 9.</li>
 * </ul>
 *
 * <p><b>Complexity Analysis:</b>
 * <ul>
 *   <li><b>Time Complexity:</b> {@code O(n + maxValue)} — Counting plus one pass over the values.</li>
 *   <li><b>Space Complexity:</b> {@code O(maxValue)} — The points array.</li>
 * </ul>
 *
 * @author Ranga Reddy
 */
public class DeleteAndEarn {

    public static void main(String[] args) {
        int[][] inputs = {{2, 2, 3, 3, 3, 4}, {3, 4, 2}, {1, 1, 1, 2, 4, 5, 5, 5, 6}, {}};
        for (int[] nums : inputs) {
            System.out.println("Input  : nums = " + Arrays.toString(nums));
            System.out.println("Output : " + deleteAndEarn(nums));
            System.out.println();
        }
    }

    public static int deleteAndEarn(int[] nums) {
        if (nums == null || nums.length == 0) {
            return 0;
        }
        int max = 0;
        for (int x : nums) {
            max = Math.max(max, x);
        }
        int[] points = new int[max + 1];
        for (int x : nums) {
            points[x] += x;
        }
        int take = 0;
        int skip = 0;
        for (int v = 0; v <= max; v++) {
            int newTake = skip + points[v];
            skip = Math.max(skip, take);
            take = newTake;
        }
        return Math.max(take, skip);
    }
}
