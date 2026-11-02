package com.ranga.blind75.string;

/**
 * Given a string {@code s} of uppercase letters and an integer {@code k}, you may change at most {@code k} characters to any other uppercase letter. Return the length of the longest substring containing the same letter after the changes.
 *
 * <p><b>Algorithm Strategy (Sliding Window with Character Counts):</b>
 * <ol>
 *   <li>Keep a window {@code [left, right]} and an array {@code count[26]} with the frequency of each letter inside the window.</li>
 *   <li>Track {@code maxCount}, the highest frequency of any single letter seen in a window. The characters that must be replaced are {@code windowSize - maxCount}.</li>
 *   <li>Expand {@code right}; if {@code (windowSize - maxCount) > k}, the window needs too many replacements, so shrink it by moving {@code left} one step (and decrement its count).</li>
 *   <li>The window never shrinks below the best size found, so {@code right - left + 1} at the end is the answer. ({@code maxCount} is never decreased because only a larger count can improve the answer.)</li>
 * </ol>
 *
 * <p><b>Example walkthrough</b>
 * <ul>
 *   <li>For {@code "AABABBA"} and {@code k = 1}: the window {@code "AABA"} has 3 As, so 1 replacement is enough (length 4); extending to 5 would need 2 replacements.</li>
 *   <li>Result: {@code 4}.</li>
 * </ul>
 *
 * <p><b>Complexity Analysis:</b>
 * <ul>
 *   <li><b>Time Complexity:</b> {@code O(n)} — Each index enters the window once and leaves at most once.</li>
 *   <li><b>Space Complexity:</b> {@code O(1)} — A fixed array of 26 counters.</li>
 * </ul>
 *
 * @author Ranga Reddy
 */
public class LongestRepeatingCharacterReplacement {

    public static void main(String[] args) {
        String[] inputs = {"ABAB", "AABABBA", "AAAA"};
        int[] ks = {2, 1, 2};
        for (int i = 0; i < inputs.length; i++) {
            System.out.println("Input  : s = \"" + inputs[i] + "\", k = " + ks[i]);
            System.out.println("Output : " + characterReplacement(inputs[i], ks[i]));
            System.out.println();
        }
    }

    public static int characterReplacement(String s, int k) {
        if (s == null || s.isEmpty()) {
            return 0;
        }

        int[] count = new int[26];
        int left = 0;
        int maxCount = 0;
        int best = 0;
        for (int right = 0; right < s.length(); right++) {
            maxCount = Math.max(maxCount, ++count[s.charAt(right) - 'A']);

            // More than k replacements needed: shrink the window from the left
            if ((right - left + 1) - maxCount > k) {
                count[s.charAt(left) - 'A']--;
                left++;
            }
            best = Math.max(best, right - left + 1);
        }
        return best;
    }
}
