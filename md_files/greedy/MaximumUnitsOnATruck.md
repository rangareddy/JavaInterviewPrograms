# Maximum Units on a Truck

You are assigned to put some amount of boxes onto one truck. `boxTypes[i] = [numberOfBoxes, unitsPerBox]` means there are `numberOfBoxes` boxes of type `i`, each containing `unitsPerBox` units. The truck can hold at most `truckSize` boxes. Return the maximum total number of units that can be put on the truck.

## Algorithm Strategy (Sort Box Types by Units per Box)

1. Every box takes one slot, so the best use of each slot is the box with the most units.
2. Sort the box types by `unitsPerBox` in descending order.
3. Take as many boxes as possible from each type until the truck is full.
4. Add `taken * unitsPerBox` to the total.

## Example walkthrough

- For `[[1, 3], [2, 2], [3, 1]]` and `truckSize = 4`: take 1 box of 3 units, 2 boxes of 2 units, and 1 box of 1 unit.
- Total: `3 + 4 + 1 = 8`.
- Result: `8`.

## Complexity Analysis

- **Time Complexity:** `O(m log m)` — Sorting `m` box types.
- **Space Complexity:** `O(m)` — Sorted copy of the input.

## Approaches

The same problem can be solved in several ways. The approach marked (Optimized) is the one to use in practice.

### Approach 1: Expand Every Box and Sort

List every single box by its units, sort descending and take the first `truckSize`. Simple, but memory grows with the number of boxes.

```java
public static int expand(int[][] types, int size) { java.util.List<Integer> all = new java.util.ArrayList<>(); for (int[] t : types) for (int i = 0; i < t[0]; i++) all.add(t[1]); all.sort(java.util.Collections.reverseOrder()); int s = 0; for (int i = 0; i < Math.min(size, all.size()); i++) s += all.get(i); return s; }
```

- **Time Complexity:** O(B log B)
- **Space Complexity:** O(B)

### Approach 2: Sort Types and Take Greedily (Optimized)

Sort only the box types and take what fits. This is the version implemented in the repository.

```java
public static int types(int[][] in, int size) { int[][] a = in.clone(); java.util.Arrays.sort(a, (x, y) -> Integer.compare(y[1], x[1])); int u = 0, rem = size; for (int[] t : a) { if (rem == 0) break; int take = Math.min(rem, t[0]); u += take * t[1]; rem -= take; } return u; }
```

- **Time Complexity:** O(m log m)
- **Space Complexity:** O(m)

### Comparison

| # | Approach | Time | Space |
|---|----------|------|-------|
| 1 | Expand Every Box and Sort | O(B log B) | O(B) |
| 2 | Sort Types and Take Greedily (Optimized) | O(m log m) | O(m) |

## Source Code

### MaximumUnitsOnATruck.java

```java
import java.util.Arrays;

public class MaximumUnitsOnATruck {

    public static void main(String[] args) {
        int[][][] inputs = {{{1, 3}, {2, 2}, {3, 1}}, {{5, 10}, {2, 5}, {4, 7}, {3, 9}}, {{1, 1}}, {}};
        int[] sizes = {4, 10, 5, 3};
        for (int i = 0; i < inputs.length; i++) {
            System.out.println("Input  : boxTypes = " + Arrays.deepToString(inputs[i]) + ", truckSize = " + sizes[i]);
            System.out.println("Output : " + maximumUnits(inputs[i], sizes[i]));
            System.out.println();
        }
    }

    public static int maximumUnits(int[][] boxTypes, int truckSize) {
        if (boxTypes == null) {
            return 0;
        }
        int[][] sorted = boxTypes.clone();
        Arrays.sort(sorted, (a, b) -> Integer.compare(b[1], a[1]));
        int units = 0;
        int remaining = truckSize;
        for (int[] type : sorted) {
            if (remaining == 0) {
                break;
            }
            int take = Math.min(remaining, type[0]);
            units += take * type[1];
            remaining -= take;
        }
        return units;
    }
}
```

## Solution

The optimized solution is Approach 2 above, which is also the version implemented in this repository.

The idea: every box occupies exactly one slot, so units per box is the only thing that distinguishes boxes. Taking the richest boxes first is optimal by an exchange argument: swapping any chosen box for a richer unused one never reduces the total.

**Input:** `boxTypes = [[1, 3], [2, 2], [3, 1]]`, `truckSize = 4`.

| Type (boxes, units) | Taken | Units added | Space left |
|---------------------|-------|-------------|------------|
| (1, 3) | 1 | 3 | 3 |
| (2, 2) | 2 | 4 | 1 |
| (3, 1) | 1 | 1 | 0 |

**Output:** `8`.
