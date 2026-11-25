package com.ranga.stack;

import java.util.ArrayDeque;
import java.util.Arrays;
import java.util.Deque;

/**
 * There are {@code n} people in a queue, numbered from left to right, with distinct heights {@code heights[i]}. A person can see the person to their right if everyone strictly between them is shorter than both. For each person, return the number of people they can see to their right.
 *
 * <p><b>Algorithm Strategy (Monotonic Stack from the Right):</b>
 * <ol>
 *   <li>Scan from right to left, keeping a stack of heights that is decreasing from the bottom to the top (the visible candidates to the right of the current person).</li>
 *   <li>While the stack top is shorter than the current person, pop it and count it: the current person sees that person.</li>
 *   <li>If the stack is still not empty, the person at the top is taller and also visible, so count one more.</li>
 *   <li>Push the current person and record the count.</li>
 * </ol>
 *
 * <p><b>Example walkthrough</b>
 * <ul>
 *   <li>For {@code [10, 6, 8, 5, 11, 9]}: the first person sees 6, 8 and 11, because 5 is hidden behind 8 and 9 behind 11.</li>
 *   <li>Result: {@code [3, 1, 2, 1, 1, 0]}.</li>
 * </ul>
 *
 * <p><b>Complexity Analysis:</b>
 * <ul>
 *   <li><b>Time Complexity:</b> {@code O(n)} — Each person is pushed and popped once.</li>
 *   <li><b>Space Complexity:</b> {@code O(n)} — The stack.</li>
 * </ul>
 *
 * @author Ranga Reddy
 */
public class NumberOfVisiblePeopleInAQueue {

    public static void main(String[] args) {
        int[][] inputs = {{10, 6, 8, 5, 11, 9}, {5, 1, 2, 3, 10}, {1}, {4, 3, 2, 1}};
        for (int[] heights : inputs) {
            System.out.println("Input  : heights = " + Arrays.toString(heights));
            System.out.println("Output : " + Arrays.toString(canSeePersonsCount(heights)));
            System.out.println();
        }
    }

    public static int[] canSeePersonsCount(int[] heights) {
        if (heights == null) {
            return new int[0];
        }
        int n = heights.length;
        int[] answer = new int[n];
        Deque<Integer> stack = new ArrayDeque<>();
        for (int i = n - 1; i >= 0; i--) {
            int seen = 0;
            while (!stack.isEmpty() && stack.peek() < heights[i]) {
                stack.pop();
                seen++;
            }
            if (!stack.isEmpty()) {
                seen++;
            }
            answer[i] = seen;
            stack.push(heights[i]);
        }
        return answer;
    }
}
