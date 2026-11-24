# Remove Duplicate Letters

Given a string `s`, remove duplicate letters so that every letter appears exactly once, and the result is the smallest in lexicographical order among all possible results.

## Algorithm Strategy (Monotonic Stack with Last Occurrence)

1. Record the last index of every letter.
2. Scan the string. Skip a letter that is already on the stack.
3. While the stack top is larger than the current letter and the top appears again later (its last index is greater than the current index), pop it.
4. Push the current letter and mark it as used. The stack is the answer.

## Example walkthrough

- For `bcabc`: b, c are pushed; a pops c and b (both appear later); then b and c are pushed again.
- Result: `abc`.

## Complexity Analysis

- **Time Complexity:** `O(n)` — Each letter is pushed and popped at most once.
- **Space Complexity:** `O(1)` — At most 26 letters on the stack.

## Approaches

The same problem can be solved in several ways. The approach marked (Optimized) is the one to use in practice.

### Approach 1: Brute Force over Subsequences

Generate every subsequence that has each distinct letter once and keep the smallest. Exponential.

```java
public static String bruteForce(String s) {
    java.util.Set<Character> set = new java.util.TreeSet<>(); for (char c : s.toCharArray()) set.add(c); int need = set.size(); String best = null;
    for (int mask = 0; mask < (1 << s.length()); mask++) {
        if (Integer.bitCount(mask) != need) continue; StringBuilder sb = new StringBuilder(); java.util.Set<Character> seen = new java.util.HashSet<>(); boolean ok = true;
        for (int i = 0; i < s.length(); i++) if ((mask >> i & 1) == 1) { if (!seen.add(s.charAt(i))) { ok = false; break; } sb.append(s.charAt(i)); }
        if (ok && (best == null || sb.toString().compareTo(best) < 0)) best = sb.toString();
    }
    return best == null ? "" : best;
}
```

- **Time Complexity:** O(2^n * n)
- **Space Complexity:** O(n)

### Approach 2: Greedy Smallest Letter First

Repeatedly choose the smallest letter whose suffix still contains every remaining letter.

```java
public static String greedy(String s) {
    if (s.isEmpty()) return ""; int[] cnt = new int[26]; for (char c : s.toCharArray()) cnt[c - 'a']++; int pos = 0;
    for (int i = 0; i < s.length(); i++) { if (s.charAt(i) < s.charAt(pos)) pos = i; if (--cnt[s.charAt(i) - 'a'] == 0) break; }
    return s.charAt(pos) + greedy(s.substring(pos + 1).replace("" + s.charAt(pos), ""));
}
```

- **Time Complexity:** O(26 * n)
- **Space Complexity:** O(n)

### Approach 3: Monotonic Stack (Optimized)

Pop larger letters that reappear later. This is the version implemented in the repository.

```java
public static String stack(String s) {
    int[] last = new int[26]; for (int i = 0; i < s.length(); i++) last[s.charAt(i) - 'a'] = i;
    boolean[] used = new boolean[26]; StringBuilder st = new StringBuilder();
    for (int i = 0; i < s.length(); i++) { char c = s.charAt(i); if (used[c - 'a']) continue;
        while (st.length() > 0 && st.charAt(st.length() - 1) > c && last[st.charAt(st.length() - 1) - 'a'] > i) { used[st.charAt(st.length() - 1) - 'a'] = false; st.deleteCharAt(st.length() - 1); }
        st.append(c); used[c - 'a'] = true; }
    return st.toString();
}
```

- **Time Complexity:** O(n)
- **Space Complexity:** O(1)

### Comparison

| # | Approach | Time | Space |
|---|----------|------|-------|
| 1 | Brute Force over Subsequences | O(2^n * n) | O(n) |
| 2 | Greedy Smallest Letter First | O(26 * n) | O(n) |
| 3 | Monotonic Stack (Optimized) | O(n) | O(1) |

## Source Code

### RemoveDuplicateLetters.java

```java
public class RemoveDuplicateLetters {

    public static void main(String[] args) {
        String[] inputs = {"bcabc", "cbacdcbc", "abacb", "a", ""};
        for (String s : inputs) {
            System.out.println("Input  : s = \"" + s + "\"");
            System.out.println("Output : \"" + removeDuplicateLetters(s) + "\"");
            System.out.println();
        }
    }

    public static String removeDuplicateLetters(String s) {
        if (s == null || s.isEmpty()) {
            return "";
        }
        int[] last = new int[26];
        for (int i = 0; i < s.length(); i++) {
            last[s.charAt(i) - 'a'] = i;
        }
        boolean[] used = new boolean[26];
        StringBuilder stack = new StringBuilder();
        for (int i = 0; i < s.length(); i++) {
            char c = s.charAt(i);
            if (used[c - 'a']) {
                continue;
            }
            while (stack.length() > 0 && stack.charAt(stack.length() - 1) > c && last[stack.charAt(stack.length() - 1) - 'a'] > i) {
                used[stack.charAt(stack.length() - 1) - 'a'] = false;
                stack.deleteCharAt(stack.length() - 1);
            }
            stack.append(c);
            used[c - 'a'] = true;
        }
        return stack.toString();
    }
}
```

## Solution

The optimized solution is Approach 3 above, which is also the version implemented in this repository.

The idea: a smaller letter should come earlier, so a larger letter on the stack is replaced by the incoming smaller one, but only if the larger letter will appear again later (otherwise it would be lost).

**Input:** `s = "cbacdcbc"`.

| Letter | Stack before | Pops | Stack after |
|--------|--------------|------|-------------|
| c | | | c |
| b | c | c (appears again) | b |
| a | b | b (appears again) | a |
| c | a | | ac |
| d | ac | | acd |
| c | acd | (already used) | acd |
| b | acd | d? d does not appear again, no pop | acdb |
| c | acdb | (already used) | acdb |

**Output for this input:** `acdb`. For the first sample in `main`, `bcabc`, the output is `abc`.
