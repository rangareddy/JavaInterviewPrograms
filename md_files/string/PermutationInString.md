# Permutation in String

Given two strings `s1` and `s2`, return `true` if `s2` contains a permutation of `s1`, i.e. one of the permutations of `s1` is a substring of `s2`.

## Algorithm Strategy (Fixed-Size Sliding Window with Letter Counts)

1. A permutation of `s1` has exactly the same letter counts as `s1` and the same length, so look for a window of length `s1.length()` in `s2` with identical counts.
2. Count the letters of `s1` in `need[26]` and the letters of the first window of `s2` in `window[26]`.
3. Slide the window one position at a time: add the new letter on the right and remove the letter that leaves on the left. Keep the number `matches` of letters whose counts are equal in both arrays so the comparison is `O(1)` per step.
4. Return `true` as soon as all 26 letters match.

## Example walkthrough

- For `s1 = "ab"` and `s2 = "eidbaooo"`: the window `"ba"` (positions 3 and 4) has the same counts as `"ab"`.
- Result: `true`. For `"eidboaoo"` no window has one `a` and one `b`: `false`.

## Complexity Analysis

- **Time Complexity:** `O(|s1| + |s2|)` — One pass with a constant amount of work per step.
- **Space Complexity:** `O(1)` — Two arrays of 26 counters.

## Approaches

The same problem can be solved in several ways. The approach marked (Optimized) is the one to use in practice.

### Approach 1: Brute Force (Sort Every Window)

Sort `s1` once, then sort every substring of `s2` with the same length and compare.

```java
public static boolean checkInclusionSort(String s1, String s2) {
    char[] target = s1.toCharArray();
    Arrays.sort(target);
    for (int i = 0; i + s1.length() <= s2.length(); i++) {
        char[] window = s2.substring(i, i + s1.length()).toCharArray();
        Arrays.sort(window);
        if (Arrays.equals(window, target)) return true;
    }
    return false;
}
```

- **Time Complexity:** O(|s2| * |s1| log |s1|)
- **Space Complexity:** O(|s1|)

### Approach 2: Sliding Window, Comparing the Count Arrays

Maintain the letter counts of the current window and compare the two 26-element arrays at every position.

```java
public static boolean checkInclusionCounts(String s1, String s2) {
    if (s1.length() > s2.length()) return false;
    int[] need = new int[26], window = new int[26];
    for (int i = 0; i < s1.length(); i++) { need[s1.charAt(i) - 'a']++; window[s2.charAt(i) - 'a']++; }
    if (Arrays.equals(need, window)) return true;
    for (int i = s1.length(); i < s2.length(); i++) {
        window[s2.charAt(i) - 'a']++;
        window[s2.charAt(i - s1.length()) - 'a']--;
        if (Arrays.equals(need, window)) return true;
    }
    return false;
}
```

- **Time Complexity:** O(26 * |s2|)
- **Space Complexity:** O(1)

### Approach 3: Sliding Window with a HashMap (Any Alphabet)

The same window technique with a `HashMap` of letter counts and a counter of the letters that are still missing, so the alphabet is not limited to `a`-`z`.

```java
public static boolean checkInclusionMap(String s1, String s2) {
    Map<Character, Integer> need = new HashMap<>();
    for (char c : s1.toCharArray()) need.merge(c, 1, Integer::sum);
    int missing = s1.length();
    for (int i = 0; i < s2.length(); i++) {
        char in = s2.charAt(i);
        if (need.containsKey(in)) {
            if (need.merge(in, -1, Integer::sum) >= 0) missing--;
        }
        if (i >= s1.length()) {
            char out = s2.charAt(i - s1.length());
            if (need.containsKey(out)) {
                if (need.merge(out, 1, Integer::sum) > 0) missing++;
            }
        }
        if (missing == 0) return true;
    }
    return false;
}
```

- **Time Complexity:** O(|s2|)
- **Space Complexity:** O(alphabet size)

### Approach 4: Sliding Window with a Matches Counter (Optimized)

Count the letters whose counts agree in the window and in `s1` (`matches`), updating it in `O(1)` when the window moves; the window is a permutation when all 26 letters match. This is the version implemented in the repository.

```java
public static boolean checkInclusion(String s1, String s2) {
    if (s1.length() > s2.length()) return false;
    int[] need = new int[26], window = new int[26];
    for (int i = 0; i < s1.length(); i++) { need[s1.charAt(i) - 'a']++; window[s2.charAt(i) - 'a']++; }
    int matches = 0;
    for (int c = 0; c < 26; c++) if (need[c] == window[c]) matches++;
    for (int right = s1.length(); right < s2.length(); right++) {
        if (matches == 26) return true;
        int in = s2.charAt(right) - 'a', out = s2.charAt(right - s1.length()) - 'a';
        window[in]++;
        if (window[in] == need[in]) matches++; else if (window[in] == need[in] + 1) matches--;
        window[out]--;
        if (window[out] == need[out]) matches++; else if (window[out] == need[out] - 1) matches--;
    }
    return matches == 26;
}
```

- **Time Complexity:** O(|s2|)
- **Space Complexity:** O(1)

### Comparison

| # | Approach | Time | Space |
|---|----------|------|-------|
| 1 | Brute Force (Sort Every Window) | O(|s2| * |s1| log |s1|) | O(|s1|) |
| 2 | Sliding Window, Comparing the Count Arrays | O(26 * |s2|) | O(1) |
| 3 | Sliding Window with a HashMap (Any Alphabet) | O(|s2|) | O(alphabet size) |
| 4 | Sliding Window with a Matches Counter (Optimized) | O(|s2|) | O(1) |

### Optimized Solution

The optimized solution is Approach 4 above, which is also the version implemented in this repository.

## Source Code

### PermutationInString.java

```java
public class PermutationInString {

    public static void main(String[] args) {
        String[][] inputs = {{"ab", "eidbaooo"}, {"ab", "eidboaoo"}, {"adc", "dcda"}, {"abc", "ab"}};
        for (String[] input : inputs) {
            System.out.println("Input  : s1 = \"" + input[0] + "\", s2 = \"" + input[1] + "\"");
            System.out.println("Output : " + checkInclusion(input[0], input[1]));
            System.out.println();
        }
    }

    public static boolean checkInclusion(String s1, String s2) {
        if (s1.length() > s2.length()) {
            return false;
        }

        int[] need = new int[26];
        int[] window = new int[26];
        for (int i = 0; i < s1.length(); i++) {
            need[s1.charAt(i) - 'a']++;
            window[s2.charAt(i) - 'a']++;
        }

        int matches = 0;
        for (int c = 0; c < 26; c++) {
            if (need[c] == window[c]) {
                matches++;
            }
        }

        for (int right = s1.length(); right < s2.length(); right++) {
            if (matches == 26) {
                return true;
            }
            int in = s2.charAt(right) - 'a';
            int out = s2.charAt(right - s1.length()) - 'a';

            window[in]++;
            if (window[in] == need[in]) {
                matches++;
            } else if (window[in] == need[in] + 1) {
                matches--;
            }

            window[out]--;
            if (window[out] == need[out]) {
                matches++;
            } else if (window[out] == need[out] - 1) {
                matches--;
            }
        }
        return matches == 26;
    }
}
```
