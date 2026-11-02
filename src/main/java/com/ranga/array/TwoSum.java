package com.ranga.array;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

/**
 * Utility class to solve the Two Sum problem using a Hash Map.
 *
 * <p><b>Algorithm Strategy (Single-Pass Hash Map):</b>
 * <ol>
 *   <li>Create an empty {@code Map<Integer, Integer>} that maps a value to the index where it was seen.</li>
 *   <li>Iterate through the array once. For the element {@code nums[i]} compute its complement: {@code complement = target - nums[i]}.</li>
 *   <li>If the complement is already in the map, the earlier index and {@code i} form the answer; return them immediately.</li>
 *   <li>Otherwise store {@code nums[i] -> i} in the map so later elements can find it, and continue.</li>
 *   <li>If the loop finishes without a match, return {@code {-1, -1}}. Storing the value only after the lookup guarantees the same element is never used twice.</li>
 * </ol>
 *
 * <p><b>Example walkthrough</b>
 * <ul>
 *   <li>For {@code nums = [2, 7, 11, 15]} and {@code target = 9}:</li>
 *   <li>{@code i = 0}: value 2, complement 7 is not in the map, store {@code 2 -> 0}.</li>
 *   <li>{@code i = 1}: value 7, complement 2 is in the map at index 0, return {@code [0, 1]}.</li>
 * </ul>
 *
 * <p><b>Complexity Analysis:</b>
 * <ul>
 *   <li><b>Time Complexity:</b> {@code O(n)} — Single linear pass over the array with {@code O(1)} average map lookups.</li>
 *   <li><b>Space Complexity:</b> {@code O(n)} — Space required to store up to {@code n} elements in the hash map.</li>
 * </ul>
 *
 * @author Ranga Reddy
 */
public class TwoSum {

    public static void main(String[] args) {
        int[] nums1 = {2, 7, 11, 15};
        int target1 = 9;
        System.out.println("Input 1 : " + Arrays.toString(nums1) + ", Target: " + target1);
        System.out.println("Output  : " + Arrays.toString(twoSum(nums1, target1)));

        System.out.println();

        int[] nums2 = {3, 2, 4};
        int target2 = 6;
        System.out.println("Input 2 : " + Arrays.toString(nums2) + ", Target: " + target2);
        System.out.println("Output  : " + Arrays.toString(twoSum(nums2, target2)));
    }

    /**
     * Finds the indices of two numbers in the array that add up to the target sum.
     *
     * @param nums   the input integer array
     * @param target the target sum to achieve
     * @return a primitive int array containing the two indices, or {-1, -1} if no match is found
     */
    public static int[] twoSum(int[] nums, int target) {
        // Guard Clause: a valid pair requires at least 2 elements
        if (nums == null || nums.length < 2) {
            return new int[]{-1, -1};
        }

        // Map to store value -> index mapping
        Map<Integer, Integer> valueToIndexMap = new HashMap<>();

        for (int i = 0; i < nums.length; i++) {
            int currentVal = nums[i];
            int complement = target - currentVal;

            // If the complement exists in our map, we've found the matching pair
            if (valueToIndexMap.containsKey(complement)) {
                return new int[]{valueToIndexMap.get(complement), i};
            }

            // Otherwise, store current value and its index for future lookups
            valueToIndexMap.put(currentVal, i);
        }

        // Fallback return if no matching pair exists
        return new int[]{-1, -1};
    }
}