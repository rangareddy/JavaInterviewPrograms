package com.ranga.greedy;

import java.util.Arrays;

/**
 * You have {@code k} bags and a row of marbles with weights {@code weights}. Divide the marbles into {@code k} non-empty bags such that each bag is a contiguous segment. The cost of a bag with marbles from index {@code i} to {@code j} is {@code weights[i] + weights[j]}. The score of a distribution is the sum of the bag costs. Return the difference between the maximum and the minimum possible scores.
 *
 * <p><b>Algorithm Strategy (Sort the Cut Costs):</b>
 * <ol>
 *   <li>Whatever the cuts, the first and last marbles always count ({@code weights[0] + weights[n - 1]}), so only the {@code k - 1} cuts matter.</li>
 *   <li>A cut between marbles {@code i} and {@code i + 1} adds {@code weights[i] + weights[i + 1]} to the score.</li>
 *   <li>Compute all {@code n - 1} pair sums and sort them.</li>
 *   <li>The maximum score picks the {@code k - 1} largest cuts and the minimum picks the {@code k - 1} smallest; the answer is their difference.</li>
 * </ol>
 *
 * <p><b>Example walkthrough</b>
 * <ul>
 *   <li>For {@code weights = [1, 3, 5, 1]} and {@code k = 2}: the cut costs are 4, 8 and 6.</li>
 *   <li>Maximum uses 8, minimum uses 4, so the answer is {@code 8 - 4 = 4}.</li>
 * </ul>
 *
 * <p><b>Complexity Analysis:</b>
 * <ul>
 *   <li><b>Time Complexity:</b> {@code O(n log n)} — Sorting the pair sums.</li>
 *   <li><b>Space Complexity:</b> {@code O(n)} — The pair sums.</li>
 * </ul>
 *
 * @author Ranga Reddy
 */
public class PutMarblesInBags {

    public static void main(String[] args) {
        int[][] weights = {{1, 3, 5, 1}, {1, 3}, {1, 4, 2, 5, 2}, {7}};
        int[] ks = {2, 2, 3, 1};
        for (int i = 0; i < weights.length; i++) {
            System.out.println("Input  : weights = " + Arrays.toString(weights[i]) + ", k = " + ks[i]);
            System.out.println("Output : " + putMarbles(weights[i], ks[i]));
            System.out.println();
        }
    }

    public static long putMarbles(int[] weights, int k) {
        if (weights == null || weights.length < 2 || k <= 1) {
            return 0;
        }
        int n = weights.length;
        long[] cuts = new long[n - 1];
        for (int i = 0; i < n - 1; i++) {
            cuts[i] = (long) weights[i] + weights[i + 1];
        }
        Arrays.sort(cuts);
        long difference = 0;
        for (int i = 0; i < k - 1; i++) {
            difference += cuts[n - 2 - i] - cuts[i];
        }
        return difference;
    }
}
