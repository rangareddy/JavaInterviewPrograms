# Minimum Depth of Binary Tree

Given a binary tree, return its minimum depth: the number of nodes along the shortest path from the root down to the nearest leaf node. A leaf is a node with no children.

## Algorithm Strategy (Level-Order BFS, Stop at the First Leaf)

1. Traverse the tree level by level with a queue, counting the depth. The first leaf encountered is on the shortest root-to-leaf path.
2. Start with the root at depth 1. For each node removed from the queue, if it has no left and no right child, return the current depth.
3. Otherwise add its children and continue; after finishing a level, increase the depth.
4. BFS can stop early and never visits the deeper part of the tree. A recursive solution must be careful with a node that has only one child: `min(left, right)` would wrongly treat the missing side as depth 0.

## Example walkthrough

- For `[3, 9, 20, null, null, 15, 7]`: level 1 is `3`, level 2 is `9` and `20`; `9` is a leaf, so the answer is `2`.
- For `[2, null, 3, null, 4, null, 5, null, 6]` the only leaf is at depth 5.

## Complexity Analysis

- **Time Complexity:** `O(n)` — In the worst case every node is visited.
- **Space Complexity:** `O(w)` — The queue holds at most one level, with `w` the maximum width.

## Approaches

The same problem can be solved in several ways. The approach marked (Optimized) is the one to use in practice.

### Approach 1: Recursive DFS

The depth of a node is 1 plus the depth of its only child, or 1 plus the smaller depth when it has two children.

```java
public static int minDepthDfs(TreeNode root) {
    if (root == null) return 0;
    if (root.left == null) return 1 + minDepthDfs(root.right);
    if (root.right == null) return 1 + minDepthDfs(root.left);
    return 1 + Math.min(minDepthDfs(root.left), minDepthDfs(root.right));
}
```

- **Time Complexity:** O(n)
- **Space Complexity:** O(h) recursion depth

### Approach 2: BFS with Early Exit (Optimized)

Walk the tree level by level and return at the first leaf, which avoids visiting the rest of the tree. This is the version implemented in the repository.

```java
public static int minDepth(TreeNode root) {
    if (root == null) {
        return 0;
    }
    Queue<TreeNode> queue = new LinkedList<>();
    queue.add(root);
    int depth = 1;
    while (!queue.isEmpty()) {
        for (int size = queue.size(); size > 0; size--) {
            TreeNode node = queue.remove();
            if (node.left == null && node.right == null) {
                return depth;
            }
            if (node.left != null) {
                queue.add(node.left);
            }
            if (node.right != null) {
                queue.add(node.right);
            }
        }
        depth++;
    }
    return depth;
}
```

- **Time Complexity:** O(n) worst case, often much less
- **Space Complexity:** O(w)

### Comparison

| # | Approach | Time | Space |
|---|----------|------|-------|
| 1 | Recursive DFS | O(n) | O(h) recursion depth |
| 2 | BFS with Early Exit (Optimized) | O(n) worst case, often much less | O(w) |

### Optimized Solution

The optimized solution is Approach 2 above, which is also the version implemented in this repository.

## Source Code

### MinimumDepthOfBinaryTree.java

```java
import java.util.LinkedList;
import java.util.Queue;

public class MinimumDepthOfBinaryTree {

    public static void main(String[] args) {
        Integer[][] inputs = {
                {3, 9, 20, null, null, 15, 7},
                {2, null, 3, null, 4, null, 5, null, 6},
                {},
                {1, 2}
        };
        for (Integer[] values : inputs) {
            TreeNode root = TreeNode.of(values);
            System.out.println("Input  : root = " + TreeNode.toString(root));
            System.out.println("Output : " + minDepth(root));
            System.out.println();
        }
    }

    public static int minDepth(TreeNode root) {
        if (root == null) {
            return 0;
        }
        Queue<TreeNode> queue = new LinkedList<>();
        queue.add(root);
        int depth = 1;
        while (!queue.isEmpty()) {
            for (int size = queue.size(); size > 0; size--) {
                TreeNode node = queue.remove();
                if (node.left == null && node.right == null) {
                    return depth;
                }
                if (node.left != null) {
                    queue.add(node.left);
                }
                if (node.right != null) {
                    queue.add(node.right);
                }
            }
            depth++;
        }
        return depth;
    }
}
```
