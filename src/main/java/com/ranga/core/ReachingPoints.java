package com.ranga.core;

/**
 * Given four integers {@code sx}, {@code sy}, {@code tx} and {@code ty}, return {@code true} if it is possible to convert the point {@code (sx, sy)} to {@code (tx, ty)} through some operations, and {@code false} otherwise. One operation turns {@code (x, y)} into {@code (x, x + y)} or {@code (x + y, y)}.
 *
 * <p><b>Algorithm Strategy (Reverse the Operations):</b>
 * <ol>
 *   <li>Going backward from {@code (tx, ty)} there is only one possible previous point: the larger coordinate must have been produced by adding the smaller one. So if {@code tx > ty} the previous point is {@code (tx - ty, ty)}, otherwise {@code (tx, ty - tx)}.</li>
 *   <li>Subtracting one by one is slow, so subtract many times at once with modulo: {@code tx %= ty} (or {@code ty %= tx}).</li>
 *   <li>Stop when {@code tx <= sx} or {@code ty <= sy}, or when {@code tx == ty}.</li>
 *   <li>At the end, check whether the remaining point can still reach the source: if {@code tx == sx} then {@code ty >= sy} and {@code (ty - sy) % tx == 0}; symmetric for {@code ty == sy}.</li>
 * </ol>
 *
 * <p><b>Example walkthrough</b>
 * <ul>
 *   <li>For {@code (1, 1)} to {@code (3, 5)}: {@code (3, 5)} came from {@code (3, 2)}, which came from {@code (1, 2)}, which came from {@code (1, 1)}.</li>
 *   <li>Result: {@code true}.</li>
 * </ul>
 *
 * <p><b>Complexity Analysis:</b>
 * <ul>
 *   <li><b>Time Complexity:</b> {@code O(log(max(tx, ty)))} — Like the Euclidean algorithm.</li>
 *   <li><b>Space Complexity:</b> {@code O(1)} — A few longs.</li>
 * </ul>
 *
 * @author Ranga Reddy
 */
public class ReachingPoints {

    public static void main(String[] args) {
        int[][] inputs = {{1, 1, 3, 5}, {1, 1, 2, 2}, {1, 1, 1, 1}, {1, 4, 5, 9}};
        for (int[] in : inputs) {
            System.out.println("Input  : sx = " + in[0] + ", sy = " + in[1] + ", tx = " + in[2] + ", ty = " + in[3]);
            System.out.println("Output : " + reachingPoints(in[0], in[1], in[2], in[3]));
            System.out.println();
        }
    }

    public static boolean reachingPoints(int sx, int sy, int tx, int ty) {
        while (tx > sx && ty > sy && tx != ty) {
            if (tx > ty) {
                tx %= ty;
            } else {
                ty %= tx;
            }
        }
        if (tx == sx && ty == sy) {
            return true;
        }
        if (tx == sx) {
            return ty > sy && (ty - sy) % tx == 0;
        }
        if (ty == sy) {
            return tx > sx && (tx - sx) % ty == 0;
        }
        return false;
    }
}
