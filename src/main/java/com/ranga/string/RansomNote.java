package com.ranga.string;

/**
 * Given two strings {@code ransomNote} and {@code magazine}, return {@code true} if {@code ransomNote} can be constructed using the letters of {@code magazine}, where each letter of the magazine can be used only once.
 *
 * <p><b>Algorithm Strategy (Counting Letters):</b>
 * <ol>
 *   <li>Count each letter of {@code magazine} in an array of 26 counters.</li>
 *   <li>For every letter of {@code ransomNote}, decrement its counter.</li>
 *   <li>If a counter goes below zero, the magazine has too few of that letter: return {@code false}.</li>
 *   <li>If the whole note is processed, return {@code true}.</li>
 * </ol>
 *
 * <p><b>Example walkthrough</b>
 * <ul>
 *   <li>For {@code "aa"} and {@code "ab"}: the magazine has one {@code a}, but the note needs two.</li>
 *   <li>Result: {@code false}.</li>
 * </ul>
 *
 * <p><b>Complexity Analysis:</b>
 * <ul>
 *   <li><b>Time Complexity:</b> {@code O(m + n)} — One pass over each string.</li>
 *   <li><b>Space Complexity:</b> {@code O(1)} — 26 counters.</li>
 * </ul>
 *
 * @author Ranga Reddy
 */
public class RansomNote {

    public static void main(String[] args) {
        String[][] inputs = {{"aa", "ab"}, {"a", "b"}, {"aa", "aab"}, {"", "x"}};
        for (String[] in : inputs) {
            System.out.println("Input  : ransomNote = \"" + in[0] + "\", magazine = \"" + in[1] + "\"");
            System.out.println("Output : " + canConstruct(in[0], in[1]));
            System.out.println();
        }
    }

    public static boolean canConstruct(String ransomNote, String magazine) {
        if (ransomNote == null || ransomNote.isEmpty()) {
            return true;
        }
        if (magazine == null || magazine.length() < ransomNote.length()) {
            return false;
        }
        int[] counts = new int[26];
        for (char c : magazine.toCharArray()) {
            counts[c - 'a']++;
        }
        for (char c : ransomNote.toCharArray()) {
            if (--counts[c - 'a'] < 0) {
                return false;
            }
        }
        return true;
    }
}
