package com.ranga.bit;

/**
 * Reverse the bits of a 32-bit unsigned integer. In Java the input is an {@code int} whose bits are interpreted as unsigned, so the result is also returned as an {@code int} with the same 32 bits reversed.
 *
 * <p><b>Algorithm Strategy (Shift Bits One by One):</b>
 * <ol>
 *   <li>Build the result bit by bit: for each of the 32 positions, shift {@code result} left by one to make room.</li>
 *   <li>Copy the lowest bit of {@code n} into it with {@code result |= n & 1}.</li>
 *   <li>Shift {@code n} right with the unsigned shift {@code >>>} so the sign bit is not copied back in, and repeat 32 times.</li>
 *   <li>After 32 iterations the first bit of the input is the last bit of the result and vice versa.</li>
 * </ol>
 *
 * <p><b>Example walkthrough</b>
 * <ul>
 *   <li>For the 8-bit pattern {@code 00000110} (considered inside a 32-bit value) the low bits {@code 0, 1, 1, 0, 0, 0, 0, 0} are pushed into the result one at a time.</li>
 *   <li>They end up as the top bits {@code 01100000...}, which is the input read backwards.</li>
 * </ul>
 *
 * <p><b>Complexity Analysis:</b>
 * <ul>
 *   <li><b>Time Complexity:</b> {@code O(1)} — Exactly 32 iterations.</li>
 *   <li><b>Space Complexity:</b> {@code O(1)} — A few integers.</li>
 * </ul>
 *
 * @author Ranga Reddy
 */
public class ReverseBits {

    public static void main(String[] args) {
        int[] inputs = {0b00000010100101000001111010011100, 0b11111111111111111111111111111101, 1, 0};
        for (int n : inputs) {
            System.out.println("Input  : n = " + binary(n));
            System.out.println("Output : " + binary(reverseBits(n)));
            System.out.println();
        }
    }

    private static String binary(int n) {
        return String.format("%32s", Integer.toBinaryString(n)).replace(' ', '0');
    }

    public static int reverseBits(int n) {
        int result = 0;
        for (int i = 0; i < 32; i++) {
            result = (result << 1) | (n & 1);
            n >>>= 1;
        }
        return result;
    }
}
