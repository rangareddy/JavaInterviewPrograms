# Find All Anagrams in a String

Given two strings `s` and `p`, return the start indices of all the substrings of `s` that are anagrams of `p`, in any order.

## Algorithm Strategy (Fixed-Size Sliding Window with Character Counts)

1. An anagram of `p` is a substring of `s` of the same length with the same character counts, so slide a window of length `p.length()` across `s`.
2. Keep `need[c]` (the counts of `p`) and `window[c]` (the counts inside the current window) as arrays of 26.
3. When the window slides one step, add the new right character and remove the character that fell off the left edge. Both are constant-time updates.
4. Whenever the two arrays are equal the window start is an answer. Comparing two arrays of 26 is a constant cost, so the total is linear in the length of `s`.

## Example walkthrough

- For `s = "cbaebabacd"`, `p = "abc"`: window `cba` at 0 matches; `bac` at 6 matches.
- Result: `[0, 6]`.

## Complexity Analysis

- **Time Complexity:** `O(n)` — One pass over `s`; each array comparison is over 26 letters.
- **Space Complexity:** `O(1)` — Two arrays of 26 counters.

## Approaches

The same problem can be solved in several ways. The approach marked (Optimized) is the one to use in practice.

### Approach 1: Sort Every Substring

Sort `p` once, then sort each substring of the same length and compare.

```java
public static List<Integer> findAnagramsSort(String s, String p) {
    char[] target = p.toCharArray();
    Arrays.sort(target);
    List<Integer> result = new ArrayList<>();
    for (int i = 0; i + p.length() <= s.length(); i++) {
        char[] part = s.substring(i, i + p.length()).toCharArray();
        Arrays.sort(part);
        if (Arrays.equals(target, part)) result.add(i);
    }
    return result;
}
```

- **Time Complexity:** O(n * m log m)
- **Space Complexity:** O(m)

### Approach 2: Sliding Window with Counts (Optimized)

Slide a window of length `p.length()` and update 26 counters instead of re-sorting. This is the version implemented in the repository.

```java
public static List<Integer> findAnagrams(String s, String p) {
    List<Integer> result = new ArrayList<>();
    int n = s.length();
    int m = p.length();
    if (m > n) {
        return result;
    }
    int[] need = new int[26];
    int[] window = new int[26];
    for (int i = 0; i < m; i++) {
        need[p.charAt(i) - 'a']++;
        window[s.charAt(i) - 'a']++;
    }
    if (Arrays.equals(need, window)) {
        result.add(0);
    }
    for (int right = m; right < n; right++) {
        window[s.charAt(right) - 'a']++;
        window[s.charAt(right - m) - 'a']--;
        if (Arrays.equals(need, window)) {
            result.add(right - m + 1);
        }
    }
    return result;
}
```

- **Time Complexity:** O(n)
- **Space Complexity:** O(1)

### Comparison

| # | Approach | Time | Space |
|---|----------|------|-------|
| 1 | Sort Every Substring | O(n * m log m) | O(m) |
| 2 | Sliding Window with Counts (Optimized) | O(n) | O(1) |

### Optimized Solution

The optimized solution is Approach 2 above, which is also the version implemented in this repository.

#### Optimized solution explained

The idea: an anagram of `p` is a window of `s` of length `m = p.length()` with identical letter counts. Keep a 26-slot count array for `p` (`need`) and one for the current window. Slide the window one step: add the new right letter, remove the letter that left, and compare the two arrays (26 numbers, a constant). Each equal window records its start index.

**Input:** `s = "cbaebabacd"`, `p = "abc"` (so `m = 3`, `need` = a:1, b:1, c:1).

| Step | Window start | Window | Letter in | Letter out | Counts equal `need`? | Result list |
|------|--------------|--------|-----------|------------|----------------------|-------------|
| 0 | 0 | `cba` | (first 3 letters) | - | yes | [0] |
| 1 | 1 | `bae` | `e` | `c` | no | [0] |
| 2 | 2 | `aeb` | `b` | `b` | no | [0] |
| 3 | 3 | `eba` | `a` | `a` | no | [0] |
| 4 | 4 | `bab` | `b` | `e` | no | [0] |
| 5 | 5 | `aba` | `a` | `b` | no | [0] |
| 6 | 6 | `bac` | `c` | `a` | yes | [0, 6] |
| 7 | 7 | `acd` | `d` | `b` | no | [0, 6] |

```text
index: 0 1 2 3 4 5 6 7 8 9
s:     c b a e b a b a c d
       [c b a]                 start 0  anagram
                   [b a c]     start 6  anagram
```

**Edge cases.** If `p` is longer than `s` the list is empty (`{"a","ab"}`). Overlapping matches are all reported (`"abab"`, `"ab"` gives `[0, 1, 2]`, `"aaaaa"`, `"aa"` gives `[0, 1, 2, 3]`). Lowercase letters only.

**Result:** `[0, 6]`, matching the class output.

## Source Code

### FindAllAnagramsInAString.java

```java
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class FindAllAnagramsInAString {

    public static void main(String[] args) {
        String[][] inputs = {{"cbaebabacd", "abc"}, {"abab", "ab"}, {"a", "ab"}, {"aaaaa", "aa"}};
        for (String[] in : inputs) {
            System.out.println("Input  : s = \"" + in[0] + "\", p = \"" + in[1] + "\"");
            System.out.println("Output : " + findAnagrams(in[0], in[1]));
            System.out.println();
        }
    }

    public static List<Integer> findAnagrams(String s, String p) {
        List<Integer> result = new ArrayList<>();
        int n = s.length();
        int m = p.length();
        if (m > n) {
            return result;
        }
        int[] need = new int[26];
        int[] window = new int[26];
        for (int i = 0; i < m; i++) {
            need[p.charAt(i) - 'a']++;
            window[s.charAt(i) - 'a']++;
        }
        if (Arrays.equals(need, window)) {
            result.add(0);
        }
        for (int right = m; right < n; right++) {
            window[s.charAt(right) - 'a']++;
            window[s.charAt(right - m) - 'a']--;
            if (Arrays.equals(need, window)) {
                result.add(right - m + 1);
            }
        }
        return result;
    }
}
```
