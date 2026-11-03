package com.ranga.linkedlist;

/**
 * Given the heads of two singly linked lists, return the node at which they intersect (the lists share their tail from that node on), or {@code null} if they do not intersect.
 *
 * <p><b>Algorithm Strategy (Two Pointers that Switch Lists):</b>
 * <ol>
 *   <li>Start pointer {@code a} at {@code headA} and pointer {@code b} at {@code headB}.</li>
 *   <li>Advance both one node at a time. When a pointer reaches the end of its list, redirect it to the head of the <em>other</em> list.</li>
 *   <li>After switching, both pointers have travelled {@code lenA + lenB} nodes in total, so the difference in length is cancelled and they line up.</li>
 *   <li>They meet at the intersection node, or both become {@code null} at the same moment when there is no intersection.</li>
 * </ol>
 *
 * <p><b>Example walkthrough</b>
 * <ul>
 *   <li>List A = {@code 4 -> 1 -> 8 -> 4 -> 5}, list B = {@code 5 -> 6 -> 1 -> 8 -> 4 -> 5} sharing the tail {@code 8 -> 4 -> 5}: both pointers meet at the node with value 8.</li>
 *   <li>For {@code 2 -> 6 -> 4} and {@code 1 -> 5} without a shared node, both pointers reach {@code null} together: no intersection.</li>
 * </ul>
 *
 * <p><b>Complexity Analysis:</b>
 * <ul>
 *   <li><b>Time Complexity:</b> {@code O(m + n)} — Each pointer walks at most both lists once.</li>
 *   <li><b>Space Complexity:</b> {@code O(1)} — Two pointers.</li>
 * </ul>
 *
 * @author Ranga Reddy
 */
public class IntersectionOfTwoLinkedLists {

    public static void main(String[] args) {
        ListNode shared = ListNode.of(8, 4, 5);
        ListNode headA = ListNode.of(4, 1);
        headA.next.next = shared;
        ListNode headB = ListNode.of(5, 6, 1);
        headB.next.next.next = shared;

        System.out.println("Input  : A = " + ListNode.toString(headA) + ", B = " + ListNode.toString(headB));
        ListNode meeting = getIntersectionNode(headA, headB);
        System.out.println("Output : intersect at value " + (meeting == null ? "none" : meeting.val));
        System.out.println();

        ListNode other1 = ListNode.of(2, 6, 4);
        ListNode other2 = ListNode.of(1, 5);
        System.out.println("Input  : A = " + ListNode.toString(other1) + ", B = " + ListNode.toString(other2));
        ListNode none = getIntersectionNode(other1, other2);
        System.out.println("Output : intersect at value " + (none == null ? "none" : none.val));
    }

    public static ListNode getIntersectionNode(ListNode headA, ListNode headB) {
        ListNode a = headA;
        ListNode b = headB;
        while (a != b) {
            a = a == null ? headB : a.next;
            b = b == null ? headA : b.next;
        }
        return a;
    }
}
