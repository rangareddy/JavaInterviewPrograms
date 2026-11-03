package com.ranga.backtracking;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Given an integer array {@code nums} of unique elements, return all possible subsets (the power set). The solution set must not contain duplicate subsets.
 *
 * <p><b>Algorithm Strategy (Backtracking: Add the Current Subset, Then Extend It):</b>
 * <ol>
 *   <li>Build subsets incrementally in a list {@code current}. Every state of {@code current} is itself a valid subset, so record a copy of it at the start of each call.</li>
 *   <li>To extend the subset, loop over the remaining elements {@code start .. n - 1}: add the element, recurse with {@code start = i + 1}, then remove the element again (backtrack).</li>
 *   <li>Only moving forward ({@code i + 1}) guarantees that each subset is generated exactly once, in the order of the original array.</li>
 *   <li>There are {@code 2^n} subsets in total.</li>
 * </ol>
 *
 * <p><b>Example walkthrough</b>
 * <ul>
 *   <li>For {@code [1, 2, 3]} the recursion records {@code []}, {@code [1]}, {@code [1,2]}, {@code [1,2,3]}, {@code [1,3]}, {@code [2]}, {@code [2,3]}, {@code [3]}.</li>
 *   <li>That is the power set with 8 subsets.</li>
 * </ul>
 *
 * <p><b>Complexity Analysis:</b>
 * <ul>
 *   <li><b>Time Complexity:</b> {@code O(n * 2^n)} — {@code 2^n} subsets, each copied in {@code O(n)}.</li>
 *   <li><b>Space Complexity:</b> {@code O(n)} — Recursion depth and the current subset (excluding the output).</li>
 * </ul>
 *
 * @author Ranga Reddy
 */
public class Subsets {

    public static void main(String[] args) {
        int[][] inputs = {{1, 2, 3}, {0}, {}, {5, 7}};
        for (int[] nums : inputs) {
            System.out.println("Input  : nums = " + Arrays.toString(nums));
            System.out.println("Output : " + subsets(nums));
            System.out.println();
        }
    }

    public static List<List<Integer>> subsets(int[] nums) {
        List<List<Integer>> result = new ArrayList<>();
        backtrack(nums, 0, new ArrayList<>(), result);
        return result;
    }

    private static void backtrack(int[] nums, int start, List<Integer> current, List<List<Integer>> result) {
        result.add(new ArrayList<>(current));
        for (int i = start; i < nums.length; i++) {
            current.add(nums[i]);
            backtrack(nums, i + 1, current, result);
            current.remove(current.size() - 1);
        }
    }
}
