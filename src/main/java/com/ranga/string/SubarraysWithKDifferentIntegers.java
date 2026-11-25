package com.ranga.string;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

/**
 * Given an integer array {@code nums} and an integer {@code k}, return the number of good subarrays: contiguous subarrays that contain exactly {@code k} different integers.
 *
 * <p><b>Algorithm Strategy (Count at Most K, Subtract at Most K - 1):</b>
 * <ol>
 *   <li>Counting subarrays with exactly {@code k} distinct values directly is awkward, but counting those with at most {@code k} is easy with a sliding window.</li>
 *   <li>{@code atMost(k)}: extend {@code right}, count values in a map; while there are more than {@code k} distinct values, shrink from the left. Add {@code right - left + 1} (all subarrays ending at {@code right}).</li>
 *   <li>Exactly {@code k} = {@code atMost(k) - atMost(k - 1)}.</li>
 *   <li>Return that difference.</li>
 * </ol>
 *
 * <p><b>Example walkthrough</b>
 * <ul>
 *   <li>For {@code [1,2,1,2,3]} and {@code k = 2}: there are 12 subarrays with at most 2 distinct values and 5 with at most 1.</li>
 *   <li>Result: {@code 12 - 5 = 7}.</li>
 * </ul>
 *
 * <p><b>Complexity Analysis:</b>
 * <ul>
 *   <li><b>Time Complexity:</b> {@code O(n)} — Two linear window passes.</li>
 *   <li><b>Space Complexity:</b> {@code O(k)} — The count map.</li>
 * </ul>
 *
 * @author Ranga Reddy
 */
public class SubarraysWithKDifferentIntegers {

    public static void main(String[] args) {
        int[][] nums = {{1, 2, 1, 2, 3}, {1, 2, 1, 3, 4}, {2, 2, 2}, {1}};
        int[] ks = {2, 3, 1, 1};
        for (int i = 0; i < nums.length; i++) {
            System.out.println("Input  : nums = " + Arrays.toString(nums[i]) + ", k = " + ks[i]);
            System.out.println("Output : " + subarraysWithKDistinct(nums[i], ks[i]));
            System.out.println();
        }
    }

    public static int subarraysWithKDistinct(int[] nums, int k) {
        if (nums == null || k <= 0) {
            return 0;
        }
        return atMost(nums, k) - atMost(nums, k - 1);
    }

    private static int atMost(int[] nums, int k) {
        Map<Integer, Integer> counts = new HashMap<>();
        int left = 0;
        int total = 0;
        for (int right = 0; right < nums.length; right++) {
            counts.merge(nums[right], 1, Integer::sum);
            while (counts.size() > k) {
                if (counts.merge(nums[left], -1, Integer::sum) == 0) {
                    counts.remove(nums[left]);
                }
                left++;
            }
            total += right - left + 1;
        }
        return total;
    }
}
