package com.ranga.graph;

import java.util.Arrays;

/**
 * Given points {@code [x, y]} on a 2D plane, the cost of connecting two points is their Manhattan distance {@code |x1 - x2| + |y1 - y2|}. Return the minimum cost to connect all points so that there is exactly one simple path between any two points (a minimum spanning tree).
 *
 * <p><b>Algorithm Strategy (Prim's Algorithm on the Complete Graph):</b>
 * <ol>
 *   <li>Every pair of points is connected, so the graph is dense ({@code n^2} edges). Prim's algorithm without a heap fits it well.</li>
 *   <li>Keep {@code minCost[i]}, the cheapest known edge connecting point {@code i} to the tree, and a flag {@code inTree[i]}. Start with point 0 at cost 0.</li>
 *   <li>Repeat {@code n} times: pick the point that is not yet in the tree and has the smallest {@code minCost}, add its cost to the total and mark it as part of the tree.</li>
 *   <li>Update {@code minCost} of every point not in the tree with its distance to the newly added point if that is smaller.</li>
 * </ol>
 *
 * <p><b>Example walkthrough</b>
 * <ul>
 *   <li>For {@code [[0,0],[2,2],[3,10],[5,2],[7,0]]}: the tree edges cost 4 (0,0 to 2,2), 3 (2,2 to 5,2), 4 (5,2 to 7,0) and 9 (2,2 to 3,10).</li>
 *   <li>Total: {@code 20}.</li>
 * </ul>
 *
 * <p><b>Complexity Analysis:</b>
 * <ul>
 *   <li><b>Time Complexity:</b> {@code O(n^2)} — {@code n} rounds, each scanning all points.</li>
 *   <li><b>Space Complexity:</b> {@code O(n)} — Two arrays.</li>
 * </ul>
 *
 * @author Ranga Reddy
 */
public class MinCostToConnectAllPoints {

    public static void main(String[] args) {
        int[][][] inputs = {
                {{0, 0}, {2, 2}, {3, 10}, {5, 2}, {7, 0}},
                {{3, 12}, {-2, 5}, {-4, 1}},
                {{0, 0}},
                {{0, 0}, {1, 1}, {1, 0}, {-1, 1}}
        };
        for (int[][] points : inputs) {
            System.out.println("Input  : points = " + Arrays.deepToString(points));
            System.out.println("Output : " + minCostConnectPoints(points));
            System.out.println();
        }
    }

    public static int minCostConnectPoints(int[][] points) {
        int n = points.length;
        int[] minCost = new int[n];
        boolean[] inTree = new boolean[n];
        Arrays.fill(minCost, Integer.MAX_VALUE);
        minCost[0] = 0;

        int total = 0;
        for (int round = 0; round < n; round++) {
            int next = -1;
            for (int i = 0; i < n; i++) {
                if (!inTree[i] && (next == -1 || minCost[i] < minCost[next])) {
                    next = i;
                }
            }
            inTree[next] = true;
            total += minCost[next];

            for (int i = 0; i < n; i++) {
                if (!inTree[i]) {
                    int distance = Math.abs(points[next][0] - points[i][0]) + Math.abs(points[next][1] - points[i][1]);
                    minCost[i] = Math.min(minCost[i], distance);
                }
            }
        }
        return total;
    }
}
