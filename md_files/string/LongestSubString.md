# Longest Substring Without Repeating Characters

Utility class to find the longest substring without repeating characters.

## Algorithm Strategy (Sliding Window with a Set)

1. Maintain a window `[left, right)` of the string that contains no repeated characters, and a `Set` holding exactly the characters inside the window.
2. Look at the character `c = str.charAt(right)`. If it is not in the set, add it and grow the window by incrementing `right`; if the window is now longer than the best one, remember its start and length.
3. If `c` is already in the set, remove `str.charAt(left)` from the set and increment `left` (shrinking the window from the left); repeat until the duplicate is gone.
4. Continue until `right` reaches the end of the string. The remembered start and length give the answer.

## Example walkthrough for `"abcadbc"`

- Window grows: `a, ab, abc` (length 3); the next `a` is a duplicate, so `a` is dropped from the left.
- Window `bca` then grows with `d`: `bcad` (length 4, new best); the next `b` is a duplicate, so the left side shrinks to `cad`, then `cadb` and so on.
- Longest substring: `"bcad"` with length 4.

## Complexity Analysis

- **Time Complexity:** `O(n)` — Each index enters and leaves the window at most once.
- **Space Complexity:** `O(min(n, k))` — The set holds at most the distinct characters of the string (`k` = alphabet size).

## Approaches

The same problem can be solved in several ways. They are listed from the simplest to the most efficient.

### Approach 1: Brute Force

Check every substring `[i, j]` and test whether all of its characters are distinct using a set; keep the longest.

```java
public static String longestBrute(String s) {
    String best = "";
    for (int i = 0; i < s.length(); i++) {
        Set<Character> seen = new HashSet<>();
        for (int j = i; j < s.length(); j++) {
            if (!seen.add(s.charAt(j))) break;      // duplicate: no longer valid
            if (j - i + 1 > best.length()) best = s.substring(i, j + 1);
        }
    }
    return best;
}
```

- **Time Complexity:** O(n^2)
- **Space Complexity:** O(min(n, k))

### Approach 2: Sliding Window with a Set

Grow the window on the right while characters are new; when a duplicate appears, remove characters from the left until it is gone. Each character is added and removed at most once (up to `2n` steps). This is the version implemented in the repository.

```java
public static String longestWindowSet(String s) {
    Set<Character> window = new HashSet<>();
    int left = 0, right = 0, bestStart = 0, bestLen = 0;
    while (right < s.length()) {
        if (window.add(s.charAt(right))) {
            right++;
            if (right - left > bestLen) { bestStart = left; bestLen = right - left; }
        } else {
            window.remove(s.charAt(left++));
        }
    }
    return s.substring(bestStart, bestStart + bestLen);
}
```

- **Time Complexity:** O(n)
- **Space Complexity:** O(min(n, k))

### Approach 3: Sliding Window with Last-Seen Index (Optimized)

Remember the last index of every character. When a duplicate is found, jump `left` directly past its previous occurrence instead of removing characters one by one, so each character is visited exactly once.

```java
public static String longestLastSeen(String s) {
    Map<Character, Integer> lastSeen = new HashMap<>();
    int left = 0, bestStart = 0, bestLen = 0;
    for (int right = 0; right < s.length(); right++) {
        Integer previous = lastSeen.put(s.charAt(right), right);
        if (previous != null && previous >= left) {
            left = previous + 1;                 // jump past the previous occurrence
        }
        if (right - left + 1 > bestLen) { bestStart = left; bestLen = right - left + 1; }
    }
    return s.substring(bestStart, bestStart + bestLen);
}
```

- **Time Complexity:** O(n)
- **Space Complexity:** O(min(n, k))

### Approach 4: Last-Seen Array for ASCII Input

When the alphabet is small and known (for example ASCII), replace the map by an `int[128]` array, which is faster and avoids boxing.

```java
public static int longestLengthAscii(String s) {
    int[] lastSeen = new int[128];            // index + 1 of the last occurrence, 0 = never seen
    int left = 0, best = 0;
    for (int right = 0; right < s.length(); right++) {
        char c = s.charAt(right);
        left = Math.max(left, lastSeen[c]);
        lastSeen[c] = right + 1;
        best = Math.max(best, right - left + 1);
    }
    return best;
}
```

- **Time Complexity:** O(n)
- **Space Complexity:** O(1) (fixed-size array)

### Comparison

| # | Approach | Time | Space |
|---|----------|------|-------|
| 1 | Brute Force | O(n^2) | O(min(n, k)) |
| 2 | Sliding Window with a Set | O(n) | O(min(n, k)) |
| 3 | Sliding Window with Last-Seen Index (Optimized) | O(n) | O(min(n, k)) |
| 4 | Last-Seen Array for ASCII Input | O(n) | O(1) (fixed-size array) |

### Optimized Solution

The optimized solution is Approach 3 above. The version implemented in this repository is Approach 2.

## Source Code

### LongestSubString.java

```java
import java.util.HashSet;
import java.util.Set;

public class LongestSubString {

    public static void main(String[] args) {
        String[] words = {"abcadbc", "bbbb", "ABDEFGABEF", "GEEKSFORGEEKS"};
        for (String str : words) {
            String longest = getLongestSubString(str);
            System.out.println("Input  : \"" + str + "\"");
            System.out.println("Output : \"" + longest + "\" (length " + longest.length() + ")");
            System.out.println();
        }
    }

    /**
     * Finds the longest substring without repeating characters.
     *
     * @param str the input string
     * @return the first longest substring without repeating characters, or an empty string for null/empty input
     */
    private static String getLongestSubString(String str) {
        // Guard Clause: nothing to scan in a null or empty string
        if (str == null || str.isEmpty()) {
            return "";
        }

        int left = 0, right = 0;
        int bestStart = 0, bestLength = 0;
        Set<Character> window = new HashSet<>();

        while (right < str.length()) {
            char c = str.charAt(right);
            if (!window.contains(c)) {
                // Grow the window to the right
                window.add(c);
                right++;
                if (window.size() > bestLength) {
                    bestStart = left;
                    bestLength = window.size();
                }
            } else {
                // Shrink the window from the left until the duplicate is removed
                window.remove(str.charAt(left));
                left++;
            }
        }
        return str.substring(bestStart, bestStart + bestLength);
    }
}
```
