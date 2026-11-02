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

### Approach 2: BFS with a Queue and Level Size (Optimized)

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

### Approach 3: BFS with Two Lists

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

### Comparison

| # | Approach | Time | Space |
|---|----------|------|-------|
| 1 | Recursive DFS by Depth | O(n) | O(h) |
| 2 | BFS with a Queue and Level Size (Optimized) | O(n) | O(w) |
| 3 | BFS with Two Lists | O(n) | O(w) |

### Optimized Solution

The optimized solution is Approach 2 above, which is also the version implemented in this repository.

## Source Code

Uses the shared helper [`TreeNode`](../../../src/main/java/com/ranga/blind75/tree/TreeNode.java) (a small `TreeNode` with `of(...)` and `toString(...)` helpers for building and printing test data).

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
