package com.ranga.linkedlist;

/**
 * Write a function to delete a node in a singly linked list. You are given only the node to be deleted, not the head. The node is guaranteed not to be the tail. After the deletion the value of the node must not exist in the list, and the number of nodes decreases by one.
 *
 * <p><b>Algorithm Strategy (Overwrite with the Next Node):</b>
 * <ol>
 *   <li>Without the head, the previous node cannot be reached, so the node itself cannot be unlinked.</li>
 *   <li>Instead copy the value of the next node into the given node.</li>
 *   <li>Then skip the next node by setting {@code node.next = node.next.next}.</li>
 *   <li>To the rest of the list this is the same as deleting the original node.</li>
 * </ol>
 *
 * <p><b>Example walkthrough</b>
 * <ul>
 *   <li>For {@code 4 -> 5 -> 1 -> 9} and the node with value 5: copy 1 into it, then skip the next node.</li>
 *   <li>Result: {@code [4, 1, 9]}.</li>
 * </ul>
 *
 * <p><b>Complexity Analysis:</b>
 * <ul>
 *   <li><b>Time Complexity:</b> {@code O(1)} — Two assignments.</li>
 *   <li><b>Space Complexity:</b> {@code O(1)} — No extra storage.</li>
 * </ul>
 *
 * @author Ranga Reddy
 */
public class DeleteNodeInALinkedList {

    public static void main(String[] args) {
        int[][] inputs = {{4, 5, 1, 9}, {4, 5, 1, 9}, {1, 2}, {0, 1, 2, 3}};
        int[] targets = {5, 1, 1, 0};
        for (int i = 0; i < inputs.length; i++) {
            ListNode head = ListNode.of(inputs[i]);
            ListNode node = head;
            while (node.val != targets[i]) {
                node = node.next;
            }
            System.out.println("Input  : head = " + ListNode.toString(head) + ", node = " + targets[i]);
            deleteNode(node);
            System.out.println("Output : " + ListNode.toString(head));
            System.out.println();
        }
    }

    public static void deleteNode(ListNode node) {
        if (node == null || node.next == null) {
            return;
        }
        node.val = node.next.val;
        node.next = node.next.next;
    }
}
