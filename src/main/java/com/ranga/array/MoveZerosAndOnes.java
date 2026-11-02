package com.ranga.array;

import java.util.Arrays;

/**
 * Utility class to sort an array containing 0s, 1s, and 2s using Dijkstra's
 * Dutch National Flag algorithm.
 *
 * <p><b>Algorithm Strategy (Dutch National Flag, Three-Way Partitioning):</b>
 * <ol>
 *   <li>Maintain three pointers: {@code low} (everything before it is 0), {@code mid} (the element being examined) and {@code high} (everything after it is 2). The region between {@code low} and {@code mid} holds 1s and {@code [mid, high]} is still unknown.</li>
 *   <li>Loop while {@code mid <= high} and look at {@code arr[mid]}.</li>
 *   <li>If it is 0, swap it with {@code arr[low]}, then advance both {@code low} and {@code mid}.</li>
 *   <li>If it is 1, it is already in the middle region, so just advance {@code mid}.</li>
 *   <li>If it is 2, swap it with {@code arr[high]} and decrement {@code high}. Do not advance {@code mid}, because the element swapped in from {@code high} has not been examined yet.</li>
 *   <li>Any other value throws an {@code IllegalArgumentException}. When {@code mid} passes {@code high} the array is sorted in a single pass.</li>
 * </ol>
 *
 * <p><b>Example walkthrough</b>
 * <ul>
 *   <li>For {@code [2, 0, 1]}:</li>
 *   <li>{@code mid = 0}: value 2, swap with {@code high} gives {@code [1, 0, 2]}, {@code high = 1}.</li>
 *   <li>{@code mid = 0}: value 1, advance {@code mid}. {@code mid = 1}: value 0, swap with {@code low} gives {@code [0, 1, 2]}.</li>
 *   <li>{@code mid} passes {@code high}: sorted.</li>
 * </ul>
 *
 * <p><b>Complexity Analysis:</b>
 * <ul>
 *   <li><b>Time Complexity:</b> {@code O(n)} — Single pass linear scan where {@code n} is the array length.</li>
 *   <li><b>Space Complexity:</b> {@code O(1)} — Operates entirely in-place with zero additional heap memory allocation.</li>
 * </ul>
 *
 * @author Ranga Reddy
 */
public class MoveZerosAndOnes {

    public static void main(String[] args) {
        int[] input = {0, 1, 2, 0, 1, 0, 1, 2, 1, 0, 2};

        System.out.println("Original Array : " + Arrays.toString(input));
        sortColors(input);
        System.out.println("Sorted Array   : " + Arrays.toString(input));
    }

    /**
     * Sorts an array consisting of only 0s, 1s, and 2s in-place.
     *
     * <p>Note: Mutates the array directly. For standard API design, returning {@code void}
     * is preferred for in-place modifications.
     *
     * @param arr the input array containing elements of value 0, 1, or 2
     */
    public static void sortColors(int[] arr) {
        // Guard Clause: null or single-element inputs require no sorting
        if (arr == null || arr.length <= 1) {
            return;
        }

        int low = 0;
        int mid = 0;
        int high = arr.length - 1;

        while (mid <= high) {
            switch (arr[mid]) {
                case 0:
                    // Swap current 0 to the low pointer boundary
                    swap(arr, low, mid);
                    low++;
                    mid++;
                    break;

                case 1:
                    // 1 is in its correct relative middle position; move to next element
                    mid++;
                    break;

                case 2:
                    // Swap current 2 to the high pointer boundary.
                    // Do NOT increment mid yet because the swapped element from 'high'
                    // has not been evaluated.
                    swap(arr, mid, high);
                    high--;
                    break;

                default:
                    throw new IllegalArgumentException(
                            "Array contains invalid element: " + arr[mid] + ". Only 0, 1, and 2 are supported."
                    );
            }
        }
    }

    /**
     * Helper method to swap two elements in an array in-place.
     *
     * @param arr the array where swap occurs
     * @param i   index of first element
     * @param j   index of second element
     */
    private static void swap(int[] arr, int i, int j) {
        int temp = arr[i];
        arr[i] = arr[j];
        arr[j] = temp;
    }
}