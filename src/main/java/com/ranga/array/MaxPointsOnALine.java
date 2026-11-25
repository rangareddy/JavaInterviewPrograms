package com.ranga.array;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

/**
 * Given an array of {@code points} where {@code points[i] = [x, y]} represents a point on the X-Y plane, return the maximum number of points that lie on the same straight line.
 *
 * <p><b>Algorithm Strategy (Reduced Slope Counting per Anchor):</b>
 * <ol>
 *   <li>Use each point as an anchor.</li>
 *   <li>For every other point, compute the slope to the anchor as a reduced fraction {@code (dy / g, dx / g)} with {@code g = gcd(dx, dy)} and a normalised sign, so that equal slopes have equal keys and no floating point is used.</li>
 *   <li>Count how many points share each slope in a {@code HashMap}; the best line through the anchor is {@code 1 + max count}.</li>
 *   <li>Keep the best over all anchors. With fewer than three points, all of them are collinear.</li>
 * </ol>
 *
 * <p><b>Example walkthrough</b>
 * <ul>
 *   <li>For {@code [[1,1],[2,2],[3,3]]} the slopes from the first point to the other two are both {@code 1/1}.</li>
 *   <li>Result: {@code 3}.</li>
 * </ul>
 *
 * <p><b>Complexity Analysis:</b>
 * <ul>
 *   <li><b>Time Complexity:</b> {@code O(n^2)} — For each anchor, one pass over the other points.</li>
 *   <li><b>Space Complexity:</b> {@code O(n)} — The slope map.</li>
 * </ul>
 *
 * @author Ranga Reddy
 */
public class MaxPointsOnALine {

    public static void main(String[] args) {
        int[][][] inputs = {{{1, 1}, {2, 2}, {3, 3}}, {{1, 1}, {3, 2}, {5, 3}, {4, 1}, {2, 3}, {1, 4}}, {{0, 0}}, {{1, 1}, {1, 2}, {1, 3}}};
        for (int[][] points : inputs) {
            System.out.println("Input  : points = " + Arrays.deepToString(points));
            System.out.println("Output : " + maxPoints(points));
            System.out.println();
        }
    }

    public static int maxPoints(int[][] points) {
        if (points == null || points.length == 0) {
            return 0;
        }
        int n = points.length;
        if (n <= 2) {
            return n;
        }
        int best = 2;
        for (int i = 0; i < n; i++) {
            Map<String, Integer> slopes = new HashMap<>();
            for (int j = i + 1; j < n; j++) {
                int dx = points[j][0] - points[i][0];
                int dy = points[j][1] - points[i][1];
                int g = gcd(Math.abs(dx), Math.abs(dy));
                dx /= g;
                dy /= g;
                if (dx < 0 || (dx == 0 && dy < 0)) {
                    dx = -dx;
                    dy = -dy;
                }
                int count = slopes.merge(dy + "/" + dx, 1, Integer::sum);
                best = Math.max(best, count + 1);
            }
        }
        return best;
    }

    private static int gcd(int a, int b) {
        return b == 0 ? a : gcd(b, a % b);
    }
}
