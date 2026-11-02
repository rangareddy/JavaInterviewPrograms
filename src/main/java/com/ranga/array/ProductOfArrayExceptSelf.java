package com.ranga.array;

import java.util.Arrays;

/**
 * Utility class to compute the product of array except self without using division.
 *
 * <p><b>Algorithm Strategy (Prefix & Suffix Products):</b>
 * <ol>
 *   <li>Without division, the product of all elements except {@code nums[i]} equals (product of everything to its left) x (product of everything to its right).</li>
 *   <li>Left pass: keep a running {@code prefix} (starting at 1). For each {@code i}, store {@code answer[i] = prefix}, then multiply {@code prefix} by {@code nums[i]}.</li>
 *   <li>Right pass: keep a running {@code suffix} (starting at 1). Iterate {@code i} from the end; multiply {@code answer[i]} by {@code suffix}, then multiply {@code suffix} by {@code nums[i]}.</li>
 *   <li>The output array is reused to hold the prefix products, so no extra arrays are needed; zeros are handled naturally.</li>
 * </ol>
 *
 * <p><b>Example walkthrough</b>
 * <ul>
 *   <li>For {@code [1, 2, 3, 4]}:</li>
 *   <li>After the left pass: {@code answer = [1, 1, 2, 6]}.</li>
 *   <li>Right pass multiplies by suffixes 1, 4, 12, 24 (from the right): {@code [24, 12, 8, 6]}.</li>
 * </ul>
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
        System.out.println("Input  : " + Arrays.toString(arr1));
        System.out.println("Output : " + Arrays.toString(getProductOfArray(arr1)));

        System.out.println();

        int[] arr2 = {1, 2, 3, 4, 0};
        System.out.println("Input  : " + Arrays.toString(arr2));
        System.out.println("Output : " + Arrays.toString(getProductOfArray(arr2)));
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
