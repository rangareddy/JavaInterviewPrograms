package com.ranga.heap;

import java.util.Arrays;
import java.util.PriorityQueue;

/**
 * Given {@code heights} of buildings in a row, some {@code bricks} and some {@code ladders}, you start at building 0 and move to the next one. If the next building is not taller, you move for free. Otherwise you must use either one ladder, or {@code h} bricks where {@code h} is the height difference. Return the furthest building index you can reach.
 *
 * <p><b>Algorithm Strategy (Min-Heap of the Largest Climbs):</b>
 * <ol>
 *   <li>Use ladders on the biggest climbs and bricks on the smaller ones. Process the buildings left to right.</li>
 *   <li>For each climb {@code d > 0}, push {@code d} into a min-heap.</li>
 *   <li>If the heap holds more than {@code ladders} climbs, pay the smallest one with bricks: {@code bricks -= heap.poll()}.</li>
 *   <li>If {@code bricks} becomes negative, return the previous index. If the end is reached, return the last index.</li>
 * </ol>
 *
 * <p><b>Example walkthrough</b>
 * <ul>
 *   <li>For {@code [4, 2, 7, 6, 9, 14, 12]}, 5 bricks and 1 ladder: the climbs are 5, 2 and 5. The ladder takes the largest, the bricks pay for the rest.</li>
 *   <li>Result: {@code 4}.</li>
 * </ul>
 *
 * <p><b>Complexity Analysis:</b>
 * <ul>
 *   <li><b>Time Complexity:</b> {@code O(n log L)} — Heap operations on at most {@code ladders + 1} elements.</li>
 *   <li><b>Space Complexity:</b> {@code O(L)} — The heap of ladder climbs.</li>
 * </ul>
 *
 * @author Ranga Reddy
 */
public class FurthestBuildingYouCanReach {

    public static void main(String[] args) {
        int[][] heights = {{4, 2, 7, 6, 9, 14, 12}, {4, 12, 2, 7, 3, 18, 20, 3, 19}, {14, 3, 19, 3}, {1, 5, 1, 2, 3, 4, 10000}};
        int[] bricks = {5, 10, 17, 4};
        int[] ladders = {1, 2, 0, 1};
        for (int i = 0; i < heights.length; i++) {
            System.out.println("Input  : heights = " + Arrays.toString(heights[i]) + ", bricks = " + bricks[i] + ", ladders = " + ladders[i]);
            System.out.println("Output : " + furthestBuilding(heights[i], bricks[i], ladders[i]));
            System.out.println();
        }
    }

    public static int furthestBuilding(int[] heights, int bricks, int ladders) {
        if (heights == null || heights.length == 0) {
            return -1;
        }
        PriorityQueue<Integer> climbs = new PriorityQueue<>();
        for (int i = 0; i < heights.length - 1; i++) {
            int climb = heights[i + 1] - heights[i];
            if (climb <= 0) {
                continue;
            }
            climbs.add(climb);
            if (climbs.size() > ladders) {
                bricks -= climbs.poll();
            }
            if (bricks < 0) {
                return i;
            }
        }
        return heights.length - 1;
    }
}
