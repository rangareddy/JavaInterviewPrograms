package com.ranga.search;

import java.util.Arrays;

/**
 * Given two sorted arrays {@code nums1} and {@code nums2} of sizes {@code m} and {@code n}, return the median of the two sorted arrays. The overall run time complexity should be {@code O(log(m + n))}.
 *
 * <p><b>Algorithm Strategy (Binary Search for the Partition):</b>
 * <ol>
 *   <li>Without merging, the median splits all {@code m + n} numbers into a left half of {@code (m + n + 1) / 2} numbers and a right half. Binary-search how many numbers {@code i} of the shorter array {@code A} go to the left half; the remaining {@code j = half - i} come from the longer array {@code B}.</li>
 *   <li>A partition is correct when {@code A[i-1] <= B[j]} and {@code B[j-1] <= A[i]}: everything on the left is not larger than everything on the right. Use {@code -infinity} / {@code +infinity} for missing neighbours at the ends.</li>
 *   <li>If {@code A[i-1] > B[j]}, too many numbers were taken from {@code A}: search smaller {@code i}. If {@code B[j-1] > A[i]}, take more from {@code A}: search larger {@code i}.</li>
 *   <li>For an odd total the median is the largest number of the left half; for an even total it is the average of that number and the smallest number of the right half.</li>
 * </ol>
 *
 * <p><b>Example walkthrough</b>
 * <ul>
 *   <li>For {@code [1, 2]} and {@code [3, 4]}: the left half must hold 2 numbers; taking {@code i = 2} from the first array leaves {@code j = 0}, and {@code 2 <= 3} and nothing is on the left of B, so the partition is valid.</li>
 *   <li>Median = {@code (max(2) + min(3)) / 2 = 2.5}.</li>
 * </ul>
 *
 * <p><b>Complexity Analysis:</b>
 * <ul>
 *   <li><b>Time Complexity:</b> {@code O(log(min(m, n)))} — The binary search runs on the shorter array.</li>
 *   <li><b>Space Complexity:</b> {@code O(1)} — Index variables only.</li>
 * </ul>
 *
 * @author Ranga Reddy
 */
public class MedianOfTwoSortedArrays {

    public static void main(String[] args) {
        int[][] first = {{1, 3}, {1, 2}, {0, 0}, {}, {2}, {1, 3, 5, 7, 9}};
        int[][] second = {{2}, {3, 4}, {0, 0}, {1}, {}, {2, 4}};
        for (int i = 0; i < first.length; i++) {
            System.out.println("Input  : nums1 = " + Arrays.toString(first[i]) + ", nums2 = " + Arrays.toString(second[i]));
            System.out.println("Output : " + findMedianSortedArrays(first[i], second[i]));
            System.out.println();
        }
    }

    public static double findMedianSortedArrays(int[] nums1, int[] nums2) {
        if (nums1.length > nums2.length) {
            return findMedianSortedArrays(nums2, nums1);        // binary search on the shorter array
        }

        int m = nums1.length;
        int n = nums2.length;
        int half = (m + n + 1) / 2;
        int low = 0;
        int high = m;
        while (low <= high) {
            int i = low + (high - low) / 2;         // numbers taken from nums1 into the left half
            int j = half - i;                       // numbers taken from nums2 into the left half

            int left1 = (i == 0) ? Integer.MIN_VALUE : nums1[i - 1];
            int right1 = (i == m) ? Integer.MAX_VALUE : nums1[i];
            int left2 = (j == 0) ? Integer.MIN_VALUE : nums2[j - 1];
            int right2 = (j == n) ? Integer.MAX_VALUE : nums2[j];

            if (left1 <= right2 && left2 <= right1) {
                if ((m + n) % 2 == 1) {
                    return Math.max(left1, left2);
                }
                return (Math.max(left1, left2) + (double) Math.min(right1, right2)) / 2.0;
            } else if (left1 > right2) {
                high = i - 1;
            } else {
                low = i + 1;
            }
        }
        throw new IllegalArgumentException("Input arrays are not sorted.");
    }
}
