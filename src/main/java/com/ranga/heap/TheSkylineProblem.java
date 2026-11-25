package com.ranga.heap;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.TreeMap;

/**
 * Given buildings {@code [left, right, height]}, return the skyline they form: a list of key points {@code [x, height]} sorted by {@code x}, where each key point is the left end of a horizontal segment, and the last point has height 0. There must be no two consecutive key points with the same height.
 *
 * <p><b>Algorithm Strategy (Sweep Line with a Max-Heap):</b>
 * <ol>
 *   <li>Turn each building into two events: a start {@code (left, -height)} and an end {@code (right, height)}. Sort by {@code x}, with starts before ends at the same {@code x} (negative heights) and taller starts first.</li>
 *   <li>Keep a max-heap (or a multiset) of active heights, initially {@code {0}}.</li>
 *   <li>On a start, add the height; on an end, remove it.</li>
 *   <li>After each event, compare the current maximum height with the previous maximum; if it changed, emit {@code [x, newMax]}.</li>
 * </ol>
 *
 * <p><b>Example walkthrough</b>
 * <ul>
 *   <li>For {@code [[2,9,10],[3,7,15],[5,12,12],[15,20,10],[19,24,8]]} the skyline rises to 10 at x = 2, to 15 at x = 3, drops to 12 at x = 7, to 0 at x = 12, and so on.</li>
 *   <li>Result: {@code [[2,10],[3,15],[7,12],[12,0],[15,10],[20,8],[24,0]]}.</li>
 * </ul>
 *
 * <p><b>Complexity Analysis:</b>
 * <ul>
 *   <li><b>Time Complexity:</b> {@code O(n log n)} — Sorting the events and heap operations.</li>
 *   <li><b>Space Complexity:</b> {@code O(n)} — The events and the multiset.</li>
 * </ul>
 *
 * @author Ranga Reddy
 */
public class TheSkylineProblem {

    public static void main(String[] args) {
        int[][][] inputs = {{{2, 9, 10}, {3, 7, 15}, {5, 12, 12}, {15, 20, 10}, {19, 24, 8}}, {{0, 2, 3}, {2, 5, 3}}, {{1, 5, 3}}, {{0, 3, 3}, {1, 4, 5}}};
        for (int[][] buildings : inputs) {
            System.out.println("Input  : buildings = " + Arrays.deepToString(buildings));
            System.out.println("Output : " + getSkyline(buildings));
            System.out.println();
        }
    }

    public static List<List<Integer>> getSkyline(int[][] buildings) {
        List<List<Integer>> skyline = new ArrayList<>();
        if (buildings == null || buildings.length == 0) {
            return skyline;
        }
        List<int[]> events = new ArrayList<>();
        for (int[] b : buildings) {
            events.add(new int[]{b[0], -b[2]});
            events.add(new int[]{b[1], b[2]});
        }
        events.sort((a, b) -> a[0] != b[0] ? a[0] - b[0] : a[1] - b[1]);
        TreeMap<Integer, Integer> heights = new TreeMap<>();
        heights.put(0, 1);
        int previous = 0;
        for (int[] event : events) {
            if (event[1] < 0) {
                heights.merge(-event[1], 1, Integer::sum);
            } else if (heights.merge(event[1], -1, Integer::sum) == 0) {
                heights.remove(event[1]);
            }
            int current = heights.lastKey();
            if (current != previous) {
                skyline.add(Arrays.asList(event[0], current));
                previous = current;
            }
        }
        return skyline;
    }
}
