package com.ranga.greedy;

import java.util.Arrays;

/**
 * Given a list of non-negative integers {@code nums}, arrange them so that they form the largest possible number, and return it as a string (the result can be very large).
 *
 * <p><b>Algorithm Strategy (Sort by Concatenation Order):</b>
 * <ol>
 *   <li>Convert every number to a string.</li>
 *   <li>Sort with the comparator {@code (a, b) -> (b + a).compareTo(a + b)}: put {@code a} before {@code b} when {@code a + b} is larger than {@code b + a}.</li>
 *   <li>If the largest element after sorting is {@code "0"}, the whole answer is {@code "0"}.</li>
 *   <li>Otherwise concatenate the sorted strings.</li>
 * </ol>
 *
 * <p><b>Example walkthrough</b>
 * <ul>
 *   <li>For {@code [3, 30, 34, 5, 9]}: {@code 9} beats everything, {@code 5} beats {@code 34}, {@code 34} beats {@code 3}, and {@code 3} beats {@code 30} because {@code 330 > 303}.</li>
 *   <li>Result: {@code 9534330}.</li>
 * </ul>
 *
 * <p><b>Complexity Analysis:</b>
 * <ul>
 *   <li><b>Time Complexity:</b> {@code O(n log n * L)} — Sorting with comparisons that cost O(L) each.</li>
 *   <li><b>Space Complexity:</b> {@code O(n * L)} — The strings.</li>
 * </ul>
 *
 * @author Ranga Reddy
 */
public class LargestNumber {

    public static void main(String[] args) {
        int[][] inputs = {{3, 30, 34, 5, 9}, {10, 2}, {0, 0}, {1}, {824, 8247}};
        for (int[] nums : inputs) {
            System.out.println("Input  : nums = " + Arrays.toString(nums));
            System.out.println("Output : " + largestNumber(nums));
            System.out.println();
        }
    }

    public static String largestNumber(int[] nums) {
        if (nums == null || nums.length == 0) {
            return "";
        }
        String[] parts = new String[nums.length];
        for (int i = 0; i < nums.length; i++) {
            parts[i] = String.valueOf(nums[i]);
        }
        Arrays.sort(parts, (a, b) -> (b + a).compareTo(a + b));
        if (parts[0].equals("0")) {
            return "0";
        }
        StringBuilder sb = new StringBuilder();
        for (String p : parts) {
            sb.append(p);
        }
        return sb.toString();
    }
}
