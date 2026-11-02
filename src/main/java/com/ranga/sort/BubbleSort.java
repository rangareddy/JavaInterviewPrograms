package com.ranga.sort;

import java.util.Arrays;

/**
 * Utility class to sort an integer array using Bubble Sort.
 *
 * <p><b>Algorithm Strategy (Repeated Adjacent Swaps):</b>
 * <ol>
 *   <li>Let {@code n} be the array length. Run an outer loop for {@code i = 0 .. n - 2} (one pass per iteration).</li>
 *   <li>In each pass, walk an inner index {@code j} from {@code 0} to {@code n - i - 2}.</li>
 *   <li>Compare {@code arr[j]} with {@code arr[j + 1]}; if the left element is greater, swap them.</li>
 *   <li>After pass {@code i}, the {@code i + 1} largest elements are in their final positions at the end of the
 *       array, so the next pass can stop one element earlier ({@code n - i - 1}).</li>
 *   <li>After {@code n - 1} passes the whole array is sorted.</li>
 * </ol>
 *
 * <p><b>Example walkthrough</b> for {@code [5, 3, 8, 4]}:
 * <ul>
 *   <li>Pass 1: {@code [5,3,8,4] -> [3,5,8,4] -> [3,5,8,4] -> [3,5,4,8]} (8 is now in place)</li>
 *   <li>Pass 2: {@code [3,5,4,8] -> [3,5,4,8] -> [3,4,5,8]} (5 is now in place)</li>
 *   <li>Pass 3: {@code [3,4,5,8]} (no swaps, the array is sorted)</li>
 * </ul>
 *
 * <p><b>Complexity Analysis:</b>
 * <ul>
 *   <li><b>Time Complexity (Best):</b> {@code O(n^2)} — Every pass is performed because this version has no early-exit check.</li>
 *   <li><b>Time Complexity (Average):</b> {@code O(n^2)} — Nested loops over the array.</li>
 *   <li><b>Time Complexity (Worst):</b> {@code O(n^2)} — Array in reverse order.</li>
 *   <li><b>Space Complexity:</b> {@code O(1)} — Sorts in place; only a temporary variable is used when swapping.</li>
 * </ul>
 *
 * @author Ranga Reddy
 */
public class BubbleSort {

    public static void main(String[] args) {
        int[] arr = {12, 4, 5, 7, 9, 20, 30, 6, 15};

        System.out.println("Original Array : " + Arrays.toString(arr));
        bubbleSort(arr);
        System.out.println("Sorted Array   : " + Arrays.toString(arr));
    }

    /**
     * Sorts the array in ascending order using Bubble Sort.
     *
     * @param arr the array to sort in place
     */
    private static void bubbleSort(int[] arr) {
        // Guard Clause: null or single-element arrays are already sorted
        if (arr == null || arr.length <= 1) {
            return;
        }

        int n = arr.length;
        for (int i = 0; i < n - 1; i++) {
            // The last i elements are already in place, so the inner loop shrinks every pass
            for (int j = 0; j < n - i - 1; j++) {
                if (arr[j] > arr[j + 1]) {
                    swap(arr, j, j + 1);
                }
            }
        }
    }

    private static void swap(int[] arr, int i, int j) {
        if (i == j) return;
        int temp = arr[i];
        arr[i] = arr[j];
        arr[j] = temp;
    }
}
