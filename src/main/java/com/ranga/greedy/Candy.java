package com.ranga.greedy;

import java.util.Arrays;

/**
 * {@code n} children stand in a line, each with a rating {@code ratings[i]}. Give each child at least one candy, and give a child strictly more candies than an adjacent neighbour with a lower rating. Return the minimum total number of candies.
 *
 * <p><b>Algorithm Strategy (Two Passes (Left-to-Right, Then Right-to-Left)):</b>
 * <ol>
 *   <li>Start with one candy for everyone.</li>
 *   <li>Left to right: if a child's rating is higher than the left neighbour's, give them one more candy than that neighbour. This satisfies every "higher than the left" constraint.</li>
 *   <li>Right to left: if a child's rating is higher than the right neighbour's, they need at least one more than that neighbour; take the maximum of the current value and {@code candies[i + 1] + 1} so that the first pass is not undone.</li>
 *   <li>The sum is the minimum, because each child received only the amount forced by the longest strictly increasing run reaching them from either side.</li>
 * </ol>
 *
 * <p><b>Example walkthrough</b>
 * <ul>
 *   <li>For {@code [1, 0, 2]}: the left pass gives {@code [1, 1, 2]}, and the right pass raises the first child because {@code 1 > 0}: {@code [2, 1, 2]}.</li>
 *   <li>Total {@code 5}. For {@code [1, 2, 2]} the result is {@code [1, 2, 1]} with total {@code 4}; equal ratings need no extra candy.</li>
 * </ul>
 *
 * <p><b>Complexity Analysis:</b>
 * <ul>
 *   <li><b>Time Complexity:</b> {@code O(n)} — Two linear passes.</li>
 *   <li><b>Space Complexity:</b> {@code O(n)} — The candy array.</li>
 * </ul>
 *
 * @author Ranga Reddy
 */
public class Candy {

    public static void main(String[] args) {
        int[][] inputs = {{1, 0, 2}, {1, 2, 2}, {1, 3, 2, 2, 1}, {5}};
        for (int[] ratings : inputs) {
            System.out.println("Input  : ratings = " + Arrays.toString(ratings));
            System.out.println("Output : " + candy(ratings));
            System.out.println();
        }
    }

    public static int candy(int[] ratings) {
        int n = ratings.length;
        int[] candies = new int[n];
        Arrays.fill(candies, 1);
        for (int i = 1; i < n; i++) {
            if (ratings[i] > ratings[i - 1]) {
                candies[i] = candies[i - 1] + 1;
            }
        }
        for (int i = n - 2; i >= 0; i--) {
            if (ratings[i] > ratings[i + 1]) {
                candies[i] = Math.max(candies[i], candies[i + 1] + 1);
            }
        }
        int total = 0;
        for (int c : candies) {
            total += c;
        }
        return total;
    }
}
