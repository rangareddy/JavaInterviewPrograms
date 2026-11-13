package com.ranga.stack;

import java.util.ArrayDeque;
import java.util.Deque;

/**
 * Implement a first-in-first-out queue with {@code push}, {@code pop}, {@code peek} and {@code empty} using only two stacks.
 *
 * <p><b>Algorithm Strategy (Input Stack and Output Stack (Amortized O(1))):</b>
 * <ol>
 *   <li>Pushes always go on the {@code input} stack, which keeps the newest element on top.</li>
 *   <li>Pops and peeks read from the {@code output} stack. When {@code output} is empty, move every element from {@code input} to {@code output} one by one; this reverses the order so the oldest element is now on top.</li>
 *   <li>Elements are moved only when {@code output} is empty, so each element is moved at most once in its lifetime.</li>
 *   <li>A single operation can cost {@code O(n)} (the one that triggers the transfer), but any sequence of {@code m} operations costs {@code O(m)} in total, which is the amortized {@code O(1)} guarantee.</li>
 * </ol>
 *
 * <p><b>Example walkthrough</b>
 * <ul>
 *   <li>{@code push(1)}, {@code push(2)}: {@code input = [2, 1]} (top first), {@code output = []}.</li>
 *   <li>{@code peek()} finds {@code output} empty, transfers to get {@code output = [1, 2]} and returns 1. {@code pop()} returns 1; {@code push(3)} goes to {@code input}; the next {@code pop()} returns 2 from {@code output} without any transfer.</li>
 * </ul>
 *
 * <p><b>Complexity Analysis:</b>
 * <ul>
 *   <li><b>Time Complexity:</b> {@code O(1) amortized} — {@code push} and {@code empty} are constant; {@code pop} and {@code peek} are constant amortized over a sequence of operations.</li>
 *   <li><b>Space Complexity:</b> {@code O(n)} — The elements are held in the two stacks.</li>
 * </ul>
 *
 * @author Ranga Reddy
 */
public class ImplementQueueUsingStacks {

    public static void main(String[] args) {
        ImplementQueueUsingStacks queue = new ImplementQueueUsingStacks();
        System.out.println("Input  : push(1), push(2), peek(), pop(), empty()");
        queue.push(1);
        queue.push(2);
        int peeked = queue.peek();
        int popped = queue.pop();
        System.out.println("Output : " + peeked + ", " + popped + ", " + queue.empty());
        System.out.println();
        System.out.println("Input  : push(3), pop(), pop(), empty()");
        queue.push(3);
        int first = queue.pop();
        int second = queue.pop();
        System.out.println("Output : " + first + ", " + second + ", " + queue.empty());
    }

    private final Deque<Integer> input = new ArrayDeque<>();
    private final Deque<Integer> output = new ArrayDeque<>();

    public void push(int x) {
        input.push(x);
    }

    public int pop() {
        shiftIfNeeded();
        return output.pop();
    }

    public int peek() {
        shiftIfNeeded();
        return output.peek();
    }

    public boolean empty() {
        return input.isEmpty() && output.isEmpty();
    }

    private void shiftIfNeeded() {
        if (output.isEmpty()) {
            while (!input.isEmpty()) {
                output.push(input.pop());
            }
        }
    }
}
