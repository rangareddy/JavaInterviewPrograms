package com.ranga.search;

/**
 * Given a positive integer {@code num}, return {@code true} if {@code num} is a perfect square, otherwise {@code false}. Do not use any built-in library function such as {@code sqrt}.
 *
 * <p><b>Algorithm Strategy (Binary Search for the Root):</b>
 * <ol>
 *   <li>If {@code num} is a perfect square, its root lies between 1 and {@code num} (and in fact at most about 46341).</li>
 *   <li>Binary search {@code mid} in {@code [1, num]}, comparing {@code mid * mid} (computed as {@code long}) with {@code num}.</li>
 *   <li>Equal means a perfect square; smaller means search the right half; larger means search the left half.</li>
 *   <li>If the range empties, there is no such root.</li>
 * </ol>
 *
 * <p><b>Example walkthrough</b>
 * <ul>
 *   <li>For {@code 16}: {@code mid = 8} gives 64, too big; {@code mid = 4} gives 16, equal.</li>
 *   <li>Result: {@code true}.</li>
 * </ul>
 *
 * <p><b>Complexity Analysis:</b>
 * <ul>
 *   <li><b>Time Complexity:</b> {@code O(log n)} — The search range halves each step.</li>
 *   <li><b>Space Complexity:</b> {@code O(1)} — A few variables.</li>
 * </ul>
 *
 * @author Ranga Reddy
 */
public class ValidPerfectSquare {

    public static void main(String[] args) {
        int[] inputs = {16, 14, 1, 808201, 2147395600, 2147483647};
        for (int num : inputs) {
            System.out.println("Input  : num = " + num);
            System.out.println("Output : " + isPerfectSquare(num));
            System.out.println();
        }
    }

    public static boolean isPerfectSquare(int num) {
        if (num < 1) {
            return false;
        }
        long low = 1;
        long high = num;
        while (low <= high) {
            long mid = low + (high - low) / 2;
            long square = mid * mid;
            if (square == num) {
                return true;
            }
            if (square < num) {
                low = mid + 1;
            } else {
                high = mid - 1;
            }
        }
        return false;
    }
}
