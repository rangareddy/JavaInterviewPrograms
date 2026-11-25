package com.ranga.backtracking;

import java.util.Arrays;

/**
 * Given an integer array {@code nums} and an integer {@code k}, return {@code true} if it is possible to divide this array into {@code k} non-empty subsets whose sums are all equal.
 *
 * <p><b>Algorithm Strategy (Backtracking, One Bucket at a Time):</b>
 * <ol>
 *   <li>If the total is not divisible by {@code k}, or any number exceeds {@code total / k}, the answer is {@code false}.</li>
 *   <li>Sort the numbers in descending order so that large values are placed first and dead ends show up early.</li>
 *   <li>Fill one bucket at a time: try each unused number that keeps the bucket at or below {@code target}.</li>
 *   <li>When a bucket reaches {@code target}, start the next one. If starting a bucket with the first unused number fails, stop; no other choice can work either.</li>
 * </ol>
 *
 * <p><b>Example walkthrough</b>
 * <ul>
 *   <li>For {@code [4, 3, 2, 3, 5, 2, 1]} and {@code k = 4}: the total is 20, so the target is 5.</li>
 *   <li>The buckets {@code {5}}, {@code {4, 1}}, {@code {3, 2}}, {@code {3, 2}} all sum to 5.</li>
 *   <li>Result: {@code true}.</li>
 * </ul>
 *
 * <p><b>Complexity Analysis:</b>
 * <ul>
 *   <li><b>Time Complexity:</b> {@code O(k * 2^n) worst case} — Each bucket explores subsets of the remaining numbers; pruning keeps it practical.</li>
 *   <li><b>Space Complexity:</b> {@code O(n)} — Used flags and recursion.</li>
 * </ul>
 *
 * @author Ranga Reddy
 */
public class PartitionToKEqualSumSubsets {

    public static void main(String[] args) {
        int[][] inputs = {{4, 3, 2, 3, 5, 2, 1}, {1, 2, 3, 4}, {2, 2, 2, 2, 3, 4, 5}, {1}, {}};
        int[] ks = {4, 3, 4, 1, 2};
        for (int i = 0; i < inputs.length; i++) {
            System.out.println("Input  : nums = " + Arrays.toString(inputs[i]) + ", k = " + ks[i]);
            System.out.println("Output : " + canPartitionKSubsets(inputs[i], ks[i]));
            System.out.println();
        }
    }

    public static boolean canPartitionKSubsets(int[] nums, int k) {
        if (nums == null || nums.length == 0 || k <= 0 || k > nums.length) {
            return false;
        }
        int sum = 0;
        for (int x : nums) {
            sum += x;
        }
        if (sum % k != 0) {
            return false;
        }
        int target = sum / k;
        int[] sorted = nums.clone();
        Arrays.sort(sorted);
        if (sorted[sorted.length - 1] > target) {
            return false;
        }
        return fill(sorted, new boolean[sorted.length], k, sorted.length - 1, 0, target);
    }

    private static boolean fill(int[] nums, boolean[] used, int bucketsLeft, int start, int current, int target) {
        if (bucketsLeft == 1) {
            return true;
        }
        if (current == target) {
            return fill(nums, used, bucketsLeft - 1, nums.length - 1, 0, target);
        }
        for (int i = Math.min(start, nums.length - 1); i >= 0; i--) {
            if (used[i] || current + nums[i] > target) {
                continue;
            }
            used[i] = true;
            if (fill(nums, used, bucketsLeft, i - 1, current + nums[i], target)) {
                return true;
            }
            used[i] = false;
            if (current == 0) {
                return false;
            }
        }
        return false;
    }
}
