# Reverse Vowels of a String

Given a string `s`, reverse only the vowels (`a`, `e`, `i`, `o`, `u`, in either case) and return the resulting string. All other characters stay where they are.

## Algorithm Strategy (Two Pointers Swapping Vowels)

1. Put the characters in an array and set `left = 0`, `right = n - 1`.
2. Advance `left` until it points at a vowel and move `right` back until it points at a vowel.
3. Swap the two vowels and move both pointers inward.
4. Stop when the pointers meet and build the string.

## Example walkthrough

- For `hello`: `left` stops at `e`, `right` at `o`; swapping gives `holle`.
- Result: `holle`.

## Complexity Analysis

- **Time Complexity:** `O(n)` — Each index is visited once.
- **Space Complexity:** `O(n)` — The character array.

## Approaches

The same problem can be solved in several ways. The approach marked (Optimized) is the one to use in practice.

### Approach 1: Collect Vowels then Refill

Collect the vowels in a stack and write them back in reverse order.

```java
public static String stackRefill(String s) {
    java.util.Deque<Character> st = new java.util.ArrayDeque<>();
    for (char c : s.toCharArray()) if ("aeiouAEIOU".indexOf(c) >= 0) st.push(c);
    StringBuilder sb = new StringBuilder();
    for (char c : s.toCharArray()) sb.append("aeiouAEIOU".indexOf(c) >= 0 ? st.pop() : c);
    return sb.toString();
}
```

- **Time Complexity:** O(n)
- **Space Complexity:** O(n)

### Approach 2: Two Pointers (Optimized)

Swap vowels from both ends. This is the version implemented in the repository.

```java
public static String twoPointers(String s) {
    char[] a = s.toCharArray(); int l = 0, r = a.length - 1;
    while (l < r) {
        while (l < r && "aeiouAEIOU".indexOf(a[l]) < 0) l++;
        while (l < r && "aeiouAEIOU".indexOf(a[r]) < 0) r--;
        char t = a[l]; a[l++] = a[r]; a[r--] = t;
    }
    return new String(a);
}
```

- **Time Complexity:** O(n)
- **Space Complexity:** O(n)

### Comparison

| # | Approach | Time | Space |
|---|----------|------|-------|
| 1 | Collect Vowels then Refill | O(n) | O(n) |
| 2 | Two Pointers (Optimized) | O(n) | O(n) |

## Source Code

### ReverseVowelsOfAString.java

```java
public class ReverseVowelsOfAString {

    public static void main(String[] args) {
        String[] inputs = {"hello", "leetcode", "AEIOU", "xyz", ""};
        for (String s : inputs) {
            System.out.println("Input  : s = \"" + s + "\"");
            System.out.println("Output : " + reverseVowels(s));
            System.out.println();
        }
    }

    public static String reverseVowels(String s) {
        if (s == null || s.length() < 2) {
            return s;
        }
        char[] chars = s.toCharArray();
        int left = 0;
        int right = chars.length - 1;
        while (left < right) {
            while (left < right && !isVowel(chars[left])) {
                left++;
            }
            while (left < right && !isVowel(chars[right])) {
                right--;
            }
            char tmp = chars[left];
            chars[left++] = chars[right];
            chars[right--] = tmp;
        }
        return new String(chars);
    }

    private static boolean isVowel(char c) {
        return "aeiouAEIOU".indexOf(c) >= 0;
    }
}
```

## Solution

The optimized solution is Approach 2 above, which is also the version implemented in this repository.

The idea: the first vowel swaps with the last vowel, the second with the second to last, and so on, which two pointers walking inward do naturally.

**Input:** `s = "leetcode"`.

| `left` | `right` | Action | String |
|--------|---------|--------|--------|
| 1 (e) | 7 (e) | swap, no visible change | leetcode |
| 2 (e) | 5 (o) | `right` skipped d and moved to 5, swap | leotcede |
| 3 | 4 | no vowels left, pointers meet | leotcede |

**Output:** `leotcede`.
