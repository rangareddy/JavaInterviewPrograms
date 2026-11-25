package com.ranga.array;

import java.util.Arrays;

/**
 * Given an integer array {@code nums} of size {@code n}, return the minimum number of moves required to make all elements equal. In one move you can increment {@code n - 1} elements of the array by {@code 1}.
 *
 * <p><b>Algorithm Strategy (Incrementing n-1 Elements Equals Decrementing One):</b>
 * <ol>
 *   <li>Raising every element except one by 1 changes the differences between elements exactly as lowering that one element by 1 does.</li>
 *   <li>So the task becomes: lower elements one at a time until all equal the minimum.</li>
 *   <li>Each element {@code x} needs {@code x - min} such moves.</li>
 *   <li>The answer is {@code sum(nums) - n * min}.</li>
 * </ol>
 *
 * <p><b>Example walkthrough</b>
 * <ul>
 *   <li>For {@code [1, 2, 3]}: the minimum is 1, so the moves are {@code (1 - 1) + (2 - 1) + (3 - 1) = 3}.</li>
 *   <li>Result: {@code 3}.</li>
 * </ul>
 *
 * <p><b>Complexity Analysis:</b>
 * <ul>
 *   <li><b>Time Complexity:</b> {@code O(n)} — One pass for the sum and the minimum.</li>
 *   <li><b>Space Complexity:</b> {@code O(1)} — Two numbers.</li>
 * </ul>
 *
 * @author Ranga Reddy
 */
public class MinimumMovesToEqualArrayElements {

    public static void main(String[] args) {
        int[][] inputs = {{1, 2, 3}, {1, 1, 1}, {5}, {1, 1000000000}, {}};
        for (int[] nums : inputs) {
            System.out.println("Input  : nums = " + Arrays.toString(nums));
            System.out.println("Output : " + minMoves(nums));
            System.out.println();
        }
    }

    public static int minMoves(int[] nums) {
        if (nums == null || nums.length == 0) {
            return 0;
        }
        long min = Long.MAX_VALUE;
        long sum = 0;
        for (int x : nums) {
            min = Math.min(min, x);
            sum += x;
        }
        return (int) (sum - nums.length * min);
    }
}
