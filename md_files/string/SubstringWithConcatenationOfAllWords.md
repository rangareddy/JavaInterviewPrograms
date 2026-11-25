# Substring with Concatenation of All Words

Given a string `s` and an array of strings `words` of the same length, return all starting indices of substrings in `s` that are a concatenation of each word in `words` exactly once, in any order, with no characters in between.

## Algorithm Strategy (Word-Sized Sliding Window)

1. Let `w` be the word length and `m` the number of words. Count the words in a `HashMap`.
2. For each starting offset `0..w-1`, slide a window across `s` in steps of `w`, counting the words seen.
3. When a word is not in the dictionary or is seen too often, shrink the window from the left (word by word) until the counts fit; when a non-word appears, reset the window.
4. When the window holds exactly `m` words, record its left index.

## Example walkthrough

- For `s = "barfoothefoobarman"` and `words = ["foo", "bar"]`: `barfoo` starts at 0 and `foobar` starts at 9.
- Result: `[0, 9]`.

## Complexity Analysis

- **Time Complexity:** `O(n * w)` — Each offset slides once across the string.
- **Space Complexity:** `O(m)` — The word counts.

## Approaches

The same problem can be solved in several ways. The approach marked (Optimized) is the one to use in practice.

### Approach 1: Check Every Start Index

At every index, cut the next `m` words and compare their counts with the dictionary.

```java
public static java.util.List<Integer> everyStart(String s, String[] words) {
    java.util.List<Integer> out = new java.util.ArrayList<>(); int w = words[0].length(), m = words.length; java.util.Map<String, Integer> need = new java.util.HashMap<>(); for (String x : words) need.merge(x, 1, Integer::sum);
    for (int i = 0; i + w * m <= s.length(); i++) { java.util.Map<String, Integer> seen = new java.util.HashMap<>(); int j = 0; for (; j < m; j++) { String x = s.substring(i + j * w, i + (j + 1) * w); if (!need.containsKey(x)) break; seen.merge(x, 1, Integer::sum); if (seen.get(x) > need.get(x)) break; } if (j == m) out.add(i); }
    return out;
}
```

- **Time Complexity:** O(n * m * w)
- **Space Complexity:** O(m)

### Approach 2: Word-Sized Sliding Window (Optimized)

Slide a window in steps of the word length for every offset. This is the version implemented in the repository.

```java
public static java.util.List<Integer> window(String s, String[] words) {
    java.util.List<Integer> out = new java.util.ArrayList<>(); int w = words[0].length(), m = words.length; java.util.Map<String, Integer> need = new java.util.HashMap<>(); for (String x : words) need.merge(x, 1, Integer::sum);
    for (int off = 0; off < w; off++) { java.util.Map<String, Integer> seen = new java.util.HashMap<>(); int left = off, cnt = 0;
        for (int r = off; r + w <= s.length(); r += w) { String x = s.substring(r, r + w); if (!need.containsKey(x)) { seen.clear(); cnt = 0; left = r + w; continue; } seen.merge(x, 1, Integer::sum); cnt++;
            while (seen.get(x) > need.get(x)) { String d = s.substring(left, left + w); seen.merge(d, -1, Integer::sum); cnt--; left += w; } if (cnt == m) out.add(left); } }
    return out;
}
```

- **Time Complexity:** O(n * w)
- **Space Complexity:** O(m)

### Comparison

| # | Approach | Time | Space |
|---|----------|------|-------|
| 1 | Check Every Start Index | O(n * m * w) | O(m) |
| 2 | Word-Sized Sliding Window (Optimized) | O(n * w) | O(m) |

## Source Code

### SubstringWithConcatenationOfAllWords.java

```java
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class SubstringWithConcatenationOfAllWords {

    public static void main(String[] args) {
        String[] strings = {"barfoothefoobarman", "wordgoodgoodgoodbestword", "barfoofoobarthefoobarman", "a"};
        String[][] words = {{"foo", "bar"}, {"word", "good", "best", "word"}, {"bar", "foo", "the"}, {"a"}};
        for (int i = 0; i < strings.length; i++) {
            System.out.println("Input  : s = \"" + strings[i] + "\", words = " + Arrays.toString(words[i]));
            System.out.println("Output : " + findSubstring(strings[i], words[i]));
            System.out.println();
        }
    }

    public static List<Integer> findSubstring(String s, String[] words) {
        List<Integer> result = new ArrayList<>();
        if (s == null || words == null || words.length == 0) {
            return result;
        }
        int w = words[0].length();
        int m = words.length;
        Map<String, Integer> need = new HashMap<>();
        for (String word : words) {
            need.merge(word, 1, Integer::sum);
        }
        for (int offset = 0; offset < w; offset++) {
            Map<String, Integer> seen = new HashMap<>();
            int left = offset;
            int count = 0;
            for (int right = offset; right + w <= s.length(); right += w) {
                String word = s.substring(right, right + w);
                if (!need.containsKey(word)) {
                    seen.clear();
                    count = 0;
                    left = right + w;
                    continue;
                }
                seen.merge(word, 1, Integer::sum);
                count++;
                while (seen.get(word) > need.get(word)) {
                    String drop = s.substring(left, left + w);
                    seen.merge(drop, -1, Integer::sum);
                    count--;
                    left += w;
                }
                if (count == m) {
                    result.add(left);
                }
            }
        }
        return result;
    }
}
```

## Solution

The optimized solution is Approach 2 above, which is also the version implemented in this repository.

The idea: because all words have the same length, any valid substring is a run of `m` consecutive word-sized pieces on the same alignment. Sliding a window in word-sized steps, one alignment at a time, reuses the counts instead of recounting every start index.

**Input:** `s = "barfoothefoobarman"`, `words = ["foo", "bar"]`, word length 3.

| Offset 0, word | Window counts | Action |
|----------------|---------------|--------|
| bar | {bar: 1} | |
| foo | {bar: 1, foo: 1}, count 2 | record index 0 |
| the | not in the dictionary | reset the window |
| foo | {foo: 1} | |
| bar | {foo: 1, bar: 1}, count 2 | record index 9 |
| man | not in the dictionary | reset |

**Output:** `[0, 9]`.
