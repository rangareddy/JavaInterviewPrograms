package com.ranga.search;

import java.util.Arrays;

/**
 * Given an array of integers {@code nums} and an integer {@code threshold}, choose a positive integer divisor, divide every element by it (rounding up), and sum the results. Return the smallest divisor such that this sum is less than or equal to {@code threshold}.
 *
 * <p><b>Algorithm Strategy (Binary Search on the Divisor):</b>
 * <ol>
 *   <li>The divisor lies between 1 and the largest element of {@code nums} (at that divisor every term is 1).</li>
 *   <li>For a candidate divisor {@code d}, compute {@code sum(ceil(x / d))}.</li>
 *   <li>The sum decreases as {@code d} grows, so if the sum is within the threshold, try a smaller divisor ({@code hi = mid}); otherwise {@code lo = mid + 1}.</li>
 *   <li>When {@code lo == hi}, that divisor is the answer.</li>
 * </ol>
 *
 * <p><b>Example walkthrough</b>
 * <ul>
 *   <li>For {@code [1, 2, 5, 9]} and {@code threshold = 6}: divisor 5 gives {@code 1 + 1 + 1 + 2 = 5 <= 6}, divisor 4 gives {@code 1 + 1 + 2 + 3 = 7 > 6}.</li>
 *   <li>Result: {@code 5}.</li>
 * </ul>
 *
 * <p><b>Complexity Analysis:</b>
 * <ul>
 *   <li><b>Time Complexity:</b> {@code O(n log max)} — A binary search over the divisor range with an O(n) sum per step.</li>
 *   <li><b>Space Complexity:</b> {@code O(1)} — Only counters.</li>
 * </ul>
 *
 * @author Ranga Reddy
 */
public class SmallestDivisorGivenAThreshold {

    public static void main(String[] args) {
        int[][] nums = {{1, 2, 5, 9}, {44, 22, 33, 11, 1}, {2, 3, 5, 7, 11}, {19}};
        int[] thresholds = {6, 5, 11, 5};
        for (int i = 0; i < nums.length; i++) {
            System.out.println("Input  : nums = " + Arrays.toString(nums[i]) + ", threshold = " + thresholds[i]);
            System.out.println("Output : " + smallestDivisor(nums[i], thresholds[i]));
            System.out.println();
        }
    }

    public static int smallestDivisor(int[] nums, int threshold) {
        if (nums == null || nums.length == 0) {
            return 0;
        }
        int lo = 1;
        int hi = 0;
        for (int x : nums) {
            hi = Math.max(hi, x);
        }
        while (lo < hi) {
            int mid = lo + (hi - lo) / 2;
            long sum = 0;
            for (int x : nums) {
                sum += (x + mid - 1) / mid;
            }
            if (sum <= threshold) {
                hi = mid;
            } else {
                lo = mid + 1;
            }
        }
        return lo;
    }
}
