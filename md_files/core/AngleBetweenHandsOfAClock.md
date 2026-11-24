# Angle Between Hands of a Clock

Given an hour and a number of minutes, return the smaller angle, in degrees, between the hour hand and the minute hand of an analog clock.

## Algorithm Strategy (Degrees from 12 o'clock)

1. The minute hand moves 6 degrees per minute: `minuteAngle = minutes * 6`.
2. The hour hand moves 30 degrees per hour plus 0.5 degrees per minute: `hourAngle = (hour % 12) * 30 + minutes * 0.5`.
3. Take the absolute difference of the two angles.
4. The smaller angle is `min(diff, 360 - diff)`.

## Example walkthrough

- For 12:30: the hour hand is at `0 + 15 = 15` degrees and the minute hand at 180, a difference of 165.
- Result: `165.0`.

## Complexity Analysis

- **Time Complexity:** `O(1)` — A few arithmetic operations.
- **Space Complexity:** `O(1)` — No extra memory.

## Approaches

The same problem can be solved in several ways. The approach marked (Optimized) is the one to use in practice.

### Approach 1: Simulate in Minutes

Convert both hands to positions on a 720-minute clock face and compute the circular distance.

```java
public static double simulate(int hour, int minutes) {
    double h = ((hour % 12) * 60 + minutes) / 720.0 * 360, m = minutes / 60.0 * 360; double d = Math.abs(h - m); return Math.min(d, 360 - d);
}
```

- **Time Complexity:** O(1)
- **Space Complexity:** O(1)

### Approach 2: Angles from 12 o'clock (Optimized)

Use 6 degrees per minute and 30 + 0.5 degrees per hour hand step. This is the version implemented in the repository.

```java
public static double angles(int hour, int minutes) {
    double ha = (hour % 12) * 30 + minutes * 0.5, ma = minutes * 6; double d = Math.abs(ha - ma); return Math.min(d, 360 - d);
}
```

- **Time Complexity:** O(1)
- **Space Complexity:** O(1)

### Comparison

| # | Approach | Time | Space |
|---|----------|------|-------|
| 1 | Simulate in Minutes | O(1) | O(1) |
| 2 | Angles from 12 o'clock (Optimized) | O(1) | O(1) |

## Source Code

### AngleBetweenHandsOfAClock.java

```java
public class AngleBetweenHandsOfAClock {

    public static void main(String[] args) {
        int[][] inputs = {{12, 30}, {3, 30}, {3, 15}, {4, 50}, {12, 0}, {6, 0}};
        for (int[] time : inputs) {
            System.out.println("Input  : hour = " + time[0] + ", minutes = " + time[1]);
            System.out.println("Output : " + angleClock(time[0], time[1]));
            System.out.println();
        }
    }

    public static double angleClock(int hour, int minutes) {
        double hourAngle = (hour % 12) * 30 + minutes * 0.5;
        double minuteAngle = minutes * 6;
        double diff = Math.abs(hourAngle - minuteAngle);
        return Math.min(diff, 360 - diff);
    }
}
```

## Solution

The optimized solution is Approach 2 above, which is also the version implemented in this repository.

The idea: measure both hands as angles from the 12 o'clock position. The minute hand covers 360 degrees in 60 minutes, and the hour hand covers 360 degrees in 12 hours, creeping forward as the minutes pass.

**Input:** `hour = 12`, `minutes = 30`.

| Hand | Angle |
|------|-------|
| hour (`12 % 12 = 0` hours, plus 30 minutes * 0.5) | 15 |
| minute (30 * 6) | 180 |

Difference: `|15 - 180| = 165`, and `360 - 165 = 195`, so the smaller angle is 165.

**Output:** `165.0`.
