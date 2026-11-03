package com.ranga.array;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * Given two integer arrays {@code nums1} and {@code nums2}, return their intersection. Each element in the result must be unique (version I) or must appear as many times as it shows in both arrays (version II). The result may be in any order.
 *
 * <p><b>Algorithm Strategy (Hash Set for Unique Values, Hash Map of Counts for Duplicates):</b>
 * <ol>
 *   <li>Unique version: put all values of {@code nums1} into a {@code HashSet}.</li>
 *   <li>Scan {@code nums2}; every value that is in the set belongs to the intersection. Remove it from the set so it is reported only once.</li>
 *   <li>Duplicates version: count the values of the smaller array in a {@code HashMap}.</li>
 *   <li>Scan the other array; whenever a value has a positive count, add it to the result and decrement the count.</li>
 * </ol>
 *
 * <p><b>Example walkthrough</b>
 * <ul>
 *   <li>For {@code [1, 2, 2, 1]} and {@code [2, 2]}: the unique intersection is {@code [2]}, the one with duplicates is {@code [2, 2]}.</li>
 *   <li>For {@code [4, 9, 5]} and {@code [9, 4, 9, 8, 4]}: unique {@code [9, 4]}, with duplicates {@code [9, 4]}.</li>
 * </ul>
 *
 * <p><b>Complexity Analysis:</b>
 * <ul>
 *   <li><b>Time Complexity:</b> {@code O(m + n)} — One pass over each array.</li>
 *   <li><b>Space Complexity:</b> {@code O(min(m, n))} — The map or set of the smaller array.</li>
 * </ul>
 *
 * @author Ranga Reddy
 */
public class IntersectionOfTwoArrays {

    public static void main(String[] args) {
        int[][] first = {{1, 2, 2, 1}, {4, 9, 5}, {1, 2, 3}};
        int[][] second = {{2, 2}, {9, 4, 9, 8, 4}, {4, 5, 6}};
        for (int i = 0; i < first.length; i++) {
            System.out.println("Input  : nums1 = " + Arrays.toString(first[i]) + ", nums2 = " + Arrays.toString(second[i]));
            System.out.println("Output : unique = " + Arrays.toString(intersectionUnique(first[i], second[i]))
                    + ", with duplicates = " + Arrays.toString(intersectionWithDuplicates(first[i], second[i])));
            System.out.println();
        }
    }

    public static int[] intersectionUnique(int[] nums1, int[] nums2) {
        Set<Integer> seen = new HashSet<>();
        for (int num : nums1) {
            seen.add(num);
        }

        List<Integer> result = new ArrayList<>();
        for (int num : nums2) {
            if (seen.remove(num)) {
                result.add(num);
            }
        }
        return toArray(result);
    }

    public static int[] intersectionWithDuplicates(int[] nums1, int[] nums2) {
        if (nums1.length > nums2.length) {
            return intersectionWithDuplicates(nums2, nums1);
        }

        Map<Integer, Integer> counts = new HashMap<>();
        for (int num : nums1) {
            counts.merge(num, 1, Integer::sum);
        }

        List<Integer> result = new ArrayList<>();
        for (int num : nums2) {
            int count = counts.getOrDefault(num, 0);
            if (count > 0) {
                result.add(num);
                counts.put(num, count - 1);
            }
        }
        return toArray(result);
    }

    private static int[] toArray(List<Integer> list) {
        int[] array = new int[list.size()];
        for (int i = 0; i < array.length; i++) {
            array[i] = list.get(i);
        }
        return array;
    }
}
