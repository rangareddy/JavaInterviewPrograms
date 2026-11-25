package com.ranga.matrix;

import java.util.Arrays;

/**
 * You are given an {@code m x n} matrix of characters {@code box} representing a side-view of a box: {@code '#'} is a stone, {@code '*'} is a stationary obstacle and {@code '.'} is empty. The box is rotated 90 degrees clockwise, causing some stones to fall to the right (before the rotation, in the row's frame) until they hit an obstacle, another stone or the bottom. Return the {@code n x m} matrix of the box after the rotation, with gravity applied.
 *
 * <p><b>Algorithm Strategy (Drop Stones to the Right in Each Row, Then Rotate):</b>
 * <ol>
 *   <li>Process each row from right to left with a pointer {@code empty} to the right-most free slot of the current segment.</li>
 *   <li>On {@code '*'}, the obstacle ends the segment: set {@code empty = j - 1}. On {@code '#'}, move the stone to {@code empty} (put {@code '.'} where it was) and decrease {@code empty}.</li>
 *   <li>After gravity is applied to each row, rotate the matrix 90 degrees clockwise: the new cell {@code [j][m - 1 - i]} takes the old {@code [i][j]}.</li>
 *   <li>Return the rotated matrix.</li>
 * </ol>
 *
 * <p><b>Example walkthrough</b>
 * <ul>
 *   <li>For the row {@code # . *  .} stones fall right until the obstacle: the first {@code #} moves next to the obstacle.</li>
 *   <li>After the rotation the rows become columns read from the bottom row up.</li>
 * </ul>
 *
 * <p><b>Complexity Analysis:</b>
 * <ul>
 *   <li><b>Time Complexity:</b> {@code O(m * n)} — One pass per row and one for the rotation.</li>
 *   <li><b>Space Complexity:</b> {@code O(m * n)} — The rotated matrix.</li>
 * </ul>
 *
 * @author Ranga Reddy
 */
public class RotatingTheBox {

    public static void main(String[] args) {
        char[][][] inputs = {
            {{'#', '.', '#'}},
            {{'#', '.', '*', '.'}, {'#', '#', '*', '.'}},
            {{'#', '#', '*', '.', '*', '.'}, {'#', '#', '#', '*', '.', '.'}, {'#', '#', '#', '.', '#', '.'}}
        };
        for (char[][] box : inputs) {
            System.out.println("Input  : box = " + Arrays.deepToString(box));
            System.out.println("Output : " + Arrays.deepToString(rotateTheBox(box)));
            System.out.println();
        }
    }

    public static char[][] rotateTheBox(char[][] box) {
        if (box == null || box.length == 0) {
            return new char[0][0];
        }
        int rows = box.length;
        int cols = box[0].length;
        char[][] rotated = new char[cols][rows];
        for (int i = 0; i < rows; i++) {
            int empty = cols - 1;
            char[] row = box[i].clone();
            for (int j = cols - 1; j >= 0; j--) {
                if (row[j] == '*') {
                    empty = j - 1;
                } else if (row[j] == '#') {
                    row[j] = '.';
                    row[empty--] = '#';
                }
            }
            for (int j = 0; j < cols; j++) {
                rotated[j][rows - 1 - i] = row[j];
            }
        }
        return rotated;
    }
}
