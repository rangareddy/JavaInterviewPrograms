package com.ranga.greedy;

import java.util.ArrayList;
import java.util.List;

/**
 * Given a string {@code s}, partition it into as many parts as possible so that each letter appears in at most one part. Return the sizes of the parts, in order.
 *
 * <p><b>Algorithm Strategy (Greedy with Last Occurrences):</b>
 * <ol>
 *   <li>Record the last index of every letter.</li>
 *   <li>Scan the string, keeping the {@code end} of the current part: the maximum last index of all letters seen in it.</li>
 *   <li>When the scan index reaches {@code end}, no letter of the part appears later: close the part and record its size.</li>
 *   <li>Start the next part right after {@code end}.</li>
 * </ol>
 *
 * <p><b>Example walkthrough</b>
 * <ul>
 *   <li>For {@code "ababcbacadefegdehijhklij"}: the first part must reach the last {@code a} (index 8), the second ends at 15 (last {@code e}), the third at 23.</li>
 *   <li>Result: {@code [9, 7, 8]}.</li>
 * </ul>
 *
 * <p><b>Complexity Analysis:</b>
 * <ul>
 *   <li><b>Time Complexity:</b> {@code O(n)} — Two passes over the string.</li>
 *   <li><b>Space Complexity:</b> {@code O(1)} — At most 26 letters.</li>
 * </ul>
 *
 * @author Ranga Reddy
 */
public class PartitionLabels {

    public static void main(String[] args) {
        String[] inputs = {"ababcbacadefegdehijhklij", "eccbbbbdec", "a", "abc"};
        for (String s : inputs) {
            System.out.println("Input  : s = \"" + s + "\"");
            System.out.println("Output : " + partitionLabels(s));
            System.out.println();
        }
    }

    public static List<Integer> partitionLabels(String s) {
        int[] last = new int[26];
        for (int i = 0; i < s.length(); i++) {
            last[s.charAt(i) - 'a'] = i;
        }

        List<Integer> sizes = new ArrayList<>();
        int start = 0;
        int end = 0;
        for (int i = 0; i < s.length(); i++) {
            end = Math.max(end, last[s.charAt(i) - 'a']);
            if (i == end) {
                sizes.add(end - start + 1);
                start = i + 1;
            }
        }
        return sizes;
    }
}
