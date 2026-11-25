# Number of Steps to Reduce a Number in Binary Representation to One

Given the binary representation of an integer as a string `s`, return the number of steps to reduce it to `1`. If the current number is even, divide it by `2`; if it is odd, add `1` to it. It is guaranteed that you can always reach `1`.

## Algorithm Strategy (Process Bits from the Right with a Carry)

1. Dividing an even number by 2 removes the last bit, which is `0`. That is one step.
2. An odd number needs one addition (which turns the trailing `1`s into `0`s and creates a carry) and then a division: two steps.
3. Scan from the last bit to the second bit, adding any pending carry to each bit.
4. A bit value of `1` costs 2 steps and sets the carry; `0` or `2` costs 1 step, and the carry stays if the value was 2. After the scan, a leftover carry costs one more step.

## Example walkthrough

- For `1101` (13): bit 1 costs 2 (carry 1); the next bit 0 plus carry is 1, cost 2 (carry 1); the next bit 1 plus carry is 2, cost 1 (carry 1).
- Total 5, plus 1 for the final carry.
- Result: `6`.

## Complexity Analysis

- **Time Complexity:** `O(n)` — One pass over the bits.
- **Space Complexity:** `O(1)` — A step counter and a carry.

## Approaches

The same problem can be solved in several ways. The approach marked (Optimized) is the one to use in practice.

### Approach 1: BigInteger Simulation

Convert to a `BigInteger` and apply the rules literally. Correct for any length, but each step costs time proportional to the number size.

```java
public static int big(String s) { java.math.BigInteger n = new java.math.BigInteger(s, 2), one = java.math.BigInteger.ONE; int steps = 0; while (!n.equals(one)) { n = n.testBit(0) ? n.add(one) : n.shiftRight(1); steps++; } return steps; }
```

- **Time Complexity:** O(n^2)
- **Space Complexity:** O(n)

### Approach 2: Right-to-Left Scan with Carry (Optimized)

Never build the number; walk the bits once and keep a carry. This is the version implemented in the repository.

```java
public static int carry(String s) { int steps = 0, c = 0; for (int i = s.length() - 1; i > 0; i--) { int b = (s.charAt(i) - '0') + c; if (b == 1) { steps += 2; c = 1; } else { steps += 1; c = b == 2 ? 1 : 0; } } return steps + c; }
```

- **Time Complexity:** O(n)
- **Space Complexity:** O(1)

### Comparison

| # | Approach | Time | Space |
|---|----------|------|-------|
| 1 | BigInteger Simulation | O(n^2) | O(n) |
| 2 | Right-to-Left Scan with Carry (Optimized) | O(n) | O(1) |

## Source Code

### NumberOfStepsToReduceABinaryNumberToOne.java

```java
public class NumberOfStepsToReduceABinaryNumberToOne {

    public static void main(String[] args) {
        String[] inputs = {"1101", "10", "1", "1111", "100000"};
        for (String s : inputs) {
            System.out.println("Input  : s = \"" + s + "\"");
            System.out.println("Output : " + numSteps(s));
            System.out.println();
        }
    }

    public static int numSteps(String s) {
        int steps = 0;
        int carry = 0;
        for (int i = s.length() - 1; i > 0; i--) {
            int bit = (s.charAt(i) - '0') + carry;
            if (bit == 1) {
                steps += 2;
                carry = 1;
            } else {
                steps += 1;
                carry = bit == 2 ? 1 : 0;
            }
        }
        return steps + carry;
    }
}
```

## Solution

The optimized solution is Approach 2 above, which is also the version implemented in this repository.

The idea: adding 1 to an odd binary number flips its trailing run of `1`s to `0`s and carries into the next bit. Instead of changing the string, remember that carry and add it to the next bit as the scan moves left.

**Input:** `s = "1101"`.

| Bit (right to left) | Bit + carry | Steps added | New carry | Total steps |
|---------------------|-------------|-------------|-----------|-------------|
| 1 | 1 | 2 | 1 | 2 |
| 0 | 1 | 2 | 1 | 4 |
| 1 | 2 | 1 | 1 | 5 |

The leading `1` is left with a carry, which makes the value 2, so one final division is needed: `5 + 1 = 6`.

**Output:** `6`.
