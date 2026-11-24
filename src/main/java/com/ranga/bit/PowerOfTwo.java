package com.ranga.bit;

/**
 * Given an integer {@code n}, return {@code true} if it is a power of two, that is, {@code n == 2^k} for some integer {@code k >= 0}.
 *
 * <p><b>Algorithm Strategy (Single Set Bit):</b>
 * <ol>
 *   <li>A power of two has exactly one bit set in binary ({@code 1}, {@code 10}, {@code 100}, ...).</li>
 *   <li>Subtracting 1 from it flips that bit to 0 and all lower bits to 1, so {@code n & (n - 1)} clears the lowest set bit.</li>
 *   <li>For a power of two the result is 0.</li>
 *   <li>Reject {@code n <= 0} first, since zero and negatives are never powers of two.</li>
 * </ol>
 *
 * <p><b>Example walkthrough</b>
 * <ul>
 *   <li>{@code 8 = 1000} and {@code 7 = 0111}, so {@code 8 & 7 = 0}: power of two.</li>
 *   <li>{@code 6 = 110} and {@code 5 = 101}, so {@code 6 & 5 = 100}, not 0: not a power of two.</li>
 * </ul>
 *
 * <p><b>Complexity Analysis:</b>
 * <ul>
 *   <li><b>Time Complexity:</b> {@code O(1)} — One bitwise operation.</li>
 *   <li><b>Space Complexity:</b> {@code O(1)} — No extra memory.</li>
 * </ul>
 *
 * @author Ranga Reddy
 */
public class PowerOfTwo {

    public static void main(String[] args) {
        int[] inputs = {1, 16, 3, 0, -8, 1024, Integer.MIN_VALUE};
        for (int n : inputs) {
            System.out.println("Input  : n = " + n);
            System.out.println("Output : " + isPowerOfTwo(n));
            System.out.println();
        }
    }

    public static boolean isPowerOfTwo(int n) {
        return n > 0 && (n & (n - 1)) == 0;
    }
}
