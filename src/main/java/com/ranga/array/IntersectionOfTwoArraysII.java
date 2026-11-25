package com.ranga.array;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Given two integer arrays {@code nums1} and {@code nums2}, return an array of their intersection. Each element in the result must appear as many times as it shows in both arrays, and the result may be in any order.
 *
 * <p><b>Algorithm Strategy (Count One Array, Consume with the Other):</b>
 * <ol>
 *   <li>Count the values of the smaller array in a {@code HashMap}.</li>
 *   <li>Walk the other array; whenever a value has a positive count, add it to the result and decrease its count.</li>
 *   <li>Each value therefore appears {@code min(count1, count2)} times.</li>
 *   <li>Convert the result list to an array.</li>
 * </ol>
 *
 * <p><b>Example walkthrough</b>
 * <ul>
 *   <li>For {@code [1, 2, 2, 1]} and {@code [2, 2]}: the counts of the first array are {@code {1: 2, 2: 2}}; both 2s in the second array are matched.</li>
 *   <li>Result: {@code [2, 2]}.</li>
 * </ul>
 *
 * <p><b>Complexity Analysis:</b>
 * <ul>
 *   <li><b>Time Complexity:</b> {@code O(m + n)} — One pass over each array.</li>
 *   <li><b>Space Complexity:</b> {@code O(min(m, n))} — The map of the smaller array.</li>
 * </ul>
 *
 * @author Ranga Reddy
 */
public class IntersectionOfTwoArraysII {

    public static void main(String[] args) {
        int[][] first = {{1, 2, 2, 1}, {4, 9, 5}, {1, 2}, {}};
        int[][] second = {{2, 2}, {9, 4, 9, 8, 4}, {3, 4}, {1}};
        for (int i = 0; i < first.length; i++) {
            int[] result = intersect(first[i], second[i]);
            Arrays.sort(result);
            System.out.println("Input  : nums1 = " + Arrays.toString(first[i]) + ", nums2 = " + Arrays.toString(second[i]));
            System.out.println("Output : " + Arrays.toString(result));
            System.out.println();
        }
    }

    public static int[] intersect(int[] nums1, int[] nums2) {
        if (nums1 == null || nums2 == null) {
            return new int[0];
        }
        if (nums1.length > nums2.length) {
            return intersect(nums2, nums1);
        }
        Map<Integer, Integer> counts = new HashMap<>();
        for (int num : nums1) {
            counts.merge(num, 1, Integer::sum);
        }
        List<Integer> result = new ArrayList<>();
        for (int num : nums2) {
            Integer left = counts.get(num);
            if (left != null && left > 0) {
                result.add(num);
                counts.put(num, left - 1);
            }
        }
        int[] answer = new int[result.size()];
        for (int i = 0; i < answer.length; i++) {
            answer[i] = result.get(i);
        }
        return answer;
    }
}
