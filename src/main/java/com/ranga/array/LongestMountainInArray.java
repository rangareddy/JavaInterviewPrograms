package com.ranga.array;

import java.util.Arrays;

/**
 * A mountain subarray has at least three elements, strictly increases to a peak and then strictly decreases. Given an integer array {@code arr}, return the length of the longest mountain subarray, or {@code 0} if there is none.
 *
 * <p><b>Algorithm Strategy (Single Pass over Peaks):</b>
 * <ol>
 *   <li>Scan with an index {@code i}. A peak is an index with {@code arr[i - 1] < arr[i] > arr[i + 1]}.</li>
 *   <li>For each peak, extend {@code left} while the values keep decreasing to the left and {@code right} while they keep decreasing to the right.</li>
 *   <li>The mountain length is {@code right - left + 1}; record the best.</li>
 *   <li>Continue the scan from {@code right + 1} so that each element is visited a constant number of times.</li>
 * </ol>
 *
 * <p><b>Example walkthrough</b>
 * <ul>
 *   <li>For {@code [2, 1, 4, 7, 3, 2, 5]}: the peak 7 has {@code 1, 4, 7, 3, 2} around it.</li>
 *   <li>Result: {@code 5}.</li>
 * </ul>
 *
 * <p><b>Complexity Analysis:</b>
 * <ul>
 *   <li><b>Time Complexity:</b> {@code O(n)} — Each element is part of at most one expansion.</li>
 *   <li><b>Space Complexity:</b> {@code O(1)} — A few indexes.</li>
 * </ul>
 *
 * @author Ranga Reddy
 */
public class LongestMountainInArray {

    public static void main(String[] args) {
        int[][] inputs = {{2, 1, 4, 7, 3, 2, 5}, {2, 2, 2}, {0, 1, 2, 3, 4, 5, 4, 3, 2, 1, 0}, {1, 2}, {}};
        for (int[] arr : inputs) {
            System.out.println("Input  : arr = " + Arrays.toString(arr));
            System.out.println("Output : " + longestMountain(arr));
            System.out.println();
        }
    }

    public static int longestMountain(int[] arr) {
        if (arr == null || arr.length < 3) {
            return 0;
        }
        int best = 0;
        int i = 1;
        while (i < arr.length - 1) {
            if (arr[i - 1] < arr[i] && arr[i] > arr[i + 1]) {
                int left = i - 1;
                while (left > 0 && arr[left - 1] < arr[left]) {
                    left--;
                }
                int right = i + 1;
                while (right < arr.length - 1 && arr[right] > arr[right + 1]) {
                    right++;
                }
                best = Math.max(best, right - left + 1);
                i = right + 1;
            } else {
                i++;
            }
        }
        return best;
    }
}
