# Amount of Time for Binary Tree to Be Infected

You are given the root of a binary tree with unique values and an integer `start`. At minute 0 the node with value `start` is infected. Each minute, an infected node infects its uninfected adjacent nodes (its left child, right child and parent). Return the number of minutes needed for the whole tree to be infected.

## Algorithm Strategy (Tree to Graph, then BFS)

1. Record the parent of every node with one traversal, and find the node with value `start`.
2. Run a BFS from the start node over three neighbours: left, right and parent.
3. Keep a visited set and process the queue level by level; each level is one minute.
4. The number of levels after the start level is the answer.

## Example walkthrough

- For `[1,5,3,null,4,10,6,9,2]` and `start = 3`: minute 1 infects 1, 10 and 6; minute 2 infects 5; minute 3 infects 4; minute 4 infects 9 and 2.
- Result: `4`.

## Complexity Analysis

- **Time Complexity:** `O(n)` — Each node is visited a constant number of times.
- **Space Complexity:** `O(n)` — The parent map and the queue.

## Approaches

The same problem can be solved in several ways. The approach marked (Optimized) is the one to use in practice.

### Approach 1: Distance by DFS Returning Depth

One DFS that returns the height of the subtree and, on the way up, combines the distance to the start node with the other subtree's height.

```java
private static int best;
public static int dfsDepth(TreeNode root, int start) { best = 0; depth(root, start); return best; }
private static int depth(TreeNode n, int start) {
    if (n == null) return 0;
    int l = depth(n.left, start), r = depth(n.right, start);
    if (n.val == start) { best = Math.max(best, Math.max(l, r)); return -1; }
    if (l >= 0 && r >= 0) return Math.max(l, r) + 1;
    int d = Math.abs(l) + Math.abs(r); best = Math.max(best, d); return Math.min(l, r) - 1;
}
```

- **Time Complexity:** O(n)
- **Space Complexity:** O(h)

### Approach 2: Parent Map and BFS (Optimized)

Convert the tree into an undirected graph with parent links and spread the infection by BFS. This is the version implemented in the repository.

```java
public static int bfs(TreeNode root, int start) {
    java.util.Map<TreeNode, TreeNode> par = new java.util.HashMap<>(); TreeNode s = null; java.util.Queue<TreeNode> q = new java.util.ArrayDeque<>(); q.add(root);
    while (!q.isEmpty()) { TreeNode n = q.poll(); if (n.val == start) s = n; if (n.left != null) { par.put(n.left, n); q.add(n.left); } if (n.right != null) { par.put(n.right, n); q.add(n.right); } }
    java.util.Set<TreeNode> seen = new java.util.HashSet<>(); q.add(s); seen.add(s); int m = -1;
    while (!q.isEmpty()) { m++; for (int z = q.size(); z > 0; z--) { TreeNode n = q.poll(); for (TreeNode x : new TreeNode[]{n.left, n.right, par.get(n)}) if (x != null && seen.add(x)) q.add(x); } }
    return m;
}
```

- **Time Complexity:** O(n)
- **Space Complexity:** O(n)

### Comparison

| # | Approach | Time | Space |
|---|----------|------|-------|
| 1 | Distance by DFS Returning Depth | O(n) | O(h) |
| 2 | Parent Map and BFS (Optimized) | O(n) | O(n) |

## Source Code

### AmountOfTimeForBinaryTreeToBeInfected.java

```java
import java.util.ArrayDeque;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Queue;
import java.util.Set;

public class AmountOfTimeForBinaryTreeToBeInfected {

    public static void main(String[] args) {
        Object[][] inputs = {
            {new Integer[]{1, 5, 3, null, 4, 10, 6, 9, 2}, 3},
            {new Integer[]{1}, 1},
            {new Integer[]{1, 2, null, 3, null, 4}, 2}
        };
        for (Object[] in : inputs) {
            TreeNode root = TreeNode.of((Integer[]) in[0]);
            int start = (Integer) in[1];
            System.out.println("Input  : root = " + TreeNode.toString(root) + ", start = " + start);
            System.out.println("Output : " + amountOfTime(root, start));
            System.out.println();
        }
    }

    public static int amountOfTime(TreeNode root, int start) {
        if (root == null) {
            return 0;
        }
        Map<TreeNode, TreeNode> parent = new HashMap<>();
        TreeNode startNode = null;
        Queue<TreeNode> walk = new ArrayDeque<>();
        walk.add(root);
        while (!walk.isEmpty()) {
            TreeNode node = walk.poll();
            if (node.val == start) {
                startNode = node;
            }
            if (node.left != null) {
                parent.put(node.left, node);
                walk.add(node.left);
            }
            if (node.right != null) {
                parent.put(node.right, node);
                walk.add(node.right);
            }
        }
        if (startNode == null) {
            return 0;
        }
        Set<TreeNode> infected = new HashSet<>();
        Queue<TreeNode> queue = new ArrayDeque<>();
        queue.add(startNode);
        infected.add(startNode);
        int minutes = -1;
        while (!queue.isEmpty()) {
            minutes++;
            for (int size = queue.size(); size > 0; size--) {
                TreeNode node = queue.poll();
                TreeNode[] neighbours = {node.left, node.right, parent.get(node)};
                for (TreeNode next : neighbours) {
                    if (next != null && infected.add(next)) {
                        queue.add(next);
                    }
                }
            }
        }
        return minutes;
    }
}
```

## Solution

The optimized solution is Approach 2 above, which is also the version implemented in this repository.

The idea: infection spreads to children and to the parent, so the tree is really an undirected graph. Adding parent links and running a BFS from the start node gives the time as the number of BFS levels.

**Input:** `root = [1, 5, 3, null, 4, 10, 6, 9, 2]`, `start = 3`.

| Minute | Newly infected |
|--------|----------------|
| 0 | 3 |
| 1 | 1, 10, 6 |
| 2 | 5 |
| 3 | 4 |
| 4 | 9, 2 |

**Output:** `4`.
