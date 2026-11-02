package com.ranga.blind75.string;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Design an algorithm to encode a list of strings into a single string that can be sent over the network, and decode it back into the original list. The strings may contain any characters.
 *
 * <p><b>Algorithm Strategy (Length-Prefix Encoding):</b>
 * <ol>
 *   <li>A delimiter alone is not safe because the strings may contain it. Instead prefix every string with its length and a separator: {@code length + "#" + string}.</li>
 *   <li>Encode: append {@code length#string} for every string to a {@code StringBuilder}.</li>
 *   <li>Decode: read digits up to the next {@code #} to get the length {@code len}, then take exactly the next {@code len} characters as the string, whatever they contain, and continue after them.</li>
 *   <li>Because the length tells how many characters to read, even strings containing {@code #}, digits or empty strings are decoded correctly.</li>
 * </ol>
 *
 * <p><b>Example walkthrough</b>
 * <ul>
 *   <li>{@code ["we", "say", ":", "yes"]} is encoded as {@code "2#we3#say1#:3#yes"}.</li>
 *   <li>Decoding reads 2 then {@code "we"}, 3 then {@code "say"}, 1 then {@code ":"}, 3 then {@code "yes"}.</li>
 * </ul>
 *
 * <p><b>Complexity Analysis:</b>
 * <ul>
 *   <li><b>Time Complexity:</b> {@code O(n)} — Both directions touch each character once ({@code n} = total length).</li>
 *   <li><b>Space Complexity:</b> {@code O(n)} — The encoded string and the decoded list.</li>
 * </ul>
 *
 * @author Ranga Reddy
 */
public class EncodeAndDecodeStrings {

    public static void main(String[] args) {
        List<List<String>> inputs = Arrays.asList(
                Arrays.asList("lint", "code", "love", "you"),
                Arrays.asList("we", "say", ":", "yes"),
                Arrays.asList("", "#", "3#abc"));
        for (List<String> strs : inputs) {
            String encoded = encode(strs);
            System.out.println("Input   : " + strs);
            System.out.println("Encoded : " + encoded);
            System.out.println("Output  : " + decode(encoded));
            System.out.println();
        }
    }

    public static String encode(List<String> strs) {
        StringBuilder sb = new StringBuilder();
        for (String str : strs) {
            sb.append(str.length()).append('#').append(str);
        }
        return sb.toString();
    }

    public static List<String> decode(String s) {
        List<String> result = new ArrayList<>();
        int i = 0;
        while (i < s.length()) {
            int separator = s.indexOf('#', i);
            int length = Integer.parseInt(s.substring(i, separator));
            result.add(s.substring(separator + 1, separator + 1 + length));
            i = separator + 1 + length;
        }
        return result;
    }
}
