package com.ranga.stack;

import java.util.ArrayDeque;
import java.util.Deque;

/**
 * Design a stack that supports {@code push}, {@code pop}, {@code top} and retrieving the minimum element, each in constant time.
 *
 * <p><b>Algorithm Strategy (Store the Minimum Together with Every Element):</b>
 * <ol>
 *   <li>Keep a single stack of pairs {@code (value, minimum so far)}.</li>
 *   <li>{@code push(x)}: the new minimum is {@code min(x, minimum of the current top)} (or {@code x} for an empty stack); push the pair.</li>
 *   <li>{@code pop()} and {@code top()} simply remove or read the top pair's value.</li>
 *   <li>{@code getMin()} returns the minimum stored in the top pair. Because every pair remembers the minimum of everything below it, the minimum is correct again right after a {@code pop}.</li>
 * </ol>
 *
 * <p><b>Example walkthrough</b>
 * <ul>
 *   <li>push(-2), push(0), push(-3) stores the pairs (-2,-2), (0,-2), (-3,-3); {@code getMin()} is -3.</li>
 *   <li>After {@code pop()} the top pair is (0,-2): {@code top()} is 0 and {@code getMin()} is -2.</li>
 * </ul>
 *
 * <p><b>Complexity Analysis:</b>
 * <ul>
 *   <li><b>Time Complexity:</b> {@code O(1)} — for every operation.</li>
 *   <li><b>Space Complexity:</b> {@code O(n)} — One pair per stored element.</li>
 * </ul>
 *
 * @author Ranga Reddy
 */
public class MinStack {

    private final Deque<int[]> stack = new ArrayDeque<>();      // {value, minimum of the stack up to this value}

    public static void main(String[] args) {
        MinStack minStack = new MinStack();
        System.out.println("Input  : push(-2), push(0), push(-3)");
        minStack.push(-2);
        minStack.push(0);
        minStack.push(-3);
        System.out.println("Output : getMin() = " + minStack.getMin());
        minStack.pop();
        System.out.println("Input  : pop()");
        System.out.println("Output : top() = " + minStack.top() + ", getMin() = " + minStack.getMin());
    }

    public void push(int value) {
        int min = stack.isEmpty() ? value : Math.min(value, stack.peek()[1]);
        stack.push(new int[]{value, min});
    }

    public void pop() {
        stack.pop();
    }

    public int top() {
        return stack.peek()[0];
    }

    public int getMin() {
        return stack.peek()[1];
    }
}
