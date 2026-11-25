# Binary Tree Vertical Order Traversal

Given the root of a binary tree, return the vertical order traversal of its nodes' values: from top to bottom, column by column from left to right. If two nodes are in the same row and column, order them from left to right.

## Algorithm Strategy (BFS with Column Offsets)

1. Do a level-order traversal (BFS) and keep, for each node, its column: the root is 0, a left child is `col - 1`, a right child is `col + 1`.
2. Append each node's value to the list of its column in a `TreeMap` (sorted by column).
3. BFS visits nodes top to bottom and left to right, so each column list comes out in the right order.
4. Return the lists of the map in key order.

## Example walkthrough

- For `[3, 9, 20, null, null, 15, 7]`: column -1 has 9, column 0 has 3 and 15, column 1 has 20, column 2 has 7.
- Result: `[[9], [3, 15], [20], [7]]`.

## Complexity Analysis

- **Time Complexity:** `O(n log n)` — `TreeMap` inserts; O(n) with a min/max column array.
- **Space Complexity:** `O(n)` — The map and queue.

## Approaches

The same problem can be solved in several ways. The approach marked (Optimized) is the one to use in practice.

### Approach 1: DFS then Sort by Column and Row

Record `(column, row, order, value)` for every node with DFS and sort the records.

```java
public static java.util.List<java.util.List<Integer>> dfs(TreeNode root) {
    java.util.List<java.util.List<Integer>> out = new java.util.ArrayList<>(); if (root == null) return out; java.util.List<int[]> rec = new java.util.ArrayList<>(); go(root, 0, 0, rec);
    rec.sort((a, b) -> a[0] != b[0] ? a[0] - b[0] : a[1] != b[1] ? a[1] - b[1] : a[2] - b[2]); int prev = Integer.MIN_VALUE; for (int[] x : rec) { if (x[0] != prev) { out.add(new java.util.ArrayList<>()); prev = x[0]; } out.get(out.size() - 1).add(x[3]); } return out;
}
private static int counter;
private static void go(TreeNode n, int col, int row, java.util.List<int[]> rec) { if (n == null) return; rec.add(new int[]{col, row, counter(), n.val}); go(n.left, col - 1, row + 1, rec); go(n.right, col + 1, row + 1, rec); }
private static int c; private static int counter() { return c++; }
```

- **Time Complexity:** O(n log n)
- **Space Complexity:** O(n)

### Approach 2: BFS with Column Offsets (Optimized)

Level-order traversal grouping values by column in a sorted map. This is the version implemented in the repository.

```java
public static java.util.List<java.util.List<Integer>> bfs(TreeNode root) {
    java.util.List<java.util.List<Integer>> out = new java.util.ArrayList<>(); if (root == null) return out; java.util.Map<Integer, java.util.List<Integer>> m = new java.util.TreeMap<>(); java.util.Queue<TreeNode> q = new java.util.ArrayDeque<>(); java.util.Queue<Integer> ci = new java.util.ArrayDeque<>(); q.add(root); ci.add(0);
    while (!q.isEmpty()) { TreeNode n = q.poll(); int c = ci.poll(); m.computeIfAbsent(c, k -> new java.util.ArrayList<>()).add(n.val); if (n.left != null) { q.add(n.left); ci.add(c - 1); } if (n.right != null) { q.add(n.right); ci.add(c + 1); } }
    out.addAll(m.values()); return out;
}
```

- **Time Complexity:** O(n log n)
- **Space Complexity:** O(n)

### Comparison

| # | Approach | Time | Space |
|---|----------|------|-------|
| 1 | DFS then Sort by Column and Row | O(n log n) | O(n) |
| 2 | BFS with Column Offsets (Optimized) | O(n log n) | O(n) |

## Source Code

### BinaryTreeVerticalOrderTraversal.java

```java
import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Queue;
import java.util.TreeMap;

public class BinaryTreeVerticalOrderTraversal {

    public static void main(String[] args) {
        Integer[][] inputs = {{3, 9, 20, null, null, 15, 7}, {3, 9, 8, 4, 0, 1, 7}, {1}, {}};
        for (Integer[] values : inputs) {
            TreeNode root = TreeNode.of(values);
            System.out.println("Input  : root = " + TreeNode.toString(root));
            System.out.println("Output : " + verticalOrder(root));
            System.out.println();
        }
    }

    public static List<List<Integer>> verticalOrder(TreeNode root) {
        List<List<Integer>> result = new ArrayList<>();
        if (root == null) {
            return result;
        }
        Map<Integer, List<Integer>> columns = new TreeMap<>();
        Queue<TreeNode> nodes = new ArrayDeque<>();
        Queue<Integer> indexes = new ArrayDeque<>();
        nodes.add(root);
        indexes.add(0);
        while (!nodes.isEmpty()) {
            TreeNode node = nodes.poll();
            int column = indexes.poll();
            columns.computeIfAbsent(column, k -> new LinkedList<>()).add(node.val);
            if (node.left != null) {
                nodes.add(node.left);
                indexes.add(column - 1);
            }
            if (node.right != null) {
                nodes.add(node.right);
                indexes.add(column + 1);
            }
        }
        result.addAll(columns.values());
        return result;
    }
}
```

## Solution

The optimized solution is Approach 2 above, which is also the version implemented in this repository.

The idea: give every node a column number (the root is 0, left is one less, right is one more). Level-order traversal already visits nodes top to bottom and, within a level, left to right, so appending to the list of each column in visiting order gives the required order with no extra sorting of the values.

**Input:** `root = [3, 9, 20, null, null, 15, 7]`.

| Node | Column | Column list afterwards |
|------|--------|-------------------------|
| 3 | 0 | 0: [3] |
| 9 | -1 | -1: [9] |
| 20 | 1 | 1: [20] |
| 15 | 0 | 0: [3, 15] |
| 7 | 2 | 2: [7] |

**Output:** `[[9], [3, 15], [20], [7]]`.
