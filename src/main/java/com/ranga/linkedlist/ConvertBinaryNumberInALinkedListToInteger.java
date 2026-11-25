package com.ranga.linkedlist;

/**
 * Given the head of a singly linked list in which every node holds 0 or 1 and the list represents a binary number (most significant bit first), return its decimal value.
 *
 * <p><b>Algorithm Strategy (Shift Left and Add the Bit):</b>
 * <ol>
 *   <li>Start with {@code value = 0}.</li>
 *   <li>For each node, shift the value left by one bit and add the node's bit: {@code value = (value << 1) | node.val}.</li>
 *   <li>Move to the next node.</li>
 *   <li>Return the value at the end of the list.</li>
 * </ol>
 *
 * <p><b>Example walkthrough</b>
 * <ul>
 *   <li>For {@code 1 -> 0 -> 1}: {@code 0 -> 1}, then {@code 10}, then {@code 101}.</li>
 *   <li>Result: {@code 5}.</li>
 * </ul>
 *
 * <p><b>Complexity Analysis:</b>
 * <ul>
 *   <li><b>Time Complexity:</b> {@code O(n)} — One pass.</li>
 *   <li><b>Space Complexity:</b> {@code O(1)} — One integer.</li>
 * </ul>
 *
 * @author Ranga Reddy
 */
public class ConvertBinaryNumberInALinkedListToInteger {

    public static void main(String[] args) {
        int[][] inputs = {{1, 0, 1}, {0}, {1}, {1, 1, 1, 1}, {1, 0, 0, 1, 0, 0, 1, 1, 1, 0, 0, 0, 0, 0, 0}};
        for (int[] bits : inputs) {
            ListNode head = ListNode.of(bits);
            System.out.println("Input  : head = " + ListNode.toString(head));
            System.out.println("Output : " + getDecimalValue(head));
            System.out.println();
        }
    }

    public static int getDecimalValue(ListNode head) {
        int value = 0;
        while (head != null) {
            value = (value << 1) | head.val;
            head = head.next;
        }
        return value;
    }
}
