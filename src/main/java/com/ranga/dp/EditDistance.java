package com.ranga.dp;

/**
 * Given two strings {@code word1} and {@code word2}, return the minimum number of operations needed to convert {@code word1} into {@code word2}. The allowed operations are inserting a character, deleting a character and replacing a character.
 *
 * <p><b>Algorithm Strategy (DP over Prefixes (Levenshtein Distance)):</b>
 * <ol>
 *   <li>Let {@code dp[i][j]} be the edit distance between the first {@code i} characters of {@code word1} and the first {@code j} characters of {@code word2}.</li>
 *   <li>Base cases: converting a prefix to the empty string needs {@code i} deletions, and building a prefix from the empty string needs {@code j} insertions.</li>
 *   <li>If the last characters are equal, no operation is needed for them: {@code dp[i][j] = dp[i-1][j-1]}.</li>
 *   <li>Otherwise take the cheapest of replace ({@code dp[i-1][j-1]}), delete ({@code dp[i-1][j]}) and insert ({@code dp[i][j-1]}) plus 1. Only the previous row is needed, so two arrays are enough.</li>
 * </ol>
 *
 * <p><b>Example walkthrough</b>
 * <ul>
 *   <li>For {@code "horse"} to {@code "ros"}: replace h with r, delete r, delete e.</li>
 *   <li>Result: {@code 3}. For {@code "intention"} to {@code "execution"} the distance is {@code 5}.</li>
 * </ul>
 *
 * <p><b>Complexity Analysis:</b>
 * <ul>
 *   <li><b>Time Complexity:</b> {@code O(m * n)} — Every pair of prefixes is examined once.</li>
 *   <li><b>Space Complexity:</b> {@code O(n)} — Two rows of the table.</li>
 * </ul>
 *
 * @author Ranga Reddy
 */
public class EditDistance {

    public static void main(String[] args) {
        String[][] inputs = {{"horse", "ros"}, {"intention", "execution"}, {"", "abc"}, {"abc", ""}, {"same", "same"}};
        for (String[] input : inputs) {
            System.out.println("Input  : word1 = \"" + input[0] + "\", word2 = \"" + input[1] + "\"");
            System.out.println("Output : " + minDistance(input[0], input[1]));
            System.out.println();
        }
    }

    public static int minDistance(String word1, String word2) {
        int m = word1.length();
        int n = word2.length();
        int[] previous = new int[n + 1];
        int[] current = new int[n + 1];
        for (int j = 0; j <= n; j++) {
            previous[j] = j;                        // insert j characters
        }

        for (int i = 1; i <= m; i++) {
            current[0] = i;                         // delete i characters
            for (int j = 1; j <= n; j++) {
                if (word1.charAt(i - 1) == word2.charAt(j - 1)) {
                    current[j] = previous[j - 1];
                } else {
                    current[j] = 1 + Math.min(previous[j - 1], Math.min(previous[j], current[j - 1]));
                }
            }
            int[] temp = previous;
            previous = current;
            current = temp;
        }
        return previous[n];
    }
}
