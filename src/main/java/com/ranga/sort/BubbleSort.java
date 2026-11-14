package com.ranga.sort;

import java.util.Arrays;

/**
 * Utility class to sort an integer array using an optimized Bubble Sort that remembers the position of the last swap.
 *
 * <p><b>Algorithm Strategy (Adjacent Swaps with a Shrinking Range):</b>
 * <ol>
 *   <li>Guard first: a {@code null} or single-element array is already sorted and is returned untouched.</li>
 *   <li>Let {@code end} be the last index that still has to be compared (initially {@code n - 1}).</li>
 *   <li>In each pass walk {@code j} from {@code 0} to {@code end - 1}; if {@code arr[j] > arr[j + 1]} swap them and
 *       remember {@code lastSwap = j}.</li>
 *   <li>Everything after the last swap of a pass is already in its final place, so the next pass stops at
 *       {@code end = lastSwap}.</li>
 *   <li>A pass without any swap leaves {@code lastSwap = 0}, which ends the loop. A sorted array therefore needs a single pass.</li>
 * </ol>
 *
 * <p><b>Example walkthrough</b> for {@code [5, 3, 8, 4]}:
 * <ul>
 *   <li>Pass 1 ({@code end = 3}): {@code [5,3,8,4] -> [3,5,8,4] -> [3,5,8,4] -> [3,5,4,8]}; the last swap was at {@code j = 2}, so {@code end = 2}.</li>
 *   <li>Pass 2 ({@code end = 2}): {@code [3,5,4,8] -> [3,5,4,8] -> [3,4,5,8]}; the last swap was at {@code j = 1}, so {@code end = 1}.</li>
 *   <li>Pass 3 ({@code end = 1}): {@code 3 <= 4}, no swap, {@code lastSwap = 0}, the loop ends with {@code [3, 4, 5, 8]}.</li>
 * </ul>
 *
 * <p><b>Complexity Analysis:</b>
 * <ul>
 *   <li><b>Time Complexity (Best):</b> {@code O(n)} — An already sorted array needs one pass without a swap.</li>
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
        System.out.println();

        int[] sorted = {1, 2, 3, 4, 5};
        System.out.println("Original Array : " + Arrays.toString(sorted) + "   (already sorted: one pass, no swap)");
        bubbleSort(sorted);
        System.out.println("Sorted Array   : " + Arrays.toString(sorted));
        System.out.println();

        int[] duplicates = {3, 1, 3, 2, 1};
        System.out.println("Original Array : " + Arrays.toString(duplicates));
        bubbleSort(duplicates);
        System.out.println("Sorted Array   : " + Arrays.toString(duplicates));
        System.out.println();

        int[] empty = {};
        bubbleSort(empty);
        bubbleSort(null);
        System.out.println("Empty and null arrays are returned untouched: " + Arrays.toString(empty));
    }

    /**
     * Sorts the array in ascending order using Bubble Sort that stops at the last swap position of each pass.
     *
     * @param arr the array to sort in place; {@code null} and arrays with fewer than two elements are left as they are
     */
    private static void bubbleSort(int[] arr) {
        // Guard Clause: null or single-element arrays are already sorted
        if (arr == null || arr.length <= 1) {
            return;
        }

        int end = arr.length - 1;
        while (end > 0) {
            int lastSwap = 0;
            for (int j = 0; j < end; j++) {
                if (arr[j] > arr[j + 1]) {
                    swap(arr, j, j + 1);
                    lastSwap = j;
                }
            }
            // Everything after the last swap is already in its final place
            end = lastSwap;
        }
    }

    private static void swap(int[] arr, int i, int j) {
        if (i == j) return;
        int temp = arr[i];
        arr[i] = arr[j];
        arr[j] = temp;
    }
}
