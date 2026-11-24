# All Nodes Distance K in Binary Tree

Given the root of a binary tree, a target node and an integer `k`, return the values of all nodes that are at distance `k` from the target node, in any order.

## Algorithm Strategy (Parent Links and BFS)

1. Build a map from each node to its parent with one traversal.
2. Start a BFS from the target node, moving to left child, right child and parent.
3. Keep a visited set to avoid walking back.
4. After exactly `k` levels, the nodes in the queue are the answer.

## Example walkthrough

- For `[3,5,1,6,2,0,8,null,null,7,4]`, target 5 and `k = 2`: the nodes two steps from 5 are 7, 4 (its grandchildren) and 1 (through the parent 3).
- Result: `[7, 4, 1]` (any order).

## Complexity Analysis

- **Time Complexity:** `O(n)` — Each node is visited a constant number of times.
- **Space Complexity:** `O(n)` — The parent map, the visited set and the queue.

## Approaches

The same problem can be solved in several ways. The approach marked (Optimized) is the one to use in practice.

### Approach 1: DFS with Distance Returned from Below

Find the target recursively and, on the way back, look for nodes at the remaining distance in the sibling subtrees.

```java
public static java.util.List<Integer> dfs(TreeNode root, TreeNode target, int k) {
    java.util.List<Integer> out = new java.util.ArrayList<>(); go(root, target, k, out); return out;
}
private static int go(TreeNode n, TreeNode t, int k, java.util.List<Integer> out) {
    if (n == null) return -1;
    if (n == t) { down(n, k, out); return 0; }
    int l = go(n.left, t, k, out); if (l >= 0) { if (l + 1 == k) out.add(n.val); else down(n.right, k - l - 2, out); return l + 1; }
    int r = go(n.right, t, k, out); if (r >= 0) { if (r + 1 == k) out.add(n.val); else down(n.left, k - r - 2, out); return r + 1; }
    return -1;
}
private static void down(TreeNode n, int d, java.util.List<Integer> out) { if (n == null || d < 0) return; if (d == 0) { out.add(n.val); return; } down(n.left, d - 1, out); down(n.right, d - 1, out); }
```

- **Time Complexity:** O(n)
- **Space Complexity:** O(h)

### Approach 2: Parent Map and BFS (Optimized)

Add parent links, then BFS outward for `k` levels. This is the version implemented in the repository.

```java
public static java.util.List<Integer> bfs(TreeNode root, TreeNode target, int k) {
    java.util.Map<TreeNode, TreeNode> par = new java.util.HashMap<>(); java.util.Queue<TreeNode> w = new java.util.ArrayDeque<>(); w.add(root);
    while (!w.isEmpty()) { TreeNode n = w.poll(); if (n.left != null) { par.put(n.left, n); w.add(n.left); } if (n.right != null) { par.put(n.right, n); w.add(n.right); } }
    java.util.Set<TreeNode> seen = new java.util.HashSet<>(); java.util.Queue<TreeNode> q = new java.util.ArrayDeque<>(); q.add(target); seen.add(target); int d = 0;
    while (!q.isEmpty() && d < k) { for (int z = q.size(); z > 0; z--) { TreeNode n = q.poll(); for (TreeNode x : new TreeNode[]{n.left, n.right, par.get(n)}) if (x != null && seen.add(x)) q.add(x); } d++; }
    java.util.List<Integer> out = new java.util.ArrayList<>(); for (TreeNode n : q) out.add(n.val); return out;
}
```

- **Time Complexity:** O(n)
- **Space Complexity:** O(n)

### Comparison

| # | Approach | Time | Space |
|---|----------|------|-------|
| 1 | DFS with Distance Returned from Below | O(n) | O(h) |
| 2 | Parent Map and BFS (Optimized) | O(n) | O(n) |

## Source Code

### AllNodesDistanceKInBinaryTree.java

```java
import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Queue;
import java.util.Set;

public class AllNodesDistanceKInBinaryTree {

    public static void main(String[] args) {
        Integer[] values = {3, 5, 1, 6, 2, 0, 8, null, null, 7, 4};
        int[][] queries = {{5, 2}, {5, 0}, {1, 1}, {7, 5}};
        TreeNode root = TreeNode.of(values);
        for (int[] q : queries) {
            System.out.println("Input  : root = " + TreeNode.toString(root) + ", target = " + q[0] + ", k = " + q[1]);
            System.out.println("Output : " + distanceK(root, find(root, q[0]), q[1]));
            System.out.println();
        }
    }

    public static List<Integer> distanceK(TreeNode root, TreeNode target, int k) {
        List<Integer> result = new ArrayList<>();
        if (root == null || target == null || k < 0) {
            return result;
        }
        Map<TreeNode, TreeNode> parent = new HashMap<>();
        Queue<TreeNode> walk = new ArrayDeque<>();
        walk.add(root);
        while (!walk.isEmpty()) {
            TreeNode node = walk.poll();
            if (node.left != null) {
                parent.put(node.left, node);
                walk.add(node.left);
            }
            if (node.right != null) {
                parent.put(node.right, node);
                walk.add(node.right);
            }
        }
        Set<TreeNode> seen = new HashSet<>();
        Queue<TreeNode> queue = new ArrayDeque<>();
        queue.add(target);
        seen.add(target);
        int distance = 0;
        while (!queue.isEmpty() && distance < k) {
            for (int size = queue.size(); size > 0; size--) {
                TreeNode node = queue.poll();
                for (TreeNode next : new TreeNode[]{node.left, node.right, parent.get(node)}) {
                    if (next != null && seen.add(next)) {
                        queue.add(next);
                    }
                }
            }
            distance++;
        }
        for (TreeNode node : queue) {
            result.add(node.val);
        }
        return result;
    }

    /** Finds the node with the given value in the tree, or null. */
    public static TreeNode find(TreeNode root, int value) {
        if (root == null || root.val == value) {
            return root;
        }
        TreeNode left = find(root.left, value);
        return left != null ? left : find(root.right, value);
    }
}
```

## Solution

The optimized solution is Approach 2 above, which is also the version implemented in this repository.

The idea: a node at distance `k` can be below the target or above it (through parents). Parent links make "up" as easy as "down", so a BFS of `k` levels from the target finds all of them.

**Input:** `root = [3, 5, 1, 6, 2, 0, 8, null, null, 7, 4]`, `target = 5`, `k = 2`.

| Level | Queue after the level |
|-------|-----------------------|
| 0 | 5 |
| 1 | 6, 2, 3 |
| 2 | 7, 4, 1 |

**Output:** `[7, 4, 1]` (any order).
