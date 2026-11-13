package com.ranga.heap;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.PriorityQueue;

/**
 * Given two integer arrays {@code nums1} and {@code nums2} sorted in non-decreasing order and an integer {@code k}, return the {@code k} pairs {@code (u, v)} with the smallest sums, where {@code u} is from {@code nums1} and {@code v} is from {@code nums2}.
 *
 * <p><b>Algorithm Strategy (Min-Heap Expanding Along Rows):</b>
 * <ol>
 *   <li>Think of all pairs as a matrix whose entry {@code (i, j)} is {@code nums1[i] + nums2[j]}. Every row and every column is sorted, so the smallest unvisited sum is always at the start of a row or just after a visited entry.</li>
 *   <li>Seed the heap with {@code (i, 0)} for the first {@code min(k, n)} rows: row {@code i} pairs {@code nums1[i]} with the smallest element of {@code nums2}.</li>
 *   <li>Pop the smallest sum {@code (i, j)} and add the pair to the answer. Then push {@code (i, j + 1)} if it exists, the next candidate in the same row.</li>
 *   <li>Stop after {@code k} pairs or when the heap is empty. Only the first {@code k} rows can contribute, because row {@code k} cannot beat the pairs of rows {@code 0..k-1} that use the same element of {@code nums2}.</li>
 * </ol>
 *
 * <p><b>Example walkthrough</b>
 * <ul>
 *   <li>For {@code nums1 = [1, 7, 11]}, {@code nums2 = [2, 4, 6]}, {@code k = 3}: the heap starts with sums 3, 9, 13.</li>
 *   <li>Pop {@code (1, 2)}, push {@code (1, 4)} with sum 5; pop it, push {@code (1, 6)} with sum 7; pop it. Result: {@code [[1, 2], [1, 4], [1, 6]]}.</li>
 * </ul>
 *
 * <p><b>Complexity Analysis:</b>
 * <ul>
 *   <li><b>Time Complexity:</b> {@code O(k log k)} — At most {@code k} pops from a heap that never holds more than {@code k} entries.</li>
 *   <li><b>Space Complexity:</b> {@code O(k)} — The heap.</li>
 * </ul>
 *
 * @author Ranga Reddy
 */
public class FindKPairsWithSmallestSums {

    public static void main(String[] args) {
        int[][][] inputs = {{{1, 7, 11}, {2, 4, 6}}, {{1, 1, 2}, {1, 2, 3}}, {{1, 2}, {3}}};
        int[] ks = {3, 2, 3};
        for (int i = 0; i < inputs.length; i++) {
            System.out.println("Input  : nums1 = " + Arrays.toString(inputs[i][0]) + ", nums2 = " + Arrays.toString(inputs[i][1]) + ", k = " + ks[i]);
            System.out.println("Output : " + kSmallestPairs(inputs[i][0], inputs[i][1], ks[i]));
            System.out.println();
        }
    }

    public static List<List<Integer>> kSmallestPairs(int[] nums1, int[] nums2, int k) {
        List<List<Integer>> result = new ArrayList<>();
        if (nums1.length == 0 || nums2.length == 0 || k <= 0) {
            return result;
        }
        PriorityQueue<int[]> heap = new PriorityQueue<>((a, b) -> Integer.compare(a[0], b[0]));
        for (int i = 0; i < Math.min(k, nums1.length); i++) {
            heap.add(new int[]{nums1[i] + nums2[0], i, 0});
        }
        while (!heap.isEmpty() && result.size() < k) {
            int[] top = heap.poll();
            int i = top[1];
            int j = top[2];
            result.add(Arrays.asList(nums1[i], nums2[j]));
            if (j + 1 < nums2.length) {
                heap.add(new int[]{nums1[i] + nums2[j + 1], i, j + 1});
            }
        }
        return result;
    }
}
