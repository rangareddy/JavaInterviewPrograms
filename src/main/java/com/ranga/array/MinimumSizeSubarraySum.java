package com.ranga.array;

import java.util.Arrays;

/**
 * Given an array of positive integers {@code nums} and a positive integer {@code target}, return the minimal length of a contiguous subarray whose sum is greater than or equal to {@code target}. Return {@code 0} if there is no such subarray.
 *
 * <p><b>Algorithm Strategy (Sliding Window):</b>
 * <ol>
 *   <li>Because every number is positive, growing the window increases the sum and shrinking it decreases the sum.</li>
 *   <li>Move the right edge forward, adding each value to {@code sum}.</li>
 *   <li>While {@code sum >= target}, record the window length and shrink from the left, subtracting {@code nums[left]}.</li>
 *   <li>The smallest recorded length is the answer, or {@code 0} if none was recorded.</li>
 * </ol>
 *
 * <p><b>Example walkthrough</b>
 * <ul>
 *   <li>For {@code target = 7}, {@code nums = [2, 3, 1, 2, 4, 3]}: the window {@code [4, 3]} has sum 7 and length 2, and no shorter window reaches 7.</li>
 *   <li>Result: {@code 2}.</li>
 * </ul>
 *
 * <p><b>Complexity Analysis:</b>
 * <ul>
 *   <li><b>Time Complexity:</b> {@code O(n)} — Each index enters and leaves the window once.</li>
 *   <li><b>Space Complexity:</b> {@code O(1)} — Two pointers and a sum.</li>
 * </ul>
 *
 * @author Ranga Reddy
 */
public class MinimumSizeSubarraySum {

    public static void main(String[] args) {
        int[][] nums = {{2, 3, 1, 2, 4, 3}, {1, 4, 4}, {1, 1, 1, 1, 1, 1, 1, 1}, {}};
        int[] targets = {7, 4, 11, 5};
        for (int i = 0; i < nums.length; i++) {
            System.out.println("Input  : target = " + targets[i] + ", nums = " + Arrays.toString(nums[i]));
            System.out.println("Output : " + minSubArrayLen(targets[i], nums[i]));
            System.out.println();
        }
    }

    public static int minSubArrayLen(int target, int[] nums) {
        if (nums == null || nums.length == 0) {
            return 0;
        }
        int best = Integer.MAX_VALUE;
        int left = 0;
        int sum = 0;
        for (int right = 0; right < nums.length; right++) {
            sum += nums[right];
            while (sum >= target) {
                best = Math.min(best, right - left + 1);
                sum -= nums[left++];
            }
        }
        return best == Integer.MAX_VALUE ? 0 : best;
    }
}
