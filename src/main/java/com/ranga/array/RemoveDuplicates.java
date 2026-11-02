package com.ranga.array;

import java.util.Arrays;

/**
 * Utility class to remove duplicates from a sorted array in-place.
 *
 * <p><b>Algorithm Strategy (Two-Pointer Technique):</b>
 * <ol>
 *   <li>The input is sorted, so equal values are adjacent.</li>
 *   <li>Use a slow pointer {@code i} that marks the last unique element written so far (initially index 0) and a fast pointer {@code j} that scans from index 1.</li>
 *   <li>If {@code arr[j] != arr[i]}, a new unique value was found: advance {@code i} and copy {@code arr[j]} into {@code arr[i]}.</li>
 *   <li>If they are equal, just move {@code j} on and skip the duplicate.</li>
 *   <li>After the scan the first {@code i + 1} positions hold the unique values; return a trimmed copy using {@code Arrays.copyOf(arr, i + 1)}.</li>
 * </ol>
 *
 * <p><b>Example walkthrough</b>
 * <ul>
 *   <li>For {@code [1, 1, 2, 2, 3]}:</li>
 *   <li>{@code j = 2}: value 2 differs from 1, so {@code i = 1} and {@code arr[1] = 2}. {@code j = 4}: value 3 differs from 2, so {@code i = 2} and {@code arr[2] = 3}.</li>
 *   <li>Return the first 3 elements: {@code [1, 2, 3]}.</li>
 * </ul>
 *
 * <p><b>Complexity Analysis:</b>
 * <ul>
 *   <li><b>Time Complexity:</b> {@code O(n)} — Single linear pass where {@code n} is the array length.</li>
 *   <li><b>Space Complexity:</b> {@code O(k)} (where {@code k} is the number of unique elements)
 *       due to resizing the final output array using {@code Arrays.copyOf()}. Auxiliary working space is {@code O(1)}.</li>
 * </ul>
 *
 * @author Ranga Reddy
 */
public class RemoveDuplicates {

    public static void main(String[] args) {
        int[] arr = {1, 2, 2, 3, 3, 3, 4};

        System.out.println("Original Array : " + Arrays.toString(arr));
        int[] result = removeDuplicates(arr);
        System.out.println("Unique Array   : " + Arrays.toString(result));
    }

    /**
     * Removes duplicates from a sorted integer array and returns a trimmed copy
     * containing only unique elements.
     *
     * @param arr the sorted input integer array
     * @return a new array containing only the unique elements in their original relative order
     */
    public static int[] removeDuplicates(int[] arr) {
        // Guard Clause: null or single-element arrays have no duplicates to remove
        if (arr == null || arr.length < 2) {
            return arr;
        }

        int i = 0; // Points to the last known unique element

        for (int j = 1; j < arr.length; j++) {
            // When a new unique element is found, advance i and update its position
            if (arr[i] != arr[j]) {
                i++;
                arr[i] = arr[j];
            }
        }

        // Return a trimmed copy of the array up to the new unique boundary (index i)
        return Arrays.copyOf(arr, i + 1);
    }
}