# Number of Visible People in a Queue

There are `n` people in a queue, numbered from left to right, with distinct heights `heights[i]`. A person can see the person to their right if everyone strictly between them is shorter than both. For each person, return the number of people they can see to their right.

## Algorithm Strategy (Monotonic Stack from the Right)

1. Scan from right to left, keeping a stack of heights that is decreasing from the bottom to the top (the visible candidates to the right of the current person).
2. While the stack top is shorter than the current person, pop it and count it: the current person sees that person.
3. If the stack is still not empty, the person at the top is taller and also visible, so count one more.
4. Push the current person and record the count.

## Example walkthrough

- For `[10, 6, 8, 5, 11, 9]`: the first person sees 6, 8 and 11, because 5 is hidden behind 8 and 9 behind 11.
- Result: `[3, 1, 2, 1, 1, 0]`.

## Complexity Analysis

- **Time Complexity:** `O(n)` — Each person is pushed and popped once.
- **Space Complexity:** `O(n)` — The stack.

## Approaches

The same problem can be solved in several ways. The approach marked (Optimized) is the one to use in practice.

### Approach 1: Brute Force

For each person scan to the right keeping the tallest seen so far; a person is visible if taller than all the people between.

```java
public static int[] bruteForce(int[] h) {
    int n = h.length; int[] a = new int[n];
    for (int i = 0; i < n; i++) { int maxBetween = 0; for (int j = i + 1; j < n; j++) { if (maxBetween < Math.min(h[i], h[j])) a[i]++; maxBetween = Math.max(maxBetween, h[j]); if (maxBetween > h[i]) break; } }
    return a;
}
```

- **Time Complexity:** O(n^2)
- **Space Complexity:** O(1)

### Approach 2: Monotonic Stack from the Right (Optimized)

Pop the shorter people the current person can see, then count one more taller person if there is one. This is the version implemented in the repository.

```java
public static int[] stack(int[] h) {
    int n = h.length; int[] a = new int[n]; java.util.Deque<Integer> st = new java.util.ArrayDeque<>();
    for (int i = n - 1; i >= 0; i--) { int seen = 0; while (!st.isEmpty() && st.peek() < h[i]) { st.pop(); seen++; } if (!st.isEmpty()) seen++; a[i] = seen; st.push(h[i]); }
    return a;
}
```

- **Time Complexity:** O(n)
- **Space Complexity:** O(n)

### Comparison

| # | Approach | Time | Space |
|---|----------|------|-------|
| 1 | Brute Force | O(n^2) | O(1) |
| 2 | Monotonic Stack from the Right (Optimized) | O(n) | O(n) |

## Source Code

### NumberOfVisiblePeopleInAQueue.java

```java
import java.util.ArrayDeque;
import java.util.Arrays;
import java.util.Deque;

public class NumberOfVisiblePeopleInAQueue {

    public static void main(String[] args) {
        int[][] inputs = {{10, 6, 8, 5, 11, 9}, {5, 1, 2, 3, 10}, {1}, {4, 3, 2, 1}};
        for (int[] heights : inputs) {
            System.out.println("Input  : heights = " + Arrays.toString(heights));
            System.out.println("Output : " + Arrays.toString(canSeePersonsCount(heights)));
            System.out.println();
        }
    }

    public static int[] canSeePersonsCount(int[] heights) {
        if (heights == null) {
            return new int[0];
        }
        int n = heights.length;
        int[] answer = new int[n];
        Deque<Integer> stack = new ArrayDeque<>();
        for (int i = n - 1; i >= 0; i--) {
            int seen = 0;
            while (!stack.isEmpty() && stack.peek() < heights[i]) {
                stack.pop();
                seen++;
            }
            if (!stack.isEmpty()) {
                seen++;
            }
            answer[i] = seen;
            stack.push(heights[i]);
        }
        return answer;
    }
}
```

## Solution

The optimized solution is Approach 2 above, which is also the version implemented in this repository.

The idea: people to the right who are hidden by someone taller can never be seen by anyone to the left of that taller person either, so a decreasing stack holds exactly the people that remain visible. A new person on the left sees everyone they pop, plus the first taller person left on the stack.

**Input:** `heights = [10, 6, 8, 5, 11, 9]`, scanned from the right.

| Person | Stack before | Pops (seen) | Stack top left | Count |
|--------|--------------|-------------|----------------|-------|
| 9 | [] | 0 | none | 0 |
| 11 | [9] | 9 | none | 1 |
| 5 | [11] | 0 | 11 | 1 |
| 8 | [11, 5] | 5 | 11 | 2 |
| 6 | [11, 8] | 0 | 8 | 1 |
| 10 | [11, 8, 6] | 6, 8 | 11 | 3 |

**Output:** `[3, 1, 2, 1, 1, 0]`.
