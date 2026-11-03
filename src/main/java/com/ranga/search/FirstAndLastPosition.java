package com.ranga.search;

import java.util.Arrays;

/**
 * Given an array of integers {@code nums} sorted in non-decreasing order, find the starting and ending position of a given {@code target}. Return {@code [-1, -1]} if the target is not in the array. The algorithm must run in {@code O(log n)} time.
 *
 * <p><b>Algorithm Strategy (Two Binary Searches (Lower Bound and Upper Bound)):</b>
 * <ol>
 *   <li>The first position is the lower bound: the first index whose value is {@code >= target}.</li>
 *   <li>The last position is one before the upper bound: the first index whose value is {@code > target}, minus 1.</li>
 *   <li>Implement one helper {@code bound(nums, target, strict)} that finds the first index with {@code nums[i] > target} (strict) or {@code nums[i] >= target} (not strict).</li>
 *   <li>If the lower bound is out of range or the value there is not the target, the target is absent: return {@code [-1, -1]}.</li>
 * </ol>
 *
 * <p><b>Example walkthrough</b>
 * <ul>
 *   <li>For {@code [5, 7, 7, 8, 8, 10]} and target 8: lower bound = 3, upper bound = 5, so the range is {@code [3, 4]}.</li>
 *   <li>For target 6 the lower bound points at 7 which is not 6, so the result is {@code [-1, -1]}.</li>
 * </ul>
 *
 * <p><b>Complexity Analysis:</b>
 * <ul>
 *   <li><b>Time Complexity:</b> {@code O(log n)} — Two binary searches.</li>
 *   <li><b>Space Complexity:</b> {@code O(1)} — Iterative.</li>
 * </ul>
 *
 * @author Ranga Reddy
 */
public class FirstAndLastPosition {

    public static void main(String[] args) {
        int[] nums = {5, 7, 7, 8, 8, 10};
        int[] targets = {8, 6, 5, 10, 11};
        for (int target : targets) {
            System.out.println("Input  : nums = " + Arrays.toString(nums) + ", target = " + target);
            System.out.println("Output : " + Arrays.toString(searchRange(nums, target)));
            System.out.println();
        }
    }

    public static int[] searchRange(int[] nums, int target) {
        int first = bound(nums, target, false);
        if (first == nums.length || nums[first] != target) {
            return new int[]{-1, -1};
        }
        return new int[]{first, bound(nums, target, true) - 1};
    }

    /** First index whose value is >= target, or > target when strict is true. */
    private static int bound(int[] nums, int target, boolean strict) {
        int low = 0;
        int high = nums.length;
        while (low < high) {
            int mid = low + (high - low) / 2;
            if (nums[mid] < target || (strict && nums[mid] == target)) {
                low = mid + 1;
            } else {
                high = mid;
            }
        }
        return low;
    }
}
