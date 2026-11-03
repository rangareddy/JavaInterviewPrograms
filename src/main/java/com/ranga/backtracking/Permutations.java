package com.ranga.backtracking;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Given an array {@code nums} of distinct integers, return all possible permutations in any order.
 *
 * <p><b>Algorithm Strategy (Backtracking with a Used Array):</b>
 * <ol>
 *   <li>Build a permutation position by position in a list {@code current}; a boolean array {@code used} remembers which numbers are already in it.</li>
 *   <li>At each position try every number that is not used: mark it, append it, recurse for the next position, then unmark and remove it (backtrack).</li>
 *   <li>When {@code current} contains all {@code n} numbers it is a complete permutation: record a copy.</li>
 *   <li>There are {@code n!} permutations; the number of choices shrinks by one at every level ({@code n}, {@code n-1}, ...).</li>
 * </ol>
 *
 * <p><b>Example walkthrough</b>
 * <ul>
 *   <li>For {@code [1, 2, 3]}: choose 1, then 2, then 3 {@code -> [1,2,3]}; backtrack and choose 3 then 2 {@code -> [1,3,2]}; then start with 2, and so on.</li>
 *   <li>Result: 6 permutations.</li>
 * </ul>
 *
 * <p><b>Complexity Analysis:</b>
 * <ul>
 *   <li><b>Time Complexity:</b> {@code O(n * n!)} — {@code n!} permutations, each copied in {@code O(n)}.</li>
 *   <li><b>Space Complexity:</b> {@code O(n)} — Recursion depth plus the {@code used} array (excluding the output).</li>
 * </ul>
 *
 * @author Ranga Reddy
 */
public class Permutations {

    public static void main(String[] args) {
        int[][] inputs = {{1, 2, 3}, {0, 1}, {1}, {}};
        for (int[] nums : inputs) {
            System.out.println("Input  : nums = " + Arrays.toString(nums));
            System.out.println("Output : " + permute(nums));
            System.out.println();
        }
    }

    public static List<List<Integer>> permute(int[] nums) {
        List<List<Integer>> result = new ArrayList<>();
        if (nums.length == 0) {
            return result;
        }
        backtrack(nums, new boolean[nums.length], new ArrayList<>(), result);
        return result;
    }

    private static void backtrack(int[] nums, boolean[] used, List<Integer> current, List<List<Integer>> result) {
        if (current.size() == nums.length) {
            result.add(new ArrayList<>(current));
            return;
        }
        for (int i = 0; i < nums.length; i++) {
            if (used[i]) {
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
