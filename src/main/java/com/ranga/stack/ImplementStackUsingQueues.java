package com.ranga.stack;

import java.util.LinkedList;
import java.util.Queue;

/**
 * Implement a last-in-first-out stack with {@code push}, {@code pop}, {@code top} and {@code empty} using only standard queue operations (add to the back, remove from the front, peek at the front, size, is-empty).
 *
 * <p><b>Algorithm Strategy (Single Queue with Rotation on Push):</b>
 * <ol>
 *   <li>A queue returns the oldest element first, but a stack must return the newest. Make the newest element the front of the queue.</li>
 *   <li>On {@code push(x)}, add {@code x} to the back, then rotate the queue: remove the front element and add it to the back, {@code size - 1} times. This moves every older element behind {@code x}.</li>
 *   <li>{@code pop}, {@code top} and {@code empty} are then plain queue operations on the front.</li>
 *   <li>The cost moves to {@code push}, which is {@code O(n)}; the other operations are {@code O(1)}.</li>
 * </ol>
 *
 * <p><b>Example walkthrough</b>
 * <ul>
 *   <li>{@code push(1)}: queue {@code [1]}. {@code push(2)}: add gives {@code [1, 2]}, rotate once gives {@code [2, 1]}.</li>
 *   <li>{@code push(3)}: add gives {@code [2, 1, 3]}, rotate twice gives {@code [3, 2, 1]}. {@code pop()} removes 3, the newest.</li>
 * </ul>
 *
 * <p><b>Complexity Analysis:</b>
 * <ul>
 *   <li><b>Time Complexity:</b> {@code O(n)} — {@code push} rotates the queue; {@code pop}, {@code top} and {@code empty} are {@code O(1)}.</li>
 *   <li><b>Space Complexity:</b> {@code O(n)} — One queue holds all elements.</li>
 * </ul>
 *
 * @author Ranga Reddy
 */
public class ImplementStackUsingQueues {

    public static void main(String[] args) {
        ImplementStackUsingQueues stack = new ImplementStackUsingQueues();
        System.out.println("Input  : push(1), push(2), push(3), top(), pop(), empty()");
        stack.push(1);
        stack.push(2);
        stack.push(3);
        int top = stack.top();
        int popped = stack.pop();
        System.out.println("Output : " + top + ", " + popped + ", " + stack.empty());
        System.out.println();
        System.out.println("Input  : pop(), pop(), empty()");
        int a = stack.pop();
        int b = stack.pop();
        System.out.println("Output : " + a + ", " + b + ", " + stack.empty());
    }

    private final Queue<Integer> queue = new LinkedList<>();

    public void push(int x) {
        queue.add(x);
        for (int i = 1; i < queue.size(); i++) {
            queue.add(queue.remove());
        }
    }

    public int pop() {
        return queue.remove();
    }

    public int top() {
        return queue.peek();
    }

    public boolean empty() {
        return queue.isEmpty();
    }
}
