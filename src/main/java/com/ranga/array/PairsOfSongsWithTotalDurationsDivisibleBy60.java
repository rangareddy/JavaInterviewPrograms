package com.ranga.array;

import java.util.Arrays;

/**
 * You are given a list of songs where the {@code i}-th song has a duration of {@code time[i]} seconds. Return the number of pairs of songs {@code (i, j)} with {@code i < j} whose total duration in seconds is divisible by {@code 60}.
 *
 * <p><b>Algorithm Strategy (Remainder Counting):</b>
 * <ol>
 *   <li>Only {@code time[i] % 60} matters.</li>
 *   <li>Keep an array {@code seen[60]} counting how many earlier songs have each remainder.</li>
 *   <li>For each song with remainder {@code r}, the earlier songs that pair with it have remainder {@code (60 - r) % 60}; add that count to the answer.</li>
 *   <li>Then record the remainder of the current song.</li>
 * </ol>
 *
 * <p><b>Example walkthrough</b>
 * <ul>
 *   <li>For {@code [30, 20, 150, 100, 40]}: remainders are 30, 20, 30, 40, 40.</li>
 *   <li>The pairs are (30, 30), (20, 40) and (20, 40), so the answer is 3.</li>
 *   <li>Result: {@code 3}.</li>
 * </ul>
 *
 * <p><b>Complexity Analysis:</b>
 * <ul>
 *   <li><b>Time Complexity:</b> {@code O(n)} — One pass with constant work per song.</li>
 *   <li><b>Space Complexity:</b> {@code O(1)} — A fixed array of 60 counters.</li>
 * </ul>
 *
 * @author Ranga Reddy
 */
public class PairsOfSongsWithTotalDurationsDivisibleBy60 {

    public static void main(String[] args) {
        int[][] inputs = {{30, 20, 150, 100, 40}, {60, 60, 60}, {10}, {}, {1, 59, 61, 119}};
        for (int[] time : inputs) {
            System.out.println("Input  : time = " + Arrays.toString(time));
            System.out.println("Output : " + numPairsDivisibleBy60(time));
            System.out.println();
        }
    }

    public static int numPairsDivisibleBy60(int[] time) {
        if (time == null) {
            return 0;
        }
        int[] seen = new int[60];
        int pairs = 0;
        for (int t : time) {
            int remainder = t % 60;
            pairs += seen[(60 - remainder) % 60];
            seen[remainder]++;
        }
        return pairs;
    }
}
