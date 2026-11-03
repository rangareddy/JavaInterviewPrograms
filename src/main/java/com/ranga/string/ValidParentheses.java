package com.ranga.string;

import java.util.ArrayDeque;
import java.util.Deque;

/**
 * Given a string containing only the characters {@code ( ) { } [ ]}, determine whether it is valid: every open bracket is closed by the same type of bracket, in the correct order.
 *
 * <p><b>Algorithm Strategy (Stack of Open Brackets):</b>
 * <ol>
 *   <li>Scan the string from left to right with a stack.</li>
 *   <li>Push every opening bracket (or, as done here, push the closing bracket that it expects).</li>
 *   <li>On a closing bracket the stack must be non-empty and its top must be that same bracket; otherwise the string is invalid.</li>
 *   <li>After the scan the stack must be empty, otherwise some bracket was never closed.</li>
 * </ol>
 *
 * <p><b>Example walkthrough</b>
 * <ul>
 *   <li>For {@code "([)]"}: push {@code )}, push {@code ]}; the next character {@code )} does not match the top {@code ]}, so the result is {@code false}.</li>
 *   <li>For {@code "()[]{}"} every closing bracket matches the top of the stack and the stack ends empty: {@code true}.</li>
 * </ul>
 *
 * <p><b>Complexity Analysis:</b>
 * <ul>
 *   <li><b>Time Complexity:</b> {@code O(n)} — Each character is pushed and popped at most once.</li>
 *   <li><b>Space Complexity:</b> {@code O(n)} — The stack may hold all opening brackets.</li>
 * </ul>
 *
 * @author Ranga Reddy
 */
public class ValidParentheses {

    public static void main(String[] args) {
        String[] inputs = {"()", "()[]{}", "(]", "([)]", "{[]}"};
        for (String s : inputs) {
            System.out.println("Input  : s = \"" + s + "\"");
            System.out.println("Output : " + isValid(s));
            System.out.println();
        }
    }

    public static boolean isValid(String s) {
        if (s == null || s.length() % 2 != 0) {
            return false;
        }

        Deque<Character> stack = new ArrayDeque<>();
        for (char c : s.toCharArray()) {
            if (c == '(') {
                stack.push(')');
            } else if (c == '{') {
                stack.push('}');
            } else if (c == '[') {
                stack.push(']');
            } else if (stack.isEmpty() || stack.pop() != c) {
                return false;
            }
        }
        return stack.isEmpty();
    }
}
