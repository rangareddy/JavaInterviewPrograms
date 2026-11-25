package com.ranga.array;

import java.util.Arrays;

/**
 * A biker goes on a road trip across {@code n + 1} points at different altitudes. They start at point 0 with altitude 0. You are given an integer array {@code gain} of length {@code n}, where {@code gain[i]} is the net gain in altitude between points {@code i} and {@code i + 1}. Return the highest altitude reached.
 *
 * <p><b>Algorithm Strategy (Running Sum with a Maximum):</b>
 * <ol>
 *   <li>Start with {@code altitude = 0} and {@code highest = 0} (the start counts).</li>
 *   <li>For each gain, add it to {@code altitude}.</li>
 *   <li>Update {@code highest} with the new altitude.</li>
 *   <li>Return {@code highest}.</li>
 * </ol>
 *
 * <p><b>Example walkthrough</b>
 * <ul>
 *   <li>For {@code [-5, 1, 5, 0, -7]}: altitudes are 0, -5, -4, 1, 1, -6.</li>
 *   <li>The highest is 1.</li>
 *   <li>Result: {@code 1}.</li>
 * </ul>
 *
 * <p><b>Complexity Analysis:</b>
 * <ul>
 *   <li><b>Time Complexity:</b> {@code O(n)} — One pass.</li>
 *   <li><b>Space Complexity:</b> {@code O(1)} — Two numbers.</li>
 * </ul>
 *
 * @author Ranga Reddy
 */
public class FindTheHighestAltitude {

    public static void main(String[] args) {
        int[][] inputs = {{-5, 1, 5, 0, -7}, {-4, -3, -2, -1, 4, 3, 2}, {}, {3, 3}, {-1}};
        for (int[] gain : inputs) {
            System.out.println("Input  : gain = " + Arrays.toString(gain));
            System.out.println("Output : " + largestAltitude(gain));
            System.out.println();
        }
    }

    public static int largestAltitude(int[] gain) {
        int altitude = 0;
        int highest = 0;
        if (gain == null) {
            return 0;
        }
        for (int g : gain) {
            altitude += g;
            highest = Math.max(highest, altitude);
        }
        return highest;
    }
}
