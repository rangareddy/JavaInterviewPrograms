# Minimum Window Substring

Given strings `s` and `t`, return the shortest substring of `s` that contains every character of `t` (including duplicates). Return an empty string if there is none.

## Algorithm Strategy (Sliding Window with a Missing Counter)

1. Count the characters required by `t` in `need[128]` and keep `missing = t.length()`, the number of required characters still absent from the window.
2. Expand `right`: decrement `need[c]` for the new character; if it was still needed (`need[c] > 0` before the decrement), decrement `missing`.
3. When `missing == 0` the window is valid. Shrink from the left while it stays valid: the character leaving the window is "free" when `need[c] < 0` after the decrement, otherwise removing it makes the window invalid.
4. Record the smallest valid window seen, then continue expanding. Return that substring (or `""`).

## Example walkthrough

- For `s = "ADOBECODEBANC"` and `t = "ABC"`: the first valid window is `"ADOBEC"`; later `"CODEBA"` and finally `"BANC"` (length 4).
- Result: `"BANC"`.

## Complexity Analysis

- **Time Complexity:** `O(|s| + |t|)` — Both pointers move forward only.
- **Space Complexity:** `O(1)` — A fixed array of 128 counters.

## Approaches

The same problem can be solved in several ways. The approach marked (Optimized) is the one to use in practice.

### Approach 1: Brute Force

For every start index extend the end until the substring contains all required letters; keep the shortest.

```java
public static String minWindowBrute(String s, String t) {
    if (t.isEmpty()) return "";
    int[] need = new int[128];
    for (char c : t.toCharArray()) need[c]++;
    String best = "";
    for (int i = 0; i < s.length(); i++) {
        int[] have = new int[128];
        int satisfied = 0, distinct = 0;
        for (int c : need) if (c > 0) distinct++;
        for (int j = i; j < s.length(); j++) {
            char c = s.charAt(j);
            if (++have[c] == need[c] && need[c] > 0) satisfied++;
            if (satisfied == distinct) {
                if (best.isEmpty() || j - i + 1 < best.length()) best = s.substring(i, j + 1);
                break;
            }
        }
    }
    return best;
}
```

- **Time Complexity:** O(n^2)
- **Space Complexity:** O(1)

### Approach 2: Sliding Window with a HashMap

Count the required letters in a map and the letters in the window in another map, and track how many distinct letters currently have enough occurrences (`formed`). Shrink from the left while the window stays valid.

```java
public static String minWindowMaps(String s, String t) {
    if (t.isEmpty() || s.length() < t.length()) return "";
    Map<Character, Integer> need = new HashMap<>();
    for (char c : t.toCharArray()) need.merge(c, 1, Integer::sum);

    Map<Character, Integer> window = new HashMap<>();
    int formed = 0, left = 0, bestStart = 0, bestLength = Integer.MAX_VALUE;
    for (int right = 0; right < s.length(); right++) {
        char c = s.charAt(right);
        window.merge(c, 1, Integer::sum);
        if (need.containsKey(c) && window.get(c).intValue() == need.get(c).intValue()) formed++;

        while (formed == need.size()) {
            if (right - left + 1 < bestLength) { bestLength = right - left + 1; bestStart = left; }
            char out = s.charAt(left++);
            window.merge(out, -1, Integer::sum);
            if (need.containsKey(out) && window.get(out) < need.get(out)) formed--;
        }
    }
    return bestLength == Integer.MAX_VALUE ? "" : s.substring(bestStart, bestStart + bestLength);
}
```

- **Time Complexity:** O(|s| + |t|)
- **Space Complexity:** O(|s| + |t|)

### Approach 3: Sliding Window with a Missing Counter (Optimized)

One array of counters (decremented when a letter enters the window) and a single counter of missing characters replace the two maps. This is the version implemented in the repository.

```java
public static String minWindow(String s, String t) {
    if (t.isEmpty() || s.length() < t.length()) return "";
    int[] need = new int[128];
    for (char c : t.toCharArray()) need[c]++;
    int missing = t.length(), left = 0, bestStart = 0, bestLength = Integer.MAX_VALUE;
    for (int right = 0; right < s.length(); right++) {
        if (need[s.charAt(right)]-- > 0) missing--;
        while (missing == 0) {
            if (right - left + 1 < bestLength) { bestLength = right - left + 1; bestStart = left; }
            if (++need[s.charAt(left++)] > 0) missing++;
        }
    }
    return bestLength == Integer.MAX_VALUE ? "" : s.substring(bestStart, bestStart + bestLength);
}
```

- **Time Complexity:** O(|s| + |t|)
- **Space Complexity:** O(1)

### Comparison

| # | Approach | Time | Space |
|---|----------|------|-------|
| 1 | Brute Force | O(n^2) | O(1) |
| 2 | Sliding Window with a HashMap | O(|s| + |t|) | O(|s| + |t|) |
| 3 | Sliding Window with a Missing Counter (Optimized) | O(|s| + |t|) | O(1) |

### Optimized Solution

The optimized solution is Approach 3 above, which is also the version implemented in this repository.

## Source Code

### MinimumWindowSubstring.java

```java
public class MinimumWindowSubstring {

    public static void main(String[] args) {
        String[][] inputs = {{"ADOBECODEBANC", "ABC"}, {"a", "a"}, {"a", "aa"}};
        for (String[] input : inputs) {
            System.out.println("Input  : s = \"" + input[0] + "\", t = \"" + input[1] + "\"");
            System.out.println("Output : \"" + minWindow(input[0], input[1]) + "\"");
            System.out.println();
        }
    }

    public static String minWindow(String s, String t) {
        if (s == null || t == null || s.length() < t.length() || t.isEmpty()) {
            return "";
        }

        int[] need = new int[128];
        for (char c : t.toCharArray()) {
            need[c]++;
        }

        int missing = t.length();
        int left = 0;
        int bestStart = 0;
        int bestLength = Integer.MAX_VALUE;
        for (int right = 0; right < s.length(); right++) {
            if (need[s.charAt(right)]-- > 0) {
                missing--;
            }

            while (missing == 0) {
                if (right - left + 1 < bestLength) {
                    bestLength = right - left + 1;
                    bestStart = left;
                }
                if (++need[s.charAt(left)] > 0) {
                    missing++;      // removing this character makes the window invalid
                }
                left++;
            }
        }
        return bestLength == Integer.MAX_VALUE ? "" : s.substring(bestStart, bestStart + bestLength);
    }
}
```
