package com.ranga.array;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Given a sorted array of unique integers {@code nums}, return the smallest sorted list of ranges that cover all the numbers exactly. Each range {@code [a, b]} is written as {@code "a->b"} if {@code a != b} and as {@code "a"} if {@code a == b}.
 *
 * <p><b>Algorithm Strategy (Scan for Breaks in Consecutiveness):</b>
 * <ol>
 *   <li>Walk the array with an index {@code i} marking the start of a range.</li>
 *   <li>Advance {@code j} while {@code nums[j + 1] == nums[j] + 1}; use {@code long} arithmetic to avoid overflow near the integer limits.</li>
 *   <li>Add {@code nums[i]} alone when the run has one number, or {@code nums[i]->nums[j]} otherwise.</li>
 *   <li>Continue from {@code j + 1}.</li>
 * </ol>
 *
 * <p><b>Example walkthrough</b>
 * <ul>
 *   <li>For {@code [0, 1, 2, 4, 5, 7]}: runs are 0 to 2, 4 to 5 and 7.</li>
 *   <li>Result: {@code [0->2, 4->5, 7]}.</li>
 * </ul>
 *
 * <p><b>Complexity Analysis:</b>
 * <ul>
 *   <li><b>Time Complexity:</b> {@code O(n)} — Each number is visited once.</li>
 *   <li><b>Space Complexity:</b> {@code O(1)} — Apart from the output.</li>
 * </ul>
 *
 * @author Ranga Reddy
 */
public class SummaryRanges {

    public static void main(String[] args) {
        int[][] inputs = {{0, 1, 2, 4, 5, 7}, {0, 2, 3, 4, 6, 8, 9}, {}, {-1}, {Integer.MAX_VALUE - 1, Integer.MAX_VALUE}};
        for (int[] nums : inputs) {
            System.out.println("Input  : nums = " + Arrays.toString(nums));
            System.out.println("Output : " + summaryRanges(nums));
            System.out.println();
        }
    }

    public static List<String> summaryRanges(int[] nums) {
        List<String> ranges = new ArrayList<>();
        if (nums == null) {
            return ranges;
        }
        int i = 0;
        while (i < nums.length) {
            int j = i;
            while (j + 1 < nums.length && (long) nums[j + 1] == (long) nums[j] + 1) {
                j++;
            }
            ranges.add(i == j ? String.valueOf(nums[i]) : nums[i] + "->" + nums[j]);
            i = j + 1;
        }
        return ranges;
    }
}
