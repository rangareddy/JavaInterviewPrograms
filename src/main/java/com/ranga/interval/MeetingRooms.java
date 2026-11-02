package com.ranga.interval;

import java.util.Arrays;
import java.util.Comparator;

/**
 * Given an array of meeting time intervals {@code [start, end]}, determine whether a person can attend all the meetings (no two meetings overlap).
 *
 * <p><b>Algorithm Strategy (Sort by Start Time):</b>
 * <ol>
 *   <li>Sort the meetings by start time.</li>
 *   <li>Compare every meeting with the previous one: if it starts before the previous meeting ends ({@code start < previousEnd}), they overlap and the person cannot attend both.</li>
 *   <li>If no pair of neighboring meetings overlaps, all meetings can be attended. (Meetings that end exactly when the next one starts are fine.)</li>
 * </ol>
 *
 * <p><b>Example walkthrough</b>
 * <ul>
 *   <li>For {@code [[0,30],[5,10],[15,20]]}: sorted order is the same; 5 &lt; 30 means the first two meetings overlap, so the result is {@code false}.</li>
 *   <li>For {@code [[7,10],[2,4]]} sorted {@code [[2,4],[7,10]]}: 7 &gt;= 4, so {@code true}.</li>
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
public class MeetingRooms {

    public static void main(String[] args) {
        int[][][] inputs = {{{0, 30}, {5, 10}, {15, 20}}, {{7, 10}, {2, 4}}, {{1, 5}, {5, 8}}};
        for (int[][] intervals : inputs) {
            System.out.println("Input  : intervals = " + Arrays.deepToString(intervals));
            System.out.println("Output : " + canAttendMeetings(intervals));
            System.out.println();
        }
    }

    public static boolean canAttendMeetings(int[][] intervals) {
        if (intervals == null || intervals.length < 2) {
            return true;
        }

        int[][] sorted = intervals.clone();
        Arrays.sort(sorted, Comparator.comparingInt((int[] interval) -> interval[0]));
        for (int i = 1; i < sorted.length; i++) {
            if (sorted[i][0] < sorted[i - 1][1]) {
                return false;
            }
        }
        return true;
    }
}
