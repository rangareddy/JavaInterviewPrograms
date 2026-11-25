package com.ranga.array;

import java.util.Arrays;

/**
 * Given an integer array {@code nums}, return the third distinct maximum number in the array. If the third maximum does not exist, return the maximum number.
 *
 * <p><b>Algorithm Strategy (Three Variables for Distinct Maximums):</b>
 * <ol>
 *   <li>Keep {@code first}, {@code second} and {@code third} as {@code Long} values (or {@code null}) so that {@code Integer.MIN_VALUE} in the input is not confused with 'not set'.</li>
 *   <li>For each number, skip it if it equals one of the three tracked values.</li>
 *   <li>Insert it into the right slot and shift the lower ones down.</li>
 *   <li>At the end, return {@code third} if it was set, otherwise {@code first}.</li>
 * </ol>
 *
 * <p><b>Example walkthrough</b>
 * <ul>
 *   <li>For {@code [2, 2, 3, 1]} the distinct values in order are 3, 2, 1, so the third maximum is 1.</li>
 *   <li>Result: {@code 1}. For {@code [1, 2]} there is no third value, so the answer is the maximum {@code 2}.</li>
 * </ul>
 *
 * <p><b>Complexity Analysis:</b>
 * <ul>
 *   <li><b>Time Complexity:</b> {@code O(n)} — One pass.</li>
 *   <li><b>Space Complexity:</b> {@code O(1)} — Three variables.</li>
 * </ul>
 *
 * @author Ranga Reddy
 */
public class ThirdMaximumNumber {

    public static void main(String[] args) {
        int[][] inputs = {{2, 2, 3, 1}, {3, 2, 1}, {1, 2}, {1, 1, 2}, {Integer.MIN_VALUE, 1, 2}};
        for (int[] nums : inputs) {
            System.out.println("Input  : nums = " + Arrays.toString(nums));
            System.out.println("Output : " + thirdMax(nums));
            System.out.println();
        }
    }

    public static int thirdMax(int[] nums) {
        if (nums == null || nums.length == 0) {
            return 0;
        }
        Long first = null;
        Long second = null;
        Long third = null;
        for (int value : nums) {
            long num = value;
            if ((first != null && num == first) || (second != null && num == second) || (third != null && num == third)) {
                continue;
            }
            if (first == null || num > first) {
                third = second;
                second = first;
                first = num;
            } else if (second == null || num > second) {
                third = second;
                second = num;
            } else if (third == null || num > third) {
                third = num;
            }
        }
        return (int) (long) (third != null ? third : first);
    }
}
