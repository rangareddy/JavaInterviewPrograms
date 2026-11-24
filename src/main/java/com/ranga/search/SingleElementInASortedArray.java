package com.ranga.search;

import java.util.Arrays;

/**
 * You are given a sorted array in which every element appears exactly twice, except for one element that appears once. Return that single element in {@code O(log n)} time and {@code O(1)} space.
 *
 * <p><b>Algorithm Strategy (Binary Search on Even Indexes):</b>
 * <ol>
 *   <li>Before the single element, each pair starts at an even index ({@code nums[2k] == nums[2k + 1]}); after it, the pairs start at odd indexes.</li>
 *   <li>Binary search on even {@code mid} (use {@code mid -= mid % 2}).</li>
 *   <li>If {@code nums[mid] == nums[mid + 1]}, the pairs are still aligned, so the single element is to the right: {@code lo = mid + 2}.</li>
 *   <li>Otherwise it is at {@code mid} or to the left: {@code hi = mid}. When {@code lo == hi}, that index holds the answer.</li>
 * </ol>
 *
 * <p><b>Example walkthrough</b>
 * <ul>
 *   <li>For {@code [1, 1, 2, 3, 3, 4, 4, 8, 8]}: the pairs start at even indexes only up to index 1; the first break is at index 2, where the single element sits.</li>
 *   <li>Result: {@code 2}.</li>
 * </ul>
 *
 * <p><b>Complexity Analysis:</b>
 * <ul>
 *   <li><b>Time Complexity:</b> {@code O(log n)} — The range halves each step.</li>
 *   <li><b>Space Complexity:</b> {@code O(1)} — A few indexes.</li>
 * </ul>
 *
 * @author Ranga Reddy
 */
public class SingleElementInASortedArray {

    public static void main(String[] args) {
        int[][] inputs = {{1, 1, 2, 3, 3, 4, 4, 8, 8}, {3, 3, 7, 7, 10, 11, 11}, {5}, {1, 2, 2}};
        for (int[] nums : inputs) {
            System.out.println("Input  : nums = " + Arrays.toString(nums));
            System.out.println("Output : " + singleNonDuplicate(nums));
            System.out.println();
        }
    }

    public static int singleNonDuplicate(int[] nums) {
        if (nums == null || nums.length == 0) {
            return -1;
        }
        int lo = 0;
        int hi = nums.length - 1;
        while (lo < hi) {
            int mid = lo + (hi - lo) / 2;
            mid -= mid % 2;
            if (nums[mid] == nums[mid + 1]) {
                lo = mid + 2;
            } else {
                hi = mid;
            }
        }
        return nums[lo];
    }
}
