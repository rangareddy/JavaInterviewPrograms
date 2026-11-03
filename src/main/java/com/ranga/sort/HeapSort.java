package com.ranga.sort;

import java.util.Arrays;

/**
 * Sort an array in ascending order in {@code O(n log n)} time and {@code O(1)} extra space by turning it into a max-heap and repeatedly moving the largest element to the end.
 *
 * <p><b>Algorithm Strategy (Build a Max-Heap, then Extract the Maximum):</b>
 * <ol>
 *   <li>A max-heap stored in an array keeps every parent larger than its children; the children of index {@code i} are {@code 2i + 1} and {@code 2i + 2}.</li>
 *   <li>Build the heap bottom-up: call {@code heapify} (sift down) for every non-leaf index, from {@code n / 2 - 1} down to 0.</li>
 *   <li>The largest element is now at index 0. Swap it with the last element of the heap, which puts it into its final position, and shrink the heap by one.</li>
 *   <li>Sift the new root down to restore the heap property and repeat until the heap has a single element.</li>
 * </ol>
 *
 * <p><b>Example walkthrough</b>
 * <ul>
 *   <li>For {@code [4, 10, 3, 5, 1]}: building the heap gives {@code [10, 5, 3, 4, 1]}; swapping 10 to the end and sifting down gives {@code [5, 4, 3, 1 | 10]}, then {@code [4, 1, 3 | 5, 10]} and so on.</li>
 *   <li>The result is {@code [1, 3, 4, 5, 10]}.</li>
 * </ul>
 *
 * <p><b>Complexity Analysis:</b>
 * <ul>
 *   <li><b>Time Complexity:</b> {@code O(n log n)} — Building the heap is O(n); each of the n extractions sifts down in O(log n).</li>
 *   <li><b>Space Complexity:</b> {@code O(1)} — In place, but not stable.</li>
 * </ul>
 *
 * @author Ranga Reddy
 */
public class HeapSort {

    public static void main(String[] args) {
        int[][] inputs = {{4, 10, 3, 5, 1}, {12, 4, 5, 7, 9, 20, 30, 6, 15}, {1, 2, 3, 4}, {4, 3, 2, 1}, {2, 2, 1, 1}};
        for (int[] arr : inputs) {
            System.out.println("Original Array : " + Arrays.toString(arr));
            heapSort(arr);
            System.out.println("Sorted Array   : " + Arrays.toString(arr));
            System.out.println();
        }
    }

    public static void heapSort(int[] arr) {
        int n = arr.length;
        for (int i = n / 2 - 1; i >= 0; i--) {
            siftDown(arr, n, i);
        }

        for (int end = n - 1; end > 0; end--) {
            swap(arr, 0, end);
            siftDown(arr, end, 0);
        }
    }

    private static void siftDown(int[] arr, int heapSize, int root) {
        while (true) {
            int largest = root;
            int left = 2 * root + 1;
            int right = 2 * root + 2;
            if (left < heapSize && arr[left] > arr[largest]) {
                largest = left;
            }
            if (right < heapSize && arr[right] > arr[largest]) {
                largest = right;
            }
            if (largest == root) {
                return;
            }
            swap(arr, root, largest);
            root = largest;
        }
    }

    private static void swap(int[] arr, int i, int j) {
        int temp = arr[i];
        arr[i] = arr[j];
        arr[j] = temp;
    }
}
