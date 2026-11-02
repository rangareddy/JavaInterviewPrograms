package com.ranga.blind75.array;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

/**
 * Given an integer array {@code nums}, return {@code true} if any value appears at least twice, and {@code false} if every element is distinct.
 *
 * <p><b>Algorithm Strategy (Hash Set of Seen Values):</b>
 * <ol>
 *   <li>Create an empty {@code HashSet} that stores the values seen so far.</li>
 *   <li>For each number, try to add it to the set. {@code Set.add} returns {@code false} when the value is already present.</li>
 *   <li>If the add fails, a duplicate exists, so return {@code true} immediately.</li>
 *   <li>If the loop ends without a failed add, all values are distinct, so return {@code false}.</li>
 * </ol>
 *
 * <p><b>Example walkthrough</b>
 * <ul>
 *   <li>For {@code [1, 2, 3, 1]}: 1, 2 and 3 are added; the second 1 cannot be added, so the result is {@code true}.</li>
 * </ul>
 *
 * <p><b>Complexity Analysis:</b>
 * <ul>
 *   <li><b>Time Complexity:</b> {@code O(n)} — Each element is inserted into the set at most once ({@code O(1)} average).</li>
 *   <li><b>Space Complexity:</b> {@code O(n)} — The set may hold every element.</li>
 * </ul>
 *
 * @author Ranga Reddy
 */
public class ContainsDuplicate {

    public static void main(String[] args) {
        int[][] inputs = {{1, 2, 3, 1}, {1, 2, 3, 4}, {1, 1, 1, 3, 3, 4, 3, 2, 4, 2}};
        for (int[] nums : inputs) {
            System.out.println("Input  : nums = " + Arrays.toString(nums));
            System.out.println("Output : " + containsDuplicate(nums));
            System.out.println();
        }
    }

    public static boolean containsDuplicate(int[] nums) {
        if (nums == null || nums.length < 2) {
            return false;
        }

        Set<Integer> seen = new HashSet<>();
        for (int num : nums) {
            if (!seen.add(num)) {
                return true;
            }
        }
        return false;
    }
}
