# Valid Word

A word is valid if it contains at least 3 characters, only digits and English letters, at least one vowel (`a e i o u`, any case) and at least one consonant. Given a string `word`, return `true` if it is valid, otherwise `false`.

## Algorithm Strategy (Classify Each Character Once)

1. Reject immediately if the length is below 3.
2. Scan the characters. A letter is a vowel or a consonant; a digit is allowed and counts as neither.
3. Any other character (for example `$` or `@`) makes the word invalid.
4. At the end, require both a vowel and a consonant.

## Example walkthrough

- For `234Adas`: it has 7 characters, only digits and letters, vowels `A` and `a`, and consonants `d` and `s`.
- Result: `true`.

## Complexity Analysis

- **Time Complexity:** `O(n)` — One pass.
- **Space Complexity:** `O(1)` — Two flags.

## Approaches

The same problem can be solved in several ways. The approach marked (Optimized) is the one to use in practice.

### Approach 1: Regular Expressions

Three regex checks: allowed characters, a vowel and a consonant. Compact, but it rescans the string three times.

```java
public static boolean regex(String w) { return w.length() >= 3 && w.matches("[a-zA-Z0-9]+") && w.matches(".*[aeiouAEIOU].*") && w.matches(".*[b-df-hj-np-tv-zB-DF-HJ-NP-TV-Z].*"); }
```

- **Time Complexity:** O(n)
- **Space Complexity:** O(1)

### Approach 2: Single Scan with Flags (Optimized)

Classify every character once and keep two flags. This is the version implemented in the repository.

```java
public static boolean scan(String w) { if (w.length() < 3) return false; boolean v = false, c = false; for (char ch : w.toLowerCase().toCharArray()) { if (ch >= 'a' && ch <= 'z') { if ("aeiou".indexOf(ch) >= 0) v = true; else c = true; } else if (ch < '0' || ch > '9') return false; } return v && c; }
```

- **Time Complexity:** O(n)
- **Space Complexity:** O(1)

### Comparison

| # | Approach | Time | Space |
|---|----------|------|-------|
| 1 | Regular Expressions | O(n) | O(1) |
| 2 | Single Scan with Flags (Optimized) | O(n) | O(1) |

## Source Code

### ValidWord.java

```java
public class ValidWord {

    public static void main(String[] args) {
        String[] inputs = {"234Adas", "b3", "a3$e", "AEIOU", "xyz9"};
        for (String word : inputs) {
            System.out.println("Input  : word = \"" + word + "\"");
            System.out.println("Output : " + isValid(word));
            System.out.println();
        }
    }

    public static boolean isValid(String word) {
        if (word == null || word.length() < 3) {
            return false;
        }
        boolean vowel = false;
        boolean consonant = false;
        for (int i = 0; i < word.length(); i++) {
            char c = Character.toLowerCase(word.charAt(i));
            if (c >= 'a' && c <= 'z') {
                if (c == 'a' || c == 'e' || c == 'i' || c == 'o' || c == 'u') {
                    vowel = true;
                } else {
                    consonant = true;
                }
            } else if (c < '0' || c > '9') {
                return false;
            }
        }
        return vowel && consonant;
    }
}
```

## Solution

The optimized solution is Approach 2 above, which is also the version implemented in this repository.

The idea: every rule can be decided while looking at one character at a time, so two booleans plus an early exit on illegal characters are enough.

**Input:** `word = "234Adas"`.

| Character | Kind | `vowel` | `consonant` |
|-----------|------|---------|-------------|
| `2`, `3`, `4` | digit | false | false |
| `A` | vowel | true | false |
| `d` | consonant | true | true |
| `a` | vowel | true | true |
| `s` | consonant | true | true |

Length is 7, and both flags are set.

**Output:** `true`.
