# Binary Tree Paths

Given the root of a binary tree, return all root-to-leaf paths in any order. A leaf is a node with no children.

## Algorithm Strategy (DFS with the Current Path)

1. Do a DFS from the root, carrying the path string so far.
2. At each node append its value (with `->` before it when the path is not empty).
3. When the node is a leaf, add the path to the result.
4. Otherwise recurse into the left and right children.

## Example walkthrough

- For `[1, 2, 3, null, 5]`: the path `1->2` continues to the leaf 5, and the right child 3 is a leaf.
- Result: `[1->2->5, 1->3]`.

## Complexity Analysis

- **Time Complexity:** `O(n)` — Each node once, plus the cost of building the strings.
- **Space Complexity:** `O(h)` — The recursion depth.

## Approaches

The same problem can be solved in several ways. The approach marked (Optimized) is the one to use in practice.

### Approach 1: Iterative DFS with a Stack

Keep (node, path) pairs on an explicit stack.

```java
public static java.util.List<String> iterative(TreeNode root) { java.util.List<String> out = new java.util.ArrayList<>(); if (root == null) return out; java.util.Deque<Object[]> st = new java.util.ArrayDeque<>(); st.push(new Object[]{root, "" + root.val});
    while (!st.isEmpty()) { Object[] e = st.pop(); TreeNode n = (TreeNode) e[0]; String p = (String) e[1]; if (n.left == null && n.right == null) out.add(p); if (n.right != null) st.push(new Object[]{n.right, p + "->" + n.right.val}); if (n.left != null) st.push(new Object[]{n.left, p + "->" + n.left.val}); } return out; }
```

- **Time Complexity:** O(n)
- **Space Complexity:** O(n)

### Approach 2: Recursive DFS (Optimized)

Pass the path string down and record it at the leaves. This is the version implemented in the repository.

```java
public static java.util.List<String> recursive(TreeNode root) { java.util.List<String> out = new java.util.ArrayList<>(); go(root, "", out); return out; }
private static void go(TreeNode n, String pre, java.util.List<String> out) { if (n == null) return; String p = pre + n.val; if (n.left == null && n.right == null) { out.add(p); return; } go(n.left, p + "->", out); go(n.right, p + "->", out); }
```

- **Time Complexity:** O(n)
- **Space Complexity:** O(h)

### Comparison

| # | Approach | Time | Space |
|---|----------|------|-------|
| 1 | Iterative DFS with a Stack | O(n) | O(n) |
| 2 | Recursive DFS (Optimized) | O(n) | O(h) |

## Source Code

### BinaryTreePaths.java

```java
import java.util.ArrayList;
import java.util.List;

public class BinaryTreePaths {

    public static void main(String[] args) {
        Integer[][] inputs = {{1, 2, 3, null, 5}, {1}, {1, 2}, {}};
        for (Integer[] values : inputs) {
            TreeNode root = TreeNode.of(values);
            System.out.println("Input  : root = " + TreeNode.toString(root));
            System.out.println("Output : " + binaryTreePaths(root));
            System.out.println();
        }
    }

    public static List<String> binaryTreePaths(TreeNode root) {
        List<String> paths = new ArrayList<>();
        collect(root, "", paths);
        return paths;
    }

    private static void collect(TreeNode node, String prefix, List<String> paths) {
        if (node == null) {
            return;
        }
        String path = prefix + node.val;
        if (node.left == null && node.right == null) {
            paths.add(path);
            return;
        }
        collect(node.left, path + "->", paths);
        collect(node.right, path + "->", paths);
    }
}
```

## Solution

The optimized solution is Approach 2 above, which is also the version implemented in this repository.

The idea: a root-to-leaf path is exactly what a depth-first walk holds at the moment it reaches a leaf. Passing the path as a string argument means no cleanup is needed when the walk returns, because each call has its own copy.

**Input:** `root = [1, 2, 3, null, 5]`.

| Node | Path so far | Leaf? |
|------|-------------|-------|
| 1 | `1` | no |
| 2 | `1->2` | no |
| 5 | `1->2->5` | yes, recorded |
| 3 | `1->3` | yes, recorded |

**Output:** `[1->2->5, 1->3]`.
