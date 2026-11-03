package com.ranga.string;

/**
 * Two strings {@code s} and {@code t} are isomorphic if the characters of {@code s} can be replaced to get {@code t}, where every character maps to exactly one character and no two characters map to the same one (the order is preserved). Return {@code true} if they are isomorphic.
 *
 * <p><b>Algorithm Strategy (Two-Way Character Mapping):</b>
 * <ol>
 *   <li>If the lengths differ the strings cannot be isomorphic.</li>
 *   <li>Keep two maps (arrays work for ASCII): {@code s -> t} and {@code t -> s}.</li>
 *   <li>For every position {@code i}, look at {@code a = s[i]} and {@code b = t[i]}. If {@code a} is already mapped to something other than {@code b}, or {@code b} is already the image of something other than {@code a}, return {@code false}.</li>
 *   <li>Otherwise record both mappings. Reaching the end means the strings are isomorphic.</li>
 * </ol>
 *
 * <p><b>Example walkthrough</b>
 * <ul>
 *   <li>{@code "egg"} / {@code "add"}: {@code e->a}, {@code g->d}, {@code g->d} (consistent): {@code true}.</li>
 *   <li>{@code "foo"} / {@code "bar"}: {@code o} would have to map to both {@code a} and {@code r}: {@code false}; {@code "badc"} / {@code "baba"}: {@code b} and {@code d} would both map to {@code b}: {@code false}.</li>
 * </ul>
 *
 * <p><b>Complexity Analysis:</b>
 * <ul>
 *   <li><b>Time Complexity:</b> {@code O(n)} — One pass.</li>
 *   <li><b>Space Complexity:</b> {@code O(1)} — Two arrays of fixed size (256 characters).</li>
 * </ul>
 *
 * @author Ranga Reddy
 */
public class IsomorphicStrings {

    public static void main(String[] args) {
        String[][] inputs = {{"egg", "add"}, {"foo", "bar"}, {"paper", "title"}, {"badc", "baba"}, {"", ""}, {"ab", "aa"}};
        for (String[] pair : inputs) {
            System.out.println("Input  : s = \"" + pair[0] + "\", t = \"" + pair[1] + "\"");
            System.out.println("Output : " + isIsomorphic(pair[0], pair[1]));
            System.out.println();
        }
    }

    public static boolean isIsomorphic(String s, String t) {
        if (s.length() != t.length()) {
            return false;
        }

        char[] sToT = new char[256];
        char[] tToS = new char[256];
        for (int i = 0; i < s.length(); i++) {
            char a = s.charAt(i);
            char b = t.charAt(i);
            if ((sToT[a] != 0 && sToT[a] != b) || (tToS[b] != 0 && tToS[b] != a)) {
                return false;
            }
            sToT[a] = b;
            tToS[b] = a;
        }
        return true;
    }
}
