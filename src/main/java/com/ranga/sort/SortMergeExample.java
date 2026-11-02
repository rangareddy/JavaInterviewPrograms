package com.ranga.sort;

import java.util.Arrays;

/**
 * Utility class to sort an integer array using the Merge Sort algorithm.
 *
 * <p><b>Algorithm Strategy (Divide and Conquer):</b>
 * <ol>
 *   <li><b>Divide:</b> Split the array into two halves recursively until base cases
 *       of size 0 or 1 are reached.</li>
 *   <li><b>Conquer:</b> Sort each half recursively.</li>
 *   <li><b>Combine:</b> Merge the two sorted sub-arrays into a single sorted output array.</li>
 * </ol>
 *
 * <p><b>Complexity Analysis:</b>
 * <ul>
 *   <li><b>Time Complexity:</b> {@code O(n log n)} across all cases (Best, Average, Worst)
 *       due to consistent recursive tree splitting and linear merging work.</li>
 *   <li><b>Space Complexity:</b> {@code O(n)} auxiliary space for maintaining temporary sub-arrays
 *       and the recursive call stack depth of {@code O(log n)}.</li>
 * </ul>
 *
 * @author Ranga Reddy
 */

public class SortMergeExample {
    public static void main(String[] args) {
        int[] arr = {3, 4, 1, 9, 5, 8};

        System.out.println("Original Array : " + Arrays.toString(arr));
        int[] sortedArr = mergeSort(arr);
        System.out.println("Sorted Array   : " + Arrays.toString(sortedArr));
    }

    /**
     * Recursively divides the array into sub-arrays and merges them in sorted order.
     *
     * @param arr the input integer array to sort
     * @return a new sorted integer array
     */

    private static int[] mergeSort(int[] arr) {
        if (arr == null || arr.length < 2) {
            return arr;
        }
        int n = arr.length;
        int mid = n/2;

        // Split array into left and right sub-arrays
        int left[] = new int[mid];
        int right[] = new int[n-mid];
        for(int i=0; i<mid; i++) {
            left[i] = arr[i];
        }
        for(int j=mid; j<n; j++) {
            right[j-mid] = arr[j];
        }

        // Recursively sort both halves
        left = mergeSort(left);
        right = mergeSort(right);

        // Combine the sorted halves
        return merge(left, right);
    }

    /**
     * Merges two sorted arrays into a single sorted array.
     *
     * @fn_param a the first sorted sub-array
     * @fn_param b the second sorted sub-array
     * @return a merged sorted array containing all elements from {@code a} and {@code b}
     */
    private static int[] merge(int[] a, int[] b) {
        int []result = new int[a.length + b.length];
        int m = a.length;
        int n = b.length;
        int i = 0; // Pointer for array a
        int j = 0; // Pointer for array b
        int k = 0; // Pointer for result array

        while(i < m || j < n) {
            if(i < m && j < n) {
                if(a[i] < b[j]) {
                    result[k++] = a[i++];
                } else {
                    result[k++] = b[j++];
                }

            } else if (i < m) {
                result[k++] = a[i++];
            } else {
                result[k++] = b[j++];
            }
        }
        return result;
    }
}
