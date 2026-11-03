package com.ranga.greedy;

import java.util.Arrays;

/**
 * You are given a 0-indexed array {@code nums} where {@code nums[i]} is the maximum jump length from index {@code i}. Starting at index 0, return the minimum number of jumps needed to reach the last index. The last index is always reachable.
 *
 * <p><b>Algorithm Strategy (Greedy BFS by Reachable Window):</b>
 * <ol>
 *   <li>Treat each jump as one BFS level: the indices reachable with {@code jumps} jumps form a window {@code [start, end]}.</li>
 *   <li>While scanning the window, track {@code farthest}, the furthest index reachable from any index in it.</li>
 *   <li>When the scan reaches {@code end}, the current level is finished: take one more jump and set {@code end = farthest}.</li>
 *   <li>Stop as soon as {@code end >= n - 1}; the number of jumps taken is the answer.</li>
 * </ol>
 *
 * <p><b>Example walkthrough</b>
 * <ul>
 *   <li>For {@code [2, 3, 1, 1, 4]}: window {@code [0, 0]} reaches index 2, jump 1; window {@code [1, 2]} reaches index 4, jump 2.</li>
 *   <li>Result: {@code 2}.</li>
 * </ul>
 *
 * <p><b>Complexity Analysis:</b>
 * <ul>
 *   <li><b>Time Complexity:</b> {@code O(n)} — Each index is visited once.</li>
 *   <li><b>Space Complexity:</b> {@code O(1)} — Three variables.</li>
 * </ul>
 *
 * @author Ranga Reddy
 */
public class JumpGameII {

    public static void main(String[] args) {
        int[][] inputs = {{2, 3, 1, 1, 4}, {2, 3, 0, 1, 4}, {0}, {1, 1, 1, 1}, {5, 1, 1, 1, 1, 1}};
        for (int[] nums : inputs) {
            System.out.println("Input  : nums = " + Arrays.toString(nums));
            System.out.println("Output : " + jump(nums));
            System.out.println();
        }
    }

    public static int jump(int[] nums) {
        int jumps = 0;
        int end = 0;            // last index reachable with the current number of jumps
        int farthest = 0;       // furthest index reachable with one more jump
        for (int i = 0; i < nums.length - 1; i++) {
            farthest = Math.max(farthest, i + nums[i]);
            if (i == end) {
                jumps++;
                end = farthest;
            }
        }
        return jumps;
    }
}
