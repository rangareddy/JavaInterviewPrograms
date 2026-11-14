package com.ranga.interval;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;

/**
 * Utility class to merge overlapping interval ranges.
 *
 * <p><b>Algorithm Strategy (Sort and Sweep):</b>
 * <ol>
 *   <li>Sort all intervals by their start value in ascending order, so overlapping intervals become neighbors.</li>
 *   <li>Copy the first interval into the output list and treat it as the {@code current} interval.</li>
 *   <li>For every following interval, compare its start with the end of {@code current}.</li>
 *   <li>If {@code next.start <= current.end} they overlap: extend the current interval with {@code current.end = max(current.end, next.end)}.</li>
 *   <li>Otherwise there is a gap: start a new current interval from {@code next} and add it to the output list.</li>
 *   <li>After the last interval, the list holds the minimal set of non-overlapping intervals.</li>
 * </ol>
 *
 * <p><b>Example walkthrough</b>
 * <ul>
 *   <li>For {@code [[1,3],[8,10],[2,6],[15,18]]}:</li>
 *   <li>Sorted: {@code [[1,3],[2,6],[8,10],[15,18]]}.</li>
 *   <li>{@code [1,3]} and {@code [2,6]} overlap and become {@code [1,6]}; {@code [8,10]} and {@code [15,18]} start after the previous end.</li>
 *   <li>Result: {@code [[1,6],[8,10],[15,18]]}.</li>
 * </ul>
 *
 * <p><b>Complexity Analysis:</b>
 * <ul>
 *   <li><b>Time Complexity:</b> {@code O(n log n)} — Dominated by the initial sorting step,
 *       where {@code n} is the number of intervals. The single linear scan takes {@code O(n)}.</li>
 *   <li><b>Space Complexity:</b> {@code O(n)} or {@code O(log n)} — Depends on the sorting algorithm
 *       implementation space (e.g., Dual-Pivot Quicksort / TimSort), plus {@code O(n)} space for the output list.</li>
 * </ul>
 *
 * @author Ranga Reddy
 */

public class MergeIntervals {
    public static void main(String[] args) {
        int[][] input1 = new int[][]{{1, 3}, {8, 10}, {2, 6}, {15, 18}};

        System.out.println("Input 1 : " + Arrays.deepToString(input1));
        System.out.println("Merged  : " + Arrays.deepToString(mergeIntervals(input1)));

        System.out.println();

        int[][] input2 = {{1, 4}, {4, 5}};
        System.out.println("Input 2 : " + Arrays.deepToString(input2));
        System.out.println("Merged  : " + Arrays.deepToString(mergeIntervals(input2)));
    }

    /**
     * Merges all overlapping intervals in a 2D array.
     *
     * @param intervals a 2D array where each element represents an interval {@code [start, end]}
     * @return a new 2D array containing the minimal set of non-overlapping intervals
     */
    public static int[][] mergeIntervals(int[][] intervals) {
        // Guard Clause: null, empty, or single-interval inputs require no processing
        if (intervals == null || intervals.length <= 1) {
            return intervals;
        }

        // Sort intervals by start time using primitive int comparator (prevents overflow issues)
        Arrays.sort(intervals, Comparator.comparingInt(a -> a[0]));

        List<int[]> mergedList = new ArrayList<>(intervals.length);

        // Copy the first interval to prevent mutating caller's original array elements
        int[] currentInterval = new int[]{intervals[0][0], intervals[0][1]};
        mergedList.add(currentInterval);

        for (int i = 1; i < intervals.length; i++) {
            int[] nextInterval = intervals[i];
            int currentEnd = currentInterval[1];
            int nextStart = nextInterval[0];
            int nextEnd = nextInterval[1];

            // Overlap detected: extend the end boundary of the current interval
            if (currentEnd >= nextStart) {
                currentInterval[1] = Math.max(currentEnd, nextEnd);
            } else {
                // No overlap: create a fresh interval reference and append to output list
                currentInterval = new int[]{nextStart, nextEnd};
                mergedList.add(currentInterval);
            }
        }

        return mergedList.toArray(new int[0][]);
    }
}
