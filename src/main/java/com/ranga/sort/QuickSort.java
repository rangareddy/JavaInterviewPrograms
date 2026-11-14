package com.ranga.sort;

import java.util.Arrays;
import java.util.concurrent.ThreadLocalRandom;

/**
 * Utility class to sort an integer array using Quick Sort with a random pivot that recurses on the smaller side.
 *
 * <p><b>Algorithm Strategy (Random Pivot, Smaller Side First):</b>
 * <ol>
 *   <li>Guard first: a {@code null} array and ranges with fewer than two elements ({@code low >= high}) are already sorted.</li>
 *   <li><b>Choose a pivot:</b> pick a random index in {@code [low, high]} and swap it to {@code high}. No fixed input order
 *       (sorted, reversed, all equal) can then trigger the quadratic worst case on purpose.</li>
 *   <li><b>Partition (Lomuto):</b> keep {@code i} at the last element known to be {@code <= pivot} (initially {@code low - 1}).
 *       Scan {@code j = low .. high - 1}; whenever {@code arr[j] <= pivot}, increment {@code i} and swap. Then swap the pivot
 *       into {@code i + 1}, its final position.</li>
 *   <li><b>Recurse on the smaller part only:</b> call {@code quickSort} on the smaller side and continue the {@code while} loop on
 *       the larger side. Each recursive call handles at most half of the range, so the stack depth is {@code O(log n)} even in the worst case.</li>
 * </ol>
 *
 * <p><b>Example walkthrough</b> for {@code [8, 3, 7, 1, 5]} (suppose the random pivot is 5):
 * <ul>
 *   <li>Scan: 8 &gt; 5 skip, 3 &lt;= 5 swap to front, 7 &gt; 5 skip, 1 &lt;= 5 swap {@code -> [3, 1, 7, 8, 5]}</li>
 *   <li>Place pivot: {@code -> [3, 1, 5, 8, 7]}; 5 is final. The left part has 2 elements and the right part 2, so the left one is recursed and the loop continues on the right one.</li>
 * </ul>
 *
 * <p><b>Complexity Analysis:</b>
 * <ul>
 *   <li><b>Time Complexity (Best):</b> {@code O(n log n)} — The pivot splits the range into two nearly equal parts.</li>
 *   <li><b>Time Complexity (Average):</b> {@code O(n log n)} — The random pivot gives balanced partitions in expectation for every input order.</li>
 *   <li><b>Time Complexity (Worst):</b> {@code O(n^2)} — Possible only with extremely unlucky random pivots; many equal keys also reduce the benefit of Lomuto partitioning.</li>
 *   <li><b>Space Complexity:</b> {@code O(log n)} — Recursion only on the smaller side bounds the stack depth, even in the worst case; sorts in place.</li>
 * </ul>
 *
 * @author Ranga Reddy
 */
public class QuickSort {

    public static void main(String[] args) {
        int[] arr = {12, 4, 5, 7, 9, 20, 30, 6, 15};
        System.out.println("Original Array : " + Arrays.toString(arr));
        quickSort(arr);
        System.out.println("Sorted Array   : " + Arrays.toString(arr));
        System.out.println();

        int[] sorted = {1, 2, 3, 4, 5, 6, 7, 8};
        System.out.println("Original Array : " + Arrays.toString(sorted) + "   (sorted input: no longer a worst case)");
        quickSort(sorted);
        System.out.println("Sorted Array   : " + Arrays.toString(sorted));
        System.out.println();

        int[] duplicates = {3, 1, 3, 2, 1, 3};
        System.out.println("Original Array : " + Arrays.toString(duplicates));
        quickSort(duplicates);
        System.out.println("Sorted Array   : " + Arrays.toString(duplicates));
        System.out.println();

        int[] empty = {};
        quickSort(empty);
        quickSort(null);
        System.out.println("Empty and null arrays are returned untouched: " + Arrays.toString(empty));
    }

    /**
     * Sorts the whole array in ascending order.
     *
     * @param arr the array to sort in place; {@code null} is ignored
     */
    private static void quickSort(int[] arr) {
        // Guard Clause: nothing to sort
        if (arr == null || arr.length <= 1) {
            return;
        }
        quickSort(arr, 0, arr.length - 1);
    }

    /**
     * Sorts {@code arr[low..high]} in place using Quick Sort with a random pivot.
     *
     * @param arr  the array to sort
     * @param low  the first index of the range
     * @param high the last index of the range
     */
    private static void quickSort(int[] arr, int low, int high) {
        while (low < high) {
            int pivot = partition(arr, low, high);

            // Recurse on the smaller part and loop on the larger one: the stack depth stays O(log n)
            if (pivot - low < high - pivot) {
                quickSort(arr, low, pivot - 1);
                low = pivot + 1;
            } else {
                quickSort(arr, pivot + 1, high);
                high = pivot - 1;
            }
        }
    }

    /**
     * Partitions {@code arr[low..high]} around a randomly chosen pivot.
     *
     * @param arr  the array to partition
     * @param low  the first index of the range
     * @param high the last index of the range
     * @return the final index of the pivot
     */
    private static int partition(int[] arr, int low, int high) {
        // choose a random pivot and move it to the end
        swap(arr, low + ThreadLocalRandom.current().nextInt(high - low + 1), high);
        int pivot = arr[high];

        // pointer for the last element that is <= pivot
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
