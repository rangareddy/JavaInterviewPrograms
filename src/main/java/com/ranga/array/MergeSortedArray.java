package com.ranga.array;

import java.util.Arrays;

/**
 * You are given two sorted integer arrays {@code nums1} (with {@code m} elements followed by {@code n} free slots at the end) and {@code nums2} (with {@code n} elements). Merge {@code nums2} into {@code nums1} so that {@code nums1} becomes one sorted array, in place.
 *
 * <p><b>Algorithm Strategy (Merge from the Back with Three Pointers):</b>
 * <ol>
 *   <li>Merging from the front would overwrite elements of {@code nums1} that have not been used yet, so fill the array from the back, where the free slots are.</li>
 *   <li>Keep three pointers: {@code i = m - 1} (last real element of {@code nums1}), {@code j = n - 1} (last element of {@code nums2}) and {@code k = m + n - 1} (next slot to fill).</li>
 *   <li>Place the larger of {@code nums1[i]} and {@code nums2[j]} at {@code nums1[k]} and move the corresponding pointer and {@code k} one step left.</li>
 *   <li>When {@code nums2} is used up, the remaining part of {@code nums1} is already in place. (If {@code nums1} runs out first, copy the rest of {@code nums2}.)</li>
 * </ol>
 *
 * <p><b>Example walkthrough</b>
 * <ul>
 *   <li>For {@code nums1 = [1, 2, 3, 0, 0, 0]}, {@code m = 3}, {@code nums2 = [2, 5, 6]}: fill from the back with 6, 5, 3, 2, 2, 1.</li>
 *   <li>Result: {@code [1, 2, 2, 3, 5, 6]}.</li>
 * </ul>
 *
 * <p><b>Complexity Analysis:</b>
 * <ul>
 *   <li><b>Time Complexity:</b> {@code O(m + n)} — Every element is placed once.</li>
 *   <li><b>Space Complexity:</b> {@code O(1)} — No extra array.</li>
 * </ul>
 *
 * @author Ranga Reddy
 */
public class MergeSortedArray {

    public static void main(String[] args) {
        int[][] first = {{1, 2, 3, 0, 0, 0}, {1}, {0}, {4, 5, 6, 0, 0, 0}};
        int[] sizes = {3, 1, 0, 3};
        int[][] second = {{2, 5, 6}, {}, {1}, {1, 2, 3}};
        for (int i = 0; i < first.length; i++) {
            System.out.println("Input  : nums1 = " + Arrays.toString(first[i]) + ", m = " + sizes[i] + ", nums2 = " + Arrays.toString(second[i]));
            merge(first[i], sizes[i], second[i], second[i].length);
            System.out.println("Output : " + Arrays.toString(first[i]));
            System.out.println();
        }
    }

    public static void merge(int[] nums1, int m, int[] nums2, int n) {
        int i = m - 1;
        int j = n - 1;
        int k = m + n - 1;
        while (j >= 0) {
            if (i >= 0 && nums1[i] > nums2[j]) {
                nums1[k--] = nums1[i--];
            } else {
                nums1[k--] = nums2[j--];
            }
        }
    }
}
