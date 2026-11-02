package com.ranga.search;

import java.util.Arrays;

/**
 * Utility class to search an element in an array using Linear Search.
 *
 * <p><b>Algorithm Strategy (Sequential Scan):</b>
 * <ol>
 *   <li>Start at index {@code 0} and compare each element with the one that is searched for.</li>
 *   <li>As soon as {@code arr[i] == element}, return {@code i}; the first matching index wins.</li>
 *   <li>If the end of the array is reached without a match, return {@code -1}.</li>
 *   <li>No ordering is required, which is the advantage over Binary Search; the cost is that every element may be inspected.</li>
 * </ol>
 *
 * <p><b>Example walkthrough</b> for finding {@code 20} in {@code [12, 4, 5, 7, 9, 20, 30, 6, 15]}:
 * <ul>
 *   <li>Indices 0 to 4 hold 12, 4, 5, 7 and 9, none of which equals 20.</li>
 *   <li>{@code arr[5] == 20}, so the result is index 5.</li>
 * </ul>
 *
 * <p><b>Complexity Analysis:</b>
 * <ul>
 *   <li><b>Time Complexity (Best):</b> {@code O(1)} — The element is the first one.</li>
 *   <li><b>Time Complexity (Average):</b> {@code O(n)} — About half of the array is scanned.</li>
 *   <li><b>Time Complexity (Worst):</b> {@code O(n)} — The element is last or absent.</li>
 *   <li><b>Space Complexity:</b> {@code O(1)} — Only an index variable is used.</li>
 * </ul>
 *
 * @author Ranga Reddy
 */
public class LinearSearch {

    public static void main(String[] args) {
        int[] arr = {12, 4, 5, 7, 9, 20, 30, 6, 15};
        System.out.println("Array : " + Arrays.toString(arr));

        int[] elements = {20, 12, 15, 8};
        for (int element : elements) {
            int index = linearSearch(arr, element);
            System.out.println("Search : " + element + " -> " + (index != -1 ? "found at index " + index : "not found"));
        }
    }

    /**
     * Searches for an element by scanning the array from the start.
     *
     * @param arr     the array to scan (any order)
     * @param element the element to find
     * @return the index of the first occurrence, or {@code -1} if it is not present
     */
    public static int linearSearch(int[] arr, int element) {
        // Guard Clause: nothing to search in a null or empty array
        if (arr == null) {
            return -1;
        }

        for (int i = 0; i < arr.length; i++) {
            if (arr[i] == element) {
                return i;
            }
        }
        return -1;
    }
}
