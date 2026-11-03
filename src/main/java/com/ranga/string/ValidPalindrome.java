package com.ranga.string;

/**
 * A phrase is a palindrome if, after converting all uppercase letters to lowercase and removing every non-alphanumeric character, it reads the same forward and backward. Given a string {@code s}, return {@code true} if it is a palindrome.
 *
 * <p><b>Algorithm Strategy (Two Pointers Skipping Non-Alphanumeric Characters):</b>
 * <ol>
 *   <li>Place {@code left} at the start and {@code right} at the end of the string.</li>
 *   <li>Move {@code left} forward while it points to a character that is not a letter or digit, and move {@code right} backward in the same way.</li>
 *   <li>Compare the two characters in lower case; if they differ, the string is not a palindrome.</li>
 *   <li>Move both pointers inward and repeat until they cross. No cleaned copy of the string is built.</li>
 * </ol>
 *
 * <p><b>Example walkthrough</b>
 * <ul>
 *   <li>For {@code "A man, a plan, a canal: Panama"}: skipping spaces and punctuation, the pairs a-a, m-m, a-a, n-n, ... all match.</li>
 *   <li>Result: {@code true}. For {@code "race a car"} the first mismatch is {@code e} vs {@code r}: {@code false}.</li>
 * </ul>
 *
 * <p><b>Complexity Analysis:</b>
 * <ul>
 *   <li><b>Time Complexity:</b> {@code O(n)} — Each character is visited once.</li>
 *   <li><b>Space Complexity:</b> {@code O(1)} — Two pointers.</li>
 * </ul>
 *
 * @author Ranga Reddy
 */
public class ValidPalindrome {

    public static void main(String[] args) {
        String[] inputs = {"A man, a plan, a canal: Panama", "race a car", " ", "0P", "No 'x' in Nixon"};
        for (String s : inputs) {
            System.out.println("Input  : s = \"" + s + "\"");
            System.out.println("Output : " + isPalindrome(s));
            System.out.println();
        }
    }

    public static boolean isPalindrome(String s) {
        if (s == null) {
            return false;
        }

        int left = 0;
        int right = s.length() - 1;
        while (left < right) {
            while (left < right && !Character.isLetterOrDigit(s.charAt(left))) {
                left++;
            }
            while (left < right && !Character.isLetterOrDigit(s.charAt(right))) {
                right--;
            }
            if (Character.toLowerCase(s.charAt(left)) != Character.toLowerCase(s.charAt(right))) {
                return false;
            }
            left++;
            right--;
        }
        return true;
    }
}
