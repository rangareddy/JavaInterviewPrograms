package com.ranga.sort;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Given a 0-indexed array of positive integers {@code nums} and a positive integer {@code limit}, you may swap {@code nums[i]} and {@code nums[j]} any number of times as long as {@code |nums[i] - nums[j]| <= limit}. Return the lexicographically smallest array you can get.
 *
 * <p><b>Algorithm Strategy (Group by Value Chains, Refill in Index Order):</b>
 * <ol>
 *   <li>Sort the indexes by value. Walk the sorted values and start a new group whenever the gap to the previous value exceeds {@code limit} (values within a group can reach one another through chains of swaps).</li>
 *   <li>For each group, collect its values (already sorted) and the original indexes of those values.</li>
 *   <li>Sort the indexes of the group and assign the sorted values to them in order.</li>
 *   <li>Smaller values go to smaller indexes inside each group, which is the best possible.</li>
 * </ol>
 *
 * <p><b>Example walkthrough</b>
 * <ul>
 *   <li>For {@code [1, 5, 3, 9, 8]} and {@code limit = 2}: sorted values 1, 3, 5, 8, 9 form two groups (1, 3, 5) and (8, 9).</li>
 *   <li>Result: {@code [1, 3, 5, 8, 9]}.</li>
 * </ul>
 *
 * <p><b>Complexity Analysis:</b>
 * <ul>
 *   <li><b>Time Complexity:</b> {@code O(n log n)} — Sorting.</li>
 *   <li><b>Space Complexity:</b> {@code O(n)} — The index array and groups.</li>
 * </ul>
 *
 * @author Ranga Reddy
 */
public class MakeLexicographicallySmallestArrayBySwappingElements {

    public static void main(String[] args) {
        int[][] nums = {{1, 5, 3, 9, 8}, {1, 7, 6, 18, 2, 1}, {1, 7, 28, 19, 10}, {5}};
        int[] limits = {2, 3, 3, 1};
        for (int i = 0; i < nums.length; i++) {
            System.out.println("Input  : nums = " + Arrays.toString(nums[i]) + ", limit = " + limits[i]);
            System.out.println("Output : " + Arrays.toString(lexicographicallySmallestArray(nums[i].clone(), limits[i])));
            System.out.println();
        }
    }

    public static int[] lexicographicallySmallestArray(int[] nums, int limit) {
        if (nums == null || nums.length == 0) {
            return new int[0];
        }
        int n = nums.length;
        Integer[] order = new Integer[n];
        for (int i = 0; i < n; i++) {
            order[i] = i;
        }
        Arrays.sort(order, (a, b) -> nums[a] - nums[b]);
        int[] result = new int[n];
        int start = 0;
        while (start < n) {
            int end = start + 1;
            while (end < n && nums[order[end]] - nums[order[end - 1]] <= limit) {
                end++;
            }
            List<Integer> indexes = new ArrayList<>();
            for (int k = start; k < end; k++) {
                indexes.add(order[k]);
            }
            java.util.Collections.sort(indexes);
            for (int k = 0; k < indexes.size(); k++) {
                result[indexes.get(k)] = nums[order[start + k]];
            }
            start = end;
        }
        return result;
    }
}
