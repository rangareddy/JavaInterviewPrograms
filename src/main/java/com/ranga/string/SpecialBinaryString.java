package com.ranga.string;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * A special binary string has an equal number of {@code 0}s and {@code 1}s, and every prefix has at least as many {@code 1}s as {@code 0}s. A move picks two consecutive, non-empty special substrings and swaps them. Return the lexicographically largest string you can get after any number of moves.
 *
 * <p><b>Algorithm Strategy (Split into Primitive Pieces, Sort Descending):</b>
 * <ol>
 *   <li>Split the string into primitive special strings: scan with a balance counter (+1 for {@code 1}, -1 for {@code 0}) and cut each time it returns to zero.</li>
 *   <li>Each primitive string has the form {@code 1 inner 0}; recursively make {@code inner} as large as possible.</li>
 *   <li>Rebuild each piece as {@code 1 + best(inner) + 0}.</li>
 *   <li>Sort the pieces in descending order and concatenate them: the largest piece first gives the largest string.</li>
 * </ol>
 *
 * <p><b>Example walkthrough</b>
 * <ul>
 *   <li>For {@code 11011000}: it is one primitive piece {@code 1 10 11 000}-style; its inner part {@code 101100} splits into {@code 10} and {@code 1100}, which sort to {@code 1100}, {@code 10}.</li>
 *   <li>Result: {@code 11100100}.</li>
 * </ul>
 *
 * <p><b>Complexity Analysis:</b>
 * <ul>
 *   <li><b>Time Complexity:</b> {@code O(n^2)} — Each recursion level scans and sorts the pieces.</li>
 *   <li><b>Space Complexity:</b> {@code O(n)} — The recursion and the pieces.</li>
 * </ul>
 *
 * @author Ranga Reddy
 */
public class SpecialBinaryString {

    public static void main(String[] args) {
        String[] inputs = {"11011000", "10", "1100", "101100"};
        for (String s : inputs) {
            System.out.println("Input  : s = \"" + s + "\"");
            System.out.println("Output : " + makeLargestSpecial(s));
            System.out.println();
        }
    }

    public static String makeLargestSpecial(String s) {
        if (s == null || s.length() <= 2) {
            return s == null ? "" : s;
        }
        List<String> pieces = new ArrayList<>();
        int balance = 0;
        int start = 0;
        for (int i = 0; i < s.length(); i++) {
            balance += s.charAt(i) == '1' ? 1 : -1;
            if (balance == 0) {
                pieces.add("1" + makeLargestSpecial(s.substring(start + 1, i)) + "0");
                start = i + 1;
            }
        }
        Collections.sort(pieces, Collections.reverseOrder());
        return String.join("", pieces);
    }
}
