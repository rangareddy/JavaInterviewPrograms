# Verifying an Alien Dictionary

In an alien language the letters of the alphabet appear in a different order. Given a list of `words` and the `order` of the alphabet, return `true` if and only if the words are sorted lexicographically in this alien language.

## Algorithm Strategy (Rank Table and Adjacent Pair Comparison)

1. Build `rank[c]` from the given order: the index of each letter.
2. Compare each pair of neighbouring words; sortedness is transitive, so only adjacent pairs matter.
3. Find the first position where the two words differ; the word with the smaller rank there must come first, otherwise return `false`.
4. If one word is a prefix of the other, the shorter one must come first.

## Example walkthrough

- For `[hello, leetcode]` and the order `hlabcdefgijkmnopqrstuvwxyz`: `h` ranks before `l`.
- Result: `true`.

## Complexity Analysis

- **Time Complexity:** `O(total characters)` — Each pair is scanned up to its first difference.
- **Space Complexity:** `O(1)` — A 26-entry rank array.

## Approaches

The same problem can be solved in several ways. The approach marked (Optimized) is the one to use in practice.

### Approach 1: Translate to Normal Letters and Compare

Map every word to the normal alphabet and check with `compareTo`.

```java
public static boolean translate(String[] w, String order) { String[] t = new String[w.length]; for (int i = 0; i < w.length; i++) { StringBuilder sb = new StringBuilder(); for (char c : w[i].toCharArray()) sb.append((char) ('a' + order.indexOf(c))); t[i] = sb.toString(); } for (int i = 0; i + 1 < t.length; i++) if (t[i].compareTo(t[i + 1]) > 0) return false; return true; }
```

- **Time Complexity:** O(total characters)
- **Space Complexity:** O(total characters)

### Approach 2: Rank Table and Adjacent Comparison (Optimized)

Compare each pair of neighbours with a rank lookup. This is the version implemented in the repository.

```java
public static boolean ranks(String[] w, String order) { int[] r = new int[26]; for (int i = 0; i < 26; i++) r[order.charAt(i) - 'a'] = i; for (int k = 0; k + 1 < w.length; k++) { String a = w[k], b = w[k + 1]; int n = Math.min(a.length(), b.length()), d = 0; for (int i = 0; i < n; i++) if (a.charAt(i) != b.charAt(i)) { d = r[a.charAt(i) - 'a'] - r[b.charAt(i) - 'a']; break; } if (d == 0) d = a.length() - b.length(); if (d > 0) return false; } return true; }
```

- **Time Complexity:** O(total characters)
- **Space Complexity:** O(1)

### Comparison

| # | Approach | Time | Space |
|---|----------|------|-------|
| 1 | Translate to Normal Letters and Compare | O(total characters) | O(total characters) |
| 2 | Rank Table and Adjacent Comparison (Optimized) | O(total characters) | O(1) |

## Source Code

### VerifyingAnAlienDictionary.java

```java
import java.util.Arrays;

public class VerifyingAnAlienDictionary {

    public static void main(String[] args) {
        String[][] words = {{"hello", "leetcode"}, {"word", "world", "row"}, {"apple", "app"}, {"kuvp", "q"}};
        String[] orders = {"hlabcdefgijkmnopqrstuvwxyz", "worldabcefghijkmnpqstuvxyz", "abcdefghijklmnopqrstuvwxyz", "ngxlkthsjuoqcpavbfdermiywz"};
        for (int i = 0; i < words.length; i++) {
            System.out.println("Input  : words = " + Arrays.toString(words[i]) + ", order = \"" + orders[i] + "\"");
            System.out.println("Output : " + isAlienSorted(words[i], orders[i]));
            System.out.println();
        }
    }

    public static boolean isAlienSorted(String[] words, String order) {
        if (words == null || order == null) {
            return false;
        }
        int[] rank = new int[26];
        for (int i = 0; i < order.length(); i++) {
            rank[order.charAt(i) - 'a'] = i;
        }
        for (int i = 0; i + 1 < words.length; i++) {
            if (compare(words[i], words[i + 1], rank) > 0) {
                return false;
            }
        }
        return true;
    }

    private static int compare(String a, String b, int[] rank) {
        int n = Math.min(a.length(), b.length());
        for (int i = 0; i < n; i++) {
            if (a.charAt(i) != b.charAt(i)) {
                return rank[a.charAt(i) - 'a'] - rank[b.charAt(i) - 'a'];
            }
        }
        return a.length() - b.length();
    }
}
```

## Solution

The optimized solution is Approach 2 above, which is also the version implemented in this repository.

The idea: sorting is decided by the first position where two words differ, using the alien alphabet's order instead of the normal one. A rank table turns each letter into its place in the alien order in constant time. A list is sorted if every adjacent pair is, so only neighbours are compared, and a word that is a prefix of the next one is fine only when it is the shorter one.

**Input:** `words = [hello, leetcode]`, `order = "hlabcdefgijkmnopqrstuvwxyz"`.

| Pair | First difference | Ranks | Result |
|------|------------------|-------|--------|
| hello, leetcode | `h` vs `l` | 0 vs 1 | `hello` first: ok |

**Output:** `true`.
