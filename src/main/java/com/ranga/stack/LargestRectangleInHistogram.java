package com.ranga.stack;

import java.util.ArrayDeque;
import java.util.Arrays;
import java.util.Deque;

/**
 * Given an array {@code heights} representing the bar heights of a histogram where every bar has width 1, return the area of the largest rectangle in the histogram.
 *
 * <p><b>Algorithm Strategy (Monotonic Increasing Stack of Indices):</b>
 * <ol>
 *   <li>Keep a stack of bar indices whose heights are increasing. A bar stays on the stack while the rectangle it starts can still grow to the right.</li>
 *   <li>Process the bars from left to right (plus a virtual bar of height 0 at the end). When the current bar is lower than the bar on top, that top bar cannot extend any further: pop it.</li>
 *   <li>The popped bar of height {@code h} extends to the left until the new top of the stack (exclusive) and to the right until the current index (exclusive), so its width is {@code i - newTop - 1} (or {@code i} if the stack is empty). Its best rectangle is {@code h * width}.</li>
 *   <li>Push the current index and keep the largest area found.</li>
 * </ol>
 *
 * <p><b>Example walkthrough</b>
 * <ul>
 *   <li>For {@code [2, 1, 5, 6, 2, 3]}: when the bar of height 2 arrives, the bars 6 and 5 are popped giving areas {@code 6 * 1 = 6} and {@code 5 * 2 = 10}.</li>
 *   <li>The maximum area is {@code 10}.</li>
 * </ul>
 *
 * <p><b>Complexity Analysis:</b>
 * <ul>
 *   <li><b>Time Complexity:</b> {@code O(n)} — Each bar is pushed and popped once.</li>
 *   <li><b>Space Complexity:</b> {@code O(n)} — The stack.</li>
 * </ul>
 *
 * @author Ranga Reddy
 */
public class LargestRectangleInHistogram {

    public static void main(String[] args) {
        int[][] inputs = {{2, 1, 5, 6, 2, 3}, {2, 4}, {1}, {6, 2, 5, 4, 5, 1, 6}, {}};
        for (int[] heights : inputs) {
            System.out.println("Input  : heights = " + Arrays.toString(heights));
            System.out.println("Output : " + largestRectangleArea(heights));
            System.out.println();
        }
    }

    public static int largestRectangleArea(int[] heights) {
        Deque<Integer> stack = new ArrayDeque<>();
        int best = 0;
        for (int i = 0; i <= heights.length; i++) {
            int current = (i == heights.length) ? 0 : heights[i];       // virtual bar of height 0 flushes the stack
            while (!stack.isEmpty() && current < heights[stack.peek()]) {
                int height = heights[stack.pop()];
                int width = stack.isEmpty() ? i : i - stack.peek() - 1;
                best = Math.max(best, height * width);
            }
            stack.push(i);
        }
        return best;
    }
}
