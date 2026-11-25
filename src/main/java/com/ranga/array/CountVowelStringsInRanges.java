package com.ranga.array;

import java.util.Arrays;

/**
 * Given a 0-indexed array of strings {@code words} and queries {@code [l, r]}, for each query return the number of strings in {@code words[l..r]} that start and end with a vowel ({@code a}, {@code e}, {@code i}, {@code o} or {@code u}).
 *
 * <p><b>Algorithm Strategy (Prefix Sum over Qualifying Words):</b>
 * <ol>
 *   <li>Mark every word that starts and ends with a vowel.</li>
 *   <li>Build a prefix array where {@code prefix[i + 1]} is the number of marked words among the first {@code i + 1}.</li>
 *   <li>Answer each query as {@code prefix[r + 1] - prefix[l]}.</li>
 *   <li>Collect the answers in order.</li>
 * </ol>
 *
 * <p><b>Example walkthrough</b>
 * <ul>
 *   <li>For {@code [aba, bcb, ece, aa, e]} and the query {@code [0, 2]}: {@code aba} and {@code ece} qualify.</li>
 *   <li>Result for the first query: {@code 2}.</li>
 * </ul>
 *
 * <p><b>Complexity Analysis:</b>
 * <ul>
 *   <li><b>Time Complexity:</b> {@code O(n + q)} — Build once, answer each query in O(1).</li>
 *   <li><b>Space Complexity:</b> {@code O(n)} — The prefix array.</li>
 * </ul>
 *
 * @author Ranga Reddy
 */
public class CountVowelStringsInRanges {

    public static void main(String[] args) {
        String[] words = {"aba", "bcb", "ece", "aa", "e"};
        int[][] queries = {{0, 2}, {1, 4}, {1, 1}};
        System.out.println("Input  : words = " + Arrays.toString(words) + ", queries = " + Arrays.deepToString(queries));
        System.out.println("Output : " + Arrays.toString(vowelStrings(words, queries)));
        System.out.println();
        String[] other = {"a", "e", "i"};
        int[][] otherQueries = {{0, 2}, {0, 1}, {2, 2}};
        System.out.println("Input  : words = " + Arrays.toString(other) + ", queries = " + Arrays.deepToString(otherQueries));
        System.out.println("Output : " + Arrays.toString(vowelStrings(other, otherQueries)));
    }

    public static int[] vowelStrings(String[] words, int[][] queries) {
        if (words == null || queries == null) {
            return new int[0];
        }
        int[] prefix = new int[words.length + 1];
        for (int i = 0; i < words.length; i++) {
            String word = words[i];
            boolean good = !word.isEmpty() && isVowel(word.charAt(0)) && isVowel(word.charAt(word.length() - 1));
            prefix[i + 1] = prefix[i] + (good ? 1 : 0);
        }
        int[] answers = new int[queries.length];
        for (int i = 0; i < queries.length; i++) {
            answers[i] = prefix[queries[i][1] + 1] - prefix[queries[i][0]];
        }
        return answers;
    }

    private static boolean isVowel(char c) {
        return c == 'a' || c == 'e' || c == 'i' || c == 'o' || c == 'u';
    }
}
