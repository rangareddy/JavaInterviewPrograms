package com.ranga.array;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

/**
 * The degree of an array is the highest frequency of any value in it. Given a non-empty array {@code nums} of non-negative integers, return the length of the smallest contiguous subarray that has the same degree as {@code nums}.
 *
 * <p><b>Algorithm Strategy (Frequency, First and Last Index):</b>
 * <ol>
 *   <li>For each value record its count, the index where it first appears, and the index where it last appears.</li>
 *   <li>The degree is the maximum count.</li>
 *   <li>For every value whose count equals the degree, the shortest subarray covering all of its occurrences is {@code last - first + 1}.</li>
 *   <li>Return the minimum of those lengths.</li>
 * </ol>
 *
 * <p><b>Example walkthrough</b>
 * <ul>
 *   <li>For {@code [1, 2, 2, 3, 1]}: the degree is 2 (values 1 and 2). Value 1 spans 5 positions, value 2 spans 2.</li>
 *   <li>Result: {@code 2}.</li>
 * </ul>
 *
 * <p><b>Complexity Analysis:</b>
 * <ul>
 *   <li><b>Time Complexity:</b> {@code O(n)} — One pass plus one pass over the distinct values.</li>
 *   <li><b>Space Complexity:</b> {@code O(n)} — Three maps.</li>
 * </ul>
 *
 * @author Ranga Reddy
 */
public class DegreeOfAnArray {

    public static void main(String[] args) {
        int[][] inputs = {{1, 2, 2, 3, 1}, {1, 2, 2, 3, 1, 4, 2}, {7}, {1, 1, 1}};
        for (int[] nums : inputs) {
            System.out.println("Input  : nums = " + Arrays.toString(nums));
            System.out.println("Output : " + findShortestSubArray(nums));
            System.out.println();
        }
    }

    public static int findShortestSubArray(int[] nums) {
        if (nums == null || nums.length == 0) {
            return 0;
        }
        Map<Integer, Integer> count = new HashMap<>();
        Map<Integer, Integer> first = new HashMap<>();
        int degree = 0;
        int best = nums.length;
        for (int i = 0; i < nums.length; i++) {
            int x = nums[i];
            first.putIfAbsent(x, i);
            int c = count.merge(x, 1, Integer::sum);
            int length = i - first.get(x) + 1;
            if (c > degree) {
                degree = c;
                best = length;
            } else if (c == degree) {
                best = Math.min(best, length);
            }
        }
        return best;
    }
}
