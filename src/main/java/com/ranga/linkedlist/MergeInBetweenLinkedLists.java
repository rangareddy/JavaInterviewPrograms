package com.ranga.linkedlist;

/**
 * You are given two linked lists {@code list1} and {@code list2}, and two integers {@code a} and {@code b} ({@code 1 <= a <= b < size of list1 - 1}). Remove the nodes of {@code list1} from index {@code a} to index {@code b} inclusive, and put {@code list2} in their place.
 *
 * <p><b>Algorithm Strategy (Find Two Anchors and Splice):</b>
 * <ol>
 *   <li>Walk to the node at index {@code a - 1}; call it {@code before}.</li>
 *   <li>Keep walking to the node at index {@code b + 1}; call it {@code after}.</li>
 *   <li>Point {@code before.next} to the head of {@code list2}.</li>
 *   <li>Find the tail of {@code list2} and point it to {@code after}.</li>
 * </ol>
 *
 * <p><b>Example walkthrough</b>
 * <ul>
 *   <li>For {@code list1 = [10, 1, 13, 6, 9, 5]}, {@code a = 3}, {@code b = 4}: {@code before} is 13 (index 2), {@code after} is 5 (index 5).</li>
 *   <li>Link 13 to {@code 1000000 -> 1000001 -> 1000002}, then its tail to 5.</li>
 *   <li>Result: {@code [10, 1, 13, 1000000, 1000001, 1000002, 5]}.</li>
 * </ul>
 *
 * <p><b>Complexity Analysis:</b>
 * <ul>
 *   <li><b>Time Complexity:</b> {@code O(n + m)} — One walk through {@code list1} and one through {@code list2}.</li>
 *   <li><b>Space Complexity:</b> {@code O(1)} — Only the existing nodes are re-linked.</li>
 * </ul>
 *
 * @author Ranga Reddy
 */
public class MergeInBetweenLinkedLists {

    public static void main(String[] args) {
        int[][] first = {{10, 1, 13, 6, 9, 5}, {0, 1, 2, 3, 4, 5}, {1, 2, 3, 4}};
        int[][] second = {{1000000, 1000001, 1000002}, {1000000, 1000001, 1000002, 1000003, 1000004}, {9}};
        int[][] ranges = {{3, 4}, {2, 3}, {1, 2}};
        for (int i = 0; i < first.length; i++) {
            ListNode list1 = ListNode.of(first[i]);
            ListNode list2 = ListNode.of(second[i]);
            System.out.println("Input  : list1 = " + ListNode.toString(list1) + ", a = " + ranges[i][0] + ", b = " + ranges[i][1] + ", list2 = " + ListNode.toString(list2));
            System.out.println("Output : " + ListNode.toString(mergeInBetween(list1, ranges[i][0], ranges[i][1], list2)));
            System.out.println();
        }
    }

    public static ListNode mergeInBetween(ListNode list1, int a, int b, ListNode list2) {
        ListNode before = list1;
        for (int i = 0; i < a - 1; i++) {
            before = before.next;
        }
        ListNode after = before;
        for (int i = a - 1; i <= b; i++) {
            after = after.next;
        }
        before.next = list2;
        ListNode tail = list2;
        while (tail.next != null) {
            tail = tail.next;
        }
        tail.next = after;
        return list1;
    }
}
