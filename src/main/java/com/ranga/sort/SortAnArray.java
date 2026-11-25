package com.ranga.sort;

import java.util.Arrays;

/**
 * Given an array of integers {@code nums}, sort it in ascending order and return it without using any built-in sorting function, in {@code O(n log n)} time.
 *
 * <p><b>Algorithm Strategy (Top-Down Merge Sort):</b>
 * <ol>
 *   <li>Split the array into two halves at the midpoint.</li>
 *   <li>Sort each half recursively; a range with one element is already sorted.</li>
 *   <li>Merge the two sorted halves into a temporary array by repeatedly taking the smaller front element (take from the left on ties to keep the sort stable).</li>
 *   <li>Copy the merged range back into the original array.</li>
 * </ol>
 *
 * <p><b>Example walkthrough</b>
 * <ul>
 *   <li>For {@code [5, 2, 3, 1]}: split into {@code [5, 2]} and {@code [3, 1]}, sort them to {@code [2, 5]} and {@code [1, 3]}, and merge to {@code [1, 2, 3, 5]}.</li>
 *   <li>Result: {@code [1, 2, 3, 5]}.</li>
 * </ul>
 *
 * <p><b>Complexity Analysis:</b>
 * <ul>
 *   <li><b>Time Complexity:</b> {@code O(n log n)} — {@code log n} levels of splitting, each merge pass is linear.</li>
 *   <li><b>Space Complexity:</b> {@code O(n)} — The temporary array used by merging.</li>
 * </ul>
 *
 * @author Ranga Reddy
 */
public class SortAnArray {

    public static void main(String[] args) {
        int[][] inputs = {{5, 2, 3, 1}, {5, 1, 1, 2, 0, 0}, {1}, {}, {3, -1, 2, -5, 0}};
        for (int[] nums : inputs) {
            System.out.println("Input  : nums = " + Arrays.toString(nums));
            System.out.println("Output : " + Arrays.toString(sortArray(nums.clone())));
            System.out.println();
        }
    }

    public static int[] sortArray(int[] nums) {
        if (nums == null || nums.length < 2) {
            return nums == null ? new int[0] : nums;
        }
        mergeSort(nums, new int[nums.length], 0, nums.length - 1);
        return nums;
    }

    private static void mergeSort(int[] nums, int[] tmp, int lo, int hi) {
        if (lo >= hi) {
            return;
        }
        int mid = lo + (hi - lo) / 2;
        mergeSort(nums, tmp, lo, mid);
        mergeSort(nums, tmp, mid + 1, hi);
        int i = lo;
        int j = mid + 1;
        int k = lo;
        while (i <= mid && j <= hi) {
            tmp[k++] = nums[i] <= nums[j] ? nums[i++] : nums[j++];
        }
        while (i <= mid) {
            tmp[k++] = nums[i++];
        }
        while (j <= hi) {
            tmp[k++] = nums[j++];
        }
        System.arraycopy(tmp, lo, nums, lo, hi - lo + 1);
    }
}
