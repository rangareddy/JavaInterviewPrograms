package com.ranga.linkedlist;

/**
 * Given the head of a singly linked list, return the list sorted in ascending order in {@code O(n log n)} time.
 *
 * <p><b>Algorithm Strategy (Merge Sort on a Linked List):</b>
 * <ol>
 *   <li>Merge sort suits linked lists: splitting needs no random access and merging relinks nodes without extra arrays.</li>
 *   <li>If the list has zero or one node it is already sorted. Otherwise find the middle with fast and slow pointers, but stop {@code slow} at the node before the middle so the list can be cut in two.</li>
 *   <li>Cut the list there by setting {@code prev.next = null}, then sort the left half and the right half recursively.</li>
 *   <li>Merge the two sorted halves with a dummy head node, always taking the smaller front node (taking from the left on ties keeps the sort stable).</li>
 * </ol>
 *
 * <p><b>Example walkthrough</b>
 * <ul>
 *   <li>For {@code [4, 2, 1, 3]}: split into {@code [4, 2]} and {@code [1, 3]}; sort them to {@code [2, 4]} and {@code [1, 3]}.</li>
 *   <li>Merging gives {@code [1, 2, 3, 4]}.</li>
 * </ul>
 *
 * <p><b>Complexity Analysis:</b>
 * <ul>
 *   <li><b>Time Complexity:</b> {@code O(n log n)} — The list is halved {@code log n} times and each level merges {@code n} nodes.</li>
 *   <li><b>Space Complexity:</b> {@code O(log n)} — Recursion depth. A bottom-up version brings this to {@code O(1)}.</li>
 * </ul>
 *
 * @author Ranga Reddy
 */
public class SortList {

    public static void main(String[] args) {
        int[][] inputs = {{4, 2, 1, 3}, {-1, 5, 3, 4, 0}, {}, {1}, {3, 3, 1}};
        for (int[] values : inputs) {
            ListNode head = ListNode.of(values);
            System.out.println("Input  : head = " + ListNode.toString(head));
            System.out.println("Output : " + ListNode.toString(sortList(head)));
            System.out.println();
        }
    }

    public static ListNode sortList(ListNode head) {
        if (head == null || head.next == null) {
            return head;
        }
        ListNode prev = null;
        ListNode slow = head;
        ListNode fast = head;
        while (fast != null && fast.next != null) {
            prev = slow;
            slow = slow.next;
            fast = fast.next.next;
        }
        prev.next = null;
        return merge(sortList(head), sortList(slow));
    }

    private static ListNode merge(ListNode a, ListNode b) {
        ListNode dummy = new ListNode(0);
        ListNode tail = dummy;
        while (a != null && b != null) {
            if (a.val <= b.val) {
                tail.next = a;
                a = a.next;
            } else {
                tail.next = b;
                b = b.next;
            }
            tail = tail.next;
        }
        tail.next = a != null ? a : b;
        return dummy.next;
    }
}
