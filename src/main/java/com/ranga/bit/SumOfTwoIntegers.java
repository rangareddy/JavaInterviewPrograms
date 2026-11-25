package com.ranga.bit;

/**
 * Given two integers {@code a} and {@code b}, return the sum of the two integers without using the operators {@code +} and {@code -}.
 *
 * <p><b>Algorithm Strategy (XOR plus Shifted AND Carry):</b>
 * <ol>
 *   <li>The XOR of two numbers is their sum without carries.</li>
 *   <li>The AND of the two numbers, shifted left by one, is the carry.</li>
 *   <li>Replace {@code a} with the XOR and {@code b} with the carry and repeat until the carry is zero.</li>
 *   <li>In Java, {@code int} arithmetic is two's complement, so negative numbers work without extra handling.</li>
 * </ol>
 *
 * <p><b>Example walkthrough</b>
 * <ul>
 *   <li>For {@code a = 1}, {@code b = 2}: XOR is 3, the carry is {@code (1 & 2) << 1 = 0}.</li>
 *   <li>Result: {@code 3}.</li>
 * </ul>
 *
 * <p><b>Complexity Analysis:</b>
 * <ul>
 *   <li><b>Time Complexity:</b> {@code O(1)} — At most 32 iterations.</li>
 *   <li><b>Space Complexity:</b> {@code O(1)} — Two variables.</li>
 * </ul>
 *
 * @author Ranga Reddy
 */
public class SumOfTwoIntegers {

    public static void main(String[] args) {
        int[][] inputs = {{1, 2}, {2, 3}, {-1, 1}, {-5, -7}, {123456, 654321}};
        for (int[] in : inputs) {
            System.out.println("Input  : a = " + in[0] + ", b = " + in[1]);
            System.out.println("Output : " + getSum(in[0], in[1]));
            System.out.println();
        }
    }

    public static int getSum(int a, int b) {
        while (b != 0) {
            int carry = (a & b) << 1;
            a ^= b;
            b = carry;
        }
        return a;
    }
}
