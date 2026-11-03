# Daily Temperatures

Given an array `temperatures` of daily temperatures, return an array `answer` where `answer[i]` is the number of days after day `i` until a warmer temperature occurs, or 0 if there is no such day.

## Algorithm Strategy (Monotonic Decreasing Stack of Indices)

1. Keep a stack of the indices of days that are still waiting for a warmer day; their temperatures are in decreasing order from bottom to top.
2. For each day `i`, while the stack is not empty and today is warmer than the temperature at the top index `j`, pop `j`: its answer is `i - j`.
3. Push `i` on the stack.
4. Days left on the stack at the end never found a warmer day, so their answer stays 0.

## Example walkthrough

- For `[73, 74, 75, 71, 69, 72, 76, 73]`: day 1 (74) resolves day 0; day 2 (75) resolves day 1; day 6 (76) resolves days 5, 4, 3 and 2 with answers 1, 2, 3 and 4.
- Result: `[1, 1, 4, 2, 1, 1, 0, 0]`.

## Complexity Analysis

- **Time Complexity:** `O(n)` — Each index is pushed and popped at most once.
- **Space Complexity:** `O(n)` — The stack.

## Approaches

The same problem can be solved in several ways. The approach marked (Optimized) is the one to use in practice.

### Approach 1: Brute Force

For every day scan forward until a warmer day is found.

```java
public static int[] dailyTemperaturesBrute(int[] temperatures) {
    int n = temperatures.length;
    int[] answer = new int[n];
    for (int i = 0; i < n; i++) {
        for (int j = i + 1; j < n; j++) {
            if (temperatures[j] > temperatures[i]) { answer[i] = j - i; break; }
        }
    }
    return answer;
}
```

- **Time Complexity:** O(n^2)
- **Space Complexity:** O(1) besides the output

### Approach 2: Right-to-Left with Jump Pointers

Process the days from the end. To find the next warmer day after `i`, start at `i + 1` and jump ahead using the already computed answers (each answer says how far the next warmer day of that day is), skipping days that cannot be warmer.

```java
public static int[] dailyTemperaturesJump(int[] temperatures) {
    int n = temperatures.length;
    int[] answer = new int[n];
    for (int i = n - 2; i >= 0; i--) {
        int j = i + 1;
        while (j < n && temperatures[j] <= temperatures[i]) {
            if (answer[j] == 0) { j = n; break; }          // no warmer day after j either
            j += answer[j];
        }
        if (j < n) answer[i] = j - i;
    }
    return answer;
}
```

- **Time Complexity:** O(n)
- **Space Complexity:** O(1) besides the output

### Approach 3: Right-to-Left with the Latest Index of Each Temperature

Temperatures are small integers (30 to 100), so remember for every temperature the most recent (nearest) day on which it occurred while scanning from the right. The next warmer day is the nearest among the days with a higher temperature.

```java
public static int[] dailyTemperaturesBuckets(int[] temperatures) {
    int n = temperatures.length;
    int[] answer = new int[n];
    int[] nextIndex = new int[101];
    Arrays.fill(nextIndex, Integer.MAX_VALUE);
    for (int i = n - 1; i >= 0; i--) {
        int warmer = Integer.MAX_VALUE;
        for (int t = temperatures[i] + 1; t <= 100; t++) warmer = Math.min(warmer, nextIndex[t]);
        if (warmer != Integer.MAX_VALUE) answer[i] = warmer - i;
        nextIndex[temperatures[i]] = i;
    }
    return answer;
}
```

- **Time Complexity:** O(n * R) where R = 70 possible temperatures
- **Space Complexity:** O(R)

### Approach 4: Monotonic Stack (Optimized)

Keep the days that are still waiting for a warmer day on a stack; a new day resolves every colder day on top of it. This is the version implemented in the repository.

```java
public static int[] dailyTemperatures(int[] temperatures) {
    int n = temperatures.length;
    int[] answer = new int[n];
    Deque<Integer> stack = new ArrayDeque<>();
    for (int i = 0; i < n; i++) {
        while (!stack.isEmpty() && temperatures[i] > temperatures[stack.peek()]) {
            int day = stack.pop();
            answer[day] = i - day;
        }
        stack.push(i);
    }
    return answer;
}
```

- **Time Complexity:** O(n)
- **Space Complexity:** O(n)

### Comparison

| # | Approach | Time | Space |
|---|----------|------|-------|
| 1 | Brute Force | O(n^2) | O(1) besides the output |
| 2 | Right-to-Left with Jump Pointers | O(n) | O(1) besides the output |
| 3 | Right-to-Left with the Latest Index of Each Temperature | O(n * R) where R = 70 possible temperatures | O(R) |
| 4 | Monotonic Stack (Optimized) | O(n) | O(n) |

### Optimized Solution

The optimized solution is Approach 4 above, which is also the version implemented in this repository.

## Source Code

### DailyTemperatures.java

```java
import java.util.ArrayDeque;
import java.util.Arrays;
import java.util.Deque;

public class DailyTemperatures {

    public static void main(String[] args) {
        int[][] inputs = {{73, 74, 75, 71, 69, 72, 76, 73}, {30, 40, 50, 60}, {30, 60, 90}, {90, 80, 70}};
        for (int[] temperatures : inputs) {
            System.out.println("Input  : temperatures = " + Arrays.toString(temperatures));
            System.out.println("Output : " + Arrays.toString(dailyTemperatures(temperatures)));
            System.out.println();
        }
    }

    public static int[] dailyTemperatures(int[] temperatures) {
        int n = temperatures.length;
        int[] answer = new int[n];
        Deque<Integer> stack = new ArrayDeque<>();
        for (int i = 0; i < n; i++) {
            while (!stack.isEmpty() && temperatures[i] > temperatures[stack.peek()]) {
                int day = stack.pop();
                answer[day] = i - day;
            }
            stack.push(i);
        }
        return answer;
    }
}
```
