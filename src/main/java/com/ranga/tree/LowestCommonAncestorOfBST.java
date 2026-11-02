package com.ranga.tree;

/**
 * Given a binary search tree and two nodes {@code p} and {@code q}, find their lowest common ancestor (LCA): the lowest node that has both as descendants (a node may be a descendant of itself).
 *
 * <p><b>Algorithm Strategy (Walk Down Using the BST Property):</b>
 * <ol>
 *   <li>Start at the root. If both {@code p} and {@code q} are smaller than the current value, the LCA is in the left subtree, so move left.</li>
 *   <li>If both are greater than the current value, the LCA is in the right subtree, so move right.</li>
 *   <li>Otherwise the two values are on different sides of the current node (or one of them equals it), so the current node is the lowest node that is above both: it is the LCA.</li>
 *   <li>No recursion or extra memory is needed.</li>
 * </ol>
 *
 * <p><b>Example walkthrough</b>
 * <ul>
 *   <li>For the tree {@code [6, 2, 8, 0, 4, 7, 9, null, null, 3, 5]} with {@code p = 2} and {@code q = 8}: both differ in side at the root 6 (2 &lt; 6 &lt; 8), so the LCA is {@code 6}.</li>
 *   <li>For {@code p = 2} and {@code q = 4}: both are smaller than 6 so go left to 2; 2 equals {@code p}, so the LCA is {@code 2}.</li>
 * </ul>
 *
 * <p><b>Complexity Analysis:</b>
 * <ul>
 *   <li><b>Time Complexity:</b> {@code O(h)} — One step per level.</li>
 *   <li><b>Space Complexity:</b> {@code O(1)} — Iterative.</li>
 * </ul>
 *
 * @author Ranga Reddy
 */
public class LowestCommonAncestorOfBST {

    public static void main(String[] args) {
        TreeNode root = TreeNode.of(6, 2, 8, 0, 4, 7, 9, null, null, 3, 5);
        int[][] pairs = {{2, 8}, {2, 4}, {3, 5}};
        for (int[] pair : pairs) {
            System.out.println("Input  : root = " + TreeNode.toString(root) + ", p = " + pair[0] + ", q = " + pair[1]);
            System.out.println("Output : " + lowestCommonAncestor(root, new TreeNode(pair[0]), new TreeNode(pair[1])).val);
            System.out.println();
        }
    }

    public static TreeNode lowestCommonAncestor(TreeNode root, TreeNode p, TreeNode q) {
        TreeNode curr = root;
        while (curr != null) {
            if (p.val < curr.val && q.val < curr.val) {
                curr = curr.left;
            } else if (p.val > curr.val && q.val > curr.val) {
                curr = curr.right;
            } else {
                return curr;
            }
        }
        return null;
    }
}
