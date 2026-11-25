package com.ranga.stack;

import java.util.ArrayDeque;
import java.util.Arrays;
import java.util.Deque;

/**
 * Given an array of integers {@code arr}, find the sum of {@code min(b)} over every contiguous subarray {@code b} of {@code arr}. Because the answer may be large, return it modulo {@code 10^9 + 7}.
 *
 * <p><b>Algorithm Strategy (Contribution of Each Element as the Minimum):</b>
 * <ol>
 *   <li>Each element {@code arr[i]} is the minimum of a number of subarrays. Count them as {@code left * right}.</li>
 *   <li>{@code left} is the number of positions to the left until a strictly smaller element (so the subarray can start there) and {@code right} is the number to the right until a smaller-or-equal element, which avoids counting ties twice.</li>
 *   <li>Find both distances with one monotonic stack pass each.</li>
 *   <li>The answer is {@code sum(arr[i] * left * right) mod (10^9 + 7)}.</li>
 * </ol>
 *
 * <p><b>Example walkthrough</b>
 * <ul>
 *   <li>For {@code [3, 1, 2, 4]}: the element 1 is the minimum of 6 subarrays, 3 of 1 subarray, 2 of 2 subarrays, and 4 of 1.</li>
 *   <li>Total: {@code 3 + 6 + 4 + 4 = 17}.</li>
 * </ul>
 *
 * <p><b>Complexity Analysis:</b>
 * <ul>
 *   <li><b>Time Complexity:</b> {@code O(n)} — Each element is pushed and popped once.</li>
 *   <li><b>Space Complexity:</b> {@code O(n)} — The stack and the distances.</li>
 * </ul>
 *
 * @author Ranga Reddy
 */
public class SumOfSubarrayMinimums {

    public static void main(String[] args) {
        int[][] inputs = {{3, 1, 2, 4}, {11, 81, 94, 43, 3}, {1}, {2, 2, 2}};
        for (int[] arr : inputs) {
            System.out.println("Input  : arr = " + Arrays.toString(arr));
            System.out.println("Output : " + sumSubarrayMins(arr));
            System.out.println();
        }
    }

    private static final int MOD = 1_000_000_007;

    public static int sumSubarrayMins(int[] arr) {
        if (arr == null || arr.length == 0) {
            return 0;
        }
        int n = arr.length;
        int[] left = new int[n];
        int[] right = new int[n];
        Deque<Integer> stack = new ArrayDeque<>();
        for (int i = 0; i < n; i++) {
            while (!stack.isEmpty() && arr[stack.peek()] > arr[i]) {
                stack.pop();
            }
            left[i] = stack.isEmpty() ? i + 1 : i - stack.peek();
            stack.push(i);
        }
        stack.clear();
        for (int i = n - 1; i >= 0; i--) {
            while (!stack.isEmpty() && arr[stack.peek()] >= arr[i]) {
                stack.pop();
            }
            right[i] = stack.isEmpty() ? n - i : stack.peek() - i;
            stack.push(i);
        }
        long sum = 0;
        for (int i = 0; i < n; i++) {
            sum = (sum + (long) arr[i] * left[i] * right[i]) % MOD;
        }
        return (int) sum;
    }
}
