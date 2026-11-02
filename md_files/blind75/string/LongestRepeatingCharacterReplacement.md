# Longest Repeating Character Replacement

Given a string `s` of uppercase letters and an integer `k`, you may change at most `k` characters to any other uppercase letter. Return the length of the longest substring containing the same letter after the changes.

## Algorithm Strategy (Sliding Window with Character Counts)

1. Keep a window `[left, right]` and an array `count[26]` with the frequency of each letter inside the window.
2. Track `maxCount`, the highest frequency of any single letter seen in a window. The characters that must be replaced are `windowSize - maxCount`.
3. Expand `right`; if `(windowSize - maxCount) > k`, the window needs too many replacements, so shrink it by moving `left` one step (and decrement its count).
4. The window never shrinks below the best size found, so `right - left + 1` at the end is the answer. (`maxCount` is never decreased because only a larger count can improve the answer.)

## Example walkthrough

- For `"AABABBA"` and `k = 1`: the window `"AABA"` has 3 As, so 1 replacement is enough (length 4); extending to 5 would need 2 replacements.
- Result: `4`.

## Complexity Analysis

- **Time Complexity:** `O(n)` — Each index enters the window once and leaves at most once.
- **Space Complexity:** `O(1)` — A fixed array of 26 counters.

## Approaches

The same problem can be solved in several ways. The approach marked (Optimized) is the one to use in practice.

### Approach 1: Brute Force

For every start index extend the substring while the characters that must be replaced (`length - highest frequency`) stay within `k`.

```java
public static int characterReplacementBrute(String s, int k) {
    int best = 0;
    for (int i = 0; i < s.length(); i++) {
        int[] count = new int[26];
        int maxCount = 0;
        for (int j = i; j < s.length(); j++) {
            maxCount = Math.max(maxCount, ++count[s.charAt(j) - 'A']);
            if ((j - i + 1) - maxCount > k) break;
            best = Math.max(best, j - i + 1);
        }
    }
    return best;
}
```

- **Time Complexity:** O(n^2)
- **Space Complexity:** O(1)

### Approach 2: Sliding Window, Recomputing the Maximum

Grow a window and shrink it from the left while it needs more than `k` replacements. The highest frequency is recomputed over the 26 counters each time.

```java
public static int characterReplacementWindow(String s, int k) {
    int[] count = new int[26];
    int left = 0, best = 0;
    for (int right = 0; right < s.length(); right++) {
        count[s.charAt(right) - 'A']++;
        while ((right - left + 1) - maxOf(count) > k) {
            count[s.charAt(left++) - 'A']--;
        }
        best = Math.max(best, right - left + 1);
    }
    return best;
}

private static int maxOf(int[] count) {
    int max = 0;
    for (int c : count) max = Math.max(max, c);
    return max;
}
```

- **Time Complexity:** O(26 * n)
- **Space Complexity:** O(1)

### Approach 3: Sliding Window with a Non-Shrinking Window (Optimized)

The window never needs to shrink below the best size found, so the highest frequency only has to be updated when it grows, and the window slides by one step instead of a `while` loop. This is the version implemented in the repository.

```java
public static int characterReplacement(String s, int k) {
    int[] count = new int[26];
    int left = 0, maxCount = 0, best = 0;
    for (int right = 0; right < s.length(); right++) {
        maxCount = Math.max(maxCount, ++count[s.charAt(right) - 'A']);
        if ((right - left + 1) - maxCount > k) {
            count[s.charAt(left++) - 'A']--;
        }
        best = Math.max(best, right - left + 1);
    }
    return best;
}
```

- **Time Complexity:** O(n)
- **Space Complexity:** O(1)

### Comparison

| # | Approach | Time | Space |
|---|----------|------|-------|
| 1 | Brute Force | O(n^2) | O(1) |
| 2 | Sliding Window, Recomputing the Maximum | O(26 * n) | O(1) |
| 3 | Sliding Window with a Non-Shrinking Window (Optimized) | O(n) | O(1) |

### Optimized Solution

The optimized solution is Approach 3 above, which is also the version implemented in this repository.

## Source Code

### LongestRepeatingCharacterReplacement.java

```java
public class LongestRepeatingCharacterReplacement {

    public static void main(String[] args) {
        String[] inputs = {"ABAB", "AABABBA", "AAAA"};
        int[] ks = {2, 1, 2};
        for (int i = 0; i < inputs.length; i++) {
            System.out.println("Input  : s = \"" + inputs[i] + "\", k = " + ks[i]);
            System.out.println("Output : " + characterReplacement(inputs[i], ks[i]));
            System.out.println();
        }
    }

    public static int characterReplacement(String s, int k) {
        if (s == null || s.isEmpty()) {
            return 0;
        }

        int[] count = new int[26];
        int left = 0;
        int maxCount = 0;
        int best = 0;
        for (int right = 0; right < s.length(); right++) {
            maxCount = Math.max(maxCount, ++count[s.charAt(right) - 'A']);

            // More than k replacements needed: shrink the window from the left
            if ((right - left + 1) - maxCount > k) {
                count[s.charAt(left) - 'A']--;
                left++;
            }
            best = Math.max(best, right - left + 1);
        }
        return best;
    }
}
```
