package com.ranga.stack;

/**
 * Given two strings {@code s} and {@code t}, return {@code true} if they are equal after typing them into empty text editors, where {@code #} means a backspace character. Backspacing on an empty text leaves it empty.
 *
 * <p><b>Algorithm Strategy (Two Pointers from the End):</b>
 * <ol>
 *   <li>Process both strings from the right; a {@code #} means skip the next normal character.</li>
 *   <li>For each string, find the next character that survives: count {@code #} as a pending skip and decrement it for every normal character that is passed.</li>
 *   <li>Compare the surviving characters of the two strings one by one.</li>
 *   <li>The strings are equal when both are exhausted at the same time with no mismatch.</li>
 * </ol>
 *
 * <p><b>Example walkthrough</b>
 * <ul>
 *   <li>For {@code ab#c} and {@code ad#c}: both end up as {@code ac}.</li>
 *   <li>Result: {@code true}.</li>
 * </ul>
 *
 * <p><b>Complexity Analysis:</b>
 * <ul>
 *   <li><b>Time Complexity:</b> {@code O(m + n)} — Each character is visited once.</li>
 *   <li><b>Space Complexity:</b> {@code O(1)} — Only indexes and counters.</li>
 * </ul>
 *
 * @author Ranga Reddy
 */
public class BackspaceStringCompare {

    public static void main(String[] args) {
        String[][] inputs = {{"ab#c", "ad#c"}, {"ab##", "c#d#"}, {"a#c", "b"}, {"a##c", "#a#c"}};
        for (String[] in : inputs) {
            System.out.println("Input  : s = \"" + in[0] + "\", t = \"" + in[1] + "\"");
            System.out.println("Output : " + backspaceCompare(in[0], in[1]));
            System.out.println();
        }
    }

    public static boolean backspaceCompare(String s, String t) {
        int i = s.length() - 1;
        int j = t.length() - 1;
        while (i >= 0 || j >= 0) {
            i = nextValid(s, i);
            j = nextValid(t, j);
            if (i < 0 || j < 0) {
                return i < 0 && j < 0;
            }
            if (s.charAt(i) != t.charAt(j)) {
                return false;
            }
            i--;
            j--;
        }
        return true;
    }

    private static int nextValid(String s, int index) {
        int skip = 0;
        while (index >= 0) {
            if (s.charAt(index) == '#') {
                skip++;
            } else if (skip > 0) {
                skip--;
            } else {
                return index;
            }
            index--;
        }
        return -1;
    }
}
