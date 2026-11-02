package com.ranga.blind75.linkedlist;

import com.ranga.blind75.linkedlist.ListNode;

/**
 * Given the head of a linked list, remove the {@code n}-th node from the end of the list and return the head.
 *
 * <p><b>Algorithm Strategy (Two Pointers with a Gap):</b>
 * <ol>
 *   <li>Add a {@code dummy} node before the head so removing the first node needs no special case.</li>
 *   <li>Move {@code fast} forward {@code n + 1} steps from the dummy so that there are exactly {@code n} nodes between {@code slow} and {@code fast}.</li>
 *   <li>Move both pointers together until {@code fast} reaches {@code null}; now {@code slow.next} is the node to delete.</li>
 *   <li>Delete it with {@code slow.next = slow.next.next} and return {@code dummy.next}. This works in one pass without knowing the length.</li>
 * </ol>
 *
 * <p><b>Example walkthrough</b>
 * <ul>
 *   <li>For {@code [1, 2, 3, 4, 5]} and {@code n = 2}: fast starts 3 nodes ahead; when it falls off the end, slow stands on 3 and {@code slow.next} is 4.</li>
 *   <li>Removing it gives {@code [1, 2, 3, 5]}.</li>
 * </ul>
 *
 * <p><b>Complexity Analysis:</b>
 * <ul>
 *   <li><b>Time Complexity:</b> {@code O(L)} — One pass over the list of length {@code L}.</li>
 *   <li><b>Space Complexity:</b> {@code O(1)} — Two pointers.</li>
 * </ul>
 *
 * @author Ranga Reddy
 */
public class RemoveNthNodeFromEnd {

    public static void main(String[] args) {
        ListNode[] inputs = {ListNode.of(1, 2, 3, 4, 5), ListNode.of(1), ListNode.of(1, 2)};
        int[] ns = {2, 1, 1};
        for (int i = 0; i < inputs.length; i++) {
            System.out.println("Input  : head = " + ListNode.toString(inputs[i]) + ", n = " + ns[i]);
            System.out.println("Output : " + ListNode.toString(removeNthFromEnd(inputs[i], ns[i])));
            System.out.println();
        }
    }

    public static ListNode removeNthFromEnd(ListNode head, int n) {
        ListNode dummy = new ListNode(0);
        dummy.next = head;
        ListNode slow = dummy;
        ListNode fast = dummy;

        for (int i = 0; i <= n; i++) {
            fast = fast.next;           // create a gap of n nodes between slow and fast
        }
        while (fast != null) {
            slow = slow.next;
            fast = fast.next;
        }
        slow.next = slow.next.next;     // delete the n-th node from the end
        return dummy.next;
    }
}
