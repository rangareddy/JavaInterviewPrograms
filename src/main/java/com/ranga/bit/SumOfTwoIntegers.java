package com.ranga.bit;

/**
 * Given two integers {@code a} and {@code b}, return their sum without using the operators {@code +} and {@code -}.
 *
 * <p><b>Algorithm Strategy (XOR for Sum, AND for Carry):</b>
 * <ol>
 *   <li>Adding two bits without a carry is XOR ({@code 1 ^ 1 = 0}, {@code 1 ^ 0 = 1}); the carry appears only when both bits are 1, which is AND, and it moves one position to the left.</li>
 *   <li>Compute the carry: {@code carry = (a & b) << 1}.</li>
 *   <li>Compute the sum without the carry: {@code a = a ^ b}, then set {@code b = carry}.</li>
 *   <li>Repeat until the carry {@code b} is 0; then {@code a} holds the result. Java integers use two's complement, so this also works for negative numbers.</li>
 * </ol>
 *
 * <p><b>Example walkthrough</b>
 * <ul>
 *   <li>For {@code a = 2} and {@code b = 3}: {@code 10 ^ 11 = 01} with carry {@code (10 & 11) << 1 = 100}.</li>
 *   <li>Next round: {@code 01 ^ 100 = 101} with carry 0. Result: {@code 5}.</li>
 * </ul>
 *
 * <p><b>Complexity Analysis:</b>
 * <ul>
 *   <li><b>Time Complexity:</b> {@code O(1)} — At most 32 iterations for a 32-bit integer.</li>
 *   <li><b>Space Complexity:</b> {@code O(1)} — Two variables.</li>
 * </ul>
 *
 * @author Ranga Reddy
 */
public class SumOfTwoIntegers {

    public static void main(String[] args) {
        int[][] inputs = {{1, 2}, {2, 3}, {-1, 1}, {-5, 3}, {7, -7}};
        for (int[] input : inputs) {
            System.out.println("Input  : a = " + input[0] + ", b = " + input[1]);
            System.out.println("Output : " + getSum(input[0], input[1]));
            System.out.println();
        }
    }

    public static int getSum(int a, int b) {
        while (b != 0) {
            int carry = (a & b) << 1;
            a = a ^ b;
            b = carry;
        }
        return a;
    }
}
