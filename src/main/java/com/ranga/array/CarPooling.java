package com.ranga.array;

import java.util.Arrays;

/**
 * A car with {@code capacity} empty seats drives only east. Given trips {@code [numPassengers, from, to]} (the passengers are picked up at {@code from} and dropped off at {@code to}), return {@code true} if it is possible to pick up and drop off all passengers without ever exceeding the capacity.
 *
 * <p><b>Algorithm Strategy (Difference Array over the Route):</b>
 * <ol>
 *   <li>Create an array {@code change} indexed by location, long enough for the largest {@code to}.</li>
 *   <li>For each trip, add {@code numPassengers} at {@code from} and subtract it at {@code to}.</li>
 *   <li>Sweep the locations keeping a running total of passengers on board.</li>
 *   <li>If the running total ever exceeds {@code capacity}, return {@code false}; otherwise {@code true}.</li>
 * </ol>
 *
 * <p><b>Example walkthrough</b>
 * <ul>
 *   <li>For trips {@code [[2,1,5],[3,3,7]]} and capacity 4: between locations 3 and 5 there are 2 + 3 = 5 passengers.</li>
 *   <li>Result: {@code false}.</li>
 * </ul>
 *
 * <p><b>Complexity Analysis:</b>
 * <ul>
 *   <li><b>Time Complexity:</b> {@code O(n + maxLocation)} — Process the trips, then sweep the route.</li>
 *   <li><b>Space Complexity:</b> {@code O(maxLocation)} — The change array.</li>
 * </ul>
 *
 * @author Ranga Reddy
 */
public class CarPooling {

    public static void main(String[] args) {
        int[][][] trips = {{{2, 1, 5}, {3, 3, 7}}, {{2, 1, 5}, {3, 3, 7}}, {{2, 1, 5}, {3, 5, 7}}, {{3, 2, 7}, {3, 7, 9}, {8, 3, 9}}};
        int[] capacity = {4, 5, 3, 11};
        for (int i = 0; i < trips.length; i++) {
            System.out.println("Input  : trips = " + Arrays.deepToString(trips[i]) + ", capacity = " + capacity[i]);
            System.out.println("Output : " + carPooling(trips[i], capacity[i]));
            System.out.println();
        }
    }

    public static boolean carPooling(int[][] trips, int capacity) {
        if (trips == null || trips.length == 0) {
            return true;
        }
        int last = 0;
        for (int[] trip : trips) {
            last = Math.max(last, trip[2]);
        }
        int[] change = new int[last + 1];
        for (int[] trip : trips) {
            change[trip[1]] += trip[0];
            change[trip[2]] -= trip[0];
        }
        int onBoard = 0;
        for (int delta : change) {
            onBoard += delta;
            if (onBoard > capacity) {
                return false;
            }
        }
        return true;
    }
}
