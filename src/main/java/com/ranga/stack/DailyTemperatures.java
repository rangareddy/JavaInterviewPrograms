package com.ranga.stack;

import java.util.ArrayDeque;
import java.util.Arrays;
import java.util.Deque;

/**
 * Given an array {@code temperatures} of daily temperatures, return an array {@code answer} where {@code answer[i]} is the number of days after day {@code i} until a warmer temperature occurs, or 0 if there is no such day.
 *
 * <p><b>Algorithm Strategy (Monotonic Decreasing Stack of Indices):</b>
 * <ol>
 *   <li>Keep a stack of the indices of days that are still waiting for a warmer day; their temperatures are in decreasing order from bottom to top.</li>
 *   <li>For each day {@code i}, while the stack is not empty and today is warmer than the temperature at the top index {@code j}, pop {@code j}: its answer is {@code i - j}.</li>
 *   <li>Push {@code i} on the stack.</li>
 *   <li>Days left on the stack at the end never found a warmer day, so their answer stays 0.</li>
 * </ol>
 *
 * <p><b>Example walkthrough</b>
 * <ul>
 *   <li>For {@code [73, 74, 75, 71, 69, 72, 76, 73]}: day 1 (74) resolves day 0; day 2 (75) resolves day 1; day 6 (76) resolves days 5, 4, 3 and 2 with answers 1, 2, 3 and 4.</li>
 *   <li>Result: {@code [1, 1, 4, 2, 1, 1, 0, 0]}.</li>
 * </ul>
 *
 * <p><b>Complexity Analysis:</b>
 * <ul>
 *   <li><b>Time Complexity:</b> {@code O(n)} — Each index is pushed and popped at most once.</li>
 *   <li><b>Space Complexity:</b> {@code O(n)} — The stack.</li>
 * </ul>
 *
 * @author Ranga Reddy
 */
public class DailyTemperatures {

    public static void main(String[] args) {
        int[][] inputs = {{73, 74, 75, 71, 69, 72, 76, 73}, {30, 40, 50, 60}, {30, 60, 90}, {90, 80, 70}};
        for (int[] temperatures : inputs) {
            System.out.println("Input  : temperatures = " + Arrays.toString(temperatures));
            System.out.println("Output : " + Arrays.toString(dailyTemperatures(temperatures)));
            System.out.println();
        }
    }

    public static int[] dailyTemperatures(int[] temperatures) {
        int n = temperatures.length;
        int[] answer = new int[n];
        Deque<Integer> stack = new ArrayDeque<>();
        for (int i = 0; i < n; i++) {
            while (!stack.isEmpty() && temperatures[i] > temperatures[stack.peek()]) {
                int day = stack.pop();
                answer[day] = i - day;
            }
            stack.push(i);
        }
        return answer;
    }
}
