package com.ranga.sort;

import java.util.Arrays;

/**
 * Utility class to sort an integer array using Quick Sort (divide and conquer).
 *
 * <p><b>Algorithm Strategy (Partition Around a Pivot):</b>
 * <ol>
 *   <li><b>Base case:</b> a range with fewer than two elements ({@code low >= high}) is already sorted.</li>
 *   <li><b>Choose a pivot:</b> take the rightmost element {@code arr[high]}.</li>
 *   <li><b>Partition:</b> keep a pointer {@code i} to the last element known to be {@code <= pivot}
 *       (initially {@code low - 1}). Scan {@code j = low .. high - 1}; whenever {@code arr[j] <= pivot},
 *       increment {@code i} and swap {@code arr[i]} with {@code arr[j]}.</li>
 *   <li>Swap the pivot into position {@code i + 1}. Everything to its left is {@code <= pivot}, everything to
 *       its right is {@code > pivot}, so the pivot is in its final position.</li>
 *   <li><b>Recurse:</b> sort {@code arr[low..pivot-1]} and {@code arr[pivot+1..high]} independently.</li>
 * </ol>
 *
 * <p><b>Example walkthrough</b> for {@code [8, 3, 7, 1, 5]} (pivot = 5):
 * <ul>
 *   <li>Scan: 8 &gt; 5 skip, 3 &lt;= 5 swap to front, 7 &gt; 5 skip, 1 &lt;= 5 swap {@code -> [3, 1, 7, 8, 5]}</li>
 *   <li>Place pivot: {@code -> [3, 1, 5, 8, 7]}; 5 is final. Recurse on {@code [3, 1]} and {@code [8, 7]}.</li>
 * </ul>
 *
 * <p><b>Complexity Analysis:</b>
 * <ul>
 *   <li><b>Time Complexity (Best):</b> {@code O(n log n)} — The pivot splits the range into two nearly equal parts.</li>
 *   <li><b>Time Complexity (Average):</b> {@code O(n log n)} — Random-like input gives balanced partitions.</li>
 *   <li><b>Time Complexity (Worst):</b> {@code O(n^2)} — Already sorted input with the rightmost pivot gives maximally unbalanced partitions.</li>
 *   <li><b>Space Complexity:</b> {@code O(log n)} — Recursion stack on average ({@code O(n)} in the worst case); sorts in place.</li>
 * </ul>
 *
 * @author Ranga Reddy
 */
public class QuickSort {

    public static void main(String[] args) {
        int[] arr = {12, 4, 5, 7, 9, 20, 30, 6, 15};

        System.out.println("Original Array : " + Arrays.toString(arr));
        quickSort(arr, 0, arr.length - 1);
        System.out.println("Sorted Array   : " + Arrays.toString(arr));
    }

    /**
     * Sorts {@code arr[low..high]} in place using Quick Sort.
     *
     * @param arr  the array to sort
     * @param low  the first index of the range
     * @param high the last index of the range
     */
    private static void quickSort(int[] arr, int low, int high) {
        if (low < high) {
            // Select the pivot position
            int pivot = partition(arr, low, high);

            // Sort the elements on the left of pivot
            quickSort(arr, low, pivot - 1);

            // Sort the elements on the right of pivot
            quickSort(arr, pivot + 1, high);
        }
    }

    /**
     * Partitions {@code arr[low..high]} around the rightmost element.
     *
     * @param arr  the array to partition
     * @param low  the first index of the range
     * @param high the last index of the range
     * @return the final index of the pivot
     */
    private static int partition(int[] arr, int low, int high) {
        // choose the rightmost element as pivot
        int pivot = arr[high];

        // pointer for greater element
        int i = low - 1;

        for (int j = low; j < high; j++) {
            if (arr[j] <= pivot) {
                i++;
                swap(arr, i, j);
            }
        }
        swap(arr, i + 1, high);
        return i + 1;
    }

    // Swapping the elements
    private static void swap(int[] arr, int i, int j) {
        if (i == j) return;
        int temp = arr[i];
        arr[i] = arr[j];
        arr[j] = temp;
    }
}
