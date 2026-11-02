package com.ranga.blind75.linkedlist;

import com.ranga.blind75.linkedlist.ListNode;

/**
 * Given the head of a singly linked list, reverse the list and return the new head.
 *
 * <p><b>Algorithm Strategy (Iterative Pointer Reversal):</b>
 * <ol>
 *   <li>Keep {@code prev} (initially {@code null}) and {@code curr} (initially the head).</li>
 *   <li>For each node, save {@code curr.next} in {@code next}, then point {@code curr.next} back to {@code prev}.</li>
 *   <li>Advance: {@code prev = curr} and {@code curr = next}.</li>
 *   <li>When {@code curr} becomes {@code null}, {@code prev} is the last node of the original list, i.e. the new head.</li>
 * </ol>
 *
 * <p><b>Example walkthrough</b>
 * <ul>
 *   <li>For {@code 1 -> 2 -> 3}: after step 1 the list is {@code 1 -> null}, then {@code 2 -> 1 -> null}, then {@code 3 -> 2 -> 1 -> null}.</li>
 *   <li>Result: {@code [3, 2, 1]}.</li>
 * </ul>
 *
 * <p><b>Complexity Analysis:</b>
 * <ul>
 *   <li><b>Time Complexity:</b> {@code O(n)} — Each node is visited once.</li>
 *   <li><b>Space Complexity:</b> {@code O(1)} — Only three pointers.</li>
 * </ul>
 *
 * @author Ranga Reddy
 */
public class ReverseLinkedList {

    public static void main(String[] args) {
        ListNode[] inputs = {ListNode.of(1, 2, 3, 4, 5), ListNode.of(1, 2), ListNode.of()};
        for (ListNode head : inputs) {
            System.out.println("Input  : head = " + ListNode.toString(head));
            System.out.println("Output : " + ListNode.toString(reverseList(head)));
            System.out.println();
        }
    }

    public static ListNode reverseList(ListNode head) {
        ListNode prev = null;
        ListNode curr = head;
        while (curr != null) {
            ListNode next = curr.next;
            curr.next = prev;
            prev = curr;
            curr = next;
        }
        return prev;
    }
}
