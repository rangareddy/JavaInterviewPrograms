package com.ranga.array;

import java.util.Arrays;
import java.util.OptionalInt;

/**
 * Find the second largest distinct value of an integer array in a single pass, or report that it does not exist (fewer than two distinct values).
 *
 * <p><b>Algorithm Strategy (Single Pass Tracking the Two Largest Values):</b>
 * <ol>
 *   <li>Keep two variables, {@code largest} and {@code second}, both initialised to "nothing yet" ({@code Long.MIN_VALUE} is used so that {@code Integer.MIN_VALUE} remains a valid element).</li>
 *   <li>For every element: if it is greater than {@code largest}, the old largest becomes {@code second} and the element becomes the new {@code largest}.</li>
 *   <li>Otherwise, if it is greater than {@code second} and different from {@code largest}, it becomes the new {@code second}. Equal values are skipped so that duplicates are not counted twice.</li>
 *   <li>If {@code second} was never set, there is no second largest value.</li>
 * </ol>
 *
 * <p><b>Example walkthrough</b>
 * <ul>
 *   <li>For {@code [12, 35, 1, 10, 34, 1]}: the pass ends with {@code largest = 35} and {@code second = 34}.</li>
 *   <li>For {@code [7, 7, 7]} there is only one distinct value, so the answer does not exist.</li>
 * </ul>
 *
 * <p><b>Complexity Analysis:</b>
 * <ul>
 *   <li><b>Time Complexity:</b> {@code O(n)} — One pass.</li>
 *   <li><b>Space Complexity:</b> {@code O(1)} — Two variables.</li>
 * </ul>
 *
 * @author Ranga Reddy
 */
public class SecondLargestElement {

    public static void main(String[] args) {
        int[][] inputs = {{12, 35, 1, 10, 34, 1}, {10, 5, 10}, {7, 7, 7}, {3}, {-5, -1, -9}};
        for (int[] nums : inputs) {
            System.out.println("Input  : nums = " + Arrays.toString(nums));
            OptionalInt second = secondLargest(nums);
            System.out.println("Output : " + (second.isPresent() ? String.valueOf(second.getAsInt()) : "does not exist"));
            System.out.println();
        }
    }

    public static OptionalInt secondLargest(int[] nums) {
        long largest = Long.MIN_VALUE;
        long second = Long.MIN_VALUE;
        for (int num : nums) {
            if (num > largest) {
                second = largest;
                largest = num;
            } else if (num > second && num != largest) {
                second = num;
            }
        }
        return second == Long.MIN_VALUE ? OptionalInt.empty() : OptionalInt.of((int) second);
    }
}
