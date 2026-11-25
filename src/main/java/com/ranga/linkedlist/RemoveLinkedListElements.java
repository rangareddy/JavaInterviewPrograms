package com.ranga.linkedlist;

/**
 * Given the head of a linked list and an integer {@code val}, remove all the nodes of the linked list that have {@code Node.val == val}, and return the new head.
 *
 * <p><b>Algorithm Strategy (Dummy Head and One Pass):</b>
 * <ol>
 *   <li>Put a dummy node in front of the head so that removing the head needs no special case.</li>
 *   <li>Walk with a pointer {@code cur} starting at the dummy.</li>
 *   <li>If {@code cur.next.val == val}, unlink it with {@code cur.next = cur.next.next} and stay on {@code cur}; otherwise advance {@code cur}.</li>
 *   <li>Return {@code dummy.next}.</li>
 * </ol>
 *
 * <p><b>Example walkthrough</b>
 * <ul>
 *   <li>For {@code 1 -> 2 -> 6 -> 3 -> 4 -> 5 -> 6} and {@code val = 6}: the two 6 nodes are skipped.</li>
 *   <li>Result: {@code [1, 2, 3, 4, 5]}.</li>
 * </ul>
 *
 * <p><b>Complexity Analysis:</b>
 * <ul>
 *   <li><b>Time Complexity:</b> {@code O(n)} — One pass.</li>
 *   <li><b>Space Complexity:</b> {@code O(1)} — Only the dummy node.</li>
 * </ul>
 *
 * @author Ranga Reddy
 */
public class RemoveLinkedListElements {

    public static void main(String[] args) {
        int[][] inputs = {{1, 2, 6, 3, 4, 5, 6}, {7, 7, 7, 7}, {}, {6, 1, 6, 2}, {1, 2, 3}};
        int[] values = {6, 7, 1, 6, 5};
        for (int i = 0; i < inputs.length; i++) {
            ListNode head = ListNode.of(inputs[i]);
            System.out.println("Input  : head = " + ListNode.toString(head) + ", val = " + values[i]);
            System.out.println("Output : " + ListNode.toString(removeElements(head, values[i])));
            System.out.println();
        }
    }

    public static ListNode removeElements(ListNode head, int val) {
        ListNode dummy = new ListNode(0);
        dummy.next = head;
        ListNode cur = dummy;
        while (cur.next != null) {
            if (cur.next.val == val) {
                cur.next = cur.next.next;
            } else {
                cur = cur.next;
            }
        }
        return dummy.next;
    }
}
