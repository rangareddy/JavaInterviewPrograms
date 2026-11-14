package com.ranga.bit;

/**
 * Swap the values of two integer variables without using a third temporary variable. (Java passes arguments by value, so a helper method cannot swap caller variables; the swap is shown on array elements and on local variables.)
 *
 * <p><b>Algorithm Strategy (Arithmetic and XOR Swaps):</b>
 * <ol>
 *   <li>Addition and subtraction: {@code a = a + b; b = a - b; a = a - b}. After the first step {@code a} holds the sum; subtracting {@code b} recovers the old {@code a}, and subtracting that from the sum recovers the old {@code b}. The sum can overflow, but with wrap-around arithmetic the result is still correct.</li>
 *   <li>XOR: {@code a = a ^ b; b = a ^ b; a = a ^ b}. XOR-ing a value with the same value twice gives the original value back.</li>
 *   <li>Both tricks fail if the two positions are the same memory location (for example swapping {@code arr[i]} with {@code arr[i]}) because the value becomes 0; guard against {@code i == j}.</li>
 *   <li>In production code a temporary variable is clearer and just as fast.</li>
 * </ol>
 *
 * <p><b>Example walkthrough</b>
 * <ul>
 *   <li>With {@code a = 5, b = 9}: sum step {@code a = 14}, {@code b = 14 - 9 = 5}, {@code a = 14 - 5 = 9}.</li>
 *   <li>With XOR: {@code a = 5 ^ 9 = 12}, {@code b = 12 ^ 9 = 5}, {@code a = 12 ^ 5 = 9}.</li>
 * </ul>
 *
 * <p><b>Complexity Analysis:</b>
 * <ul>
 *   <li><b>Time Complexity:</b> {@code O(1)} — Three operations.</li>
 *   <li><b>Space Complexity:</b> {@code O(1)} — No extra variable.</li>
 * </ul>
 *
 * @author Ranga Reddy
 */
public class SwapTwoNumbers {

    public static void main(String[] args) {
        int[][] inputs = {{5, 9}, {-3, 7}, {0, 4}, {Integer.MAX_VALUE, 1}};
        for (int[] pair : inputs) {
            int[] byArithmetic = pair.clone();
            swapArithmetic(byArithmetic, 0, 1);
            int[] byXor = pair.clone();
            swapXor(byXor, 0, 1);
            System.out.println("Input  : a = " + pair[0] + ", b = " + pair[1]);
            System.out.println("Output : arithmetic -> a = " + byArithmetic[0] + ", b = " + byArithmetic[1]
                    + " | xor -> a = " + byXor[0] + ", b = " + byXor[1]);
            System.out.println();
        }
    }

    public static void swapArithmetic(int[] values, int i, int j) {
        if (i == j) {
            return;
        }
        values[i] = values[i] + values[j];
        values[j] = values[i] - values[j];
        values[i] = values[i] - values[j];
    }

    public static void swapXor(int[] values, int i, int j) {
        if (i == j) {
            return;
        }
        values[i] = values[i] ^ values[j];
        values[j] = values[i] ^ values[j];
        values[i] = values[i] ^ values[j];
    }
}
