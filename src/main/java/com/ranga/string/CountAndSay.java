package com.ranga.string;

/**
 * The count-and-say sequence starts with {@code "1"}, and each term describes the previous one by counting groups of equal digits: {@code 1} is read as {@code 11} (one 1), {@code 11} as {@code 21} (two 1s), {@code 21} as {@code 1211} (one 2, one 1). Given {@code n}, return the {@code n}-th term.
 *
 * <p><b>Algorithm Strategy (Iterate Run-Length Encoding):</b>
 * <ol>
 *   <li>Start with {@code "1"} as the first term.</li>
 *   <li>For each next term, scan the current term and count each run of equal digits.</li>
 *   <li>Append the run's count followed by its digit to a {@code StringBuilder}.</li>
 *   <li>Repeat {@code n - 1} times and return the final string.</li>
 * </ol>
 *
 * <p><b>Example walkthrough</b>
 * <ul>
 *   <li>{@code 1} -> {@code 11} -> {@code 21} -> {@code 1211} -> {@code 111221}.</li>
 *   <li>The 4th term is {@code 1211}.</li>
 * </ul>
 *
 * <p><b>Complexity Analysis:</b>
 * <ul>
 *   <li><b>Time Complexity:</b> {@code O(sum of term lengths)} — Each term is scanned once; terms grow by about 30% each step.</li>
 *   <li><b>Space Complexity:</b> {@code O(length of the term)} — The current and next term.</li>
 * </ul>
 *
 * @author Ranga Reddy
 */
public class CountAndSay {

    public static void main(String[] args) {
        int[] inputs = {4, 1, 2, 5, 6};
        for (int n : inputs) {
            System.out.println("Input  : n = " + n);
            System.out.println("Output : " + countAndSay(n));
            System.out.println();
        }
    }

    public static String countAndSay(int n) {
        if (n <= 0) {
            return "";
        }
        String term = "1";
        for (int step = 1; step < n; step++) {
            StringBuilder next = new StringBuilder();
            int i = 0;
            while (i < term.length()) {
                int j = i;
                while (j < term.length() && term.charAt(j) == term.charAt(i)) {
                    j++;
                }
                next.append(j - i).append(term.charAt(i));
                i = j;
            }
            term = next.toString();
        }
        return term;
    }
}
