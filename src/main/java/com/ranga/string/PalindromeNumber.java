package com.ranga.string;

/**
 * Given an integer {@code x}, return {@code true} if {@code x} reads the same forward and backward, and {@code false} otherwise. Negative numbers are not palindromes.
 *
 * <p><b>Algorithm Strategy (Reverse Half of the Digits):</b>
 * <ol>
 *   <li>Negative numbers, and non-zero numbers ending in 0, can never be palindromes, so return {@code false} for them.</li>
 *   <li>Build {@code reversed} from the last digits of {@code x} while {@code x > reversed}, moving each digit across.</li>
 *   <li>Stop when the reversed half is at least as large as what remains of {@code x}: this means half of the digits have moved.</li>
 *   <li>An even-length palindrome has {@code x == reversed}; an odd-length one has {@code x == reversed / 10} (the middle digit is dropped).</li>
 * </ol>
 *
 * <p><b>Example walkthrough</b>
 * <ul>
 *   <li>For {@code 1221}: {@code reversed} becomes 1 then 12 while {@code x} shrinks to 122 then 12; {@code x == reversed}, so it is a palindrome.</li>
 *   <li>For {@code 121}: the loop ends with {@code x = 1} and {@code reversed = 12}; dropping the middle digit, {@code reversed / 10 = 1 = x}, so it is a palindrome too.</li>
 * </ul>
 *
 * <p><b>Complexity Analysis:</b>
 * <ul>
 *   <li><b>Time Complexity:</b> {@code O(log10 n)} — Half of the digits are processed.</li>
 *   <li><b>Space Complexity:</b> {@code O(1)} — Only two integers. No string is created.</li>
 * </ul>
 *
 * @author Ranga Reddy
 */
public class PalindromeNumber {

    public static void main(String[] args) {
        int[] inputs = {121, -121, 10, 1221, 0, 12321};
        for (int x : inputs) {
            System.out.println("Input  : x = " + x);
            System.out.println("Output : " + isPalindrome(x));
            System.out.println();
        }
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
