package com.ranga.backtracking;

import java.util.ArrayList;
import java.util.List;

/**
 * Given a string {@code s}, partition it so that every substring of the partition is a palindrome, and return all possible palindrome partitionings of {@code s}.
 *
 * <p><b>Algorithm Strategy (Backtracking over Palindromic Prefixes):</b>
 * <ol>
 *   <li>At a start position, try every prefix {@code s[start..end]} of the remaining string.</li>
 *   <li>Only a prefix that is a palindrome may be cut off: add it to the current partition and recurse on the rest of the string starting at {@code end + 1}.</li>
 *   <li>When the start position reaches the end of the string, the current list of pieces is a complete partitioning: record a copy.</li>
 *   <li>Remove the piece again after the recursive call (backtrack) and try the next longer prefix. A precomputed table {@code isPal[i][j]} makes each palindrome test {@code O(1)}.</li>
 * </ol>
 *
 * <p><b>Example walkthrough</b>
 * <ul>
 *   <li>For {@code "aab"}: cut {@code "a"}, then {@code "a"}, then {@code "b"} {@code -> [a, a, b]}; cut {@code "aa"} then {@code "b"} {@code -> [aa, b]}.</li>
 *   <li>{@code "aab"} itself is not a palindrome, so there is no single-piece partition.</li>
 * </ul>
 *
 * <p><b>Complexity Analysis:</b>
 * <ul>
 *   <li><b>Time Complexity:</b> {@code O(n * 2^n)} — Up to {@code 2^(n-1)} partitionings, each copied in {@code O(n)}.</li>
 *   <li><b>Space Complexity:</b> {@code O(n^2)} — The palindrome table (plus recursion depth {@code O(n)}).</li>
 * </ul>
 *
 * @author Ranga Reddy
 */
public class PalindromePartitioning {

    public static void main(String[] args) {
        String[] inputs = {"aab", "a", "aaa", "abc", "racecar"};
        for (String s : inputs) {
            System.out.println("Input  : s = \"" + s + "\"");
            System.out.println("Output : " + partition(s));
            System.out.println();
        }
    }

    public static List<List<String>> partition(String s) {
        int n = s.length();
        boolean[][] isPal = new boolean[n][n];
        for (int end = 0; end < n; end++) {                 // isPal[start][end] from shorter palindromes
            for (int start = 0; start <= end; start++) {
                isPal[start][end] = s.charAt(start) == s.charAt(end) && (end - start < 2 || isPal[start + 1][end - 1]);
            }
        }

        List<List<String>> result = new ArrayList<>();
        backtrack(s, 0, isPal, new ArrayList<>(), result);
        return result;
    }

    private static void backtrack(String s, int start, boolean[][] isPal, List<String> current, List<List<String>> result) {
        if (start == s.length()) {
            result.add(new ArrayList<>(current));
            return;
        }
        for (int end = start; end < s.length(); end++) {
            if (isPal[start][end]) {
                current.add(s.substring(start, end + 1));
                backtrack(s, end + 1, isPal, current, result);
                current.remove(current.size() - 1);
            }
        }
    }
}
