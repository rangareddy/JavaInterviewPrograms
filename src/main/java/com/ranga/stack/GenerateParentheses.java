package com.ranga.stack;

import java.util.ArrayList;
import java.util.List;

/**
 * Given {@code n} pairs of parentheses, generate all combinations of well-formed parentheses.
 *
 * <p><b>Algorithm Strategy (Backtracking with Open and Close Counters):</b>
 * <ol>
 *   <li>Build the string character by character, tracking how many opening ({@code open}) and closing ({@code close}) parentheses have been used.</li>
 *   <li>An opening parenthesis may be added while {@code open < n}.</li>
 *   <li>A closing parenthesis may be added only while {@code close < open}, otherwise it would have no partner.</li>
 *   <li>When the string has length {@code 2n} it is a valid combination: record it. Trying "(" before ")" produces the results in lexicographic order.</li>
 * </ol>
 *
 * <p><b>Example walkthrough</b>
 * <ul>
 *   <li>For {@code n = 2}: the choices lead to {@code "(())"} (open, open, close, close) and {@code "()()"} (open, close, open, close).</li>
 *   <li>For {@code n = 3} there are 5 results, the Catalan number {@code C3}.</li>
 * </ul>
 *
 * <p><b>Complexity Analysis:</b>
 * <ul>
 *   <li><b>Time Complexity:</b> {@code O(4^n / sqrt(n))} — The number of valid combinations is the Catalan number {@code C(n)}, and each has length {@code 2n}.</li>
 *   <li><b>Space Complexity:</b> {@code O(n)} — Recursion depth (excluding the output).</li>
 * </ul>
 *
 * @author Ranga Reddy
 */
public class GenerateParentheses {

    public static void main(String[] args) {
        int[] inputs = {3, 1, 2, 4};
        for (int n : inputs) {
            System.out.println("Input  : n = " + n);
            System.out.println("Output : " + generateParenthesis(n));
            System.out.println();
        }
    }

    public static List<String> generateParenthesis(int n) {
        List<String> result = new ArrayList<>();
        backtrack(result, new StringBuilder(), 0, 0, n);
        return result;
    }

    private static void backtrack(List<String> result, StringBuilder current, int open, int close, int n) {
        if (current.length() == 2 * n) {
            result.add(current.toString());
            return;
        }
        if (open < n) {
            current.append('(');
            backtrack(result, current, open + 1, close, n);
            current.deleteCharAt(current.length() - 1);
        }
        if (close < open) {
            current.append(')');
            backtrack(result, current, open, close + 1, n);
            current.deleteCharAt(current.length() - 1);
        }
    }
}
