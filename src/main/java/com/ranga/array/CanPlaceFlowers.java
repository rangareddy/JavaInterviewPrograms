package com.ranga.array;

import java.util.Arrays;

/**
 * A flowerbed is an array of {@code 0}s (empty) and {@code 1}s (planted). Flowers cannot be planted in adjacent plots. Given the flowerbed and an integer {@code n}, return {@code true} if {@code n} new flowers can be planted without breaking the rule.
 *
 * <p><b>Algorithm Strategy (Greedy Left-to-Right):</b>
 * <ol>
 *   <li>Scan the plots from the left.</li>
 *   <li>A plot is plantable if it is empty and both neighbours are empty (a missing neighbour at either end counts as empty).</li>
 *   <li>Plant greedily: set the plot to 1 and count it, which also blocks the next plot.</li>
 *   <li>Return {@code true} once {@code n} flowers have been placed, or after the scan if the count is at least {@code n}.</li>
 * </ol>
 *
 * <p><b>Example walkthrough</b>
 * <ul>
 *   <li>For {@code [1, 0, 0, 0, 1]} and {@code n = 1}: the middle plot has empty neighbours, so one flower fits.</li>
 *   <li>Result: {@code true} for {@code n = 1}, and {@code false} for {@code n = 2}.</li>
 * </ul>
 *
 * <p><b>Complexity Analysis:</b>
 * <ul>
 *   <li><b>Time Complexity:</b> {@code O(n)} — One pass.</li>
 *   <li><b>Space Complexity:</b> {@code O(1)} — In place on a copy.</li>
 * </ul>
 *
 * @author Ranga Reddy
 */
public class CanPlaceFlowers {

    public static void main(String[] args) {
        int[][] beds = {{1, 0, 0, 0, 1}, {1, 0, 0, 0, 1}, {0, 0, 0}, {1}};
        int[] ns = {1, 2, 2, 0};
        for (int i = 0; i < beds.length; i++) {
            System.out.println("Input  : flowerbed = " + Arrays.toString(beds[i]) + ", n = " + ns[i]);
            System.out.println("Output : " + canPlaceFlowers(beds[i].clone(), ns[i]));
            System.out.println();
        }
    }

    public static boolean canPlaceFlowers(int[] flowerbed, int n) {
        if (n <= 0) {
            return true;
        }
        if (flowerbed == null) {
            return false;
        }
        int planted = 0;
        for (int i = 0; i < flowerbed.length && planted < n; i++) {
            boolean leftFree = i == 0 || flowerbed[i - 1] == 0;
            boolean rightFree = i == flowerbed.length - 1 || flowerbed[i + 1] == 0;
            if (flowerbed[i] == 0 && leftFree && rightFree) {
                flowerbed[i] = 1;
                planted++;
            }
        }
        return planted >= n;
    }
}
