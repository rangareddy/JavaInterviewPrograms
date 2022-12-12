package com.ranga.search;

import java.util.Arrays;

/**
 * User: Ranga Reddy
 * Date: 11/15/2019
 * Time: 5:00 PM
 * Description:
 */
public class BinarySearch {
    public static void main(String[] args) {

        int[] arr = {12, 4, 5, 7, 9, 20, 30, 6, 15};
        Arrays.sort(arr);

        System.out.println("Array elements after sorting ");
        Arrays.stream(arr).forEach(val -> System.out.print(val + " "));
        System.out.println();

        int[] elements = {20, 16, 9, 4, 8};
        for (int element : elements) {
            int index = binarySearch(arr, element);
            if (index != -1) {
                System.out.println(element + " found at index " + index);
            } else {
                System.out.println(element + " is not found");
            }
        }

    }

    public static int binarySearch(int[] arr, int element) {
        int n = arr.length;
        int low = 0;
        int high = n - 1;
        while (low <= high) {
            int mid = (low + high) / 2;
            int val = arr[mid];
            if (val == element) {
                return mid;
            } else if (val < element) {
                low = mid + 1;
            } else {
                high = mid - 1;
            }
        }
        return -1;
    }
}