package com.ranga.string;

import java.util.LinkedHashSet;
import java.util.Set;

/**
 * User: Ranga Reddy
 * Date: 11/22/2019
 * Time: 9:42 AM
 * Description: Finding the longest substring for non-repated characters.
 */
public class LongestSubString {
    public static void main(String[] args) {

        String words[] = {"abcadbc", "bbbb", "ABDEFGABEF", "GEEKSFORGEEKS"};
        for (String str : words) {
            int len = getLongestSubString(str);
            System.out.println(str + ", " + len);
        }
    }

    private static int getLongestSubString(String str) {
        int max = 0;
        int i = 0, j = 0;
        Set<Character> set = new LinkedHashSet<>();
        String subStr = "";
        while (j < str.length()) {
            if (!set.contains(str.charAt(j))) {
                set.add(str.charAt(j));
                if (set.size() > max) {
                    subStr = set.toString();
                    max = set.size();
                }
                j++;
            } else {
                set.remove(str.charAt(i));
                i++;
            }
        }
        System.out.println(subStr);
        return max;
    }
}
