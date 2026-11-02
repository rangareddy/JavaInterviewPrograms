package com.ranga.array;

import java.util.Arrays;

/**
 * Utility class demonstrating in-place array rotation using the Reversal Algorithm.
 *
 * <p><b>Algorithm Strategy (3-Step Reversal):</b>
 * Given an array of size {@code n} and right-rotations {@code k}:
 * <ol>
 *   <li>Reverse the entire array {@code [0, n - 1]}</li>
 *   <li>Reverse the first {@code k} elements {@code [0, k - 1]}</li>
 *   <li>Reverse the remaining {@code n - k} elements {@code [k, n - 1]}</li>
 * </ol>
 *
 * <p><b>Complexity Analysis:</b>
 * <ul>
 *   <li><b>Time Complexity:</b> {@code O(n)} — Each element is visited/swapped a constant number of times.</li>
 *   <li><b>Space Complexity:</b> {@code O(1)} — Operates entirely in-place with zero additional memory allocation.</li>
 * </ul>
 *
 * @author Ranga Reddy
 */
public class RotateAnArray {

    public static void main(String[] args) {
        int[] arr = {1, 2, 3, 4, 5, 6, 7};
        int k = -1;

        System.out.println("Original Array : " + Arrays.toString(arr));
        rotateAnArray(arr, k);
        System.out.println("Rotated Array  : " + Arrays.toString(arr));
    }

    /**
     * Rotates an array in-place to the right by {@code k} positions.
     *
     * <p>Handles edge cases seamlessly:
     * <ul>
     *   <li>{@code null} or single-element arrays</li>
     *   <li>{@code k} larger than array length (using modulo {@code k % n})</li>
     *   <li>Negative values of {@code k} (converting left shifts to equivalent right shifts)</li>
     * </ul>
     *
     * @param arr the integer array to rotate in-place
     * @param k   the number of positions to shift elements to the right
     */
    public static void rotateAnArray(int[] arr, int k) {
        // Guard clause: no operation needed for empty, single-element arrays, or null input
        if (arr == null || arr.length <= 1) {
            return;
        }

        int n = arr.length;

        // Normalize k to fall within array bounds [0, n - 1]
        k = k % n;

        // Handle negative rotations (e.g., rotating -1 to right is equivalent to rotating n - 1)
        if (k < 0) {
            k += n;
        }

        // If normalized k is 0, no shift is required
        if (k == 0) {
            return;
        }

        // Step 1: Reverse the entire array
        reverse(arr, 0, n - 1);

        // Step 2: Reverse the first k elements (former suffix)
        reverse(arr, 0, k - 1);

        // Step 3: Reverse the remaining n - k elements (former prefix)
        reverse(arr, k, n - 1);
    }

    /**
     * Helper method to reverse elements in an array within a specified index range [start, end].
     *
     * @param arr   the array containing elements to swap
     * @param start starting index (inclusive)
     * @param end   ending index (inclusive)
     */
    private static void reverse(int[] arr, int start, int end) {
        while (start < end) {
            int temp = arr[start];
            arr[start] = arr[end];
            arr[end] = temp;
            start++;
            end--;
        }
    }
}