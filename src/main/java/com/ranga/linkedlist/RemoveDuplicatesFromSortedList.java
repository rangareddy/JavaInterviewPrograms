package com.ranga.linkedlist;

/**
 * Given the head of a sorted linked list, delete all duplicates so that each value appears only once, and return the list still sorted.
 *
 * <p><b>Algorithm Strategy (Single Pointer Walk):</b>
 * <ol>
 *   <li>Because the list is sorted, equal values are always adjacent, so one pass is enough.</li>
 *   <li>Keep a pointer {@code cur} at the head. While {@code cur} and {@code cur.next} exist, compare their values.</li>
 *   <li>If they are equal, unlink the next node with {@code cur.next = cur.next.next} and stay on {@code cur}, since the new neighbour may also be a duplicate.</li>
 *   <li>If they differ, advance {@code cur}. Return the original head, which is never removed.</li>
 * </ol>
 *
 * <p><b>Example walkthrough</b>
 * <ul>
 *   <li>For {@code 1 -> 1 -> 2 -> 3 -> 3}: the second 1 is unlinked, {@code cur} moves to 2 and then 3, and the second 3 is unlinked.</li>
 *   <li>Result: {@code 1 -> 2 -> 3}.</li>
 * </ul>
 *
 * <p><b>Complexity Analysis:</b>
 * <ul>
 *   <li><b>Time Complexity:</b> {@code O(n)} — Each node is visited once.</li>
 *   <li><b>Space Complexity:</b> {@code O(1)} — Nodes are unlinked in place.</li>
 * </ul>
 *
 * @author Ranga Reddy
 */
public class RemoveDuplicatesFromSortedList {

    public static void main(String[] args) {
        int[][] inputs = {{1, 1, 2}, {1, 1, 2, 3, 3}, {1, 1, 1}, {}};
        for (int[] values : inputs) {
            ListNode head = ListNode.of(values);
            System.out.println("Input  : head = " + ListNode.toString(head));
            System.out.println("Output : " + ListNode.toString(deleteDuplicates(head)));
            System.out.println();
        }
    }

    public static ListNode deleteDuplicates(ListNode head) {
        ListNode cur = head;
        while (cur != null && cur.next != null) {
            if (cur.val == cur.next.val) {
                cur.next = cur.next.next;
            } else {
                cur = cur.next;
            }
        }
        return head;
    }
}
