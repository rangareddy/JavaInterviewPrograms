package com.ranga.blind75.linkedlist;

import com.ranga.blind75.linkedlist.ListNode;

/**
 * Merge two sorted linked lists into one sorted list by splicing together the nodes of the two lists, and return the head of the merged list.
 *
 * <p><b>Algorithm Strategy (Dummy Head and Two Pointers):</b>
 * <ol>
 *   <li>Create a {@code dummy} node and a {@code tail} pointer that starts at it; the merged list is built after {@code tail}.</li>
 *   <li>While both lists have nodes, link the smaller of {@code list1.val} and {@code list2.val} after {@code tail}, advance that list and advance {@code tail}.</li>
 *   <li>When one list is exhausted, link the remaining nodes of the other list (it is already sorted).</li>
 *   <li>Return {@code dummy.next}. The dummy node removes the special case of an empty result head.</li>
 * </ol>
 *
 * <p><b>Example walkthrough</b>
 * <ul>
 *   <li>For {@code [1, 2, 4]} and {@code [1, 3, 4]}: take 1 (list1), 1 (list2), 2, 3, 4 (list1), then the remaining 4 of list2.</li>
 *   <li>Result: {@code [1, 1, 2, 3, 4, 4]}.</li>
 * </ul>
 *
 * <p><b>Complexity Analysis:</b>
 * <ul>
 *   <li><b>Time Complexity:</b> {@code O(n + m)} — Every node is linked once.</li>
 *   <li><b>Space Complexity:</b> {@code O(1)} — Nodes are reused, no new nodes are created.</li>
 * </ul>
 *
 * @author Ranga Reddy
 */
public class MergeTwoSortedLists {

    public static void main(String[] args) {
        ListNode[][] inputs = {
                {ListNode.of(1, 2, 4), ListNode.of(1, 3, 4)},
                {ListNode.of(), ListNode.of()},
                {ListNode.of(), ListNode.of(0)}
        };
        for (ListNode[] pair : inputs) {
            System.out.println("Input  : list1 = " + ListNode.toString(pair[0]) + ", list2 = " + ListNode.toString(pair[1]));
            System.out.println("Output : " + ListNode.toString(mergeTwoLists(pair[0], pair[1])));
            System.out.println();
        }
    }

    public static ListNode mergeTwoLists(ListNode list1, ListNode list2) {
        ListNode dummy = new ListNode(0);
        ListNode tail = dummy;
        while (list1 != null && list2 != null) {
            if (list1.val <= list2.val) {
                tail.next = list1;
                list1 = list1.next;
            } else {
                tail.next = list2;
                list2 = list2.next;
            }
            tail = tail.next;
        }
        tail.next = (list1 != null) ? list1 : list2;
        return dummy.next;
    }
}
