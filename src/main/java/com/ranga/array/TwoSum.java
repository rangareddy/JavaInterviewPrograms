package com.ranga.array;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

/**
 * Utility class to solve the Two Sum problem using a Hash Map.
 *
 * <p><b>Algorithm Strategy (Single-Pass Hash Map):</b>
 * <ol>
 *   <li>Iterate through the array once.</li>
 *   <li>For each element, calculate its complement: {@code complement = target - nums[i]}.</li>
 *   <li>Check if the complement already exists in the map. If it does, we found our pair.</li>
 *   <li>Otherwise, store the current element and its index in the map and continue.</li>
 * </ol>
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