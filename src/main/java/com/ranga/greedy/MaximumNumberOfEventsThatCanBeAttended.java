package com.ranga.greedy;

import java.util.Arrays;
import java.util.PriorityQueue;

/**
 * Given events {@code [startDay, endDay]}, you can attend an event on any single day from its start to its end (inclusive), and only one event per day. Return the maximum number of events you can attend.
 *
 * <p><b>Algorithm Strategy (Earliest Deadline First):</b>
 * <ol>
 *   <li>Sort the events by start day.</li>
 *   <li>Sweep the days from the earliest start. On each day, add the end days of all events that start today to a min-heap.</li>
 *   <li>Remove events from the heap whose end day has already passed.</li>
 *   <li>If the heap is not empty, attend the event with the earliest end day (pop it) and count it; move on to the next day.</li>
 * </ol>
 *
 * <p><b>Example walkthrough</b>
 * <ul>
 *   <li>For {@code [[1,2],[2,3],[3,4]]}: attend them on days 1, 2 and 3.</li>
 *   <li>Result: {@code 3}.</li>
 * </ul>
 *
 * <p><b>Complexity Analysis:</b>
 * <ul>
 *   <li><b>Time Complexity:</b> {@code O(n log n + D)} — Sorting plus heap operations; {@code D} is the number of days swept.</li>
 *   <li><b>Space Complexity:</b> {@code O(n)} — The heap.</li>
 * </ul>
 *
 * @author Ranga Reddy
 */
public class MaximumNumberOfEventsThatCanBeAttended {

    public static void main(String[] args) {
        int[][][] inputs = {{{1, 2}, {2, 3}, {3, 4}}, {{1, 2}, {2, 3}, {3, 4}, {1, 2}}, {{1, 4}, {4, 4}, {2, 2}, {3, 4}, {1, 1}}, {{1, 1}}};
        for (int[][] events : inputs) {
            System.out.println("Input  : events = " + Arrays.deepToString(events));
            System.out.println("Output : " + maxEvents(events.clone()));
            System.out.println();
        }
    }

    public static int maxEvents(int[][] events) {
        if (events == null || events.length == 0) {
            return 0;
        }
        Arrays.sort(events, (a, b) -> a[0] - b[0]);
        PriorityQueue<Integer> ends = new PriorityQueue<>();
        int attended = 0;
        int i = 0;
        int day = 0;
        while (i < events.length || !ends.isEmpty()) {
            if (ends.isEmpty()) {
                day = events[i][0];
            }
            while (i < events.length && events[i][0] <= day) {
                ends.add(events[i++][1]);
            }
            while (!ends.isEmpty() && ends.peek() < day) {
                ends.poll();
            }
            if (!ends.isEmpty()) {
                ends.poll();
                attended++;
            }
            day++;
        }
        return attended;
    }
}
