# Binary Tree Level Order Traversal

Given the root of a binary tree, return the level order traversal of its node values (left to right, level by level).

## Algorithm Strategy (Breadth-First Search with a Queue)

1. Start with the root in a queue.
2. While the queue is not empty, the number of nodes currently in it is exactly the size of the current level; remember it as `levelSize`.
3. Remove `levelSize` nodes, record their values in a list for this level and enqueue their non-null children for the next level.
4. Add the list of the level to the result and repeat.

## Example walkthrough

- For `[3, 9, 20, null, null, 15, 7]`: level 0 is `[3]`; its children 9 and 20 form level 1; the children of 20 form level 2.
- Result: `[[3], [9, 20], [15, 7]]`.

## Complexity Analysis

- **Time Complexity:** `O(n)` — Every node is enqueued and dequeued once.
- **Space Complexity:** `O(w)` — The queue holds at most one level, where `w` is the maximum width of the tree.

## Approaches

The same problem can be solved in several ways. The approach marked (Optimized) is the one to use in practice.

### Approach 1: Recursive DFS by Depth

Traverse depth-first and add each node to the list of its depth, creating the list when the depth is first reached.

```java
public static List<List<Integer>> levelOrderDfs(TreeNode root) {
    List<List<Integer>> levels = new ArrayList<>();
    fill(root, 0, levels);
    return levels;
}

private static void fill(TreeNode node, int depth, List<List<Integer>> levels) {
    if (node == null) return;
    if (depth == levels.size()) levels.add(new ArrayList<>());
    levels.get(depth).add(node.val);
    fill(node.left, depth + 1, levels);
    fill(node.right, depth + 1, levels);
}
```

- **Time Complexity:** O(n)
- **Space Complexity:** O(h)

### Approach 2: BFS with Two Lists

Keep the nodes of the current level in one list and build the next level in another, without a queue.

```java
public static List<List<Integer>> levelOrderTwoLists(TreeNode root) {
    List<List<Integer>> levels = new ArrayList<>();
    List<TreeNode> current = new ArrayList<>();
    if (root != null) current.add(root);
    while (!current.isEmpty()) {
        List<Integer> values = new ArrayList<>();
        List<TreeNode> next = new ArrayList<>();
        for (TreeNode node : current) {
            values.add(node.val);
            if (node.left != null) next.add(node.left);
            if (node.right != null) next.add(node.right);
        }
        levels.add(values);
        current = next;
    }
    return levels;
}
```

- **Time Complexity:** O(n)
- **Space Complexity:** O(w)

### Approach 3: BFS with a Null Level Marker

Put a `null` marker in the queue after each level. Meeting the marker means the current level is complete: store it, and re-insert the marker if more nodes follow.

```java
public static List<List<Integer>> levelOrderMarker(TreeNode root) {
    List<List<Integer>> levels = new ArrayList<>();
    if (root == null) return levels;
    Queue<TreeNode> queue = new LinkedList<>();
    queue.add(root);
    queue.add(null);
    List<Integer> level = new ArrayList<>();
    while (!queue.isEmpty()) {
        TreeNode node = queue.poll();
        if (node == null) {
            levels.add(level);
            level = new ArrayList<>();
            if (!queue.isEmpty()) queue.add(null);
        } else {
            level.add(node.val);
            if (node.left != null) queue.add(node.left);
            if (node.right != null) queue.add(node.right);
        }
    }
    return levels;
}
```

- **Time Complexity:** O(n)
- **Space Complexity:** O(w)

### Approach 4: BFS with Depth Stored Next to Each Node

Queue `(node, depth)` pairs and add every node to the list of its depth. No level-size bookkeeping is necessary.

```java
public static List<List<Integer>> levelOrderPairs(TreeNode root) {
    List<List<Integer>> levels = new ArrayList<>();
    if (root == null) return levels;
    Queue<Object[]> queue = new LinkedList<>();
    queue.add(new Object[]{root, 0});
    while (!queue.isEmpty()) {
        Object[] entry = queue.poll();
        TreeNode node = (TreeNode) entry[0];
        int depth = (Integer) entry[1];
        if (depth == levels.size()) levels.add(new ArrayList<>());
        levels.get(depth).add(node.val);
        if (node.left != null) queue.add(new Object[]{node.left, depth + 1});
        if (node.right != null) queue.add(new Object[]{node.right, depth + 1});
    }
    return levels;
}
```

- **Time Complexity:** O(n)
- **Space Complexity:** O(w)

### Approach 5: Iterative DFS with a Stack of (node, depth)

A pre-order traversal with an explicit stack that remembers each node's depth. Pushing the right child first makes the left child come out first, so every level list is filled left to right.

```java
public static List<List<Integer>> levelOrderStack(TreeNode root) {
    List<List<Integer>> levels = new ArrayList<>();
    if (root == null) return levels;
    Deque<Object[]> stack = new ArrayDeque<>();
    stack.push(new Object[]{root, 0});
    while (!stack.isEmpty()) {
        Object[] entry = stack.pop();
        TreeNode node = (TreeNode) entry[0];
        int depth = (Integer) entry[1];
        if (depth == levels.size()) levels.add(new ArrayList<>());
        levels.get(depth).add(node.val);
        if (node.right != null) stack.push(new Object[]{node.right, depth + 1});
        if (node.left != null) stack.push(new Object[]{node.left, depth + 1});
    }
    return levels;
}
```

- **Time Complexity:** O(n)
- **Space Complexity:** O(h)

### Approach 6: Collect Each Level by Depth (Brute Force)

Compute the height of the tree and, for every level from 0 to the height, run a recursive traversal that collects only the nodes at that depth. Easy to reason about, but the tree is traversed once per level.

```java
public static List<List<Integer>> levelOrderPerLevel(TreeNode root) {
    List<List<Integer>> levels = new ArrayList<>();
    int height = height(root);
    for (int depth = 0; depth < height; depth++) {
        List<Integer> level = new ArrayList<>();
        collect(root, depth, level);
        levels.add(level);
    }
    return levels;
}

private static int height(TreeNode node) {
    return node == null ? 0 : 1 + Math.max(height(node.left), height(node.right));
}

private static void collect(TreeNode node, int depth, List<Integer> level) {
    if (node == null) return;
    if (depth == 0) { level.add(node.val); return; }
    collect(node.left, depth - 1, level);
    collect(node.right, depth - 1, level);
}
```

- **Time Complexity:** O(n * h)
- **Space Complexity:** O(h)

### Approach 7: BFS with a Queue and Level Size (Optimized)

Take `queue.size()` nodes per round so each round is exactly one level. This is the version implemented in the repository.

```java
public static List<List<Integer>> levelOrder(TreeNode root) {
    List<List<Integer>> levels = new ArrayList<>();
    if (root == null) return levels;
    Queue<TreeNode> queue = new LinkedList<>();
    queue.add(root);
    while (!queue.isEmpty()) {
        List<Integer> level = new ArrayList<>();
        for (int i = queue.size(); i > 0; i--) {
            TreeNode node = queue.poll();
            level.add(node.val);
            if (node.left != null) queue.add(node.left);
            if (node.right != null) queue.add(node.right);
        }
        levels.add(level);
    }
    return levels;
}
```

- **Time Complexity:** O(n)
- **Space Complexity:** O(w)

### Comparison

| # | Approach | Time | Space |
|---|----------|------|-------|
| 1 | Recursive DFS by Depth | O(n) | O(h) |
| 2 | BFS with Two Lists | O(n) | O(w) |
| 3 | BFS with a Null Level Marker | O(n) | O(w) |
| 4 | BFS with Depth Stored Next to Each Node | O(n) | O(w) |
| 5 | Iterative DFS with a Stack of (node, depth) | O(n) | O(h) |
| 6 | Collect Each Level by Depth (Brute Force) | O(n * h) | O(h) |
| 7 | BFS with a Queue and Level Size (Optimized) | O(n) | O(w) |

### Optimized Solution

The optimized solution is Approach 7 above, which is also the version implemented in this repository.

## Source Code

Uses the shared helper [`TreeNode`](../../src/main/java/com/ranga/tree/TreeNode.java) (a small `TreeNode` with `of(...)` and `toString(...)` helpers for building and printing test data).

### BinaryTreeLevelOrderTraversal.java

```java
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

public class BinaryTreeLevelOrderTraversal {

    public static void main(String[] args) {
        TreeNode[] inputs = {TreeNode.of(3, 9, 20, null, null, 15, 7), TreeNode.of(1), TreeNode.of()};
        for (TreeNode root : inputs) {
            System.out.println("Input  : root = " + TreeNode.toString(root));
            System.out.println("Output : " + levelOrder(root));
            System.out.println();
        }
    }

    public static List<List<Integer>> levelOrder(TreeNode root) {
        List<List<Integer>> levels = new ArrayList<>();
        if (root == null) {
            return levels;
        }

        Queue<TreeNode> queue = new LinkedList<>();
        queue.add(root);
        while (!queue.isEmpty()) {
            int levelSize = queue.size();
            List<Integer> level = new ArrayList<>();
            for (int i = 0; i < levelSize; i++) {
                TreeNode node = queue.poll();
                level.add(node.val);
                if (node.left != null) {
                    queue.add(node.left);
                }
                if (node.right != null) {
                    queue.add(node.right);
                }
            }
            levels.add(level);
        }
        return levels;
    }
}
```
