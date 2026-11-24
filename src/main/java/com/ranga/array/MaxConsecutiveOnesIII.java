package com.ranga.array;

import java.util.Arrays;

/**
 * Given a binary array {@code nums} and an integer {@code k}, return the maximum number of consecutive {@code 1}s in the array if you can flip at most {@code k} zeros.
 *
 * <p><b>Algorithm Strategy (Sliding Window with a Zero Count):</b>
 * <ol>
 *   <li>Maintain a window {@code [left, right]} that contains at most {@code k} zeros.</li>
 *   <li>Extend {@code right}; if {@code nums[right]} is 0, increase the zero count.</li>
 *   <li>While the zero count exceeds {@code k}, move {@code left} forward, decreasing the count when a 0 leaves the window.</li>
 *   <li>The window length after each step is a valid answer candidate; keep the maximum.</li>
 * </ol>
 *
 * <p><b>Example walkthrough</b>
 * <ul>
 *   <li>For {@code [1,1,1,0,0,0,1,1,1,1,0]} and {@code k = 2}: the window from index 5 to 10 holds 6 values with two zeros.</li>
 *   <li>Result: {@code 6}.</li>
 * </ul>
 *
 * <p><b>Complexity Analysis:</b>
 * <ul>
 *   <li><b>Time Complexity:</b> {@code O(n)} — Each index enters and leaves the window once.</li>
 *   <li><b>Space Complexity:</b> {@code O(1)} — Two pointers and a counter.</li>
 * </ul>
 *
 * @author Ranga Reddy
 */
public class MaxConsecutiveOnesIII {

    public static void main(String[] args) {
        int[][] nums = {{1, 1, 1, 0, 0, 0, 1, 1, 1, 1, 0}, {0, 0, 1, 1, 0, 0, 1, 1, 1, 0, 1, 1, 0, 0, 0, 1, 1, 1, 1}, {0, 0, 0}, {1, 1}};
        int[] ks = {2, 3, 0, 0};
        for (int i = 0; i < nums.length; i++) {
            System.out.println("Input  : nums = " + Arrays.toString(nums[i]) + ", k = " + ks[i]);
            System.out.println("Output : " + longestOnes(nums[i], ks[i]));
            System.out.println();
        }
    }

    public static int longestOnes(int[] nums, int k) {
        if (nums == null || nums.length == 0) {
            return 0;
        }
        int best = 0;
        int left = 0;
        int zeros = 0;
        for (int right = 0; right < nums.length; right++) {
            if (nums[right] == 0) {
                zeros++;
            }
            while (zeros > k) {
                if (nums[left++] == 0) {
                    zeros--;
                }
            }
            best = Math.max(best, right - left + 1);
        }
        return best;
    }
}
