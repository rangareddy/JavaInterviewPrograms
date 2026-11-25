package com.ranga.greedy;

import java.util.ArrayDeque;
import java.util.Deque;

/**
 * Given a string {@code s} and an empty robot holding an empty string {@code t}, you may either move the first character of {@code s} to the end of {@code t}, or remove the last character of {@code t} and write it on paper. Return the lexicographically smallest string that can be written on paper.
 *
 * <p><b>Algorithm Strategy (Stack with the Smallest Remaining Letter):</b>
 * <ol>
 *   <li>Count the remaining letters of {@code s} so you can always know the smallest letter still to come.</li>
 *   <li>Push each character of {@code s} onto a stack (the robot's {@code t}), updating the counts.</li>
 *   <li>While the stack top is less than or equal to the smallest letter still remaining in {@code s}, pop it onto the paper: nothing better can appear later.</li>
 *   <li>At the end pop everything left.</li>
 * </ol>
 *
 * <p><b>Example walkthrough</b>
 * <ul>
 *   <li>For {@code bdda}: push b, d, d; the remaining {@code a} is smaller than all of them, so push {@code a} and write it first; then write d, d, b.</li>
 *   <li>Result: {@code addb}. For {@code zza} the answer is {@code azz}.</li>
 * </ul>
 *
 * <p><b>Complexity Analysis:</b>
 * <ul>
 *   <li><b>Time Complexity:</b> {@code O(n)} — Each character is pushed and popped once.</li>
 *   <li><b>Space Complexity:</b> {@code O(n)} — The stack.</li>
 * </ul>
 *
 * @author Ranga Reddy
 */
public class UsingARobotToPrintTheLexicographicallySmallestString {

    public static void main(String[] args) {
        String[] inputs = {"zza", "bac", "bdda", "a"};
        for (String s : inputs) {
            System.out.println("Input  : s = \"" + s + "\"");
            System.out.println("Output : " + robotWithString(s));
            System.out.println();
        }
    }

    public static String robotWithString(String s) {
        if (s == null || s.isEmpty()) {
            return "";
        }
        int[] count = new int[26];
        for (char c : s.toCharArray()) {
            count[c - 'a']++;
        }
        Deque<Character> stack = new ArrayDeque<>();
        StringBuilder paper = new StringBuilder();
        int smallest = 0;
        for (char c : s.toCharArray()) {
            stack.push(c);
            count[c - 'a']--;
            while (smallest < 25 && count[smallest] == 0) {
                smallest++;
            }
            while (!stack.isEmpty() && stack.peek() - 'a' <= smallest) {
                paper.append(stack.pop());
            }
        }
        return paper.toString();
    }
}
