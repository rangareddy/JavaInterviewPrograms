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

### Approach 3: Window over the Filtered Positions

Only the characters that occur in `t` matter. Build the list of their positions and slide the window over this (usually much shorter) list; the window length in the original string is the difference of the first and last position. Fast when `t` uses rare characters.

```java
public static String minWindowFiltered(String s, String t) {
    if (t.isEmpty()) return "";
    int[] need = new int[128];
    for (char c : t.toCharArray()) need[c]++;
    List<Integer> positions = new ArrayList<>();
    for (int i = 0; i < s.length(); i++) if (need[s.charAt(i)] > 0) positions.add(i);

    int[] window = new int[128];
    int formed = 0, required = 0, left = 0, bestStart = 0, bestLength = Integer.MAX_VALUE;
    for (int c : need) if (c > 0) required++;
    for (int right = 0; right < positions.size(); right++) {
        char c = s.charAt(positions.get(right));
        if (++window[c] == need[c]) formed++;
        while (formed == required) {
            int length = positions.get(right) - positions.get(left) + 1;
            if (length < bestLength) { bestLength = length; bestStart = positions.get(left); }
            char out = s.charAt(positions.get(left++));
            if (window[out]-- == need[out]) formed--;
        }
    }
    return bestLength == Integer.MAX_VALUE ? "" : s.substring(bestStart, bestStart + bestLength);
}
```

- **Time Complexity:** O(|s| + |t|)
- **Space Complexity:** O(|s|)

### Approach 4: Binary Search on the Window Length

If a window of length `L` contains `t`, every longer length has such a window too, so the minimum length can be found by binary search. Checking one length is a single pass with a fixed-size window.

```java
public static String minWindowBinary(String s, String t) {
    if (t.isEmpty() || s.length() < t.length()) return "";
    int low = t.length(), high = s.length();
    if (find(s, t, high) < 0) return "";
    while (low < high) {
        int mid = (low + high) / 2;
        if (find(s, t, mid) >= 0) high = mid; else low = mid + 1;
    }
    int start = find(s, t, low);
    return s.substring(start, start + low);
}

// start index of the first window of the given length that contains t, or -1
private static int find(String s, String t, int length) {
    int[] need = new int[128];
    for (char c : t.toCharArray()) need[c]++;
    int missing = t.length();
    for (int i = 0; i < s.length(); i++) {
        if (need[s.charAt(i)]-- > 0) missing--;
        if (i >= length && ++need[s.charAt(i - length)] > 0) missing++;
        if (i >= length - 1 && missing == 0) return i - length + 1;
    }
    return -1;
}
```

- **Time Complexity:** O((|s| + |t|) log |s|)
- **Space Complexity:** O(1)

### Approach 5: Sliding Window with a Counter of Satisfied Letters

Count, for every distinct letter of `t`, whether the window already contains enough of it, and keep one counter `formed` of the letters that are satisfied. The window is valid when `formed` equals the number of distinct letters.

```java
public static String minWindowFormed(String s, String t) {
    if (t.isEmpty()) return "";
    int[] need = new int[128], window = new int[128];
    int required = 0;
    for (char c : t.toCharArray()) if (need[c]++ == 0) required++;
    int formed = 0, left = 0, bestStart = 0, bestLength = Integer.MAX_VALUE;
    for (int right = 0; right < s.length(); right++) {
        char c = s.charAt(right);
        if (++window[c] == need[c] && need[c] > 0) formed++;
        while (formed == required) {
            if (right - left + 1 < bestLength) { bestLength = right - left + 1; bestStart = left; }
            char out = s.charAt(left++);
            if (window[out]-- == need[out] && need[out] > 0) formed--;
        }
    }
    return bestLength == Integer.MAX_VALUE ? "" : s.substring(bestStart, bestStart + bestLength);
}
```

- **Time Complexity:** O(|s| + |t|)
- **Space Complexity:** O(1)

### Approach 6: Prefix Counts and Binary Search for the End

Precompute prefix counts for the letters of `t`. For each start index, the smallest end that contains `t` is found by binary search because containing `t` is monotone in the end index.

```java
public static String minWindowPrefix(String s, String t) {
    if (t.isEmpty()) return "";
    int n = s.length();
    int[] need = new int[128];
    for (char c : t.toCharArray()) need[c]++;
    int[][] prefix = new int[128][];
    for (int c = 0; c < 128; c++) if (need[c] > 0) prefix[c] = new int[n + 1];
    for (int i = 0; i < n; i++) {
        for (int c = 0; c < 128; c++) if (prefix[c] != null) prefix[c][i + 1] = prefix[c][i];
        if (prefix[s.charAt(i)] != null) prefix[s.charAt(i)][i + 1]++;
    }
    int bestStart = 0, bestLength = Integer.MAX_VALUE;
    for (int start = 0; start < n; start++) {
        int low = start, high = n - 1;                   // smallest end that contains t
        if (!contains(prefix, need, start, high)) break;
        while (low < high) {
            int mid = (low + high) / 2;
            if (contains(prefix, need, start, mid)) high = mid; else low = mid + 1;
        }
        if (low - start + 1 < bestLength) { bestLength = low - start + 1; bestStart = start; }
    }
    return bestLength == Integer.MAX_VALUE ? "" : s.substring(bestStart, bestStart + bestLength);
}

private static boolean contains(int[][] prefix, int[] need, int start, int end) {
    for (int c = 0; c < 128; c++) {
        if (need[c] > 0 && prefix[c][end + 1] - prefix[c][start] < need[c]) return false;
    }
    return true;
}
```

- **Time Complexity:** O(|s| log |s| * |alphabet|)
- **Space Complexity:** O(|s| * |t|)

### Approach 7: Sliding Window with a Missing Counter (Optimized)

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
| 3 | Window over the Filtered Positions | O(|s| + |t|) | O(|s|) |
| 4 | Binary Search on the Window Length | O((|s| + |t|) log |s|) | O(1) |
| 5 | Sliding Window with a Counter of Satisfied Letters | O(|s| + |t|) | O(1) |
| 6 | Prefix Counts and Binary Search for the End | O(|s| log |s| * |alphabet|) | O(|s| * |t|) |
| 7 | Sliding Window with a Missing Counter (Optimized) | O(|s| + |t|) | O(1) |

### Optimized Solution

The optimized solution is Approach 7 above, which is also the version implemented in this repository.

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
