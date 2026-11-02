package com.ranga.sort;

import java.util.Arrays;

/**
 * Utility class to sort an integer array using the Merge Sort algorithm.
 *
 * <p><b>Algorithm Strategy (Divide and Conquer):</b>
 * <ol>
 *   <li><b>Base case:</b> an array with 0 or 1 elements is already sorted.</li>
 *   <li><b>Divide:</b> split the array at the middle into a left and a right half and recursively sort each half.</li>
 *   <li><b>Combine:</b> merge the two sorted halves by comparing their front elements, copying the smaller one into the output, until both halves are consumed.</li>
 *   <li>Every element is copied once per recursion level, and there are {@code log n} levels.</li>
 * </ol>
 *
 * <p><b>Example walkthrough</b>
 * <ul>
 *   <li>For {@code [3, 4, 1, 9]}:</li>
 *   <li>Divide into {@code [3, 4]} and {@code [1, 9]} (each already sorted after further splitting).</li>
 *   <li>Merge {@code [3, 4]} and {@code [1, 9]}: take 1, then 3, then 4, then 9, giving {@code [1, 3, 4, 9]}.</li>
 * </ul>
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
