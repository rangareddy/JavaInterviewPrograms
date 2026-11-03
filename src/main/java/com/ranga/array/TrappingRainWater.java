package com.ranga.array;

import java.util.Arrays;

/**
 * Given {@code n} non-negative integers representing an elevation map where the width of each bar is 1, compute how much water it can trap after raining.
 *
 * <p><b>Algorithm Strategy (Two Pointers with Running Maxima):</b>
 * <ol>
 *   <li>The water above a bar is {@code min(highest bar to its left, highest bar to its right) - height of the bar}, never negative.</li>
 *   <li>Keep two pointers {@code left} and {@code right} at the ends and the highest bars seen so far from each side, {@code leftMax} and {@code rightMax}.</li>
 *   <li>Always move the pointer on the side with the smaller maximum: the water above that bar is limited by that smaller maximum (the other side has an even taller wall), so it can be added immediately as {@code max - height}.</li>
 *   <li>Update the maximum when a taller bar is met and continue until the pointers meet.</li>
 * </ol>
 *
 * <p><b>Example walkthrough</b>
 * <ul>
 *   <li>For {@code [4, 2, 0, 3, 2, 5]}: from the left, bars 2, 0, 3, 2 are lower than the left wall 4 and trap 2 + 4 + 1 + 2 = 9 units as long as the right wall (5) is taller.</li>
 *   <li>Result: {@code 9}.</li>
 * </ul>
 *
 * <p><b>Complexity Analysis:</b>
 * <ul>
 *   <li><b>Time Complexity:</b> {@code O(n)} — Each bar is visited once.</li>
 *   <li><b>Space Complexity:</b> {@code O(1)} — A few variables.</li>
 * </ul>
 *
 * @author Ranga Reddy
 */
public class TrappingRainWater {

    public static void main(String[] args) {
        int[][] inputs = {{0, 1, 0, 2, 1, 0, 1, 3, 2, 1, 2, 1}, {4, 2, 0, 3, 2, 5}, {3}, {}};
        for (int[] height : inputs) {
            System.out.println("Input  : height = " + Arrays.toString(height));
            System.out.println("Output : " + trap(height));
            System.out.println();
        }
    }

    public static int trap(int[] height) {
        if (height == null || height.length < 3) {
            return 0;
        }

        int left = 0;
        int right = height.length - 1;
        int leftMax = 0;
        int rightMax = 0;
        int water = 0;
        while (left < right) {
            if (height[left] < height[right]) {
                leftMax = Math.max(leftMax, height[left]);
                water += leftMax - height[left];
                left++;
            } else {
                rightMax = Math.max(rightMax, height[right]);
                water += rightMax - height[right];
                right--;
            }
        }
        return water;
    }
}
