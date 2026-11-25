package com.ranga.array;

import java.util.Arrays;

/**
 * Given an integer array {@code nums}, return the number of triplets chosen from the array that can make the sides of a triangle (three lengths {@code a <= b <= c} form a triangle when {@code a + b > c}).
 *
 * <p><b>Algorithm Strategy (Sort, Fix the Longest Side, Two Pointers):</b>
 * <ol>
 *   <li>Sort the array.</li>
 *   <li>Fix the largest side {@code nums[k]} for {@code k} from the end down to index 2.</li>
 *   <li>Use two pointers {@code i = 0} and {@code j = k - 1}: if {@code nums[i] + nums[j] > nums[k]}, then every index from {@code i} to {@code j - 1} works with {@code j}, so add {@code j - i} and decrease {@code j}; otherwise increase {@code i}.</li>
 *   <li>Sum the counts.</li>
 * </ol>
 *
 * <p><b>Example walkthrough</b>
 * <ul>
 *   <li>For {@code [2, 2, 3, 4]} sorted: with {@code c = 4}, {@code (2, 3)} and {@code (2, 3)} using the two 2s work (2 + 3 > 4), and {@code (2, 2)} does not; with {@code c = 3}, {@code (2, 2)} works.</li>
 *   <li>Result: {@code 3}.</li>
 * </ul>
 *
 * <p><b>Complexity Analysis:</b>
 * <ul>
 *   <li><b>Time Complexity:</b> {@code O(n^2)} — Sorting plus one two-pointer pass per longest side.</li>
 *   <li><b>Space Complexity:</b> {@code O(1)} — Apart from the sort.</li>
 * </ul>
 *
 * @author Ranga Reddy
 */
public class ValidTriangleNumber {

    public static void main(String[] args) {
        int[][] inputs = {{2, 2, 3, 4}, {4, 2, 3, 4}, {1, 1, 1}, {1, 2, 3}, {}};
        for (int[] nums : inputs) {
            System.out.println("Input  : nums = " + Arrays.toString(nums));
            System.out.println("Output : " + triangleNumber(nums.clone()));
            System.out.println();
        }
    }

    public static int triangleNumber(int[] nums) {
        if (nums == null || nums.length < 3) {
            return 0;
        }
        Arrays.sort(nums);
        int count = 0;
        for (int k = nums.length - 1; k >= 2; k--) {
            int i = 0;
            int j = k - 1;
            while (i < j) {
                if (nums[i] + nums[j] > nums[k]) {
                    count += j - i;
                    j--;
                } else {
                    i++;
                }
            }
        }
        return count;
    }
}
