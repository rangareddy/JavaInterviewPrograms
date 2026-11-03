# Koko Eating Bananas

Koko has `piles[i]` bananas in the i-th pile and `h` hours before the guards return. She chooses an eating speed `k` (bananas per hour); each hour she eats up to `k` bananas from one pile and does not start another pile in the same hour. Return the minimum integer `k` that lets her finish all piles within `h` hours.

## Algorithm Strategy (Binary Search on the Answer)

1. The time needed at speed `k` is `sum(ceil(pile / k))` and it never increases when `k` grows, so the feasible speeds form a range `[answer, max pile]`.
2. Binary-search the speed between 1 and the size of the largest pile (eating one whole pile per hour always works if `h >= piles.length`).
3. For a candidate `mid`, compute the hours needed. If they are at most `h`, `mid` is feasible: remember it and try a smaller speed; otherwise try a larger one.
4. The smallest feasible speed found is the answer. Compute `ceil(p / k)` as `(p + k - 1) / k` to stay in integer arithmetic.

## Example walkthrough

- For `[3, 6, 7, 11]` and `h = 8`: speed 4 needs `1 + 2 + 2 + 3 = 8` hours (feasible), speed 3 needs `1 + 2 + 3 + 4 = 10` hours (not feasible).
- Result: `4`.

## Complexity Analysis

- **Time Complexity:** `O(n log M)` — `n` piles, `M` the size of the largest pile.
- **Space Complexity:** `O(1)` — A few variables.

## Approaches

The same problem can be solved in several ways. The approach marked (Optimized) is the one to use in practice.

### Approach 1: Try Every Speed from 1 Upwards

Test the speeds 1, 2, 3, ... and return the first one that finishes in time. Simple, but linear in the answer, which can be as large as the biggest pile.

```java
public static int minEatingSpeedLinear(int[] piles, int h) {
    for (int speed = 1; ; speed++) {
        long hours = 0;
        for (int pile : piles) hours += (pile + speed - 1) / speed;
        if (hours <= h) return speed;
    }
}
```

- **Time Complexity:** O(n * M)
- **Space Complexity:** O(1)

### Approach 2: Linear Search from the Lower Bound

The speed can never be lower than `ceil(total / h)` (otherwise the total number of bananas could not be eaten in `h` hours), so start the linear search there.

```java
public static int minEatingSpeedBound(int[] piles, int h) {
    long total = 0;
    for (int pile : piles) total += pile;
    for (int speed = (int) ((total + h - 1) / h); ; speed++) {
        long hours = 0;
        for (int pile : piles) hours += (pile + speed - 1) / speed;
        if (hours <= h) return speed;
    }
}
```

- **Time Complexity:** O(n * (M - lower bound))
- **Space Complexity:** O(1)

### Approach 3: Binary Search on the Speed (Optimized)

The hours needed never grow with the speed, so binary-search the smallest feasible speed between 1 and the largest pile. This is the version implemented in the repository.

```java
public static int minEatingSpeed(int[] piles, int h) {
    int low = 1, high = 0;
    for (int pile : piles) high = Math.max(high, pile);
    int answer = high;
    while (low <= high) {
        int mid = low + (high - low) / 2;
        long hours = 0;
        for (int pile : piles) hours += (pile + mid - 1) / mid;
        if (hours <= h) { answer = mid; high = mid - 1; } else low = mid + 1;
    }
    return answer;
}
```

- **Time Complexity:** O(n log M)
- **Space Complexity:** O(1)

### Approach 4: Binary Search with Tight Bounds

Narrow the search range first: the answer lies between `ceil(total / h)` and `ceil(total / (h - n + 1))` (when `h - n + 1` slow piles can waste at most `n - 1` hours), and never above the largest pile. A smaller range means fewer feasibility checks.

```java
public static int minEatingSpeedTight(int[] piles, int h) {
    long total = 0;
    int max = 0, n = piles.length;
    for (int pile : piles) { total += pile; max = Math.max(max, pile); }
    int low = (int) ((total + h - 1) / h);
    int high = Math.min(max, (int) ((total + (h - n + 1) - 1) / (h - n + 1)));
    low = Math.max(low, 1);
    while (low < high) {
        int mid = low + (high - low) / 2;
        long hours = 0;
        for (int pile : piles) hours += (pile + mid - 1) / mid;
        if (hours <= h) high = mid; else low = mid + 1;
    }
    return low;
}
```

- **Time Complexity:** O(n log(range))
- **Space Complexity:** O(1)

### Comparison

| # | Approach | Time | Space |
|---|----------|------|-------|
| 1 | Try Every Speed from 1 Upwards | O(n * M) | O(1) |
| 2 | Linear Search from the Lower Bound | O(n * (M - lower bound)) | O(1) |
| 3 | Binary Search on the Speed (Optimized) | O(n log M) | O(1) |
| 4 | Binary Search with Tight Bounds | O(n log(range)) | O(1) |

### Optimized Solution

The optimized solution is Approach 3 above, which is also the version implemented in this repository.

## Source Code

### KokoEatingBananas.java

```java
import java.util.Arrays;

public class KokoEatingBananas {

    public static void main(String[] args) {
        int[][] piles = {{3, 6, 7, 11}, {30, 11, 23, 4, 20}, {30, 11, 23, 4, 20}, {1000000000}};
        int[] hours = {8, 5, 6, 2};
        for (int i = 0; i < piles.length; i++) {
            System.out.println("Input  : piles = " + Arrays.toString(piles[i]) + ", h = " + hours[i]);
            System.out.println("Output : " + minEatingSpeed(piles[i], hours[i]));
            System.out.println();
        }
    }

    public static int minEatingSpeed(int[] piles, int h) {
        int low = 1;
        int high = 0;
        for (int pile : piles) {
            high = Math.max(high, pile);
        }

        int answer = high;
        while (low <= high) {
            int mid = low + (high - low) / 2;
            if (hoursNeeded(piles, mid) <= h) {
                answer = mid;          // feasible: try a slower speed
                high = mid - 1;
            } else {
                low = mid + 1;
            }
        }
        return answer;
    }

    private static long hoursNeeded(int[] piles, int speed) {
        long hours = 0;
        for (int pile : piles) {
            hours += (pile + speed - 1) / speed;       // ceil(pile / speed)
        }
        return hours;
    }
}
```
