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

### Approach 3: One Sliding Window per Target Letter

Fix the letter that the substring should consist of and slide a window that contains at most `k` other letters; repeat for all 26 letters. No "highest frequency" bookkeeping is needed.

```java
public static int characterReplacementPerLetter(String s, int k) {
    int best = 0;
    for (char target = 'A'; target <= 'Z'; target++) {
        int left = 0, others = 0;
        for (int right = 0; right < s.length(); right++) {
            if (s.charAt(right) != target) others++;
            while (others > k) {
                if (s.charAt(left) != target) others--;
                left++;
            }
            best = Math.max(best, right - left + 1);
        }
    }
    return best;
}
```

- **Time Complexity:** O(26 * n)
- **Space Complexity:** O(1)

### Approach 4: Binary Search on the Answer Length

If a window of length `L` can be made uniform with at most `k` replacements, so can every shorter window, so the answer can be found by binary search on `L`. Checking a length is one pass of a fixed-size window.

```java
public static int characterReplacementBinary(String s, int k) {
    int low = 0, high = s.length();
    while (low < high) {
        int mid = (low + high + 1) / 2;
        if (feasible(s, k, mid)) low = mid; else high = mid - 1;
    }
    return low;
}

private static boolean feasible(String s, int k, int length) {
    int[] count = new int[26];
    for (int i = 0; i < s.length(); i++) {
        count[s.charAt(i) - 'A']++;
        if (i >= length) count[s.charAt(i - length) - 'A']--;
        if (i >= length - 1) {
            int max = 0;
            for (int c : count) max = Math.max(max, c);
            if (length - max <= k) return true;
        }
    }
    return length == 0;
}
```

- **Time Complexity:** O(26 * n log n)
- **Space Complexity:** O(1)

### Approach 5: Sliding Window with a HashMap (Any Alphabet)

The non-shrinking window with a `HashMap` of counts instead of a 26-element array, so it works for any character set (not only `A`-`Z`).

```java
public static int characterReplacementMap(String s, int k) {
    Map<Character, Integer> count = new HashMap<>();
    int left = 0, maxCount = 0, best = 0;
    for (int right = 0; right < s.length(); right++) {
        maxCount = Math.max(maxCount, count.merge(s.charAt(right), 1, Integer::sum));
        if ((right - left + 1) - maxCount > k) count.merge(s.charAt(left++), -1, Integer::sum);
        best = Math.max(best, right - left + 1);
    }
    return best;
}
```

- **Time Complexity:** O(n)
- **Space Complexity:** O(alphabet size)

### Approach 6: Prefix Counts and Binary Search on the Start

Precompute prefix counts of each letter. For every end index the number of replacements needed shrinks as the start moves right, so the smallest valid start can be found by binary search, reading the highest letter count from the prefix tables.

```java
public static int characterReplacementPrefix(String s, int k) {
    int n = s.length();
    int[][] prefix = new int[26][n + 1];
    for (int i = 0; i < n; i++) {
        for (int c = 0; c < 26; c++) prefix[c][i + 1] = prefix[c][i];
        prefix[s.charAt(i) - 'A'][i + 1]++;
    }
    int best = 0;
    for (int end = 0; end < n; end++) {
        int low = 0, high = end;                          // smallest start with replacements <= k
        while (low < high) {
            int mid = (low + high) / 2;
            int max = 0;
            for (int c = 0; c < 26; c++) max = Math.max(max, prefix[c][end + 1] - prefix[c][mid]);
            if ((end - mid + 1) - max <= k) high = mid; else low = mid + 1;
        }
        best = Math.max(best, end - low + 1);
    }
    return best;
}
```

- **Time Complexity:** O(26 * n log n)
- **Space Complexity:** O(26 * n)

### Approach 7: Sliding Window with a Non-Shrinking Window (Optimized)

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
| 3 | One Sliding Window per Target Letter | O(26 * n) | O(1) |
| 4 | Binary Search on the Answer Length | O(26 * n log n) | O(1) |
| 5 | Sliding Window with a HashMap (Any Alphabet) | O(n) | O(alphabet size) |
| 6 | Prefix Counts and Binary Search on the Start | O(26 * n log n) | O(26 * n) |
| 7 | Sliding Window with a Non-Shrinking Window (Optimized) | O(n) | O(1) |

### Optimized Solution

The optimized solution is Approach 7 above, which is also the version implemented in this repository.

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
