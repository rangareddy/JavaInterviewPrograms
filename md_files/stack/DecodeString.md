# Decode String

Given an encoded string such as `3[a2[c]]`, return its decoded form. The rule is `k[encoded]`, where `encoded` is repeated exactly `k` times. The input is always valid, `k` is a positive integer, and brackets can be nested.

## Algorithm Strategy (Two Stacks (Counts and Partial Strings))

1. Scan the characters left to right, keeping the string being built (`current`) and the number being read (`k`).
2. A digit extends `k` (a count can have several digits, such as `12`).
3. On `[`, push the pending `current` and `k` onto two stacks, then start a fresh `current` and reset `k`.
4. On `]`, pop the repeat count and the previous partial string, and set `current = previous + current repeated count times`.
5. Letters are appended to `current`. When the scan ends, `current` is the decoded string.

## Example walkthrough

- For `3[a2[c]]`: after `3[a2[c` the stacks hold the counts `3, 2` and the strings `"", "a"`, and `current = "c"`.
- The first `]` gives `"a" + "cc" = "acc"`, the second gives `"" + "acc" * 3 = "accaccacc"`.

## Complexity Analysis

- **Time Complexity:** `O(n * maxK)` — More precisely proportional to the length of the decoded output, which can be much larger than the input.
- **Space Complexity:** `O(n)` — The stacks hold at most one entry per open bracket, plus the output.

## Approaches

The same problem can be solved in several ways. The approach marked (Optimized) is the one to use in practice.

### Approach 1: Recursive Descent

Treat each `[` as a recursive call that decodes until its matching `]` and returns the string, using a shared index.

```java
public static String decodeStringRecursive(String s) {
    return decode(s, new int[]{0});
}

private static String decode(String s, int[] pos) {
    StringBuilder sb = new StringBuilder();
    int k = 0;
    while (pos[0] < s.length()) {
        char c = s.charAt(pos[0]++);
        if (Character.isDigit(c)) {
            k = k * 10 + (c - '0');
        } else if (c == '[') {
            String inner = decode(s, pos);
            for (int i = 0; i < k; i++) sb.append(inner);
            k = 0;
        } else if (c == ']') {
            return sb.toString();
        } else {
            sb.append(c);
        }
    }
    return sb.toString();
}
```

- **Time Complexity:** O(n * maxK)
- **Space Complexity:** O(n) recursion depth plus the output

### Approach 2: Two Stacks (Optimized)

Keep one stack for repeat counts and one for the partial strings of the enclosing brackets, so nesting needs no recursion. This is the version implemented in the repository.

```java
public static String decodeString(String s) {
    Deque<Integer> counts = new ArrayDeque<>();
    Deque<StringBuilder> previous = new ArrayDeque<>();
    StringBuilder current = new StringBuilder();
    int k = 0;
    for (char c : s.toCharArray()) {
        if (Character.isDigit(c)) {
            k = k * 10 + (c - '0');
        } else if (c == '[') {
            counts.push(k);
            previous.push(current);
            current = new StringBuilder();
            k = 0;
        } else if (c == ']') {
            StringBuilder decoded = previous.pop();
            int repeat = counts.pop();
            for (int i = 0; i < repeat; i++) {
                decoded.append(current);
            }
            current = decoded;
        } else {
            current.append(c);
        }
    }
    return current.toString();
}
```

- **Time Complexity:** O(n * maxK)
- **Space Complexity:** O(n)

### Comparison

| # | Approach | Time | Space |
|---|----------|------|-------|
| 1 | Recursive Descent | O(n * maxK) | O(n) recursion depth plus the output |
| 2 | Two Stacks (Optimized) | O(n * maxK) | O(n) |

### Optimized Solution

The optimized solution is Approach 2 above, which is also the version implemented in this repository.

#### Optimized solution explained

The idea: an encoded string nests as `k[...]`, and the inner part must be fully decoded before it is repeated into the outer part. The code reads left to right keeping the string being built (`current`) and the number being read (`k`). On `[` it saves the pair (`k`, `current`) on two stacks and starts a fresh `current`. On `]` it takes the saved outer text back, appends the finished inner text `repeat` times to it, and makes that the new `current`. One pass, and every character is appended once per repetition level, so the work is proportional to the length of the output.

**Input:** `s = "3[a2[c]]"`. Stacks are written bottom to top.

| Step | Char | What happens | k | current | counts | previous |
|------|------|--------------|---|---------|--------|----------|
| 1 | `3` | digit: k = 0 * 10 + 3 | 3 | `""` | | |
| 2 | `[` | push k and current, reset | 0 | `""` | 3 | `""` |
| 3 | `a` | letter: append | 0 | `a` | 3 | `""` |
| 4 | `2` | digit | 2 | `a` | 3 | `""` |
| 5 | `[` | push k and current, reset | 0 | `""` | 3 2 | `""` `a` |
| 6 | `c` | letter: append | 0 | `c` | 3 2 | `""` `a` |
| 7 | `]` | pop `a` and 2; `a` + `c` twice | 0 | `acc` | 3 | `""` |
| 8 | `]` | pop `""` and 3; `""` + `acc` three times | 0 | `accaccacc` | | |

```text
3 [ a 2 [ c ] ]
      |   +-- c x 2 -> "acc"   (joined to the saved "a")
      +------ "acc" x 3 -> "accaccacc"
```

**Edge cases.** Digits are accumulated (`k = k * 10 + digit`), so `10[z]` repeats ten times, not once or zero times. A string without brackets such as `abc` never touches the stacks and is returned as is. Letters following a `]` (as in `2[abc]3[cd]ef`) are simply appended to the restored `current`. The code assumes well-formed input; an unmatched `]` would pop an empty stack and throw.

**Result:** `"accaccacc"`, matching the class output (`3[a]2[bc]` gives `"aaabcbc"`).

## Source Code

### DecodeString.java

```java
import java.util.ArrayDeque;
import java.util.Deque;

public class DecodeString {

    public static void main(String[] args) {
        String[] inputs = {"3[a]2[bc]", "3[a2[c]]", "2[abc]3[cd]ef", "abc", "10[z]"};
        for (String s : inputs) {
            System.out.println("Input  : s = \"" + s + "\"");
            System.out.println("Output : \"" + decodeString(s) + "\"");
            System.out.println();
        }
    }

    public static String decodeString(String s) {
        Deque<Integer> counts = new ArrayDeque<>();
        Deque<StringBuilder> previous = new ArrayDeque<>();
        StringBuilder current = new StringBuilder();
        int k = 0;
        for (char c : s.toCharArray()) {
            if (Character.isDigit(c)) {
                k = k * 10 + (c - '0');
            } else if (c == '[') {
                counts.push(k);
                previous.push(current);
                current = new StringBuilder();
                k = 0;
            } else if (c == ']') {
                StringBuilder decoded = previous.pop();
                int repeat = counts.pop();
                for (int i = 0; i < repeat; i++) {
                    decoded.append(current);
                }
                current = decoded;
            } else {
                current.append(c);
            }
        }
        return current.toString();
    }
}
```
