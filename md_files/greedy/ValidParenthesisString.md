# Valid Parenthesis String

Given a string `s` containing only `(`, `)` and `*`, return `true` if it is valid. A `*` may be treated as `(`, as `)` or as an empty string. A string is valid when every `(` has a matching `)` after it and every `)` has a matching `(` before it.

## Algorithm Strategy (Greedy Range of Open Parentheses)

1. Track the range `[low, high]` of possible numbers of unmatched `(` after each character.
2. A `(` increases both bounds. A `)` decreases both. A `*` lowers `low` (as `)`) and raises `high` (as `(`).
3. If `high < 0`, there are too many `)` even when every `*` is an opening bracket: return `false`.
4. Keep `low` at least 0 (an unmatched `)` cannot be repaired later, but a `*` can be an empty string). At the end the string is valid when `low == 0`.

## Example walkthrough

- For `"(*))"`: ranges `[1,1]`, `[0,2]`, `[0,1]`, `[0,0]`; the final `low` is 0.
- Result: `true`.

## Complexity Analysis

- **Time Complexity:** `O(n)` — One pass.
- **Space Complexity:** `O(1)` — Two counters.

## Approaches

The same problem can be solved in several ways. The approach marked (Optimized) is the one to use in practice.

### Approach 1: Brute Force Recursion

Try each meaning of every `*` and keep a balance counter.

```java
public static boolean checkValidStringBrute(String s) {
    return go(s, 0, 0);
}

private static boolean go(String s, int i, int open) {
    if (open < 0) return false;
    if (i == s.length()) return open == 0;
    char c = s.charAt(i);
    if (c == '(') return go(s, i + 1, open + 1);
    if (c == ')') return go(s, i + 1, open - 1);
    return go(s, i + 1, open + 1) || go(s, i + 1, open - 1) || go(s, i + 1, open);
}
```

- **Time Complexity:** O(3^k)
- **Space Complexity:** O(n)

### Approach 2: Memoization

The state is only `(index, open count)`, so cache it.

```java
public static boolean checkValidStringMemo(String s) {
    return goMemo(s, 0, 0, new Boolean[s.length() + 1][s.length() + 2]);
}

private static boolean goMemo(String s, int i, int open, Boolean[][] memo) {
    if (open < 0) return false;
    if (i == s.length()) return open == 0;
    if (memo[i][open] != null) return memo[i][open];
    char c = s.charAt(i);
    boolean result;
    if (c == '(') result = goMemo(s, i + 1, open + 1, memo);
    else if (c == ')') result = goMemo(s, i + 1, open - 1, memo);
    else result = goMemo(s, i + 1, open + 1, memo) || goMemo(s, i + 1, open - 1, memo) || goMemo(s, i + 1, open, memo);
    return memo[i][open] = result;
}
```

- **Time Complexity:** O(n^2)
- **Space Complexity:** O(n^2)

### Approach 3: Two Stacks of Indices

Keep the indices of unmatched `(` and `*`; match each `)` with a `(` first, then a `*`, and finally every leftover `(` needs a later `*`.

```java
public static boolean checkValidStringStacks(String s) {
    Deque<Integer> open = new ArrayDeque<>(), star = new ArrayDeque<>();
    for (int i = 0; i < s.length(); i++) {
        char c = s.charAt(i);
        if (c == '(') open.push(i);
        else if (c == '*') star.push(i);
        else if (!open.isEmpty()) open.pop();
        else if (!star.isEmpty()) star.pop();
        else return false;
    }
    while (!open.isEmpty() && !star.isEmpty()) {
        if (open.pop() > star.pop()) return false;
    }
    return open.isEmpty();
}
```

- **Time Complexity:** O(n)
- **Space Complexity:** O(n)

### Approach 4: Greedy Range Counters (Optimized)

Track the lowest and highest possible number of open brackets. This is the version implemented in the repository.

```java
public static boolean checkValidString(String s) {
    int low = 0, high = 0;
    for (char c : s.toCharArray()) {
        if (c == '(') { low++; high++; }
        else if (c == ')') { low--; high--; }
        else { low--; high++; }
        if (high < 0) return false;
        low = Math.max(low, 0);
    }
    return low == 0;
}
```

- **Time Complexity:** O(n)
- **Space Complexity:** O(1)

### Comparison

| # | Approach | Time | Space |
|---|----------|------|-------|
| 1 | Brute Force Recursion | O(3^k) | O(n) |
| 2 | Memoization | O(n^2) | O(n^2) |
| 3 | Two Stacks of Indices | O(n) | O(n) |
| 4 | Greedy Range Counters (Optimized) | O(n) | O(1) |

### Optimized Solution

The optimized solution is Approach 4 above, which is also the version implemented in this repository.

## Source Code

### ValidParenthesisString.java

```java
public class ValidParenthesisString {

    public static void main(String[] args) {
        String[] inputs = {"()", "(*)", "(*))", "(((*)", ")(", "*", "(()*"};
        for (String s : inputs) {
            System.out.println("Input  : s = \"" + s + "\"");
            System.out.println("Output : " + checkValidString(s));
            System.out.println();
        }
    }

    public static boolean checkValidString(String s) {
        int low = 0;            // fewest possible unmatched '('
        int high = 0;           // most possible unmatched '('
        for (char c : s.toCharArray()) {
            if (c == '(') {
                low++;
                high++;
            } else if (c == ')') {
                low--;
                high--;
            } else {
                low--;
                high++;
            }
            if (high < 0) {
                return false;
            }
            low = Math.max(low, 0);
        }
        return low == 0;
    }
}
```
