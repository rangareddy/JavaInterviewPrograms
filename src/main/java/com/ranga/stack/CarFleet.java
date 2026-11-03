package com.ranga.stack;

import java.util.Arrays;

/**
 * There are {@code n} cars heading to a destination {@code target} miles away on a one-lane road. Car {@code i} starts at {@code position[i]} with constant speed {@code speed[i]}. A car cannot pass another car; it can only catch up and then drive at the speed of the slower car in front, forming a fleet. Return the number of car fleets that arrive at the destination.
 *
 * <p><b>Algorithm Strategy (Sort by Position, Compare Arrival Times):</b>
 * <ol>
 *   <li>Compute for each car the time it needs to reach the target if nothing blocked it: {@code (target - position) / speed}.</li>
 *   <li>Process the cars from the closest to the target to the farthest (sort by position in decreasing order).</li>
 *   <li>A car whose arrival time is not larger than the arrival time of the fleet in front of it catches up and joins that fleet.</li>
 *   <li>A car with a larger arrival time can never catch the fleet in front, so it starts a new fleet. Count the fleets.</li>
 * </ol>
 *
 * <p><b>Example walkthrough</b>
 * <ul>
 *   <li>For target 12, positions {@code [10, 8, 0, 5, 3]}, speeds {@code [2, 4, 1, 1, 3]}: sorted by position the arrival times are 1, 1, 7, 3, 12.</li>
 *   <li>The cars with times 1, 1 form one fleet, 7 and 3 form another (3 &lt;= 7), and 12 starts a third: {@code 3}.</li>
 * </ul>
 *
 * <p><b>Complexity Analysis:</b>
 * <ul>
 *   <li><b>Time Complexity:</b> {@code O(n log n)} — Dominated by sorting.</li>
 *   <li><b>Space Complexity:</b> {@code O(n)} — Sorted indexes and arrival times.</li>
 * </ul>
 *
 * @author Ranga Reddy
 */
public class CarFleet {

    public static void main(String[] args) {
        int[] targets = {12, 10, 100, 10};
        int[][] positions = {{10, 8, 0, 5, 3}, {3}, {0, 2, 4}, {6, 8}};
        int[][] speeds = {{2, 4, 1, 1, 3}, {3}, {4, 2, 1}, {3, 2}};
        for (int i = 0; i < targets.length; i++) {
            System.out.println("Input  : target = " + targets[i] + ", position = " + Arrays.toString(positions[i]) + ", speed = " + Arrays.toString(speeds[i]));
            System.out.println("Output : " + carFleet(targets[i], positions[i], speeds[i]));
            System.out.println();
        }
    }

    public static int carFleet(int target, int[] position, int[] speed) {
        int n = position.length;
        Integer[] order = new Integer[n];
        for (int i = 0; i < n; i++) {
            order[i] = i;
        }
        Arrays.sort(order, (a, b) -> position[b] - position[a]);        // closest to the target first

        int fleets = 0;
        double slowestAhead = 0;
        for (int index : order) {
            double time = (double) (target - position[index]) / speed[index];
            if (time > slowestAhead) {          // cannot catch the fleet in front: a new fleet
                fleets++;
                slowestAhead = time;
            }
        }
        return fleets;
    }
}
