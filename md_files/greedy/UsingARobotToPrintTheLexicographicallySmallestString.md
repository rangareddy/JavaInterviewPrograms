# Using a Robot to Print the Lexicographically Smallest String

Given a string `s` and an empty robot holding an empty string `t`, you may either move the first character of `s` to the end of `t`, or remove the last character of `t` and write it on paper. Return the lexicographically smallest string that can be written on paper.

## Algorithm Strategy (Stack with the Smallest Remaining Letter)

1. Count the remaining letters of `s` so you can always know the smallest letter still to come.
2. Push each character of `s` onto a stack (the robot's `t`), updating the counts.
3. While the stack top is less than or equal to the smallest letter still remaining in `s`, pop it onto the paper: nothing better can appear later.
4. At the end pop everything left.

## Example walkthrough

- For `bdda`: push b, d, d; the remaining `a` is smaller than all of them, so push `a` and write it first; then write d, d, b.
- Result: `addb`. For `zza` the answer is `azz`.

## Complexity Analysis

- **Time Complexity:** `O(n)` — Each character is pushed and popped once.
- **Space Complexity:** `O(n)` — The stack.

## Approaches

The same problem can be solved in several ways. The approach marked (Optimized) is the one to use in practice.

### Approach 1: Brute Force over All Operation Orders

Try every interleaving of push and pop operations and keep the smallest result. Exponential.

```java
public static String brute(String s) { return go(s, 0, new StringBuilder(), new StringBuilder()); }
private static String go(String s, int i, StringBuilder t, StringBuilder p) {
    if (i == s.length() && t.length() == 0) return p.toString(); String best = null;
    if (i < s.length()) { t.append(s.charAt(i)); String r = go(s, i + 1, t, p); t.setLength(t.length() - 1); if (best == null || r.compareTo(best) < 0) best = r; }
    if (t.length() > 0) { char c = t.charAt(t.length() - 1); t.setLength(t.length() - 1); p.append(c); String r = go(s, i, t, p); p.setLength(p.length() - 1); t.append(c); if (best == null || r.compareTo(best) < 0) best = r; }
    return best;
}
```

- **Time Complexity:** O(2^(2n))
- **Space Complexity:** O(n)

### Approach 2: Stack with the Smallest Remaining Letter (Optimized)

Write the top of the stack whenever nothing smaller can still arrive. This is the version implemented in the repository.

```java
public static String greedy(String s) {
    int[] c = new int[26]; for (char ch : s.toCharArray()) c[ch - 'a']++; java.util.Deque<Character> st = new java.util.ArrayDeque<>(); StringBuilder p = new StringBuilder(); int m = 0;
    for (char ch : s.toCharArray()) { st.push(ch); c[ch - 'a']--; while (m < 25 && c[m] == 0) m++; while (!st.isEmpty() && st.peek() - 'a' <= m) p.append(st.pop()); }
    return p.toString();
}
```

- **Time Complexity:** O(n)
- **Space Complexity:** O(n)

### Comparison

| # | Approach | Time | Space |
|---|----------|------|-------|
| 1 | Brute Force over All Operation Orders | O(2^(2n)) | O(n) |
| 2 | Stack with the Smallest Remaining Letter (Optimized) | O(n) | O(n) |

## Source Code

### UsingARobotToPrintTheLexicographicallySmallestString.java

```java
import java.util.ArrayDeque;
import java.util.Deque;

public class UsingARobotToPrintTheLexicographicallySmallestString {

    public static void main(String[] args) {
        String[] inputs = {"zza", "bac", "bdda", "a"};
        for (String s : inputs) {
            System.out.println("Input  : s = \"" + s + "\"");
            System.out.println("Output : " + robotWithString(s));
            System.out.println();
        }
    }

    public static String robotWithString(String s) {
        if (s == null || s.isEmpty()) {
            return "";
        }
        int[] count = new int[26];
        for (char c : s.toCharArray()) {
            count[c - 'a']++;
        }
        Deque<Character> stack = new ArrayDeque<>();
        StringBuilder paper = new StringBuilder();
        int smallest = 0;
        for (char c : s.toCharArray()) {
            stack.push(c);
            count[c - 'a']--;
            while (smallest < 25 && count[smallest] == 0) {
                smallest++;
            }
            while (!stack.isEmpty() && stack.peek() - 'a' <= smallest) {
                paper.append(stack.pop());
            }
        }
        return paper.toString();
    }
}
```

## Solution

The optimized solution is Approach 2 above, which is also the version implemented in this repository.

The idea: a letter on the stack should be written as soon as no smaller letter can still arrive from `s`, because waiting only lets bigger letters or the same ones pile above it. Tracking the smallest remaining letter tells exactly when that is.

**Input:** `s = "zza"`.

| Char read | Stack | Smallest remaining | Written |
|-----------|-------|--------------------|---------|
| z | [z] | a | |
| z | [z, z] | a | |
| a | [z, z, a] | z (nothing left but the stack) | a, then z, z |

**Output:** `azz`.
