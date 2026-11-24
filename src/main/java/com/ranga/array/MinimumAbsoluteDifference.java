package com.ranga.array;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Given an array of distinct integers {@code arr}, find all pairs of elements with the minimum absolute difference of any two elements. Return the pairs in ascending order, each pair {@code [a, b]} with {@code a < b}.
 *
 * <p><b>Algorithm Strategy (Sort and Scan Neighbours):</b>
 * <ol>
 *   <li>After sorting, the smallest difference is always between two neighbouring values.</li>
 *   <li>Scan the neighbours once keeping the smallest difference {@code min} seen so far and the pairs that reach it.</li>
 *   <li>If a smaller difference is found, clear the list and start again with that pair.</li>
 *   <li>If it equals {@code min}, add the pair.</li>
 * </ol>
 *
 * <p><b>Example walkthrough</b>
 * <ul>
 *   <li>For {@code [4, 2, 1, 3]} the sorted array is {@code [1, 2, 3, 4]} and every neighbour pair differs by 1.</li>
 *   <li>Result: {@code [[1, 2], [2, 3], [3, 4]]}.</li>
 * </ul>
 *
 * <p><b>Complexity Analysis:</b>
 * <ul>
 *   <li><b>Time Complexity:</b> {@code O(n log n)} — Dominated by the sort.</li>
 *   <li><b>Space Complexity:</b> {@code O(n)} — The output (plus the sort).</li>
 * </ul>
 *
 * @author Ranga Reddy
 */
public class MinimumAbsoluteDifference {

    public static void main(String[] args) {
        int[][] inputs = {{4, 2, 1, 3}, {1, 3, 6, 10, 15}, {3, 8, -10, 23, 19, -4, -14, 27}};
        for (int[] arr : inputs) {
            System.out.println("Input  : arr = " + Arrays.toString(arr));
            System.out.println("Output : " + minimumAbsDifference(arr.clone()));
            System.out.println();
        }
    }

    public static List<List<Integer>> minimumAbsDifference(int[] arr) {
        List<List<Integer>> result = new ArrayList<>();
        if (arr == null || arr.length < 2) {
            return result;
        }
        Arrays.sort(arr);
        int min = Integer.MAX_VALUE;
        for (int i = 1; i < arr.length; i++) {
            int diff = arr[i] - arr[i - 1];
            if (diff < min) {
                min = diff;
                result.clear();
            }
            if (diff == min) {
                result.add(Arrays.asList(arr[i - 1], arr[i]));
            }
        }
        return result;
    }
}
