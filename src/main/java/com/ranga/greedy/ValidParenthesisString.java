package com.ranga.greedy;

/**
 * Given a string {@code s} containing only {@code (}, {@code )} and {@code *}, return {@code true} if it is valid. A {@code *} may be treated as {@code (}, as {@code )} or as an empty string. A string is valid when every {@code (} has a matching {@code )} after it and every {@code )} has a matching {@code (} before it.
 *
 * <p><b>Algorithm Strategy (Greedy Range of Open Parentheses):</b>
 * <ol>
 *   <li>Track the range {@code [low, high]} of possible numbers of unmatched {@code (} after each character.</li>
 *   <li>A {@code (} increases both bounds. A {@code )} decreases both. A {@code *} lowers {@code low} (as {@code )}) and raises {@code high} (as {@code (}).</li>
 *   <li>If {@code high < 0}, there are too many {@code )} even when every {@code *} is an opening bracket: return {@code false}.</li>
 *   <li>Keep {@code low} at least 0 (an unmatched {@code )} cannot be repaired later, but a {@code *} can be an empty string). At the end the string is valid when {@code low == 0}.</li>
 * </ol>
 *
 * <p><b>Example walkthrough</b>
 * <ul>
 *   <li>For {@code "(*))"}: ranges {@code [1,1]}, {@code [0,2]}, {@code [0,1]}, {@code [0,0]}; the final {@code low} is 0.</li>
 *   <li>Result: {@code true}.</li>
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
public class ValidParenthesisString {

    public static void main(String[] args) {
        String[] inputs = {"()", "(*)", "(*))", "(((*)", ")(", "*", "(()*"};
        for (String s : inputs) {
            System.out.println("Input  : s = \"" + s + "\"");
            System.out.println("Output : " + checkValidString(s));
            System.out.println();
        }
    }

    public static boolean checkValidString(String s) {
        int low = 0;            // fewest possible unmatched '('
        int high = 0;           // most possible unmatched '('
        for (char c : s.toCharArray()) {
            if (c == '(') {
                low++;
                high++;
            } else if (c == ')') {
                low--;
                high--;
            } else {
                low--;
                high++;
            }
            if (high < 0) {
                return false;
            }
            low = Math.max(low, 0);
        }
        return low == 0;
    }
}
