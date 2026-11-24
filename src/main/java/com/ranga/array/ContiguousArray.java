package com.ranga.array;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

/**
 * Given a binary array {@code nums}, return the maximum length of a contiguous subarray with an equal number of {@code 0} and {@code 1}.
 *
 * <p><b>Algorithm Strategy (Balance Prefix with First Occurrence):</b>
 * <ol>
 *   <li>Treat each {@code 0} as -1 and each {@code 1} as +1, and keep a running {@code balance}.</li>
 *   <li>A subarray with equal zeros and ones has total change 0, so its start and end prefix balances are equal.</li>
 *   <li>Store the first index at which each balance appears in a map (the balance 0 appears at index -1).</li>
 *   <li>Whenever the current balance was seen before at index {@code j}, the subarray {@code (j, i]} is balanced: update the best length with {@code i - j}.</li>
 * </ol>
 *
 * <p><b>Example walkthrough</b>
 * <ul>
 *   <li>For {@code [0, 1]}: the balances are -1, 0; balance 0 was seen at index -1, so the length is {@code 1 - (-1) = 2}.</li>
 *   <li>Result: {@code 2}.</li>
 * </ul>
 *
 * <p><b>Complexity Analysis:</b>
 * <ul>
 *   <li><b>Time Complexity:</b> {@code O(n)} — One pass.</li>
 *   <li><b>Space Complexity:</b> {@code O(n)} — The map.</li>
 * </ul>
 *
 * @author Ranga Reddy
 */
public class ContiguousArray {

    public static void main(String[] args) {
        int[][] inputs = {{0, 1}, {0, 1, 0}, {0, 0, 1, 0, 0, 0, 1, 1}, {1, 1, 1}};
        for (int[] nums : inputs) {
            System.out.println("Input  : nums = " + Arrays.toString(nums));
            System.out.println("Output : " + findMaxLength(nums));
            System.out.println();
        }
    }

    public static int findMaxLength(int[] nums) {
        if (nums == null) {
            return 0;
        }
        Map<Integer, Integer> firstIndex = new HashMap<>();
        firstIndex.put(0, -1);
        int balance = 0;
        int best = 0;
        for (int i = 0; i < nums.length; i++) {
            balance += nums[i] == 1 ? 1 : -1;
            Integer seen = firstIndex.putIfAbsent(balance, i);
            if (seen != null) {
                best = Math.max(best, i - seen);
            }
        }
        return best;
    }
}
