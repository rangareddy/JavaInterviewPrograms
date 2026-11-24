# Merge Two Binary Trees

Merge two binary trees by overlapping them. Where both nodes exist, the merged node's value is the sum of the two. Where only one exists, that node is used as it is.

## Algorithm Strategy (Parallel Recursion)

1. If one tree is `null`, the other tree is the answer for that position.
2. Otherwise create a node with `t1.val + t2.val`.
3. Recurse on the two left children and the two right children to build the merged subtrees.
4. Return the new node. The inputs are left unmodified.

## Example walkthrough

- For `[1,3,2,5]` and `[2,1,3,null,4,null,7]` the roots add to 3, the left children to 4, the right children to 5.
- Result: `[3,4,5,5,4,null,7]`.

## Complexity Analysis

- **Time Complexity:** `O(min(n, m))` — Only positions present in both trees are recursed into.
- **Space Complexity:** `O(min(h1, h2))` — Recursion depth is bounded by the shallower tree height.

## Approaches

The same problem can be solved in several ways. The approach marked (Optimized) is the one to use in practice.

### Approach 1: Recursive, Reuse Nodes

Add `t2` into `t1` in place and return `t1`. Saves allocation but mutates the input.

```java
public static TreeNode mergeInPlace(TreeNode t1, TreeNode t2) {
    if (t1 == null) return t2;
    if (t2 == null) return t1;
    t1.val += t2.val;
    t1.left = mergeInPlace(t1.left, t2.left);
    t1.right = mergeInPlace(t1.right, t2.right);
    return t1;
}
```

- **Time Complexity:** O(min(n, m))
- **Space Complexity:** O(min(h1, h2))

### Approach 2: Recursive, New Nodes (Optimized)

Build a fresh tree and leave both inputs untouched. This is the version implemented in the repository.

```java
public static TreeNode mergeNew(TreeNode t1, TreeNode t2) {
    if (t1 == null) return t2;
    if (t2 == null) return t1;
    TreeNode m = new TreeNode(t1.val + t2.val);
    m.left = mergeNew(t1.left, t2.left);
    m.right = mergeNew(t1.right, t2.right);
    return m;
}
```

- **Time Complexity:** O(min(n, m))
- **Space Complexity:** O(min(h1, h2))

### Comparison

| # | Approach | Time | Space |
|---|----------|------|-------|
| 1 | Recursive, Reuse Nodes | O(min(n, m)) | O(min(h1, h2)) |
| 2 | Recursive, New Nodes (Optimized) | O(min(n, m)) | O(min(h1, h2)) |

## Source Code

### MergeTwoBinaryTrees.java

```java
public class MergeTwoBinaryTrees {

    public static void main(String[] args) {
        Integer[][][] inputs = {
            {{1, 3, 2, 5}, {2, 1, 3, null, 4, null, 7}},
            {{1}, {1, 2}},
            {{}, {1}}
        };
        for (Integer[][] pair : inputs) {
            TreeNode a = TreeNode.of(pair[0]);
            TreeNode b = TreeNode.of(pair[1]);
            System.out.println("Input  : t1 = " + TreeNode.toString(a) + ", t2 = " + TreeNode.toString(b));
            System.out.println("Output : " + TreeNode.toString(mergeTrees(a, b)));
            System.out.println();
        }
    }

    public static TreeNode mergeTrees(TreeNode t1, TreeNode t2) {
        if (t1 == null) {
            return t2;
        }
        if (t2 == null) {
            return t1;
        }
        TreeNode merged = new TreeNode(t1.val + t2.val);
        merged.left = mergeTrees(t1.left, t2.left);
        merged.right = mergeTrees(t1.right, t2.right);
        return merged;
    }
}
```

## Solution

The optimized solution is Approach 2 above, which is also the version implemented in this repository.

The idea: walk both trees together. Where both have a node, add the values; where one is missing, reuse the other subtree as it is.

**Input:** `t1 = [1, 3, 2, 5]`, `t2 = [2, 1, 3, null, 4, null, 7]`.

| Position | `t1` | `t2` | Merged |
|----------|------|------|--------|
| root | 1 | 2 | 3 |
| left | 3 | 1 | 4 |
| right | 2 | 3 | 5 |
| left.left | 5 | null | 5 (reuse) |
| left.right | null | 4 | 4 (reuse) |
| right.right | null | 7 | 7 (reuse) |

**Output:** `[3, 4, 5, 5, 4, null, 7]`.
