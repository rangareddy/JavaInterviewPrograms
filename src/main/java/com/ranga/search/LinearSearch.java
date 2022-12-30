package com.ranga.search;

import java.util.Arrays;

public class LinearSearch {
    public static void main(String[] args) {
        int[] arr = {12, 4, 5, 7, 9, 20, 30, 6, 15};
        Arrays.stream(arr).forEach(val -> System.out.print(val + " "));
        System.out.println();
        int element = 20;
        int index = linearSearch(arr, element);
        if (index != -1) {
            System.out.println(element + " found at index " + index);
        } else {
            System.out.println(element + " is not found");
        }
    }

    public static int linearSearch(int[] arr, int element) {
        for(int i=0; i< arr.length; i++) {
            if(arr[i] == element) {
                return i;
            }
        }
        return -1;
    }
}