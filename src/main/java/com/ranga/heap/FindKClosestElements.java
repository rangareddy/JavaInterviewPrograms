package com.ranga.heap;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Given a sorted integer array {@code arr}, two integers {@code k} and {@code x}, return the {@code k} closest integers to {@code x} in the array, sorted in ascending order. An integer {@code a} is closer than {@code b} if {@code |a - x| < |b - x|}, or the distances are equal and {@code a < b}.
 *
 * <p><b>Algorithm Strategy (Binary Search for the Window Start):</b>
 * <ol>
 *   <li>The answer is a window of {@code k} consecutive elements, so only its start index {@code left} in {@code [0, n - k]} needs to be found.</li>
 *   <li>Binary search {@code left}: compare {@code x - arr[mid]} with {@code arr[mid + k] - x}.</li>
 *   <li>If {@code x - arr[mid] > arr[mid + k] - x}, the window should move right: {@code lo = mid + 1}; otherwise {@code hi = mid}.</li>
 *   <li>Return {@code arr[lo .. lo + k - 1]}.</li>
 * </ol>
 *
 * <p><b>Example walkthrough</b>
 * <ul>
 *   <li>For {@code [1, 2, 3, 4, 5]}, {@code k = 4}, {@code x = 3}: the window starting at 0 ({@code 1..4}) is as close as the one starting at 1, and ties favour the smaller values.</li>
 *   <li>Result: {@code [1, 2, 3, 4]}.</li>
 * </ul>
 *
 * <p><b>Complexity Analysis:</b>
 * <ul>
 *   <li><b>Time Complexity:</b> {@code O(log(n - k) + k)} — The search plus copying the window.</li>
 *   <li><b>Space Complexity:</b> {@code O(1)} — Apart from the output.</li>
 * </ul>
 *
 * @author Ranga Reddy
 */
public class FindKClosestElements {

    public static void main(String[] args) {
        int[][] arrs = {{1, 2, 3, 4, 5}, {1, 1, 2, 3, 4, 5}, {1, 3}, {1, 2, 3, 4, 5}};
        int[] ks = {4, 4, 1, 2};
        int[] xs = {3, -1, 2, 6};
        for (int i = 0; i < arrs.length; i++) {
            System.out.println("Input  : arr = " + Arrays.toString(arrs[i]) + ", k = " + ks[i] + ", x = " + xs[i]);
            System.out.println("Output : " + findClosestElements(arrs[i], ks[i], xs[i]));
            System.out.println();
        }
    }

    public static List<Integer> findClosestElements(int[] arr, int k, int x) {
        List<Integer> result = new ArrayList<>();
        if (arr == null || k <= 0 || k > arr.length) {
            return result;
        }
        int lo = 0;
        int hi = arr.length - k;
        while (lo < hi) {
            int mid = lo + (hi - lo) / 2;
            if (x - arr[mid] > arr[mid + k] - x) {
                lo = mid + 1;
            } else {
                hi = mid;
            }
        }
        for (int i = lo; i < lo + k; i++) {
            result.add(arr[i]);
        }
        return result;
    }
}
