package com.ranga.dp;

/**
 * A fence has {@code n} posts and {@code k} colours. Paint every post so that no three consecutive posts share a colour. Return the number of ways to paint the fence.
 *
 * <p><b>Algorithm Strategy (Same and Different Counts):</b>
 * <ol>
 *   <li>Track two numbers for the posts painted so far: {@code same}, the ways where the last two posts have the same colour, and {@code diff}, the ways where they differ.</li>
 *   <li>For post 2: {@code same = k} and {@code diff = k * (k - 1)}.</li>
 *   <li>For each later post: the new {@code same} is the previous {@code diff} (repeat the last colour), and the new {@code diff} is {@code (same + diff) * (k - 1)}.</li>
 *   <li>The answer is {@code same + diff}. Handle {@code n = 0} as 0 and {@code n = 1} as {@code k}.</li>
 * </ol>
 *
 * <p><b>Example walkthrough</b>
 * <ul>
 *   <li>For {@code n = 3}, {@code k = 2}: after two posts {@code same = 2}, {@code diff = 2}.</li>
 *   <li>Third post: {@code same = 2}, {@code diff = (2 + 2) * 1 = 4}, so the total is 6.</li>
 * </ul>
 *
 * <p><b>Complexity Analysis:</b>
 * <ul>
 *   <li><b>Time Complexity:</b> {@code O(n)} — One update per post.</li>
 *   <li><b>Space Complexity:</b> {@code O(1)} — Only two counters are kept.</li>
 * </ul>
 *
 * @author Ranga Reddy
 */
public class PaintFence {

    public static void main(String[] args) {
        int[][] inputs = {{3, 2}, {1, 1}, {7, 2}, {4, 3}, {0, 5}};
        for (int[] in : inputs) {
            System.out.println("Input  : n = " + in[0] + ", k = " + in[1]);
            System.out.println("Output : " + numWays(in[0], in[1]));
            System.out.println();
        }
    }

    public static int numWays(int n, int k) {
        if (n <= 0 || k <= 0) {
            return 0;
        }
        if (n == 1) {
            return k;
        }
        int same = k;
        int diff = k * (k - 1);
        for (int i = 3; i <= n; i++) {
            int newSame = diff;
            int newDiff = (same + diff) * (k - 1);
            same = newSame;
            diff = newDiff;
        }
        return same + diff;
    }
}
