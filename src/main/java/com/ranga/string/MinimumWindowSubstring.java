package com.ranga.string;

/**
 * Given strings {@code s} and {@code t}, return the shortest substring of {@code s} that contains every character of {@code t} (including duplicates). Return an empty string if there is none.
 *
 * <p><b>Algorithm Strategy (Sliding Window with a Missing Counter):</b>
 * <ol>
 *   <li>Count the characters required by {@code t} in {@code need[128]} and keep {@code missing = t.length()}, the number of required characters still absent from the window.</li>
 *   <li>Expand {@code right}: decrement {@code need[c]} for the new character; if it was still needed ({@code need[c] > 0} before the decrement), decrement {@code missing}.</li>
 *   <li>When {@code missing == 0} the window is valid. Shrink from the left while it stays valid: the character leaving the window is "free" when {@code need[c] < 0} after the decrement, otherwise removing it makes the window invalid.</li>
 *   <li>Record the smallest valid window seen, then continue expanding. Return that substring (or {@code ""}).</li>
 * </ol>
 *
 * <p><b>Example walkthrough</b>
 * <ul>
 *   <li>For {@code s = "ADOBECODEBANC"} and {@code t = "ABC"}: the first valid window is {@code "ADOBEC"}; later {@code "CODEBA"} and finally {@code "BANC"} (length 4).</li>
 *   <li>Result: {@code "BANC"}.</li>
 * </ul>
 *
 * <p><b>Complexity Analysis:</b>
 * <ul>
 *   <li><b>Time Complexity:</b> {@code O(|s| + |t|)} — Both pointers move forward only.</li>
 *   <li><b>Space Complexity:</b> {@code O(1)} — A fixed array of 128 counters.</li>
 * </ul>
 *
 * @author Ranga Reddy
 */
public class MinimumWindowSubstring {

    public static void main(String[] args) {
        String[][] inputs = {{"ADOBECODEBANC", "ABC"}, {"a", "a"}, {"a", "aa"}};
        for (String[] input : inputs) {
            System.out.println("Input  : s = \"" + input[0] + "\", t = \"" + input[1] + "\"");
            System.out.println("Output : \"" + minWindow(input[0], input[1]) + "\"");
            System.out.println();
        }
    }

    public static String minWindow(String s, String t) {
        if (s == null || t == null || s.length() < t.length() || t.isEmpty()) {
            return "";
        }

        int[] need = new int[128];
        for (char c : t.toCharArray()) {
            need[c]++;
        }

        int missing = t.length();
        int left = 0;
        int bestStart = 0;
        int bestLength = Integer.MAX_VALUE;
        for (int right = 0; right < s.length(); right++) {
            if (need[s.charAt(right)]-- > 0) {
                missing--;
            }

            while (missing == 0) {
                if (right - left + 1 < bestLength) {
                    bestLength = right - left + 1;
                    bestStart = left;
                }
                if (++need[s.charAt(left)] > 0) {
                    missing++;      // removing this character makes the window invalid
                }
                left++;
            }
        }
        return bestLength == Integer.MAX_VALUE ? "" : s.substring(bestStart, bestStart + bestLength);
    }
}
