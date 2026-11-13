package com.ranga.tree;

/**
 * Given the root of a binary tree, check whether it is a mirror of itself, that is, symmetric around its center.
 *
 * <p><b>Algorithm Strategy (Recursive Mirror Comparison):</b>
 * <ol>
 *   <li>A tree is symmetric when its left and right subtrees are mirror images of each other.</li>
 *   <li>Define {@code isMirror(a, b)}: two empty subtrees are mirrors; if only one is empty they are not.</li>
 *   <li>Otherwise the values must be equal, and the outer children must mirror each other ({@code a.left} with {@code b.right}) and the inner children must mirror each other ({@code a.right} with {@code b.left}).</li>
 *   <li>Call {@code isMirror(root.left, root.right)} for the answer. Every node is visited once.</li>
 * </ol>
 *
 * <p><b>Example walkthrough</b>
 * <ul>
 *   <li>For {@code [1, 2, 2, 3, 4, 4, 3]}: the two 2s match, then 3 pairs with 3 and 4 pairs with 4, so the tree is symmetric.</li>
 *   <li>For {@code [1, 2, 2, null, 3, null, 3]} the 3s sit on the same side of both subtrees, so it is not.</li>
 * </ul>
 *
 * <p><b>Complexity Analysis:</b>
 * <ul>
 *   <li><b>Time Complexity:</b> {@code O(n)} — Each node is compared once.</li>
 *   <li><b>Space Complexity:</b> {@code O(h)} — Recursion depth, equal to the tree height.</li>
 * </ul>
 *
 * @author Ranga Reddy
 */
public class SymmetricTree {

    public static void main(String[] args) {
        Integer[][] inputs = {
                {1, 2, 2, 3, 4, 4, 3},
                {1, 2, 2, null, 3, null, 3},
                {1},
                {1, 2, 3}
        };
        for (Integer[] values : inputs) {
            TreeNode root = TreeNode.of(values);
            System.out.println("Input  : root = " + TreeNode.toString(root));
            System.out.println("Output : " + isSymmetric(root));
            System.out.println();
        }
    }

    public static boolean isSymmetric(TreeNode root) {
        return root == null || isMirror(root.left, root.right);
    }

    private static boolean isMirror(TreeNode a, TreeNode b) {
        if (a == null || b == null) {
            return a == b;
        }
        return a.val == b.val && isMirror(a.left, b.right) && isMirror(a.right, b.left);
    }
}
