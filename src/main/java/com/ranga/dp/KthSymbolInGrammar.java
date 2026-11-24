package com.ranga.dp;

/**
 * Build a table of {@code n} rows. Row 1 is {@code 0}. Each following row is made from the previous one by replacing every {@code 0} with {@code 01} and every {@code 1} with {@code 10}. Return the {@code k}-th symbol (1-indexed) of row {@code n}.
 *
 * <p><b>Algorithm Strategy (Parent Lookup):</b>
 * <ol>
 *   <li>Row {@code n} has {@code 2^(n-1)} symbols, and each symbol of row {@code n - 1} produces two children: positions {@code 2p - 1} and {@code 2p}.</li>
 *   <li>So the parent of position {@code k} is {@code (k + 1) / 2}.</li>
 *   <li>If {@code k} is odd, the symbol equals its parent. If {@code k} is even, it is the parent flipped.</li>
 *   <li>Recurse from row {@code n} to row 1, where the answer is {@code 0}.</li>
 * </ol>
 *
 * <p><b>Example walkthrough</b>
 * <ul>
 *   <li>For {@code n = 4}, {@code k = 5}: row 4 is {@code 01101001}, whose 5th symbol is 1.</li>
 *   <li>Walk up: position 5 -> 3 -> 2 -> 1, with flips at the even steps, giving 1.</li>
 * </ul>
 *
 * <p><b>Complexity Analysis:</b>
 * <ul>
 *   <li><b>Time Complexity:</b> {@code O(n)} — One step per row.</li>
 *   <li><b>Space Complexity:</b> {@code O(1)} — The loop version keeps a flip counter only.</li>
 * </ul>
 *
 * @author Ranga Reddy
 */
public class KthSymbolInGrammar {

    public static void main(String[] args) {
        int[][] inputs = {{1, 1}, {2, 1}, {2, 2}, {4, 5}, {30, 434991989}};
        for (int[] in : inputs) {
            System.out.println("Input  : n = " + in[0] + ", k = " + in[1]);
            System.out.println("Output : " + kthGrammar(in[0], in[1]));
            System.out.println();
        }
    }

    public static int kthGrammar(int n, int k) {
        int flips = 0;
        while (n > 1) {
            if (k % 2 == 0) {
                flips++;
            }
            k = (k + 1) / 2;
            n--;
        }
        return flips % 2;
    }
}
