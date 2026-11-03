package com.ranga.array;

import java.util.Arrays;

/**
 * Rearrange the numbers of {@code nums} into the next lexicographically greater permutation. If no such permutation exists (the array is in descending order), rearrange it into the lowest possible order (ascending). The change must be done in place with constant extra memory.
 *
 * <p><b>Algorithm Strategy (Find the Pivot, Swap, Reverse the Suffix):</b>
 * <ol>
 *   <li>Scan from the right to find the first index {@code i} with {@code nums[i] < nums[i + 1]} (the pivot). The part after it is in descending order and cannot be made bigger by itself.</li>
 *   <li>If no pivot exists, the whole array is the last permutation: reverse it to get the first one.</li>
 *   <li>Otherwise find the rightmost element {@code nums[j]} that is greater than the pivot and swap the two.</li>
 *   <li>Reverse the suffix after position {@code i} so it becomes the smallest possible (ascending) arrangement.</li>
 * </ol>
 *
 * <p><b>Example walkthrough</b>
 * <ul>
 *   <li>For {@code [1, 3, 2]}: pivot is 1 (index 0), the rightmost larger element is 2; after the swap {@code [2, 3, 1]} and reversing the suffix gives {@code [2, 1, 3]}.</li>
 *   <li>For {@code [3, 2, 1]} there is no pivot, so the array is reversed to {@code [1, 2, 3]}.</li>
 * </ul>
 *
 * <p><b>Complexity Analysis:</b>
 * <ul>
 *   <li><b>Time Complexity:</b> {@code O(n)} — At most three linear scans.</li>
 *   <li><b>Space Complexity:</b> {@code O(1)} — In place.</li>
 * </ul>
 *
 * @author Ranga Reddy
 */
public class NextPermutation {

    public static void main(String[] args) {
        int[][] inputs = {{1, 2, 3}, {3, 2, 1}, {1, 1, 5}, {1, 3, 2}, {2, 3, 1}, {5}};
        for (int[] nums : inputs) {
            System.out.println("Input  : nums = " + Arrays.toString(nums));
            nextPermutation(nums);
            System.out.println("Output : " + Arrays.toString(nums));
            System.out.println();
        }
    }

    public static void nextPermutation(int[] nums) {
        int i = nums.length - 2;
        while (i >= 0 && nums[i] >= nums[i + 1]) {
            i--;
        }

        if (i >= 0) {
            int j = nums.length - 1;
            while (nums[j] <= nums[i]) {
                j--;
            }
            swap(nums, i, j);
        }
        reverse(nums, i + 1, nums.length - 1);
    }

    private static void reverse(int[] nums, int left, int right) {
        while (left < right) {
            swap(nums, left++, right--);
        }
    }

    private static void swap(int[] nums, int i, int j) {
        int temp = nums[i];
        nums[i] = nums[j];
        nums[j] = temp;
    }
}
