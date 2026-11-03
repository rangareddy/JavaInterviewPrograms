package com.ranga.backtracking;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Given a collection of candidate numbers (which may contain duplicates) and a {@code target}, find all unique combinations of candidates that sum to the target. Each number may be used at most once in a combination, and the solution set must not contain duplicate combinations.
 *
 * <p><b>Algorithm Strategy (Sorted Backtracking with Duplicate Skipping and Pruning):</b>
 * <ol>
 *   <li>Sort the candidates: equal numbers become adjacent and the loop can stop early once a number exceeds the remaining target.</li>
 *   <li>Backtrack with a start index and the remaining target; when the remaining target is 0 record a copy of the current combination.</li>
 *   <li>Every candidate is used at most once, so recurse with {@code i + 1}. To avoid duplicate combinations, skip a candidate equal to the previous one at the same level ({@code i > start && candidates[i] == candidates[i - 1]}).</li>
 *   <li>Break the loop when {@code candidates[i] > remaining}: the following numbers are even larger.</li>
 * </ol>
 *
 * <p><b>Example walkthrough</b>
 * <ul>
 *   <li>For {@code [10, 1, 2, 7, 6, 1, 5]} and target 8 (sorted {@code [1, 1, 2, 5, 6, 7, 10]}): {@code [1,1,6]}, {@code [1,2,5]}, {@code [1,7]}, {@code [2,6]}.</li>
 *   <li>The second 1 is only used inside {@code [1,1,6]}, never as an alternative first choice.</li>
 * </ul>
 *
 * <p><b>Complexity Analysis:</b>
 * <ul>
 *   <li><b>Time Complexity:</b> {@code O(2^n)} — Each candidate is either taken or skipped; pruning cuts most branches.</li>
 *   <li><b>Space Complexity:</b> {@code O(n)} — Recursion depth (excluding the output).</li>
 * </ul>
 *
 * @author Ranga Reddy
 */
public class CombinationSumII {

    public static void main(String[] args) {
        int[][] candidates = {{10, 1, 2, 7, 6, 1, 5}, {2, 5, 2, 1, 2}, {1, 1, 1}, {3, 4}};
        int[] targets = {8, 5, 2, 2};
        for (int i = 0; i < candidates.length; i++) {
            System.out.println("Input  : candidates = " + Arrays.toString(candidates[i]) + ", target = " + targets[i]);
            System.out.println("Output : " + combinationSum2(candidates[i], targets[i]));
            System.out.println();
        }
    }

    public static List<List<Integer>> combinationSum2(int[] candidates, int target) {
        int[] sorted = candidates.clone();
        Arrays.sort(sorted);
        List<List<Integer>> result = new ArrayList<>();
        backtrack(sorted, target, 0, new ArrayList<>(), result);
        return result;
    }

    private static void backtrack(int[] candidates, int remaining, int start, List<Integer> current, List<List<Integer>> result) {
        if (remaining == 0) {
            result.add(new ArrayList<>(current));
            return;
        }
        for (int i = start; i < candidates.length; i++) {
            if (candidates[i] > remaining) {
                break;                                      // all following numbers are larger
            }
            if (i > start && candidates[i] == candidates[i - 1]) {
                continue;                                   // skip duplicates at the same level
            }
            current.add(candidates[i]);
            backtrack(candidates, remaining - candidates[i], i + 1, current, result);
            current.remove(current.size() - 1);
        }
    }
}
