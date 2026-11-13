package com.ranga.stack;

import java.util.ArrayDeque;
import java.util.Arrays;
import java.util.Deque;
import java.util.HashMap;
import java.util.Map;

/**
 * Given two arrays of distinct integers {@code nums1} and {@code nums2}, where {@code nums1} is a subset of {@code nums2}, return for each {@code nums1[i]} the first element to its right in {@code nums2} that is greater than it, or {@code -1} if there is none.
 *
 * <p><b>Algorithm Strategy (Monotonic Decreasing Stack and a Lookup Map):</b>
 * <ol>
 *   <li>Compute the next greater element for every value in {@code nums2} once, and answer each query of {@code nums1} from a map.</li>
 *   <li>Scan {@code nums2} left to right with a stack of values that are still waiting for a greater element (decreasing from bottom to top).</li>
 *   <li>For each {@code x}, while the top of the stack is smaller than {@code x}, pop it and record {@code x} as its next greater element.</li>
 *   <li>Push {@code x}. Values left on the stack at the end have no greater element, so a lookup defaults to {@code -1}.</li>
 * </ol>
 *
 * <p><b>Example walkthrough</b>
 * <ul>
 *   <li>For {@code nums2 = [1, 3, 4, 2]}: 3 resolves 1, 4 resolves 3, and 4 and 2 stay on the stack.</li>
 *   <li>The map is {@code {1: 3, 3: 4}}. For {@code nums1 = [4, 1, 2]} the answer is {@code [-1, 3, -1]}.</li>
 * </ul>
 *
 * <p><b>Complexity Analysis:</b>
 * <ul>
 *   <li><b>Time Complexity:</b> {@code O(m + n)} — One pass over {@code nums2} and one over {@code nums1}, where each value is pushed and popped once.</li>
 *   <li><b>Space Complexity:</b> {@code O(n)} — The stack and the map.</li>
 * </ul>
 *
 * @author Ranga Reddy
 */
public class NextGreaterElementI {

    public static void main(String[] args) {
        int[][][] inputs = {{{4, 1, 2}, {1, 3, 4, 2}}, {{2, 4}, {1, 2, 3, 4}}, {{1}, {1}}};
        for (int[][] in : inputs) {
            System.out.println("Input  : nums1 = " + Arrays.toString(in[0]) + ", nums2 = " + Arrays.toString(in[1]));
            System.out.println("Output : " + Arrays.toString(nextGreaterElement(in[0], in[1])));
            System.out.println();
        }
    }

    public static int[] nextGreaterElement(int[] nums1, int[] nums2) {
        Map<Integer, Integer> nextGreater = new HashMap<>();
        Deque<Integer> stack = new ArrayDeque<>();
        for (int x : nums2) {
            while (!stack.isEmpty() && stack.peek() < x) {
                nextGreater.put(stack.pop(), x);
            }
            stack.push(x);
        }
        int[] result = new int[nums1.length];
        for (int i = 0; i < nums1.length; i++) {
            result[i] = nextGreater.getOrDefault(nums1[i], -1);
        }
        return result;
    }
}
