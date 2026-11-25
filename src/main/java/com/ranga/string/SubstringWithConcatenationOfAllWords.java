package com.ranga.string;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Given a string {@code s} and an array of strings {@code words} of the same length, return all starting indices of substrings in {@code s} that are a concatenation of each word in {@code words} exactly once, in any order, with no characters in between.
 *
 * <p><b>Algorithm Strategy (Word-Sized Sliding Window):</b>
 * <ol>
 *   <li>Let {@code w} be the word length and {@code m} the number of words. Count the words in a {@code HashMap}.</li>
 *   <li>For each starting offset {@code 0..w-1}, slide a window across {@code s} in steps of {@code w}, counting the words seen.</li>
 *   <li>When a word is not in the dictionary or is seen too often, shrink the window from the left (word by word) until the counts fit; when a non-word appears, reset the window.</li>
 *   <li>When the window holds exactly {@code m} words, record its left index.</li>
 * </ol>
 *
 * <p><b>Example walkthrough</b>
 * <ul>
 *   <li>For {@code s = "barfoothefoobarman"} and {@code words = ["foo", "bar"]}: {@code barfoo} starts at 0 and {@code foobar} starts at 9.</li>
 *   <li>Result: {@code [0, 9]}.</li>
 * </ul>
 *
 * <p><b>Complexity Analysis:</b>
 * <ul>
 *   <li><b>Time Complexity:</b> {@code O(n * w)} — Each offset slides once across the string.</li>
 *   <li><b>Space Complexity:</b> {@code O(m)} — The word counts.</li>
 * </ul>
 *
 * @author Ranga Reddy
 */
public class SubstringWithConcatenationOfAllWords {

    public static void main(String[] args) {
        String[] strings = {"barfoothefoobarman", "wordgoodgoodgoodbestword", "barfoofoobarthefoobarman", "a"};
        String[][] words = {{"foo", "bar"}, {"word", "good", "best", "word"}, {"bar", "foo", "the"}, {"a"}};
        for (int i = 0; i < strings.length; i++) {
            System.out.println("Input  : s = \"" + strings[i] + "\", words = " + Arrays.toString(words[i]));
            System.out.println("Output : " + findSubstring(strings[i], words[i]));
            System.out.println();
        }
    }

    public static List<Integer> findSubstring(String s, String[] words) {
        List<Integer> result = new ArrayList<>();
        if (s == null || words == null || words.length == 0) {
            return result;
        }
        int w = words[0].length();
        int m = words.length;
        Map<String, Integer> need = new HashMap<>();
        for (String word : words) {
            need.merge(word, 1, Integer::sum);
        }
        for (int offset = 0; offset < w; offset++) {
            Map<String, Integer> seen = new HashMap<>();
            int left = offset;
            int count = 0;
            for (int right = offset; right + w <= s.length(); right += w) {
                String word = s.substring(right, right + w);
                if (!need.containsKey(word)) {
                    seen.clear();
                    count = 0;
                    left = right + w;
                    continue;
                }
                seen.merge(word, 1, Integer::sum);
                count++;
                while (seen.get(word) > need.get(word)) {
                    String drop = s.substring(left, left + w);
                    seen.merge(drop, -1, Integer::sum);
                    count--;
                    left += w;
                }
                if (count == m) {
                    result.add(left);
                }
            }
        }
        return result;
    }
}
