package com.ranga.array;

import java.util.Arrays;

/**
 * Given a row of seats, {@code seats[i] = 1} means the seat is occupied and {@code 0} means empty. At least one seat is empty and at least one is occupied. Alex wants to sit in an empty seat so that the distance to the closest person is as large as possible. Return that maximum distance.
 *
 * <p><b>Algorithm Strategy (Longest Gap between Occupied Seats):</b>
 * <ol>
 *   <li>Between two occupied seats a gap of {@code g} positions lets Alex sit in the middle with distance {@code g / 2}.</li>
 *   <li>At the ends of the row there is no person on one side, so the whole run of empty seats counts: the distance equals the run length.</li>
 *   <li>Scan once, tracking the index of the previous occupied seat.</li>
 *   <li>The answer is the maximum of the leading run, the trailing run and half of each inner gap.</li>
 * </ol>
 *
 * <p><b>Example walkthrough</b>
 * <ul>
 *   <li>For {@code [1, 0, 0, 0, 1, 0, 1]} the inner gap between index 0 and 4 has 4 steps, so Alex sits in the middle at distance 2.</li>
 *   <li>Result: {@code 2}.</li>
 * </ul>
 *
 * <p><b>Complexity Analysis:</b>
 * <ul>
 *   <li><b>Time Complexity:</b> {@code O(n)} — One pass.</li>
 *   <li><b>Space Complexity:</b> {@code O(1)} — A few indexes.</li>
 * </ul>
 *
 * @author Ranga Reddy
 */
public class MaximizeDistanceToClosestPerson {

    public static void main(String[] args) {
        int[][] inputs = {{1, 0, 0, 0, 1, 0, 1}, {1, 0, 0, 0}, {0, 1}, {1, 0, 1}};
        for (int[] seats : inputs) {
            System.out.println("Input  : seats = " + Arrays.toString(seats));
            System.out.println("Output : " + maxDistToClosest(seats));
            System.out.println();
        }
    }

    public static int maxDistToClosest(int[] seats) {
        if (seats == null || seats.length == 0) {
            return 0;
        }
        int best = 0;
        int previous = -1;
        for (int i = 0; i < seats.length; i++) {
            if (seats[i] == 1) {
                best = previous == -1 ? i : Math.max(best, (i - previous) / 2);
                previous = i;
            }
        }
        if (previous != -1) {
            best = Math.max(best, seats.length - 1 - previous);
        }
        return best;
    }
}
