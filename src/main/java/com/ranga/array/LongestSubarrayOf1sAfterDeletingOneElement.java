package com.ranga.array;

import java.util.Arrays;

/**
 * Given a binary array {@code nums}, you should delete exactly one element from it. Return the size of the longest non-empty subarray containing only {@code 1}s in the resulting array, or {@code 0} if there is none.
 *
 * <p><b>Algorithm Strategy (Window with at Most One Zero):</b>
 * <ol>
 *   <li>Slide a window {@code [left, right]} that contains at most one zero.</li>
 *   <li>Extend {@code right}; when the window holds two zeros, move {@code left} forward until only one remains.</li>
 *   <li>The window length minus one (the element that must be deleted) is a candidate answer.</li>
 *   <li>Return the best candidate; an array of all ones still loses one element.</li>
 * </ol>
 *
 * <p><b>Example walkthrough</b>
 * <ul>
 *   <li>For {@code [1, 1, 0, 1]} the whole array is a window with one zero: length 4, minus the deleted zero gives 3.</li>
 *   <li>Result: {@code 3}.</li>
 * </ul>
 *
 * <p><b>Complexity Analysis:</b>
 * <ul>
 *   <li><b>Time Complexity:</b> {@code O(n)} — Each index enters and leaves the window once.</li>
 *   <li><b>Space Complexity:</b> {@code O(1)} — Two indexes and a zero counter.</li>
 * </ul>
 *
 * @author Ranga Reddy
 */
public class LongestSubarrayOf1sAfterDeletingOneElement {

    public static void main(String[] args) {
        int[][] inputs = {{1, 1, 0, 1}, {0, 1, 1, 1, 0, 1, 1, 0, 1}, {1, 1, 1}, {0, 0}, {}};
        for (int[] nums : inputs) {
            System.out.println("Input  : nums = " + Arrays.toString(nums));
            System.out.println("Output : " + longestSubarray(nums));
            System.out.println();
        }
    }

    public static int longestSubarray(int[] nums) {
        if (nums == null || nums.length == 0) {
            return 0;
        }
        int left = 0;
        int zeros = 0;
        int best = 0;
        for (int right = 0; right < nums.length; right++) {
            if (nums[right] == 0) {
                zeros++;
            }
            while (zeros > 1) {
                if (nums[left++] == 0) {
                    zeros--;
                }
            }
            best = Math.max(best, right - left);
        }
        return best;
    }
}
