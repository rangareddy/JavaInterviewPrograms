package com.ranga.graph;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

/**
 * Given an unsorted array of integers, return the length of the longest sequence of consecutive values (such as 1, 2, 3, 4). The algorithm must run in {@code O(n)} time.
 *
 * <p><b>Algorithm Strategy (Hash Set and Sequence Starts):</b>
 * <ol>
 *   <li>Put all numbers into a {@code HashSet} for {@code O(1)} membership tests.</li>
 *   <li>A number is the start of a sequence only if {@code num - 1} is not in the set. Ignore all other numbers: they will be visited from their start.</li>
 *   <li>From each start, count how long {@code num + 1}, {@code num + 2}, ... keep being in the set.</li>
 *   <li>Return the longest length found. Every number is part of exactly one sequence and is walked over once, so the total work is linear.</li>
 * </ol>
 *
 * <p><b>Example walkthrough</b>
 * <ul>
 *   <li>For {@code [100, 4, 200, 1, 3, 2]}: the starts are 100, 200 and 1 (because 0 is missing).</li>
 *   <li>From 1 the sequence 1, 2, 3, 4 has length 4; from 100 and 200 the length is 1. Result: {@code 4}.</li>
 * </ul>
 *
 * <p><b>Complexity Analysis:</b>
 * <ul>
 *   <li><b>Time Complexity:</b> {@code O(n)} — Each number is counted only from the start of its sequence.</li>
 *   <li><b>Space Complexity:</b> {@code O(n)} — The hash set.</li>
 * </ul>
 *
 * @author Ranga Reddy
 */
public class LongestConsecutiveSequence {

    public static void main(String[] args) {
        int[][] inputs = {{100, 4, 200, 1, 3, 2}, {0, 3, 7, 2, 5, 8, 4, 6, 0, 1}, {}};
        for (int[] nums : inputs) {
            System.out.println("Input  : nums = " + Arrays.toString(nums));
            System.out.println("Output : " + longestConsecutive(nums));
            System.out.println();
        }
    }

    public static int longestConsecutive(int[] nums) {
        if (nums == null || nums.length == 0) {
            return 0;
        }

        Set<Integer> set = new HashSet<>();
        for (int num : nums) {
            set.add(num);
        }

        int longest = 0;
        for (int num : set) {
            if (!set.contains(num - 1)) {           // num is the start of a sequence
                int current = num;
                int length = 1;
                while (set.contains(current + 1)) {
                    current++;
                    length++;
                }
                longest = Math.max(longest, length);
            }
        }
        return longest;
    }
}
