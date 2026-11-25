package com.ranga.search;

import java.util.Arrays;

/**
 * Given a strictly increasing array {@code arr} of positive integers and an integer {@code k}, return the {@code k}-th positive integer that is missing from the array.
 *
 * <p><b>Algorithm Strategy (Binary Search on How Many Numbers Are Missing Before Each Index):</b>
 * <ol>
 *   <li>At index {@code i} the number of missing positives before {@code arr[i]} is {@code arr[i] - (i + 1)}.</li>
 *   <li>Binary search for the first index {@code lo} where this missing count is at least {@code k}.</li>
 *   <li>The {@code k}-th missing number then lies before {@code arr[lo]}, and its value is {@code k + lo} (there are {@code lo} array elements below it).</li>
 *   <li>If no such index exists, the answer is {@code k + arr.length}.</li>
 * </ol>
 *
 * <p><b>Example walkthrough</b>
 * <ul>
 *   <li>For {@code [2, 3, 4, 7, 11]} and {@code k = 5}: missing counts are 1, 1, 1, 3, 6. The first index with at least 5 missing is 4.</li>
 *   <li>Result: {@code 5 + 4 = 9}.</li>
 * </ul>
 *
 * <p><b>Complexity Analysis:</b>
 * <ul>
 *   <li><b>Time Complexity:</b> {@code O(log n)} — Binary search.</li>
 *   <li><b>Space Complexity:</b> {@code O(1)} — Two indexes.</li>
 * </ul>
 *
 * @author Ranga Reddy
 */
public class KthMissingPositiveNumber {

    public static void main(String[] args) {
        int[][] arrs = {{2, 3, 4, 7, 11}, {1, 2, 3, 4}, {}, {5}};
        int[] ks = {5, 2, 3, 1};
        for (int i = 0; i < arrs.length; i++) {
            System.out.println("Input  : arr = " + Arrays.toString(arrs[i]) + ", k = " + ks[i]);
            System.out.println("Output : " + findKthPositive(arrs[i], ks[i]));
            System.out.println();
        }
    }

    public static int findKthPositive(int[] arr, int k) {
        if (arr == null || arr.length == 0) {
            return k;
        }
        int lo = 0;
        int hi = arr.length;
        while (lo < hi) {
            int mid = lo + (hi - lo) / 2;
            if (arr[mid] - (mid + 1) >= k) {
                hi = mid;
            } else {
                lo = mid + 1;
            }
        }
        return lo + k;
    }
}
