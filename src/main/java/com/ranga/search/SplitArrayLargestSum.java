package com.ranga.search;

import java.util.Arrays;

/**
 * Given an integer array {@code nums} and an integer {@code k}, split {@code nums} into {@code k} non-empty contiguous subarrays so that the largest sum of any subarray is minimized. Return that minimized largest sum.
 *
 * <p><b>Algorithm Strategy (Binary Search on the Answer):</b>
 * <ol>
 *   <li>The answer lies between {@code max(nums)} (each element alone) and {@code sum(nums)} (one subarray).</li>
 *   <li>For a candidate limit {@code cap}, greedily fill each subarray until adding the next value would exceed {@code cap}, then start a new one and count the pieces.</li>
 *   <li>If the pieces needed are at most {@code k}, {@code cap} is feasible, so try a smaller limit; otherwise try a larger one.</li>
 *   <li>The smallest feasible {@code cap} is the answer.</li>
 * </ol>
 *
 * <p><b>Example walkthrough</b>
 * <ul>
 *   <li>For {@code [7, 2, 5, 10, 8]} and {@code k = 2}: with {@code cap = 18} the greedy split is {@code [7,2,5]} and {@code [10,8]}, two pieces, so 18 is feasible; 17 needs three pieces.</li>
 *   <li>Result: {@code 18}.</li>
 * </ul>
 *
 * <p><b>Complexity Analysis:</b>
 * <ul>
 *   <li><b>Time Complexity:</b> {@code O(n log(sum))} — A binary search over the sum range with an O(n) check per step.</li>
 *   <li><b>Space Complexity:</b> {@code O(1)} — Only counters.</li>
 * </ul>
 *
 * @author Ranga Reddy
 */
public class SplitArrayLargestSum {

    public static void main(String[] args) {
        int[][] nums = {{7, 2, 5, 10, 8}, {1, 2, 3, 4, 5}, {1, 4, 4}, {5}};
        int[] ks = {2, 2, 3, 1};
        for (int i = 0; i < nums.length; i++) {
            System.out.println("Input  : nums = " + Arrays.toString(nums[i]) + ", k = " + ks[i]);
            System.out.println("Output : " + splitArray(nums[i], ks[i]));
            System.out.println();
        }
    }

    public static int splitArray(int[] nums, int k) {
        if (nums == null || nums.length == 0 || k <= 0) {
            return 0;
        }
        int lo = 0;
        int hi = 0;
        for (int x : nums) {
            lo = Math.max(lo, x);
            hi += x;
        }
        while (lo < hi) {
            int mid = lo + (hi - lo) / 2;
            if (piecesNeeded(nums, mid) <= k) {
                hi = mid;
            } else {
                lo = mid + 1;
            }
        }
        return lo;
    }

    private static int piecesNeeded(int[] nums, int cap) {
        int pieces = 1;
        int sum = 0;
        for (int x : nums) {
            if (sum + x > cap) {
                pieces++;
                sum = 0;
            }
            sum += x;
        }
        return pieces;
    }
}
