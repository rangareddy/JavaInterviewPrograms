package com.ranga.sort;

import java.util.Arrays;

/**
 * Utility class to sort an integer array using Merge Sort (divide and conquer).
 *
 * <p><b>Algorithm Strategy (Divide and Merge):</b>
 * <ol>
 *   <li><b>Base case:</b> an array with {@code 0} or {@code 1} elements is already sorted, return it as is.</li>
 *   <li><b>Divide:</b> compute {@code mid = n / 2} and copy the elements into a {@code left} array
 *       ({@code arr[0..mid-1]}) and a {@code right} array ({@code arr[mid..n-1]}).</li>
 *   <li><b>Conquer:</b> recursively sort {@code left} and {@code right}.</li>
 *   <li><b>Merge:</b> keep one index per sorted half and repeatedly copy the smaller front element into the
 *       result; when one half is exhausted, copy the remainder of the other half.</li>
 * </ol>
 *
 * <p><b>Example walkthrough</b> for {@code [38, 27, 43, 3]}:
 * <ul>
 *   <li>Divide: {@code [38, 27] | [43, 3]} then {@code [38] [27] | [43] [3]}</li>
 *   <li>Merge: {@code [27, 38]} and {@code [3, 43]}</li>
 *   <li>Merge: {@code [3, 27, 38, 43]}</li>
 * </ul>
 *
 * <p><b>Complexity Analysis:</b>
 * <ul>
 *   <li><b>Time Complexity (Best):</b> {@code O(n log n)} — The array is always halved ({@code log n} levels) and each level merges in {@code O(n)}.</li>
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

        System.out.println("Original Array : " + Arrays.toString(arr));
        int[] sortedArr = mergeSort(arr);
        System.out.println("Sorted Array   : " + Arrays.toString(sortedArr));
    }

    /**
     * Recursively splits the array in halves and merges the sorted halves.
     *
     * @param arr the array to sort
     * @return a new sorted array
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
        System.arraycopy(arr, mid, right, 0, right.length);

        left = mergeSort(left);
        right = mergeSort(right);

        return merge(left, right);
    }

    /**
     * Merges two sorted arrays into one sorted array.
     *
     * @param left  the first sorted array
     * @param right the second sorted array
     * @return a sorted array containing all elements of both inputs
     */
    private static int[] merge(int[] left, int[] right) {
        int lengthL = left.length;
        int lengthR = right.length;

        int[] result = new int[lengthL + lengthR];
        int k = 0, i = 0, j = 0;

        while (i < lengthL || j < lengthR) {
            if (i < lengthL && j < lengthR) {
                // Take the smaller front element ('<=' keeps the sort stable)
                if (left[i] <= right[j]) {
                    result[k++] = left[i++];
                } else {
                    result[k++] = right[j++];
                }
            } else if (i < lengthL) {
                result[k++] = left[i++];
            } else {
                result[k++] = right[j++];
            }
        }
        return result;
    }
}
