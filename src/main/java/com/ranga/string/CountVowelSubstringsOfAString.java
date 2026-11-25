package com.ranga.string;

/**
 * A vowel substring is a substring made only of vowels ({@code a}, {@code e}, {@code i}, {@code o}, {@code u}) that contains all five vowels at least once. Given a string {@code word}, return the number of vowel substrings.
 *
 * <p><b>Algorithm Strategy (Check Every Substring that Stays Vowel-Only):</b>
 * <ol>
 *   <li>For each start index, extend the end index while the characters are vowels; stop at the first consonant.</li>
 *   <li>Keep a set (or a bitmask) of the vowels seen in the window.</li>
 *   <li>Whenever the set holds all five vowels, count the substring.</li>
 *   <li>Sum over all start indexes.</li>
 * </ol>
 *
 * <p><b>Example walkthrough</b>
 * <ul>
 *   <li>For {@code aeiouu}: the substrings {@code aeiou} and {@code aeiouu} contain all five vowels.</li>
 *   <li>Result: {@code 2}.</li>
 * </ul>
 *
 * <p><b>Complexity Analysis:</b>
 * <ul>
 *   <li><b>Time Complexity:</b> {@code O(n^2)} — Each start extends over the vowel run, at most n characters.</li>
 *   <li><b>Space Complexity:</b> {@code O(1)} — A bitmask.</li>
 * </ul>
 *
 * @author Ranga Reddy
 */
public class CountVowelSubstringsOfAString {

    public static void main(String[] args) {
        String[] inputs = {"aeiouu", "unicornarihan", "cuaieuouac", "bbaeixoubb"};
        for (String word : inputs) {
            System.out.println("Input  : word = \"" + word + "\"");
            System.out.println("Output : " + countVowelSubstrings(word));
            System.out.println();
        }
    }

    public static int countVowelSubstrings(String word) {
        if (word == null) {
            return 0;
        }
        int count = 0;
        for (int start = 0; start < word.length(); start++) {
            int mask = 0;
            for (int end = start; end < word.length(); end++) {
                int bit = vowelBit(word.charAt(end));
                if (bit == 0) {
                    break;
                }
                mask |= bit;
                if (mask == 31) {
                    count++;
                }
            }
        }
        return count;
    }

    private static int vowelBit(char c) {
        switch (c) {
            case 'a': return 1;
            case 'e': return 2;
            case 'i': return 4;
            case 'o': return 8;
            case 'u': return 16;
            default: return 0;
        }
    }
}
