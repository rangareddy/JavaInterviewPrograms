package com.ranga.string;

/**
 * Given a binary string {@code s}, return the number of non-empty substrings that have the same number of {@code 0}s and {@code 1}s, where all the {@code 0}s and all the {@code 1}s in the substring are grouped consecutively.
 *
 * <p><b>Algorithm Strategy (Adjacent Run Lengths):</b>
 * <ol>
 *   <li>Split the string into runs of equal characters, such as {@code 00 11 0 1 00} for {@code 0011010 0}.</li>
 *   <li>Between two adjacent runs of lengths {@code a} and {@code b}, exactly {@code min(a, b)} valid substrings are centred on the boundary.</li>
 *   <li>Keep {@code prev} (the previous run length) and {@code cur} (the current one) while scanning.</li>
 *   <li>Whenever a run ends, add {@code min(prev, cur)} to the answer.</li>
 * </ol>
 *
 * <p><b>Example walkthrough</b>
 * <ul>
 *   <li>For {@code 00110011}: runs are 2, 2, 2, 2, so each of the three boundaries adds {@code min(2, 2) = 2}.</li>
 *   <li>Result: {@code 6}.</li>
 * </ul>
 *
 * <p><b>Complexity Analysis:</b>
 * <ul>
 *   <li><b>Time Complexity:</b> {@code O(n)} — One pass.</li>
 *   <li><b>Space Complexity:</b> {@code O(1)} — Two counters.</li>
 * </ul>
 *
 * @author Ranga Reddy
 */
public class CountBinarySubstrings {

    public static void main(String[] args) {
        String[] inputs = {"00110011", "10101", "000111", "0"};
        for (String s : inputs) {
            System.out.println("Input  : s = \"" + s + "\"");
            System.out.println("Output : " + countBinarySubstrings(s));
            System.out.println();
        }
    }

    public static int countBinarySubstrings(String s) {
        if (s == null || s.length() < 2) {
            return 0;
        }
        int count = 0;
        int prev = 0;
        int cur = 1;
        for (int i = 1; i < s.length(); i++) {
            if (s.charAt(i) == s.charAt(i - 1)) {
                cur++;
            } else {
                count += Math.min(prev, cur);
                prev = cur;
                cur = 1;
            }
        }
        return count + Math.min(prev, cur);
    }
}
