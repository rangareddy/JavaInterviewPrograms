package com.ranga.search;

import java.util.Arrays;

/**
 * Houses and heaters are placed on a horizontal line. Every heater warms all houses within its radius. Given the positions of {@code houses} and {@code heaters}, return the minimum radius so that every house is covered by at least one heater (all heaters use the same radius).
 *
 * <p><b>Algorithm Strategy (Sort and Binary Search the Nearest Heater):</b>
 * <ol>
 *   <li>Sort the heaters.</li>
 *   <li>For each house, binary search the first heater at a position {@code >=} the house.</li>
 *   <li>The nearest heater is that one or the one just before it; the distance to it is the house's required radius.</li>
 *   <li>The answer is the largest of those distances.</li>
 * </ol>
 *
 * <p><b>Example walkthrough</b>
 * <ul>
 *   <li>For houses {@code [1, 2, 3]} and heater {@code [2]}: houses 1 and 3 are one unit away.</li>
 *   <li>Result: {@code 1}.</li>
 * </ul>
 *
 * <p><b>Complexity Analysis:</b>
 * <ul>
 *   <li><b>Time Complexity:</b> {@code O((n + m) log m)} — Sorting the heaters and one binary search per house.</li>
 *   <li><b>Space Complexity:</b> {@code O(1)} — Apart from sorting.</li>
 * </ul>
 *
 * @author Ranga Reddy
 */
public class Heaters {

    public static void main(String[] args) {
        int[][] houses = {{1, 2, 3}, {1, 2, 3, 4}, {1, 5}, {1, 2, 3, 5, 15}};
        int[][] heaters = {{2}, {1, 4}, {2}, {2, 30}};
        for (int i = 0; i < houses.length; i++) {
            System.out.println("Input  : houses = " + Arrays.toString(houses[i]) + ", heaters = " + Arrays.toString(heaters[i]));
            System.out.println("Output : " + findRadius(houses[i], heaters[i].clone()));
            System.out.println();
        }
    }

    public static int findRadius(int[] houses, int[] heaters) {
        if (houses == null || houses.length == 0 || heaters == null || heaters.length == 0) {
            return 0;
        }
        Arrays.sort(heaters);
        int radius = 0;
        for (int house : houses) {
            int lo = 0;
            int hi = heaters.length;
            while (lo < hi) {
                int mid = lo + (hi - lo) / 2;
                if (heaters[mid] < house) {
                    lo = mid + 1;
                } else {
                    hi = mid;
                }
            }
            int right = lo < heaters.length ? heaters[lo] - house : Integer.MAX_VALUE;
            int left = lo > 0 ? house - heaters[lo - 1] : Integer.MAX_VALUE;
            radius = Math.max(radius, Math.min(left, right));
        }
        return radius;
    }
}
