# Anagram

**Definition:** An anagram is a word or phrase formed by rearranging the letters of a different word or phrase, 
typically using all the original letters exactly once.
    
**Example:** The word **silent** can be rearranged into **listen**.

## Algorithm Strategy (Character Frequency Count)

1. If either string is `null`, or the lengths differ, the strings cannot be anagrams, return `false`.
2. Walk through the first string and count how many times each character occurs, using a `Map<Character, Integer>`.
3. Walk through the second string and decrement the count of every character. If a character is missing or its count is already `0`, the second string has a character the first one does not have, return `false`.
4. Because both strings have the same length and no count went negative, every count is back to `0`, so the strings are anagrams.

## Example walkthrough

- For `"listen"` and `"silent"` the counts after the first pass are `l=1, i=1, s=1, t=1, e=1, n=1`; decrementing for every character of `"silent"` brings every count to `0`: anagram.
- For `"aa"` and `"bb"` the first `b` is not in the map, so the check fails immediately: not an anagram (XOR would have wrongly said yes).

## Complexity Analysis

- **Time Complexity:** `O(n)` — One pass over each string with `O(1)` average map operations.
- **Space Complexity:** `O(k)` — The map holds at most the distinct characters (`k`) of the string.

## Approaches

The same problem can be solved in several ways. They are listed from the simplest to the most efficient.

### Approach 1: Sort Both Strings

Anagrams contain the same characters, so after sorting both strings they must be identical.

```java
public static boolean anagramSort(String a, String b) {
    if (a == null || b == null || a.length() != b.length()) return false;
    char[] x = a.toCharArray(), y = b.toCharArray();
    Arrays.sort(x);
    Arrays.sort(y);
    return Arrays.equals(x, y);
}
```

- **Time Complexity:** O(n log n)
- **Space Complexity:** O(n)

### Approach 2: Frequency Map (HashMap)

Count the characters of the first string and subtract the characters of the second; any shortage means they differ. Works for any Unicode text. This is the version implemented in the repository.

```java
public static boolean anagramMap(String a, String b) {
    if (a == null || b == null || a.length() != b.length()) return false;
    Map<Character, Integer> counts = new HashMap<>();
    for (char c : a.toCharArray()) counts.merge(c, 1, Integer::sum);
    for (char c : b.toCharArray()) {
        Integer count = counts.get(c);
        if (count == null || count == 0) return false;
        counts.put(c, count - 1);
    }
    return true;
}
```

- **Time Complexity:** O(n)
- **Space Complexity:** O(k)

### Approach 3: Frequency Array (Optimized)

For a fixed alphabet (here the 256 extended-ASCII characters) use an `int[]` instead of a map: increment for the first string, decrement for the second, and fail as soon as a count goes below zero. Fastest option, no boxing.

```java
public static boolean anagramArray(String a, String b) {
    if (a == null || b == null || a.length() != b.length()) return false;
    int[] counts = new int[256];
    for (int i = 0; i < a.length(); i++) {
        counts[a.charAt(i)]++;
        counts[b.charAt(i)]--;
    }
    for (int count : counts) {
        if (count != 0) return false;
    }
    return true;
}
```

- **Time Complexity:** O(n)
- **Space Complexity:** O(1) (fixed-size array)

### Approach 4: Pitfall: XOR of All Characters (Incorrect)

XOR-ing every character of both strings gives 0 for anagrams, but also for non-anagrams such as `"aa"` and `"bb"` (equal characters cancel out in pairs). The same problem affects summing the characters (`"ad"` vs `"bc"`). Do not use these as an anagram check.

```java
public static boolean anagramXorWrong(String a, String b) {
    if (a.length() != b.length()) return false;
    int value = 0;
    for (int i = 0; i < a.length(); i++) {
        value ^= a.charAt(i);
        value ^= b.charAt(i);
    }
    return value == 0;     // WRONG: returns true for ("aa", "bb")
}
```

- **Time Complexity:** O(n)
- **Space Complexity:** O(1)

### Comparison

| # | Approach | Time | Space |
|---|----------|------|-------|
| 1 | Sort Both Strings | O(n log n) | O(n) |
| 2 | Frequency Map (HashMap) | O(n) | O(k) |
| 3 | Frequency Array (Optimized) | O(n) | O(1) (fixed-size array) |
| 4 | Pitfall: XOR of All Characters (Incorrect) | O(n) | O(1) |

### Optimized Solution

The optimized solution is Approach 3 above. The version implemented in this repository is Approach 2.

## Source Code

### Anagram.java

```java
import java.util.HashMap;
import java.util.Map;

public class Anagram {

    public static void main(String[] args) {
        String[][] input = {{"abc", "bac"}, {"bac", "bacd"}, {"abc", null}, {"listen", "silent"}, {"aa", "bb"}};

        for (String[] inputs : input) {
            String str1 = inputs[0];
            String str2 = inputs[1];
            System.out.println("Input  : " + str1 + ", " + str2);
            System.out.println("Output : " + (checkAnagram(str1, str2) ? "Anagram" : "Not an Anagram"));
            System.out.println();
        }
    }

    /**
     * Checks whether the two strings are anagrams.
     *
     * @param str1 the first string
     * @param str2 the second string
     * @return {@code true} if both strings contain exactly the same characters with the same counts
     */
    private static boolean checkAnagram(String str1, String str2) {
        // Guard Clause: null inputs and different lengths can never be anagrams
        if (str1 == null || str2 == null || str1.length() != str2.length()) {
            return false;
        }

        Map<Character, Integer> counts = new HashMap<>();
        for (char c : str1.toCharArray()) {
            counts.merge(c, 1, Integer::sum);
        }

        for (char c : str2.toCharArray()) {
            Integer count = counts.get(c);
            if (count == null || count == 0) {
                return false;
            }
            counts.put(c, count - 1);
        }
        return true;
    }
}
```
