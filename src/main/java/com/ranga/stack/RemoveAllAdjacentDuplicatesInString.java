package com.ranga.stack;

/**
 * Given a string {@code s}, repeatedly remove two adjacent equal characters until no such pair remains. Return the final string.
 *
 * <p><b>Algorithm Strategy (Stack of Survivors):</b>
 * <ol>
 *   <li>Scan the characters left to right with a {@code StringBuilder} used as a stack.</li>
 *   <li>If the stack top equals the current character, pop it: the pair cancels.</li>
 *   <li>Otherwise push the current character.</li>
 *   <li>The stack content is the final string.</li>
 * </ol>
 *
 * <p><b>Example walkthrough</b>
 * <ul>
 *   <li>For {@code abbaca}: {@code bb} cancels, then the two {@code a}s become neighbours and cancel too, leaving {@code ca}.</li>
 *   <li>Result: {@code ca}.</li>
 * </ul>
 *
 * <p><b>Complexity Analysis:</b>
 * <ul>
 *   <li><b>Time Complexity:</b> {@code O(n)} — Each character is pushed and popped at most once.</li>
 *   <li><b>Space Complexity:</b> {@code O(n)} — The stack.</li>
 * </ul>
 *
 * @author Ranga Reddy
 */
public class RemoveAllAdjacentDuplicatesInString {

    public static void main(String[] args) {
        String[] inputs = {"abbaca", "azxxzy", "aaaa", "abc", ""};
        for (String s : inputs) {
            System.out.println("Input  : s = \"" + s + "\"");
            System.out.println("Output : \"" + removeDuplicates(s) + "\"");
            System.out.println();
        }
    }

    public static String removeDuplicates(String s) {
        if (s == null) {
            return "";
        }
        StringBuilder stack = new StringBuilder();
        for (char c : s.toCharArray()) {
            int top = stack.length() - 1;
            if (top >= 0 && stack.charAt(top) == c) {
                stack.deleteCharAt(top);
            } else {
                stack.append(c);
            }
        }
        return stack.toString();
    }
}
