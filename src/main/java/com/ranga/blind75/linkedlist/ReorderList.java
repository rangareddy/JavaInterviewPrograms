package com.ranga.blind75.linkedlist;

import com.ranga.blind75.linkedlist.ListNode;

/**
 * Reorder the list {@code L0 -> L1 -> ... -> Ln-1 -> Ln} into {@code L0 -> Ln -> L1 -> Ln-1 -> L2 -> Ln-2 -> ...} by changing only the links, not the node values.
 *
 * <p><b>Algorithm Strategy (Find Middle, Reverse Second Half, Interleave):</b>
 * <ol>
 *   <li>Find the middle of the list with slow and fast pointers; {@code slow} ends at the last node of the first half.</li>
 *   <li>Cut the list after {@code slow} and reverse the second half in place.</li>
 *   <li>Merge the two halves alternately: take one node from the first half, then one from the reversed second half, until the second half is exhausted.</li>
 *   <li>The first half is never shorter than the second half, so no nodes are lost.</li>
 * </ol>
 *
 * <p><b>Example walkthrough</b>
 * <ul>
 *   <li>For {@code [1, 2, 3, 4]}: halves {@code 1 -> 2} and {@code 3 -> 4}; the reversed second half is {@code 4 -> 3}.</li>
 *   <li>Interleaving gives {@code 1 -> 4 -> 2 -> 3}.</li>
 * </ul>
 *
 * <p><b>Complexity Analysis:</b>
 * <ul>
 *   <li><b>Time Complexity:</b> {@code O(n)} — Three linear passes.</li>
 *   <li><b>Space Complexity:</b> {@code O(1)} — Only pointers are changed.</li>
 * </ul>
 *
 * @author Ranga Reddy
 */
public class ReorderList {

    public static void main(String[] args) {
        ListNode[] inputs = {ListNode.of(1, 2, 3, 4), ListNode.of(1, 2, 3, 4, 5), ListNode.of(1)};
        for (ListNode head : inputs) {
            System.out.println("Input  : head = " + ListNode.toString(head));
            reorderList(head);
            System.out.println("Output : " + ListNode.toString(head));
            System.out.println();
        }
    }

    public static void reorderList(ListNode head) {
        if (head == null || head.next == null) {
            return;
        }

        // 1. Find the middle
        ListNode slow = head;
        ListNode fast = head;
        while (fast.next != null && fast.next.next != null) {
            slow = slow.next;
            fast = fast.next.next;
        }

        // 2. Reverse the second half
        ListNode prev = null;
        ListNode curr = slow.next;
        slow.next = null;
        while (curr != null) {
            ListNode next = curr.next;
            curr.next = prev;
            prev = curr;
            curr = next;
        }

        // 3. Interleave the first half with the reversed second half
        ListNode first = head;
        ListNode second = prev;
        while (second != null) {
            ListNode firstNext = first.next;
            ListNode secondNext = second.next;
            first.next = second;
            second.next = firstNext;
            first = firstNext;
            second = secondNext;
        }
    }
}
