package com.ranga.string;

/**
 * Given two strings {@code s} and {@code part}, repeatedly remove the leftmost occurrence of {@code part} from {@code s} until none remains, and return {@code s}.
 *
 * <p><b>Algorithm Strategy (Build with a StringBuilder and Check the Suffix):</b>
 * <ol>
 *   <li>Append the characters of {@code s} one by one to a {@code StringBuilder}.</li>
 *   <li>After each append, if the builder ends with {@code part}, delete those last {@code part.length()} characters.</li>
 *   <li>Because removals can create a new match with earlier characters, the builder acts like a stack that is re-checked after every append.</li>
 *   <li>The builder's content is the answer.</li>
 * </ol>
 *
 * <p><b>Example walkthrough</b>
 * <ul>
 *   <li>For {@code daabcbaabcbc} and {@code abc}: the first {@code abc} is deleted, which exposes a new {@code abc} shortly after, and so on.</li>
 *   <li>Result: {@code dab}.</li>
 * </ul>
 *
 * <p><b>Complexity Analysis:</b>
 * <ul>
 *   <li><b>Time Complexity:</b> {@code O(n * m)} — Each append may compare a suffix of length {@code m}.</li>
 *   <li><b>Space Complexity:</b> {@code O(n)} — The builder.</li>
 * </ul>
 *
 * @author Ranga Reddy
 */
public class RemoveAllOccurrencesOfASubstring {

    public static void main(String[] args) {
        String[][] inputs = {{"daabcbaabcbc", "abc"}, {"axxxxyyyyb", "xy"}, {"aaaa", "aa"}, {"abc", "x"}};
        for (String[] in : inputs) {
            System.out.println("Input  : s = \"" + in[0] + "\", part = \"" + in[1] + "\"");
            System.out.println("Output : \"" + removeOccurrences(in[0], in[1]) + "\"");
            System.out.println();
        }
    }

    public static String removeOccurrences(String s, String part) {
        if (s == null || part == null || part.isEmpty()) {
            return s;
        }
        StringBuilder sb = new StringBuilder();
        for (char c : s.toCharArray()) {
            sb.append(c);
            int start = sb.length() - part.length();
            if (start >= 0 && sb.indexOf(part, start) == start) {
                sb.setLength(start);
            }
        }
        return sb.toString();
    }
}
