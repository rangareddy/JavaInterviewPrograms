package com.ranga.bit;

/**
 * Return the number of {@code 1} bits (the Hamming weight) in the binary representation of a 32-bit integer, counting the bits of the two's complement form for negative numbers.
 *
 * <p><b>Algorithm Strategy (Brian Kernighan's Algorithm):</b>
 * <ol>
 *   <li>For any non-zero {@code n}, {@code n & (n - 1)} clears the lowest set bit: subtracting 1 turns the lowest 1 into 0 and every 0 below it into 1, so the AND removes exactly that one bit.</li>
 *   <li>Repeat {@code n &= n - 1} and count the iterations until {@code n} becomes 0.</li>
 *   <li>The loop runs once per set bit, not once per bit position, so it is fast for sparse numbers and never more than 32 iterations. It also works for negative numbers because only bit operations are used.</li>
 * </ol>
 *
 * <p><b>Example walkthrough</b>
 * <ul>
 *   <li>For {@code n = 11} ({@code 1011}): {@code 1011 & 1010 = 1010}, then {@code 1010 & 1001 = 1000}, then {@code 1000 & 0111 = 0}.</li>
 *   <li>Three iterations, so the result is {@code 3}.</li>
 * </ul>
 *
 * <p><b>Complexity Analysis:</b>
 * <ul>
 *   <li><b>Time Complexity:</b> {@code O(k)} — {@code k} is the number of set bits, at most 32.</li>
 *   <li><b>Space Complexity:</b> {@code O(1)} — A counter.</li>
 * </ul>
 *
 * @author Ranga Reddy
 */
public class NumberOf1Bits {

    public static void main(String[] args) {
        int[] inputs = {11, 128, -3, 0, 2147483645};
        for (int n : inputs) {
            System.out.println("Input  : n = " + n + " (" + String.format("%32s", Integer.toBinaryString(n)).replace(' ', '0') + ")");
            System.out.println("Output : " + hammingWeight(n));
            System.out.println();
        }
    }

    public static int hammingWeight(int n) {
        int count = 0;
        while (n != 0) {
            n &= n - 1;
            count++;
        }
        return count;
    }
}
