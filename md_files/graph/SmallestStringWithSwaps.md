# Smallest String With Swaps

You are given a string `s` and an array of index pairs `pairs`, where `pairs[i] = [a, b]` means the characters at indexes `a` and `b` can be swapped. You may swap any pair any number of times. Return the lexicographically smallest string that `s` can be changed to.

## Algorithm Strategy (Group Positions, Sort Within Each Group)

1. Positions linked by swap pairs, directly or through a chain, can be arranged in any order, so they form connected components.
2. Build the components with Union-Find.
3. For each component, collect its characters in a min-heap.
4. Walk the string from left to right and, at each index, place the smallest remaining character of that index's component.

## Example walkthrough

- For `s = dcab` with pairs `[0, 3]` and `[1, 2]`: components are `{0, 3}` with `d, b` and `{1, 2}` with `c, a`.
- Sorted, they place `b` at index 0, `a` at index 1, `c` at index 2, `d` at index 3.
- Result: `bacd`.

## Complexity Analysis

- **Time Complexity:** `O((n + p) log n)` — Union-Find over `p` pairs plus heap operations for `n` characters.
- **Space Complexity:** `O(n)` — Parent array and heaps.

## Approaches

The same problem can be solved in several ways. The approach marked (Optimized) is the one to use in practice.

### Approach 1: Search Every Reachable String

Apply every swap to every string reached so far and keep the smallest. Exact, but the set of strings grows factorially.

```java
public static String search(String s, int[][] pairs) { java.util.Set<String> seen = new java.util.HashSet<>(); java.util.Deque<String> q = new java.util.ArrayDeque<>(); q.add(s); seen.add(s); String best = s; while (!q.isEmpty()) { String cur = q.poll(); if (cur.compareTo(best) < 0) best = cur; for (int[] p : pairs) { char[] a = cur.toCharArray(); char t = a[p[0]]; a[p[0]] = a[p[1]]; a[p[1]] = t; String nx = new String(a); if (seen.add(nx)) q.add(nx); } } return best; }
```

- **Time Complexity:** O(n! * p)
- **Space Complexity:** O(n!)

### Approach 2: Union-Find and Sorted Components (Optimized)

Sort the characters of each connected component and place them in index order. This is the version implemented in the repository.

```java
public static String union(String s, int[][] pairs) { int n = s.length(); int[] p = new int[n]; for (int i = 0; i < n; i++) p[i] = i; for (int[] e : pairs) p[f(p, e[0])] = f(p, e[1]); java.util.List<Character>[] g = new java.util.List[n]; for (int i = 0; i < n; i++) { int rt = f(p, i); if (g[rt] == null) g[rt] = new java.util.ArrayList<>(); g[rt].add(s.charAt(i)); } for (java.util.List<Character> l : g) if (l != null) java.util.Collections.sort(l); int[] used = new int[n]; StringBuilder sb = new StringBuilder(); for (int i = 0; i < n; i++) { int rt = f(p, i); sb.append(g[rt].get(used[rt]++)); } return sb.toString(); }
static int f(int[] p, int x) { while (p[x] != x) x = p[x]; return x; }
```

- **Time Complexity:** O((n + p) log n)
- **Space Complexity:** O(n)

### Comparison

| # | Approach | Time | Space |
|---|----------|------|-------|
| 1 | Search Every Reachable String | O(n! * p) | O(n!) |
| 2 | Union-Find and Sorted Components (Optimized) | O((n + p) log n) | O(n) |

## Source Code

### SmallestStringWithSwaps.java

```java
import java.util.Arrays;
import java.util.PriorityQueue;

public class SmallestStringWithSwaps {

    public static void main(String[] args) {
        String[] strings = {"dcab", "dcab", "cba"};
        int[][][] pairs = {{{0, 3}, {1, 2}}, {{0, 3}, {1, 2}, {0, 2}}, {{0, 1}, {1, 2}}};
        for (int i = 0; i < strings.length; i++) {
            System.out.println("Input  : s = \"" + strings[i] + "\", pairs = " + Arrays.deepToString(pairs[i]));
            System.out.println("Output : " + smallestStringWithSwaps(strings[i], pairs[i]));
            System.out.println();
        }
    }

    public static String smallestStringWithSwaps(String s, int[][] pairs) {
        int n = s.length();
        int[] parent = new int[n];
        for (int i = 0; i < n; i++) {
            parent[i] = i;
        }
        for (int[] pair : pairs) {
            int a = find(parent, pair[0]);
            int b = find(parent, pair[1]);
            if (a != b) {
                parent[a] = b;
            }
        }
        @SuppressWarnings("unchecked")
        PriorityQueue<Character>[] heaps = new PriorityQueue[n];
        for (int i = 0; i < n; i++) {
            int root = find(parent, i);
            if (heaps[root] == null) {
                heaps[root] = new PriorityQueue<>();
            }
            heaps[root].add(s.charAt(i));
        }
        StringBuilder result = new StringBuilder(n);
        for (int i = 0; i < n; i++) {
            result.append(heaps[find(parent, i)].poll());
        }
        return result.toString();
    }

    private static int find(int[] parent, int x) {
        while (parent[x] != x) {
            parent[x] = parent[parent[x]];
            x = parent[x];
        }
        return x;
    }
}
```

## Solution

The optimized solution is Approach 2 above, which is also the version implemented in this repository.

The idea: swaps are transitive. If `a` can swap with `b` and `b` with `c`, the characters at those three positions can be put in any order. So each connected component of positions can be sorted independently, and the smallest string puts the smallest characters at the smallest indexes within each component.

**Input:** `s = "dcab"`, `pairs = [[0, 3], [1, 2]]`.

| Component | Positions | Characters | Sorted |
|-----------|-----------|------------|--------|
| A | 0, 3 | d, b | b, d |
| B | 1, 2 | c, a | a, c |

Fill index by index: 0 takes `b`, 1 takes `a`, 2 takes `c`, 3 takes `d`.

**Output:** `bacd`.
