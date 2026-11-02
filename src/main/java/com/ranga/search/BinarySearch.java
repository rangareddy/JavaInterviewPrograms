package com.ranga.search;

import java.util.Arrays;

/**
 * Utility class to search an element in a sorted array using Binary Search.
 *
 * <p><b>Algorithm Strategy (Halve the Search Range):</b>
 * <ol>
 *   <li><b>Precondition:</b> the array must be sorted in ascending order.</li>
 *   <li>Keep two indices, {@code low = 0} and {@code high = n - 1}, that bound the range which can still
 *       contain the element.</li>
 *   <li>While {@code low <= high}, compute the middle index {@code mid = low + (high - low) / 2}
 *       (this form avoids integer overflow for very large arrays).</li>
 *   <li>If {@code arr[mid] == element}, return {@code mid}.</li>
 *   <li>If {@code arr[mid] < element}, the element can only be on the right, so set {@code low = mid + 1}.</li>
 *   <li>Otherwise it can only be on the left, so set {@code high = mid - 1}.</li>
 *   <li>When the range becomes empty ({@code low > high}) the element is not present, return {@code -1}.</li>
 * </ol>
 *
 * <p><b>Example walkthrough</b> for finding {@code 9} in {@code [4, 5, 6, 7, 9, 12, 15, 20, 30]}:
 * <ul>
 *   <li>low = 0, high = 8, mid = 4, {@code arr[4] = 9} — found at index 4.</li>
 *   <li>Searching {@code 16}: mid = 4 (9 &lt; 16, low = 5), mid = 6 (15 &lt; 16, low = 7),
 *       mid = 7 (20 &gt; 16, high = 6), range is empty — not found.</li>
 * </ul>
 *
 * <p><b>Complexity Analysis:</b>
 * <ul>
 *   <li><b>Time Complexity (Best):</b> {@code O(1)} — The target is the middle element.</li>
 *   <li><b>Time Complexity (Average):</b> {@code O(log n)} — The range is halved on every step.</li>
 *   <li><b>Time Complexity (Worst):</b> {@code O(log n)} — The target is absent or found in the last step.</li>
 *   <li><b>Space Complexity:</b> {@code O(1)} — Iterative implementation with a few index variables.</li>
 * </ul>
 *
 * @author Ranga Reddy
 */
public class BinarySearch {

    public static void main(String[] args) {
        int[] arr = {12, 4, 5, 7, 9, 20, 30, 6, 15};
        Arrays.sort(arr);
        System.out.println("Sorted Array : " + Arrays.toString(arr));

        int[] elements = {20, 16, 9, 4, 8};
        for (int element : elements) {
            int index = binarySearch(arr, element);
            System.out.println("Search : " + element + " -> " + (index != -1 ? "found at index " + index : "not found"));
        }
    }

    /**
     * Searches for an element in a sorted array.
     *
     * @param arr     the array sorted in ascending order
     * @param element the element to find
     * @return the index of the element, or {@code -1} if it is not present
     */
    public static int binarySearch(int[] arr, int element) {
        // Guard Clause: nothing to search in a null or empty array
        if (arr == null || arr.length == 0) {
            return -1;
        }

        int low = 0;
        int high = arr.length - 1;
        while (low <= high) {
            // Equivalent to (low + high) / 2 but cannot overflow
            int mid = low + (high - low) / 2;
            int val = arr[mid];
            if (val == element) {
                return mid;
            } else if (val < element) {
                low = mid + 1;
            } else {
                high = mid - 1;
            }
        }
        return -1;
    }
}
