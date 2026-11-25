package com.ranga.search;

import java.util.Arrays;

/**
 * You are given an integer array {@code bloomDay}, an integer {@code m} and an integer {@code k}. You want {@code m} bouquets. To make a bouquet you use {@code k} adjacent flowers from the garden, and the {@code i}-th flower blooms on day {@code bloomDay[i]} and can be used in exactly one bouquet. Return the minimum number of days you need to wait to make {@code m} bouquets, or {@code -1} if it is impossible.
 *
 * <p><b>Algorithm Strategy (Binary Search on the Number of Days):</b>
 * <ol>
 *   <li>If {@code m * k} is more than the number of flowers, return {@code -1}.</li>
 *   <li>The answer lies between the smallest and the largest bloom day. For a given day {@code d}, count how many bouquets can be made: scan the garden and count runs of adjacent flowers with {@code bloomDay <= d}, taking one bouquet per {@code k} flowers.</li>
 *   <li>The count never decreases as {@code d} grows, so binary search for the smallest day with at least {@code m} bouquets.</li>
 *   <li>Return that day.</li>
 * </ol>
 *
 * <p><b>Example walkthrough</b>
 * <ul>
 *   <li>For {@code bloomDay = [1, 10, 3, 10, 2]}, {@code m = 3}, {@code k = 1}: on day 3, flowers 0, 2 and 4 are open, giving 3 bouquets.</li>
 *   <li>Result: {@code 3}.</li>
 * </ul>
 *
 * <p><b>Complexity Analysis:</b>
 * <ul>
 *   <li><b>Time Complexity:</b> {@code O(n log(max day))} — One linear check per binary search step.</li>
 *   <li><b>Space Complexity:</b> {@code O(1)} — A counter.</li>
 * </ul>
 *
 * @author Ranga Reddy
 */
public class MinimumNumberOfDaysToMakeMBouquets {

    public static void main(String[] args) {
        int[][] days = {{1, 10, 3, 10, 2}, {1, 10, 3, 10, 2}, {7, 7, 7, 7, 12, 7, 7}, {1, 10, 2, 9, 3, 8, 4, 7, 5, 6}};
        int[] ms = {3, 3, 2, 4};
        int[] ks = {1, 2, 3, 2};
        for (int i = 0; i < days.length; i++) {
            System.out.println("Input  : bloomDay = " + Arrays.toString(days[i]) + ", m = " + ms[i] + ", k = " + ks[i]);
            System.out.println("Output : " + minDays(days[i], ms[i], ks[i]));
            System.out.println();
        }
    }

    public static int minDays(int[] bloomDay, int m, int k) {
        if (bloomDay == null || m <= 0 || k <= 0 || (long) m * k > bloomDay.length) {
            return -1;
        }
        int lo = Integer.MAX_VALUE;
        int hi = 0;
        for (int day : bloomDay) {
            lo = Math.min(lo, day);
            hi = Math.max(hi, day);
        }
        while (lo < hi) {
            int mid = lo + (hi - lo) / 2;
            if (bouquets(bloomDay, mid, k) >= m) {
                hi = mid;
            } else {
                lo = mid + 1;
            }
        }
        return lo;
    }

    private static int bouquets(int[] bloomDay, int day, int k) {
        int count = 0;
        int run = 0;
        for (int bloom : bloomDay) {
            if (bloom <= day) {
                if (++run == k) {
                    count++;
                    run = 0;
                }
            } else {
                run = 0;
            }
        }
        return count;
    }
}
