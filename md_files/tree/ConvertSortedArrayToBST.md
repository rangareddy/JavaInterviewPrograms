# Convert Sorted Array to Binary Search Tree

Given an integer array sorted in ascending order, convert it to a height-balanced binary search tree, one in which the depths of the two subtrees of every node never differ by more than one.

## Algorithm Strategy (Midpoint Recursion)

1. The middle element of a sorted range is the best root: half of the values are smaller, half are larger.
2. Pick `mid = lo + (hi - lo) / 2` as the root of the range `[lo, hi]`.
3. Build the left subtree from `[lo, mid - 1]` and the right subtree from `[mid + 1, hi]` recursively.
4. An empty range returns `null`.

## Example walkthrough

- For `[-10, -3, 0, 5, 9]`: the root is 0, the left range `[-10, -3]` gives -10 with -3 as its right child, the right range `[5, 9]` gives 5 with 9 as its right child.
- Level order: `[0, -10, 5, null, -3, null, 9]`.

## Complexity Analysis

- **Time Complexity:** `O(n)` — Every element becomes exactly one node.
- **Space Complexity:** `O(log n)` — Recursion depth equals the height of the balanced tree.

## Approaches

The same problem can be solved in several ways. The approach marked (Optimized) is the one to use in practice.

### Approach 1: Insert One by One

Insert the values into a plain BST in order. The tree degenerates into a linked list, so it is not balanced.

```java
public static TreeNode naiveInsert(int[] nums) {
    TreeNode root = null;
    for (int v : nums) root = insert(root, v);
    return root;
}
private static TreeNode insert(TreeNode r, int v) {
    if (r == null) return new TreeNode(v);
    if (v < r.val) r.left = insert(r.left, v); else r.right = insert(r.right, v);
    return r;
}
public static int height(TreeNode r) { return r == null ? 0 : 1 + Math.max(height(r.left), height(r.right)); }
```

- **Time Complexity:** O(n^2)
- **Space Complexity:** O(n)

### Approach 2: Midpoint Recursion (Optimized)

Choose the middle element as the root of each range. This is the version implemented in the repository.

```java
public static TreeNode midpointBuild(int[] nums, int lo, int hi) {
    if (lo > hi) return null;
    int mid = lo + (hi - lo) / 2;
    TreeNode n = new TreeNode(nums[mid]);
    n.left = midpointBuild(nums, lo, mid - 1);
    n.right = midpointBuild(nums, mid + 1, hi);
    return n;
}
```

- **Time Complexity:** O(n)
- **Space Complexity:** O(log n)

### Comparison

| # | Approach | Time | Space |
|---|----------|------|-------|
| 1 | Insert One by One | O(n^2) | O(n) |
| 2 | Midpoint Recursion (Optimized) | O(n) | O(log n) |

## Source Code

### ConvertSortedArrayToBST.java

```java
import java.util.Arrays;

public class ConvertSortedArrayToBST {

    public static void main(String[] args) {
        int[][] inputs = {{-10, -3, 0, 5, 9}, {1, 3}, {7}, {}};
        for (int[] nums : inputs) {
            System.out.println("Input  : nums = " + Arrays.toString(nums));
            System.out.println("Output : " + TreeNode.toString(sortedArrayToBST(nums)));
            System.out.println();
        }
    }

    public static TreeNode sortedArrayToBST(int[] nums) {
        if (nums == null || nums.length == 0) {
            return null;
        }
        return build(nums, 0, nums.length - 1);
    }

    private static TreeNode build(int[] nums, int lo, int hi) {
        if (lo > hi) {
            return null;
        }
        int mid = lo + (hi - lo) / 2;
        TreeNode node = new TreeNode(nums[mid]);
        node.left = build(nums, lo, mid - 1);
        node.right = build(nums, mid + 1, hi);
        return node;
    }
}
```

## Solution

The optimized solution is Approach 2 above, which is also the version implemented in this repository.

The idea: a balanced BST over a sorted array needs the middle element as its root, then the same rule on each half. Inserting values one by one in sorted order would make a chain, because every new value is the largest so far.

**Input:** `nums = [-10, -3, 0, 5, 9]`.

| Call | Range | `mid` | Node created |
|------|-------|-------|--------------|
| 1 | [0, 4] | 2 | 0 (root) |
| 2 | [0, 1] | 0 | -10 |
| 3 | [1, 1] | 1 | -3 |
| 4 | [3, 4] | 3 | 5 |
| 5 | [4, 4] | 4 | 9 |

```text
        0
       / \
    -10    5
      \     \
      -3     9
```

**Output:** the tree above, height 3.
