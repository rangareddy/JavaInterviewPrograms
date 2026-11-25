package com.ranga.array;

import java.util.Arrays;

/**
 * Given an array of strings {@code wordsDict} and two different strings {@code word1} and {@code word2} that already exist in the array, return the shortest distance between the two words in the list.
 *
 * <p><b>Algorithm Strategy (One Pass with the Last Seen Indexes):</b>
 * <ol>
 *   <li>Keep {@code index1} and {@code index2}, the last positions where {@code word1} and {@code word2} were seen (start at -1).</li>
 *   <li>Walk the array; whenever a word matches, update its index.</li>
 *   <li>If both indexes are set, the distance {@code |index1 - index2|} is a candidate for the answer.</li>
 *   <li>Return the smallest candidate.</li>
 * </ol>
 *
 * <p><b>Example walkthrough</b>
 * <ul>
 *   <li>For {@code [practice, makes, perfect, coding, makes]}, {@code word1 = coding}, {@code word2 = practice}: {@code practice} at 0 and {@code coding} at 3.</li>
 *   <li>Result: {@code 3}.</li>
 * </ul>
 *
 * <p><b>Complexity Analysis:</b>
 * <ul>
 *   <li><b>Time Complexity:</b> {@code O(n)} — One pass.</li>
 *   <li><b>Space Complexity:</b> {@code O(1)} — Two indexes.</li>
 * </ul>
 *
 * @author Ranga Reddy
 */
public class ShortestWordDistance {

    public static void main(String[] args) {
        String[][] words = {{"practice", "makes", "perfect", "coding", "makes"}, {"practice", "makes", "perfect", "coding", "makes"}, {"a", "b"}};
        String[] first = {"coding", "makes", "a"};
        String[] second = {"practice", "coding", "b"};
        for (int i = 0; i < words.length; i++) {
            System.out.println("Input  : wordsDict = " + Arrays.toString(words[i]) + ", word1 = \"" + first[i] + "\", word2 = \"" + second[i] + "\"");
            System.out.println("Output : " + shortestDistance(words[i], first[i], second[i]));
            System.out.println();
        }
    }

    public static int shortestDistance(String[] wordsDict, String word1, String word2) {
        if (wordsDict == null || word1 == null || word2 == null) {
            return -1;
        }
        int index1 = -1;
        int index2 = -1;
        int best = Integer.MAX_VALUE;
        for (int i = 0; i < wordsDict.length; i++) {
            if (wordsDict[i].equals(word1)) {
                index1 = i;
            } else if (wordsDict[i].equals(word2)) {
                index2 = i;
            }
            if (index1 != -1 && index2 != -1) {
                best = Math.min(best, Math.abs(index1 - index2));
            }
        }
        return best == Integer.MAX_VALUE ? -1 : best;
    }
}
