package com.ranga.search;

import java.util.Arrays;

/**
 * Packages with the given {@code weights} must be shipped in order (no reordering) by a ship that loads packages each day up to its capacity. Return the least ship capacity that delivers all packages within {@code days} days.
 *
 * <p><b>Algorithm Strategy (Binary Search on the Answer):</b>
 * <ol>
 *   <li>The answer lies between {@code max(weights)} (every package must fit on the ship) and {@code sum(weights)} (everything ships in one day).</li>
 *   <li>Feasibility is monotonic: if capacity {@code c} works, any larger capacity also works. That makes the question "is {@code c} enough?" suitable for binary search.</li>
 *   <li>To test a capacity, simulate the loading greedily: add packages to the current day until the next one would exceed {@code c}, then start a new day. Count the days.</li>
 *   <li>If the days needed are at most {@code days}, try a smaller capacity ({@code hi = mid}), otherwise a larger one ({@code lo = mid + 1}). The search ends when {@code lo == hi}.</li>
 * </ol>
 *
 * <p><b>Example walkthrough</b>
 * <ul>
 *   <li>For {@code weights = [1..10]}, {@code days = 5}: the search range is {@code [10, 55]}.</li>
 *   <li>Capacity 15 needs 5 days ({@code 1..5}, {@code 6..7}, {@code 8}, {@code 9}, {@code 10}), capacity 14 needs 6, so the answer is {@code 15}.</li>
 * </ul>
 *
 * <p><b>Complexity Analysis:</b>
 * <ul>
 *   <li><b>Time Complexity:</b> {@code O(n log S)} — {@code S} is the sum of the weights; each feasibility check is {@code O(n)} and the search needs {@code log S} checks.</li>
 *   <li><b>Space Complexity:</b> {@code O(1)} — A few integers.</li>
 * </ul>
 *
 * @author Ranga Reddy
 */
public class CapacityToShipPackages {

    public static void main(String[] args) {
        int[][] inputs = {{1, 2, 3, 4, 5, 6, 7, 8, 9, 10}, {3, 2, 2, 4, 1, 4}, {1, 2, 3, 1, 1}};
        int[] days = {5, 3, 4};
        for (int i = 0; i < inputs.length; i++) {
            System.out.println("Input  : weights = " + Arrays.toString(inputs[i]) + ", days = " + days[i]);
            System.out.println("Output : " + shipWithinDays(inputs[i], days[i]));
            System.out.println();
        }
    }

    public static int shipWithinDays(int[] weights, int days) {
        int lo = 0;
        int hi = 0;
        for (int w : weights) {
            lo = Math.max(lo, w);
            hi += w;
        }
        while (lo < hi) {
            int mid = lo + (hi - lo) / 2;
            if (daysNeeded(weights, mid) <= days) {
                hi = mid;
            } else {
                lo = mid + 1;
            }
        }
        return lo;
    }

    private static int daysNeeded(int[] weights, int capacity) {
        int days = 1;
        int load = 0;
        for (int w : weights) {
            if (load + w > capacity) {
                days++;
                load = 0;
            }
            load += w;
        }
        return days;
    }
}
