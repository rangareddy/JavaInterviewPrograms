package com.ranga.blind75.linkedlist;

import com.ranga.blind75.linkedlist.ListNode;
import java.util.Comparator;
import java.util.PriorityQueue;

/**
 * Given an array of {@code k} linked lists, each sorted in ascending order, merge them into one sorted linked list and return it.
 *
 * <p><b>Algorithm Strategy (Min-Heap of List Heads):</b>
 * <ol>
 *   <li>Put the head node of every non-empty list into a {@code PriorityQueue} ordered by node value; the heap always holds at most {@code k} nodes.</li>
 *   <li>Repeatedly remove the smallest node from the heap and append it to the result list (after a dummy head).</li>
 *   <li>After removing a node, push its {@code next} node (if any) into the heap, so the next candidate from the same list is available.</li>
 *   <li>When the heap is empty all nodes have been merged; return {@code dummy.next}.</li>
 * </ol>
 *
 * <p><b>Example walkthrough</b>
 * <ul>
 *   <li>For {@code [[1,4,5],[1,3,4],[2,6]]} the heap starts with the heads 1, 1, 2.</li>
 *   <li>Pops: 1 (push 4), 1 (push 3), 2 (push 6), 3, 4, 4, 5, 6. Result: {@code [1, 1, 2, 3, 4, 4, 5, 6]}.</li>
 * </ul>
 *
 * <p><b>Complexity Analysis:</b>
 * <ul>
 *   <li><b>Time Complexity:</b> {@code O(N log k)} — {@code N} nodes in total, each heap operation costs {@code O(log k)}.</li>
 *   <li><b>Space Complexity:</b> {@code O(k)} — The heap holds one node per list.</li>
 * </ul>
 *
 * @author Ranga Reddy
 */
public class MergeKSortedLists {

    public static void main(String[] args) {
        ListNode[][] inputs = {
                {ListNode.of(1, 4, 5), ListNode.of(1, 3, 4), ListNode.of(2, 6)},
                {},
                {ListNode.of()}
        };
        for (ListNode[] lists : inputs) {
            StringBuilder sb = new StringBuilder("[");
            for (int i = 0; i < lists.length; i++) {
                sb.append(i > 0 ? ", " : "").append(ListNode.toString(lists[i]));
            }
            System.out.println("Input  : lists = " + sb.append("]"));
            System.out.println("Output : " + ListNode.toString(mergeKLists(lists)));
            System.out.println();
        }
    }

    public static ListNode mergeKLists(ListNode[] lists) {
        if (lists == null || lists.length == 0) {
            return null;
        }

        PriorityQueue<ListNode> heap = new PriorityQueue<>(Comparator.comparingInt((ListNode node) -> node.val));
        for (ListNode head : lists) {
            if (head != null) {
                heap.add(head);
            }
        }

        ListNode dummy = new ListNode(0);
        ListNode tail = dummy;
        while (!heap.isEmpty()) {
            ListNode smallest = heap.poll();
            tail.next = smallest;
            tail = tail.next;
            if (smallest.next != null) {
                heap.add(smallest.next);
            }
        }
        return dummy.next;
    }
}
