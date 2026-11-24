package com.ranga.interval;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Given two lists of closed intervals, {@code firstList} and {@code secondList}, each sorted by start time and made of pairwise disjoint intervals, return the intersection of the two lists. The intersection of {@code [a, b]} and {@code [c, d]} is {@code [max(a, c), min(b, d)]} when {@code max(a, c) <= min(b, d)}.
 *
 * <p><b>Algorithm Strategy (Two Pointers):</b>
 * <ol>
 *   <li>Keep one pointer in each list.</li>
 *   <li>For the current pair, compute {@code start = max(a.start, b.start)} and {@code end = min(a.end, b.end)}; if {@code start <= end}, record {@code [start, end]}.</li>
 *   <li>Advance the pointer of the interval that ends first, because it cannot intersect anything further in the other list.</li>
 *   <li>Stop when either list is exhausted.</li>
 * </ol>
 *
 * <p><b>Example walkthrough</b>
 * <ul>
 *   <li>For {@code [[0,2],[5,10],[13,23],[24,25]]} and {@code [[1,5],[8,12],[15,24],[25,26]]}: the first overlap is {@code [1, 2]}, then {@code [5, 5]}, then {@code [8, 10]}, and so on.</li>
 *   <li>Result: {@code [[1, 2], [5, 5], [8, 10], [15, 23], [24, 24], [25, 25]]}.</li>
 * </ul>
 *
 * <p><b>Complexity Analysis:</b>
 * <ul>
 *   <li><b>Time Complexity:</b> {@code O(m + n)} — Each interval is visited once.</li>
 *   <li><b>Space Complexity:</b> {@code O(1)} — Apart from the output.</li>
 * </ul>
 *
 * @author Ranga Reddy
 */
public class IntervalListIntersections {

    public static void main(String[] args) {
        int[][][] first = {{{0, 2}, {5, 10}, {13, 23}, {24, 25}}, {{1, 3}, {5, 9}}, {}, {{1, 7}}};
        int[][][] second = {{{1, 5}, {8, 12}, {15, 24}, {25, 26}}, {}, {{4, 8}, {10, 12}}, {{3, 10}}};
        for (int i = 0; i < first.length; i++) {
            System.out.println("Input  : firstList = " + Arrays.deepToString(first[i]) + ", secondList = " + Arrays.deepToString(second[i]));
            System.out.println("Output : " + Arrays.deepToString(intervalIntersection(first[i], second[i])));
            System.out.println();
        }
    }

    public static int[][] intervalIntersection(int[][] firstList, int[][] secondList) {
        List<int[]> result = new ArrayList<>();
        if (firstList == null || secondList == null) {
            return new int[0][];
        }
        int i = 0;
        int j = 0;
        while (i < firstList.length && j < secondList.length) {
            int start = Math.max(firstList[i][0], secondList[j][0]);
            int end = Math.min(firstList[i][1], secondList[j][1]);
            if (start <= end) {
                result.add(new int[]{start, end});
            }
            if (firstList[i][1] < secondList[j][1]) {
                i++;
            } else {
                j++;
            }
        }
        return result.toArray(new int[0][]);
    }
}
