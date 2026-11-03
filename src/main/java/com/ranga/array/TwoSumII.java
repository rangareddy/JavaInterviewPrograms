package com.ranga.array;

import java.util.Arrays;

/**
 * Given a 1-indexed array of integers {@code numbers} sorted in non-decreasing order, find two numbers that add up to {@code target} and return their 1-based indices {@code [index1, index2]} with {@code index1 < index2}. Exactly one solution exists, and the same element cannot be used twice. Use only constant extra space.
 *
 * <p><b>Algorithm Strategy (Two Pointers from Both Ends):</b>
 * <ol>
 *   <li>Put one pointer at the start ({@code left = 0}) and one at the end ({@code right = n - 1}) of the sorted array.</li>
 *   <li>Compute {@code sum = numbers[left] + numbers[right]}. If it equals the target, return the 1-based indices.</li>
 *   <li>If the sum is too small, the only way to increase it is to move {@code left} to a larger number, so increment {@code left}.</li>
 *   <li>If the sum is too large, move {@code right} to a smaller number, so decrement {@code right}. Each step discards one number that cannot be part of the answer, so the pointers meet at the solution.</li>
 * </ol>
 *
 * <p><b>Example walkthrough</b>
 * <ul>
 *   <li>For {@code [2, 7, 11, 15]} and target 9: {@code 2 + 15 = 17} is too large, so {@code right} moves; {@code 2 + 11 = 13} is too large; {@code 2 + 7 = 9}.</li>
 *   <li>Result: {@code [1, 2]}.</li>
 * </ul>
 *
 * <p><b>Complexity Analysis:</b>
 * <ul>
 *   <li><b>Time Complexity:</b> {@code O(n)} — Each pointer moves at most {@code n} times in total.</li>
 *   <li><b>Space Complexity:</b> {@code O(1)} — Two index variables.</li>
 * </ul>
 *
 * @author Ranga Reddy
 */
public class TwoSumII {

    public static void main(String[] args) {
        int[][] inputs = {{2, 7, 11, 15}, {2, 3, 4}, {-1, 0}, {1, 2, 3, 4, 4, 9, 56, 90}};
        int[] targets = {9, 6, -1, 8};
        for (int i = 0; i < inputs.length; i++) {
            System.out.println("Input  : numbers = " + Arrays.toString(inputs[i]) + ", target = " + targets[i]);
            System.out.println("Output : " + Arrays.toString(twoSum(inputs[i], targets[i])));
            System.out.println();
        }
    }

    public static int[] twoSum(int[] numbers, int target) {
        int left = 0;
        int right = numbers.length - 1;
        while (left < right) {
            int sum = numbers[left] + numbers[right];
            if (sum == target) {
                return new int[]{left + 1, right + 1};
            } else if (sum < target) {
                left++;
            } else {
                right--;
            }
        }
        return new int[]{-1, -1};
    }
}
