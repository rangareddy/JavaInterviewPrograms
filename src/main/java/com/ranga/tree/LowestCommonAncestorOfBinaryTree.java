package com.ranga.tree;

/**
 * Given a binary tree and two nodes {@code p} and {@code q} that are both in the tree, find their lowest common ancestor (LCA): the deepest node that has both {@code p} and {@code q} among its descendants (a node can be a descendant of itself). The tree is not a binary search tree.
 *
 * <p><b>Algorithm Strategy (Post-Order Recursion):</b>
 * <ol>
 *   <li>If the current node is {@code null}, or it is {@code p} or {@code q}, return it: nothing deeper can be a better answer on this path.</li>
 *   <li>Search the left subtree and the right subtree recursively.</li>
 *   <li>If both searches return a node, then {@code p} and {@code q} lie in different subtrees, so the current node is the LCA.</li>
 *   <li>If only one search returns a node, that node is either the LCA already found below or one of the targets: pass it up.</li>
 * </ol>
 *
 * <p><b>Example walkthrough</b>
 * <ul>
 *   <li>For the tree {@code [3, 5, 1, 6, 2, 0, 8, null, null, 7, 4]}, the LCA of {@code 5} and {@code 1} is {@code 3}.</li>
 *   <li>The LCA of {@code 5} and {@code 4} is {@code 5}, because a node may be its own descendant; the LCA of {@code 6} and {@code 4} is {@code 5}.</li>
 * </ul>
 *
 * <p><b>Complexity Analysis:</b>
 * <ul>
 *   <li><b>Time Complexity:</b> {@code O(n)} — In the worst case every node is visited once.</li>
 *   <li><b>Space Complexity:</b> {@code O(h)} — The recursion depth equals the height of the tree.</li>
 * </ul>
 *
 * @author Ranga Reddy
 */
public class LowestCommonAncestorOfBinaryTree {

    public static void main(String[] args) {
        TreeNode root = TreeNode.of(3, 5, 1, 6, 2, 0, 8, null, null, 7, 4);
        int[][] pairs = {{5, 1}, {5, 4}, {6, 4}, {7, 8}, {0, 8}};
        System.out.println("Tree   : root = " + TreeNode.toString(root));
        System.out.println();
        for (int[] pair : pairs) {
            TreeNode ancestor = lowestCommonAncestor(root, find(root, pair[0]), find(root, pair[1]));
            System.out.println("Input  : p = " + pair[0] + ", q = " + pair[1]);
            System.out.println("Output : " + ancestor.val);
            System.out.println();
        }
    }

    public static TreeNode lowestCommonAncestor(TreeNode root, TreeNode p, TreeNode q) {
        if (root == null || root == p || root == q) {
            return root;
        }

        TreeNode left = lowestCommonAncestor(root.left, p, q);
        TreeNode right = lowestCommonAncestor(root.right, p, q);
        if (left != null && right != null) {
            return root;
        }
        return left != null ? left : right;
    }

    private static TreeNode find(TreeNode root, int value) {
        if (root == null || root.val == value) {
            return root;
        }
        TreeNode left = find(root.left, value);
        return left != null ? left : find(root.right, value);
    }
}
