package com.ranga.stack;

/**
 * Given a string {@code s}, return the lexicographically smallest subsequence of {@code s} that contains all the distinct characters of {@code s} exactly once.
 *
 * <p><b>Algorithm Strategy (Monotonic Stack with Last Occurrence):</b>
 * <ol>
 *   <li>Record the last index of every character.</li>
 *   <li>Walk the string; skip a character that is already on the stack.</li>
 *   <li>While the stack top is greater than the current character and the top occurs again later (its last index is after {@code i}), pop it and unmark it.</li>
 *   <li>Push the current character and mark it. The stack, read from the bottom, is the answer.</li>
 * </ol>
 *
 * <p><b>Example walkthrough</b>
 * <ul>
 *   <li>For {@code cbacdcbc}: {@code c}, then {@code b}, then {@code a} pops {@code b} and {@code c} (both occur later); then {@code c}, {@code d} follow, and the later {@code b} is pushed at the end.</li>
 *   <li>Result: {@code acdb}.</li>
 * </ul>
 *
 * <p><b>Complexity Analysis:</b>
 * <ul>
 *   <li><b>Time Complexity:</b> {@code O(n)} — Each character is pushed and popped once.</li>
 *   <li><b>Space Complexity:</b> {@code O(1)} — At most 26 characters on the stack.</li>
 * </ul>
 *
 * @author Ranga Reddy
 */
public class SmallestSubsequenceOfDistinctCharacters {

    public static void main(String[] args) {
        String[] inputs = {"cbacdcbc", "bcabc", "abcd", "", "ecbacba"};
        for (String s : inputs) {
            System.out.println("Input  : s = \"" + s + "\"");
            System.out.println("Output : " + smallestSubsequence(s));
            System.out.println();
        }
    }

    public static String smallestSubsequence(String s) {
        if (s == null || s.isEmpty()) {
            return "";
        }
        int[] last = new int[26];
        for (int i = 0; i < s.length(); i++) {
            last[s.charAt(i) - 'a'] = i;
        }
        boolean[] used = new boolean[26];
        StringBuilder stack = new StringBuilder();
        for (int i = 0; i < s.length(); i++) {
            char c = s.charAt(i);
            if (used[c - 'a']) {
                continue;
            }
            while (stack.length() > 0 && stack.charAt(stack.length() - 1) > c && last[stack.charAt(stack.length() - 1) - 'a'] > i) {
                used[stack.charAt(stack.length() - 1) - 'a'] = false;
                stack.setLength(stack.length() - 1);
            }
            stack.append(c);
            used[c - 'a'] = true;
        }
        return stack.toString();
    }
}
