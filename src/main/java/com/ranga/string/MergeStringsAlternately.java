package com.ranga.string;

/**
 * Given two strings {@code word1} and {@code word2}, merge them by adding letters in alternating order, starting with {@code word1}. If one string is longer, append its remaining letters to the end of the merged string.
 *
 * <p><b>Algorithm Strategy (Interleave then Append the Tail):</b>
 * <ol>
 *   <li>Use one index {@code i} that walks both strings.</li>
 *   <li>While {@code i} is within {@code word1}, append {@code word1[i]}; while {@code i} is within {@code word2}, append {@code word2[i]}.</li>
 *   <li>When the shorter string runs out the longer one keeps appending its own letters.</li>
 *   <li>Return the builder content.</li>
 * </ol>
 *
 * <p><b>Example walkthrough</b>
 * <ul>
 *   <li>For {@code abc} and {@code pqr}: a, p, b, q, c, r.</li>
 *   <li>Result: {@code apbqcr}.</li>
 * </ul>
 *
 * <p><b>Complexity Analysis:</b>
 * <ul>
 *   <li><b>Time Complexity:</b> {@code O(m + n)} — Each letter is appended once.</li>
 *   <li><b>Space Complexity:</b> {@code O(m + n)} — The result.</li>
 * </ul>
 *
 * @author Ranga Reddy
 */
public class MergeStringsAlternately {

    public static void main(String[] args) {
        String[][] inputs = {{"abc", "pqr"}, {"ab", "pqrs"}, {"abcd", "pq"}, {"", "xyz"}};
        for (String[] in : inputs) {
            System.out.println("Input  : word1 = \"" + in[0] + "\", word2 = \"" + in[1] + "\"");
            System.out.println("Output : " + mergeAlternately(in[0], in[1]));
            System.out.println();
        }
    }

    public static String mergeAlternately(String word1, String word2) {
        StringBuilder result = new StringBuilder();
        int max = Math.max(word1.length(), word2.length());
        for (int i = 0; i < max; i++) {
            if (i < word1.length()) {
                result.append(word1.charAt(i));
            }
            if (i < word2.length()) {
                result.append(word2.charAt(i));
            }
        }
        return result.toString();
    }
}
