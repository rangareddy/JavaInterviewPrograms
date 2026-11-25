package com.ranga.sort;

import java.util.Arrays;

/**
 * Given an array {@code nums} with {@code n} objects coloured red, white or blue (represented by 0, 1 and 2), sort them in place so that objects of the same colour are adjacent, in the order red, white, blue. Do it without using the library sort function.
 *
 * <p><b>Algorithm Strategy (Dutch National Flag in One Pass):</b>
 * <ol>
 *   <li>Keep three regions with pointers {@code low}, {@code mid} and {@code high}: everything before {@code low} is 0, everything from {@code high + 1} on is 2, and {@code mid} scans the unknown middle.</li>
 *   <li>If {@code nums[mid]} is 0, swap it with {@code nums[low]} and advance both {@code low} and {@code mid}.</li>
 *   <li>If it is 1, just advance {@code mid}.</li>
 *   <li>If it is 2, swap it with {@code nums[high]} and shrink {@code high} only, because the swapped-in value has not been looked at yet.</li>
 * </ol>
 *
 * <p><b>Example walkthrough</b>
 * <ul>
 *   <li>For {@code [2, 0, 2, 1, 1, 0]} the pointers sweep once: 2s are pushed to the right end and 0s to the left end.</li>
 *   <li>Result: {@code [0, 0, 1, 1, 2, 2]}.</li>
 * </ul>
 *
 * <p><b>Complexity Analysis:</b>
 * <ul>
 *   <li><b>Time Complexity:</b> {@code O(n)} — A single pass.</li>
 *   <li><b>Space Complexity:</b> {@code O(1)} — Three indexes.</li>
 * </ul>
 *
 * @author Ranga Reddy
 */
public class SortColors {

    public static void main(String[] args) {
        int[][] inputs = {{2, 0, 2, 1, 1, 0}, {2, 0, 1}, {0}, {1, 1, 1}};
        for (int[] nums : inputs) {
            System.out.println("Input  : nums = " + Arrays.toString(nums));
            sortColors(nums);
            System.out.println("Output : " + Arrays.toString(nums));
            System.out.println();
        }
    }

    public static void sortColors(int[] nums) {
        if (nums == null) {
            return;
        }
        int low = 0;
        int mid = 0;
        int high = nums.length - 1;
        while (mid <= high) {
            if (nums[mid] == 0) {
                swap(nums, low++, mid++);
            } else if (nums[mid] == 1) {
                mid++;
            } else {
                swap(nums, mid, high--);
            }
        }
    }

    private static void swap(int[] nums, int i, int j) {
        int temp = nums[i];
        nums[i] = nums[j];
        nums[j] = temp;
    }
}
