package com.ranga.blind75.interval;

import java.util.Arrays;

/**
 * Given an array of meeting time intervals {@code [start, end]}, find the minimum number of conference rooms required.
 *
 * <p><b>Algorithm Strategy (Sorted Start and End Times):</b>
 * <ol>
 *   <li>Put all start times in one array and all end times in another, and sort both. The pairing of start and end does not matter: only how many meetings run at the same time.</li>
 *   <li>Walk through the start times in order with a pointer {@code end} into the sorted end times.</li>
 *   <li>If a meeting starts before the earliest unfinished meeting ends ({@code start < ends[end]}), it needs a new room: increment {@code rooms}.</li>
 *   <li>Otherwise a room has just been freed and is reused: advance the {@code end} pointer. The final {@code rooms} is the maximum number of simultaneous meetings.</li>
 * </ol>
 *
 * <p><b>Example walkthrough</b>
 * <ul>
 *   <li>For {@code [[0,30],[5,10],[15,20]]}: starts {@code [0,5,15]}, ends {@code [10,20,30]}.</li>
 *   <li>0 &lt; 10 and 5 &lt; 10 open two rooms; 15 &gt;= 10 reuses the first room. Result: {@code 2}.</li>
 * </ul>
 *
 * <p><b>Complexity Analysis:</b>
 * <ul>
 *   <li><b>Time Complexity:</b> {@code O(n log n)} — Dominated by sorting.</li>
 *   <li><b>Space Complexity:</b> {@code O(n)} — The two sorted arrays.</li>
 * </ul>
 *
 * @author Ranga Reddy
 */
public class MeetingRoomsII {

    public static void main(String[] args) {
        int[][][] inputs = {{{0, 30}, {5, 10}, {15, 20}}, {{7, 10}, {2, 4}}, {{1, 5}, {2, 6}, {3, 7}, {4, 8}}};
        for (int[][] intervals : inputs) {
            System.out.println("Input  : intervals = " + Arrays.deepToString(intervals));
            System.out.println("Output : " + minMeetingRooms(intervals));
            System.out.println();
        }
    }

    public static int minMeetingRooms(int[][] intervals) {
        if (intervals == null || intervals.length == 0) {
            return 0;
        }

        int n = intervals.length;
        int[] starts = new int[n];
        int[] ends = new int[n];
        for (int i = 0; i < n; i++) {
            starts[i] = intervals[i][0];
            ends[i] = intervals[i][1];
        }
        Arrays.sort(starts);
        Arrays.sort(ends);

        int rooms = 0;
        int end = 0;
        for (int start : starts) {
            if (start < ends[end]) {
                rooms++;        // every room is busy: open a new one
            } else {
                end++;          // a meeting has ended: reuse its room
            }
        }
        return rooms;
    }
}
