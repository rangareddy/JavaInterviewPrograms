package com.ranga.stack;

import java.util.ArrayDeque;
import java.util.Deque;

/**
 * You are given a string {@code s} that consists of lower case English letters and brackets. Reverse the strings in each pair of matching parentheses, starting from the innermost one. The result should not contain any brackets.
 *
 * <p><b>Algorithm Strategy (Teleport Through Matching Parentheses):</b>
 * <ol>
 *   <li>With a stack, precompute for every parenthesis the index of its partner.</li>
 *   <li>Walk the string with a direction ({@code +1} or {@code -1}): start at index 0 moving right.</li>
 *   <li>When you meet a parenthesis, jump to its partner and reverse the direction; otherwise append the character and step.</li>
 *   <li>This writes every character exactly once, in the final order.</li>
 * </ol>
 *
 * <p><b>Example walkthrough</b>
 * <ul>
 *   <li>For {@code (abcd)}: the first {@code (} jumps to its partner at index 5 and walks left, writing {@code dcba}.</li>
 *   <li>Result: {@code dcba}.</li>
 * </ul>
 *
 * <p><b>Complexity Analysis:</b>
 * <ul>
 *   <li><b>Time Complexity:</b> {@code O(n)} — Pairing and walking are both linear.</li>
 *   <li><b>Space Complexity:</b> {@code O(n)} — The pair table and the stack.</li>
 * </ul>
 *
 * @author Ranga Reddy
 */
public class ReverseSubstringsBetweenEachPairOfParentheses {

    public static void main(String[] args) {
        String[] inputs = {"(abcd)", "(u(love)i)", "(ed(et(oc))el)", "a(bcdefghijkl(mno)p)q", "abc"};
        for (String s : inputs) {
            System.out.println("Input  : s = \"" + s + "\"");
            System.out.println("Output : " + reverseParentheses(s));
            System.out.println();
        }
    }

    public static String reverseParentheses(String s) {
        if (s == null || s.isEmpty()) {
            return "";
        }
        int n = s.length();
        int[] pair = new int[n];
        Deque<Integer> stack = new ArrayDeque<>();
        for (int i = 0; i < n; i++) {
            if (s.charAt(i) == '(') {
                stack.push(i);
            } else if (s.charAt(i) == ')') {
                int j = stack.pop();
                pair[i] = j;
                pair[j] = i;
            }
        }
        StringBuilder result = new StringBuilder();
        int direction = 1;
        for (int i = 0; i < n; i += direction) {
            char c = s.charAt(i);
            if (c == '(' || c == ')') {
                i = pair[i];
                direction = -direction;
            } else {
                result.append(c);
            }
        }
        return result.toString();
    }
}
