package com.ranga.string;

/**
 * Given a string {@code s}, return the number of palindromic substrings in it. Substrings with different start or end positions are counted separately even if they have the same characters.
 *
 * <p><b>Algorithm Strategy (Manacher's Algorithm):</b>
 * <ol>
 *   <li>Guard first: {@code null} and empty strings have 0 palindromic substrings.</li>
 *   <li>Insert a separator between all characters and add sentinels at both ends ({@code "aaa"} becomes {@code "^#a#a#a#$"}), so every palindrome is an odd-length palindrome around one center.</li>
 *   <li>{@code radius[i]} is the palindrome radius around center {@code i}; it equals the length of the longest palindrome there in the original string. Reuse the mirror position of the rightmost palindrome: {@code radius[i] = min(right - i, radius[2 * center - i])}, then expand further and move the boundary if needed.</li>
 *   <li>A center with radius {@code r} contributes {@code (r + 1) / 2} palindromic substrings (the palindromes of length {@code r}, {@code r - 2}, ... that are centered there and start and end on a real character).</li>
 *   <li>The sum over all centers is the answer.</li>
 * </ol>
 *
 * <p><b>Example walkthrough</b>
 * <ul>
 *   <li>For {@code "aaa"} the new string is {@code ^#a#a#a#$}; the radii of the seven centers between the sentinels are {@code 0, 1, 2, 3, 2, 1, 0}, which contribute {@code (r + 1) / 2 = 0, 1, 1, 2, 1, 1, 0}.</li>
 *   <li>Result: {@code 6}.</li>
 * </ul>
 *
 * <p><b>Complexity Analysis:</b>
 * <ul>
 *   <li><b>Time Complexity:</b> {@code O(n)} — The right boundary only moves forward, so the total number of expansion steps is linear.</li>
 *   <li><b>Space Complexity:</b> {@code O(n)} — The transformed string and the radius array.</li>
 * </ul>
 *
 * @author Ranga Reddy
 */
public class PalindromicSubstrings {

    public static void main(String[] args) {
        String[] inputs = {"abc", "aaa", "abba", "", null};
        for (String s : inputs) {
            System.out.println("Input  : s = " + (s == null ? "null" : "\"" + s + "\""));
            System.out.println("Output : " + countSubstrings(s));
            System.out.println();
        }
    }

    public static int countSubstrings(String s) {
        // Guard Clause: nothing to count
        if (s == null || s.isEmpty()) {
            return 0;
        }

        StringBuilder sb = new StringBuilder("^");
        for (int i = 0; i < s.length(); i++) {
            sb.append('#').append(s.charAt(i));
        }
        String t = sb.append("#$").toString();

        int n = t.length(), center = 0, right = 0, count = 0;
        int[] radius = new int[n];
        for (int i = 1; i < n - 1; i++) {
            if (i < right) {
                radius[i] = Math.min(right - i, radius[2 * center - i]);
            }
            while (t.charAt(i + 1 + radius[i]) == t.charAt(i - 1 - radius[i])) {
                radius[i]++;
            }
            if (i + radius[i] > right) {
                center = i;
                right = i + radius[i];
            }
            count += (radius[i] + 1) / 2;
        }
        return count;
    }
}
