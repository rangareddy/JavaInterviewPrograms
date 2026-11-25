package com.ranga.search;

import java.util.Arrays;

/**
 * An array {@code arr} is a mountain if it strictly increases to a peak and then strictly decreases. Given a mountain array, return the index {@code i} of the peak, in {@code O(log n)} time.
 *
 * <p><b>Algorithm Strategy (Binary Search on Whether the Slope Is Rising):</b>
 * <ol>
 *   <li>Search between {@code lo = 0} and {@code hi = n - 1}.</li>
 *   <li>At {@code mid}, compare {@code arr[mid]} with {@code arr[mid + 1]}.</li>
 *   <li>If {@code arr[mid] < arr[mid + 1]} the peak lies to the right: {@code lo = mid + 1}; otherwise it is at {@code mid} or to the left: {@code hi = mid}.</li>
 *   <li>When {@code lo == hi}, that index is the peak.</li>
 * </ol>
 *
 * <p><b>Example walkthrough</b>
 * <ul>
 *   <li>For {@code [0, 10, 5, 2]}: {@code mid = 1}, {@code arr[1] = 10 > arr[2] = 5}, so {@code hi = 1}; then {@code mid = 0}, {@code 0 < 10}, so {@code lo = 1}.</li>
 *   <li>Result: {@code 1}.</li>
 * </ul>
 *
 * <p><b>Complexity Analysis:</b>
 * <ul>
 *   <li><b>Time Complexity:</b> {@code O(log n)} — The range halves each step.</li>
 *   <li><b>Space Complexity:</b> {@code O(1)} — Two indexes.</li>
 * </ul>
 *
 * @author Ranga Reddy
 */
public class PeakIndexInAMountainArray {

    public static void main(String[] args) {
        int[][] inputs = {{0, 10, 5, 2}, {0, 1, 0}, {3, 4, 5, 1}, {24, 69, 100, 99, 79, 78, 67, 36, 26, 19}, {1, 3, 2}};
        for (int[] arr : inputs) {
            System.out.println("Input  : arr = " + Arrays.toString(arr));
            System.out.println("Output : " + peakIndexInMountainArray(arr));
            System.out.println();
        }
    }

    public static int peakIndexInMountainArray(int[] arr) {
        if (arr == null || arr.length == 0) {
            return -1;
        }
        int lo = 0;
        int hi = arr.length - 1;
        while (lo < hi) {
            int mid = lo + (hi - lo) / 2;
            if (arr[mid] < arr[mid + 1]) {
                lo = mid + 1;
            } else {
                hi = mid;
            }
        }
        return lo;
    }
}
