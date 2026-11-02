# Word Break

Given a string `s` and a dictionary of words, return `true` if `s` can be split into a space-separated sequence of one or more dictionary words (a word may be reused).

## Algorithm Strategy (Dynamic Programming over Prefixes)

1. Let `dp[i]` be `true` when the first `i` characters of `s` can be segmented. `dp[0] = true` (the empty prefix).
2. For every end position `i` from 1 to `n`, try every split point `j < i`: if `dp[j]` is true and the substring `s[j..i)` is in the dictionary, then `dp[i]` is true.
3. Store the dictionary in a `HashSet` so each check is `O(L)` (hashing the substring).
4. The answer is `dp[n]`.

## Example walkthrough

- For `"leetcode"` with `["leet", "code"]`: `dp[4]` is true ("leet") and `dp[8]` is true because `dp[4]` is true and "code" is a word.
- Result: `true`.

## Complexity Analysis

- **Time Complexity:** `O(n^2 * L)` — Pairs of positions times the cost of building and hashing a substring of length up to `L`.
- **Space Complexity:** `O(n)` — The `dp` array (plus the dictionary).

## Source Code

### WordBreak.java

```java
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class WordBreak {

    public static void main(String[] args) {
        String[] strings = {"leetcode", "applepenapple", "catsandog"};
        List<List<String>> dictionaries = Arrays.asList(
                Arrays.asList("leet", "code"),
                Arrays.asList("apple", "pen"),
                Arrays.asList("cats", "dog", "sand", "and", "cat"));
        for (int i = 0; i < strings.length; i++) {
            System.out.println("Input  : s = \"" + strings[i] + "\", wordDict = " + dictionaries.get(i));
            System.out.println("Output : " + wordBreak(strings[i], dictionaries.get(i)));
            System.out.println();
        }
    }

    public static boolean wordBreak(String s, List<String> wordDict) {
        Set<String> words = new HashSet<>(wordDict);
        boolean[] dp = new boolean[s.length() + 1];
        dp[0] = true;
        for (int i = 1; i <= s.length(); i++) {
            for (int j = 0; j < i; j++) {
                if (dp[j] && words.contains(s.substring(j, i))) {
                    dp[i] = true;
                    break;
                }
            }
        }
        return dp[s.length()];
    }
}
```
