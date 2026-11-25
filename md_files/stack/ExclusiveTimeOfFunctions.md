# Exclusive Time of Functions

A single-threaded CPU executes `n` functions, each with an id from 0 to `n - 1`. Given log lines like `"0:start:3"` and `"1:end:4"` (function id, event, timestamp), return the exclusive time of each function: the time spent in the function itself, not counting the time spent in functions it called. A function's `end` timestamp is the end of that unit of time, so `start:0` followed by `end:1` is 2 units.

## Algorithm Strategy (Stack of Running Functions)

1. Keep a stack of function ids and the timestamp `prev` when the current function last started or resumed.
2. On `start`: if the stack is not empty, add `time - prev` to the function on top (it ran until now), then push the new function and set `prev = time`.
3. On `end`: pop the function, add `time - prev + 1` to it, and set `prev = time + 1` so the caller resumes after this unit.
4. The totals per function id are the answer.

## Example walkthrough

- For `["0:start:0","1:start:2","1:end:5","0:end:6"]`: function 1 runs 2 to 5 (4 units); function 0 runs 0 to 1 and 6 (3 units).
- Result: `[3, 4]`.

## Complexity Analysis

- **Time Complexity:** `O(m)` — `m` is the number of log lines.
- **Space Complexity:** `O(n)` — The stack and the result.

## Approaches

The same problem can be solved in several ways. The approach marked (Optimized) is the one to use in practice.

### Approach 1: Per-Timestamp Simulation

Walk every unit of time and charge it to the function on top of the stack. Linear in the time span, not in the log size.

```java
public static int[] perUnit(int n, java.util.List<String> logs) {
    int[] r = new int[n]; java.util.Deque<Integer> st = new java.util.ArrayDeque<>(); int t = 0; int idx = 0;
    String[] p = null; if (!logs.isEmpty()) p = logs.get(0).split(":");
    int end = 0; for (String l : logs) end = Math.max(end, Integer.parseInt(l.split(":")[2]) + 1);
    for (t = 0; t < end; t++) {
        while (idx < logs.size()) { String[] q = logs.get(idx).split(":"); int id = Integer.parseInt(q[0]), ts = Integer.parseInt(q[2]);
            if (q[1].equals("start") && ts == t) { st.push(id); idx++; } else break; }
        if (!st.isEmpty()) r[st.peek()]++;
        while (idx < logs.size()) { String[] q = logs.get(idx).split(":"); int ts = Integer.parseInt(q[2]); if (q[1].equals("end") && ts == t) { st.pop(); idx++; } else break; }
    }
    return r;
}
```

- **Time Complexity:** O(span + m)
- **Space Complexity:** O(n)

### Approach 2: Stack with Previous Timestamp (Optimized)

Charge the time between events to the function on top of the stack. This is the version implemented in the repository.

```java
public static int[] stack(int n, java.util.List<String> logs) {
    int[] r = new int[n]; java.util.Deque<Integer> st = new java.util.ArrayDeque<>(); int prev = 0;
    for (String l : logs) { String[] p = l.split(":"); int id = Integer.parseInt(p[0]), t = Integer.parseInt(p[2]);
        if (p[1].equals("start")) { if (!st.isEmpty()) r[st.peek()] += t - prev; st.push(id); prev = t; } else { r[st.pop()] += t - prev + 1; prev = t + 1; } }
    return r;
}
```

- **Time Complexity:** O(m)
- **Space Complexity:** O(n)

### Comparison

| # | Approach | Time | Space |
|---|----------|------|-------|
| 1 | Per-Timestamp Simulation | O(span + m) | O(n) |
| 2 | Stack with Previous Timestamp (Optimized) | O(m) | O(n) |

## Source Code

### ExclusiveTimeOfFunctions.java

```java
import java.util.ArrayDeque;
import java.util.Arrays;
import java.util.Deque;
import java.util.List;

public class ExclusiveTimeOfFunctions {

    public static void main(String[] args) {
        int[] ns = {2, 1, 2};
        String[][] logs = {{"0:start:0", "1:start:2", "1:end:5", "0:end:6"}, {"0:start:0", "0:start:2", "0:end:5", "0:start:6", "0:end:6", "0:end:7"}, {"0:start:0", "0:start:2", "0:end:5", "1:start:6", "1:end:6", "0:end:7"}};
        for (int i = 0; i < ns.length; i++) {
            System.out.println("Input  : n = " + ns[i] + ", logs = " + Arrays.toString(logs[i]));
            System.out.println("Output : " + Arrays.toString(exclusiveTime(ns[i], Arrays.asList(logs[i]))));
            System.out.println();
        }
    }

    public static int[] exclusiveTime(int n, List<String> logs) {
        int[] result = new int[n];
        Deque<Integer> stack = new ArrayDeque<>();
        int prev = 0;
        for (String log : logs) {
            String[] parts = log.split(":");
            int id = Integer.parseInt(parts[0]);
            int time = Integer.parseInt(parts[2]);
            if (parts[1].equals("start")) {
                if (!stack.isEmpty()) {
                    result[stack.peek()] += time - prev;
                }
                stack.push(id);
                prev = time;
            } else {
                result[stack.pop()] += time - prev + 1;
                prev = time + 1;
            }
        }
        return result;
    }
}
```

## Solution

The optimized solution is Approach 2 above, which is also the version implemented in this repository.

The idea: the function on top of the stack is the one running. Between two consecutive events the time belongs to whoever is on top, and an `end` log covers its own last unit of time, which is why one is added.

**Input:** `["0:start:0", "1:start:2", "1:end:5", "0:end:6"]`.

| Log | Action | `result` | `prev` |
|-----|--------|----------|--------|
| 0:start:0 | push 0 | [0, 0] | 0 |
| 1:start:2 | charge 0: 2 - 0 = 2, push 1 | [2, 0] | 2 |
| 1:end:5 | pop 1: 5 - 2 + 1 = 4 | [2, 4] | 6 |
| 0:end:6 | pop 0: 6 - 6 + 1 = 1 | [3, 4] | 7 |

**Output:** `[3, 4]`.
