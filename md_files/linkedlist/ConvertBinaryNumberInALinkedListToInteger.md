# Convert Binary Number in a Linked List to Integer

Given the head of a singly linked list in which every node holds 0 or 1 and the list represents a binary number (most significant bit first), return its decimal value.

## Algorithm Strategy (Shift Left and Add the Bit)

1. Start with `value = 0`.
2. For each node, shift the value left by one bit and add the node's bit: `value = (value << 1) | node.val`.
3. Move to the next node.
4. Return the value at the end of the list.

## Example walkthrough

- For `1 -> 0 -> 1`: `0 -> 1`, then `10`, then `101`.
- Result: `5`.

## Complexity Analysis

- **Time Complexity:** `O(n)` — One pass.
- **Space Complexity:** `O(1)` — One integer.

## Approaches

The same problem can be solved in several ways. The approach marked (Optimized) is the one to use in practice.

### Approach 1: Collect Bits then Compute Powers

Store the bits in a list and add `bit * 2^position`.

```java
public static int powers(ListNode h) { java.util.List<Integer> l = new java.util.ArrayList<>(); for (; h != null; h = h.next) l.add(h.val); int v = 0; for (int i = 0; i < l.size(); i++) v += l.get(i) * (1 << (l.size() - 1 - i)); return v; }
```

- **Time Complexity:** O(n)
- **Space Complexity:** O(n)

### Approach 2: Shift and Add (Optimized)

Build the number while walking the list. This is the version implemented in the repository.

```java
public static int shift(ListNode h) { int v = 0; for (; h != null; h = h.next) v = (v << 1) | h.val; return v; }
```

- **Time Complexity:** O(n)
- **Space Complexity:** O(1)

### Comparison

| # | Approach | Time | Space |
|---|----------|------|-------|
| 1 | Collect Bits then Compute Powers | O(n) | O(n) |
| 2 | Shift and Add (Optimized) | O(n) | O(1) |

## Source Code

### ConvertBinaryNumberInALinkedListToInteger.java

```java
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
```

## Solution

The optimized solution is Approach 2 above, which is also the version implemented in this repository.

The idea: reading a binary number from the most significant bit is the same as repeatedly doubling what has been read so far and adding the next bit. This is Horner's rule, so no powers of two and no list are needed, and the length of the list does not have to be known in advance.

**Input:** `head = 1 -> 0 -> 1`.

| Node | `value` before | After `(value << 1) | bit` |
|------|----------------|-----------------------------|
| 1 | 0 | 1 |
| 0 | 1 | 2 |
| 1 | 2 | 5 |

**Output:** `5`.
