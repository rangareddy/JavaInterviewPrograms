package com.ranga.array;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Given an array {@code nums} of {@code n} integers where {@code nums[i]} is in the range {@code [1, n]}, return all the integers in the range {@code [1, n]} that do not appear in {@code nums}.
 *
 * <p><b>Algorithm Strategy (In-Place Marking by Sign):</b>
 * <ol>
 *   <li>Every value {@code v} is a valid index {@code v - 1}, so the array itself can record which values have been seen.</li>
 *   <li>For each element, take its absolute value {@code v} and make {@code nums[v - 1]} negative (if it is not already negative).</li>
 *   <li>After the pass, an index {@code i} that still holds a positive number means the value {@code i + 1} never appeared.</li>
 *   <li>Collect {@code i + 1} for every positive position. The input array is modified; restore the signs if the caller needs it back.</li>
 * </ol>
 *
 * <p><b>Example walkthrough</b>
 * <ul>
 *   <li>For {@code [4, 3, 2, 7, 8, 2, 3, 1]}: marking turns it into {@code [-4, -3, -2, -7, 8, 2, -3, -1]}.</li>
 *   <li>Indices 4 and 5 are still positive, so the missing numbers are {@code 5} and {@code 6}.</li>
 * </ul>
 *
 * <p><b>Complexity Analysis:</b>
 * <ul>
 *   <li><b>Time Complexity:</b> {@code O(n)} — Two linear passes.</li>
 *   <li><b>Space Complexity:</b> {@code O(1)} — Not counting the output list; the marking is done in place.</li>
 * </ul>
 *
 * @author Ranga Reddy
 */
public class FindAllNumbersDisappeared {

    public static void main(String[] args) {
        int[][] inputs = {{4, 3, 2, 7, 8, 2, 3, 1}, {1, 1}, {1, 2, 3}, {2, 2, 2}};
        for (int[] nums : inputs) {
            System.out.println("Input  : nums = " + Arrays.toString(nums));
            System.out.println("Output : " + findDisappearedNumbers(nums.clone()));
            System.out.println();
        }
    }

    public static List<Integer> findDisappearedNumbers(int[] nums) {
        for (int i = 0; i < nums.length; i++) {
            int index = Math.abs(nums[i]) - 1;
            if (nums[index] > 0) {
                nums[index] = -nums[index];
            }
        }
        List<Integer> missing = new ArrayList<>();
        for (int i = 0; i < nums.length; i++) {
            if (nums[i] > 0) {
                missing.add(i + 1);
            }
        }
        return missing;
    }
}
