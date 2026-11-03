package com.ranga.linkedlist;

import com.ranga.linkedlist.ListNode;

/**
 * Given the head of a linked list, reverse the nodes of the list {@code k} at a time and return the modified list. {@code k} is a positive integer not greater than the length of the list; if the number of nodes is not a multiple of {@code k}, the left-out nodes at the end stay as they are. Only the nodes themselves may be changed, not their values.
 *
 * <p><b>Algorithm Strategy (Reverse Each Complete Group In Place):</b>
 * <ol>
 *   <li>Use a {@code dummy} node before the head and {@code groupPrev}, the node just before the current group.</li>
 *   <li>Find the {@code k}-th node {@code kth} from {@code groupPrev}. If fewer than {@code k} nodes remain, stop: the rest stays unchanged.</li>
 *   <li>Reverse the {@code k} nodes between {@code groupPrev} and {@code kth} with the usual three-pointer reversal, starting with {@code prev} set to the node after the group so the reversed group is already linked to the rest.</li>
 *   <li>The old first node of the group is now its last node: it becomes the new {@code groupPrev}. Repeat for the next group.</li>
 * </ol>
 *
 * <p><b>Example walkthrough</b>
 * <ul>
 *   <li>For {@code [1, 2, 3, 4, 5]} and {@code k = 2}: the groups {@code (1,2)} and {@code (3,4)} are reversed, node 5 has no partner.</li>
 *   <li>Result: {@code [2, 1, 4, 3, 5]}. For {@code k = 3}: {@code [3, 2, 1, 4, 5]}.</li>
 * </ul>
 *
 * <p><b>Complexity Analysis:</b>
 * <ul>
 *   <li><b>Time Complexity:</b> {@code O(n)} — Every node is visited a constant number of times.</li>
 *   <li><b>Space Complexity:</b> {@code O(1)} — Only pointers are changed.</li>
 * </ul>
 *
 * @author Ranga Reddy
 */
public class ReverseNodesInKGroup {

    public static void main(String[] args) {
        int[][] inputs = {{1, 2, 3, 4, 5}, {1, 2, 3, 4, 5}, {1, 2}, {1, 2, 3, 4, 5, 6}};
        int[] ks = {2, 3, 1, 3};
        for (int i = 0; i < inputs.length; i++) {
            ListNode head = ListNode.of(inputs[i]);
            System.out.println("Input  : head = " + ListNode.toString(head) + ", k = " + ks[i]);
            System.out.println("Output : " + ListNode.toString(reverseKGroup(head, ks[i])));
            System.out.println();
        }
    }

    public static ListNode reverseKGroup(ListNode head, int k) {
        ListNode dummy = new ListNode(0);
        dummy.next = head;
        ListNode groupPrev = dummy;

        while (true) {
            ListNode kth = groupPrev;
            for (int i = 0; i < k && kth != null; i++) {
                kth = kth.next;
            }
            if (kth == null) {
                break;                              // fewer than k nodes left
            }

            ListNode groupNext = kth.next;
            ListNode prev = groupNext;
            ListNode curr = groupPrev.next;
            while (curr != groupNext) {             // reverse the k nodes of this group
                ListNode next = curr.next;
                curr.next = prev;
                prev = curr;
                curr = next;
            }

            ListNode oldFirst = groupPrev.next;     // now the last node of the group
            groupPrev.next = kth;
            groupPrev = oldFirst;
        }
        return dummy.next;
    }
}
