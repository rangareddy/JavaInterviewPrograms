package com.ranga.string;

/**
 * Given a string {@code s}, return {@code true} if it can become a palindrome after deleting at most one character.
 *
 * <p><b>Algorithm Strategy (Two Pointers with One Skip):</b>
 * <ol>
 *   <li>Walk two pointers inward from both ends while the characters match.</li>
 *   <li>On the first mismatch, one of the two characters must be the one deleted.</li>
 *   <li>Check whether the rest is a palindrome after skipping the left character, or after skipping the right one.</li>
 *   <li>If neither remainder is a palindrome, no single deletion works. Only the first mismatch needs this branch, so the work stays linear.</li>
 * </ol>
 *
 * <p><b>Example walkthrough</b>
 * <ul>
 *   <li>For {@code abca}: the outer {@code a} characters match, then {@code b != c} at indexes 1 and 2.</li>
 *   <li>Skipping the left {@code b} leaves indexes 2..2, the single character {@code c}, which is a palindrome. Result: {@code true} (delete {@code b} to get {@code aca}).</li>
 *   <li>For {@code abc}: {@code a != c} at once; {@code bc} and {@code ab} are not palindromes. Result: {@code false}.</li>
 * </ul>
 *
 * <p><b>Complexity Analysis:</b>
 * <ul>
 *   <li><b>Time Complexity:</b> {@code O(n)} — The main scan and at most two remainder scans, each linear.</li>
 *   <li><b>Space Complexity:</b> {@code O(1)} — Only pointers.</li>
 * </ul>
 *
 * @author Ranga Reddy
 */
public class ValidPalindromeII {

    public static void main(String[] args) {
        String[] inputs = {"aba", "abca", "abc", "deeee", "a", ""};
        for (String s : inputs) {
            System.out.println("Input  : s = \"" + s + "\"");
            System.out.println("Output : " + validPalindrome(s));
            System.out.println();
        }
    }

    public static boolean validPalindrome(String s) {
        int left = 0;
        int right = s.length() - 1;
        while (left < right) {
            if (s.charAt(left) != s.charAt(right)) {
                return isPalindrome(s, left + 1, right) || isPalindrome(s, left, right - 1);
            }
            left++;
            right--;
        }
        return true;
    }

    private static boolean isPalindrome(String s, int left, int right) {
        while (left < right) {
            if (s.charAt(left++) != s.charAt(right--)) {
                return false;
            }
        }
        return true;
    }
}
