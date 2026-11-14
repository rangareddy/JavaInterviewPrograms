package com.ranga.stack;

import java.util.ArrayDeque;
import java.util.Deque;

/**
 * Given an encoded string such as {@code 3[a2[c]]}, return its decoded form. The rule is {@code k[encoded]}, where {@code encoded} is repeated exactly {@code k} times. The input is always valid, {@code k} is a positive integer, and brackets can be nested.
 *
 * <p><b>Algorithm Strategy (Two Stacks (Counts and Partial Strings)):</b>
 * <ol>
 *   <li>Scan the characters left to right, keeping the string being built ({@code current}) and the number being read ({@code k}).</li>
 *   <li>A digit extends {@code k} (a count can have several digits, such as {@code 12}).</li>
 *   <li>On {@code [}, push the pending {@code current} and {@code k} onto two stacks, then start a fresh {@code current} and reset {@code k}.</li>
 *   <li>On {@code ]}, pop the repeat count and the previous partial string, and set {@code current = previous + current repeated count times}.</li>
 *   <li>Letters are appended to {@code current}. When the scan ends, {@code current} is the decoded string.</li>
 * </ol>
 *
 * <p><b>Example walkthrough</b>
 * <ul>
 *   <li>For {@code 3[a2[c]]}: after {@code 3[a2[c} the stacks hold the counts {@code 3, 2} and the strings {@code "", "a"}, and {@code current = "c"}.</li>
 *   <li>The first {@code ]} gives {@code "a" + "cc" = "acc"}, the second gives {@code "" + "acc" * 3 = "accaccacc"}.</li>
 * </ul>
 *
 * <p><b>Complexity Analysis:</b>
 * <ul>
 *   <li><b>Time Complexity:</b> {@code O(n * maxK)} — More precisely proportional to the length of the decoded output, which can be much larger than the input.</li>
 *   <li><b>Space Complexity:</b> {@code O(n)} — The stacks hold at most one entry per open bracket, plus the output.</li>
 * </ul>
 *
 * @author Ranga Reddy
 */
public class DecodeString {

    public static void main(String[] args) {
        String[] inputs = {"3[a]2[bc]", "3[a2[c]]", "2[abc]3[cd]ef", "abc", "10[z]"};
        for (String s : inputs) {
            System.out.println("Input  : s = \"" + s + "\"");
            System.out.println("Output : \"" + decodeString(s) + "\"");
            System.out.println();
        }
    }

    public static String decodeString(String s) {
        Deque<Integer> counts = new ArrayDeque<>();
        Deque<StringBuilder> previous = new ArrayDeque<>();
        StringBuilder current = new StringBuilder();
        int k = 0;
        for (char c : s.toCharArray()) {
            if (Character.isDigit(c)) {
                k = k * 10 + (c - '0');
            } else if (c == '[') {
                counts.push(k);
                previous.push(current);
                current = new StringBuilder();
                k = 0;
            } else if (c == ']') {
                StringBuilder decoded = previous.pop();
                int repeat = counts.pop();
                for (int i = 0; i < repeat; i++) {
                    decoded.append(current);
                }
                current = decoded;
            } else {
                current.append(c);
            }
        }
        return current.toString();
    }
}
