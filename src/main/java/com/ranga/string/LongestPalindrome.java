package com.ranga.string;

import java.util.Arrays;
import java.util.List;

/**
 * User: Ranga Reddy
 * Date: 11/22/2019
 * Time: 7:18 AM
 * Description: Used to find the longest palindrome in the string
 */
public class LongestPalindrome {
    public static void main(String[] args) {
        List<String> list = Arrays.asList("BBABCBCAB", "aaa", "12321", "bananas");
        for (String str : list) {
            String longestStr = getLongestPalindrome(str);
            System.out.println(longestStr);
        }
    }

    private static String getLongestPalindrome(String str) {
        if (str == null || str.length() < 2) {
            return str;
        }

        String longest = str.substring(0, 1);
        int n = str.length();
        for (int i = 0; i < n; i++) {

            // get longest palindrome with center of i
            String palindrome = intermediatePalindrome(str, i, i);
            if (palindrome.length() > longest.length()) {
                longest = palindrome;
            }

            // get longest palindrome with center of i, i+1
            palindrome = intermediatePalindrome(str, i, i + 1);
            if (palindrome.length() > longest.length()) {
                longest = palindrome;
            }
        }
        return longest;
    }

    // Given a center, either one letter or two letter,
    private static String intermediatePalindrome(String str, int start, int end) {
        while (start >= 0 && end < str.length() && str.charAt(start) == str.charAt(end)) {
            start--;
            end++;
        }
        return str.substring(start + 1, end);
    }
}
