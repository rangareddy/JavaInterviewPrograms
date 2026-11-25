package com.ranga.greedy;

import java.util.Arrays;

/**
 * A one-dimensional garden spans points 0 to {@code n}. There are {@code n + 1} taps at points {@code 0..n}; tap {@code i} waters the area {@code [i - ranges[i], i + ranges[i]]} when opened. Return the minimum number of taps to open to water the whole garden, or {@code -1} if it is impossible.
 *
 * <p><b>Algorithm Strategy (Convert to Jump Game):</b>
 * <ol>
 *   <li>For each tap, the leftmost point it covers is {@code left = max(0, i - ranges[i])}. Store in {@code reach[left]} the furthest right end any tap starting there can reach.</li>
 *   <li>Now the problem is a jump game: from position {@code p} you can jump anywhere up to {@code reach[p]}.</li>
 *   <li>Greedily extend: keep the current covered end {@code curEnd}, the furthest end seen so far {@code farthest}, and a counter of taps.</li>
 *   <li>When the scan reaches {@code curEnd}, open the tap that gave {@code farthest} (counter + 1); if {@code farthest} did not move beyond {@code curEnd}, return {@code -1}.</li>
 * </ol>
 *
 * <p><b>Example walkthrough</b>
 * <ul>
 *   <li>For {@code n = 5} and {@code ranges = [3, 4, 1, 1, 0, 0]}: the tap at point 1 covers {@code [-3, 5]}, the whole garden.</li>
 *   <li>Result: {@code 1}.</li>
 * </ul>
 *
 * <p><b>Complexity Analysis:</b>
 * <ul>
 *   <li><b>Time Complexity:</b> {@code O(n)} — One pass over the points.</li>
 *   <li><b>Space Complexity:</b> {@code O(n)} — The reach array.</li>
 * </ul>
 *
 * @author Ranga Reddy
 */
public class MinimumNumberOfTapsToOpenToWaterAGarden {

    public static void main(String[] args) {
        int[] ns = {5, 3, 7, 8};
        int[][] ranges = {{3, 4, 1, 1, 0, 0}, {0, 0, 0, 0}, {1, 2, 1, 0, 2, 1, 0, 1}, {4, 0, 0, 0, 0, 0, 0, 0, 4}};
        for (int i = 0; i < ns.length; i++) {
            System.out.println("Input  : n = " + ns[i] + ", ranges = " + Arrays.toString(ranges[i]));
            System.out.println("Output : " + minTaps(ns[i], ranges[i]));
            System.out.println();
        }
    }

    public static int minTaps(int n, int[] ranges) {
        if (ranges == null || ranges.length != n + 1) {
            return -1;
        }
        int[] reach = new int[n + 1];
        for (int i = 0; i <= n; i++) {
            int left = Math.max(0, i - ranges[i]);
            int right = Math.min(n, i + ranges[i]);
            reach[left] = Math.max(reach[left], right);
        }
        int taps = 0;
        int curEnd = 0;
        int farthest = 0;
        for (int i = 0; i < n; i++) {
            farthest = Math.max(farthest, reach[i]);
            if (i == curEnd) {
                if (farthest <= i) {
                    return -1;
                }
                taps++;
                curEnd = farthest;
            }
        }
        return taps;
    }
}
