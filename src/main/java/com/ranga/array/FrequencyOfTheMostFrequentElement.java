package com.ranga.array;

import java.util.Arrays;

/**
 * The frequency of an element is how often it occurs in the array. In one operation you may increment any element by 1. Given an integer array {@code nums} and an integer {@code k}, return the maximum possible frequency of an element after performing at most {@code k} operations.
 *
 * <p><b>Algorithm Strategy (Sort and Slide a Window to the Right End):</b>
 * <ol>
 *   <li>Sort the array. For a window ending at {@code right}, the cheapest target value is {@code nums[right]} (raise everything else to it).</li>
 *   <li>The cost to raise the whole window is {@code nums[right] * windowLength - windowSum}.</li>
 *   <li>While that cost exceeds {@code k}, shrink from the left, subtracting {@code nums[left]} from the window sum.</li>
 *   <li>The best window length is the answer.</li>
 * </ol>
 *
 * <p><b>Example walkthrough</b>
 * <ul>
 *   <li>For {@code [1, 2, 4]} and {@code k = 5}: sorted {@code [1, 2, 4]}; raising 1 and 2 to 4 costs 3 + 2 = 5, within {@code k}.</li>
 *   <li>Result: {@code 3}.</li>
 * </ul>
 *
 * <p><b>Complexity Analysis:</b>
 * <ul>
 *   <li><b>Time Complexity:</b> {@code O(n log n)} — Sorting; the window scan is linear.</li>
 *   <li><b>Space Complexity:</b> {@code O(1)} — Apart from sorting.</li>
 * </ul>
 *
 * @author Ranga Reddy
 */
public class FrequencyOfTheMostFrequentElement {

    public static void main(String[] args) {
        int[][] nums = {{1, 2, 4}, {1, 4, 8, 13}, {3, 9, 6}, {7}};
        int[] ks = {5, 5, 2, 0};
        for (int i = 0; i < nums.length; i++) {
            System.out.println("Input  : nums = " + Arrays.toString(nums[i]) + ", k = " + ks[i]);
            System.out.println("Output : " + maxFrequency(nums[i].clone(), ks[i]));
            System.out.println();
        }
    }

    public static int maxFrequency(int[] nums, int k) {
        if (nums == null || nums.length == 0) {
            return 0;
        }
        Arrays.sort(nums);
        long windowSum = 0;
        int left = 0;
        int best = 1;
        for (int right = 0; right < nums.length; right++) {
            windowSum += nums[right];
            while ((long) nums[right] * (right - left + 1) - windowSum > k) {
                windowSum -= nums[left++];
            }
            best = Math.max(best, right - left + 1);
        }
        return best;
    }
}
