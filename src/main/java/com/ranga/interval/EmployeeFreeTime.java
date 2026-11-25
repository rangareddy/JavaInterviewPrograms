package com.ranga.interval;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Each employee has a schedule, a sorted list of non-overlapping busy intervals {@code [start, end]}. Return the finite intervals during which all employees are free, in sorted order (do not include the infinite time before the first and after the last busy interval).
 *
 * <p><b>Algorithm Strategy (Merge All Busy Intervals, Report the Gaps):</b>
 * <ol>
 *   <li>Flatten all employees' intervals into one list and sort it by start time.</li>
 *   <li>Sweep the list keeping the furthest {@code end} seen so far.</li>
 *   <li>If the next interval starts after that {@code end}, the gap {@code [end, start]} is free time for everyone: record it.</li>
 *   <li>Update {@code end = max(end, interval.end)} and continue.</li>
 * </ol>
 *
 * <p><b>Example walkthrough</b>
 * <ul>
 *   <li>For the schedules {@code [[1,2],[5,6]]}, {@code [[1,3]]} and {@code [[4,10]]}: merging gives busy time {@code [1, 3]} and {@code [4, 10]}.</li>
 *   <li>The free time between them is {@code [3, 4]}.</li>
 * </ul>
 *
 * <p><b>Complexity Analysis:</b>
 * <ul>
 *   <li><b>Time Complexity:</b> {@code O(n log n)} — Sorting all intervals.</li>
 *   <li><b>Space Complexity:</b> {@code O(n)} — The flat list.</li>
 * </ul>
 *
 * @author Ranga Reddy
 */
public class EmployeeFreeTime {

    public static void main(String[] args) {
        int[][][][] inputs = {
            {{{1, 2}, {5, 6}}, {{1, 3}}, {{4, 10}}},
            {{{1, 3}, {6, 7}}, {{2, 4}}, {{2, 5}, {9, 12}}},
            {{{1, 5}}, {{2, 3}}}
        };
        for (int[][][] schedule : inputs) {
            System.out.println("Input  : schedule = " + Arrays.deepToString(schedule));
            System.out.println("Output : " + Arrays.deepToString(employeeFreeTime(schedule)));
            System.out.println();
        }
    }

    public static int[][] employeeFreeTime(int[][][] schedule) {
        List<int[]> all = new ArrayList<>();
        if (schedule != null) {
            for (int[][] employee : schedule) {
                all.addAll(Arrays.asList(employee));
            }
        }
        if (all.isEmpty()) {
            return new int[0][];
        }
        all.sort((a, b) -> a[0] - b[0]);
        List<int[]> free = new ArrayList<>();
        int end = all.get(0)[1];
        for (int[] interval : all) {
            if (interval[0] > end) {
                free.add(new int[]{end, interval[0]});
            }
            end = Math.max(end, interval[1]);
        }
        return free.toArray(new int[0][]);
    }
}
