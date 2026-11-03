package com.ranga.string;

/**
 * Given two strings {@code s1} and {@code s2}, return {@code true} if {@code s2} contains a permutation of {@code s1}, i.e. one of the permutations of {@code s1} is a substring of {@code s2}.
 *
 * <p><b>Algorithm Strategy (Fixed-Size Sliding Window with Letter Counts):</b>
 * <ol>
 *   <li>A permutation of {@code s1} has exactly the same letter counts as {@code s1} and the same length, so look for a window of length {@code s1.length()} in {@code s2} with identical counts.</li>
 *   <li>Count the letters of {@code s1} in {@code need[26]} and the letters of the first window of {@code s2} in {@code window[26]}.</li>
 *   <li>Slide the window one position at a time: add the new letter on the right and remove the letter that leaves on the left. Keep the number {@code matches} of letters whose counts are equal in both arrays so the comparison is {@code O(1)} per step.</li>
 *   <li>Return {@code true} as soon as all 26 letters match.</li>
 * </ol>
 *
 * <p><b>Example walkthrough</b>
 * <ul>
 *   <li>For {@code s1 = "ab"} and {@code s2 = "eidbaooo"}: the window {@code "ba"} (positions 3 and 4) has the same counts as {@code "ab"}.</li>
 *   <li>Result: {@code true}. For {@code "eidboaoo"} no window has one {@code a} and one {@code b}: {@code false}.</li>
 * </ul>
 *
 * <p><b>Complexity Analysis:</b>
 * <ul>
 *   <li><b>Time Complexity:</b> {@code O(|s1| + |s2|)} — One pass with a constant amount of work per step.</li>
 *   <li><b>Space Complexity:</b> {@code O(1)} — Two arrays of 26 counters.</li>
 * </ul>
 *
 * @author Ranga Reddy
 */
public class PermutationInString {

    public static void main(String[] args) {
        String[][] inputs = {{"ab", "eidbaooo"}, {"ab", "eidboaoo"}, {"adc", "dcda"}, {"abc", "ab"}};
        for (String[] input : inputs) {
            System.out.println("Input  : s1 = \"" + input[0] + "\", s2 = \"" + input[1] + "\"");
            System.out.println("Output : " + checkInclusion(input[0], input[1]));
            System.out.println();
        }
    }

    public static boolean checkInclusion(String s1, String s2) {
        if (s1.length() > s2.length()) {
            return false;
        }

        int[] need = new int[26];
        int[] window = new int[26];
        for (int i = 0; i < s1.length(); i++) {
            need[s1.charAt(i) - 'a']++;
            window[s2.charAt(i) - 'a']++;
        }

        int matches = 0;
        for (int c = 0; c < 26; c++) {
            if (need[c] == window[c]) {
                matches++;
            }
        }

        for (int right = s1.length(); right < s2.length(); right++) {
            if (matches == 26) {
                return true;
            }
            int in = s2.charAt(right) - 'a';
            int out = s2.charAt(right - s1.length()) - 'a';

            window[in]++;
            if (window[in] == need[in]) {
                matches++;
            } else if (window[in] == need[in] + 1) {
                matches--;
            }

            window[out]--;
            if (window[out] == need[out]) {
                matches++;
            } else if (window[out] == need[out] - 1) {
                matches--;
            }
        }
        return matches == 26;
    }
}
