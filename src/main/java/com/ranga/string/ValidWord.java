package com.ranga.string;

/**
 * A word is valid if it contains at least 3 characters, only digits and English letters, at least one vowel ({@code a e i o u}, any case) and at least one consonant. Given a string {@code word}, return {@code true} if it is valid, otherwise {@code false}.
 *
 * <p><b>Algorithm Strategy (Classify Each Character Once):</b>
 * <ol>
 *   <li>Reject immediately if the length is below 3.</li>
 *   <li>Scan the characters. A letter is a vowel or a consonant; a digit is allowed and counts as neither.</li>
 *   <li>Any other character (for example {@code $} or {@code @}) makes the word invalid.</li>
 *   <li>At the end, require both a vowel and a consonant.</li>
 * </ol>
 *
 * <p><b>Example walkthrough</b>
 * <ul>
 *   <li>For {@code 234Adas}: it has 7 characters, only digits and letters, vowels {@code A} and {@code a}, and consonants {@code d} and {@code s}.</li>
 *   <li>Result: {@code true}.</li>
 * </ul>
 *
 * <p><b>Complexity Analysis:</b>
 * <ul>
 *   <li><b>Time Complexity:</b> {@code O(n)} — One pass.</li>
 *   <li><b>Space Complexity:</b> {@code O(1)} — Two flags.</li>
 * </ul>
 *
 * @author Ranga Reddy
 */
public class ValidWord {

    public static void main(String[] args) {
        String[] inputs = {"234Adas", "b3", "a3$e", "AEIOU", "xyz9"};
        for (String word : inputs) {
            System.out.println("Input  : word = \"" + word + "\"");
            System.out.println("Output : " + isValid(word));
            System.out.println();
        }
    }

    public static boolean isValid(String word) {
        if (word == null || word.length() < 3) {
            return false;
        }
        boolean vowel = false;
        boolean consonant = false;
        for (int i = 0; i < word.length(); i++) {
            char c = Character.toLowerCase(word.charAt(i));
            if (c >= 'a' && c <= 'z') {
                if (c == 'a' || c == 'e' || c == 'i' || c == 'o' || c == 'u') {
                    vowel = true;
                } else {
                    consonant = true;
                }
            } else if (c < '0' || c > '9') {
                return false;
            }
        }
        return vowel && consonant;
    }
}
