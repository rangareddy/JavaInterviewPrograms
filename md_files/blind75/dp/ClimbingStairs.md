# Climbing Stairs

You are climbing a staircase with `n` steps. Each time you can climb 1 or 2 steps. In how many distinct ways can you reach the top?

## Algorithm Strategy (Dynamic Programming (Fibonacci Recurrence))

1. To stand on step `i`, you came either from step `i - 1` (one step) or from step `i - 2` (two steps), so `ways(i) = ways(i - 1) + ways(i - 2)`.
2. Base cases: there is 1 way to stay at the ground (`ways(0) = 1`) and 1 way to reach step 1.
3. Only the last two values are needed, so keep two variables instead of a table.
4. Roll them forward `n` times; the last value is the answer.

## Example walkthrough

- For `n = 5`: ways are 1, 1, 2, 3, 5, 8 for steps 0 to 5.
- Result: `8`.

## Complexity Analysis

- **Time Complexity:** `O(n)` — A single loop.
- **Space Complexity:** `O(1)` — Two variables.

## Source Code

### ClimbingStairs.java

```java
public class ClimbingStairs {

    public static void main(String[] args) {
        int[] inputs = {2, 3, 5, 1};
        for (int n : inputs) {
            System.out.println("Input  : n = " + n);
            System.out.println("Output : " + climbStairs(n));
            System.out.println();
        }
    }

    public static int climbStairs(int n) {
        if (n <= 1) {
            return 1;
        }

        int previous = 1;       // ways to reach step i - 2
        int current = 1;        // ways to reach step i - 1
        for (int i = 2; i <= n; i++) {
            int next = previous + current;
            previous = current;
            current = next;
        }
        return current;
    }
}
```
