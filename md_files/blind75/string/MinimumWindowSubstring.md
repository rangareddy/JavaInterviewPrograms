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
