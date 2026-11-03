package com.ranga.array;

import java.util.Arrays;

/**
 * A peak element is strictly greater than its neighbours. Given an array {@code nums} where adjacent elements are different, return the index of any peak. Assume {@code nums[-1] = nums[n] = -infinity}. The solution must run in {@code O(log n)} time.
 *
 * <p><b>Algorithm Strategy (Binary Search on the Slope):</b>
 * <ol>
 *   <li>Keep the search window {@code [low, high]}. Look at the middle element and its right neighbour.</li>
 *   <li>If {@code nums[mid] < nums[mid + 1]} the array is going up, so a peak must exist to the right (at worst the last element, since the right border is minus infinity): move {@code low = mid + 1}.</li>
 *   <li>Otherwise the array goes down after {@code mid}, so a peak exists at {@code mid} or to its left: move {@code high = mid}.</li>
 *   <li>When {@code low == high} the window holds a peak; return that index.</li>
 * </ol>
 *
 * <p><b>Example walkthrough</b>
 * <ul>
 *   <li>For {@code [1, 2, 3, 1]}: {@code mid = 1}, 2 < 3 so go right; window {@code [2, 3]}, {@code mid = 2}, 3 > 1 so {@code high = 2}; the peak is index 2.</li>
 *   <li>For {@code [1, 2, 1, 3, 5, 6, 4]} the search ends at index 5 (value 6); index 1 (value 2) is also a valid peak.</li>
 * </ul>
 *
 * <p><b>Complexity Analysis:</b>
 * <ul>
 *   <li><b>Time Complexity:</b> {@code O(log n)} — The window halves in every step.</li>
 *   <li><b>Space Complexity:</b> {@code O(1)} — Iterative binary search.</li>
 * </ul>
 *
 * @author Ranga Reddy
 */
public class FindPeakElement {

    public static void main(String[] args) {
        int[][] inputs = {{1, 2, 3, 1}, {1, 2, 1, 3, 5, 6, 4}, {1}, {3, 2, 1}, {1, 2, 3}};
        for (int[] nums : inputs) {
            int index = findPeakElement(nums);
            System.out.println("Input  : nums = " + Arrays.toString(nums));
            System.out.println("Output : index " + index + " (value " + nums[index] + ")");
            System.out.println();
        }
    }

    public static int findPeakElement(int[] nums) {
        int low = 0;
        int high = nums.length - 1;
        while (low < high) {
            int mid = low + (high - low) / 2;
            if (nums[mid] < nums[mid + 1]) {
                low = mid + 1;
            } else {
                high = mid;
            }
        }
        return low;
    }
}
