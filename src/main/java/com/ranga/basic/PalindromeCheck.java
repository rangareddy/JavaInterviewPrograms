package com.ranga.basic;

/**
 * Check whether a string or an integer reads the same forwards and backwards. For strings only letters and digits are compared and case is ignored (so {@code "A man, a plan, a canal: Panama"} is a palindrome); for integers negative numbers are never palindromes.
 *
 * <p><b>Algorithm Strategy (Two Pointers for Strings, Half Reversal for Numbers):</b>
 * <ol>
 *   <li>String: put one pointer at the start and one at the end; skip characters that are not letters or digits and compare the rest ignoring case. Any mismatch means the text is not a palindrome.</li>
 *   <li>Number: a negative number, or a positive number that ends with {@code 0} (except {@code 0} itself), can never be a palindrome.</li>
 *   <li>Reverse only the second half of the digits: repeatedly move the last digit of {@code x} onto {@code reversed} until {@code reversed >= x}.</li>
 *   <li>The number is a palindrome if {@code x == reversed} (even number of digits) or {@code x == reversed / 10} (odd number of digits, the middle digit is dropped).</li>
 * </ol>
 *
 * <p><b>Example walkthrough</b>
 * <ul>
 *   <li>{@code "race a car"}: {@code r/r}, {@code a/a}, {@code c/c} match, the space is skipped, then {@code e} is compared with {@code a}: {@code false}.</li>
 *   <li>For {@code 12321}: {@code x = 12321, rev = 0} -> {@code x = 1232, rev = 1} -> {@code x = 123, rev = 12} -> {@code x = 12, rev = 123}; {@code x == rev / 10}: {@code true}.</li>
 * </ul>
 *
 * <p><b>Complexity Analysis:</b>
 * <ul>
 *   <li><b>Time Complexity:</b> {@code O(n) for strings, O(log10 n) for numbers} — One pass over the characters or over half of the digits.</li>
 *   <li><b>Space Complexity:</b> {@code O(1)} — Only a few variables.</li>
 * </ul>
 *
 * @author Ranga Reddy
 */
public class PalindromeCheck {

    public static void main(String[] args) {
        String[] texts = {"A man, a plan, a canal: Panama", "race a car", "", "No lemon, no melon"};
        for (String text : texts) {
            System.out.println("Input  : text = \"" + text + "\"");
            System.out.println("Output : " + isPalindrome(text));
            System.out.println();
        }

        int[] numbers = {121, -121, 10, 0, 12321, 1221};
        for (int number : numbers) {
            System.out.println("Input  : number = " + number);
            System.out.println("Output : " + isPalindrome(number));
            System.out.println();
        }
    }

    public static boolean isPalindrome(String text) {
        int left = 0;
        int right = text.length() - 1;
        while (left < right) {
            if (!Character.isLetterOrDigit(text.charAt(left))) {
                left++;
            } else if (!Character.isLetterOrDigit(text.charAt(right))) {
                right--;
            } else {
                if (Character.toLowerCase(text.charAt(left)) != Character.toLowerCase(text.charAt(right))) {
                    return false;
                }
                left++;
                right--;
            }
        }
        return true;
    }

    public static boolean isPalindrome(int x) {
        if (x < 0 || (x % 10 == 0 && x != 0)) {
            return false;
        }

        int reversed = 0;
        while (x > reversed) {
            reversed = reversed * 10 + x % 10;
            x /= 10;
        }
        return x == reversed || x == reversed / 10;
    }
}
