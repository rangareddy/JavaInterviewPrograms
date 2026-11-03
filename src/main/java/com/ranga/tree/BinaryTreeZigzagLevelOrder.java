package com.ranga.tree;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

/**
 * Given the root of a binary tree, return the zigzag level order traversal of its nodes: the first level from left to right, the next level from right to left, then left to right again, and so on.
 *
 * <p><b>Algorithm Strategy (BFS with an Alternating Direction):</b>
 * <ol>
 *   <li>Run a breadth-first search with a queue, processing the tree one level at a time.</li>
 *   <li>For each level, take {@code size = queue.size()} nodes and collect their values in a {@code LinkedList}.</li>
 *   <li>If the level index is even, append each value at the end (left to right); if it is odd, insert it at the front (right to left).</li>
 *   <li>Add the children of every node to the queue as usual; the direction only changes how the level list is filled.</li>
 * </ol>
 *
 * <p><b>Example walkthrough</b>
 * <ul>
 *   <li>For {@code [3, 9, 20, null, null, 15, 7]}: level 0 is {@code [3]}, level 1 is read right to left {@code [20, 9]}, level 2 left to right {@code [15, 7]}.</li>
 *   <li>Result: {@code [[3], [20, 9], [15, 7]]}.</li>
 * </ul>
 *
 * <p><b>Complexity Analysis:</b>
 * <ul>
 *   <li><b>Time Complexity:</b> {@code O(n)} — Every node is visited once and inserted at either end in O(1).</li>
 *   <li><b>Space Complexity:</b> {@code O(n)} — The queue holds up to one level, and the result holds all nodes.</li>
 * </ul>
 *
 * @author Ranga Reddy
 */
public class BinaryTreeZigzagLevelOrder {

    public static void main(String[] args) {
        Integer[][] inputs = {{3, 9, 20, null, null, 15, 7}, {1}, {}, {1, 2, 3, 4, null, null, 5}};
        for (Integer[] values : inputs) {
            TreeNode root = TreeNode.of(values);
            System.out.println("Input  : root = " + TreeNode.toString(root));
            System.out.println("Output : " + zigzagLevelOrder(root));
            System.out.println();
        }
    }

    public static List<List<Integer>> zigzagLevelOrder(TreeNode root) {
        List<List<Integer>> result = new ArrayList<>();
        if (root == null) {
            return result;
        }

        Queue<TreeNode> queue = new LinkedList<>();
        queue.add(root);
        boolean leftToRight = true;
        while (!queue.isEmpty()) {
            LinkedList<Integer> level = new LinkedList<>();
            for (int size = queue.size(); size > 0; size--) {
                TreeNode node = queue.poll();
                if (leftToRight) {
                    level.addLast(node.val);
                } else {
                    level.addFirst(node.val);
                }
                if (node.left != null) {
                    queue.add(node.left);
                }
                if (node.right != null) {
                    queue.add(node.right);
                }
            }
            result.add(level);
            leftToRight = !leftToRight;
        }
        return result;
    }
}
