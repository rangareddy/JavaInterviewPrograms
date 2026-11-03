package com.ranga.tree;

/**
 * In a binary tree, a node {@code X} is good if on the path from the root to {@code X} there is no node with a value greater than {@code X}. Given the root of a binary tree, return the number of good nodes.
 *
 * <p><b>Algorithm Strategy (DFS Carrying the Maximum of the Path):</b>
 * <ol>
 *   <li>Walk the tree depth-first and pass down the largest value seen on the path from the root to the current node ({@code maxSoFar}).</li>
 *   <li>The root is always good; any other node is good when its value is {@code >= maxSoFar}.</li>
 *   <li>Count the node if it is good, then recurse on both children with {@code max(maxSoFar, node.val)}.</li>
 *   <li>The sum of the counts of both subtrees plus the node itself is the answer for the node.</li>
 * </ol>
 *
 * <p><b>Example walkthrough</b>
 * <ul>
 *   <li>For {@code [3, 1, 4, 3, null, 1, 5]}: 3 (root) is good; 1 is not (3 &gt; 1); 3 under 1 is good (3 &gt;= 3); 4 is good; 1 under 4 is not; 5 is good.</li>
 *   <li>Result: {@code 4}.</li>
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
public class CountGoodNodesInBinaryTree {

    public static void main(String[] args) {
        Integer[][] inputs = {{3, 1, 4, 3, null, 1, 5}, {3, 3, null, 4, 2}, {1}, {}, {2, null, 4, 10, 8, null, null, 4}};
        for (Integer[] values : inputs) {
            TreeNode root = TreeNode.of(values);
            System.out.println("Input  : root = " + TreeNode.toString(root));
            System.out.println("Output : " + goodNodes(root));
            System.out.println();
        }
    }

    public static int goodNodes(TreeNode root) {
        return count(root, Integer.MIN_VALUE);
    }

    private static int count(TreeNode node, int maxSoFar) {
        if (node == null) {
            return 0;
        }
        int good = node.val >= maxSoFar ? 1 : 0;
        int newMax = Math.max(maxSoFar, node.val);
        return good + count(node.left, newMax) + count(node.right, newMax);
    }
}
