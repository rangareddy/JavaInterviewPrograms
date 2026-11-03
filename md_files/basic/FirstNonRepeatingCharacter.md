# First Non-Repeating Character

Given a string `s`, find the first character that appears exactly once and return its index, or `-1` if there is none.

## Algorithm Strategy (Two Passes with a Frequency Array)

1. Count how many times each character occurs. For lowercase letters an `int[26]` is enough; a `Map<Character, Integer>` is the general version.
2. Scan the string again from the left.
3. The first character whose count is exactly 1 is the answer; return its index.
4. If the scan finishes without finding one, return `-1`.

## Example walkthrough

- For `"leetcode"`: counts are `l=1, e=3, t=1, c=1, o=1, d=1`; the first character with count 1 is `l` at index 0.
- For `"aabb"` every count is 2, so the result is `-1`.

## Complexity Analysis

- **Time Complexity:** `O(n)` — Two passes over the string.
- **Space Complexity:** `O(1)` — The count array has a fixed size (26 letters).

## Approaches

The same problem can be solved in several ways. The approach marked (Optimized) is the one to use in practice.

### Approach 1: Nested Loops

For every character scan the whole string looking for another copy.

```java
public static int firstUniqueCharBrute(String s) {
    for (int i = 0; i < s.length(); i++) {
        boolean unique = true;
        for (int j = 0; j < s.length(); j++)
            if (i != j && s.charAt(i) == s.charAt(j)) { unique = false; break; }
        if (unique) return i;
    }
    return -1;
}
```

- **Time Complexity:** O(n^2)
- **Space Complexity:** O(1)

### Approach 2: indexOf / lastIndexOf

A character is unique when its first and last index are equal.

```java
public static int firstUniqueCharIndexOf(String s) {
    for (int i = 0; i < s.length(); i++)
        if (s.indexOf(s.charAt(i)) == s.lastIndexOf(s.charAt(i))) return i;
    return -1;
}
```

- **Time Complexity:** O(n^2)
- **Space Complexity:** O(1)

### Approach 3: LinkedHashMap of Counts

A `LinkedHashMap` keeps the insertion order, so the first entry with count 1 is the answer. Works for any character set.

```java
public static int firstUniqueCharLinkedMap(String s) {
    Map<Character, Integer> counts = new LinkedHashMap<>();
    for (char c : s.toCharArray()) counts.merge(c, 1, Integer::sum);
    for (Map.Entry<Character, Integer> e : counts.entrySet())
        if (e.getValue() == 1) return s.indexOf(e.getKey());
    return -1;
}
```

- **Time Complexity:** O(n)
- **Space Complexity:** O(k)

### Approach 4: Two Passes with a Count Array (Optimized)

Count the letters, then find the first one with count 1. This is the version implemented in the repository.

```java
public static int firstUniqueChar(String s) {
    int[] counts = new int[26];
    for (int i = 0; i < s.length(); i++) counts[s.charAt(i) - 'a']++;
    for (int i = 0; i < s.length(); i++) if (counts[s.charAt(i) - 'a'] == 1) return i;
    return -1;
}
```

- **Time Complexity:** O(n)
- **Space Complexity:** O(1)

### Comparison

| # | Approach | Time | Space |
|---|----------|------|-------|
| 1 | Nested Loops | O(n^2) | O(1) |
| 2 | indexOf / lastIndexOf | O(n^2) | O(1) |
| 3 | LinkedHashMap of Counts | O(n) | O(k) |
| 4 | Two Passes with a Count Array (Optimized) | O(n) | O(1) |

### Optimized Solution

The optimized solution is Approach 4 above, which is also the version implemented in this repository.

## Source Code

### FirstNonRepeatingCharacter.java

```java
public class FirstNonRepeatingCharacter {

    public static void main(String[] args) {
        String[] inputs = {"leetcode", "loveleetcode", "aabb", "z", ""};
        for (String s : inputs) {
            int index = firstUniqueChar(s);
            System.out.println("Input  : s = \"" + s + "\"");
            System.out.println("Output : " + index + (index == -1 ? "" : " ('" + s.charAt(index) + "')"));
            System.out.println();
        }
    }

    public static int firstUniqueChar(String s) {
        int[] counts = new int[26];
        for (int i = 0; i < s.length(); i++) {
            counts[s.charAt(i) - 'a']++;
        }

        for (int i = 0; i < s.length(); i++) {
            if (counts[s.charAt(i) - 'a'] == 1) {
                return i;
            }
        }
        return -1;
    }
}
```
