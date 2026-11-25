# Largest Number

Given a list of non-negative integers `nums`, arrange them so that they form the largest possible number, and return it as a string (the result can be very large).

## Algorithm Strategy (Sort by Concatenation Order)

1. Convert every number to a string.
2. Sort with the comparator `(a, b) -> (b + a).compareTo(a + b)`: put `a` before `b` when `a + b` is larger than `b + a`.
3. If the largest element after sorting is `"0"`, the whole answer is `"0"`.
4. Otherwise concatenate the sorted strings.

## Example walkthrough

- For `[3, 30, 34, 5, 9]`: `9` beats everything, `5` beats `34`, `34` beats `3`, and `3` beats `30` because `330 > 303`.
- Result: `9534330`.

## Complexity Analysis

- **Time Complexity:** `O(n log n * L)` — Sorting with comparisons that cost O(L) each.
- **Space Complexity:** `O(n * L)` — The strings.

## Approaches

The same problem can be solved in several ways. The approach marked (Optimized) is the one to use in practice.

### Approach 1: Try Every Order

Build every permutation and keep the largest string. Factorial time, only for tiny inputs.

```java
public static String permutations(int[] a) {
    String[] s = new String[a.length]; for (int i = 0; i < a.length; i++) s[i] = "" + a[i];
    String best = ""; best = go(s, new boolean[s.length], "", best);
    return best.startsWith("0") ? "0" : best;
}
private static String go(String[] s, boolean[] used, String cur, String best) {
    boolean any = false;
    for (int i = 0; i < s.length; i++) if (!used[i]) { any = true; used[i] = true; best = go(s, used, cur + s[i], best); used[i] = false; }
    if (!any && (cur.length() > best.length() || (cur.length() == best.length() && cur.compareTo(best) > 0))) best = cur;
    return best;
}
```

- **Time Complexity:** O(n! * L)
- **Space Complexity:** O(n * L)

### Approach 2: Sort with a Concatenation Comparator (Optimized)

Order two numbers by which concatenation is larger. This is the version implemented in the repository.

```java
public static String comparator(int[] a) {
    String[] s = new String[a.length]; for (int i = 0; i < a.length; i++) s[i] = "" + a[i];
    java.util.Arrays.sort(s, (x, y) -> (y + x).compareTo(x + y));
    if (s[0].equals("0")) return "0";
    StringBuilder sb = new StringBuilder(); for (String p : s) sb.append(p); return sb.toString();
}
```

- **Time Complexity:** O(n log n * L)
- **Space Complexity:** O(n * L)

### Comparison

| # | Approach | Time | Space |
|---|----------|------|-------|
| 1 | Try Every Order | O(n! * L) | O(n * L) |
| 2 | Sort with a Concatenation Comparator (Optimized) | O(n log n * L) | O(n * L) |

## Source Code

### LargestNumber.java

```java
import java.util.Arrays;

public class LargestNumber {

    public static void main(String[] args) {
        int[][] inputs = {{3, 30, 34, 5, 9}, {10, 2}, {0, 0}, {1}, {824, 8247}};
        for (int[] nums : inputs) {
            System.out.println("Input  : nums = " + Arrays.toString(nums));
            System.out.println("Output : " + largestNumber(nums));
            System.out.println();
        }
    }

    public static String largestNumber(int[] nums) {
        if (nums == null || nums.length == 0) {
            return "";
        }
        String[] parts = new String[nums.length];
        for (int i = 0; i < nums.length; i++) {
            parts[i] = String.valueOf(nums[i]);
        }
        Arrays.sort(parts, (a, b) -> (b + a).compareTo(a + b));
        if (parts[0].equals("0")) {
            return "0";
        }
        StringBuilder sb = new StringBuilder();
        for (String p : parts) {
            sb.append(p);
        }
        return sb.toString();
    }
}
```

## Solution

The optimized solution is Approach 2 above, which is also the version implemented in this repository.

The idea: comparing the numbers themselves does not work (`30` is larger than `3` but must come after it). Comparing the two possible concatenations does: `a` goes before `b` when `a + b` is greater than `b + a`.

**Input:** `nums = [3, 30, 34, 5, 9]`.

| Pair | `a + b` | `b + a` | Order |
|------|---------|---------|-------|
| 3, 30 | 330 | 303 | 3 first |
| 34, 3 | 343 | 334 | 34 first |
| 5, 34 | 534 | 345 | 5 first |
| 9, 5 | 95 | 59 | 9 first |

Sorted: `9, 5, 34, 3, 30`.

**Output:** `9534330`.
