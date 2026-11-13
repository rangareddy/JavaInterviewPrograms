package com.ranga.string;

import java.util.ArrayDeque;
import java.util.Deque;

/**
 * Given a string {@code s} of {@code (}, {@code )} and lowercase letters, remove the minimum number of parentheses (in any positions) so that the resulting string is valid, and return any valid result.
 *
 * <p><b>Algorithm Strategy (Stack of Indices to Remove):</b>
 * <ol>
 *   <li>Scan left to right with a stack of the indices of unmatched {@code (}.</li>
 *   <li>On {@code (} push its index. On {@code )}, if the stack is not empty pop it (a match), otherwise this {@code )} can never be matched, so mark its index for removal.</li>
 *   <li>After the scan, every index still on the stack is an unmatched {@code (}, so mark those for removal too.</li>
 *   <li>Build the answer from the characters whose indices are not marked. Letters are never removed.</li>
 * </ol>
 *
 * <p><b>Example walkthrough</b>
 * <ul>
 *   <li>For {@code "lee(t(c)o)de)"}: the {@code )} at the end finds an empty stack and is marked.</li>
 *   <li>Result: {@code "lee(t(c)o)de"}.</li>
 * </ul>
 *
 * <p><b>Complexity Analysis:</b>
 * <ul>
 *   <li><b>Time Complexity:</b> {@code O(n)} — One pass to mark and one pass to build the output.</li>
 *   <li><b>Space Complexity:</b> {@code O(n)} — The stack and the removal marks.</li>
 * </ul>
 *
 * @author Ranga Reddy
 */
public class MinimumRemoveToMakeValidParentheses {

    public static void main(String[] args) {
        String[] inputs = {"lee(t(c)o)de)", "a)b(c)d", "))((", "abc"};
        for (String s : inputs) {
            System.out.println("Input  : s = \"" + s + "\"");
            System.out.println("Output : \"" + minRemoveToMakeValid(s) + "\"");
            System.out.println();
        }
    }

    public static String minRemoveToMakeValid(String s) {
        boolean[] remove = new boolean[s.length()];
        Deque<Integer> open = new ArrayDeque<>();
        for (int i = 0; i < s.length(); i++) {
            char c = s.charAt(i);
            if (c == '(') {
                open.push(i);
            } else if (c == ')') {
                if (open.isEmpty()) {
                    remove[i] = true;
                } else {
                    open.pop();
                }
            }
        }
        while (!open.isEmpty()) {
            remove[open.pop()] = true;
        }
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < s.length(); i++) {
            if (!remove[i]) {
                sb.append(s.charAt(i));
            }
        }
        return sb.toString();
    }
}
