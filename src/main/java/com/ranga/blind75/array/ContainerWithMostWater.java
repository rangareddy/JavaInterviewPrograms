package com.ranga.blind75.array;

import java.util.Arrays;

/**
 * Given an array {@code height} where {@code height[i]} is the height of a vertical line at position {@code i}, find two lines that together with the x-axis hold the most water, and return that amount.
 *
 * <p><b>Algorithm Strategy (Two Pointers from Both Ends):</b>
 * <ol>
 *   <li>The water held by lines {@code i < j} is {@code (j - i) * min(height[i], height[j])}.</li>
 *   <li>Start with the widest container: {@code left = 0}, {@code right = n - 1}, and record its area.</li>
 *   <li>Move the pointer at the shorter line inward. Moving the taller one can never help, because the width shrinks and the height is still limited by the shorter line.</li>
 *   <li>Repeat until the pointers meet and return the largest area seen.</li>
 * </ol>
 *
 * <p><b>Example walkthrough</b>
 * <ul>
 *   <li>For {@code [1, 8, 6, 2, 5, 4, 8, 3, 7]}: the first area is {@code 8 * min(1, 7) = 8}; moving past the short 1 eventually pairs the 8 at index 1 with the 7 at index 8.</li>
 *   <li>That container has area {@code 7 * min(8, 7) = 49}, the maximum.</li>
 * </ul>
 *
 * <p><b>Complexity Analysis:</b>
 * <ul>
 *   <li><b>Time Complexity:</b> {@code O(n)} — Each pointer moves inward at most {@code n} times in total.</li>
 *   <li><b>Space Complexity:</b> {@code O(1)} — Two pointers and the best area.</li>
 * </ul>
 *
 * @author Ranga Reddy
 */
public class ContainerWithMostWater {

    public static void main(String[] args) {
        int[][] inputs = {{1, 8, 6, 2, 5, 4, 8, 3, 7}, {1, 1}};
        for (int[] height : inputs) {
            System.out.println("Input  : height = " + Arrays.toString(height));
            System.out.println("Output : " + maxArea(height));
            System.out.println();
        }
    }

    public static int maxArea(int[] height) {
        if (height == null || height.length < 2) {
            return 0;
        }

        int left = 0;
        int right = height.length - 1;
        int best = 0;
        while (left < right) {
            int area = (right - left) * Math.min(height[left], height[right]);
            best = Math.max(best, area);
            if (height[left] < height[right]) {
                left++;
            } else {
                right--;
            }
        }
        return best;
    }
}
