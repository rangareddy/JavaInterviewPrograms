package com.ranga.graph;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * In a grid of heights, rain water flows from a cell to a neighboring cell (north, south, east, west) whose height is less than or equal to the current one. The Pacific Ocean touches the top and left edges and the Atlantic Ocean the bottom and right edges. Return all cells from which water can flow to both oceans.
 *
 * <p><b>Algorithm Strategy (Reverse DFS from Each Ocean):</b>
 * <ol>
 *   <li>Instead of checking water flow from every cell, search backwards from the oceans: water that can reach an ocean cell comes from cells that are at least as high.</li>
 *   <li>Start a DFS from all cells on the Pacific border (top row and left column) and mark every cell reachable by moving to neighbors that are not lower. Do the same from the Atlantic border (bottom row and right column).</li>
 *   <li>A cell is part of the answer when it is marked by both searches.</li>
 *   <li>Collect the cells in row-major order.</li>
 * </ol>
 *
 * <p><b>Example walkthrough</b>
 * <ul>
 *   <li>For the 5 x 5 example, the cell (0, 4) with height 5 touches the Pacific (top) and the Atlantic (right edge), and so does (1, 3) and (1, 4).</li>
 *   <li>Result: {@code [[0,4],[1,3],[1,4],[2,2],[3,0],[3,1],[4,0]]}.</li>
 * </ul>
 *
 * <p><b>Complexity Analysis:</b>
 * <ul>
 *   <li><b>Time Complexity:</b> {@code O(m * n)} — Each cell is visited at most once per ocean.</li>
 *   <li><b>Space Complexity:</b> {@code O(m * n)} — Two visited matrices and the recursion stack.</li>
 * </ul>
 *
 * @author Ranga Reddy
 */
public class PacificAtlanticWaterFlow {

    public static void main(String[] args) {
        int[][][] inputs = {
                {{1, 2, 2, 3, 5}, {3, 2, 3, 4, 4}, {2, 4, 5, 3, 1}, {6, 7, 1, 4, 5}, {5, 1, 1, 2, 4}},
                {{1}}
        };
        for (int[][] heights : inputs) {
            System.out.println("Input  : heights = " + Arrays.deepToString(heights));
            System.out.println("Output : " + pacificAtlantic(heights));
            System.out.println();
        }
    }

    public static List<List<Integer>> pacificAtlantic(int[][] heights) {
        List<List<Integer>> result = new ArrayList<>();
        if (heights == null || heights.length == 0) {
            return result;
        }

        int rows = heights.length;
        int cols = heights[0].length;
        boolean[][] pacific = new boolean[rows][cols];
        boolean[][] atlantic = new boolean[rows][cols];

        for (int i = 0; i < rows; i++) {
            dfs(heights, pacific, i, 0);
            dfs(heights, atlantic, i, cols - 1);
        }
        for (int j = 0; j < cols; j++) {
            dfs(heights, pacific, 0, j);
            dfs(heights, atlantic, rows - 1, j);
        }

        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                if (pacific[i][j] && atlantic[i][j]) {
                    result.add(Arrays.asList(i, j));
                }
            }
        }
        return result;
    }

    private static void dfs(int[][] heights, boolean[][] reachable, int i, int j) {
        reachable[i][j] = true;
        int[][] directions = {{1, 0}, {-1, 0}, {0, 1}, {0, -1}};
        for (int[] d : directions) {
            int x = i + d[0];
            int y = j + d[1];
            if (x >= 0 && y >= 0 && x < heights.length && y < heights[0].length
                    && !reachable[x][y] && heights[x][y] >= heights[i][j]) {
                dfs(heights, reachable, x, y);
            }
        }
    }
}
