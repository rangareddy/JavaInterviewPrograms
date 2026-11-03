package com.ranga.tree;

/**
 * Given the root of a binary tree, return the length of its diameter: the length (number of edges) of the longest path between any two nodes. The path may or may not pass through the root.
 *
 * <p><b>Algorithm Strategy (Post-order Heights with a Global Maximum):</b>
 * <ol>
 *   <li>The longest path that bends at a node goes down its deepest left branch and its deepest right branch, so its length in edges is {@code leftHeight + rightHeight}.</li>
 *   <li>Compute heights bottom-up: the height of a missing child is 0 and the height of a node is {@code 1 + max(leftHeight, rightHeight)}.</li>
 *   <li>At every node update the best diameter with {@code leftHeight + rightHeight}.</li>
 *   <li>The best value after visiting the whole tree is the diameter.</li>
 * </ol>
 *
 * <p><b>Example walkthrough</b>
 * <ul>
 *   <li>For {@code [1, 2, 3, 4, 5]}: node 2 has heights 1 and 1, so the path 4-2-5 has 2 edges; at the root the left height is 2 and the right height is 1.</li>
 *   <li>Diameter: {@code 3} (the path 4-2-1-3 or 5-2-1-3).</li>
 * </ul>
 *
 * <p><b>Complexity Analysis:</b>
 * <ul>
 *   <li><b>Time Complexity:</b> {@code O(n)} — Every node is visited once.</li>
 *   <li><b>Space Complexity:</b> {@code O(h)} — Recursion depth equals the tree height.</li>
 * </ul>
 *
 * @author Ranga Reddy
 */
public class DiameterOfBinaryTree {

    public static void main(String[] args) {
        Integer[][] inputs = {{1, 2, 3, 4, 5}, {1, 2}, {1}, {1, 2, 3, 4, null, null, 5, 6, null, null, 7}, {}};
        for (Integer[] values : inputs) {
            TreeNode root = TreeNode.of(values);
            System.out.println("Input  : root = " + TreeNode.toString(root));
            System.out.println("Output : " + diameterOfBinaryTree(root));
            System.out.println();
        }
    }

    public static int diameterOfBinaryTree(TreeNode root) {
        int[] best = {0};
        height(root, best);
        return best[0];
    }

    // returns the height of the node (number of nodes on the longest downward path)
    private static int height(TreeNode node, int[] best) {
        if (node == null) {
            return 0;
        }
        int left = height(node.left, best);
        int right = height(node.right, best);
        best[0] = Math.max(best[0], left + right);          // longest path bending at this node
        return 1 + Math.max(left, right);
    }
}
