package com.ranga.array;

import java.util.Arrays;

/**
 * Given an integer array {@code nums}, move all {@code 0}s to the end while keeping the relative order of the non-zero elements. Do it in place, without making a copy of the array.
 *
 * <p><b>Algorithm Strategy (Fast and Slow Pointers (Stable Partition)):</b>
 * <ol>
 *   <li>Keep a write pointer {@code slow} that marks where the next non-zero value belongs; everything before it is already the compacted non-zero prefix.</li>
 *   <li>Scan with a read pointer {@code fast}. When {@code nums[fast]} is non-zero, swap it with {@code nums[slow]} and advance {@code slow}.</li>
 *   <li>Swapping (instead of overwriting and then zero-filling) keeps the algorithm a single pass and makes zero extra writes when the array has no zeros in front.</li>
 *   <li>The relative order of the non-zero values is preserved because they are moved forward in the order they are read.</li>
 * </ol>
 *
 * <p><b>Example walkthrough</b>
 * <ul>
 *   <li>For {@code [0, 1, 0, 3, 12]}: {@code fast = 1} swaps 1 into index 0, {@code fast = 3} swaps 3 into index 1, {@code fast = 4} swaps 12 into index 2.</li>
 *   <li>Result: {@code [1, 3, 12, 0, 0]}.</li>
 * </ul>
 *
 * <p><b>Complexity Analysis:</b>
 * <ul>
 *   <li><b>Time Complexity:</b> {@code O(n)} — A single pass.</li>
 *   <li><b>Space Complexity:</b> {@code O(1)} — In-place swaps only.</li>
 * </ul>
 *
 * @author Ranga Reddy
 */
public class MoveZeroes {

    public static void main(String[] args) {
        int[][] inputs = {{0, 1, 0, 3, 12}, {0}, {1, 2, 3}, {0, 0, 1}};
        for (int[] nums : inputs) {
            System.out.println("Input  : nums = " + Arrays.toString(nums));
            moveZeroes(nums);
            System.out.println("Output : " + Arrays.toString(nums));
            System.out.println();
        }
    }

    public static void moveZeroes(int[] nums) {
        int slow = 0;
        for (int fast = 0; fast < nums.length; fast++) {
            if (nums[fast] != 0) {
                int temp = nums[slow];
                nums[slow] = nums[fast];
                nums[fast] = temp;
                slow++;
            }
        }
    }
}
