package com.ranga.sort;

import java.util.Arrays;

/**
 * Utility class to sort an integer array using Selection Sort.
 *
 * <p><b>Algorithm Strategy (Select the Minimum):</b>
 * <ol>
 *   <li>Find the minimum element in the unsorted part of the array.</li>
 *   <li>Swap it with the first element of the unsorted part, extending the sorted prefix by one.</li>
 *   <li>Repeat until the whole array is sorted.</li>
 * </ol>
 *
 * <p><b>Complexity Analysis:</b>
 * <ul>
 *   <li><b>Time Complexity (Best):</b> {@code O(n^2)} — The minimum is always searched for in the whole unsorted part.</li>
 *   <li><b>Time Complexity (Average):</b> {@code O(n^2)} — Nested loops over the array.</li>
 *   <li><b>Time Complexity (Worst):</b> {@code O(n^2)} — Same number of comparisons regardless of input order.</li>
 *   <li><b>Space Complexity:</b> {@code O(1)} — Sorts in place.</li>
 * </ul>
 *
 * @author Ranga Reddy
 */
public class SelectionSort {
    public static void main(String[] args) {
        int[] arr = {12, 4, 5, 7, 9, 20, 30, 6, 15};
        System.out.println("Array elements before sorting ");
        Arrays.stream(arr).forEach(val -> System.out.print(val + " "));
        selectionSort(arr);
        System.out.println("\nArray elements after sorting ");
        Arrays.stream(arr).forEach(val -> System.out.print(val + " "));
    }

    /**
     * Sorts the array in ascending order using Selection Sort.
     *
     * @param arr the array to sort in place
     */
    private static void selectionSort(int[] arr) {
        int n = arr.length;
        for (int i = 0; i < n - 1; i++) {
            int min_index = i;
            for (int j = i + 1; j < n; j++) {
                if (arr[j] < arr[min_index]) {
                    min_index = j;   // searching for minimum index
                }
            }
            swap(arr, min_index, i); // swap the array
        }
    }

    private static void swap(int[] arr, int i, int j) {
        if (i == j) return;
        int temp = arr[i];
        arr[i] = arr[j];
        arr[j] = temp;
    }
}
