package com.ranga.sort;

import java.util.Arrays;

/**
 * Utility class to sort an integer array using Selection Sort.
 *
 * <p><b>Algorithm Strategy (Select the Minimum):</b>
 * <ol>
 *   <li>Split the array into a sorted prefix (initially empty) and an unsorted suffix.</li>
 *   <li>For each position {@code i} from {@code 0} to {@code n - 2}, assume {@code arr[i]} is the minimum and
 *       remember its index in {@code min_index}.</li>
 *   <li>Scan the rest of the array ({@code j = i + 1 .. n - 1}); whenever {@code arr[j] < arr[min_index]},
 *       update {@code min_index = j}.</li>
 *   <li>Swap {@code arr[i]} with {@code arr[min_index]}, which grows the sorted prefix by one element.</li>
 *   <li>When {@code i} reaches {@code n - 1} the last element is automatically the largest, so the array is sorted.</li>
 * </ol>
 *
 * <p><b>Example walkthrough</b> for {@code [29, 10, 14, 37, 13]}:
 * <ul>
 *   <li>i = 0: minimum is 10, swap with 29 {@code -> [10, 29, 14, 37, 13]}</li>
 *   <li>i = 1: minimum is 13, swap with 29 {@code -> [10, 13, 14, 37, 29]}</li>
 *   <li>i = 2: minimum is 14, already in place</li>
 *   <li>i = 3: minimum is 29, swap with 37 {@code -> [10, 13, 14, 29, 37]}</li>
 * </ul>
 *
 * <p><b>Complexity Analysis:</b>
 * <ul>
 *   <li><b>Time Complexity (Best):</b> {@code O(n^2)} — The minimum is always searched for in the whole unsorted part.</li>
 *   <li><b>Time Complexity (Average):</b> {@code O(n^2)} — Nested loops over the array.</li>
 *   <li><b>Time Complexity (Worst):</b> {@code O(n^2)} — Same number of comparisons regardless of input order.</li>
 *   <li><b>Space Complexity:</b> {@code O(1)} — Sorts in place. At most {@code n - 1} swaps are performed.</li>
 * </ul>
 *
 * @author Ranga Reddy
 */
public class SelectionSort {

    public static void main(String[] args) {
        int[] arr = {12, 4, 5, 7, 9, 20, 30, 6, 15};

        System.out.println("Original Array : " + Arrays.toString(arr));
        selectionSort(arr);
        System.out.println("Sorted Array   : " + Arrays.toString(arr));
    }

    /**
     * Sorts the array in ascending order using Selection Sort.
     *
     * @param arr the array to sort in place
     */
    private static void selectionSort(int[] arr) {
        // Guard Clause: null or single-element arrays are already sorted
        if (arr == null || arr.length <= 1) {
            return;
        }

        int n = arr.length;
        for (int i = 0; i < n - 1; i++) {
            int min_index = i;
            for (int j = i + 1; j < n; j++) {
                if (arr[j] < arr[min_index]) {
                    min_index = j;   // searching for minimum index
                }
            }
            swap(arr, min_index, i); // place the minimum at the end of the sorted prefix
        }
    }

    private static void swap(int[] arr, int i, int j) {
        if (i == j) return;
        int temp = arr[i];
        arr[i] = arr[j];
        arr[j] = temp;
    }
}
