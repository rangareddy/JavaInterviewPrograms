package com.ranga.string;

import java.util.HashMap;
import java.util.Map;

/**
 * Given a {@code pattern} and a string {@code s}, return {@code true} if {@code s} follows the same pattern: there is a bijection between a letter in {@code pattern} and a non-empty word in {@code s}, so that each letter maps to exactly one word and each word is mapped from exactly one letter.
 *
 * <p><b>Algorithm Strategy (Check Both Directions of the Mapping):</b>
 * <ol>
 *   <li>Split {@code s} into words; if the number of words differs from the length of the pattern, return {@code false}.</li>
 *   <li>Keep two maps: letter to word and word to letter.</li>
 *   <li>For every position, if the letter is already mapped, the word must match; if the word is already mapped, the letter must match.</li>
 *   <li>Otherwise record both mappings. Return {@code true} if every position agrees.</li>
 * </ol>
 *
 * <p><b>Example walkthrough</b>
 * <ul>
 *   <li>For {@code abba} and {@code dog cat cat dog}: a to dog, b to cat, b to cat (matches), a to dog (matches).</li>
 *   <li>Result: {@code true}.</li>
 * </ul>
 *
 * <p><b>Complexity Analysis:</b>
 * <ul>
 *   <li><b>Time Complexity:</b> {@code O(n)} — One pass over the words.</li>
 *   <li><b>Space Complexity:</b> {@code O(n)} — The two maps.</li>
 * </ul>
 *
 * @author Ranga Reddy
 */
public class WordPattern {

    public static void main(String[] args) {
        String[] patterns = {"abba", "abba", "aaaa", "abba"};
        String[] strings = {"dog cat cat dog", "dog cat cat fish", "dog cat cat dog", "dog dog dog dog"};
        for (int i = 0; i < patterns.length; i++) {
            System.out.println("Input  : pattern = \"" + patterns[i] + "\", s = \"" + strings[i] + "\"");
            System.out.println("Output : " + wordPattern(patterns[i], strings[i]));
            System.out.println();
        }
    }

    public static boolean wordPattern(String pattern, String s) {
        if (pattern == null || s == null) {
            return false;
        }
        String[] words = s.trim().split("\\s+");
        if (words.length != pattern.length()) {
            return false;
        }
        Map<Character, String> letterToWord = new HashMap<>();
        Map<String, Character> wordToLetter = new HashMap<>();
        for (int i = 0; i < words.length; i++) {
            char letter = pattern.charAt(i);
            String word = words[i];
            if (letterToWord.containsKey(letter) && !letterToWord.get(letter).equals(word)) {
                return false;
            }
            if (wordToLetter.containsKey(word) && wordToLetter.get(word) != letter) {
                return false;
            }
            letterToWord.put(letter, word);
            wordToLetter.put(word, letter);
        }
        return true;
    }
}
