package com.ranga.string;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Given an array of words and a width {@code maxWidth}, format the text so that each line has exactly {@code maxWidth} characters and is fully justified. Pack as many words as fit on each line, distribute extra spaces as evenly as possible between words (extra spaces go to the left slots), left-justify the last line, and left-justify any line with a single word.
 *
 * <p><b>Algorithm Strategy (Greedy Packing then Space Distribution):</b>
 * <ol>
 *   <li>Greedily add words to the current line while {@code lineLength + wordCount - 1 + nextWord <= maxWidth}, counting one space between words.</li>
 *   <li>For a normal line, compute {@code spaces = maxWidth - letters}, {@code gaps = words - 1}. Each gap gets {@code spaces / gaps}, and the first {@code spaces % gaps} gaps get one extra.</li>
 *   <li>A line with one word, and the last line, is left-justified: words joined by single spaces, then padded with trailing spaces.</li>
 *   <li>Append the finished line to the result and start the next line.</li>
 * </ol>
 *
 * <p><b>Example walkthrough</b>
 * <ul>
 *   <li>For {@code ["This","is","an","example","of","text","justification."]} with width 16: the first line is {@code This    is    an} (8 spaces split 4 and 4), the second is {@code example  of text} (3 spaces split 2 and 1).</li>
 *   <li>The last line, {@code justification.  }, is left-justified.</li>
 * </ul>
 *
 * <p><b>Complexity Analysis:</b>
 * <ul>
 *   <li><b>Time Complexity:</b> {@code O(total characters)} — Every word is placed once and every output line is built once.</li>
 *   <li><b>Space Complexity:</b> {@code O(maxWidth)} — Only the current line is held, apart from the result.</li>
 * </ul>
 *
 * @author Ranga Reddy
 */
public class TextJustification {

    public static void main(String[] args) {
        String[][] inputs = {
            {"This", "is", "an", "example", "of", "text", "justification."},
            {"What", "must", "be", "acknowledgment", "shall", "be"},
            {"Listen", "to", "many,", "speak", "to", "a", "few."}
        };
        int[] widths = {16, 16, 6};
        for (int i = 0; i < inputs.length; i++) {
            System.out.println("Input  : words = " + Arrays.toString(inputs[i]) + ", maxWidth = " + widths[i]);
            for (String line : fullJustify(inputs[i], widths[i])) {
                System.out.println("Output : \"" + line + "\"");
            }
            System.out.println();
        }
    }

    public static List<String> fullJustify(String[] words, int maxWidth) {
        List<String> lines = new ArrayList<>();
        if (words == null || words.length == 0) {
            return lines;
        }
        int i = 0;
        while (i < words.length) {
            int j = i;
            int letters = 0;
            while (j < words.length && letters + words[j].length() + (j - i) <= maxWidth) {
                letters += words[j].length();
                j++;
            }
            int gaps = j - i - 1;
            StringBuilder line = new StringBuilder();
            if (j == words.length || gaps == 0) {
                for (int k = i; k < j; k++) {
                    line.append(words[k]);
                    if (k < j - 1) {
                        line.append(' ');
                    }
                }
                while (line.length() < maxWidth) {
                    line.append(' ');
                }
            } else {
                int spaces = maxWidth - letters;
                int each = spaces / gaps;
                int extra = spaces % gaps;
                for (int k = i; k < j; k++) {
                    line.append(words[k]);
                    if (k < j - 1) {
                        int pad = each + (k - i < extra ? 1 : 0);
                        for (int s = 0; s < pad; s++) {
                            line.append(' ');
                        }
                    }
                }
            }
            lines.add(line.toString());
            i = j;
        }
        return lines;
    }
}
