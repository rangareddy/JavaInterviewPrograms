# Happy Number

A number is happy if repeatedly replacing it with the sum of the squares of its digits eventually reaches 1. If the process loops forever in a cycle that does not contain 1, the number is not happy. Return `true` if `n` is happy.

## Algorithm Strategy (Cycle Detection with Floyd's Tortoise and Hare)

1. Define `next(n)` as the sum of the squares of the digits of `n`.
2. The sequence either reaches 1 (and stays there) or enters a cycle, so it is a linked list that may have a loop.
3. Move `slow` one step and `fast` two steps at a time.
4. If `fast` reaches 1, the number is happy; if `slow == fast` before that, a cycle without 1 exists: not happy.

## Example walkthrough

- For 19: `1 + 81 = 82`, `64 + 4 = 68`, `36 + 64 = 100`, `1`.
- Result: `true`. For 2 the sequence reaches the cycle `4 -> 16 -> 37 -> 58 -> 89 -> 145 -> 42 -> 20 -> 4`: `false`.

## Complexity Analysis

- **Time Complexity:** `O(log n)` — The sequence stays small after the first step and has bounded length.
- **Space Complexity:** `O(1)` — Two pointers.

## Approaches

The same problem can be solved in several ways. The approach marked (Optimized) is the one to use in practice.

### Approach 1: HashSet of Seen Numbers

Stop when the sequence reaches 1 or repeats a value.

```java
public static boolean isHappySet(int n) {
    Set<Integer> seen = new HashSet<>();
    while (n != 1 && seen.add(n)) n = next(n);
    return n == 1;
}

private static int next(int n) {
    int sum = 0;
    while (n > 0) { int d = n % 10; sum += d * d; n /= 10; }
    return sum;
}
```

- **Time Complexity:** O(log n)
- **Space Complexity:** O(log n)

### Approach 2: Known Cycle Value

Every unhappy number ends in the cycle containing 4, so stop as soon as 1 or 4 appears (uses the `next` helper from the first approach).

```java
public static boolean isHappyCycleFour(int n) {
    while (n != 1 && n != 4) n = next(n);
    return n == 1;
}
```

- **Time Complexity:** O(log n)
- **Space Complexity:** O(1)

### Approach 3: Digit String Variant

Compute the digit-square sum through `String` characters, with a bounded number of steps.

```java
public static boolean isHappyString(int n) {
    for (int step = 0; step < 1000; step++) {
        if (n == 1) return true;
        int sum = 0;
        for (char c : String.valueOf(n).toCharArray()) sum += (c - '0') * (c - '0');
        n = sum;
    }
    return false;
}
```

- **Time Complexity:** O(log n) per step
- **Space Complexity:** O(log n)

### Approach 4: Floyd Cycle Detection (Optimized)

Tortoise and hare find the cycle without extra memory. This is the version implemented in the repository.

```java
public static boolean isHappy(int n) {
    int slow = n, fast = next(n);
    while (fast != 1 && slow != fast) {
        slow = next(slow);
        fast = next(next(fast));
    }
    return fast == 1;
}
```

- **Time Complexity:** O(log n)
- **Space Complexity:** O(1)

### Comparison

| # | Approach | Time | Space |
|---|----------|------|-------|
| 1 | HashSet of Seen Numbers | O(log n) | O(log n) |
| 2 | Known Cycle Value | O(log n) | O(1) |
| 3 | Digit String Variant | O(log n) per step | O(log n) |
| 4 | Floyd Cycle Detection (Optimized) | O(log n) | O(1) |

### Optimized Solution

The optimized solution is Approach 4 above, which is also the version implemented in this repository.

## Source Code

### HappyNumber.java

```java
public class HappyNumber {

    public static void main(String[] args) {
        int[] inputs = {19, 2, 1, 7, 4, 100};
        for (int n : inputs) {
            System.out.println("Input  : n = " + n);
            System.out.println("Output : " + isHappy(n));
            System.out.println();
        }
    }

    public static boolean isHappy(int n) {
        int slow = n;
        int fast = next(n);
        while (fast != 1 && slow != fast) {
            slow = next(slow);
            fast = next(next(fast));
        }
        return fast == 1;
    }

    private static int next(int n) {
        int sum = 0;
        while (n > 0) {
            int digit = n % 10;
            sum += digit * digit;
            n /= 10;
        }
        return sum;
    }
}
```
