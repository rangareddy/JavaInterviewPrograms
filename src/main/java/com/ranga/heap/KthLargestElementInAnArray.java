package com.ranga.heap;

import java.util.Arrays;
import java.util.Random;

/**
 * Given an integer array {@code nums} and an integer {@code k}, return the {@code k}-th largest element in sorted order (not the k-th distinct element). Try to solve it without fully sorting the array.
 *
 * <p><b>Algorithm Strategy (Quickselect with a Random Pivot):</b>
 * <ol>
 *   <li>The {@code k}-th largest element is the element that would be at index {@code n - k} if the array were sorted in ascending order.</li>
 *   <li>Partition the array around a random pivot with a three-way partition: elements smaller than, equal to and larger than the pivot.</li>
 *   <li>If the target index lies in the "equal" block, the pivot is the answer. Otherwise continue only in the side that contains the target index and ignore the other side.</li>
 *   <li>On average each round halves the search range, so the total work is linear; the random pivot makes the quadratic worst case extremely unlikely.</li>
 * </ol>
 *
 * <p><b>Example walkthrough</b>
 * <ul>
 *   <li>For {@code [3, 2, 1, 5, 6, 4]} and {@code k = 2} the target index is {@code 6 - 2 = 4} in sorted order {@code [1, 2, 3, 4, 5, 6]}.</li>
 *   <li>Partitioning narrows the range to the element at index 4: {@code 5}.</li>
 * </ul>
 *
 * <p><b>Complexity Analysis:</b>
 * <ul>
 *   <li><b>Time Complexity:</b> {@code O(n) average} — O(n^2) in the worst case with unlucky pivots.</li>
 *   <li><b>Space Complexity:</b> {@code O(1)} — In-place partitioning on a copy of the array.</li>
 * </ul>
 *
 * @author Ranga Reddy
 */
public class KthLargestElementInAnArray {

    public static void main(String[] args) {
        int[][] inputs = {{3, 2, 1, 5, 6, 4}, {3, 2, 3, 1, 2, 4, 5, 5, 6}, {1}, {7, 7, 7, 7}};
        int[] ks = {2, 4, 1, 3};
        for (int i = 0; i < inputs.length; i++) {
            System.out.println("Input  : nums = " + Arrays.toString(inputs[i]) + ", k = " + ks[i]);
            System.out.println("Output : " + findKthLargest(inputs[i], ks[i]));
            System.out.println();
        }
    }

    public static int findKthLargest(int[] nums, int k) {
        int[] a = nums.clone();                     // the input array stays unchanged
        int target = a.length - k;                  // index of the answer in ascending order
        int low = 0;
        int high = a.length - 1;
        Random random = new Random();
        while (low < high) {
            int pivot = a[low + random.nextInt(high - low + 1)];

            // three-way partition: [low, lt) < pivot, [lt, gt] == pivot, (gt, high] > pivot
            int lt = low;
            int i = low;
            int gt = high;
            while (i <= gt) {
                if (a[i] < pivot) {
                    swap(a, lt++, i++);
                } else if (a[i] > pivot) {
                    swap(a, i, gt--);
                } else {
                    i++;
                }
            }

            if (target < lt) {
                high = lt - 1;
            } else if (target > gt) {
                low = gt + 1;
            } else {
                return pivot;
            }
        }
        return a[target];
    }

    private static void swap(int[] a, int i, int j) {
        int temp = a[i];
        a[i] = a[j];
        a[j] = temp;
    }
}
