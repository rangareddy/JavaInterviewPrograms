package com.ranga.stack;

import java.util.ArrayDeque;
import java.util.Deque;

/**
 * Given a string {@code s} and an integer {@code k}, a k duplicate removal chooses {@code k} adjacent and equal letters and removes them, concatenating the left and right parts. Repeat the removal until it is no longer possible and return the final string.
 *
 * <p><b>Algorithm Strategy (Stack of Character and Count):</b>
 * <ol>
 *   <li>Keep a stack where each entry is a character and the length of the current run of that character at the top.</li>
 *   <li>For each character, if it equals the top character, increase the run count; otherwise push a new entry with count 1.</li>
 *   <li>When a run count reaches {@code k}, pop the entry (the whole run disappears).</li>
 *   <li>Rebuild the answer from the entries left in the stack, repeating each character as many times as its count.</li>
 * </ol>
 *
 * <p><b>Example walkthrough</b>
 * <ul>
 *   <li>For {@code deeedbbcccbdaa} and {@code k = 3}: {@code eee} is removed, then {@code ccc}, then {@code bbb} (formed after the earlier removals), then {@code ddd}... leaving {@code aa}.</li>
 *   <li>Result: {@code aa}.</li>
 * </ul>
 *
 * <p><b>Complexity Analysis:</b>
 * <ul>
 *   <li><b>Time Complexity:</b> {@code O(n)} — Each character is pushed or counted once.</li>
 *   <li><b>Space Complexity:</b> {@code O(n)} — The stack.</li>
 * </ul>
 *
 * @author Ranga Reddy
 */
public class RemoveAllAdjacentDuplicatesInStringII {

    public static void main(String[] args) {
        String[] inputs = {"abcd", "deeedbbcccbdaa", "pbbcggttciiippooaais"};
        int[] ks = {2, 3, 2};
        for (int i = 0; i < inputs.length; i++) {
            System.out.println("Input  : s = \"" + inputs[i] + "\", k = " + ks[i]);
            System.out.println("Output : " + removeDuplicates(inputs[i], ks[i]));
            System.out.println();
        }
    }

    public static String removeDuplicates(String s, int k) {
        if (s == null || k <= 1) {
            return k == 1 ? "" : (s == null ? "" : s);
        }
        Deque<int[]> stack = new ArrayDeque<>();
        for (char c : s.toCharArray()) {
            if (!stack.isEmpty() && stack.peek()[0] == c) {
                if (++stack.peek()[1] == k) {
                    stack.pop();
                }
            } else {
                stack.push(new int[]{c, 1});
            }
        }
        StringBuilder result = new StringBuilder();
        for (int[] entry : stack) {
            for (int i = 0; i < entry[1]; i++) {
                result.append((char) entry[0]);
            }
        }
        return result.reverse().toString();
    }
}
