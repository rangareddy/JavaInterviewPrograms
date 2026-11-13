# Binary Tree Cameras

Place cameras on the nodes of a binary tree. A camera at a node monitors that node, its parent and its children. Return the minimum number of cameras needed to monitor every node.

## Algorithm Strategy (Greedy Post-Order with Three States)

1. Process the tree bottom-up and give every node a state: `0` means the node is not monitored yet, `1` means it holds a camera, `2` means it is monitored by a child camera (or has no need).
2. A missing child counts as state `2` (monitored), so a camera is never wasted on `null`.
3. If any child is state `0`, this node must hold a camera to cover it: count one camera and return `1`.
4. Otherwise, if any child holds a camera (`1`), this node is monitored: return `2`. If both children are `2`, this node is not monitored by them: return `0` and let its parent cover it.
5. After the traversal, if the root is state `0`, add one more camera. Placing cameras at the parents of leaves, never on the leaves, is the greedy choice that covers the most nodes per camera.

## Example walkthrough

- For `[0, 0, null, 0, 0]`: the leaves are state 0, so their parent (the second node) gets a camera and returns 1.
- The root sees a child with a camera, so it is monitored (state 2). One camera in total.
- For a path of four nodes `[0, 0, null, 0, null, 0]` two cameras are needed.

## Complexity Analysis

- **Time Complexity:** `O(n)` — Each node is visited once.
- **Space Complexity:** `O(h)` — Recursion depth.

## Approaches

The same problem can be solved in several ways. The approach marked (Optimized) is the one to use in practice.

### Approach 1: Tree DP with Three Costs

For every node compute the cheapest cost for three cases: it holds a camera, it is covered by a child, or it is covered by its parent (not yet by its subtree).

```java
public static int minCameraCoverDp(TreeNode root) {
    int[] r = dp(root);
    return Math.min(r[0], r[1]);
}

// r[0] = camera at node, r[1] = node covered by a child, r[2] = node covered only if the parent has a camera
private static int[] dp(TreeNode node) {
    if (node == null) return new int[]{1_000_000, 0, 0};
    int[] l = dp(node.left), r = dp(node.right);
    int withCamera = 1 + Math.min(l[0], Math.min(l[1], l[2])) + Math.min(r[0], Math.min(r[1], r[2]));
    int coveredByChild = Math.min(l[0] + Math.min(r[0], r[1]), r[0] + Math.min(l[0], l[1]));
    int coveredByParent = l[1] + r[1];
    return new int[]{withCamera, coveredByChild, coveredByParent};
}
```

- **Time Complexity:** O(n)
- **Space Complexity:** O(h)

### Approach 2: Greedy with Node States (Optimized)

Return one of three states from each subtree and place a camera only when a child is not monitored. This is the version implemented in the repository.

```java
public static int minCameraCover(TreeNode root) {
    int[] cameras = new int[1];
    if (state(root, cameras) == NOT_MONITORED) {
        cameras[0]++;
    }
    return cameras[0];
}

private static int state(TreeNode node, int[] cameras) {
    if (node == null) {
        return MONITORED;
    }
    int left = state(node.left, cameras);
    int right = state(node.right, cameras);
    if (left == NOT_MONITORED || right == NOT_MONITORED) {
        cameras[0]++;
        return HAS_CAMERA;
    }
    if (left == HAS_CAMERA || right == HAS_CAMERA) {
        return MONITORED;
    }
    return NOT_MONITORED;
}
```

- **Time Complexity:** O(n)
- **Space Complexity:** O(h)

### Comparison

| # | Approach | Time | Space |
|---|----------|------|-------|
| 1 | Tree DP with Three Costs | O(n) | O(h) |
| 2 | Greedy with Node States (Optimized) | O(n) | O(h) |

### Optimized Solution

The optimized solution is Approach 2 above, which is also the version implemented in this repository.

## Source Code

### BinaryTreeCameras.java

```java
public class BinaryTreeCameras {

    private static final int NOT_MONITORED = 0;
    private static final int HAS_CAMERA = 1;
    private static final int MONITORED = 2;

    public static void main(String[] args) {
        Integer[][] inputs = {
                {0, 0, null, 0, 0},
                {0, 0, null, 0, null, 0, null, null, 0},
                {0},
                {0, 0, 0}
        };
        for (Integer[] values : inputs) {
            TreeNode root = TreeNode.of(values);
            System.out.println("Input  : root = " + TreeNode.toString(root));
            System.out.println("Output : " + minCameraCover(root));
            System.out.println();
        }
    }

    public static int minCameraCover(TreeNode root) {
        int[] cameras = new int[1];
        if (state(root, cameras) == NOT_MONITORED) {
            cameras[0]++;
        }
        return cameras[0];
    }

    private static int state(TreeNode node, int[] cameras) {
        if (node == null) {
            return MONITORED;
        }
        int left = state(node.left, cameras);
        int right = state(node.right, cameras);
        if (left == NOT_MONITORED || right == NOT_MONITORED) {
            cameras[0]++;
            return HAS_CAMERA;
        }
        if (left == HAS_CAMERA || right == HAS_CAMERA) {
            return MONITORED;
        }
        return NOT_MONITORED;
    }
}
```
