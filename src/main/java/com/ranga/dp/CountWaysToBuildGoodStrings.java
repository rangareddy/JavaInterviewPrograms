package com.ranga.dp;

/**
 * Given the integers {@code zero}, {@code one}, {@code low} and {@code high}, you build a string by repeatedly appending either {@code '0'} exactly {@code zero} times or {@code '1'} exactly {@code one} times. A good string has a length between {@code low} and {@code high} inclusive. Return the number of different good strings, modulo {@code 1_000_000_007}.
 *
 * <p><b>Algorithm Strategy (Count the Ways to Reach Each Length):</b>
 * <ol>
 *   <li>Let {@code ways[len]} be the number of distinct strings of exactly length {@code len}; {@code ways[0] = 1} (the empty string).</li>
 *   <li>For each length from 1 to {@code high}: add {@code ways[len - zero]} (if {@code len >= zero}) and {@code ways[len - one]} (if {@code len >= one}), modulo {@code 1_000_000_007}.</li>
 *   <li>Every string is built uniquely by its sequence of appended blocks, so no pair is counted twice.</li>
 *   <li>Sum {@code ways[len]} for {@code len} from {@code low} to {@code high}.</li>
 * </ol>
 *
 * <p><b>Example walkthrough</b>
 * <ul>
 *   <li>For {@code low = 3}, {@code high = 3}, {@code zero = 1}, {@code one = 1}: {@code ways = [1, 2, 4, 8]}, because every length doubles.</li>
 *   <li>Result: {@code 8}.</li>
 * </ul>
 *
 * <p><b>Complexity Analysis:</b>
 * <ul>
 *   <li><b>Time Complexity:</b> {@code O(high)} — One update per length.</li>
 *   <li><b>Space Complexity:</b> {@code O(high)} — The {@code ways} array.</li>
 * </ul>
 *
 * @author Ranga Reddy
 */
public class CountWaysToBuildGoodStrings {

    public static void main(String[] args) {
        int[][] inputs = {{3, 3, 1, 1}, {2, 3, 1, 2}, {1, 1, 1, 1}, {5, 20, 4, 6}, {1, 5, 6, 7}};
        for (int[] in : inputs) {
            System.out.println("Input  : low = " + in[0] + ", high = " + in[1] + ", zero = " + in[2] + ", one = " + in[3]);
            System.out.println("Output : " + countGoodStrings(in[0], in[1], in[2], in[3]));
            System.out.println();
        }
    }

    private static final int MOD = 1_000_000_007;

    public static int countGoodStrings(int low, int high, int zero, int one) {
        int[] ways = new int[high + 1];
        ways[0] = 1;
        int total = 0;
        for (int len = 1; len <= high; len++) {
            long count = 0;
            if (len >= zero) {
                count += ways[len - zero];
            }
            if (len >= one) {
                count += ways[len - one];
            }
            ways[len] = (int) (count % MOD);
            if (len >= low) {
                total = (total + ways[len]) % MOD;
            }
        }
        return total;
    }
}
