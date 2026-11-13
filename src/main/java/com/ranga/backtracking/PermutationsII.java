package com.ranga.backtracking;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Given an array {@code nums} that may contain duplicates, return all unique permutations in any order.
 *
 * <p><b>Algorithm Strategy (Sorted Backtracking with a Duplicate Skip Rule):</b>
 * <ol>
 *   <li>Sort the array so that equal values sit next to each other.</li>
 *   <li>Build a permutation position by position, marking each chosen index in a {@code used} array.</li>
 *   <li>Skip {@code nums[i]} when it equals {@code nums[i - 1]} and {@code nums[i - 1]} is not currently used. That means the earlier twin was already tried at this position, so choosing the later one would repeat the same permutation.</li>
 *   <li>When the current permutation has {@code n} elements, add a copy to the result, then undo the last choice and continue.</li>
 * </ol>
 *
 * <p><b>Example walkthrough</b>
 * <ul>
 *   <li>For {@code [1, 1, 2]} (already sorted): the first position can be the first {@code 1}, then the second {@code 1} is allowed (its twin is used) or {@code 2}.</li>
 *   <li>Choosing the second {@code 1} first is skipped because its twin is unused, so each distinct arrangement is built once.</li>
 *   <li>Result: {@code [[1, 1, 2], [1, 2, 1], [2, 1, 1]]}.</li>
 * </ul>
 *
 * <p><b>Complexity Analysis:</b>
 * <ul>
 *   <li><b>Time Complexity:</b> {@code O(n * n!)} — In the worst case, all values distinct: {@code n!} permutations, each copied in {@code O(n)}.</li>
 *   <li><b>Space Complexity:</b> {@code O(n)} — Recursion depth, the {@code used} array and the current permutation, excluding the output.</li>
 * </ul>
 *
 * @author Ranga Reddy
 */
public class PermutationsII {

    public static void main(String[] args) {
        int[][] inputs = {{1, 1, 2}, {1, 2, 3}, {2, 2, 2}, {1}};
        for (int[] nums : inputs) {
            System.out.println("Input  : nums = " + Arrays.toString(nums));
            System.out.println("Output : " + permuteUnique(nums));
            System.out.println();
        }
    }

    public static List<List<Integer>> permuteUnique(int[] nums) {
        List<List<Integer>> result = new ArrayList<>();
        int[] sorted = nums.clone();
        Arrays.sort(sorted);
        backtrack(sorted, new boolean[sorted.length], new ArrayList<>(), result);
        return result;
    }

    private static void backtrack(int[] nums, boolean[] used, List<Integer> current, List<List<Integer>> result) {
        if (current.size() == nums.length) {
            result.add(new ArrayList<>(current));
            return;
        }
        for (int i = 0; i < nums.length; i++) {
            if (used[i] || (i > 0 && nums[i] == nums[i - 1] && !used[i - 1])) {
                continue;
            }
            used[i] = true;
            current.add(nums[i]);
            backtrack(nums, used, current, result);
            current.remove(current.size() - 1);
            used[i] = false;
        }
    }
}
