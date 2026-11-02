package com.ranga.array;

import java.util.Arrays;

/**
 * A sorted array of unique elements was rotated an unknown number of times. Return its minimum element in {@code O(log n)} time.
 *
 * <p><b>Algorithm Strategy (Binary Search on the Rotation Point):</b>
 * <ol>
 *   <li>Keep {@code low = 0} and {@code high = n - 1}. The minimum is the only element that is smaller than its predecessor (the rotation point).</li>
 *   <li>While {@code low < high}, compute {@code mid}. If {@code nums[mid] > nums[high]}, the rotation point is to the right of {@code mid}, so set {@code low = mid + 1}.</li>
 *   <li>Otherwise the minimum is at {@code mid} or to its left, so set {@code high = mid} (not {@code mid - 1}, because {@code mid} itself may be the minimum).</li>
 *   <li>When {@code low == high}, that index holds the minimum.</li>
 * </ol>
 *
 * <p><b>Example walkthrough</b>
 * <ul>
 *   <li>For {@code [3, 4, 5, 1, 2]}: mid = 2 (5 &gt; 2) so low = 3; mid = 3 (1 &lt;= 2) so high = 3.</li>
 *   <li>Result: {@code nums[3] = 1}.</li>
 * </ul>
 *
 * <p><b>Complexity Analysis:</b>
 * <ul>
 *   <li><b>Time Complexity:</b> {@code O(log n)} — The range is halved each step.</li>
 *   <li><b>Space Complexity:</b> {@code O(1)} — Index variables only.</li>
 * </ul>
 *
 * @author Ranga Reddy
 */
public class FindMinimumInRotatedSortedArray {

    public static void main(String[] args) {
        int[][] inputs = {{3, 4, 5, 1, 2}, {4, 5, 6, 7, 0, 1, 2}, {11, 13, 15, 17}, {2, 1}};
        for (int[] nums : inputs) {
            System.out.println("Input  : nums = " + Arrays.toString(nums));
            System.out.println("Output : " + findMin(nums));
            System.out.println();
        }
    }

    public static int findMin(int[] nums) {
        if (nums == null || nums.length == 0) {
            throw new IllegalArgumentException("Array must not be null or empty.");
        }

        int low = 0;
        int high = nums.length - 1;
        while (low < high) {
            int mid = low + (high - low) / 2;
            if (nums[mid] > nums[high]) {
                low = mid + 1;      // minimum is in the right half
            } else {
                high = mid;         // minimum is at mid or in the left half
            }
        }
        return nums[low];
    }
}
