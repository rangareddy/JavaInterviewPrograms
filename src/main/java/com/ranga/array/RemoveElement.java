package com.ranga.array;

import java.util.Arrays;

/**
 * Given an integer array {@code nums} and a value {@code val}, remove all occurrences of {@code val} in place and return the number {@code k} of elements that are not equal to {@code val}. The first {@code k} elements of {@code nums} must hold those values (in any order).
 *
 * <p><b>Algorithm Strategy (Write Pointer):</b>
 * <ol>
 *   <li>Keep {@code write = 0}: the position where the next kept value goes.</li>
 *   <li>Scan every element; if it is not {@code val}, copy it to {@code nums[write]} and advance {@code write}.</li>
 *   <li>Elements equal to {@code val} are simply skipped.</li>
 *   <li>Return {@code write}.</li>
 * </ol>
 *
 * <p><b>Example walkthrough</b>
 * <ul>
 *   <li>For {@code [3, 2, 2, 3]} and {@code val = 3}: the two 2s are copied to the front.</li>
 *   <li>Result: {@code 2}, with the array starting {@code [2, 2, ...]}.</li>
 * </ul>
 *
 * <p><b>Complexity Analysis:</b>
 * <ul>
 *   <li><b>Time Complexity:</b> {@code O(n)} — One pass.</li>
 *   <li><b>Space Complexity:</b> {@code O(1)} — In place.</li>
 * </ul>
 *
 * @author Ranga Reddy
 */
public class RemoveElement {

    public static void main(String[] args) {
        int[][] nums = {{3, 2, 2, 3}, {0, 1, 2, 2, 3, 0, 4, 2}, {1, 1}, {}};
        int[] vals = {3, 2, 1, 5};
        for (int i = 0; i < nums.length; i++) {
            System.out.println("Input  : nums = " + Arrays.toString(nums[i]) + ", val = " + vals[i]);
            int k = removeElement(nums[i], vals[i]);
            System.out.println("Output : " + k + ", nums = " + Arrays.toString(Arrays.copyOf(nums[i], k)));
            System.out.println();
        }
    }

    public static int removeElement(int[] nums, int val) {
        if (nums == null) {
            return 0;
        }
        int write = 0;
        for (int x : nums) {
            if (x != val) {
                nums[write++] = x;
            }
        }
        return write;
    }
}
