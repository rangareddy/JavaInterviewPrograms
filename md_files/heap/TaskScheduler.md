# Task Scheduler

Given a list of CPU tasks, each labelled with a letter, and a cooldown `n`, return the least number of intervals the CPU needs to finish all tasks. Each interval runs one task or stays idle, and two identical tasks must be separated by at least `n` intervals.

## Algorithm Strategy (Greedy Formula from the Most Frequent Task)

1. The most frequent task (frequency `maxFreq`) dictates the schedule: its executions form `maxFreq - 1` gaps that each need at least `n` other intervals, so a frame of `(maxFreq - 1) * (n + 1)` intervals comes before the last copy.
2. Add the last row: one interval for every task that has the maximum frequency (`countMax`), since they all appear in the final row.
3. The frame length is therefore `(maxFreq - 1) * (n + 1) + countMax`.
4. If there are so many different tasks that no idle time is needed, the answer is simply the number of tasks, so take `max(frame, tasks.length)`.

## Example walkthrough

- For `A A A B B B` and `n = 2`: `maxFreq = 3`, `countMax = 2`, frame `(3 - 1) * 3 + 2 = 8`: `A B _ A B _ A B`.
- The tasks count is 6 (smaller), so the answer is `8`. With `n = 0` the frame is 5, and `max(5, 6) = 6`.

## Complexity Analysis

- **Time Complexity:** `O(t)` — `t` = number of tasks; counting plus a scan of 26 counters.
- **Space Complexity:** `O(1)` — A 26-element counter array.

## Approaches

The same problem can be solved in several ways. The approach marked (Optimized) is the one to use in practice.

### Approach 1: Simulation with a Max-Heap and a Cooldown Queue

Simulate the CPU interval by interval: take the task with the most remaining executions from a max-heap, run it and park it in a cooldown queue until `n` intervals have passed; if the heap is empty the interval is idle.

```java
public static int leastIntervalHeap(char[] tasks, int n) {
    int[] counts = new int[26];
    for (char task : tasks) counts[task - 'A']++;
    PriorityQueue<Integer> heap = new PriorityQueue<>(Collections.reverseOrder());
    for (int count : counts) if (count > 0) heap.add(count);
    Queue<int[]> cooldown = new LinkedList<>();               // {remaining count, time when it may run again}
    int time = 0;
    while (!heap.isEmpty() || !cooldown.isEmpty()) {
        time++;
        if (!heap.isEmpty()) {
            int remaining = heap.poll() - 1;
            if (remaining > 0) cooldown.add(new int[]{remaining, time + n});
        }
        if (!cooldown.isEmpty() && cooldown.peek()[1] == time) heap.add(cooldown.poll()[0]);
    }
    return time;
}
```

- **Time Complexity:** O(t log 26)
- **Space Complexity:** O(26)

### Approach 2: Simulation Choosing the Best Available Task

In every interval scan the 26 counters for the available task (not run in the last `n` intervals) with the most executions left. Slower than the heap but needs no data structure beyond two small arrays.

```java
public static int leastIntervalScan(char[] tasks, int n) {
    int[] remaining = new int[26], lastRun = new int[26];
    Arrays.fill(lastRun, -n - 1);
    for (char task : tasks) remaining[task - 'A']++;
    int left = tasks.length, time = 0;
    while (left > 0) {
        int best = -1;
        for (int t = 0; t < 26; t++) {
            if (remaining[t] > 0 && time - lastRun[t] > n && (best == -1 || remaining[t] > remaining[best])) best = t;
        }
        if (best != -1) { remaining[best]--; lastRun[best] = time; left--; }
        time++;
    }
    return time;
}
```

- **Time Complexity:** O(t * 26) plus idle intervals
- **Space Complexity:** O(26)

### Approach 3: Counting the Idle Slots

Fill the `(maxFreq - 1) * n` idle slots between the copies of the most frequent task with the other tasks (each task can fill at most `maxFreq - 1` of them). Whatever remains empty is idle time, so the answer is the number of tasks plus the idle slots.

```java
public static int leastIntervalIdle(char[] tasks, int n) {
    int[] counts = new int[26];
    for (char task : tasks) counts[task - 'A']++;
    Arrays.sort(counts);
    int maxFreq = counts[25];
    int idleSlots = (maxFreq - 1) * n;
    for (int i = 24; i >= 0 && counts[i] > 0; i--) idleSlots -= Math.min(counts[i], maxFreq - 1);
    return tasks.length + Math.max(0, idleSlots);
}
```

- **Time Complexity:** O(t)
- **Space Complexity:** O(26)

### Approach 4: Closed Formula (Optimized)

The most frequent task forces a frame of `(maxFreq - 1) * (n + 1) + countMax` intervals; if there are enough different tasks, no idle time is needed and the answer is the number of tasks. This is the version implemented in the repository.

```java
public static int leastInterval(char[] tasks, int n) {
    int[] counts = new int[26];
    for (char task : tasks) counts[task - 'A']++;
    int maxFreq = 0, countMax = 0;
    for (int count : counts) maxFreq = Math.max(maxFreq, count);
    for (int count : counts) if (count == maxFreq) countMax++;
    return Math.max(tasks.length, (maxFreq - 1) * (n + 1) + countMax);
}
```

- **Time Complexity:** O(t)
- **Space Complexity:** O(26)

### Comparison

| # | Approach | Time | Space |
|---|----------|------|-------|
| 1 | Simulation with a Max-Heap and a Cooldown Queue | O(t log 26) | O(26) |
| 2 | Simulation Choosing the Best Available Task | O(t * 26) plus idle intervals | O(26) |
| 3 | Counting the Idle Slots | O(t) | O(26) |
| 4 | Closed Formula (Optimized) | O(t) | O(26) |

### Optimized Solution

The optimized solution is Approach 4 above, which is also the version implemented in this repository.

## Source Code

### TaskScheduler.java

```java
import java.util.Arrays;

public class TaskScheduler {

    public static void main(String[] args) {
        char[][] inputs = {
                {'A', 'A', 'A', 'B', 'B', 'B'},
                {'A', 'A', 'A', 'B', 'B', 'B'},
                {'A', 'A', 'A', 'A', 'A', 'A', 'B', 'C', 'D', 'E', 'F', 'G'},
                {'A', 'B', 'C', 'D'}
        };
        int[] cooldowns = {2, 0, 2, 3};
        for (int i = 0; i < inputs.length; i++) {
            System.out.println("Input  : tasks = " + Arrays.toString(inputs[i]) + ", n = " + cooldowns[i]);
            System.out.println("Output : " + leastInterval(inputs[i], cooldowns[i]));
            System.out.println();
        }
    }

    public static int leastInterval(char[] tasks, int n) {
        int[] counts = new int[26];
        for (char task : tasks) {
            counts[task - 'A']++;
        }

        int maxFreq = 0;
        for (int count : counts) {
            maxFreq = Math.max(maxFreq, count);
        }
        int countMax = 0;
        for (int count : counts) {
            if (count == maxFreq) {
                countMax++;
            }
        }
        return Math.max(tasks.length, (maxFreq - 1) * (n + 1) + countMax);
    }
}
```
