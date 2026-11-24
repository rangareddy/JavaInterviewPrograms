package com.ranga.stack;

/**
 * You are given a 0-indexed string {@code s} of even length {@code n} with exactly {@code n / 2} opening brackets {@code [} and {@code n / 2} closing brackets {@code ]}. You may swap the brackets at any two indices any number of times. Return the minimum number of swaps to make {@code s} balanced.
 *
 * <p><b>Algorithm Strategy (Count Unmatched Closing Brackets):</b>
 * <ol>
 *   <li>Scan the string with a counter {@code open} of unmatched opening brackets.</li>
 *   <li>An opening bracket increments {@code open}; a closing bracket decrements it if {@code open > 0}, otherwise it is unmatched and {@code unmatched} increases.</li>
 *   <li>Each swap can fix two unmatched closing brackets (swap an unmatched {@code ]} near the front with an unused {@code [} near the end).</li>
 *   <li>The answer is {@code (unmatched + 1) / 2}.</li>
 * </ol>
 *
 * <p><b>Example walkthrough</b>
 * <ul>
 *   <li>For {@code ][][}: the first {@code ]} is unmatched, the pairs {@code []} match, and the last {@code [} stays open.</li>
 *   <li>One unmatched closing bracket needs {@code (1 + 1) / 2 = 1} swap.</li>
 * </ul>
 *
 * <p><b>Complexity Analysis:</b>
 * <ul>
 *   <li><b>Time Complexity:</b> {@code O(n)} — One pass.</li>
 *   <li><b>Space Complexity:</b> {@code O(1)} — Two counters.</li>
 * </ul>
 *
 * @author Ranga Reddy
 */
public class MinimumNumberOfSwapsToMakeTheStringBalanced {

    public static void main(String[] args) {
        String[] inputs = {"][][", "]]][[[", "[]", "]][[[]][[]]][["};
        for (String s : inputs) {
            System.out.println("Input  : s = \"" + s + "\"");
            System.out.println("Output : " + minSwaps(s));
            System.out.println();
        }
    }

    public static int minSwaps(String s) {
        if (s == null) {
            return 0;
        }
        int open = 0;
        int unmatched = 0;
        for (char c : s.toCharArray()) {
            if (c == '[') {
                open++;
            } else if (open > 0) {
                open--;
            } else {
                unmatched++;
            }
        }
        return (unmatched + 1) / 2;
    }
}
