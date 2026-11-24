package com.ranga.linkedlist;

/**
 * Given the head of a singly linked list, group all the nodes with odd indexes together followed by the nodes with even indexes (the first node has index 1), and return the reordered list. The relative order inside each group is kept. Use {@code O(1)} extra space and {@code O(n)} time.
 *
 * <p><b>Algorithm Strategy (Odd and Even Chains):</b>
 * <ol>
 *   <li>Keep {@code odd = head} and {@code even = head.next}, and remember {@code evenHead = even}.</li>
 *   <li>While {@code even} and {@code even.next} exist: link {@code odd.next = even.next} and advance {@code odd}, then link {@code even.next = odd.next} and advance {@code even}.</li>
 *   <li>After the loop the odd chain ends at {@code odd}: attach {@code odd.next = evenHead}.</li>
 *   <li>Return {@code head}.</li>
 * </ol>
 *
 * <p><b>Example walkthrough</b>
 * <ul>
 *   <li>For {@code 1 -> 2 -> 3 -> 4 -> 5}: the odd chain is 1, 3, 5 and the even chain is 2, 4.</li>
 *   <li>Result: {@code 1 -> 3 -> 5 -> 2 -> 4}.</li>
 * </ul>
 *
 * <p><b>Complexity Analysis:</b>
 * <ul>
 *   <li><b>Time Complexity:</b> {@code O(n)} — One pass.</li>
 *   <li><b>Space Complexity:</b> {@code O(1)} — Pointers only.</li>
 * </ul>
 *
 * @author Ranga Reddy
 */
public class OddEvenLinkedList {

    public static void main(String[] args) {
        int[][] inputs = {{1, 2, 3, 4, 5}, {2, 1, 3, 5, 6, 4, 7}, {1}, {}};
        for (int[] values : inputs) {
            ListNode head = ListNode.of(values);
            System.out.println("Input  : head = " + ListNode.toString(head));
            System.out.println("Output : " + ListNode.toString(oddEvenList(head)));
            System.out.println();
        }
    }

    public static ListNode oddEvenList(ListNode head) {
        if (head == null || head.next == null) {
            return head;
        }
        ListNode odd = head;
        ListNode even = head.next;
        ListNode evenHead = even;
        while (even != null && even.next != null) {
            odd.next = even.next;
            odd = odd.next;
            even.next = odd.next;
            even = even.next;
        }
        odd.next = evenHead;
        return head;
    }
}
