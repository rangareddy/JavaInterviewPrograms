package com.ranga.tree;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

/**
 * Given the root of a binary tree, return the level order traversal of its node values (left to right, level by level).
 *
 * <p><b>Algorithm Strategy (Breadth-First Search with a Queue):</b>
 * <ol>
 *   <li>Start with the root in a queue.</li>
 *   <li>While the queue is not empty, the number of nodes currently in it is exactly the size of the current level; remember it as {@code levelSize}.</li>
 *   <li>Remove {@code levelSize} nodes, record their values in a list for this level and enqueue their non-null children for the next level.</li>
 *   <li>Add the list of the level to the result and repeat.</li>
 * </ol>
 *
 * <p><b>Example walkthrough</b>
 * <ul>
 *   <li>For {@code [3, 9, 20, null, null, 15, 7]}: level 0 is {@code [3]}; its children 9 and 20 form level 1; the children of 20 form level 2.</li>
 *   <li>Result: {@code [[3], [9, 20], [15, 7]]}.</li>
 * </ul>
 *
 * <p><b>Complexity Analysis:</b>
 * <ul>
 *   <li><b>Time Complexity:</b> {@code O(n)} — Every node is enqueued and dequeued once.</li>
 *   <li><b>Space Complexity:</b> {@code O(w)} — The queue holds at most one level, where {@code w} is the maximum width of the tree.</li>
 * </ul>
 *
 * @author Ranga Reddy
 */
public class BinaryTreeLevelOrderTraversal {

    public static void main(String[] args) {
        TreeNode[] inputs = {TreeNode.of(3, 9, 20, null, null, 15, 7), TreeNode.of(1), TreeNode.of()};
        for (TreeNode root : inputs) {
            System.out.println("Input  : root = " + TreeNode.toString(root));
            System.out.println("Output : " + levelOrder(root));
            System.out.println();
        }
    }

    public static List<List<Integer>> levelOrder(TreeNode root) {
        List<List<Integer>> levels = new ArrayList<>();
        if (root == null) {
            return levels;
        }

        Queue<TreeNode> queue = new LinkedList<>();
        queue.add(root);
        while (!queue.isEmpty()) {
            int levelSize = queue.size();
            List<Integer> level = new ArrayList<>();
            for (int i = 0; i < levelSize; i++) {
                TreeNode node = queue.poll();
                level.add(node.val);
                if (node.left != null) {
                    queue.add(node.left);
                }
                if (node.right != null) {
                    queue.add(node.right);
                }
            }
            levels.add(level);
        }
        return levels;
    }
}
