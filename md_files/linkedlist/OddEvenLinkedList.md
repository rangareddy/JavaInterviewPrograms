# Odd Even Linked List

Given the head of a singly linked list, group all the nodes with odd indexes together followed by the nodes with even indexes (the first node has index 1), and return the reordered list. The relative order inside each group is kept. Use `O(1)` extra space and `O(n)` time.

## Algorithm Strategy (Odd and Even Chains)

1. Keep `odd = head` and `even = head.next`, and remember `evenHead = even`.
2. While `even` and `even.next` exist: link `odd.next = even.next` and advance `odd`, then link `even.next = odd.next` and advance `even`.
3. After the loop the odd chain ends at `odd`: attach `odd.next = evenHead`.
4. Return `head`.

## Example walkthrough

- For `1 -> 2 -> 3 -> 4 -> 5`: the odd chain is 1, 3, 5 and the even chain is 2, 4.
- Result: `1 -> 3 -> 5 -> 2 -> 4`.

## Complexity Analysis

- **Time Complexity:** `O(n)` — One pass.
- **Space Complexity:** `O(1)` — Pointers only.

## Approaches

The same problem can be solved in several ways. The approach marked (Optimized) is the one to use in practice.

### Approach 1: Collect Values and Rebuild

Read the values into two lists and write them back. Uses extra space.

```java
public static ListNode viaLists(ListNode head) {
    java.util.List<Integer> o = new java.util.ArrayList<>(), e = new java.util.ArrayList<>(); int i = 1;
    for (ListNode n = head; n != null; n = n.next, i++) (i % 2 == 1 ? o : e).add(n.val);
    o.addAll(e); ListNode dummy = new ListNode(0), t = dummy; for (int v : o) { t.next = new ListNode(v); t = t.next; }
    return dummy.next;
}
```

- **Time Complexity:** O(n)
- **Space Complexity:** O(n)

### Approach 2: Two Interleaved Chains (Optimized)

Relink the nodes into an odd chain and an even chain, then join them. This is the version implemented in the repository.

```java
public static ListNode chains(ListNode head) {
    if (head == null || head.next == null) return head;
    ListNode odd = head, even = head.next, evenHead = even;
    while (even != null && even.next != null) { odd.next = even.next; odd = odd.next; even.next = odd.next; even = even.next; }
    odd.next = evenHead; return head;
}
```

- **Time Complexity:** O(n)
- **Space Complexity:** O(1)

### Comparison

| # | Approach | Time | Space |
|---|----------|------|-------|
| 1 | Collect Values and Rebuild | O(n) | O(n) |
| 2 | Two Interleaved Chains (Optimized) | O(n) | O(1) |

## Source Code

### OddEvenLinkedList.java

```java
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
```

## Solution

The optimized solution is Approach 2 above, which is also the version implemented in this repository.

The idea: re-link the nodes into two chains in one pass, then hang the even chain after the odd one.

**Input:** `1 -> 2 -> 3 -> 4 -> 5`.

| Step | `odd` | `even` | Odd chain | Even chain |
|------|-------|--------|-----------|------------|
| start | 1 | 2 | 1 | 2 |
| 1 | 3 | 4 | 1 -> 3 | 2 -> 4 |
| 2 | 5 | null | 1 -> 3 -> 5 | 2 -> 4 |

Joining: `1 -> 3 -> 5 -> 2 -> 4`.

**Output:** `1 -> 3 -> 5 -> 2 -> 4`.
