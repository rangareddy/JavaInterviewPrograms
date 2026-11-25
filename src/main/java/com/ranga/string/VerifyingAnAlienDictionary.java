package com.ranga.string;

import java.util.Arrays;

/**
 * In an alien language the letters of the alphabet appear in a different order. Given a list of {@code words} and the {@code order} of the alphabet, return {@code true} if and only if the words are sorted lexicographically in this alien language.
 *
 * <p><b>Algorithm Strategy (Rank Table and Adjacent Pair Comparison):</b>
 * <ol>
 *   <li>Build {@code rank[c]} from the given order: the index of each letter.</li>
 *   <li>Compare each pair of neighbouring words; sortedness is transitive, so only adjacent pairs matter.</li>
 *   <li>Find the first position where the two words differ; the word with the smaller rank there must come first, otherwise return {@code false}.</li>
 *   <li>If one word is a prefix of the other, the shorter one must come first.</li>
 * </ol>
 *
 * <p><b>Example walkthrough</b>
 * <ul>
 *   <li>For {@code [hello, leetcode]} and the order {@code hlabcdefgijkmnopqrstuvwxyz}: {@code h} ranks before {@code l}.</li>
 *   <li>Result: {@code true}.</li>
 * </ul>
 *
 * <p><b>Complexity Analysis:</b>
 * <ul>
 *   <li><b>Time Complexity:</b> {@code O(total characters)} — Each pair is scanned up to its first difference.</li>
 *   <li><b>Space Complexity:</b> {@code O(1)} — A 26-entry rank array.</li>
 * </ul>
 *
 * @author Ranga Reddy
 */
public class VerifyingAnAlienDictionary {

    public static void main(String[] args) {
        String[][] words = {{"hello", "leetcode"}, {"word", "world", "row"}, {"apple", "app"}, {"kuvp", "q"}};
        String[] orders = {"hlabcdefgijkmnopqrstuvwxyz", "worldabcefghijkmnpqstuvxyz", "abcdefghijklmnopqrstuvwxyz", "ngxlkthsjuoqcpavbfdermiywz"};
        for (int i = 0; i < words.length; i++) {
            System.out.println("Input  : words = " + Arrays.toString(words[i]) + ", order = \"" + orders[i] + "\"");
            System.out.println("Output : " + isAlienSorted(words[i], orders[i]));
            System.out.println();
        }
    }

    public static boolean isAlienSorted(String[] words, String order) {
        if (words == null || order == null) {
            return false;
        }
        int[] rank = new int[26];
        for (int i = 0; i < order.length(); i++) {
            rank[order.charAt(i) - 'a'] = i;
        }
        for (int i = 0; i + 1 < words.length; i++) {
            if (compare(words[i], words[i + 1], rank) > 0) {
                return false;
            }
        }
        return true;
    }

    private static int compare(String a, String b, int[] rank) {
        int n = Math.min(a.length(), b.length());
        for (int i = 0; i < n; i++) {
            if (a.charAt(i) != b.charAt(i)) {
                return rank[a.charAt(i) - 'a'] - rank[b.charAt(i) - 'a'];
            }
        }
        return a.length() - b.length();
    }
}
