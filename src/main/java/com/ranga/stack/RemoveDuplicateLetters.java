package com.ranga.stack;

/**
 * Given a string {@code s}, remove duplicate letters so that every letter appears exactly once, and the result is the smallest in lexicographical order among all possible results.
 *
 * <p><b>Algorithm Strategy (Monotonic Stack with Last Occurrence):</b>
 * <ol>
 *   <li>Record the last index of every letter.</li>
 *   <li>Scan the string. Skip a letter that is already on the stack.</li>
 *   <li>While the stack top is larger than the current letter and the top appears again later (its last index is greater than the current index), pop it.</li>
 *   <li>Push the current letter and mark it as used. The stack is the answer.</li>
 * </ol>
 *
 * <p><b>Example walkthrough</b>
 * <ul>
 *   <li>For {@code bcabc}: b, c are pushed; a pops c and b (both appear later); then b and c are pushed again.</li>
 *   <li>Result: {@code abc}.</li>
 * </ul>
 *
 * <p><b>Complexity Analysis:</b>
 * <ul>
 *   <li><b>Time Complexity:</b> {@code O(n)} — Each letter is pushed and popped at most once.</li>
 *   <li><b>Space Complexity:</b> {@code O(1)} — At most 26 letters on the stack.</li>
 * </ul>
 *
 * @author Ranga Reddy
 */
public class RemoveDuplicateLetters {

    public static void main(String[] args) {
        String[] inputs = {"bcabc", "cbacdcbc", "abacb", "a", ""};
        for (String s : inputs) {
            System.out.println("Input  : s = \"" + s + "\"");
            System.out.println("Output : \"" + removeDuplicateLetters(s) + "\"");
            System.out.println();
        }
    }

    public static String removeDuplicateLetters(String s) {
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
                stack.deleteCharAt(stack.length() - 1);
            }
            stack.append(c);
            used[c - 'a'] = true;
        }
        return stack.toString();
    }
}
