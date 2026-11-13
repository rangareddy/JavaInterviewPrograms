package com.ranga.stack;

import java.util.ArrayDeque;
import java.util.Arrays;
import java.util.Deque;

/**
 * Given a circular integer array {@code nums} (the next element of the last element is the first element), return for each element its next greater element, searching circularly, or {@code -1} if no greater element exists. Duplicates are allowed.
 *
 * <p><b>Algorithm Strategy (Monotonic Stack over Two Laps):</b>
 * <ol>
 *   <li>A circular array can be handled by walking the indices {@code 0 .. 2n - 1} and reading the element at {@code i % n}, which lets every element see the elements before it as well.</li>
 *   <li>Keep a stack of indices whose next greater element is not known yet (their values decrease from bottom to top).</li>
 *   <li>For every position {@code i}, while the value at the top index is smaller than {@code nums[i % n]}, pop it and set its answer to {@code nums[i % n]}.</li>
 *   <li>Push an index only during the first lap ({@code i < n}); the second lap exists only to resolve indices that were left on the stack. Indices still on the stack at the end keep the default {@code -1}.</li>
 * </ol>
 *
 * <p><b>Example walkthrough</b>
 * <ul>
 *   <li>For {@code [1, 2, 1]}: during the first lap index 0 is resolved by {@code 2}, and indices 1 and 2 stay on the stack.</li>
 *   <li>In the second lap the value 1 at {@code i = 3} does not resolve index 2 (not greater), but the value 2 at {@code i = 4} does. Nothing is greater than the 2 at index 1. Result: {@code [2, -1, 2]}.</li>
 * </ul>
 *
 * <p><b>Complexity Analysis:</b>
 * <ul>
 *   <li><b>Time Complexity:</b> {@code O(n)} — Each index is pushed once and popped once across the two laps.</li>
 *   <li><b>Space Complexity:</b> {@code O(n)} — The stack and the result.</li>
 * </ul>
 *
 * @author Ranga Reddy
 */
public class NextGreaterElementII {

    public static void main(String[] args) {
        int[][] inputs = {{1, 2, 1}, {1, 2, 3, 4, 3}, {5, 4, 3, 2, 1}, {2, 2, 2}};
        for (int[] nums : inputs) {
            System.out.println("Input  : nums = " + Arrays.toString(nums));
            System.out.println("Output : " + Arrays.toString(nextGreaterElements(nums)));
            System.out.println();
        }
    }

    public static int[] nextGreaterElements(int[] nums) {
        int n = nums.length;
        int[] result = new int[n];
        Arrays.fill(result, -1);
        Deque<Integer> stack = new ArrayDeque<>();
        for (int i = 0; i < 2 * n; i++) {
            int value = nums[i % n];
            while (!stack.isEmpty() && nums[stack.peek()] < value) {
                result[stack.pop()] = value;
            }
            if (i < n) {
                stack.push(i);
            }
        }
        return result;
    }
}
