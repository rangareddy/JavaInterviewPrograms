package com.ranga.graph;

import java.util.Arrays;

/**
 * An {@code image} is an {@code m x n} grid of pixel colors. Starting at pixel {@code (sr, sc)}, change the color of that pixel and of every pixel connected to it 4-directionally through pixels of the same original color to {@code color}. Return the modified image.
 *
 * <p><b>Algorithm Strategy (Depth-First Search from the Start Pixel):</b>
 * <ol>
 *   <li>Remember the original color of the start pixel. If it is already {@code color}, return immediately; otherwise the fill would revisit pixels forever, because a recolored pixel would look "unvisited".</li>
 *   <li>Recolor the current pixel, then visit its four neighbours (up, down, left, right).</li>
 *   <li>A neighbour is visited only if it is inside the grid and still has the original color. Recoloring a pixel on entry doubles as the "visited" mark.</li>
 *   <li>The recursion ends when every connected pixel of the original color has been recolored.</li>
 * </ol>
 *
 * <p><b>Example walkthrough</b>
 * <ul>
 *   <li>For {@code [[1, 1, 1], [1, 1, 0], [1, 0, 1]]}, {@code (sr, sc) = (1, 1)}, {@code color = 2}: the connected region of 1s is the five pixels around the center.</li>
 *   <li>Result: {@code [[2, 2, 2], [2, 2, 0], [2, 0, 1]]}; the bottom-right 1 is not connected 4-directionally, so it keeps its color.</li>
 * </ul>
 *
 * <p><b>Complexity Analysis:</b>
 * <ul>
 *   <li><b>Time Complexity:</b> {@code O(m * n)} — Each pixel is recolored at most once.</li>
 *   <li><b>Space Complexity:</b> {@code O(m * n)} — Recursion depth in the worst case (a snake-shaped region).</li>
 * </ul>
 *
 * @author Ranga Reddy
 */
public class FloodFill {

    public static void main(String[] args) {
        int[][] image = {{1, 1, 1}, {1, 1, 0}, {1, 0, 1}};
        System.out.println("Input  : image = " + Arrays.deepToString(image) + ", sr = 1, sc = 1, color = 2");
        System.out.println("Output : " + Arrays.deepToString(floodFill(image, 1, 1, 2)));
        System.out.println();

        int[][] same = {{0, 0, 0}, {0, 0, 0}};
        System.out.println("Input  : image = " + Arrays.deepToString(same) + ", sr = 0, sc = 0, color = 0");
        System.out.println("Output : " + Arrays.deepToString(floodFill(same, 0, 0, 0)));
    }

    public static int[][] floodFill(int[][] image, int sr, int sc, int color) {
        int original = image[sr][sc];
        if (original != color) {
            fill(image, sr, sc, original, color);
        }
        return image;
    }

    private static void fill(int[][] image, int r, int c, int original, int color) {
        if (r < 0 || c < 0 || r >= image.length || c >= image[0].length || image[r][c] != original) {
            return;
        }
        image[r][c] = color;
        fill(image, r + 1, c, original, color);
        fill(image, r - 1, c, original, color);
        fill(image, r, c + 1, original, color);
        fill(image, r, c - 1, original, color);
    }
}
