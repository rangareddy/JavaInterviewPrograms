# Remove All Adjacent Duplicates in String II

Given a string `s` and an integer `k`, a k duplicate removal chooses `k` adjacent and equal letters and removes them, concatenating the left and right parts. Repeat the removal until it is no longer possible and return the final string.

## Algorithm Strategy (Stack of Character and Count)

1. Keep a stack where each entry is a character and the length of the current run of that character at the top.
2. For each character, if it equals the top character, increase the run count; otherwise push a new entry with count 1.
3. When a run count reaches `k`, pop the entry (the whole run disappears).
4. Rebuild the answer from the entries left in the stack, repeating each character as many times as its count.

## Example walkthrough

- For `deeedbbcccbdaa` and `k = 3`: `eee` is removed, then `ccc`, then `bbb` (formed after the earlier removals), then `ddd`... leaving `aa`.
- Result: `aa`.

## Complexity Analysis

- **Time Complexity:** `O(n)` — Each character is pushed or counted once.
- **Space Complexity:** `O(n)` — The stack.

## Approaches

The same problem can be solved in several ways. The approach marked (Optimized) is the one to use in practice.

### Approach 1: Repeated Scan and Remove

Scan for a run of `k` equal characters, delete it and start again. Quadratic.

```java
public static String repeat(String s, int k) {
    StringBuilder sb = new StringBuilder(s); boolean changed = true;
    while (changed) { changed = false; int run = 1; for (int i = 1; i <= sb.length(); i++) { if (i < sb.length() && sb.charAt(i) == sb.charAt(i - 1)) run++; else { if (run >= k) { sb.delete(i - run, i - run + k); changed = true; break; } run = 1; } } }
    return sb.toString();
}
```

- **Time Complexity:** O(n^2 / k)
- **Space Complexity:** O(n)

### Approach 2: Stack of Character and Count (Optimized)

Track the current run length at the top of a stack. This is the version implemented in the repository.

```java
public static String stack(String s, int k) {
    java.util.Deque<int[]> st = new java.util.ArrayDeque<>();
    for (char c : s.toCharArray()) { if (!st.isEmpty() && st.peek()[0] == c) { if (++st.peek()[1] == k) st.pop(); } else st.push(new int[]{c, 1}); }
    StringBuilder sb = new StringBuilder(); for (int[] e : st) for (int i = 0; i < e[1]; i++) sb.append((char) e[0]); return sb.reverse().toString();
}
```

- **Time Complexity:** O(n)
- **Space Complexity:** O(n)

### Comparison

| # | Approach | Time | Space |
|---|----------|------|-------|
| 1 | Repeated Scan and Remove | O(n^2 / k) | O(n) |
| 2 | Stack of Character and Count (Optimized) | O(n) | O(n) |

## Source Code

### RemoveAllAdjacentDuplicatesInStringII.java

```java
import java.util.ArrayDeque;
import java.util.Deque;

public class RemoveAllAdjacentDuplicatesInStringII {

    public static void main(String[] args) {
        String[] inputs = {"abcd", "deeedbbcccbdaa", "pbbcggttciiippooaais"};
        int[] ks = {2, 3, 2};
        for (int i = 0; i < inputs.length; i++) {
            System.out.println("Input  : s = \"" + inputs[i] + "\", k = " + ks[i]);
            System.out.println("Output : " + removeDuplicates(inputs[i], ks[i]));
            System.out.println();
        }
    }

    public static String removeDuplicates(String s, int k) {
        if (s == null || k <= 1) {
            return k == 1 ? "" : (s == null ? "" : s);
        }
        Deque<int[]> stack = new ArrayDeque<>();
        for (char c : s.toCharArray()) {
            if (!stack.isEmpty() && stack.peek()[0] == c) {
                if (++stack.peek()[1] == k) {
                    stack.pop();
                }
            } else {
                stack.push(new int[]{c, 1});
            }
        }
        StringBuilder result = new StringBuilder();
        for (int[] entry : stack) {
            for (int i = 0; i < entry[1]; i++) {
                result.append((char) entry[0]);
            }
        }
        return result.reverse().toString();
    }
}
```

## Solution

The optimized solution is Approach 2 above, which is also the version implemented in this repository.

The idea: a removal can bring two runs of the same letter together, so the structure has to remember the current run length. A stack of `(character, count)` pairs does this: a run that reaches `k` is popped, and the entry below it is automatically the new neighbour.

**Input:** `s = "deeedbbcccbdaa"`, `k = 3`.

| Character | Stack after (character x count) |
|-----------|----------------------------------|
| d | d1 |
| e, e, e | d1 e1, d1 e2, d1 (e3 popped) |
| d | d2 |
| b, b | d2 b2 |
| c, c, c | d2 b2 (c3 popped) |
| b | d2 b3 -> popped, d2 |
| d | d3 -> popped, empty |
| a, a | a2 |

**Output for this input:** `aa`. For the first sample in `main`, `"abcd"` with `k = 2`, the output is `abcd`.
