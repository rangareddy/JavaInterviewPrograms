package com.ranga.design;

import java.util.ArrayDeque;
import java.util.Deque;
import java.util.HashMap;
import java.util.Map;

/**
 * Design a stack-like structure with {@code push(val)} and {@code pop()}. {@code pop()} removes and returns the most frequent element in the stack; if several elements share the highest frequency, it removes the one that was pushed closest to the top (most recently).
 *
 * <p><b>Algorithm Strategy (One Stack per Frequency Level):</b>
 * <ol>
 *   <li>Keep {@code freq[val]}, the number of times {@code val} is in the stack, and {@code groups[f]}, a stack of the values whose count has reached {@code f}.</li>
 *   <li>{@code push(val)} increments {@code freq[val]} to {@code f} and pushes {@code val} onto {@code groups[f]}; update {@code maxFreq} if needed.</li>
 *   <li>{@code pop()} takes the top of {@code groups[maxFreq]}, decrements its {@code freq}, and lowers {@code maxFreq} if that group is now empty.</li>
 *   <li>A value with count 3 appears in {@code groups[1]}, {@code groups[2]} and {@code groups[3]}, so popping from the top group respects recency among the most frequent.</li>
 * </ol>
 *
 * <p><b>Example walkthrough</b>
 * <ul>
 *   <li>Push 5, 7, 5, 7, 4, 5: the count of 5 is 3, the highest. {@code pop()} returns 5, then 7 (tied at 2 with 5, but pushed later), then 5, then 4.</li>
 *   <li>The first pop returns {@code 5}.</li>
 * </ul>
 *
 * <p><b>Complexity Analysis:</b>
 * <ul>
 *   <li><b>Time Complexity:</b> {@code O(1) per operation} — Map and stack operations.</li>
 *   <li><b>Space Complexity:</b> {@code O(n)} — The values across the groups.</li>
 * </ul>
 *
 * @author Ranga Reddy
 */
public class MaximumFrequencyStack {

    public static void main(String[] args) {
        MaximumFrequencyStack stack = new MaximumFrequencyStack();
        for (int x : new int[]{5, 7, 5, 7, 4, 5}) {
            stack.push(x);
        }
        System.out.println("Input  : push 5, 7, 5, 7, 4, 5, then pop()");
        System.out.println("Output : " + stack.pop());
        System.out.println();
        System.out.println("Input  : pop(), pop(), pop()");
        System.out.println("Output : " + stack.pop() + ", " + stack.pop() + ", " + stack.pop());
    }

    private final Map<Integer, Integer> freq = new HashMap<>();
    private final Map<Integer, Deque<Integer>> groups = new HashMap<>();
    private int maxFreq;

    public void push(int val) {
        int f = freq.merge(val, 1, Integer::sum);
        maxFreq = Math.max(maxFreq, f);
        groups.computeIfAbsent(f, k -> new ArrayDeque<>()).push(val);
    }

    public int pop() {
        Deque<Integer> top = groups.get(maxFreq);
        int val = top.pop();
        if (top.isEmpty()) {
            groups.remove(maxFreq);
            maxFreq--;
        }
        if (freq.merge(val, -1, Integer::sum) == 0) {
            freq.remove(val);
        }
        return val;
    }
}
