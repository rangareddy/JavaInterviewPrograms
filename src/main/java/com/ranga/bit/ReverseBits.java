package com.ranga.bit;

/**
 * Reverse the bits of a given 32-bit unsigned integer. (Java has no unsigned int, so the value is passed as a signed {@code int} with the same bit pattern.)
 *
 * <p><b>Algorithm Strategy (Shift Bits One by One):</b>
 * <ol>
 *   <li>Keep a {@code result} that starts at 0.</li>
 *   <li>Repeat 32 times: shift {@code result} left by one to make room, copy the lowest bit of {@code n} ({@code n & 1}) into it with OR, and then shift {@code n} right by one.</li>
 *   <li>Use the unsigned shift {@code >>>} so that the sign bit does not get copied into the upper bits.</li>
 *   <li>After 32 iterations the bit that was lowest in {@code n} is the highest in {@code result}, i.e. the bits are reversed.</li>
 * </ol>
 *
 * <p><b>Example walkthrough</b>
 * <ul>
 *   <li>For an 8-bit sketch of {@code 00000110}: the loop takes bits 0, 1, 1, 0, 0, 0, 0, 0 in that order and builds {@code 01100000}.</li>
 *   <li>For the 32-bit input {@code 43261596} the result is {@code 964176192}.</li>
 * </ul>
 *
 * <p><b>Complexity Analysis:</b>
 * <ul>
 *   <li><b>Time Complexity:</b> {@code O(1)} — Always 32 iterations.</li>
 *   <li><b>Space Complexity:</b> {@code O(1)} — Two variables.</li>
 * </ul>
 *
 * @author Ranga Reddy
 */
public class ReverseBits {

    public static void main(String[] args) {
        int[] inputs = {43261596, -3, 1, 0};
        for (int n : inputs) {
            int reversed = reverseBits(n);
            System.out.println("Input  : n = " + toBinary(n) + " (" + Integer.toUnsignedString(n) + ")");
            System.out.println("Output : " + toBinary(reversed) + " (" + Integer.toUnsignedString(reversed) + ")");
            System.out.println();
        }
    }

    public static int reverseBits(int n) {
        int result = 0;
        for (int i = 0; i < 32; i++) {
            result = (result << 1) | (n & 1);
            n >>>= 1;
        }
        return result;
    }

    private static String toBinary(int n) {
        return String.format("%32s", Integer.toBinaryString(n)).replace(' ', '0');
    }
}
