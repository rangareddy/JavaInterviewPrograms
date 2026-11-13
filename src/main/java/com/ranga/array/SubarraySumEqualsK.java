package com.ranga.array;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

/**
 * Given an array of integers {@code nums} and an integer {@code k}, return the total number of contiguous subarrays whose sum equals {@code k}. The array may contain negative numbers.
 *
 * <p><b>Algorithm Strategy (Prefix Sum with a HashMap):</b>
 * <ol>
 *   <li>Let {@code prefix[i]} be the sum of the first {@code i} elements. A subarray {@code (j, i]} sums to {@code k} exactly when {@code prefix[i] - prefix[j] = k}.</li>
 *   <li>Walk through the array keeping the running sum and a map from "prefix sum value" to "how many times it has occurred". Start the map with {@code {0: 1}} so that subarrays starting at index 0 are counted.</li>
 *   <li>At each element add {@code map[runningSum - k]} to the answer: that is the number of earlier positions that make a subarray ending here sum to {@code k}.</li>
 *   <li>Then record the current running sum in the map. A sliding window does not work here because negative numbers break the "grow or shrink" monotonicity.</li>
 * </ol>
 *
 * <p><b>Example walkthrough</b>
 * <ul>
 *   <li>For {@code [1, 2, 3]} with {@code k = 3}: running sums are 1, 3, 6.</li>
 *   <li>At sum 3 the map holds 0, so {@code 3 - 3 = 0} gives one match ({@code [1, 2]}). At sum 6, {@code 6 - 3 = 3} is in the map once ({@code [3]}).</li>
 *   <li>Result: {@code 2}.</li>
 * </ul>
 *
 * <p><b>Complexity Analysis:</b>
 * <ul>
 *   <li><b>Time Complexity:</b> {@code O(n)} — One pass with constant-time map operations.</li>
 *   <li><b>Space Complexity:</b> {@code O(n)} — The map holds up to {@code n + 1} prefix sums.</li>
 * </ul>
 *
 * @author Ranga Reddy
 */
public class SubarraySumEqualsK {

    public static void main(String[] args) {
        int[][] inputs = {{1, 1, 1}, {1, 2, 3}, {1, -1, 0}, {3, 4, 7, 2, -3, 1, 4, 2}};
        int[] ks = {2, 3, 0, 7};
        for (int i = 0; i < inputs.length; i++) {
            System.out.println("Input  : nums = " + Arrays.toString(inputs[i]) + ", k = " + ks[i]);
            System.out.println("Output : " + subarraySum(inputs[i], ks[i]));
            System.out.println();
        }
    }

    public static int subarraySum(int[] nums, int k) {
        Map<Integer, Integer> seen = new HashMap<>();
        seen.put(0, 1);
        int sum = 0;
        int count = 0;
        for (int x : nums) {
            sum += x;
            count += seen.getOrDefault(sum - k, 0);
            seen.merge(sum, 1, Integer::sum);
        }
        return count;
    }
}
