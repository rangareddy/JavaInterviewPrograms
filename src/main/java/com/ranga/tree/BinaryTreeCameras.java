package com.ranga.tree;

/**
 * Place cameras on the nodes of a binary tree. A camera at a node monitors that node, its parent and its children. Return the minimum number of cameras needed to monitor every node.
 *
 * <p><b>Algorithm Strategy (Greedy Post-Order with Three States):</b>
 * <ol>
 *   <li>Process the tree bottom-up and give every node a state: {@code 0} means the node is not monitored yet, {@code 1} means it holds a camera, {@code 2} means it is monitored by a child camera (or has no need).</li>
 *   <li>A missing child counts as state {@code 2} (monitored), so a camera is never wasted on {@code null}.</li>
 *   <li>If any child is state {@code 0}, this node must hold a camera to cover it: count one camera and return {@code 1}.</li>
 *   <li>Otherwise, if any child holds a camera ({@code 1}), this node is monitored: return {@code 2}. If both children are {@code 2}, this node is not monitored by them: return {@code 0} and let its parent cover it.</li>
 *   <li>After the traversal, if the root is state {@code 0}, add one more camera. Placing cameras at the parents of leaves, never on the leaves, is the greedy choice that covers the most nodes per camera.</li>
 * </ol>
 *
 * <p><b>Example walkthrough</b>
 * <ul>
 *   <li>For {@code [0, 0, null, 0, 0]}: the leaves are state 0, so their parent (the second node) gets a camera and returns 1.</li>
 *   <li>The root sees a child with a camera, so it is monitored (state 2). One camera in total.</li>
 *   <li>For a path of four nodes {@code [0, 0, null, 0, null, 0]} two cameras are needed.</li>
 * </ul>
 *
 * <p><b>Complexity Analysis:</b>
 * <ul>
 *   <li><b>Time Complexity:</b> {@code O(n)} — Each node is visited once.</li>
 *   <li><b>Space Complexity:</b> {@code O(h)} — Recursion depth.</li>
 * </ul>
 *
 * @author Ranga Reddy
 */
public class BinaryTreeCameras {

    private static final int NOT_MONITORED = 0;
    private static final int HAS_CAMERA = 1;
    private static final int MONITORED = 2;

    public static void main(String[] args) {
        Integer[][] inputs = {
                {0, 0, null, 0, 0},
                {0, 0, null, 0, null, 0, null, null, 0},
                {0},
                {0, 0, 0}
        };
        for (Integer[] values : inputs) {
            TreeNode root = TreeNode.of(values);
            System.out.println("Input  : root = " + TreeNode.toString(root));
            System.out.println("Output : " + minCameraCover(root));
            System.out.println();
        }
    }

    public static int minCameraCover(TreeNode root) {
        int[] cameras = new int[1];
        if (state(root, cameras) == NOT_MONITORED) {
            cameras[0]++;
        }
        return cameras[0];
    }

    private static int state(TreeNode node, int[] cameras) {
        if (node == null) {
            return MONITORED;
        }
        int left = state(node.left, cameras);
        int right = state(node.right, cameras);
        if (left == NOT_MONITORED || right == NOT_MONITORED) {
            cameras[0]++;
            return HAS_CAMERA;
        }
        if (left == HAS_CAMERA || right == HAS_CAMERA) {
            return MONITORED;
        }
        return NOT_MONITORED;
    }
}
