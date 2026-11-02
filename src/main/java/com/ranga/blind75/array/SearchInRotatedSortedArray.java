package com.ranga.blind75.array;

import java.util.Arrays;

/**
 * Given a sorted array of unique values that was rotated at an unknown pivot and a {@code target}, return the index of the target, or {@code -1} if it is absent, in {@code O(log n)} time.
 *
 * <p><b>Algorithm Strategy (Binary Search on the Sorted Half):</b>
 * <ol>
 *   <li>Run a normal binary search with {@code low}, {@code high} and {@code mid}; return {@code mid} when {@code nums[mid] == target}.</li>
 *   <li>At least one half around {@code mid} is always sorted. If {@code nums[low] <= nums[mid]} the left half {@code [low, mid]} is sorted, otherwise the right half is sorted.</li>
 *   <li>Check whether the target lies inside the sorted half using its endpoints. If so, continue in that half; otherwise continue in the other half.</li>
 *   <li>If the range becomes empty, return {@code -1}.</li>
 * </ol>
 *
 * <p><b>Example walkthrough</b>
 * <ul>
 *   <li>For {@code [4, 5, 6, 7, 0, 1, 2]} and target 0: mid = 3 (7), left half {@code [4..7]} is sorted but does not contain 0, so low = 4.</li>
 *   <li>mid = 5 (1): the left half {@code [0, 1]} is sorted and contains 0, so high = 4; then {@code nums[4] = 0}, result {@code 4}.</li>
 * </ul>
 *
 * <p><b>Complexity Analysis:</b>
 * <ul>
 *   <li><b>Time Complexity:</b> {@code O(log n)} — The search range halves each step.</li>
 *   <li><b>Space Complexity:</b> {@code O(1)} — Index variables only.</li>
 * </ul>
 *
 * @author Ranga Reddy
 */
public class SearchInRotatedSortedArray {

    public static void main(String[] args) {
        int[][] inputs = {{4, 5, 6, 7, 0, 1, 2}, {4, 5, 6, 7, 0, 1, 2}, {1}};
        int[] targets = {0, 3, 1};
        for (int i = 0; i < inputs.length; i++) {
            System.out.println("Input  : nums = " + Arrays.toString(inputs[i]) + ", target = " + targets[i]);
            System.out.println("Output : " + search(inputs[i], targets[i]));
            System.out.println();
        }
    }

    public static int search(int[] nums, int target) {
        if (nums == null || nums.length == 0) {
            return -1;
        }

        int low = 0;
        int high = nums.length - 1;
        while (low <= high) {
            int mid = low + (high - low) / 2;
            if (nums[mid] == target) {
                return mid;
            }

            if (nums[low] <= nums[mid]) {                       // left half is sorted
                if (nums[low] <= target && target < nums[mid]) {
                    high = mid - 1;
                } else {
                    low = mid + 1;
                }
            } else {                                            // right half is sorted
                if (nums[mid] < target && target <= nums[high]) {
                    low = mid + 1;
                } else {
                    high = mid - 1;
                }
            }
        }
        return -1;
    }
}
