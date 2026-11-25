package com.ranga.array;

/**
 * Given the coordinates of two axis-aligned rectangles in a 2D plane, return the total area covered by the two rectangles. The first rectangle has bottom-left {@code (ax1, ay1)} and top-right {@code (ax2, ay2)}; the second has bottom-left {@code (bx1, by1)} and top-right {@code (bx2, by2)}.
 *
 * <p><b>Algorithm Strategy (Add Both Areas, Subtract the Overlap):</b>
 * <ol>
 *   <li>Compute the area of each rectangle.</li>
 *   <li>The overlap width is {@code min(ax2, bx2) - max(ax1, bx1)} and the overlap height is {@code min(ay2, by2) - max(ay1, by1)}; if either is not positive, the rectangles do not overlap and the overlap area is 0.</li>
 *   <li>Return {@code areaA + areaB - overlap}.</li>
 *   <li>Use {@code long} for the intermediate values to avoid overflow on large coordinates, then cast.</li>
 * </ol>
 *
 * <p><b>Example walkthrough</b>
 * <ul>
 *   <li>For {@code A = (-3, 0, 3, 4)} and {@code B = (0, -1, 9, 2)}: areas 24 and 27; the overlap is {@code 3 * 2 = 6}.</li>
 *   <li>Result: {@code 24 + 27 - 6 = 45}.</li>
 * </ul>
 *
 * <p><b>Complexity Analysis:</b>
 * <ul>
 *   <li><b>Time Complexity:</b> {@code O(1)} — A fixed number of arithmetic operations.</li>
 *   <li><b>Space Complexity:</b> {@code O(1)} — A few variables.</li>
 * </ul>
 *
 * @author Ranga Reddy
 */
public class RectangleArea {

    public static void main(String[] args) {
        int[][] inputs = {{-3, 0, 3, 4, 0, -1, 9, 2}, {-2, -2, 2, 2, -2, -2, 2, 2}, {0, 0, 1, 1, 5, 5, 6, 6}, {0, 0, 2, 2, 2, 0, 4, 2}};
        for (int[] in : inputs) {
            System.out.println("Input  : A = (" + in[0] + ", " + in[1] + ", " + in[2] + ", " + in[3] + "), B = (" + in[4] + ", " + in[5] + ", " + in[6] + ", " + in[7] + ")");
            System.out.println("Output : " + computeArea(in[0], in[1], in[2], in[3], in[4], in[5], in[6], in[7]));
            System.out.println();
        }
    }

    public static int computeArea(int ax1, int ay1, int ax2, int ay2, int bx1, int by1, int bx2, int by2) {
        long areaA = (long) (ax2 - ax1) * (ay2 - ay1);
        long areaB = (long) (bx2 - bx1) * (by2 - by1);
        long width = Math.min((long) ax2, bx2) - Math.max((long) ax1, bx1);
        long height = Math.min((long) ay2, by2) - Math.max((long) ay1, by1);
        long overlap = width > 0 && height > 0 ? width * height : 0;
        return (int) (areaA + areaB - overlap);
    }
}
