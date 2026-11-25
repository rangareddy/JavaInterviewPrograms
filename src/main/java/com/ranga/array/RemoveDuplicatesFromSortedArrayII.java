package com.ranga.array;

import java.util.Arrays;

/**
 * Given a sorted array {@code nums}, remove duplicates in place so that each value appears at most twice, keeping the relative order. Return the new length {@code k}; the first {@code k} elements must hold the result.
 *
 * <p><b>Algorithm Strategy (Compare with the Element Two Behind the Write Index):</b>
 * <ol>
 *   <li>Keep a write index {@code k}. The first two elements are always kept.</li>
 *   <li>For every later element {@code x}, keep it only if {@code x != nums[k - 2]}. Because the array is sorted, this means fewer than two copies of {@code x} have been kept so far.</li>
 *   <li>When kept, write it at {@code nums[k]} and increase {@code k}.</li>
 *   <li>Return {@code k}.</li>
 * </ol>
 *
 * <p><b>Example walkthrough</b>
 * <ul>
 *   <li>For {@code [1, 1, 1, 2, 2, 3]}: keep 1, 1; the third 1 equals {@code nums[0]} so it is skipped; 2 and 2 are kept; 3 is kept.</li>
 *   <li>Result: {@code 5}, array starts {@code [1, 1, 2, 2, 3]}.</li>
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
public class RemoveDuplicatesFromSortedArrayII {

    public static void main(String[] args) {
        int[][] inputs = {{1, 1, 1, 2, 2, 3}, {0, 0, 1, 1, 1, 1, 2, 3, 3}, {1}, {}};
        for (int[] nums : inputs) {
            System.out.println("Input  : nums = " + Arrays.toString(nums));
            int k = removeDuplicates(nums);
            System.out.println("Output : " + k + ", " + Arrays.toString(Arrays.copyOf(nums, k)));
            System.out.println();
        }
    }

    public static int removeDuplicates(int[] nums) {
        if (nums == null) {
            return 0;
        }
        if (nums.length <= 2) {
            return nums.length;
        }
        int k = 2;
        for (int i = 2; i < nums.length; i++) {
            if (nums[i] != nums[k - 2]) {
                nums[k++] = nums[i];
            }
        }
        return k;
    }
}
