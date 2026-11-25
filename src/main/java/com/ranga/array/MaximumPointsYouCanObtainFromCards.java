package com.ranga.array;

import java.util.Arrays;

/**
 * Several cards are arranged in a row, each with points {@code cardPoints[i]}. In one step you take a card from either the beginning or the end of the row. You must take exactly {@code k} cards. Return the maximum score you can obtain.
 *
 * <p><b>Algorithm Strategy (Fixed Window over the Leftover Cards):</b>
 * <ol>
 *   <li>Whichever cards you take, the {@code n - k} cards you leave form one contiguous window in the middle.</li>
 *   <li>Maximizing the taken sum is the same as minimizing the leftover window's sum.</li>
 *   <li>Compute the minimum sum of a window of length {@code n - k} with a sliding window.</li>
 *   <li>The answer is {@code total - minWindow} (and the total if {@code k == n}).</li>
 * </ol>
 *
 * <p><b>Example walkthrough</b>
 * <ul>
 *   <li>For {@code [1, 2, 3, 4, 5, 6, 1]} and {@code k = 3}: the leftover window has length 4 and its smallest sum is 10 ({@code [1, 2, 3, 4]}).</li>
 *   <li>The total is 22, so the answer is {@code 22 - 10 = 12}.</li>
 * </ul>
 *
 * <p><b>Complexity Analysis:</b>
 * <ul>
 *   <li><b>Time Complexity:</b> {@code O(n)} — One pass.</li>
 *   <li><b>Space Complexity:</b> {@code O(1)} — A running sum.</li>
 * </ul>
 *
 * @author Ranga Reddy
 */
public class MaximumPointsYouCanObtainFromCards {

    public static void main(String[] args) {
        int[][] cards = {{1, 2, 3, 4, 5, 6, 1}, {2, 2, 2}, {9, 7, 7, 9, 7, 7, 9}, {1, 1000, 1}};
        int[] ks = {3, 2, 7, 1};
        for (int i = 0; i < cards.length; i++) {
            System.out.println("Input  : cardPoints = " + Arrays.toString(cards[i]) + ", k = " + ks[i]);
            System.out.println("Output : " + maxScore(cards[i], ks[i]));
            System.out.println();
        }
    }

    public static int maxScore(int[] cardPoints, int k) {
        if (cardPoints == null || k <= 0) {
            return 0;
        }
        int n = cardPoints.length;
        int total = 0;
        for (int x : cardPoints) {
            total += x;
        }
        if (k >= n) {
            return total;
        }
        int window = n - k;
        int sum = 0;
        for (int i = 0; i < window; i++) {
            sum += cardPoints[i];
        }
        int min = sum;
        for (int i = window; i < n; i++) {
            sum += cardPoints[i] - cardPoints[i - window];
            min = Math.min(min, sum);
        }
        return total - min;
    }
}
