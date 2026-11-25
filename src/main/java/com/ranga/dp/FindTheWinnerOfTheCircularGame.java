package com.ranga.dp;

/**
 * {@code n} friends sit in a circle numbered {@code 1} to {@code n}. Starting at friend 1, count {@code k} friends clockwise (including the starting one); the friend at the end of the count leaves the circle, and the next count starts from the friend clockwise of the one who left. Repeat until one friend remains and return that friend's number.
 *
 * <p><b>Algorithm Strategy (Josephus Recurrence):</b>
 * <ol>
 *   <li>Let {@code f(n)} be the 0-indexed winner among {@code n} friends. With one friend, {@code f(1) = 0}.</li>
 *   <li>After the first removal, the circle has {@code n - 1} friends and the count restarts {@code k} positions later, so {@code f(n) = (f(n - 1) + k) % n}.</li>
 *   <li>Compute it iteratively from 2 to {@code n}.</li>
 *   <li>Return {@code f(n) + 1} to get a 1-indexed friend.</li>
 * </ol>
 *
 * <p><b>Example walkthrough</b>
 * <ul>
 *   <li>For {@code n = 5}, {@code k = 2}: the friends leave in the order 2, 4, 1, 5, so friend 3 wins.</li>
 *   <li>Result: {@code 3}.</li>
 * </ul>
 *
 * <p><b>Complexity Analysis:</b>
 * <ul>
 *   <li><b>Time Complexity:</b> {@code O(n)} — One loop.</li>
 *   <li><b>Space Complexity:</b> {@code O(1)} — One integer.</li>
 * </ul>
 *
 * @author Ranga Reddy
 */
public class FindTheWinnerOfTheCircularGame {

    public static void main(String[] args) {
        int[][] inputs = {{5, 2}, {6, 5}, {1, 3}, {7, 3}};
        for (int[] in : inputs) {
            System.out.println("Input  : n = " + in[0] + ", k = " + in[1]);
            System.out.println("Output : " + findTheWinner(in[0], in[1]));
            System.out.println();
        }
    }

    public static int findTheWinner(int n, int k) {
        int winner = 0;
        for (int size = 2; size <= n; size++) {
            winner = (winner + k) % size;
        }
        return winner + 1;
    }
}
