package com.ranga.array;

import java.util.Arrays;

/**
 * Given an unsorted integer array {@code nums}, return the smallest positive integer that is not present in it. The algorithm must run in {@code O(n)} time and use {@code O(1)} auxiliary space.
 *
 * <p><b>Algorithm Strategy (Cyclic Placement (Index Placement)):</b>
 * <ol>
 *   <li>The answer is always in {@code [1, n + 1]}, because {@code n} numbers can cover at most {@code 1..n}.</li>
 *   <li>Try to place every value {@code v} in {@code [1, n]} at index {@code v - 1} by swapping it with the element that is currently there. Keep swapping at the same position until it holds a value that is out of range or is already in its place (this also handles duplicates).</li>
 *   <li>Each swap puts at least one value in its final slot, so the total number of swaps is at most {@code n}.</li>
 *   <li>Scan again: the first index {@code i} with {@code nums[i] != i + 1} means {@code i + 1} is missing. If every slot is right, the answer is {@code n + 1}.</li>
 * </ol>
 *
 * <p><b>Example walkthrough</b>
 * <ul>
 *   <li>For {@code [3, 4, -1, 1]}: swaps give {@code [1, -1, 3, 4]}.</li>
 *   <li>Index 1 holds {@code -1} instead of {@code 2}, so the answer is {@code 2}.</li>
 * </ul>
 *
 * <p><b>Complexity Analysis:</b>
 * <ul>
 *   <li><b>Time Complexity:</b> {@code O(n)} — Each element is swapped into place at most once, then one scan.</li>
 *   <li><b>Space Complexity:</b> {@code O(1)} — The array is rearranged in place.</li>
 * </ul>
 *
 * @author Ranga Reddy
 */
public class FirstMissingPositive {

    public static void main(String[] args) {
        int[][] inputs = {{1, 2, 0}, {3, 4, -1, 1}, {7, 8, 9, 11, 12}, {1, 1}, {2, 1}};
        for (int[] nums : inputs) {
            System.out.println("Input  : nums = " + Arrays.toString(nums));
            System.out.println("Output : " + firstMissingPositive(nums.clone()));
            System.out.println();
        }
    }

    public static int firstMissingPositive(int[] nums) {
        int n = nums.length;
        for (int i = 0; i < n; i++) {
            while (nums[i] > 0 && nums[i] <= n && nums[nums[i] - 1] != nums[i]) {
                int target = nums[i] - 1;
                int temp = nums[target];
                nums[target] = nums[i];
                nums[i] = temp;
            }
        }
        for (int i = 0; i < n; i++) {
            if (nums[i] != i + 1) {
                return i + 1;
            }
        }
        return n + 1;
    }
}
