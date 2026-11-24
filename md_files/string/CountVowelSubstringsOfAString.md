# Count Vowel Substrings of a String

A vowel substring is a substring made only of vowels (`a`, `e`, `i`, `o`, `u`) that contains all five vowels at least once. Given a string `word`, return the number of vowel substrings.

## Algorithm Strategy (Check Every Substring that Stays Vowel-Only)

1. For each start index, extend the end index while the characters are vowels; stop at the first consonant.
2. Keep a set (or a bitmask) of the vowels seen in the window.
3. Whenever the set holds all five vowels, count the substring.
4. Sum over all start indexes.

## Example walkthrough

- For `aeiouu`: the substrings `aeiou` and `aeiouu` contain all five vowels.
- Result: `2`.

## Complexity Analysis

- **Time Complexity:** `O(n^2)` — Each start extends over the vowel run, at most n characters.
- **Space Complexity:** `O(1)` — A bitmask.

## Approaches

The same problem can be solved in several ways. The approach marked (Optimized) is the one to use in practice.

### Approach 1: Brute Force with Sets

For every substring check that it has only vowels and all five of them.

```java
public static int bruteForce(String w) {
    int c = 0;
    for (int i = 0; i < w.length(); i++) for (int j = i + 1; j <= w.length(); j++) {
        String s = w.substring(i, j); boolean ok = true; java.util.Set<Character> seen = new java.util.HashSet<>();
        for (char ch : s.toCharArray()) { if ("aeiou".indexOf(ch) < 0) { ok = false; break; } seen.add(ch); }
        if (ok && seen.size() == 5) c++;
    }
    return c;
}
```

- **Time Complexity:** O(n^3)
- **Space Complexity:** O(n)

### Approach 2: Extend While Vowels with a Bitmask (Optimized)

Extend each start only through vowels and track the vowels in a 5-bit mask. This is the version implemented in the repository.

```java
public static int bitmask(String w) {
    int c = 0;
    for (int i = 0; i < w.length(); i++) { int m = 0; for (int j = i; j < w.length(); j++) { int b = "aeiou".indexOf(w.charAt(j)); if (b < 0) break; m |= 1 << b; if (m == 31) c++; } }
    return c;
}
```

- **Time Complexity:** O(n^2)
- **Space Complexity:** O(1)

### Approach 3: Sliding Window per Vowel Run

Within each vowel-only run count substrings with all five vowels using `atMost(5) - atMost(4)` distinct-vowel windows. Linear time.

```java
public static int atMost(String w) {
    int total = 0, i = 0, n = w.length();
    while (i < n) {
        if ("aeiou".indexOf(w.charAt(i)) < 0) { i++; continue; }
        int j = i; while (j < n && "aeiou".indexOf(w.charAt(j)) >= 0) j++;
        total += windows(w.substring(i, j), 5) - windows(w.substring(i, j), 4); i = j;
    }
    return total;
}
private static int windows(String s, int k) {
    int[] cnt = new int[26]; int distinct = 0, l = 0, res = 0;
    for (int r = 0; r < s.length(); r++) { if (cnt[s.charAt(r) - 'a']++ == 0) distinct++; while (distinct > k) if (--cnt[s.charAt(l++) - 'a'] == 0) distinct--; res += r - l + 1; }
    return res;
}
```

- **Time Complexity:** O(n)
- **Space Complexity:** O(1)

### Comparison

| # | Approach | Time | Space |
|---|----------|------|-------|
| 1 | Brute Force with Sets | O(n^3) | O(n) |
| 2 | Extend While Vowels with a Bitmask (Optimized) | O(n^2) | O(1) |
| 3 | Sliding Window per Vowel Run | O(n) | O(1) |

## Source Code

### CountVowelSubstringsOfAString.java

```java
public class CountVowelSubstringsOfAString {

    public static void main(String[] args) {
        String[] inputs = {"aeiouu", "unicornarihan", "cuaieuouac", "bbaeixoubb"};
        for (String word : inputs) {
            System.out.println("Input  : word = \"" + word + "\"");
            System.out.println("Output : " + countVowelSubstrings(word));
            System.out.println();
        }
    }

    public static int countVowelSubstrings(String word) {
        if (word == null) {
            return 0;
        }
        int count = 0;
        for (int start = 0; start < word.length(); start++) {
            int mask = 0;
            for (int end = start; end < word.length(); end++) {
                int bit = vowelBit(word.charAt(end));
                if (bit == 0) {
                    break;
                }
                mask |= bit;
                if (mask == 31) {
                    count++;
                }
            }
        }
        return count;
    }

    private static int vowelBit(char c) {
        switch (c) {
            case 'a': return 1;
            case 'e': return 2;
            case 'i': return 4;
            case 'o': return 8;
            case 'u': return 16;
            default: return 0;
        }
    }
}
```

## Solution

The optimized solution is Approach 2 above, which is also the version implemented in this repository.

The idea: a vowel substring can never cross a consonant, so each start only extends through a run of vowels. A 5-bit mask records which vowels have appeared, and `31` (all bits set) means a match.

**Input:** `word = "aeiouu"`.

| Start | End | Mask after the window | Counted? |
|-------|-----|------------------------|----------|
| 0 | 3 (`aeio`) | 15 | no |
| 0 | 4 (`aeiou`) | 31 | yes |
| 0 | 5 (`aeiouu`) | 31 | yes |
| 1 to 5 | | never reaches 31 | no |

**Output:** `2`.
