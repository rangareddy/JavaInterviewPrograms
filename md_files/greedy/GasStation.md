# Gas Station

There are `n` gas stations on a circular route. Station `i` offers `gas[i]` units of fuel and driving to the next station costs `cost[i]`. Starting with an empty tank, return the index of the station from which you can complete the circuit once clockwise, or `-1` if it is impossible. If a solution exists it is unique.

## Algorithm Strategy (Single Pass Greedy)

1. If the total gas is less than the total cost, no start works: return `-1`.
2. Otherwise a valid start exists. Scan the stations while keeping the `tank` balance since the candidate start.
3. If the tank drops below zero at station `i`, none of the stations between the candidate and `i` can be the start (each would have arrived with at least as much fuel as the candidate). Make `i + 1` the new candidate and reset the tank.
4. The final candidate is the answer.

## Example walkthrough

- For `gas = [1, 2, 3, 4, 5]` and `cost = [3, 4, 5, 1, 2]`: the tank is negative at stations 0, 1 and 2, so the candidate moves to 3; from 3 the balance stays non-negative.
- Result: `3`.

## Complexity Analysis

- **Time Complexity:** `O(n)` — One pass.
- **Space Complexity:** `O(1)` — A few variables.

## Approaches

The same problem can be solved in several ways. The approach marked (Optimized) is the one to use in practice.

### Approach 1: Brute Force Simulation

Try every station as the start and simulate the whole circuit.

```java
public static int canCompleteCircuitBrute(int[] gas, int[] cost) {
    int n = gas.length;
    for (int start = 0; start < n; start++) {
        int tank = 0;
        boolean ok = true;
        for (int k = 0; k < n; k++) {
            int i = (start + k) % n;
            tank += gas[i] - cost[i];
            if (tank < 0) { ok = false; break; }
        }
        if (ok) return start;
    }
    return -1;
}
```

- **Time Complexity:** O(n^2)
- **Space Complexity:** O(1)

### Approach 2: Skip Failed Starts

When the trip from `start` fails at station `i`, no station between them can work either, so jump the start to `i + 1`.

```java
public static int canCompleteCircuitSkip(int[] gas, int[] cost) {
    int n = gas.length, start = 0;
    while (start < n) {
        int tank = 0, k = 0;
        while (k < n) {
            int i = (start + k) % n;
            tank += gas[i] - cost[i];
            if (tank < 0) break;
            k++;
        }
        if (k == n) return start;
        start += k + 1;
    }
    return -1;
}
```

- **Time Complexity:** O(n)
- **Space Complexity:** O(1)

### Approach 3: Prefix Sum Minimum

Start right after the position where the running balance of the circuit is lowest (when several starts are valid, this returns one of them, not necessarily the smallest index).

```java
public static int canCompleteCircuitPrefix(int[] gas, int[] cost) {
    int balance = 0, minBalance = Integer.MAX_VALUE, minIndex = -1;
    for (int i = 0; i < gas.length; i++) {
        balance += gas[i] - cost[i];
        if (balance < minBalance) { minBalance = balance; minIndex = i; }
    }
    return balance >= 0 ? (minIndex + 1) % gas.length : -1;
}
```

- **Time Complexity:** O(n)
- **Space Complexity:** O(1)

### Approach 4: Single Pass Greedy (Optimized)

One scan with a total balance and a running tank. This is the version implemented in the repository.

```java
public static int canCompleteCircuit(int[] gas, int[] cost) {
    int total = 0, tank = 0, start = 0;
    for (int i = 0; i < gas.length; i++) {
        int diff = gas[i] - cost[i];
        total += diff;
        tank += diff;
        if (tank < 0) { start = i + 1; tank = 0; }
    }
    return total >= 0 ? start : -1;
}
```

- **Time Complexity:** O(n)
- **Space Complexity:** O(1)

### Comparison

| # | Approach | Time | Space |
|---|----------|------|-------|
| 1 | Brute Force Simulation | O(n^2) | O(1) |
| 2 | Skip Failed Starts | O(n) | O(1) |
| 3 | Prefix Sum Minimum | O(n) | O(1) |
| 4 | Single Pass Greedy (Optimized) | O(n) | O(1) |

### Optimized Solution

The optimized solution is Approach 4 above, which is also the version implemented in this repository.

#### Optimized solution explained

The idea: two running sums. `total` is the overall balance `sum(gas - cost)`; if it is negative no start can work, so the answer is `-1`. `tank` is the balance since the current candidate `start`. When `tank` drops below 0 at station `i`, no station from `start` to `i` can be a valid start (each would have arrived at `i` with even less), so the next candidate is `i + 1` and the tank resets. If `total >= 0`, the last candidate survives and is the answer.

**Input:** `gas = [1, 2, 3, 4, 5]`, `cost = [3, 4, 5, 1, 2]`.

| i | diff = gas - cost | total | tank | tank < 0? | start |
|---|-------------------|-------|------|-----------|-------|
| 0 | 1 - 3 = -2 | -2 | -2 | yes, reset | 1 |
| 1 | 2 - 4 = -2 | -4 | -2 | yes, reset | 2 |
| 2 | 3 - 5 = -2 | -6 | -2 | yes, reset | 3 |
| 3 | 4 - 1 = 3 | -3 | 3 | no | 3 |
| 4 | 5 - 2 = 3 | 0 | 6 | no | 3 |

`total = 0 >= 0`, so the result is `start = 3`.

**Edge cases:** for `gas = [2, 3, 4]`, `cost = [3, 4, 3]` the diffs are -1, -1, 1, so `total = -1 < 0` and the method returns `-1` even though `start` ended as 2. A single station such as `[5]`, `[4]` has `total = 1`, `tank = 1`, and returns `0`. An empty array would return `0` (total 0, start 0).

**Result:** returns `3`, matching the class output.

## Source Code

### GasStation.java

```java
import java.util.Arrays;

public class GasStation {

    public static void main(String[] args) {
        int[][] gas = {{1, 2, 3, 4, 5}, {2, 3, 4}, {5}, {5, 1, 2, 3, 4}};
        int[][] cost = {{3, 4, 5, 1, 2}, {3, 4, 3}, {4}, {4, 4, 1, 5, 1}};
        for (int i = 0; i < gas.length; i++) {
            System.out.println("Input  : gas = " + Arrays.toString(gas[i]) + ", cost = " + Arrays.toString(cost[i]));
            System.out.println("Output : " + canCompleteCircuit(gas[i], cost[i]));
            System.out.println();
        }
    }

    public static int canCompleteCircuit(int[] gas, int[] cost) {
        int total = 0;          // overall balance of the whole circuit
        int tank = 0;           // balance since the candidate start
        int start = 0;
        for (int i = 0; i < gas.length; i++) {
            int diff = gas[i] - cost[i];
            total += diff;
            tank += diff;
            if (tank < 0) {
                start = i + 1;
                tank = 0;
            }
        }
        return total >= 0 ? start : -1;
    }
}
```
