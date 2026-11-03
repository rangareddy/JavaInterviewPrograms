package com.ranga.heap;

import java.util.Arrays;
import java.util.Comparator;
import java.util.PriorityQueue;

/**
 * Given an array of points {@code [x, y]} on the plane and an integer {@code k}, return the {@code k} points closest to the origin (0, 0) by Euclidean distance. The answer may be returned in any order and is guaranteed to be unique apart from its order.
 *
 * <p><b>Algorithm Strategy (Max-Heap of Size k):</b>
 * <ol>
 *   <li>Compare squared distances {@code x^2 + y^2}: the square root is not needed because it preserves the order.</li>
 *   <li>Keep a max-heap (ordered by squared distance) that holds the {@code k} closest points seen so far; its top is the farthest of them.</li>
 *   <li>For every point push it; if the heap grows beyond {@code k}, remove the top (the farthest), which cannot be among the {@code k} closest.</li>
 *   <li>The points left in the heap are the answer.</li>
 * </ol>
 *
 * <p><b>Example walkthrough</b>
 * <ul>
 *   <li>For {@code [[3,3],[5,-1],[-2,4]]} and {@code k = 2} the squared distances are 18, 26 and 20.</li>
 *   <li>The point with distance 26 is evicted: {@code [[3,3],[-2,4]]}.</li>
 * </ul>
 *
 * <p><b>Complexity Analysis:</b>
 * <ul>
 *   <li><b>Time Complexity:</b> {@code O(n log k)} — Every point causes at most one push and one removal on a heap of size {@code k}.</li>
 *   <li><b>Space Complexity:</b> {@code O(k)} — The heap.</li>
 * </ul>
 *
 * @author Ranga Reddy
 */
public class KClosestPointsToOrigin {

    public static void main(String[] args) {
        int[][][] inputs = {{{1, 3}, {-2, 2}}, {{3, 3}, {5, -1}, {-2, 4}}, {{0, 1}, {1, 0}}, {{1, 1}}};
        int[] ks = {1, 2, 2, 1};
        for (int i = 0; i < inputs.length; i++) {
            System.out.println("Input  : points = " + Arrays.deepToString(inputs[i]) + ", k = " + ks[i]);
            int[][] closest = kClosest(inputs[i], ks[i]);
            Arrays.sort(closest, Comparator.comparingLong(p -> (long) p[0] * p[0] + (long) p[1] * p[1]));
            System.out.println("Output : " + Arrays.deepToString(closest));
            System.out.println();
        }
    }

    public static int[][] kClosest(int[][] points, int k) {
        PriorityQueue<int[]> heap = new PriorityQueue<>(
                (a, b) -> Long.compare(squaredDistance(b), squaredDistance(a)));         // farthest point on top
        for (int[] point : points) {
            heap.add(point);
            if (heap.size() > k) {
                heap.poll();
            }
        }

        int[][] result = new int[heap.size()][];
        for (int i = 0; i < result.length; i++) {
            result[i] = heap.poll();
        }
        return result;
    }

    private static long squaredDistance(int[] point) {
        return (long) point[0] * point[0] + (long) point[1] * point[1];
    }
}
