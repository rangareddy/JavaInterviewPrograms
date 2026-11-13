package com.ranga.tree;

import java.util.LinkedList;
import java.util.Queue;

/**
 * Given a binary tree, return its minimum depth: the number of nodes along the shortest path from the root down to the nearest leaf node. A leaf is a node with no children.
 *
 * <p><b>Algorithm Strategy (Level-Order BFS, Stop at the First Leaf):</b>
 * <ol>
 *   <li>Traverse the tree level by level with a queue, counting the depth. The first leaf encountered is on the shortest root-to-leaf path.</li>
 *   <li>Start with the root at depth 1. For each node removed from the queue, if it has no left and no right child, return the current depth.</li>
 *   <li>Otherwise add its children and continue; after finishing a level, increase the depth.</li>
 *   <li>BFS can stop early and never visits the deeper part of the tree. A recursive solution must be careful with a node that has only one child: {@code min(left, right)} would wrongly treat the missing side as depth 0.</li>
 * </ol>
 *
 * <p><b>Example walkthrough</b>
 * <ul>
 *   <li>For {@code [3, 9, 20, null, null, 15, 7]}: level 1 is {@code 3}, level 2 is {@code 9} and {@code 20}; {@code 9} is a leaf, so the answer is {@code 2}.</li>
 *   <li>For {@code [2, null, 3, null, 4, null, 5, null, 6]} the only leaf is at depth 5.</li>
 * </ul>
 *
 * <p><b>Complexity Analysis:</b>
 * <ul>
 *   <li><b>Time Complexity:</b> {@code O(n)} — In the worst case every node is visited.</li>
 *   <li><b>Space Complexity:</b> {@code O(w)} — The queue holds at most one level, with {@code w} the maximum width.</li>
 * </ul>
 *
 * @author Ranga Reddy
 */
public class MinimumDepthOfBinaryTree {

    public static void main(String[] args) {
        Integer[][] inputs = {
                {3, 9, 20, null, null, 15, 7},
                {2, null, 3, null, 4, null, 5, null, 6},
                {},
                {1, 2}
        };
        for (Integer[] values : inputs) {
            TreeNode root = TreeNode.of(values);
            System.out.println("Input  : root = " + TreeNode.toString(root));
            System.out.println("Output : " + minDepth(root));
            System.out.println();
        }
    }

    public static int minDepth(TreeNode root) {
        if (root == null) {
            return 0;
        }
        Queue<TreeNode> queue = new LinkedList<>();
        queue.add(root);
        int depth = 1;
        while (!queue.isEmpty()) {
            for (int size = queue.size(); size > 0; size--) {
                TreeNode node = queue.remove();
                if (node.left == null && node.right == null) {
                    return depth;
                }
                if (node.left != null) {
                    queue.add(node.left);
                }
                if (node.right != null) {
                    queue.add(node.right);
                }
            }
            depth++;
        }
        return depth;
    }
}
