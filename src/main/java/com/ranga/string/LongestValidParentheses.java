package com.ranga.string;

import java.util.ArrayDeque;
import java.util.Deque;

/**
 * Given a string containing only {@code (} and {@code )}, return the length of the longest valid (well-formed) parentheses substring.
 *
 * <p><b>Algorithm Strategy (Stack of Indices):</b>
 * <ol>
 *   <li>Keep a stack of indices. Start with {@code -1} on it: this is the index just before the current candidate substring.</li>
 *   <li>On {@code (}, push its index.</li>
 *   <li>On {@code )}, pop. If the stack becomes empty, this {@code )} is unmatched, so push its index as the new base. Otherwise the length of the valid run ending here is {@code i - stack.peek()}, so update the best length.</li>
 *   <li>The index left on top of the stack is always the position of the last unmatched character, which is why subtracting it gives the length of the current valid run (including adjacent valid pieces such as {@code ()()}).</li>
 * </ol>
 *
 * <p><b>Example walkthrough</b>
 * <ul>
 *   <li>For {@code ")()())"}: the stack starts as {@code [-1]}; index 0 {@code )} pops {@code -1} and pushes 0.</li>
 *   <li>Index 2 gives length {@code 2 - 0 = 2}, index 4 gives {@code 4 - 0 = 4}, index 5 pops 0 and pushes 5.</li>
 *   <li>Result: {@code 4}.</li>
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
public class LongestValidParentheses {

    public static void main(String[] args) {
        String[] inputs = {"(()", ")()())", "", "()(()", "()(())"};
        for (String s : inputs) {
            System.out.println("Input  : s = \"" + s + "\"");
            System.out.println("Output : " + longestValidParentheses(s));
            System.out.println();
        }
    }

    public static int longestValidParentheses(String s) {
        Deque<Integer> stack = new ArrayDeque<>();
        stack.push(-1);
        int best = 0;
        for (int i = 0; i < s.length(); i++) {
            if (s.charAt(i) == '(') {
                stack.push(i);
            } else {
                stack.pop();
                if (stack.isEmpty()) {
                    stack.push(i);
                } else {
                    best = Math.max(best, i - stack.peek());
                }
            }
        }
        return best;
    }
}
