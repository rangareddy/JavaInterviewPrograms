package com.ranga.interval;

import java.util.Arrays;
import java.util.PriorityQueue;

/**
 * You are given a 2D array {@code intervals} where {@code intervals[i] = [left, right]} is an inclusive interval of size {@code right - left + 1}, and an array {@code queries}. For each query {@code q} return the size of the smallest interval that contains {@code q} ({@code left <= q <= right}), or {@code -1} if none does.
 *
 * <p><b>Algorithm Strategy (Offline Processing: Sort and Min-Heap):</b>
 * <ol>
 *   <li>Sort the intervals by their left end, and process the queries in increasing order (remember each query's original index).</li>
 *   <li>For each query {@code q}, push into a min-heap (keyed by size) every interval whose left end is {@code <= q}.</li>
 *   <li>Pop heap tops whose right end is {@code < q}: they end before the query and cannot help any later (larger) query either.</li>
 *   <li>The heap top, if present, is the smallest interval containing {@code q}; otherwise the answer is {@code -1}.</li>
 * </ol>
 *
 * <p><b>Example walkthrough</b>
 * <ul>
 *   <li>For {@code intervals = [[1,4],[2,4],[3,6],[4,4]]} and {@code queries = [2,3,4,5]}: sizes 3, 3, 1 and 4.</li>
 *   <li>Result: {@code [3, 3, 1, 4]}.</li>
 * </ul>
 *
 * <p><b>Complexity Analysis:</b>
 * <ul>
 *   <li><b>Time Complexity:</b> {@code O((n + q) log (n + q))} — Sorting plus heap operations.</li>
 *   <li><b>Space Complexity:</b> {@code O(n + q)} — The heap and the sorted query order.</li>
 * </ul>
 *
 * @author Ranga Reddy
 */
public class MinimumIntervalToIncludeEachQuery {

    public static void main(String[] args) {
        int[][][] intervals = {
                {{1, 4}, {2, 4}, {3, 6}, {4, 4}},
                {{2, 3}, {2, 5}, {1, 8}, {20, 25}},
                {{1, 1}}
        };
        int[][] queries = {{2, 3, 4, 5}, {2, 19, 5, 22}, {2}};
        for (int i = 0; i < intervals.length; i++) {
            System.out.println("Input  : intervals = " + Arrays.deepToString(intervals[i]) + ", queries = " + Arrays.toString(queries[i]));
            System.out.println("Output : " + Arrays.toString(minInterval(intervals[i], queries[i])));
            System.out.println();
        }
    }

    public static int[] minInterval(int[][] intervals, int[] queries) {
        int[][] sorted = intervals.clone();
        Arrays.sort(sorted, (a, b) -> Integer.compare(a[0], b[0]));

        Integer[] order = new Integer[queries.length];
        for (int i = 0; i < order.length; i++) {
            order[i] = i;
        }
        Arrays.sort(order, (a, b) -> Integer.compare(queries[a], queries[b]));

        // heap entries are {size, right end}
        PriorityQueue<int[]> heap = new PriorityQueue<>((a, b) -> Integer.compare(a[0], b[0]));
        int[] answer = new int[queries.length];
        int next = 0;
        for (int index : order) {
            int q = queries[index];
            while (next < sorted.length && sorted[next][0] <= q) {
                heap.add(new int[]{sorted[next][1] - sorted[next][0] + 1, sorted[next][1]});
                next++;
            }
            while (!heap.isEmpty() && heap.peek()[1] < q) {
                heap.poll();
            }
            answer[index] = heap.isEmpty() ? -1 : heap.peek()[0];
        }
        return answer;
    }
}
