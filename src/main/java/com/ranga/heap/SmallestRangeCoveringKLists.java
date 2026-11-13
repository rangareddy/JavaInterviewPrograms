package com.ranga.heap;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.PriorityQueue;

/**
 * Given {@code k} lists of integers, each sorted in non-decreasing order, find the smallest range {@code [a, b]} that includes at least one number from each of the {@code k} lists. A range {@code [a, b]} is smaller than {@code [c, d]} if {@code b - a < d - c}, or {@code a < c} when the lengths are equal.
 *
 * <p><b>Algorithm Strategy (Min-Heap of the Current Front of Each List):</b>
 * <ol>
 *   <li>Take the first element of every list. The range that covers them all is {@code [min, max]} where {@code max} is tracked separately and {@code min} is the top of a min-heap.</li>
 *   <li>Record the current range if it is smaller than the best so far (strictly smaller, so the earliest start wins a tie).</li>
 *   <li>Pop the smallest element: this list is the one that limits the range from the left. Advance in that list to its next element, push it and update {@code max} if needed. If the list has no next element, stop, because a range can no longer cover all lists.</li>
 *   <li>Repeat. Each step moves the left boundary to the next-smallest candidate and every window of {@code k} elements (one from each list) is considered.</li>
 * </ol>
 *
 * <p><b>Example walkthrough</b>
 * <ul>
 *   <li>For {@code [[4, 10, 15, 24, 26], [0, 9, 12, 20], [5, 18, 22, 30]]}: the first window is {@code 4, 0, 5} with range {@code [0, 5]}, length 5.</li>
 *   <li>The window {@code 24, 20, 22} gives {@code [20, 24]} with length 4, the smallest range found. Result: {@code [20, 24]}.</li>
 * </ul>
 *
 * <p><b>Complexity Analysis:</b>
 * <ul>
 *   <li><b>Time Complexity:</b> {@code O(N log k)} — {@code N} is the total number of elements; each is pushed and popped once from a heap of size {@code k}.</li>
 *   <li><b>Space Complexity:</b> {@code O(k)} — The heap.</li>
 * </ul>
 *
 * @author Ranga Reddy
 */
public class SmallestRangeCoveringKLists {

    public static void main(String[] args) {
        int[][][] inputs = {
                {{4, 10, 15, 24, 26}, {0, 9, 12, 20}, {5, 18, 22, 30}},
                {{1, 2, 3}, {1, 2, 3}, {1, 2, 3}},
                {{10, 10}, {11, 11}}
        };
        for (int[][] arrays : inputs) {
            List<List<Integer>> lists = new ArrayList<>();
            for (int[] a : arrays) {
                List<Integer> list = new ArrayList<>();
                for (int v : a) {
                    list.add(v);
                }
                lists.add(list);
            }
            System.out.println("Input  : nums = " + lists);
            System.out.println("Output : " + Arrays.toString(smallestRange(lists)));
            System.out.println();
        }
    }

    public static int[] smallestRange(List<List<Integer>> nums) {
        PriorityQueue<int[]> heap = new PriorityQueue<>((a, b) -> Integer.compare(a[0], b[0]));
        int max = Integer.MIN_VALUE;
        for (int i = 0; i < nums.size(); i++) {
            int value = nums.get(i).get(0);
            heap.add(new int[]{value, i, 0});
            max = Math.max(max, value);
        }
        int[] best = {heap.peek()[0], max};
        while (true) {
            int[] top = heap.poll();
            if (max - top[0] < best[1] - best[0]) {
                best[0] = top[0];
                best[1] = max;
            }
            List<Integer> list = nums.get(top[1]);
            if (top[2] + 1 == list.size()) {
                return best;
            }
            int next = list.get(top[2] + 1);
            heap.add(new int[]{next, top[1], top[2] + 1});
            max = Math.max(max, next);
        }
    }
}
