package com.ranga.string;

/**
 * Given a string {@code s}, reverse only the vowels ({@code a}, {@code e}, {@code i}, {@code o}, {@code u}, in either case) and return the resulting string. All other characters stay where they are.
 *
 * <p><b>Algorithm Strategy (Two Pointers Swapping Vowels):</b>
 * <ol>
 *   <li>Put the characters in an array and set {@code left = 0}, {@code right = n - 1}.</li>
 *   <li>Advance {@code left} until it points at a vowel and move {@code right} back until it points at a vowel.</li>
 *   <li>Swap the two vowels and move both pointers inward.</li>
 *   <li>Stop when the pointers meet and build the string.</li>
 * </ol>
 *
 * <p><b>Example walkthrough</b>
 * <ul>
 *   <li>For {@code hello}: {@code left} stops at {@code e}, {@code right} at {@code o}; swapping gives {@code holle}.</li>
 *   <li>Result: {@code holle}.</li>
 * </ul>
 *
 * <p><b>Complexity Analysis:</b>
 * <ul>
 *   <li><b>Time Complexity:</b> {@code O(n)} — Each index is visited once.</li>
 *   <li><b>Space Complexity:</b> {@code O(n)} — The character array.</li>
 * </ul>
 *
 * @author Ranga Reddy
 */
public class ReverseVowelsOfAString {

    public static void main(String[] args) {
        String[] inputs = {"hello", "leetcode", "AEIOU", "xyz", ""};
        for (String s : inputs) {
            System.out.println("Input  : s = \"" + s + "\"");
            System.out.println("Output : " + reverseVowels(s));
            System.out.println();
        }
    }

    public static String reverseVowels(String s) {
        if (s == null || s.length() < 2) {
            return s;
        }
        char[] chars = s.toCharArray();
        int left = 0;
        int right = chars.length - 1;
        while (left < right) {
            while (left < right && !isVowel(chars[left])) {
                left++;
            }
            while (left < right && !isVowel(chars[right])) {
                right--;
            }
            char tmp = chars[left];
            chars[left++] = chars[right];
            chars[right--] = tmp;
        }
        return new String(chars);
    }

    private static boolean isVowel(char c) {
        return "aeiouAEIOU".indexOf(c) >= 0;
    }
}
