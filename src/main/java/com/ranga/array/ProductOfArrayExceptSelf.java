package com.ranga.array;

import java.util.Arrays;

/**
 * Utility class to compute the product of array except self without using division.
 *
 * <p><b>Algorithm Strategy (Prefix & Suffix Products):</b>
 * <ol>
 *   <li>Create a result array where {@code result[i]} stores the product of all elements to the left of {@code i}.</li>
 *   <li>Iterate backwards from the right, maintaining a running right product, and multiply it with
 *       {@code result[i]} to get the final product of elements to both the left and right of {@code i}.</li>
 * </ol>
 *
 * <p><b>Complexity Analysis:</b>
 * <ul>
 *   <li><b>Time Complexity:</b> {@code O(n)} — Two linear passes over the array.</li>
 *   <li><b>Space Complexity:</b> {@code O(1)} auxiliary space (excluding the output array).</li>
 * </ul>
 *
 * @author Ranga Reddy
 */

public class ProductOfArrayExceptSelf {

    public static void main(String[] args) {
        int[] arr1 = {1, 2, 3, 4};
        System.out.println("Input:  " + Arrays.toString(arr1));
        System.out.println("Output: " + Arrays.toString(getProductOfArray(arr1)));

        System.out.println();

        int[] arr2 = {1, 2, 3, 4, 0};
        System.out.println("Input:  " + Arrays.toString(arr2));
        System.out.println("Output: " + Arrays.toString(getProductOfArray(arr2)));
    }

    /**
     * Computes an array such that {@code result[i]} is equal to the product of all
     * the elements of {@code arr} except {@code arr[i]}.
     *
     * @param nums the input integer array
     * @return an array containing the product of array except self
     */
    private static int[] getProductOfArray(int[] nums) {
        if (nums == null || nums.length <= 1) {
            return nums;
        }
        int n = nums.length;
        int[] answer = new int[n];

        // 1. Left pass: store prefix products in 'answer'
        int prefix = 1;
        for (int i = 0; i < n; i++) {
            answer[i] = prefix;
            prefix *= nums[i];
        }

        // 2. Right pass: multiply suffix products with existing prefix products
        int suffix = 1;
        for (int i = n - 1; i >= 0; i--) {
            answer[i] *= suffix;
            suffix *= nums[i];
        }

        return answer;
    }

}
