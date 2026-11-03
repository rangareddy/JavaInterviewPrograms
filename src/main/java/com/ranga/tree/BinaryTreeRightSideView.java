package com.ranga.tree;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

/**
 * Given the root of a binary tree, imagine standing on its right side and return the values of the nodes you can see, ordered from top to bottom (the last node of every level).
 *
 * <p><b>Algorithm Strategy (Level-Order Traversal, Keep the Last Node of Each Level):</b>
 * <ol>
 *   <li>Run a breadth-first traversal with a queue and process the tree level by level ({@code levelSize = queue.size()} nodes per round).</li>
 *   <li>Inside a level, the node that is processed last is the right-most node, the one visible from the right side.</li>
 *   <li>Add the value of that last node to the result after each level.</li>
 *   <li>Enqueue the left child before the right child so the right-most node of the next level is again processed last.</li>
 * </ol>
 *
 * <p><b>Example walkthrough</b>
 * <ul>
 *   <li>For {@code [1, 2, 3, null, 5, null, 4]}: level 0 shows 1; level 1 has 2 and 3, the last is 3; level 2 has 5 and 4, the last is 4.</li>
 *   <li>Result: {@code [1, 3, 4]}.</li>
 * </ul>
 *
 * <p><b>Complexity Analysis:</b>
 * <ul>
 *   <li><b>Time Complexity:</b> {@code O(n)} — Every node is enqueued once.</li>
 *   <li><b>Space Complexity:</b> {@code O(w)} — The queue holds at most one level, {@code w} = maximum width.</li>
 * </ul>
 *
 * @author Ranga Reddy
 */
public class BinaryTreeRightSideView {

    public static void main(String[] args) {
        Integer[][] inputs = {{1, 2, 3, null, 5, null, 4}, {1, null, 3}, {}, {1, 2, 3, 4}, {1, 2}};
        for (Integer[] values : inputs) {
            TreeNode root = TreeNode.of(values);
            System.out.println("Input  : root = " + TreeNode.toString(root));
            System.out.println("Output : " + rightSideView(root));
            System.out.println();
        }
    }

    public static List<Integer> rightSideView(TreeNode root) {
        List<Integer> result = new ArrayList<>();
        if (root == null) {
            return result;
        }

        Queue<TreeNode> queue = new LinkedList<>();
        queue.add(root);
        while (!queue.isEmpty()) {
            int levelSize = queue.size();
            for (int i = 0; i < levelSize; i++) {
                TreeNode node = queue.poll();
                if (i == levelSize - 1) {
                    result.add(node.val);               // last node of the level = visible from the right
                }
                if (node.left != null) {
                    queue.add(node.left);
                }
                if (node.right != null) {
                    queue.add(node.right);
                }
            }
        }
        return result;
    }
}
