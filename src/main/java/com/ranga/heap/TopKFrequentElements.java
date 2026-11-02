package com.ranga.heap;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Given an integer array {@code nums} and an integer {@code k}, return the {@code k} most frequent elements in any order.
 *
 * <p><b>Algorithm Strategy (Frequency Map + Bucket Sort):</b>
 * <ol>
 *   <li>Count the frequency of each number with a {@code HashMap}.</li>
 *   <li>Create buckets where index {@code f} holds the numbers that occur exactly {@code f} times; a frequency can never exceed {@code n}, so {@code n + 1} buckets are enough.</li>
 *   <li>Walk the buckets from the highest frequency down and collect numbers until {@code k} of them have been taken.</li>
 *   <li>This avoids sorting or a heap and runs in linear time. (Alternative: a min-heap of size {@code k} gives {@code O(n log k)}.)</li>
 * </ol>
 *
 * <p><b>Example walkthrough</b>
 * <ul>
 *   <li>For {@code [1, 1, 1, 2, 2, 3]} and {@code k = 2}: counts are 1 -> 3, 2 -> 2, 3 -> 1; buckets are [3: {1}], [2: {2}], [1: {3}].</li>
 *   <li>Taking from the top gives {@code [1, 2]}.</li>
 * </ul>
 *
 * <p><b>Complexity Analysis:</b>
 * <ul>
 *   <li><b>Time Complexity:</b> {@code O(n)} — Counting plus one pass over the buckets.</li>
 *   <li><b>Space Complexity:</b> {@code O(n)} — The map and the buckets.</li>
 * </ul>
 *
 * @author Ranga Reddy
 */
public class TopKFrequentElements {

    public static void main(String[] args) {
        int[][] inputs = {{1, 1, 1, 2, 2, 3}, {1}, {4, 4, 4, 5, 5, 6, 6, 6, 6}};
        int[] ks = {2, 1, 2};
        for (int i = 0; i < inputs.length; i++) {
            System.out.println("Input  : nums = " + Arrays.toString(inputs[i]) + ", k = " + ks[i]);
            System.out.println("Output : " + Arrays.toString(topKFrequent(inputs[i], ks[i])));
            System.out.println();
        }
    }

    public static int[] topKFrequent(int[] nums, int k) {
        Map<Integer, Integer> frequency = new HashMap<>();
        for (int num : nums) {
            frequency.merge(num, 1, Integer::sum);
        }

        @SuppressWarnings("unchecked")
        List<Integer>[] buckets = new List[nums.length + 1];
        for (Map.Entry<Integer, Integer> entry : frequency.entrySet()) {
            int count = entry.getValue();
            if (buckets[count] == null) {
                buckets[count] = new ArrayList<>();
            }
            buckets[count].add(entry.getKey());
        }

        int[] result = new int[k];
        int index = 0;
        for (int count = buckets.length - 1; count >= 0 && index < k; count--) {
            if (buckets[count] == null) {
                continue;
            }
            for (int num : buckets[count]) {
                if (index == k) {
                    break;
                }
                result[index++] = num;
            }
        }
        return result;
    }
}
