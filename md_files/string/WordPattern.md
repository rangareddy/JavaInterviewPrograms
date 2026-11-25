# Word Pattern

Given a `pattern` and a string `s`, return `true` if `s` follows the same pattern: there is a bijection between a letter in `pattern` and a non-empty word in `s`, so that each letter maps to exactly one word and each word is mapped from exactly one letter.

## Algorithm Strategy (Check Both Directions of the Mapping)

1. Split `s` into words; if the number of words differs from the length of the pattern, return `false`.
2. Keep two maps: letter to word and word to letter.
3. For every position, if the letter is already mapped, the word must match; if the word is already mapped, the letter must match.
4. Otherwise record both mappings. Return `true` if every position agrees.

## Example walkthrough

- For `abba` and `dog cat cat dog`: a to dog, b to cat, b to cat (matches), a to dog (matches).
- Result: `true`.

## Complexity Analysis

- **Time Complexity:** `O(n)` — One pass over the words.
- **Space Complexity:** `O(n)` — The two maps.

## Approaches

The same problem can be solved in several ways. The approach marked (Optimized) is the one to use in practice.

### Approach 1: Compare First-Occurrence Indexes

Two sequences follow the same pattern when every position has the same first-occurrence index in both.

```java
public static boolean firstIndex(String p, String s) { String[] w = s.trim().split("\\s+"); if (w.length != p.length()) return false; java.util.Map<Object, Integer> a = new java.util.HashMap<>(), b = new java.util.HashMap<>(); for (int i = 0; i < w.length; i++) { Integer x = a.put(p.charAt(i), i), y = b.put(w[i], i); if (!java.util.Objects.equals(x, y)) return false; } return true; }
```

- **Time Complexity:** O(n)
- **Space Complexity:** O(n)

### Approach 2: Two Maps (Optimized)

Keep letter-to-word and word-to-letter maps. This is the version implemented in the repository.

```java
public static boolean twoMaps(String p, String s) { String[] w = s.trim().split("\\s+"); if (w.length != p.length()) return false; java.util.Map<Character, String> a = new java.util.HashMap<>(); java.util.Map<String, Character> b = new java.util.HashMap<>(); for (int i = 0; i < w.length; i++) { char c = p.charAt(i); if (a.containsKey(c) && !a.get(c).equals(w[i])) return false; if (b.containsKey(w[i]) && b.get(w[i]) != c) return false; a.put(c, w[i]); b.put(w[i], c); } return true; }
```

- **Time Complexity:** O(n)
- **Space Complexity:** O(n)

### Comparison

| # | Approach | Time | Space |
|---|----------|------|-------|
| 1 | Compare First-Occurrence Indexes | O(n) | O(n) |
| 2 | Two Maps (Optimized) | O(n) | O(n) |

## Source Code

### WordPattern.java

```java
import java.util.HashMap;
import java.util.Map;

public class WordPattern {

    public static void main(String[] args) {
        String[] patterns = {"abba", "abba", "aaaa", "abba"};
        String[] strings = {"dog cat cat dog", "dog cat cat fish", "dog cat cat dog", "dog dog dog dog"};
        for (int i = 0; i < patterns.length; i++) {
            System.out.println("Input  : pattern = \"" + patterns[i] + "\", s = \"" + strings[i] + "\"");
            System.out.println("Output : " + wordPattern(patterns[i], strings[i]));
            System.out.println();
        }
    }

    public static boolean wordPattern(String pattern, String s) {
        if (pattern == null || s == null) {
            return false;
        }
        String[] words = s.trim().split("\\s+");
        if (words.length != pattern.length()) {
            return false;
        }
        Map<Character, String> letterToWord = new HashMap<>();
        Map<String, Character> wordToLetter = new HashMap<>();
        for (int i = 0; i < words.length; i++) {
            char letter = pattern.charAt(i);
            String word = words[i];
            if (letterToWord.containsKey(letter) && !letterToWord.get(letter).equals(word)) {
                return false;
            }
            if (wordToLetter.containsKey(word) && wordToLetter.get(word) != letter) {
                return false;
            }
            letterToWord.put(letter, word);
            wordToLetter.put(word, letter);
        }
        return true;
    }
}
```

## Solution

The optimized solution is Approach 2 above, which is also the version implemented in this repository.

The idea: "follows the pattern" means a bijection, so the mapping has to be consistent in both directions. One map alone would accept `abba` with `dog dog dog dog`, since a and b would both map to `dog`. The second map catches that: `dog` would be mapped from two different letters.

**Input:** `pattern = "abba"`, `s = "dog cat cat dog"`.

| Position | Letter | Word | Letter-to-word | Word-to-letter |
|----------|--------|------|----------------|----------------|
| 0 | a | dog | a -> dog (new) | dog -> a (new) |
| 1 | b | cat | b -> cat (new) | cat -> b (new) |
| 2 | b | cat | matches | matches |
| 3 | a | dog | matches | matches |

**Output:** `true`.
