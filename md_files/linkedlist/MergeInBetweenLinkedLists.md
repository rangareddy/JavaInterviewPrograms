# Merge In Between Linked Lists

You are given two linked lists `list1` and `list2`, and two integers `a` and `b` (`1 <= a <= b < size of list1 - 1`). Remove the nodes of `list1` from index `a` to index `b` inclusive, and put `list2` in their place.

## Algorithm Strategy (Find Two Anchors and Splice)

1. Walk to the node at index `a - 1`; call it `before`.
2. Keep walking to the node at index `b + 1`; call it `after`.
3. Point `before.next` to the head of `list2`.
4. Find the tail of `list2` and point it to `after`.

## Example walkthrough

- For `list1 = [10, 1, 13, 6, 9, 5]`, `a = 3`, `b = 4`: `before` is 13 (index 2), `after` is 5 (index 5).
- Link 13 to `1000000 -> 1000001 -> 1000002`, then its tail to 5.
- Result: `[10, 1, 13, 1000000, 1000001, 1000002, 5]`.

## Complexity Analysis

- **Time Complexity:** `O(n + m)` — One walk through `list1` and one through `list2`.
- **Space Complexity:** `O(1)` — Only the existing nodes are re-linked.

## Approaches

The same problem can be solved in several ways. The approach marked (Optimized) is the one to use in practice.

### Approach 1: Rebuild Through Arrays

Copy both lists into arrays, concatenate the pieces and rebuild a list. Easy to reason about, but allocates new nodes.

```java
public static ListNode rebuild(int[] l1, int a, int b, int[] l2) { java.util.List<Integer> out = new java.util.ArrayList<>(); for (int i = 0; i < a; i++) out.add(l1[i]); for (int v : l2) out.add(v); for (int i = b + 1; i < l1.length; i++) out.add(l1[i]); int[] arr = new int[out.size()]; for (int i = 0; i < arr.length; i++) arr[i] = out.get(i); return ListNode.of(arr); }
```

- **Time Complexity:** O(n + m)
- **Space Complexity:** O(n + m)

### Approach 2: Splice with Two Anchors (Optimized)

Walk to the node before the cut and the node after it, then re-link in place. This is the version implemented in the repository.

```java
public static ListNode splice(ListNode l1, int a, int b, ListNode l2) { ListNode before = l1; for (int i = 0; i < a - 1; i++) before = before.next; ListNode after = before; for (int i = a - 1; i <= b; i++) after = after.next; before.next = l2; ListNode t = l2; while (t.next != null) t = t.next; t.next = after; return l1; }
```

- **Time Complexity:** O(n + m)
- **Space Complexity:** O(1)

### Comparison

| # | Approach | Time | Space |
|---|----------|------|-------|
| 1 | Rebuild Through Arrays | O(n + m) | O(n + m) |
| 2 | Splice with Two Anchors (Optimized) | O(n + m) | O(1) |

## Source Code

### MergeInBetweenLinkedLists.java

```java
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
```

## Solution

The optimized solution is Approach 2 above, which is also the version implemented in this repository.

The idea: only two existing nodes matter, the last kept node before the cut and the first kept node after it. Everything between them is dropped by re-pointing `before.next`, and `list2` is attached with its tail pointing to `after`.

**Input:** `list1 = [10, 1, 13, 6, 9, 5]`, `a = 3`, `b = 4`, `list2 = [1000000, 1000001, 1000002]`.

| Step | State |
|------|-------|
| `before` (index 2) | 13 |
| `after` (index 5) | 5 |
| `before.next = list2` | 10 -> 1 -> 13 -> 1000000 -> 1000001 -> 1000002 |
| `tail.next = after` | ... -> 1000002 -> 5 |

Nodes 6 and 9 are no longer reachable.

**Output:** `[10, 1, 13, 1000000, 1000001, 1000002, 5]`.
