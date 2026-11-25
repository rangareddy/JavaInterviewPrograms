package com.ranga.dp;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

/**
 * A word {@code a} is a predecessor of a word {@code b} if you can insert exactly one letter anywhere in {@code a} (without changing the order of the other letters) to make {@code b}. A word chain is a sequence of words in which each word is a predecessor of the next. Given a list of {@code words}, return the length of the longest possible word chain.
 *
 * <p><b>Algorithm Strategy (DP over Words Sorted by Length):</b>
 * <ol>
 *   <li>Sort the words by length so that predecessors are processed first.</li>
 *   <li>Let {@code best[w]} be the length of the longest chain ending at word {@code w}, stored in a {@code HashMap}.</li>
 *   <li>For each word, try deleting each character to get a candidate predecessor; if it is in the map, {@code best[w] = max(best[w], best[pred] + 1)}.</li>
 *   <li>The answer is the maximum of {@code best} over all words.</li>
 * </ol>
 *
 * <p><b>Example walkthrough</b>
 * <ul>
 *   <li>For {@code ["a","b","ba","bca","bda","bdca"]}: {@code a -> ba -> bda -> bdca} has length 4.</li>
 *   <li>Result: {@code 4}.</li>
 * </ul>
 *
 * <p><b>Complexity Analysis:</b>
 * <ul>
 *   <li><b>Time Complexity:</b> {@code O(n * L^2)} — For each word, {@code L} deletions each cost O(L) to build and hash.</li>
 *   <li><b>Space Complexity:</b> {@code O(n * L)} — The map of chain lengths.</li>
 * </ul>
 *
 * @author Ranga Reddy
 */
public class LongestStringChain {

    public static void main(String[] args) {
        String[][] inputs = {{"a", "b", "ba", "bca", "bda", "bdca"}, {"xbc", "pcxbcf", "xb", "cxbc", "pcxbc"}, {"abcd", "dbqca"}, {"a"}};
        for (String[] words : inputs) {
            System.out.println("Input  : words = " + Arrays.toString(words));
            System.out.println("Output : " + longestStrChain(words.clone()));
            System.out.println();
        }
    }

    public static int longestStrChain(String[] words) {
        if (words == null || words.length == 0) {
            return 0;
        }
        Arrays.sort(words, (a, b) -> a.length() - b.length());
        Map<String, Integer> best = new HashMap<>();
        int answer = 1;
        for (String word : words) {
            int length = 1;
            for (int i = 0; i < word.length(); i++) {
                String predecessor = word.substring(0, i) + word.substring(i + 1);
                length = Math.max(length, best.getOrDefault(predecessor, 0) + 1);
            }
            best.put(word, length);
            answer = Math.max(answer, length);
        }
        return answer;
    }
}
