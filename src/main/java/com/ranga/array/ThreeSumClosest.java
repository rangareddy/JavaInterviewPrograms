package com.ranga.array;

import java.util.Arrays;

/**
 * Given an integer array {@code nums} of length {@code n} and an integer {@code target}, find three integers in {@code nums} whose sum is closest to {@code target} and return that sum. Assume each input has exactly one solution.
 *
 * <p><b>Algorithm Strategy (Sort then Two Pointers):</b>
 * <ol>
 *   <li>Sort the array.</li>
 *   <li>Fix the first number {@code nums[i]} and use two pointers {@code lo = i + 1}, {@code hi = n - 1} on the rest.</li>
 *   <li>Compute {@code sum}; if it is closer to {@code target} than the best so far, remember it. If {@code sum == target}, return it immediately.</li>
 *   <li>Move {@code lo} right when {@code sum < target} and {@code hi} left when {@code sum > target}.</li>
 * </ol>
 *
 * <p><b>Example walkthrough</b>
 * <ul>
 *   <li>For {@code [-1, 2, 1, -4]} and {@code target = 1}: the sum {@code -1 + 2 + 1 = 2} is the closest.</li>
 *   <li>Result: {@code 2}.</li>
 * </ul>
 *
 * <p><b>Complexity Analysis:</b>
 * <ul>
 *   <li><b>Time Complexity:</b> {@code O(n^2)} — Sorting plus a two-pointer scan for every first element.</li>
 *   <li><b>Space Complexity:</b> {@code O(1)} — Apart from sorting.</li>
 * </ul>
 *
 * @author Ranga Reddy
 */
public class ThreeSumClosest {

    public static void main(String[] args) {
        int[][] nums = {{-1, 2, 1, -4}, {0, 0, 0}, {1, 1, 1, 0}, {4, 0, 5, -5, 3, 3, 0, -4, -5}};
        int[] targets = {1, 1, -100, -2};
        for (int i = 0; i < nums.length; i++) {
            System.out.println("Input  : nums = " + Arrays.toString(nums[i]) + ", target = " + targets[i]);
            System.out.println("Output : " + threeSumClosest(nums[i].clone(), targets[i]));
            System.out.println();
        }
    }

    public static int threeSumClosest(int[] nums, int target) {
        if (nums == null || nums.length < 3) {
            return 0;
        }
        Arrays.sort(nums);
        int best = nums[0] + nums[1] + nums[2];
        for (int i = 0; i < nums.length - 2; i++) {
            int lo = i + 1;
            int hi = nums.length - 1;
            while (lo < hi) {
                int sum = nums[i] + nums[lo] + nums[hi];
                if (Math.abs(sum - target) < Math.abs(best - target)) {
                    best = sum;
                }
                if (sum == target) {
                    return sum;
                } else if (sum < target) {
                    lo++;
                } else {
                    hi--;
                }
            }
        }
        return best;
    }
}
