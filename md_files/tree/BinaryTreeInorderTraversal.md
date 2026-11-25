# Binary Tree Inorder Traversal

Given the `root` of a binary tree, return the inorder traversal of its nodes' values: for every node visit the left subtree, then the node, then the right subtree.

## Algorithm Strategy (Iterative Traversal with a Stack)

1. Keep a pointer `cur` and a stack.
2. While `cur` is not null, push it and move to its left child.
3. When `cur` is null, pop a node, record its value, and move to its right child.
4. Stop when both `cur` is null and the stack is empty.

## Example walkthrough

- For the tree `1 -> right 2 -> left 3`: go left from 1 (nothing), visit 1, go right to 2, go left to 3, visit 3, then visit 2.
- Result: `[1, 3, 2]`.

## Complexity Analysis

- **Time Complexity:** `O(n)` — Every node is pushed and popped once.
- **Space Complexity:** `O(h)` — The stack holds at most one root-to-leaf path.

## Approaches

The same problem can be solved in several ways. The approach marked (Optimized) is the one to use in practice.

### Approach 1: Recursion

Traverse the left subtree, visit the node, traverse the right subtree. Deep trees can overflow the call stack.

```java
public static java.util.List<Integer> recursive(TreeNode root) { java.util.List<Integer> out = new java.util.ArrayList<>(); go(root, out); return out; }
static void go(TreeNode n, java.util.List<Integer> out) { if (n == null) return; go(n.left, out); out.add(n.val); go(n.right, out); }
```

- **Time Complexity:** O(n)
- **Space Complexity:** O(h)

### Approach 2: Explicit Stack (Optimized)

Replace the call stack with an explicit one. This is the version implemented in the repository.

```java
public static java.util.List<Integer> iterative(TreeNode root) { java.util.List<Integer> out = new java.util.ArrayList<>(); java.util.Deque<TreeNode> st = new java.util.ArrayDeque<>(); TreeNode cur = root; while (cur != null || !st.isEmpty()) { while (cur != null) { st.push(cur); cur = cur.left; } cur = st.pop(); out.add(cur.val); cur = cur.right; } return out; }
```

- **Time Complexity:** O(n)
- **Space Complexity:** O(h)

### Comparison

| # | Approach | Time | Space |
|---|----------|------|-------|
| 1 | Recursion | O(n) | O(h) |
| 2 | Explicit Stack (Optimized) | O(n) | O(h) |

## Source Code

### BinaryTreeInorderTraversal.java

```java
import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Deque;
import java.util.List;

public class BinaryTreeInorderTraversal {

    public static void main(String[] args) {
        Integer[][] inputs = {{1, null, 2, 3}, {1, 2, 3, 4, 5}, {1}, {}};
        for (Integer[] values : inputs) {
            TreeNode root = TreeNode.of(values);
            System.out.println("Input  : root = " + TreeNode.toString(root));
            System.out.println("Output : " + inorderTraversal(root));
            System.out.println();
        }
    }

    public static List<Integer> inorderTraversal(TreeNode root) {
        List<Integer> result = new ArrayList<>();
        Deque<TreeNode> stack = new ArrayDeque<>();
        TreeNode cur = root;
        while (cur != null || !stack.isEmpty()) {
            while (cur != null) {
                stack.push(cur);
                cur = cur.left;
            }
            cur = stack.pop();
            result.add(cur.val);
            cur = cur.right;
        }
        return result;
    }
}
```

## Solution

The optimized solution is Approach 2 above, which is also the version implemented in this repository.

The idea: recursion remembers the path back up the tree through the call stack. An explicit stack does the same job with no recursion limit: go as far left as possible, visit, then switch to the right subtree.

**Input:** `root = [1, null, 2, 3]`.

| Step | Stack (top first) | Action | Output so far |
|------|-------------------|--------|---------------|
| 1 | 1 | push 1, left is null | [] |
| 2 | (empty) | pop 1, visit, go right to 2 | [1] |
| 3 | 2 | push 2, push 3 | [1] |
| 4 | 2 | pop 3, visit, no right child | [1, 3] |
| 5 | (empty) | pop 2, visit | [1, 3, 2] |

**Output:** `[1, 3, 2]`.
