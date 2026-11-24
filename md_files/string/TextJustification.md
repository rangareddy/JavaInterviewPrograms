# Text Justification

Given an array of words and a width `maxWidth`, format the text so that each line has exactly `maxWidth` characters and is fully justified. Pack as many words as fit on each line, distribute extra spaces as evenly as possible between words (extra spaces go to the left slots), left-justify the last line, and left-justify any line with a single word.

## Algorithm Strategy (Greedy Packing then Space Distribution)

1. Greedily add words to the current line while `lineLength + wordCount - 1 + nextWord <= maxWidth`, counting one space between words.
2. For a normal line, compute `spaces = maxWidth - letters`, `gaps = words - 1`. Each gap gets `spaces / gaps`, and the first `spaces % gaps` gaps get one extra.
3. A line with one word, and the last line, is left-justified: words joined by single spaces, then padded with trailing spaces.
4. Append the finished line to the result and start the next line.

## Example walkthrough

- For `["This","is","an","example","of","text","justification."]` with width 16: the first line is `This    is    an` (8 spaces split 4 and 4), the second is `example  of text` (3 spaces split 2 and 1).
- The last line, `justification.  `, is left-justified.

## Complexity Analysis

- **Time Complexity:** `O(total characters)` — Every word is placed once and every output line is built once.
- **Space Complexity:** `O(maxWidth)` — Only the current line is held, apart from the result.

## Approaches

The same problem can be solved in several ways. The approach marked (Optimized) is the one to use in practice.

### Approach 1: Word by Word with Padding Pass

Build each line with single spaces first, then insert the missing spaces gap by gap in a second pass.

```java
public static java.util.List<String> twoPass(String[] words, int w) {
    java.util.List<String> out = new java.util.ArrayList<>();
    int i = 0;
    while (i < words.length) {
        java.util.List<String> line = new java.util.ArrayList<>(); int len = 0;
        while (i < words.length && len + words[i].length() + line.size() <= w) { len += words[i].length(); line.add(words[i++]); }
        StringBuilder sb = new StringBuilder();
        if (i == words.length || line.size() == 1) {
            sb.append(String.join(" ", line));
            while (sb.length() < w) sb.append(' ');
        } else {
            int gaps = line.size() - 1, spaces = w - len;
            for (int k = 0; k < line.size(); k++) {
                sb.append(line.get(k));
                if (k < gaps) { int pad = spaces / gaps + (k < spaces % gaps ? 1 : 0); for (int p = 0; p < pad; p++) sb.append(' '); }
            }
        }
        out.add(sb.toString());
    }
    return out;
}
```

- **Time Complexity:** O(total characters)
- **Space Complexity:** O(maxWidth)

### Approach 2: Index Window with Direct Distribution (Optimized)

Find the window of words that fits, then write the line once with the even-plus-extra spaces. This is the version implemented in the repository.

```java
public static java.util.List<String> direct(String[] words, int w) {
    java.util.List<String> out = new java.util.ArrayList<>();
    int i = 0;
    while (i < words.length) {
        int j = i, letters = 0;
        while (j < words.length && letters + words[j].length() + (j - i) <= w) { letters += words[j].length(); j++; }
        int gaps = j - i - 1;
        StringBuilder sb = new StringBuilder();
        if (j == words.length || gaps == 0) {
            for (int k = i; k < j; k++) { sb.append(words[k]); if (k < j - 1) sb.append(' '); }
            while (sb.length() < w) sb.append(' ');
        } else {
            int spaces = w - letters, each = spaces / gaps, extra = spaces % gaps;
            for (int k = i; k < j; k++) {
                sb.append(words[k]);
                if (k < j - 1) for (int s = 0; s < each + (k - i < extra ? 1 : 0); s++) sb.append(' ');
            }
        }
        out.add(sb.toString()); i = j;
    }
    return out;
}
```

- **Time Complexity:** O(total characters)
- **Space Complexity:** O(maxWidth)

### Comparison

| # | Approach | Time | Space |
|---|----------|------|-------|
| 1 | Word by Word with Padding Pass | O(total characters) | O(maxWidth) |
| 2 | Index Window with Direct Distribution (Optimized) | O(total characters) | O(maxWidth) |

## Source Code

### TextJustification.java

```java
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

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
```

## Solution

The optimized solution is Approach 2 above, which is also the version implemented in this repository.

The idea: decide the words of a line first, then compute the spaces in one step. The extra spaces that do not divide evenly go to the leftmost gaps, and the last line or a one-word line is left-justified.

**Input:** `words = ["This", "is", "an", "example", "of", "text", "justification."]`, `maxWidth = 16`.

| Line | Words | Letters | Spaces to place | Result |
|------|-------|---------|-----------------|--------|
| 1 | This, is, an | 8 | 8 over 2 gaps = 4, 4 | `This    is    an` |
| 2 | example, of, text | 13 | 3 over 2 gaps = 2, 1 | `example  of text` |
| 3 (last) | justification. | 14 | left-justified | `justification.  ` |

**Output:** the three lines above, each exactly 16 characters.
