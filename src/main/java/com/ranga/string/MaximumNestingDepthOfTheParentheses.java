package com.ranga.string;

/**
 * Given a valid parentheses string {@code s} (it may also contain digits and operators), return the nesting depth of {@code s}, which is the maximum number of nested parentheses at any position.
 *
 * <p><b>Algorithm Strategy (Track the Current Depth):</b>
 * <ol>
 *   <li>Keep a counter {@code depth} for the number of currently open parentheses.</li>
 *   <li>Increase it on {@code (} and record the maximum seen so far.</li>
 *   <li>Decrease it on {@code )}.</li>
 *   <li>Ignore every other character.</li>
 * </ol>
 *
 * <p><b>Example walkthrough</b>
 * <ul>
 *   <li>For {@code (1+(2*3)+((8)/4))+1}: the counter climbs to 3 at the {@code ((} before {@code 8}.</li>
 *   <li>Result: {@code 3}.</li>
 * </ul>
 *
 * <p><b>Complexity Analysis:</b>
 * <ul>
 *   <li><b>Time Complexity:</b> {@code O(n)} — One pass.</li>
 *   <li><b>Space Complexity:</b> {@code O(1)} — Two integers.</li>
 * </ul>
 *
 * @author Ranga Reddy
 */
public class MaximumNestingDepthOfTheParentheses {

    public static void main(String[] args) {
        String[] inputs = {"(1+(2*3)+((8)/4))+1", "(1)+((2))+(((3)))", "1+(2*3)/(2-1)", "1", ""};
        for (String s : inputs) {
            System.out.println("Input  : s = \"" + s + "\"");
            System.out.println("Output : " + maxDepth(s));
            System.out.println();
        }
    }

    public static int maxDepth(String s) {
        if (s == null) {
            return 0;
        }
        int depth = 0;
        int best = 0;
        for (int i = 0; i < s.length(); i++) {
            char c = s.charAt(i);
            if (c == '(') {
                depth++;
                best = Math.max(best, depth);
            } else if (c == ')') {
                depth--;
            }
        }
        return best;
    }
}
