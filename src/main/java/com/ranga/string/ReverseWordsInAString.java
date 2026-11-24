package com.ranga.string;

/**
 * Given a string {@code s}, reverse the order of its words. A word is a maximal run of non-space characters. The result must contain the words separated by a single space, with no leading or trailing spaces, even when the input has extra spaces.
 *
 * <p><b>Algorithm Strategy (Scan Backward Collecting Words):</b>
 * <ol>
 *   <li>Walk the string from the end towards the start.</li>
 *   <li>Skip spaces, then mark the end of a word and move left until the next space or the start to find its beginning.</li>
 *   <li>Append the word to a {@code StringBuilder}, adding a single space first if something is already there.</li>
 *   <li>Return the builder's content.</li>
 * </ol>
 *
 * <p><b>Example walkthrough</b>
 * <ul>
 *   <li>For {@code "  hello world  "} the scan finds {@code world} and then {@code hello}.</li>
 *   <li>Result: {@code "world hello"}.</li>
 * </ul>
 *
 * <p><b>Complexity Analysis:</b>
 * <ul>
 *   <li><b>Time Complexity:</b> {@code O(n)} — Each character is looked at once.</li>
 *   <li><b>Space Complexity:</b> {@code O(n)} — The result builder.</li>
 * </ul>
 *
 * @author Ranga Reddy
 */
public class ReverseWordsInAString {

    public static void main(String[] args) {
        String[] inputs = {"the sky is blue", "  hello world  ", "a good   example", "", "word"};
        for (String s : inputs) {
            System.out.println("Input  : s = \"" + s + "\"");
            System.out.println("Output : \"" + reverseWords(s) + "\"");
            System.out.println();
        }
    }

    public static String reverseWords(String s) {
        if (s == null || s.isEmpty()) {
            return "";
        }
        StringBuilder result = new StringBuilder();
        int i = s.length() - 1;
        while (i >= 0) {
            while (i >= 0 && s.charAt(i) == ' ') {
                i--;
            }
            if (i < 0) {
                break;
            }
            int end = i;
            while (i >= 0 && s.charAt(i) != ' ') {
                i--;
            }
            if (result.length() > 0) {
                result.append(' ');
            }
            result.append(s, i + 1, end + 1);
        }
        return result.toString();
    }
}
