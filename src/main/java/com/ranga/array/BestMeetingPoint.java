package com.ranga.array;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Given an {@code m x n} binary grid where each {@code 1} marks the home of a friend, return the minimal total travel distance. The total travel distance is the sum of the Manhattan distances between the friends' homes and the meeting point, which can be any cell.
 *
 * <p><b>Algorithm Strategy (Median of the Row and Column Coordinates):</b>
 * <ol>
 *   <li>Manhattan distance splits into a row part and a column part, so the best meeting row and column can be chosen independently.</li>
 *   <li>Collect the row index of every friend (already sorted by scanning rows in order) and the column indexes (scan columns in order to keep them sorted).</li>
 *   <li>The best row is the median of the row list, the best column is the median of the column list.</li>
 *   <li>Sum the distances of every coordinate to its median.</li>
 * </ol>
 *
 * <p><b>Example walkthrough</b>
 * <ul>
 *   <li>For the 3x5 sample, the rows are {@code [0, 0, 2]} with median 0 and the columns {@code [0, 2, 4]} with median 2.</li>
 *   <li>Row cost 2, column cost 4: result {@code 6}.</li>
 * </ul>
 *
 * <p><b>Complexity Analysis:</b>
 * <ul>
 *   <li><b>Time Complexity:</b> {@code O(m * n)} — Two scans of the grid.</li>
 *   <li><b>Space Complexity:</b> {@code O(k)} — The coordinates of the k friends.</li>
 * </ul>
 *
 * @author Ranga Reddy
 */
public class BestMeetingPoint {

    public static void main(String[] args) {
        int[][][] inputs = {{{1, 0, 0, 0, 1}, {0, 0, 0, 0, 0}, {0, 0, 1, 0, 0}}, {{1, 1}}, {{1, 0, 1}}, {}};
        for (int[][] grid : inputs) {
            System.out.println("Input  : grid = " + Arrays.deepToString(grid));
            System.out.println("Output : " + minTotalDistance(grid));
            System.out.println();
        }
    }

    public static int minTotalDistance(int[][] grid) {
        if (grid == null || grid.length == 0) {
            return 0;
        }
        List<Integer> rows = new ArrayList<>();
        List<Integer> cols = new ArrayList<>();
        for (int i = 0; i < grid.length; i++) {
            for (int j = 0; j < grid[i].length; j++) {
                if (grid[i][j] == 1) {
                    rows.add(i);
                }
            }
        }
        for (int j = 0; j < grid[0].length; j++) {
            for (int[] row : grid) {
                if (row[j] == 1) {
                    cols.add(j);
                }
            }
        }
        return distanceToMedian(rows) + distanceToMedian(cols);
    }

    private static int distanceToMedian(List<Integer> sorted) {
        int total = 0;
        int i = 0;
        int j = sorted.size() - 1;
        while (i < j) {
            total += sorted.get(j--) - sorted.get(i++);
        }
        return total;
    }
}
