package com.ranga.interval;

import java.util.Arrays;
import java.util.Comparator;

/**
 * Given a list of intervals, return the minimum number of intervals to remove so that the remaining intervals do not overlap (intervals that only touch at an endpoint do not overlap).
 *
 * <p><b>Algorithm Strategy (Greedy by Earliest End Time):</b>
 * <ol>
 *   <li>Sort the intervals by end time. Keeping the interval that finishes first leaves the most room for the following intervals.</li>
 *   <li>Keep the first interval and remember its end as {@code lastEnd}.</li>
 *   <li>For each next interval: if its start is {@code >= lastEnd}, there is no overlap, so keep it and update {@code lastEnd}; otherwise it overlaps, so it is removed (count it).</li>
 *   <li>Return the number of removed intervals.</li>
 * </ol>
 *
 * <p><b>Example walkthrough</b>
 * <ul>
 *   <li>For {@code [[1,2],[2,3],[3,4],[1,3]]} sorted by end: {@code [1,2],[2,3],[1,3],[3,4]}. Keep {@code [1,2]} and {@code [2,3]}; {@code [1,3]} starts at 1 &lt; 3, remove it; keep {@code [3,4]}.</li>
 *   <li>Result: {@code 1}.</li>
 * </ul>
 *
 * <p><b>Complexity Analysis:</b>
 * <ul>
 *   <li><b>Time Complexity:</b> {@code O(n log n)} — Dominated by sorting.</li>
 *   <li><b>Space Complexity:</b> {@code O(1)} — Besides the sort.</li>
 * </ul>
 *
 * @author Ranga Reddy
 */
public class NonOverlappingIntervals {

    public static void main(String[] args) {
        int[][][] inputs = {{{1, 2}, {2, 3}, {3, 4}, {1, 3}}, {{1, 2}, {1, 2}, {1, 2}}, {{1, 2}, {2, 3}}};
        for (int[][] intervals : inputs) {
            System.out.println("Input  : intervals = " + Arrays.deepToString(intervals));
            System.out.println("Output : " + eraseOverlapIntervals(intervals));
            System.out.println();
        }
    }

    public static int eraseOverlapIntervals(int[][] intervals) {
        if (intervals == null || intervals.length == 0) {
            return 0;
        }

        int[][] sorted = intervals.clone();
        Arrays.sort(sorted, Comparator.comparingInt((int[] interval) -> interval[1]));

        int removed = 0;
        int lastEnd = sorted[0][1];
        for (int i = 1; i < sorted.length; i++) {
            if (sorted[i][0] >= lastEnd) {
                lastEnd = sorted[i][1];     // no overlap: keep this interval
            } else {
                removed++;                  // overlap: remove the interval that ends later
            }
        }
        return removed;
    }
}
