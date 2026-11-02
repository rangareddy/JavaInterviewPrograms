package com.ranga.sort;

import java.util.Arrays;

/**
 * Utility class to sort an integer array using Merge Sort (divide and conquer).
 *
 * <p><b>Algorithm Strategy (Divide and Merge):</b>
 * <ol>
 *   <li>Split the array into two halves until each part has at most one element.</li>
 *   <li>Recursively sort both halves.</li>
 *   <li>Merge the two sorted halves into one sorted array by repeatedly taking the smaller front element.</li>
 * </ol>
 *
 * <p><b>Complexity Analysis:</b>
 * <ul>
 *   <li><b>Time Complexity (Best):</b> {@code O(n log n)} — The array is always halved and merged in linear time.</li>
 *   <li><b>Time Complexity (Average):</b> {@code O(n log n)} — Same as best case.</li>
 *   <li><b>Time Complexity (Worst):</b> {@code O(n log n)} — Same as best case.</li>
 *   <li><b>Space Complexity:</b> {@code O(n)} — Auxiliary arrays are created for the halves and the merged result.</li>
 * </ul>
 *
 * @author Ranga Reddy
 */
public class MergeSort {
    public static void main(String[] args) {
        int[] arr = {12, 4, 5, 7, 9, 20, 30, 6, 15};
        System.out.println("Array elements before sorting ");
        Arrays.stream(arr).forEach(val -> System.out.print(val + " "));
        arr = mergeSort(arr);
        System.out.println("\nArray elements after sorting ");
        Arrays.stream(arr).forEach(val -> System.out.print(val + " "));
    }

    /**
     * Recursively splits the array in halves and merges the sorted halves.
     *
     * @param arr the array to sort
     * @return a new sorted array
     */
    /**
     * Merges two sorted arrays into one sorted array.
     *
     * @param left the first sorted array
     * @param right the second sorted array
     * @return a sorted array containing all elements of both inputs
     */
    private static int[] mergeSort(int[] arr) {
        int n = arr.length;
        if (n <= 1) {
            return arr;
        }

        int mid = n / 2;
        int[] left = new int[mid];
        int[] right = new int[n - mid];

        System.arraycopy(arr, 0, left, 0, left.length);
        System.arraycopy(arr, mid + 0, right, 0, right.length);

        left = mergeSort(left);
        right = mergeSort(right);

        return merge(left, right);
    }

    private static int[] merge(int[] left, int[] right) {
        int lengthL = left.length;
        int lengthR = right.length;

        int[] result = new int[lengthL + lengthR];
        int k = 0, i = 0, j = 0;

        while (i < lengthL || j < lengthR) {
            if (i < lengthL && j < lengthR) {
                if (left[i] < right[j]) {
                    result[k++] = left[i++];
                } else {
                    result[k++] = right[j++];
                }
            } else if (i < lengthL) {
                result[k++] = left[i++];
            } else if (j < lengthR) {
                result[k++] = right[j++];
            }
        }
        return result;
    }
}
