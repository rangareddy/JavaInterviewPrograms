# Swap Nodes in Pairs

Given a linked list, swap every two adjacent nodes and return its head. Solve it by changing the node links, not the values inside the nodes.

## Algorithm Strategy (Dummy Head and Pairwise Rewiring)

1. Put a `dummy` node before the head and let `prev` point to it.
2. While `prev.next` and `prev.next.next` both exist, name them `first` and `second`.
3. Rewire: `first.next = second.next`, `second.next = first`, `prev.next = second`.
4. Move `prev` to `first` (now the second node of the pair) and repeat. Return `dummy.next`.

## Example walkthrough

- For `1 -> 2 -> 3 -> 4`: the pair (1, 2) becomes 2 -> 1, then (3, 4) becomes 4 -> 3.
- Result: `2 -> 1 -> 4 -> 3`.

## Complexity Analysis

- **Time Complexity:** `O(n)` — One pass.
- **Space Complexity:** `O(1)` — Only a few pointers.

## Approaches

The same problem can be solved in several ways. The approach marked (Optimized) is the one to use in practice.

### Approach 1: Recursion

Swap the first pair and recurse on the rest.

```java
public static ListNode recursive(ListNode head) {
    if (head == null || head.next == null) return head;
    ListNode second = head.next;
    head.next = recursive(second.next);
    second.next = head;
    return second;
}
```

- **Time Complexity:** O(n)
- **Space Complexity:** O(n)

### Approach 2: Iterative with a Dummy Head (Optimized)

Rewire each pair in place. This is the version implemented in the repository.

```java
public static ListNode iterative(ListNode head) {
    ListNode dummy = new ListNode(0); dummy.next = head; ListNode prev = dummy;
    while (prev.next != null && prev.next.next != null) {
        ListNode a = prev.next, b = a.next; a.next = b.next; b.next = a; prev.next = b; prev = a;
    }
    return dummy.next;
}
```

- **Time Complexity:** O(n)
- **Space Complexity:** O(1)

### Comparison

| # | Approach | Time | Space |
|---|----------|------|-------|
| 1 | Recursion | O(n) | O(n) |
| 2 | Iterative with a Dummy Head (Optimized) | O(n) | O(1) |

## Source Code

### SwapNodesInPairs.java

```java
public class SwapNodesInPairs {

    public static void main(String[] args) {
        int[][] inputs = {{1, 2, 3, 4}, {1, 2, 3}, {1}, {}};
        for (int[] values : inputs) {
            ListNode head = ListNode.of(values);
            System.out.println("Input  : head = " + ListNode.toString(head));
            System.out.println("Output : " + ListNode.toString(swapPairs(head)));
            System.out.println();
        }
    }

    public static ListNode swapPairs(ListNode head) {
        ListNode dummy = new ListNode(0);
        dummy.next = head;
        ListNode prev = dummy;
        while (prev.next != null && prev.next.next != null) {
            ListNode first = prev.next;
            ListNode second = first.next;
            first.next = second.next;
            second.next = first;
            prev.next = second;
            prev = first;
        }
        return dummy.next;
    }
}
```

## Solution

The optimized solution is Approach 2 above, which is also the version implemented in this repository.

The idea: for each pair, three pointer assignments swap the nodes, and `prev` always points at the node before the pair.

**Input:** `1 -> 2 -> 3 -> 4`.

| Step | `prev` | `first`, `second` | List afterwards |
|------|--------|-------------------|-----------------|
| 1 | dummy | 1, 2 | 2 -> 1 -> 3 -> 4 |
| 2 | 1 | 3, 4 | 2 -> 1 -> 4 -> 3 |

**Output:** `2 -> 1 -> 4 -> 3`.
