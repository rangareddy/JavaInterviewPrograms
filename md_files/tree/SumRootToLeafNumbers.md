# Sum Root to Leaf Numbers

You are given the root of a binary tree containing digits from 0 to 9 only. Each root-to-leaf path represents a number (for example, `1 -> 2 -> 3` is `123`). Return the total sum of all root-to-leaf numbers.

## Algorithm Strategy (DFS Carrying the Number Built So Far)

1. Pass `current` down the recursion: at each node compute `current * 10 + node.val`.
2. At a leaf, return that number.
3. At an inner node, return the sum of the results of its children.
4. The answer is the result for the root.

## Example walkthrough

- For `[1, 2, 3]` the paths give 12 and 13.
- Result: `12 + 13 = 25`.

## Complexity Analysis

- **Time Complexity:** `O(n)` — Each node once.
- **Space Complexity:** `O(h)` — The recursion depth.

## Approaches

The same problem can be solved in several ways. The approach marked (Optimized) is the one to use in practice.

### Approach 1: Collect Path Strings then Sum

Build every root-to-leaf string and parse it.

```java
public static int strings(TreeNode root) { java.util.List<String> p = new java.util.ArrayList<>(); go(root, "", p); int s = 0; for (String x : p) s += Integer.parseInt(x); return s; }
private static void go(TreeNode n, String pre, java.util.List<String> out) { if (n == null) return; String p = pre + n.val; if (n.left == null && n.right == null) { out.add(p); return; } go(n.left, p, out); go(n.right, p, out); }
```

- **Time Complexity:** O(n * h)
- **Space Complexity:** O(n * h)

### Approach 2: DFS with the Running Number (Optimized)

Carry the value built so far and add it at the leaves. This is the version implemented in the repository.

```java
public static int running(TreeNode n) { return go(n, 0); }
private static int go(TreeNode n, int cur) { if (n == null) return 0; cur = cur * 10 + n.val; if (n.left == null && n.right == null) return cur; return go(n.left, cur) + go(n.right, cur); }
```

- **Time Complexity:** O(n)
- **Space Complexity:** O(h)

### Comparison

| # | Approach | Time | Space |
|---|----------|------|-------|
| 1 | Collect Path Strings then Sum | O(n * h) | O(n * h) |
| 2 | DFS with the Running Number (Optimized) | O(n) | O(h) |

## Source Code

### SumRootToLeafNumbers.java

```java
public class SumRootToLeafNumbers {

    public static void main(String[] args) {
        Integer[][] inputs = {{1, 2, 3}, {4, 9, 0, 5, 1}, {0}, {}};
        for (Integer[] values : inputs) {
            TreeNode root = TreeNode.of(values);
            System.out.println("Input  : root = " + TreeNode.toString(root));
            System.out.println("Output : " + sumNumbers(root));
            System.out.println();
        }
    }

    public static int sumNumbers(TreeNode root) {
        return sum(root, 0);
    }

    private static int sum(TreeNode node, int current) {
        if (node == null) {
            return 0;
        }
        current = current * 10 + node.val;
        if (node.left == null && node.right == null) {
            return current;
        }
        return sum(node.left, current) + sum(node.right, current);
    }
}
```

## Solution

The optimized solution is Approach 2 above, which is also the version implemented in this repository.

The idea: appending a digit to a decimal number is `number * 10 + digit`, so the number for a path can be built while walking down, with no strings. A leaf contributes the finished number and an inner node contributes the sum of its subtrees.

**Input:** `root = [1, 2, 3]`.

| Node | `current` after | Contribution |
|------|-----------------|--------------|
| 1 | 1 | sum of children |
| 2 (leaf) | 12 | 12 |
| 3 (leaf) | 13 | 13 |

**Output:** `12 + 13 = 25`.
