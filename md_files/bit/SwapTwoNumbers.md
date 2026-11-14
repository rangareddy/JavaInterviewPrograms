# Swap Two Numbers Without a Temporary Variable

Swap the values of two integer variables without using a third temporary variable. (Java passes arguments by value, so a helper method cannot swap caller variables; the swap is shown on array elements and on local variables.)

## Algorithm Strategy (Arithmetic and XOR Swaps)

1. Addition and subtraction: `a = a + b; b = a - b; a = a - b`. After the first step `a` holds the sum; subtracting `b` recovers the old `a`, and subtracting that from the sum recovers the old `b`. The sum can overflow, but with wrap-around arithmetic the result is still correct.
2. XOR: `a = a ^ b; b = a ^ b; a = a ^ b`. XOR-ing a value with the same value twice gives the original value back.
3. Both tricks fail if the two positions are the same memory location (for example swapping `arr[i]` with `arr[i]`) because the value becomes 0; guard against `i == j`.
4. In production code a temporary variable is clearer and just as fast.

## Example walkthrough

- With `a = 5, b = 9`: sum step `a = 14`, `b = 14 - 9 = 5`, `a = 14 - 5 = 9`.
- With XOR: `a = 5 ^ 9 = 12`, `b = 12 ^ 9 = 5`, `a = 12 ^ 5 = 9`.

## Complexity Analysis

- **Time Complexity:** `O(1)` — Three operations.
- **Space Complexity:** `O(1)` — No extra variable.

## Approaches

The same problem can be solved in several ways. The approach marked (Optimized) is the one to use in practice.

### Approach 1: Temporary Variable

The clear and idiomatic way.

```java
public static void swapTemp(int[] values, int i, int j) {
    int temp = values[i];
    values[i] = values[j];
    values[j] = temp;
}
```

- **Time Complexity:** O(1)
- **Space Complexity:** O(1)

### Approach 2: Multiply and Divide (Fragile)

Works only for non-zero values and can overflow; shown as an example of what not to do.

```java
public static void swapMultiply(long[] values, int i, int j) {
    // requires both values to be non-zero and the product to fit in a long
    if (i == j) return;
    values[i] = values[i] * values[j];
    values[j] = values[i] / values[j];
    values[i] = values[i] / values[j];
}
```

- **Time Complexity:** O(1)
- **Space Complexity:** O(1)

### Approach 3: Addition and Subtraction

Uses the sum as temporary storage. Wrap-around on overflow still gives the right result.

```java
public static void swapArithmetic(int[] values, int i, int j) {
    if (i == j) return;
    values[i] = values[i] + values[j];
    values[j] = values[i] - values[j];
    values[i] = values[i] - values[j];
}
```

- **Time Complexity:** O(1)
- **Space Complexity:** O(1)

### Approach 4: XOR Swap (Optimized)

XOR-ing twice with the same value restores it. This is the second version implemented in the repository.

```java
public static void swapXor(int[] values, int i, int j) {
    if (i == j) return;
    values[i] = values[i] ^ values[j];
    values[j] = values[i] ^ values[j];
    values[i] = values[i] ^ values[j];
}
```

- **Time Complexity:** O(1)
- **Space Complexity:** O(1)

### Comparison

| # | Approach | Time | Space |
|---|----------|------|-------|
| 1 | Temporary Variable | O(1) | O(1) |
| 2 | Multiply and Divide (Fragile) | O(1) | O(1) |
| 3 | Addition and Subtraction | O(1) | O(1) |
| 4 | XOR Swap | O(1) | O(1) |

## Source Code

### SwapTwoNumbers.java

```java
public class SwapTwoNumbers {

    public static void main(String[] args) {
        int[][] inputs = {{5, 9}, {-3, 7}, {0, 4}, {Integer.MAX_VALUE, 1}};
        for (int[] pair : inputs) {
            int[] byArithmetic = pair.clone();
            swapArithmetic(byArithmetic, 0, 1);
            int[] byXor = pair.clone();
            swapXor(byXor, 0, 1);
            System.out.println("Input  : a = " + pair[0] + ", b = " + pair[1]);
            System.out.println("Output : arithmetic -> a = " + byArithmetic[0] + ", b = " + byArithmetic[1]
                    + " | xor -> a = " + byXor[0] + ", b = " + byXor[1]);
            System.out.println();
        }
    }

    public static void swapArithmetic(int[] values, int i, int j) {
        if (i == j) {
            return;
        }
        values[i] = values[i] + values[j];
        values[j] = values[i] - values[j];
        values[i] = values[i] - values[j];
    }

    public static void swapXor(int[] values, int i, int j) {
        if (i == j) {
            return;
        }
        values[i] = values[i] ^ values[j];
        values[j] = values[i] ^ values[j];
        values[i] = values[i] ^ values[j];
    }
}
```

## Solution

The optimized solution is Approach 4 above (the XOR swap), which is also the version implemented in this repository next to the addition/subtraction swap of Approach 3.

The idea: XOR (`^`) gives the bits where two numbers differ, and XOR-ing the same value twice cancels it out. Three XORs on the two array slots therefore exchange the values without a temporary variable and without any chance of arithmetic overflow.

**Input:** `a = 5`, `b = 9` (stored as `values[0] = 5`, `values[1] = 9`). In binary, `5 = 0101` and `9 = 1001`.

| Step | Statement | Calculation | `a` afterwards | `b` afterwards |
|------|-----------|-------------|----------------|----------------|
| start | | | `0101` (5) | `1001` (9) |
| 1 | `a = a ^ b` | `0101 ^ 1001 = 1100` | `1100` (12) | `1001` (9) |
| 2 | `b = a ^ b` | `1100 ^ 1001 = 0101` | `1100` (12) | `0101` (5) |
| 3 | `a = a ^ b` | `1100 ^ 0101 = 1001` | `1001` (9) | `0101` (5) |

Now `a = 9` and `b = 5`: the values have been exchanged.

```text
a = 0101        step 1:  a = a ^ b  ->  1100   (a now holds "the difference" of a and b)
b = 1001        step 2:  b = a ^ b  ->  0101   ((a ^ b) ^ b  =  the old a)
                step 3:  a = a ^ b  ->  1001   ((a ^ b) ^ old a  =  the old b)
```

**Why it is safe in this class.** `swapXor` returns at once when the two indexes are the same (`i == j`): XOR-ing a slot with itself would turn it into `0` and destroy the value. XOR never overflows, so `Integer.MAX_VALUE` and `1` swap correctly (`a = 1, b = 2147483647`), as do negative numbers (`-3` and `7` give `7` and `-3`) and zero (`0` and `4` give `4` and `0`). The arithmetic swap (`a + b`) gives the same results here only because Java integers wrap around on overflow. Both methods expect a non-`null` array and valid indexes.
