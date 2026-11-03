package com.ranga.backtracking;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Given an integer array {@code nums} that may contain duplicates, return all possible subsets (the power set). The solution set must not contain duplicate subsets.
 *
 * <p><b>Algorithm Strategy (Sort, Then Skip Equal Elements at the Same Level):</b>
 * <ol>
 *   <li>Sort the array so that equal numbers are adjacent.</li>
 *   <li>Use the same backtracking as for Subsets: record the current subset, then extend it with the elements {@code start .. n - 1}.</li>
 *   <li>To avoid duplicates, inside one loop skip an element that equals the previous one ({@code i > start && nums[i] == nums[i - 1]}): choosing the same value twice at the same position would build the same subset again.</li>
 *   <li>An equal value is still allowed at a deeper level (a later position), so subsets such as {@code [2, 2]} are produced.</li>
 * </ol>
 *
 * <p><b>Example walkthrough</b>
 * <ul>
 *   <li>For {@code [1, 2, 2]}: the subsets are {@code []}, {@code [1]}, {@code [1,2]}, {@code [1,2,2]}, {@code [2]}, {@code [2,2]}.</li>
 *   <li>The second 2 is skipped as a first choice at each level, which avoids the duplicates {@code [1,2]} and {@code [2]}.</li>
 * </ul>
 *
 * <p><b>Complexity Analysis:</b>
 * <ul>
 *   <li><b>Time Complexity:</b> {@code O(n * 2^n)} — At most {@code 2^n} subsets, each copied in {@code O(n)}.</li>
 *   <li><b>Space Complexity:</b> {@code O(n)} — Recursion depth (excluding the output).</li>
 * </ul>
 *
 * @author Ranga Reddy
 */
public class SubsetsII {

    public static void main(String[] args) {
        int[][] inputs = {{1, 2, 2}, {0}, {4, 4, 4, 1, 4}, {2, 1, 2}};
        for (int[] nums : inputs) {
            System.out.println("Input  : nums = " + Arrays.toString(nums));
            System.out.println("Output : " + subsetsWithDup(nums));
            System.out.println();
        }
    }

    public static List<List<Integer>> subsetsWithDup(int[] nums) {
        int[] sorted = nums.clone();
        Arrays.sort(sorted);
        List<List<Integer>> result = new ArrayList<>();
        backtrack(sorted, 0, new ArrayList<>(), result);
        return result;
    }

    private static void backtrack(int[] nums, int start, List<Integer> current, List<List<Integer>> result) {
        result.add(new ArrayList<>(current));
        for (int i = start; i < nums.length; i++) {
            if (i > start && nums[i] == nums[i - 1]) {
                continue;                                   // same value already tried at this position
            }
            current.add(nums[i]);
            backtrack(nums, i + 1, current, result);
            current.remove(current.size() - 1);
        }
    }
}
