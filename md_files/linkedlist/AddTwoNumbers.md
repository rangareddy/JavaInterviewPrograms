# Add Two Numbers

Two non-empty linked lists represent two non-negative integers. The digits are stored in reverse order (the least significant digit first) and each node holds a single digit. Add the two numbers and return the sum as a linked list in the same format.

## Algorithm Strategy (Digit-by-Digit Addition with a Carry)

1. Both lists start with the least significant digit, which is exactly the order in which school addition works.
2. Walk both lists together with a `carry` (initially 0). In each step add the two current digits (a missing digit counts as 0) and the carry.
3. The new digit is `sum % 10` and the new carry is `sum / 10`; append a node with the digit to the result (built behind a dummy head).
4. Continue while either list has digits left or the carry is not 0, so a final carry creates one more node.

## Example walkthrough

- For `[2, 4, 3]` (342) and `[5, 6, 4]` (465): `2+5=7`, `4+6=10` (digit 0, carry 1), `3+4+1=8`.
- Result: `[7, 0, 8]` (807).

## Complexity Analysis

- **Time Complexity:** `O(max(m, n))` — One step per digit.
- **Space Complexity:** `O(max(m, n))` — The result list.

## Approaches

The same problem can be solved in several ways. The approach marked (Optimized) is the one to use in practice.

### Approach 1: Convert to BigInteger, Add, Rebuild

Convert both lists to `BigInteger` numbers (the lists may have hundreds of digits), add them with the library and write the digits of the sum back into a new list. Short, but converts back and forth.

```java
public static ListNode addTwoNumbersBig(ListNode l1, ListNode l2) {
    BigInteger sum = toNumber(l1).add(toNumber(l2));
    String digits = new StringBuilder(sum.toString()).reverse().toString();
    ListNode dummy = new ListNode(0), tail = dummy;
    for (char c : digits.toCharArray()) { tail.next = new ListNode(c - '0'); tail = tail.next; }
    return dummy.next;
}

private static BigInteger toNumber(ListNode head) {
    StringBuilder sb = new StringBuilder();
    for (ListNode curr = head; curr != null; curr = curr.next) sb.append(curr.val);
    return new BigInteger(sb.reverse().toString());
}
```

- **Time Complexity:** O(n^2) for the conversions
- **Space Complexity:** O(n)

### Approach 2: Recursion with a Carry Parameter

Add the two current digits and the incoming carry, create a node for the digit and let the recursion build the rest of the list from the next digits and the new carry.

```java
public static ListNode addTwoNumbersRecursive(ListNode l1, ListNode l2) {
    return add(l1, l2, 0);
}

private static ListNode add(ListNode a, ListNode b, int carry) {
    if (a == null && b == null && carry == 0) return null;
    int sum = carry + (a == null ? 0 : a.val) + (b == null ? 0 : b.val);
    ListNode node = new ListNode(sum % 10);
    node.next = add(a == null ? null : a.next, b == null ? null : b.next, sum / 10);
    return node;
}
```

- **Time Complexity:** O(max(m, n))
- **Space Complexity:** O(max(m, n)) recursion stack

### Approach 3: Write the Sum into the First List

Reuse the nodes of `l1` for the result (appending a new node only when `l2` is longer or a final carry remains), so almost no new nodes are allocated. The input list `l1` is overwritten.

```java
public static ListNode addTwoNumbersInPlace(ListNode l1, ListNode l2) {
    ListNode head = l1, prev = null;
    int carry = 0;
    while (l1 != null || l2 != null || carry != 0) {
        if (l1 == null) {
            l1 = new ListNode(0);
            if (prev != null) prev.next = l1;
            if (head == null) head = l1;
        }
        int sum = l1.val + (l2 == null ? 0 : l2.val) + carry;
        l1.val = sum % 10;
        carry = sum / 10;
        prev = l1;
        l1 = l1.next;
        if (l2 != null) l2 = l2.next;
    }
    return head;
}
```

- **Time Complexity:** O(max(m, n))
- **Space Complexity:** O(1) besides the extra nodes

### Approach 4: Iterative Addition with a Dummy Head (Optimized)

Add digit by digit with a carry and append the result digits behind a dummy head. This is the version implemented in the repository.

```java
public static ListNode addTwoNumbers(ListNode l1, ListNode l2) {
    ListNode dummy = new ListNode(0), tail = dummy;
    int carry = 0;
    while (l1 != null || l2 != null || carry != 0) {
        int sum = carry;
        if (l1 != null) { sum += l1.val; l1 = l1.next; }
        if (l2 != null) { sum += l2.val; l2 = l2.next; }
        tail.next = new ListNode(sum % 10);
        tail = tail.next;
        carry = sum / 10;
    }
    return dummy.next;
}
```

- **Time Complexity:** O(max(m, n))
- **Space Complexity:** O(max(m, n))

### Comparison

| # | Approach | Time | Space |
|---|----------|------|-------|
| 1 | Convert to BigInteger, Add, Rebuild | O(n^2) for the conversions | O(n) |
| 2 | Recursion with a Carry Parameter | O(max(m, n)) | O(max(m, n)) recursion stack |
| 3 | Write the Sum into the First List | O(max(m, n)) | O(1) besides the extra nodes |
| 4 | Iterative Addition with a Dummy Head (Optimized) | O(max(m, n)) | O(max(m, n)) |

### Optimized Solution

The optimized solution is Approach 4 above, which is also the version implemented in this repository.

## Source Code

Uses the shared helper [`ListNode`](../../src/main/java/com/ranga/linkedlist/ListNode.java) (a small `ListNode` with `of(...)` and `toString(...)` helpers for building and printing test data).

### AddTwoNumbers.java

```java
import com.ranga.linkedlist.ListNode;

public class AddTwoNumbers {

    public static void main(String[] args) {
        int[][][] inputs = {
                {{2, 4, 3}, {5, 6, 4}},
                {{0}, {0}},
                {{9, 9, 9, 9, 9, 9, 9}, {9, 9, 9, 9}},
                {{5}, {5}}
        };
        for (int[][] pair : inputs) {
            ListNode l1 = ListNode.of(pair[0]);
            ListNode l2 = ListNode.of(pair[1]);
            System.out.println("Input  : l1 = " + ListNode.toString(l1) + ", l2 = " + ListNode.toString(l2));
            System.out.println("Output : " + ListNode.toString(addTwoNumbers(l1, l2)));
            System.out.println();
        }
    }

    public static ListNode addTwoNumbers(ListNode l1, ListNode l2) {
        ListNode dummy = new ListNode(0);
        ListNode tail = dummy;
        int carry = 0;
        while (l1 != null || l2 != null || carry != 0) {
            int sum = carry;
            if (l1 != null) {
                sum += l1.val;
                l1 = l1.next;
            }
            if (l2 != null) {
                sum += l2.val;
                l2 = l2.next;
            }
            tail.next = new ListNode(sum % 10);
            tail = tail.next;
            carry = sum / 10;
        }
        return dummy.next;
    }
}
```
