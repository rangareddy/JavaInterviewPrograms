package com.ranga.sort;

import java.util.Arrays;

/**
 * Given an array of integers {@code nums}, sort it in ascending order and return it, without using a built-in sorting function. Aim for {@code O(n log n)} time.
 *
 * <p><b>Algorithm Strategy (Merge Sort (Top-Down, with One Shared Buffer)):</b>
 * <ol>
 *   <li>Split the range {@code [lo, hi)} in the middle, sort each half recursively, then merge the two sorted halves.</li>
 *   <li>A range of length 0 or 1 is already sorted, which ends the recursion.</li>
 *   <li>To merge, copy the two halves into the shared buffer and write back into {@code nums} by repeatedly taking the smaller front element. Taking from the left half on ties makes the sort stable.</li>
 *   <li>Because the work is split evenly, the time is {@code O(n log n)} in the best, average and worst case, unlike quick sort whose worst case is {@code O(n^2)}.</li>
 * </ol>
 *
 * <p><b>Example walkthrough</b>
 * <ul>
 *   <li>For {@code [5, 2, 3, 1]}: split into {@code [5, 2]} and {@code [3, 1]}, sort to {@code [2, 5]} and {@code [1, 3]}.</li>
 *   <li>Merge by taking 1, 2, 3, 5: {@code [1, 2, 3, 5]}.</li>
 * </ul>
 *
 * <p><b>Complexity Analysis:</b>
 * <ul>
 *   <li><b>Time Complexity:</b> {@code O(n log n)} — The array is halved {@code log n} times and each level merges {@code n} elements.</li>
 *   <li><b>Space Complexity:</b> {@code O(n)} — The merge buffer.</li>
 * </ul>
 *
 * @author Ranga Reddy
 */
public class SortAnArray {

    public static void main(String[] args) {
        int[][] inputs = {{5, 2, 3, 1}, {5, 1, 1, 2, 0, 0}, {}, {7}, {-3, 4, -3, 0}};
        for (int[] nums : inputs) {
            System.out.println("Input  : nums = " + Arrays.toString(nums));
            System.out.println("Output : " + Arrays.toString(sortArray(nums.clone())));
            System.out.println();
        }
    }

    public static int[] sortArray(int[] nums) {
        mergeSort(nums, new int[nums.length], 0, nums.length);
        return nums;
    }

    private static void mergeSort(int[] nums, int[] buffer, int lo, int hi) {
        if (hi - lo < 2) {
            return;
        }
        int mid = lo + (hi - lo) / 2;
        mergeSort(nums, buffer, lo, mid);
        mergeSort(nums, buffer, mid, hi);
        System.arraycopy(nums, lo, buffer, lo, hi - lo);
        int left = lo;
        int right = mid;
        for (int k = lo; k < hi; k++) {
            if (left < mid && (right >= hi || buffer[left] <= buffer[right])) {
                nums[k] = buffer[left++];
            } else {
                nums[k] = buffer[right++];
            }
        }
    }
}
