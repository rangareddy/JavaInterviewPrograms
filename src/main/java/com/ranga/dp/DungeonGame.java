package com.ranga.dp;

import java.util.Arrays;

/**
 * A knight starts in the top-left cell of an {@code m x n} dungeon and must reach the bottom-right cell where a princess waits. Each cell holds an integer: a negative value drains health when entered, a positive value restores it. The knight dies if his health reaches 0 or below at any time. He moves only right or down. Return the minimum initial health required.
 *
 * <p><b>Algorithm Strategy (Backward DP):</b>
 * <ol>
 *   <li>Let {@code need[i][j]} be the minimum health needed when entering cell {@code (i, j)} so that the knight can still reach the end.</li>
 *   <li>At the princess's cell: {@code need = max(1, 1 - dungeon[i][j])}.</li>
 *   <li>Elsewhere: {@code need = max(1, min(need[i+1][j], need[i][j+1]) - dungeon[i][j])}, taking the cheaper of the two ways onward.</li>
 *   <li>The answer is {@code need[0][0]}. A single row of values is enough.</li>
 * </ol>
 *
 * <p><b>Example walkthrough</b>
 * <ul>
 *   <li>For {@code [[-2,-3,3],[-5,-10,1],[10,30,-5]]}: the best path is right, right, down, down, needing 7 health at the start.</li>
 *   <li>Result: {@code 7}.</li>
 * </ul>
 *
 * <p><b>Complexity Analysis:</b>
 * <ul>
 *   <li><b>Time Complexity:</b> {@code O(m * n)} — Each cell once.</li>
 *   <li><b>Space Complexity:</b> {@code O(n)} — One row.</li>
 * </ul>
 *
 * @author Ranga Reddy
 */
public class DungeonGame {

    public static void main(String[] args) {
        int[][][] dungeons = {{{-2, -3, 3}, {-5, -10, 1}, {10, 30, -5}}, {{0}}, {{100}}, {{-3, 5}}};
        for (int[][] dungeon : dungeons) {
            System.out.println("Input  : dungeon = " + Arrays.deepToString(dungeon));
            System.out.println("Output : " + calculateMinimumHP(dungeon));
            System.out.println();
        }
    }

    public static int calculateMinimumHP(int[][] dungeon) {
        if (dungeon == null || dungeon.length == 0 || dungeon[0].length == 0) {
            return 1;
        }
        int m = dungeon.length;
        int n = dungeon[0].length;
        int[] need = new int[n + 1];
        Arrays.fill(need, Integer.MAX_VALUE);
        need[n - 1] = 1;
        for (int i = m - 1; i >= 0; i--) {
            for (int j = n - 1; j >= 0; j--) {
                int onward = Math.min(need[j], need[j + 1]);
                need[j] = Math.max(1, onward - dungeon[i][j]);
            }
            need[n] = Integer.MAX_VALUE;
        }
        return need[0];
    }
}
