package com.ranga.string;

/**
 * Given two version strings {@code version1} and {@code version2}, each made of numeric revisions joined by dots, compare them revision by revision from left to right, ignoring leading zeros. A missing revision counts as 0. Return {@code -1} if {@code version1 < version2}, {@code 1} if {@code version1 > version2}, and {@code 0} if they are equal.
 *
 * <p><b>Algorithm Strategy (Segment-by-Segment Comparison):</b>
 * <ol>
 *   <li>Split both strings on {@code .} (the dot must be escaped in a regular expression).</li>
 *   <li>Iterate to the longer of the two lengths.</li>
 *   <li>For each position parse the revision as an integer, using 0 when the array is exhausted.</li>
 *   <li>Return -1 or 1 at the first difference, otherwise 0.</li>
 * </ol>
 *
 * <p><b>Example walkthrough</b>
 * <ul>
 *   <li>For {@code 1.01} and {@code 1.001}: the revisions are 1 and 1, then 1 and 1 (leading zeros are ignored): equal.</li>
 *   <li>For {@code 1.0.1} and {@code 1}: the third revision is 1 against 0, so the first is larger.</li>
 * </ul>
 *
 * <p><b>Complexity Analysis:</b>
 * <ul>
 *   <li><b>Time Complexity:</b> {@code O(n + m)} — Each character is read a constant number of times.</li>
 *   <li><b>Space Complexity:</b> {@code O(n + m)} — The split arrays.</li>
 * </ul>
 *
 * @author Ranga Reddy
 */
public class CompareVersionNumbers {

    public static void main(String[] args) {
        String[][] inputs = {{"1.2", "1.10"}, {"1.01", "1.001"}, {"1.0", "1.0.0"}, {"1.0.1", "1"}, {"0.1", "1.1"}};
        for (String[] in : inputs) {
            System.out.println("Input  : version1 = \"" + in[0] + "\", version2 = \"" + in[1] + "\"");
            System.out.println("Output : " + compareVersion(in[0], in[1]));
            System.out.println();
        }
    }

    public static int compareVersion(String version1, String version2) {
        String[] a = version1.split("\\.");
        String[] b = version2.split("\\.");
        int length = Math.max(a.length, b.length);
        for (int i = 0; i < length; i++) {
            int x = i < a.length ? Integer.parseInt(a[i]) : 0;
            int y = i < b.length ? Integer.parseInt(b[i]) : 0;
            if (x != y) {
                return x < y ? -1 : 1;
            }
        }
        return 0;
    }
}
