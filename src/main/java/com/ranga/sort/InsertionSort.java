package com.ranga.sort;

import java.util.Arrays;

/**
 * Sort an array in ascending order by repeatedly taking the next element and inserting it into its correct place in the already sorted part on its left, the way most people sort playing cards in their hand.
 *
 * <p><b>Algorithm Strategy (Grow a Sorted Prefix One Element at a Time):</b>
 * <ol>
 *   <li>Treat the first element as a sorted prefix of length 1.</li>
 *   <li>For every following element {@code key = arr[i]}, look at the sorted prefix from right to left.</li>
 *   <li>Shift every element that is greater than {@code key} one position to the right to open a gap.</li>
 *   <li>Put {@code key} into the gap. After processing index {@code i} the prefix {@code arr[0..i]} is sorted.</li>
 * </ol>
 *
 * <p><b>Example walkthrough</b>
 * <ul>
 *   <li>For {@code [5, 2, 4, 6, 1, 3]}: insert 2 -> {@code [2, 5, 4, 6, 1, 3]}, insert 4 -> {@code [2, 4, 5, 6, 1, 3]}, 6 stays, insert 1 -> {@code [1, 2, 4, 5, 6, 3]}, insert 3 -> {@code [1, 2, 3, 4, 5, 6]}.</li>
 *   <li>An already sorted array needs only one comparison per element, which makes the algorithm very fast on nearly sorted data.</li>
 * </ul>
 *
 * <p><b>Complexity Analysis:</b>
 * <ul>
 *   <li><b>Time Complexity:</b> {@code O(n^2) worst/average, O(n) best} — The best case is an already sorted array; the worst case is a reversed array.</li>
 *   <li><b>Space Complexity:</b> {@code O(1)} — Sorts in place; it is stable (equal elements keep their order).</li>
 * </ul>
 *
 * @author Ranga Reddy
 */
public class InsertionSort {

    public static void main(String[] args) {
        int[][] inputs = {{5, 2, 4, 6, 1, 3}, {12, 4, 5, 7, 9, 20, 30, 6, 15}, {1, 2, 3, 4}, {4, 3, 2, 1}, {7}};
        for (int[] arr : inputs) {
            System.out.println("Original Array : " + Arrays.toString(arr));
            insertionSort(arr);
            System.out.println("Sorted Array   : " + Arrays.toString(arr));
            System.out.println();
        }
    }

    public static void insertionSort(int[] arr) {
        for (int i = 1; i < arr.length; i++) {
            int key = arr[i];
            int j = i - 1;
            while (j >= 0 && arr[j] > key) {
                arr[j + 1] = arr[j];
                j--;
            }
            arr[j + 1] = key;
        }
    }
}
