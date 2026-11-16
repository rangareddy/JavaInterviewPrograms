package com.ranga.sort;

import java.util.Arrays;

/**
 * Utility class to sort an integer array using Merge Sort (divide and conquer) with one shared auxiliary buffer.
 *
 * <p><b>Algorithm Strategy (Divide and Merge with One Buffer):</b>
 * <ol>
 *   <li>Guard first: a {@code null} array and arrays with fewer than two elements are already sorted.</li>
 *   <li>Allocate a single auxiliary array {@code buffer} of the same length, once, before the recursion starts.</li>
 *   <li><b>Divide:</b> sort the index range {@code [low, high]} by sorting {@code [low, mid]} and {@code [mid + 1, high]} recursively, with {@code mid = low + (high - low) / 2}. No sub-array is copied.</li>
 *   <li><b>Skip when ordered:</b> if {@code arr[mid] <= arr[mid + 1]} the two halves are already in order, so the merge is skipped. An already sorted array is therefore processed in linear time.</li>
 *   <li><b>Merge:</b> copy {@code arr[low..high]} into the buffer, then write back into {@code arr}, each time taking the smaller front element of the two halves in the buffer ({@code <} on the right half, so equal keys keep their order and the sort is stable).</li>
 * </ol>
 *
 * <p><b>Example walkthrough</b> for {@code [38, 27, 43, 3]}:
 * <ul>
 *   <li>Divide: {@code [38, 27] | [43, 3]} then {@code [38] [27] | [43] [3]}</li>
 *   <li>Merge {@code [0..1]}: {@code [27, 38, 43, 3]}; merge {@code [2..3]}: {@code [27, 38, 3, 43]}</li>
 *   <li>Merge {@code [0..3]}: {@code [3, 27, 38, 43]}</li>
 * </ul>
 *
 * <p><b>Complexity Analysis:</b>
 * <ul>
 *   <li><b>Time Complexity (Best):</b> {@code O(n)} — A sorted input skips every merge and only the recursion remains.</li>
 *   <li><b>Time Complexity (Average):</b> {@code O(n log n)} — The range is always halved ({@code log n} levels) and each level merges in {@code O(n)}.</li>
 *   <li><b>Time Complexity (Worst):</b> {@code O(n log n)} — Same as the average case.</li>
 *   <li><b>Space Complexity:</b> {@code O(n)} — One buffer allocated once, plus {@code O(log n)} recursion stack, instead of new arrays at every level.</li>
 * </ul>
 *
 * @author Ranga Reddy
 */
public class MergeSort {

    public static void main(String[] args) {
        int[] arr = {12, 4, 5, 7, 9, 20, 30, 6, 15};
        System.out.println("Original Array : " + Arrays.toString(arr));
        mergeSort(arr);
        System.out.println("Sorted Array   : " + Arrays.toString(arr));
        System.out.println();

        int[] sorted = {1, 2, 3, 4, 5, 6};
        System.out.println("Original Array : " + Arrays.toString(sorted) + "   (already sorted: every merge is skipped)");
        mergeSort(sorted);
        System.out.println("Sorted Array   : " + Arrays.toString(sorted));
        System.out.println();

        int[] duplicates = {3, 1, 3, 2, 1};
        System.out.println("Original Array : " + Arrays.toString(duplicates));
        mergeSort(duplicates);
        System.out.println("Sorted Array   : " + Arrays.toString(duplicates));
        System.out.println();

        int[] empty = {};
        mergeSort(empty);
        mergeSort(null);
        System.out.println("Empty and null arrays are returned untouched: " + Arrays.toString(empty));
    }

    /**
     * Sorts the array in ascending order, in place.
     *
     * @param arr the array to sort; {@code null} and arrays with fewer than two elements are left as they are
     */
    private static void mergeSort(int[] arr) {
        // Guard Clause: nothing to sort
        if (arr == null || arr.length <= 1) {
            return;
        }
        mergeSort(arr, new int[arr.length], 0, arr.length - 1);
    }

    /**
     * Sorts {@code arr[low..high]} using the shared {@code buffer}.
     */
    private static void mergeSort(int[] arr, int[] buffer, int low, int high) {
        if (low >= high) {
            return;
        }
        int mid = low + (high - low) / 2;
        mergeSort(arr, buffer, low, mid);
        mergeSort(arr, buffer, mid + 1, high);

        // The halves are already in order: nothing to merge
        if (arr[mid] <= arr[mid + 1]) {
            return;
        }
        merge(arr, buffer, low, mid, high);
    }

    /**
     * Merges the sorted ranges {@code arr[low..mid]} and {@code arr[mid + 1..high]}.
     */
    private static void merge(int[] arr, int[] buffer, int low, int mid, int high) {
        System.arraycopy(arr, low, buffer, low, high - low + 1);
        int i = low, j = mid + 1;
        for (int k = low; k <= high; k++) {
            if (i > mid) {
                arr[k] = buffer[j++];
            } else if (j > high) {
                arr[k] = buffer[i++];
            } else if (buffer[j] < buffer[i]) {
                // Take from the right half only when it is strictly smaller ('<' keeps the sort stable)
                arr[k] = buffer[j++];
            } else {
                arr[k] = buffer[i++];
            }
        }
    }
}
