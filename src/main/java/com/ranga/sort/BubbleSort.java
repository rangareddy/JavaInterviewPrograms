package com.ranga.sort;

import java.util.Arrays;

/**
 * Utility class to sort an integer array using Bubble Sort.
 *
 * <p><b>Algorithm Strategy (Repeated Adjacent Swaps):</b>
 * <ol>
 *   <li>Compare each pair of adjacent elements and swap them if they are in the wrong order.</li>
 *   <li>After each pass the largest remaining element "bubbles" to the end, so the unsorted range shrinks by one.</li>
 *   <li>Repeat for {@code n - 1} passes.</li>
 * </ol>
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
        System.out.println("Array elements before sorting ");
        Arrays.stream(arr).forEach(val -> System.out.print(val + " "));
        bubbleSort(arr);
        System.out.println("\nArray elements after sorting ");
        Arrays.stream(arr).forEach(val -> System.out.print(val + " "));
    }

    /**
     * Sorts the array in ascending order using Bubble Sort.
     *
     * @param arr the array to sort in place
     */
    private static void bubbleSort(int[] arr) {
        int n = arr.length;
        for (int i = 0; i < n - 1; i++) {
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
