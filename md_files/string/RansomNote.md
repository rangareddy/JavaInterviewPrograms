# Ransom Note

Given two strings `ransomNote` and `magazine`, return `true` if `ransomNote` can be constructed using the letters of `magazine`, where each letter of the magazine can be used only once.

## Algorithm Strategy (Counting Letters)

1. Count each letter of `magazine` in an array of 26 counters.
2. For every letter of `ransomNote`, decrement its counter.
3. If a counter goes below zero, the magazine has too few of that letter: return `false`.
4. If the whole note is processed, return `true`.

## Example walkthrough

- For `"aa"` and `"ab"`: the magazine has one `a`, but the note needs two.
- Result: `false`.

## Complexity Analysis

- **Time Complexity:** `O(m + n)` — One pass over each string.
- **Space Complexity:** `O(1)` — 26 counters.

## Approaches

The same problem can be solved in several ways. The approach marked (Optimized) is the one to use in practice.

### Approach 1: Remove Used Letters

Find and delete each letter from a mutable copy of the magazine.

```java
public static boolean removeLetters(String note, String mag) {
    StringBuilder sb = new StringBuilder(mag);
    for (char c : note.toCharArray()) { int i = sb.indexOf(String.valueOf(c)); if (i < 0) return false; sb.deleteCharAt(i); }
    return true;
}
```

- **Time Complexity:** O(m * n)
- **Space Complexity:** O(m)

### Approach 2: HashMap Counts

Count letters with a `HashMap`.

```java
public static boolean hashMap(String note, String mag) {
    java.util.Map<Character, Integer> m = new java.util.HashMap<>();
    for (char c : mag.toCharArray()) m.merge(c, 1, Integer::sum);
    for (char c : note.toCharArray()) { Integer v = m.get(c); if (v == null || v == 0) return false; m.put(c, v - 1); }
    return true;
}
```

- **Time Complexity:** O(m + n)
- **Space Complexity:** O(1)

### Approach 3: Counter Array (Optimized)

26 integer counters. This is the version implemented in the repository.

```java
public static boolean counters(String note, String mag) {
    int[] c = new int[26];
    for (char ch : mag.toCharArray()) c[ch - 'a']++;
    for (char ch : note.toCharArray()) if (--c[ch - 'a'] < 0) return false;
    return true;
}
```

- **Time Complexity:** O(m + n)
- **Space Complexity:** O(1)

### Comparison

| # | Approach | Time | Space |
|---|----------|------|-------|
| 1 | Remove Used Letters | O(m * n) | O(m) |
| 2 | HashMap Counts | O(m + n) | O(1) |
| 3 | Counter Array (Optimized) | O(m + n) | O(1) |

## Source Code

### RansomNote.java

```java
public class RansomNote {

    public static void main(String[] args) {
        String[][] inputs = {{"aa", "ab"}, {"a", "b"}, {"aa", "aab"}, {"", "x"}};
        for (String[] in : inputs) {
            System.out.println("Input  : ransomNote = \"" + in[0] + "\", magazine = \"" + in[1] + "\"");
            System.out.println("Output : " + canConstruct(in[0], in[1]));
            System.out.println();
        }
    }

    public static boolean canConstruct(String ransomNote, String magazine) {
        if (ransomNote == null || ransomNote.isEmpty()) {
            return true;
        }
        if (magazine == null || magazine.length() < ransomNote.length()) {
            return false;
        }
        int[] counts = new int[26];
        for (char c : magazine.toCharArray()) {
            counts[c - 'a']++;
        }
        for (char c : ransomNote.toCharArray()) {
            if (--counts[c - 'a'] < 0) {
                return false;
            }
        }
        return true;
    }
}
```

## Solution

The optimized solution is Approach 3 above, which is also the version implemented in this repository.

The idea: the magazine is a stock of letters. Count it once, then spend one unit per letter of the note; running out means the note cannot be built.

**Input:** `ransomNote = "aa"`, `magazine = "ab"`.

| Step | Letter | Counter after | Result |
|------|--------|---------------|--------|
| count `a` | | a: 1 | |
| count `b` | | a: 1, b: 1 | |
| note letter 1 | a | a: 0 | ok |
| note letter 2 | a | a: -1 | out of stock |

**Output:** `false`.
