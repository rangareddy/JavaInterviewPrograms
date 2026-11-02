package com.ranga.blind75.dp;

import java.util.Arrays;

/**
 * Each element of an array is the maximum jump length from that position. Starting at the first index, return {@code true} if the last index can be reached.
 *
 * <p><b>Algorithm Strategy (Greedy: Track the Farthest Reachable Index):</b>
 * <ol>
 *   <li>Keep {@code farthest}, the largest index reachable using the positions seen so far (initially 0).</li>
 *   <li>For each index {@code i}: if {@code i > farthest}, this position cannot be reached at all, so the last index is unreachable: return {@code false}.</li>
 *   <li>Otherwise update {@code farthest = max(farthest, i + nums[i])}.</li>
 *   <li>If {@code farthest} ever reaches the last index, return {@code true}. No table is needed because reaching a position means every earlier position is reachable too.</li>
 * </ol>
 *
 * <p><b>Example walkthrough</b>
 * <ul>
 *   <li>For {@code [2, 3, 1, 1, 4]}: farthest becomes 2, 4 (at index 1) and the last index 4 is reached: {@code true}.</li>
 *   <li>For {@code [3, 2, 1, 0, 4]}: farthest is stuck at 3 and index 4 is never reachable: {@code false}.</li>
 * </ul>
 *
 * <p><b>Complexity Analysis:</b>
 * <ul>
 *   <li><b>Time Complexity:</b> {@code O(n)} — A single pass.</li>
 *   <li><b>Space Complexity:</b> {@code O(1)} — One variable.</li>
 * </ul>
 *
 * @author Ranga Reddy
 */
public class JumpGame {

    public static void main(String[] args) {
        int[][] inputs = {{2, 3, 1, 1, 4}, {3, 2, 1, 0, 4}, {0}, {2, 0, 0}};
        for (int[] nums : inputs) {
            System.out.println("Input  : nums = " + Arrays.toString(nums));
            System.out.println("Output : " + canJump(nums));
            System.out.println();
        }
    }

    public static boolean canJump(int[] nums) {
        if (nums == null || nums.length == 0) {
            return false;
        }

        int farthest = 0;
        for (int i = 0; i < nums.length; i++) {
            if (i > farthest) {
                return false;       // position i cannot be reached
            }
            farthest = Math.max(farthest, i + nums[i]);
            if (farthest >= nums.length - 1) {
                return true;
            }
        }
        return true;
    }
}
