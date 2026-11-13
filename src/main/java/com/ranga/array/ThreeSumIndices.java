package com.ranga.array;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

/**
 * Given an integer array {@code nums} and an integer {@code target}, return the indices of three different elements whose sum is {@code target}. This is Two Sum extended by one number, and the same hash map idea carries over. (For all unique triplets that sum to 0, see 3Sum.)
 *
 * <p><b>Algorithm Strategy (Fix One Number, Then Run Two Sum with a Hash Map):</b>
 * <ol>
 *   <li>Fix the first index {@code i}. The remaining task is Two Sum on the elements after {@code i} with the target {@code target - nums[i]}.</li>
 *   <li>Solve that inner Two Sum exactly like the single-pass hash map version: create an empty map from value to index, and walk {@code j} from {@code i + 1} to the end.</li>
 *   <li>For each {@code j} compute the complement {@code target - nums[i] - nums[j]}. If the map already holds it, the indices {@code i}, the stored index and {@code j} are the answer.</li>
 *   <li>Otherwise store {@code nums[j] -> j} and continue. The value is stored only after the lookup, so the same element is never used twice, and every stored index is greater than {@code i} and smaller than {@code j}.</li>
 *   <li>If no {@code i} produces a match, return {@code {-1, -1, -1}}. A fresh map is created for every {@code i}, so the extra space stays {@code O(n)}.</li>
 * </ol>
 *
 * <p><b>Example walkthrough</b>
 * <ul>
 *   <li>For {@code nums = [1, 2, 3, 4, 5]} and {@code target = 9}: with {@code i = 0} (value 1) the inner target is 8; {@code j = 1} stores 2, {@code j = 2} stores 3, {@code j = 3} (value 4) needs 4 but the map holds only 2 and 3, {@code j = 4} (value 5) needs 3, which is stored at index 2.</li>
 *   <li>The answer is the indices {@code [0, 2, 4]}, the values {@code 1 + 3 + 5 = 9}.</li>
 * </ul>
 *
 * <p><b>Complexity Analysis:</b>
 * <ul>
 *   <li><b>Time Complexity:</b> {@code O(n^2)} — One {@code O(n)} hash map pass for each of the {@code n} choices of the first index.</li>
 *   <li><b>Space Complexity:</b> {@code O(n)} — The hash map of the inner pass.</li>
 * </ul>
 *
 * @author Ranga Reddy
 */
public class ThreeSumIndices {

    public static void main(String[] args) {
        int[][] inputs = {{1, 2, 3, 4, 5}, {-1, 0, 1, 2, -1, -4}, {3, 3, 3}, {1, 2}};
        int[] targets = {9, 0, 9, 3};
        for (int i = 0; i < inputs.length; i++) {
            System.out.println("Input  : " + Arrays.toString(inputs[i]) + ", Target: " + targets[i]);
            System.out.println("Output : " + Arrays.toString(threeSum(inputs[i], targets[i])));
            System.out.println();
        }
    }

    public static int[] threeSum(int[] nums, int target) {
        // Guard Clause: a valid triple requires at least 3 elements
        if (nums == null || nums.length < 3) {
            return new int[]{-1, -1, -1};
        }

        for (int i = 0; i < nums.length - 2; i++) {
            // Two Sum on the elements after i
            Map<Integer, Integer> valueToIndex = new HashMap<>();
            for (int j = i + 1; j < nums.length; j++) {
                int complement = target - nums[i] - nums[j];
                if (valueToIndex.containsKey(complement)) {
                    return new int[]{i, valueToIndex.get(complement), j};
                }
                valueToIndex.put(nums[j], j);
            }
        }
        return new int[]{-1, -1, -1};
    }
}
