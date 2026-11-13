package com.ranga.array;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Given an integer array {@code nums} and a {@code target}, return all the unique quadruplets {@code [nums[a], nums[b], nums[c], nums[d]]} with distinct indices such that their sum equals {@code target}.
 *
 * <p><b>Algorithm Strategy (Sort, Fix Two Numbers, Two Pointers for the Rest):</b>
 * <ol>
 *   <li>Sort the array so equal values are adjacent and a two-pointer scan can move by comparing the sum with the target.</li>
 *   <li>Fix the first number with index {@code i} and the second with index {@code j > i}. Skip a value that equals the previous choice at the same level; that is what removes duplicate quadruplets.</li>
 *   <li>For the remaining two numbers use {@code left = j + 1} and {@code right = n - 1}. If the sum is too small move {@code left}, if too large move {@code right}, otherwise record the quadruplet and skip the duplicates on both sides.</li>
 *   <li>Sum with {@code long} arithmetic: four values near {@code Integer.MAX_VALUE} overflow an {@code int}.</li>
 * </ol>
 *
 * <p><b>Example walkthrough</b>
 * <ul>
 *   <li>For {@code [1, 0, -1, 0, -2, 2]} with {@code target = 0}, sorted: {@code [-2, -1, 0, 0, 1, 2]}.</li>
 *   <li>Result: {@code [[-2, -1, 1, 2], [-2, 0, 0, 2], [-1, 0, 0, 1]]}.</li>
 * </ul>
 *
 * <p><b>Complexity Analysis:</b>
 * <ul>
 *   <li><b>Time Complexity:</b> {@code O(n^3)} — Two nested loops for the fixed numbers and a linear two-pointer scan inside.</li>
 *   <li><b>Space Complexity:</b> {@code O(1)} — Besides the sort and the output list.</li>
 * </ul>
 *
 * @author Ranga Reddy
 */
public class FourSum {

    public static void main(String[] args) {
        int[][] inputs = {{1, 0, -1, 0, -2, 2}, {2, 2, 2, 2, 2}, {1000000000, 1000000000, 1000000000, 1000000000}};
        int[] targets = {0, 8, -294967296};
        for (int i = 0; i < inputs.length; i++) {
            System.out.println("Input  : nums = " + Arrays.toString(inputs[i]) + ", target = " + targets[i]);
            System.out.println("Output : " + fourSum(inputs[i], targets[i]));
            System.out.println();
        }
    }

    public static List<List<Integer>> fourSum(int[] nums, int target) {
        List<List<Integer>> result = new ArrayList<>();
        int[] sorted = nums.clone();
        Arrays.sort(sorted);
        int n = sorted.length;
        for (int i = 0; i < n - 3; i++) {
            if (i > 0 && sorted[i] == sorted[i - 1]) continue;
            for (int j = i + 1; j < n - 2; j++) {
                if (j > i + 1 && sorted[j] == sorted[j - 1]) continue;
                int left = j + 1;
                int right = n - 1;
                while (left < right) {
                    long sum = (long) sorted[i] + sorted[j] + sorted[left] + sorted[right];
                    if (sum < target) {
                        left++;
                    } else if (sum > target) {
                        right--;
                    } else {
                        result.add(Arrays.asList(sorted[i], sorted[j], sorted[left], sorted[right]));
                        left++;
                        right--;
                        while (left < right && sorted[left] == sorted[left - 1]) left++;
                        while (left < right && sorted[right] == sorted[right + 1]) right--;
                    }
                }
            }
        }
        return result;
    }
}
