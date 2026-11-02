package com.ranga.blind75.interval;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Given non-overlapping intervals sorted by start and a new interval, insert the new interval so the list stays sorted and non-overlapping, merging overlapping intervals if necessary.
 *
 * <p><b>Algorithm Strategy (Three Phases in One Pass):</b>
 * <ol>
 *   <li>Phase 1: copy every interval that ends before the new interval starts ({@code end < newStart}); they cannot overlap it.</li>
 *   <li>Phase 2: while an interval starts before or at the new end ({@code start <= newEnd}), it overlaps: widen the new interval to {@code [min(starts), max(ends)]}. Then add the merged interval.</li>
 *   <li>Phase 3: copy the remaining intervals, which start after the merged interval.</li>
 *   <li>Because the input is already sorted, a single pass suffices.</li>
 * </ol>
 *
 * <p><b>Example walkthrough</b>
 * <ul>
 *   <li>For {@code [[1,3],[6,9]]} and new {@code [2,5]}: {@code [1,3]} overlaps (2 &lt;= 3), giving {@code [1,5]}; {@code [6,9]} starts after 5.</li>
 *   <li>Result: {@code [[1,5],[6,9]]}.</li>
 * </ul>
 *
 * <p><b>Complexity Analysis:</b>
 * <ul>
 *   <li><b>Time Complexity:</b> {@code O(n)} — One pass over the intervals.</li>
 *   <li><b>Space Complexity:</b> {@code O(n)} — The output list.</li>
 * </ul>
 *
 * @author Ranga Reddy
 */
public class InsertInterval {

    public static void main(String[] args) {
        int[][][] intervals = {{{1, 3}, {6, 9}}, {{1, 2}, {3, 5}, {6, 7}, {8, 10}, {12, 16}}, {}};
        int[][] newIntervals = {{2, 5}, {4, 8}, {5, 7}};
        for (int i = 0; i < intervals.length; i++) {
            System.out.println("Input  : intervals = " + Arrays.deepToString(intervals[i]) + ", newInterval = " + Arrays.toString(newIntervals[i]));
            System.out.println("Output : " + Arrays.deepToString(insert(intervals[i], newIntervals[i])));
            System.out.println();
        }
    }

    public static int[][] insert(int[][] intervals, int[] newInterval) {
        List<int[]> result = new ArrayList<>();
        int i = 0;
        int n = intervals.length;

        // 1. Intervals that end before the new interval starts
        while (i < n && intervals[i][1] < newInterval[0]) {
            result.add(intervals[i++]);
        }

        // 2. Intervals that overlap the new interval are merged into it
        int start = newInterval[0];
        int end = newInterval[1];
        while (i < n && intervals[i][0] <= end) {
            start = Math.min(start, intervals[i][0]);
            end = Math.max(end, intervals[i][1]);
            i++;
        }
        result.add(new int[]{start, end});

        // 3. Intervals that start after the merged interval
        while (i < n) {
            result.add(intervals[i++]);
        }
        return result.toArray(new int[0][]);
    }
}
