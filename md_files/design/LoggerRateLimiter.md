# Logger Rate Limiter

Design a `Logger` with `shouldPrintMessage(timestamp, message)`. A message may be printed only if it has not been printed in the last 10 seconds; the method returns true (and records the print) when the message is allowed and false otherwise. Timestamps are in seconds and never decrease.

## Algorithm Strategy (HashMap of the Next Allowed Time)

1. Keep a map from each message to the earliest timestamp at which it may be printed again.
2. If the message is not in the map, or its stored time is at most the current timestamp, allow it and store `timestamp + 10`.
3. Otherwise reject it and leave the map unchanged: a rejected message must not extend its own blocking window.
4. Each call is a constant number of hash operations. The map grows with the number of distinct messages, which is the memory trade-off.

## Example walkthrough

- `(1, "foo")` is new: print, store 11. `(2, "bar")`: print, store 12. `(3, "foo")`: 3 < 11, reject. `(8, "bar")`: 8 < 12, reject.
- `(10, "foo")`: reject. `(11, "foo")`: 11 >= 11, print and store 21.

## Complexity Analysis

- **Time Complexity:** `O(1)` — Average, one map lookup and update.
- **Space Complexity:** `O(m)` — `m` distinct messages in the map.

## Approaches

The same problem can be solved in several ways. The approach marked (Optimized) is the one to use in practice.

### Approach 1: Queue Plus Set with Cleanup

Keep recent messages in a queue (oldest first) and a set of the messages currently blocked. Before each call, drop the queue entries older than 10 seconds from both. Memory is bounded by the messages of the last 10 seconds.

```java
class QueueLogger {
    private final Deque<Object[]> queue = new ArrayDeque<>();   // {timestamp, message}
    private final Set<String> blocked = new HashSet<>();

    boolean shouldPrintMessage(int timestamp, String message) {
        while (!queue.isEmpty() && (Integer) queue.peekFirst()[0] <= timestamp - 10) {
            blocked.remove((String) queue.pollFirst()[1]);
        }
        if (blocked.contains(message)) return false;
        blocked.add(message);
        queue.addLast(new Object[]{timestamp, message});
        return true;
    }
}
```

- **Time Complexity:** O(1) amortized
- **Space Complexity:** O(messages in the last 10 seconds)

### Approach 2: HashMap of Next Allowed Time (Optimized)

Store `timestamp + 10` per message and compare on each call; simple and constant time, but the map never shrinks. This is the version implemented in the repository.

```java
private static final int INTERVAL = 10;
private final Map<String, Integer> nextAllowed = new HashMap<>();

public boolean shouldPrintMessage(int timestamp, String message) {
    Integer allowedAt = nextAllowed.get(message);
    if (allowedAt != null && timestamp < allowedAt) {
        return false;
    }
    nextAllowed.put(message, timestamp + INTERVAL);
    return true;
}
```

- **Time Complexity:** O(1)
- **Space Complexity:** O(distinct messages)

### Comparison

| # | Approach | Time | Space |
|---|----------|------|-------|
| 1 | Queue Plus Set with Cleanup | O(1) amortized | O(messages in the last 10 seconds) |
| 2 | HashMap of Next Allowed Time (Optimized) | O(1) | O(distinct messages) |

### Optimized Solution

The optimized solution is Approach 2 above, which is also the version implemented in this repository.

#### Optimized solution explained

The idea: instead of remembering when a message was last printed and doing arithmetic on every call, store the **next allowed time** for each message in a `HashMap`. A message may print when it has never been seen or when `timestamp >= nextAllowed`; printing sets `nextAllowed = timestamp + 10`. A rejected call changes nothing, so rejections do not extend the wait.

**Input:** `shouldPrintMessage` called with `(1, foo)`, `(2, bar)`, `(3, foo)`, `(8, bar)`, `(10, foo)`, `(11, foo)`.

| Step | Call | Stored next-allowed time | Decision | Map afterwards | Result |
|------|------|--------------------------|----------|----------------|--------|
| 1 | (1, foo) | none | print, set `foo -> 11` | `{foo=11}` | `true` |
| 2 | (2, bar) | none | print, set `bar -> 12` | `{foo=11, bar=12}` | `true` |
| 3 | (3, foo) | 11 | 3 < 11, reject | unchanged | `false` |
| 4 | (8, bar) | 12 | 8 < 12, reject | unchanged | `false` |
| 5 | (10, foo) | 11 | 10 < 11, reject | unchanged | `false` |
| 6 | (11, foo) | 11 | 11 < 11 is false, print, set `foo -> 21` | `{foo=21, bar=12}` | `true` |

Step 6 shows the boundary: exactly 10 seconds after the first print the message is allowed again.

**Edge cases.** The first time a message appears, `get` returns `null` and it is always printed. Different messages never affect each other. The map is never cleaned, so memory grows with the number of distinct messages. Each call is O(1). No diagram is added; the table is the picture.

**Result:** `true, true, false, false, false, true`, exactly what the class prints.

## Source Code

### LoggerRateLimiter.java

```java
import java.util.HashMap;
import java.util.Map;

public class LoggerRateLimiter {

    public static void main(String[] args) {
        LoggerRateLimiter logger = new LoggerRateLimiter();
        int[] times = {1, 2, 3, 8, 10, 11};
        String[] messages = {"foo", "bar", "foo", "bar", "foo", "foo"};
        StringBuilder input = new StringBuilder();
        StringBuilder output = new StringBuilder();
        for (int i = 0; i < times.length; i++) {
            input.append(i == 0 ? "" : ", ").append("(").append(times[i]).append(", ").append(messages[i]).append(")");
            output.append(i == 0 ? "" : ", ").append(logger.shouldPrintMessage(times[i], messages[i]));
        }
        System.out.println("Input  : shouldPrintMessage" + input);
        System.out.println("Output : " + output);
    }

    private static final int INTERVAL = 10;
    private final Map<String, Integer> nextAllowed = new HashMap<>();

    public boolean shouldPrintMessage(int timestamp, String message) {
        Integer allowedAt = nextAllowed.get(message);
        if (allowedAt != null && timestamp < allowedAt) {
            return false;
        }
        nextAllowed.put(message, timestamp + INTERVAL);
        return true;
    }
}
```
