package com.ranga.dp;

import java.util.Arrays;

/**
 * You are given several boxes with different colours represented by positive integers. In each round you remove a group of consecutive boxes of the same colour; if the group has {@code k} boxes you earn {@code k * k} points. Return the maximum number of points you can get by removing all the boxes.
 *
 * <p><b>Algorithm Strategy (Interval DP with a Count of Attached Boxes):</b>
 * <ol>
 *   <li>Let {@code f(l, r, k)} be the best score for the boxes {@code l..r} when there are {@code k} extra boxes of the same colour as {@code boxes[l]} attached on the left (already joined to it).</li>
 *   <li>Option 1: remove {@code boxes[l]} together with those {@code k} boxes now: {@code (k + 1)^2 + f(l + 1, r, 0)}.</li>
 *   <li>Option 2: for every {@code m} in {@code (l, r]} with {@code boxes[m] == boxes[l]}, first clear everything between them and then join: {@code f(l + 1, m - 1, 0) + f(m, r, k + 1)}.</li>
 *   <li>Cache the results in a 3D table and return {@code f(0, n - 1, 0)}.</li>
 * </ol>
 *
 * <p><b>Example walkthrough</b>
 * <ul>
 *   <li>For {@code [1, 3, 2, 2, 2, 3, 4, 3, 1]}: remove the 2s (9 points), then the 4 (1), then join the 3s (16 points), then the 1s (4)... the best total is 23.</li>
 *   <li>Result: {@code 23}.</li>
 * </ul>
 *
 * <p><b>Complexity Analysis:</b>
 * <ul>
 *   <li><b>Time Complexity:</b> {@code O(n^4)} — {@code O(n^3)} states, each trying up to {@code n} partners.</li>
 *   <li><b>Space Complexity:</b> {@code O(n^3)} — The memo table.</li>
 * </ul>
 *
 * @author Ranga Reddy
 */
public class RemoveBoxes {

    public static void main(String[] args) {
        int[][] inputs = {{1, 3, 2, 2, 2, 3, 4, 3, 1}, {1, 1, 1}, {1}, {1, 2, 1}};
        for (int[] boxes : inputs) {
            System.out.println("Input  : boxes = " + Arrays.toString(boxes));
            System.out.println("Output : " + removeBoxes(boxes));
            System.out.println();
        }
    }

    public static int removeBoxes(int[] boxes) {
        if (boxes == null || boxes.length == 0) {
            return 0;
        }
        int n = boxes.length;
        int[][][] memo = new int[n][n][n];
        return solve(boxes, 0, n - 1, 0, memo);
    }

    private static int solve(int[] boxes, int l, int r, int k, int[][][] memo) {
        if (l > r) {
            return 0;
        }
        if (memo[l][r][k] != 0) {
            return memo[l][r][k];
        }
        while (l + 1 <= r && boxes[l + 1] == boxes[l]) {
            l++;
            k++;
        }
        int best = (k + 1) * (k + 1) + solve(boxes, l + 1, r, 0, memo);
        for (int m = l + 1; m <= r; m++) {
            if (boxes[m] == boxes[l]) {
                best = Math.max(best, solve(boxes, l + 1, m - 1, 0, memo) + solve(boxes, m, r, k + 1, memo));
            }
        }
        memo[l][r][k] = best;
        return best;
    }
}
