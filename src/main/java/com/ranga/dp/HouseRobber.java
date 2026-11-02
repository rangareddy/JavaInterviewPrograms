package com.ranga.dp;

import java.util.Arrays;

/**
 * Houses along a street hold some amount of money, but adjacent houses have connected alarms. Given the amounts, return the maximum money that can be robbed without robbing two adjacent houses.
 *
 * <p><b>Algorithm Strategy (Dynamic Programming (Take or Skip)):</b>
 * <ol>
 *   <li>At house {@code i} there are two choices: skip it (keep the best of the first {@code i - 1} houses) or rob it (its money plus the best of the first {@code i - 2} houses).</li>
 *   <li>So {@code best(i) = max(best(i - 1), best(i - 2) + nums[i])}.</li>
 *   <li>Only the last two results are required, so keep them in two variables {@code skip} and {@code take}.</li>
 *   <li>After the last house, the larger value is the answer.</li>
 * </ol>
 *
 * <p><b>Example walkthrough</b>
 * <ul>
 *   <li>For {@code [2, 7, 9, 3, 1]}: best values per house are 2, 7, 11, 11, 12.</li>
 *   <li>Result: {@code 12} (houses with 2, 9 and 1).</li>
 * </ul>
 *
 * <p><b>Complexity Analysis:</b>
 * <ul>
 *   <li><b>Time Complexity:</b> {@code O(n)} — A single pass.</li>
 *   <li><b>Space Complexity:</b> {@code O(1)} — Two variables.</li>
 * </ul>
 *
 * @author Ranga Reddy
 */
public class HouseRobber {

    public static void main(String[] args) {
        int[][] inputs = {{1, 2, 3, 1}, {2, 7, 9, 3, 1}, {5}, {}};
        for (int[] nums : inputs) {
            System.out.println("Input  : nums = " + Arrays.toString(nums));
            System.out.println("Output : " + rob(nums));
            System.out.println();
        }
    }

    public static int rob(int[] nums) {
        if (nums == null || nums.length == 0) {
            return 0;
        }

        int prev2 = 0;      // best up to house i - 2
        int prev1 = 0;      // best up to house i - 1
        for (int money : nums) {
            int best = Math.max(prev1, prev2 + money);
            prev2 = prev1;
            prev1 = best;
        }
        return prev1;
    }
}
