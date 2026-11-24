package com.ranga.backtracking;

import java.util.ArrayList;
import java.util.List;

/**
 * A valid IPv4 address has four integers (segments) between 0 and 255 separated by dots, with no leading zeros in a segment (except the segment {@code 0} itself). Given a string {@code s} of digits, return all valid IP addresses that can be formed by inserting dots into {@code s} without reordering or removing digits, in any order.
 *
 * <p><b>Algorithm Strategy (Backtracking over 1 to 3 Digit Segments):</b>
 * <ol>
 *   <li>Build the address one segment at a time, keeping the start index in {@code s} and the number of segments placed.</li>
 *   <li>At each step try segment lengths 1, 2 and 3, as long as they fit in the remaining string.</li>
 *   <li>Reject a segment with a leading zero (length above 1) or a value above 255.</li>
 *   <li>When four segments are placed and the whole string is consumed, add the address; prune early if the remaining digits cannot fill the remaining segments.</li>
 * </ol>
 *
 * <p><b>Example walkthrough</b>
 * <ul>
 *   <li>For {@code 25525511135} the valid splits are {@code 255.255.11.135} and {@code 255.255.111.35}.</li>
 *   <li>Splits such as {@code 255.255.1.1135} fail because a segment would exceed 255.</li>
 * </ul>
 *
 * <p><b>Complexity Analysis:</b>
 * <ul>
 *   <li><b>Time Complexity:</b> {@code O(1)} — At most 3^4 = 81 combinations are explored, because an address has four segments of at most three digits.</li>
 *   <li><b>Space Complexity:</b> {@code O(1)} — The path and the (bounded) result.</li>
 * </ul>
 *
 * @author Ranga Reddy
 */
public class RestoreIPAddresses {

    public static void main(String[] args) {
        String[] inputs = {"25525511135", "0000", "101023", "1111", ""};
        for (String s : inputs) {
            System.out.println("Input  : s = \"" + s + "\"");
            System.out.println("Output : " + restoreIpAddresses(s));
            System.out.println();
        }
    }

    public static List<String> restoreIpAddresses(String s) {
        List<String> result = new ArrayList<>();
        if (s == null || s.length() < 4 || s.length() > 12) {
            return result;
        }
        backtrack(s, 0, new ArrayList<>(), result);
        return result;
    }

    private static void backtrack(String s, int start, List<String> parts, List<String> result) {
        int remainingParts = 4 - parts.size();
        int remainingDigits = s.length() - start;
        if (remainingDigits < remainingParts || remainingDigits > remainingParts * 3) {
            return;
        }
        if (parts.size() == 4) {
            result.add(String.join(".", parts));
            return;
        }
        for (int len = 1; len <= 3 && start + len <= s.length(); len++) {
            String part = s.substring(start, start + len);
            if ((part.length() > 1 && part.charAt(0) == '0') || Integer.parseInt(part) > 255) {
                continue;
            }
            parts.add(part);
            backtrack(s, start + len, parts, result);
            parts.remove(parts.size() - 1);
        }
    }
}
