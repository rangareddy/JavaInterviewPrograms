package com.ranga.search;

import java.util.Arrays;

public class BinarySearch {
    public static void main(String[] args) {

        int[] arr = {12, 4, 5, 7, 9, 20, 30, 6, 15};
        Arrays.sort(arr);

        System.out.println("Array elements after sorting ");
        Arrays.stream(arr).forEach(val -> System.out.print(val + " "));
        System.out.println();

        int element = 20;
        int index = binarySearch(arr, element);
        if (index != -1) {
            System.out.println(element + " found at index " + index);
        } else {
            System.out.println(element + " is not found");
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