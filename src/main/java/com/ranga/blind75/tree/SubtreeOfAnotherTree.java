package com.ranga.blind75.tree;

/**
 * Given the roots of two binary trees {@code root} and {@code subRoot}, return {@code true} if some subtree of {@code root} (a node together with all its descendants) has exactly the structure and values of {@code subRoot}.
 *
 * <p><b>Algorithm Strategy (Compare Every Node with Same Tree):</b>
 * <ol>
 *   <li>An empty {@code subRoot} is a subtree of any tree; an empty {@code root} cannot contain a non-empty {@code subRoot}.</li>
 *   <li>If the tree rooted at the current node is identical to {@code subRoot} (checked with the Same Tree routine), return {@code true}.</li>
 *   <li>Otherwise look for {@code subRoot} in the left subtree or in the right subtree.</li>
 *   <li>Return {@code false} when all nodes were tried without a match.</li>
 * </ol>
 *
 * <p><b>Example walkthrough</b>
 * <ul>
 *   <li>For {@code root = [3, 4, 5, 1, 2]} and {@code subRoot = [4, 1, 2]}: the root 3 does not match 4; its left child 4 has children 1 and 2 and matches exactly.</li>
 *   <li>Result: {@code true}. If node 4 had an extra child, the structure would differ and the result would be {@code false}.</li>
 * </ul>
 *
 * <p><b>Complexity Analysis:</b>
 * <ul>
 *   <li><b>Time Complexity:</b> {@code O(m * n)} — In the worst case each of the {@code m} nodes of root starts a comparison of up to {@code n} nodes.</li>
 *   <li><b>Space Complexity:</b> {@code O(h)} — Recursion depth.</li>
 * </ul>
 *
 * @author Ranga Reddy
 */
public class SubtreeOfAnotherTree {

    public static void main(String[] args) {
        TreeNode[][] inputs = {
                {TreeNode.of(3, 4, 5, 1, 2), TreeNode.of(4, 1, 2)},
                {TreeNode.of(3, 4, 5, 1, 2, null, null, null, null, 0), TreeNode.of(4, 1, 2)}
        };
        for (TreeNode[] pair : inputs) {
            System.out.println("Input  : root = " + TreeNode.toString(pair[0]) + ", subRoot = " + TreeNode.toString(pair[1]));
            System.out.println("Output : " + isSubtree(pair[0], pair[1]));
            System.out.println();
        }
    }

    public static boolean isSubtree(TreeNode root, TreeNode subRoot) {
        if (subRoot == null) {
            return true;
        }
        if (root == null) {
            return false;
        }
        return isSameTree(root, subRoot) || isSubtree(root.left, subRoot) || isSubtree(root.right, subRoot);
    }

    private static boolean isSameTree(TreeNode p, TreeNode q) {
        if (p == null && q == null) {
            return true;
        }
        if (p == null || q == null || p.val != q.val) {
            return false;
        }
        return isSameTree(p.left, q.left) && isSameTree(p.right, q.right);
    }
}
