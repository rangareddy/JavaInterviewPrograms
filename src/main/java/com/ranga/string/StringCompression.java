package com.ranga.string;

/**
 * Compress a string by replacing every run of repeated characters with the character followed by the run length when the length is greater than 1 (for example {@code "aabcccccaaa"} becomes {@code "a2bc5a3"}). Return the original string if it is not made shorter.
 *
 * <p><b>Algorithm Strategy (Run-Length Encoding with a Single Pass):</b>
 * <ol>
 *   <li>Walk through the string and count how many equal characters follow each other.</li>
 *   <li>When the run ends (the next character is different or the string ends), append the character and, if the run is longer than 1, its length.</li>
 *   <li>Use a {@code StringBuilder} so the appends do not copy the whole result every time.</li>
 *   <li>If the compressed string is not shorter than the input, return the input unchanged.</li>
 * </ol>
 *
 * <p><b>Example walkthrough</b>
 * <ul>
 *   <li>{@code "aabcccccaaa"}: runs {@code aa, b, ccccc, aaa} give {@code a2 b c5 a3} = {@code "a2bc5a3"} (7 < 11).</li>
 *   <li>{@code "abc"} would become {@code "abc"} (no run longer than 1), which is not shorter, so the original is returned.</li>
 * </ul>
 *
 * <p><b>Complexity Analysis:</b>
 * <ul>
 *   <li><b>Time Complexity:</b> {@code O(n)} — One pass.</li>
 *   <li><b>Space Complexity:</b> {@code O(n)} — The result builder.</li>
 * </ul>
 *
 * @author Ranga Reddy
 */
public class StringCompression {

    public static void main(String[] args) {
        String[] inputs = {"aabcccccaaa", "abc", "aaaaaaaaaaaa", "aabb", "a", ""};
        for (String s : inputs) {
            System.out.println("Input  : s = \"" + s + "\"");
            System.out.println("Output : \"" + compress(s) + "\"");
            System.out.println();
        }
    }

    public static String compress(String s) {
        StringBuilder result = new StringBuilder();
        int i = 0;
        while (i < s.length()) {
            char c = s.charAt(i);
            int run = 0;
            while (i < s.length() && s.charAt(i) == c) {
                run++;
                i++;
            }
            result.append(c);
            if (run > 1) {
                result.append(run);
            }
        }
        return result.length() < s.length() ? result.toString() : s;
    }
}
