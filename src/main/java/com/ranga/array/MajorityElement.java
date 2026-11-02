package com.ranga.array;

import java.util.Arrays;

/**
 * Utility class to find the majority element in an array using
 * Boyer-Moore Voting Algorithm.
 *
 * <p><b>Algorithm Strategy (Boyer-Moore Voting):</b>
 * <ol>
 *   <li>A majority element appears more than {@code n / 2} times, so it outnumbers all other elements combined.</li>
 *   <li>Maintain a {@code candidate} element and a {@code count}, both starting from the first element.</li>
 *   <li>Iterate through the array. When {@code count} is 0, adopt the current element as the new candidate.</li>
 *   <li>If the current element equals the candidate, increment {@code count}; otherwise decrement it (a pair of different elements cancels out).</li>
 *   <li>Because the majority element has more occurrences than all the others together, it cannot be fully cancelled and is the final candidate. (This assumes a majority element exists; otherwise verify with a second counting pass.)</li>
 * </ol>
 *
 * <p><b>Example walkthrough</b>
 * <ul>
 *   <li>For {@code [2, 2, 1, 2, 1, 1, 2]}:</li>
 *   <li>candidate/count after each element: 2/1, 2/2, 2/1, 2/2, 2/1, 2/0, then count is 0 so the last element 2 becomes the candidate with count 1.</li>
 *   <li>Result: {@code 2}.</li>
 * </ul>
 *
 * <p><b>Complexity Analysis:</b>
 * <ul>
 *   <li><b>Time Complexity:</b> {@code O(n)} — Single linear pass over the array.</li>
 *   <li><b>Space Complexity:</b> {@code O(1)} — Constant extra space used for pointers and counters.</li>
 * </ul>
 *
 * @author Ranga Reddy
 */
public class MajorityElement {

    public static void main(String[] args) {
        int[] input1 = {3, 2, 3};
        System.out.println("Input  : " + Arrays.toString(input1));
        System.out.println("Output : " + majorityElement(input1)); // Output: 3

        System.out.println();

        int[] input2 = {2, 2, 1, 2, 1, 1, 2};
        System.out.println("Input  : " + Arrays.toString(input2));
        System.out.println("Output : " + majorityElement(input2)); // Output: 2
    }

    /**
     * Finds the majority element that appears more than {@code n / 2} times in the array.
     * Assumes that a majority element always exists in the input array.
     *
     * @param nums the input integer array
     * @return the majority element
     * @throws IllegalArgumentException if the array is null or empty
     */
    public static int majorityElement(int[] nums) {
        // Guard Clause: prevent ArrayIndexOutOfBoundsException on empty or null arrays
        if (nums == null || nums.length == 0) {
            throw new IllegalArgumentException("Array must not be null or empty.");
        }

        // Phase 1: Find the candidate using Boyer-Moore Voting
        int count = 0;
        int candidate = nums[0];

        for (int num : nums) {
            if (count == 0) {
                candidate = num;
            }
            count += (num == candidate) ? 1 : -1;
        }

        return candidate;
    }
}