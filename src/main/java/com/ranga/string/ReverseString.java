package com.ranga.string;

/**
 * Reverse the characters of a string, and reverse the order of the words in a sentence (collapsing extra spaces).
 *
 * <p><b>Algorithm Strategy (Two Pointers Swap on a Character Array):</b>
 * <ol>
 *   <li>Characters: copy the string into a {@code char[]}, put one pointer at each end and swap the two characters while {@code left < right}.</li>
 *   <li>Words: split the text on one or more spaces ({@code trim()} first, then {@code split("\\s+")}).</li>
 *   <li>Walk through the words from the last one to the first and join them with a single space.</li>
 *   <li>Strings are immutable in Java, so each result is a new string.</li>
 * </ol>
 *
 * <p><b>Example walkthrough</b>
 * <ul>
 *   <li>{@code "hello"}: swap {@code h}/{@code o}, then {@code e}/{@code l} to get {@code "olleh"}.</li>
 *   <li>{@code "  the sky  is blue "} gives the words {@code [the, sky, is, blue]}; reversed and joined: {@code "blue is sky the"}.</li>
 * </ul>
 *
 * <p><b>Complexity Analysis:</b>
 * <ul>
 *   <li><b>Time Complexity:</b> {@code O(n)} — Every character is visited once.</li>
 *   <li><b>Space Complexity:</b> {@code O(n)} — The character array or the word list.</li>
 * </ul>
 *
 * @author Ranga Reddy
 */
public class ReverseString {

    public static void main(String[] args) {
        String[] texts = {"hello", "Java", "a", ""};
        for (String text : texts) {
            System.out.println("Input  : text = \"" + text + "\"");
            System.out.println("Output : \"" + reverse(text) + "\"");
            System.out.println();
        }

        String[] sentences = {"the sky is blue", "  hello   world  ", "single"};
        for (String sentence : sentences) {
            System.out.println("Input  : sentence = \"" + sentence + "\"");
            System.out.println("Output : \"" + reverseWords(sentence) + "\"");
            System.out.println();
        }
    }

    public static String reverse(String text) {
        char[] chars = text.toCharArray();
        int left = 0;
        int right = chars.length - 1;
        while (left < right) {
            char temp = chars[left];
            chars[left++] = chars[right];
            chars[right--] = temp;
        }
        return new String(chars);
    }

    public static String reverseWords(String sentence) {
        String trimmed = sentence.trim();
        if (trimmed.isEmpty()) {
            return "";
        }

        String[] words = trimmed.split("\\s+");
        StringBuilder result = new StringBuilder();
        for (int i = words.length - 1; i >= 0; i--) {
            result.append(words[i]);
            if (i > 0) {
                result.append(' ');
            }
        }
        return result.toString();
    }
}
