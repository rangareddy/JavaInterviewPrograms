package com.ranga.bit;

/**
 * Write a function that takes the binary representation of an unsigned integer and returns the number of '1' bits it has (the Hamming weight).
 *
 * <p><b>Algorithm Strategy (Brian Kernighan's Bit Trick):</b>
 * <ol>
 *   <li>Subtracting 1 from a number flips its lowest set bit to 0 and all the zeros below it to 1.</li>
 *   <li>So {@code n & (n - 1)} clears exactly the lowest set bit of {@code n}.</li>
 *   <li>Repeat {@code n = n & (n - 1)} and count the iterations until {@code n} becomes 0; the loop runs once per set bit, not once per bit position.</li>
 *   <li>Java's {@code int} is signed, but the loop only uses AND and subtraction, so a value with the top bit set (a negative {@code int}) works without any special case.</li>
 * </ol>
 *
 * <p><b>Example walkthrough</b>
 * <ul>
 *   <li>For {@code n = 11} (binary {@code 1011}): {@code 1011 & 1010 = 1010}, then {@code 1010 & 1001 = 1000}, then {@code 1000 & 0111 = 0}.</li>
 *   <li>Three iterations: the answer is {@code 3}.</li>
 * </ul>
 *
 * <p><b>Complexity Analysis:</b>
 * <ul>
 *   <li><b>Time Complexity:</b> {@code O(k)} — {@code k} is the number of set bits (at most 32).</li>
 *   <li><b>Space Complexity:</b> {@code O(1)} — One counter.</li>
 * </ul>
 *
 * @author Ranga Reddy
 */
public class NumberOf1Bits {

    public static void main(String[] args) {
        int[] inputs = {11, 128, -3, 0};
        for (int n : inputs) {
            System.out.println("Input  : n = " + toBinary(n));
            System.out.println("Output : " + hammingWeight(n));
            System.out.println();
        }
    }

    public static int hammingWeight(int n) {
        int count = 0;
        while (n != 0) {
            n &= (n - 1);       // clear the lowest set bit
            count++;
        }
        return count;
    }

    private static String toBinary(int n) {
        return String.format("%32s", Integer.toBinaryString(n)).replace(' ', '0');
    }
}
