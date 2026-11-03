package com.ranga.backtracking;

import java.util.ArrayList;
import java.util.List;

/**
 * Given a string containing digits from 2 to 9, return all possible letter combinations that the number could represent on a telephone keypad (2 = abc, 3 = def, 4 = ghi, 5 = jkl, 6 = mno, 7 = pqrs, 8 = tuv, 9 = wxyz). Digit 1 maps to no letters. The answer may be returned in any order.
 *
 * <p><b>Algorithm Strategy (Backtracking over the Digits):</b>
 * <ol>
 *   <li>Keep a lookup table from digit to letters.</li>
 *   <li>Build a combination one digit at a time: at depth {@code d} try every letter of {@code digits.charAt(d)}, append it, recurse for the next digit and remove it again (backtrack).</li>
 *   <li>When the combination has one letter for every digit, record it.</li>
 *   <li>An empty input has no combination at all (the answer is an empty list, not a list containing the empty string).</li>
 * </ol>
 *
 * <p><b>Example walkthrough</b>
 * <ul>
 *   <li>For {@code "23"}: the letters of 2 are {@code abc}, the letters of 3 are {@code def}, giving {@code ad, ae, af, bd, be, bf, cd, ce, cf}.</li>
 *   <li>The number of results is the product of the number of letters of each digit.</li>
 * </ul>
 *
 * <p><b>Complexity Analysis:</b>
 * <ul>
 *   <li><b>Time Complexity:</b> {@code O(4^n * n)} — At most 4 letters per digit; every combination is copied in {@code O(n)}.</li>
 *   <li><b>Space Complexity:</b> {@code O(n)} — Recursion depth (excluding the output).</li>
 * </ul>
 *
 * @author Ranga Reddy
 */
public class LetterCombinationsOfAPhoneNumber {

    private static final String[] LETTERS = {"", "", "abc", "def", "ghi", "jkl", "mno", "pqrs", "tuv", "wxyz"};

    public static void main(String[] args) {
        String[] inputs = {"23", "", "2", "79"};
        for (String digits : inputs) {
            System.out.println("Input  : digits = \"" + digits + "\"");
            System.out.println("Output : " + letterCombinations(digits));
            System.out.println();
        }
    }

    public static List<String> letterCombinations(String digits) {
        List<String> result = new ArrayList<>();
        if (digits == null || digits.isEmpty()) {
            return result;
        }
        backtrack(digits, 0, new StringBuilder(), result);
        return result;
    }

    private static void backtrack(String digits, int index, StringBuilder current, List<String> result) {
        if (index == digits.length()) {
            result.add(current.toString());
            return;
        }
        for (char letter : LETTERS[digits.charAt(index) - '0'].toCharArray()) {
            current.append(letter);
            backtrack(digits, index + 1, current, result);
            current.deleteCharAt(current.length() - 1);
        }
    }
}
