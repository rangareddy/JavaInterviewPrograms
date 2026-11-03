package com.ranga.search;

import java.util.Arrays;

/**
 * Given a sorted array of distinct integers and a target value, return the index of the target if it is found. If not, return the index where it would be inserted to keep the array sorted. The algorithm must run in {@code O(log n)} time.
 *
 * <p><b>Algorithm Strategy (Binary Search for the Lower Bound):</b>
 * <ol>
 *   <li>The answer is the first index whose element is greater than or equal to the target (the lower bound), or {@code n} if every element is smaller.</li>
 *   <li>Search the half-open window {@code [low, high)} starting with {@code low = 0}, {@code high = n}.</li>
 *   <li>If {@code nums[mid] < target} the answer lies to the right: {@code low = mid + 1}. Otherwise {@code mid} could be the answer: {@code high = mid}.</li>
 *   <li>When the window is empty, {@code low} is the insertion position (and the index of the target when it exists).</li>
 * </ol>
 *
 * <p><b>Example walkthrough</b>
 * <ul>
 *   <li>For {@code [1, 3, 5, 6]} and target 5 the answer is index 2; for target 2 it is 1 (between 1 and 3); for target 7 it is 4 (after the last element).</li>
 *   <li>For target 0 the answer is 0 because every element is larger.</li>
 * </ul>
 *
 * <p><b>Complexity Analysis:</b>
 * <ul>
 *   <li><b>Time Complexity:</b> {@code O(log n)} — The window halves in every step.</li>
 *   <li><b>Space Complexity:</b> {@code O(1)} — Iterative.</li>
 * </ul>
 *
 * @author Ranga Reddy
 */
public class SearchInsertPosition {

    public static void main(String[] args) {
        int[] nums = {1, 3, 5, 6};
        int[] targets = {5, 2, 7, 0, 6};
        for (int target : targets) {
            System.out.println("Input  : nums = " + Arrays.toString(nums) + ", target = " + target);
            System.out.println("Output : " + searchInsert(nums, target));
            System.out.println();
        }
    }

    public static int searchInsert(int[] nums, int target) {
        int low = 0;
        int high = nums.length;
        while (low < high) {
            int mid = low + (high - low) / 2;
            if (nums[mid] < target) {
                low = mid + 1;
            } else {
                high = mid;
            }
        }
        return low;
    }
}
