# Palindrome Partitioning

Given a string `s`, partition it so that every substring of the partition is a palindrome, and return all possible palindrome partitionings of `s`.

## Algorithm Strategy (Backtracking over Palindromic Prefixes)

1. At a start position, try every prefix `s[start..end]` of the remaining string.
2. Only a prefix that is a palindrome may be cut off: add it to the current partition and recurse on the rest of the string starting at `end + 1`.
3. When the start position reaches the end of the string, the current list of pieces is a complete partitioning: record a copy.
4. Remove the piece again after the recursive call (backtrack) and try the next longer prefix. A precomputed table `isPal[i][j]` makes each palindrome test `O(1)`.

## Example walkthrough

- For `"aab"`: cut `"a"`, then `"a"`, then `"b"` `-> [a, a, b]`; cut `"aa"` then `"b"` `-> [aa, b]`.
- `"aab"` itself is not a palindrome, so there is no single-piece partition.

## Complexity Analysis

- **Time Complexity:** `O(n * 2^n)` — Up to `2^(n-1)` partitionings, each copied in `O(n)`.
- **Space Complexity:** `O(n^2)` — The palindrome table (plus recursion depth `O(n)`).

## Approaches

The same problem can be solved in several ways. The approach marked (Optimized) is the one to use in practice.

### Approach 1: Backtracking with an On-the-Fly Palindrome Test

Try every prefix of the remaining string and test with two pointers whether it is a palindrome. Simple, but the same substrings are tested again and again.

```java
public static List<List<String>> partitionCheck(String s) {
    List<List<String>> result = new ArrayList<>();
    cut(s, 0, new ArrayList<>(), result);
    return result;
}

private static void cut(String s, int start, List<String> current, List<List<String>> result) {
    if (start == s.length()) { result.add(new ArrayList<>(current)); return; }
    for (int end = start; end < s.length(); end++) {
        if (isPalindrome(s, start, end)) {
            current.add(s.substring(start, end + 1));
            cut(s, end + 1, current, result);
            current.remove(current.size() - 1);
        }
    }
}

private static boolean isPalindrome(String s, int i, int j) {
    while (i < j) if (s.charAt(i++) != s.charAt(j--)) return false;
    return true;
}
```

- **Time Complexity:** O(n * 2^n)
- **Space Complexity:** O(n)

### Approach 2: Memoized Partitions of Each Suffix

Compute the list of all palindrome partitionings of every suffix once and reuse it: the partitionings of `s[start..]` are each palindromic prefix followed by every partitioning of the rest. Trades memory for repeated work.

```java
public static List<List<String>> partitionMemo(String s) {
    return solve(s, 0, new HashMap<>());
}

private static List<List<String>> solve(String s, int start, Map<Integer, List<List<String>>> memo) {
    if (start == s.length()) { List<List<String>> base = new ArrayList<>(); base.add(new ArrayList<>()); return base; }
    if (memo.containsKey(start)) return memo.get(start);
    List<List<String>> result = new ArrayList<>();
    for (int end = start; end < s.length(); end++) {
        String prefix = s.substring(start, end + 1);
        if (new StringBuilder(prefix).reverse().toString().equals(prefix)) {
            for (List<String> rest : solve(s, end + 1, memo)) {
                List<String> partition = new ArrayList<>();
                partition.add(prefix);
                partition.addAll(rest);
                result.add(partition);
            }
        }
    }
    memo.put(start, result);
    return result;
}
```

- **Time Complexity:** O(n * 2^n)
- **Space Complexity:** O(n * 2^n)

### Approach 3: Bottom-Up DP over Prefixes

Let `partitions[i]` hold all palindrome partitionings of the first `i` characters. For every end `i`, every palindromic piece `s[j..i)` extends each partitioning of the first `j` characters.

```java
public static List<List<String>> partitionBottomUp(String s) {
    int n = s.length();
    List<List<List<String>>> partitions = new ArrayList<>();
    for (int i = 0; i <= n; i++) partitions.add(new ArrayList<>());
    partitions.get(0).add(new ArrayList<>());
    for (int end = 1; end <= n; end++) {
        for (int start = 0; start < end; start++) {
            String piece = s.substring(start, end);
            if (!new StringBuilder(piece).reverse().toString().equals(piece)) continue;
            for (List<String> before : partitions.get(start)) {
                List<String> partition = new ArrayList<>(before);
                partition.add(piece);
                partitions.get(end).add(partition);
            }
        }
    }
    return partitions.get(n);
}
```

- **Time Complexity:** O(n * 2^n)
- **Space Complexity:** O(n * 2^n)

### Approach 4: Backtracking with a Palindrome Table (Optimized)

Precompute `isPal[i][j]` with dynamic programming so every palindrome test during the backtracking is `O(1)`. This is the version implemented in the repository.

```java
public static List<List<String>> partition(String s) {
    int n = s.length();
    boolean[][] isPal = new boolean[n][n];
    for (int end = 0; end < n; end++)
        for (int start = 0; start <= end; start++)
            isPal[start][end] = s.charAt(start) == s.charAt(end) && (end - start < 2 || isPal[start + 1][end - 1]);
    List<List<String>> result = new ArrayList<>();
    backtrack(s, 0, isPal, new ArrayList<>(), result);
    return result;
}

private static void backtrack(String s, int start, boolean[][] isPal, List<String> current, List<List<String>> result) {
    if (start == s.length()) { result.add(new ArrayList<>(current)); return; }
    for (int end = start; end < s.length(); end++) {
        if (isPal[start][end]) {
            current.add(s.substring(start, end + 1));
            backtrack(s, end + 1, isPal, current, result);
            current.remove(current.size() - 1);
        }
    }
}
```

- **Time Complexity:** O(n * 2^n)
- **Space Complexity:** O(n^2)

### Comparison

| # | Approach | Time | Space |
|---|----------|------|-------|
| 1 | Backtracking with an On-the-Fly Palindrome Test | O(n * 2^n) | O(n) |
| 2 | Memoized Partitions of Each Suffix | O(n * 2^n) | O(n * 2^n) |
| 3 | Bottom-Up DP over Prefixes | O(n * 2^n) | O(n * 2^n) |
| 4 | Backtracking with a Palindrome Table (Optimized) | O(n * 2^n) | O(n^2) |

### Optimized Solution

The optimized solution is Approach 4 above, which is also the version implemented in this repository.

## Source Code

### PalindromePartitioning.java

```java
import java.util.ArrayList;
import java.util.List;

public class PalindromePartitioning {

    public static void main(String[] args) {
        String[] inputs = {"aab", "a", "aaa", "abc", "racecar"};
        for (String s : inputs) {
            System.out.println("Input  : s = \"" + s + "\"");
            System.out.println("Output : " + partition(s));
            System.out.println();
        }
    }

    public static List<List<String>> partition(String s) {
        int n = s.length();
        boolean[][] isPal = new boolean[n][n];
        for (int end = 0; end < n; end++) {                 // isPal[start][end] from shorter palindromes
            for (int start = 0; start <= end; start++) {
                isPal[start][end] = s.charAt(start) == s.charAt(end) && (end - start < 2 || isPal[start + 1][end - 1]);
            }
        }

        List<List<String>> result = new ArrayList<>();
        backtrack(s, 0, isPal, new ArrayList<>(), result);
        return result;
    }

    private static void backtrack(String s, int start, boolean[][] isPal, List<String> current, List<List<String>> result) {
        if (start == s.length()) {
            result.add(new ArrayList<>(current));
            return;
        }
        for (int end = start; end < s.length(); end++) {
            if (isPal[start][end]) {
                current.add(s.substring(start, end + 1));
                backtrack(s, end + 1, isPal, current, result);
                current.remove(current.size() - 1);
            }
        }
    }
}
```
