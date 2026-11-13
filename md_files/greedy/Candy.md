# Candy

`n` children stand in a line, each with a rating `ratings[i]`. Give each child at least one candy, and give a child strictly more candies than an adjacent neighbour with a lower rating. Return the minimum total number of candies.

## Algorithm Strategy (Two Passes (Left-to-Right, Then Right-to-Left))

1. Start with one candy for everyone.
2. Left to right: if a child's rating is higher than the left neighbour's, give them one more candy than that neighbour. This satisfies every "higher than the left" constraint.
3. Right to left: if a child's rating is higher than the right neighbour's, they need at least one more than that neighbour; take the maximum of the current value and `candies[i + 1] + 1` so that the first pass is not undone.
4. The sum is the minimum, because each child received only the amount forced by the longest strictly increasing run reaching them from either side.

## Example walkthrough

- For `[1, 0, 2]`: the left pass gives `[1, 1, 2]`, and the right pass raises the first child because `1 > 0`: `[2, 1, 2]`.
- Total `5`. For `[1, 2, 2]` the result is `[1, 2, 1]` with total `4`; equal ratings need no extra candy.

## Complexity Analysis

- **Time Complexity:** `O(n)` — Two linear passes.
- **Space Complexity:** `O(n)` — The candy array.

## Approaches

The same problem can be solved in several ways. The approach marked (Optimized) is the one to use in practice.

### Approach 1: Repeat Until Stable

Start with one candy each and keep fixing violated neighbours until nothing changes.

```java
public static int candyRepeat(int[] ratings) {
    int n = ratings.length;
    int[] candies = new int[n];
    Arrays.fill(candies, 1);
    boolean changed = true;
    while (changed) {
        changed = false;
        for (int i = 0; i < n; i++) {
            if (i > 0 && ratings[i] > ratings[i - 1] && candies[i] <= candies[i - 1]) { candies[i] = candies[i - 1] + 1; changed = true; }
            if (i + 1 < n && ratings[i] > ratings[i + 1] && candies[i] <= candies[i + 1]) { candies[i] = candies[i + 1] + 1; changed = true; }
        }
    }
    int total = 0;
    for (int c : candies) total += c;
    return total;
}
```

- **Time Complexity:** O(n^2) in the worst case
- **Space Complexity:** O(n)

### Approach 2: One Pass with Slopes (Constant Space)

Track the length of the current increasing run `up`, the current decreasing run `down` and the length of the last peak's ascent. A descent of length `down` adds `down` candies, and the peak is raised by one only when the descent is longer than its ascent.

```java
public static int candySlopes(int[] ratings) {
    int n = ratings.length;
    if (n <= 1) return n;
    int total = 1, up = 0, down = 0, peak = 0;
    for (int i = 1; i < n; i++) {
        if (ratings[i] > ratings[i - 1]) {
            up++; peak = up; down = 0;
            total += 1 + up;
        } else if (ratings[i] == ratings[i - 1]) {
            up = 0; peak = 0; down = 0;
            total += 1;
        } else {
            up = 0; down++;
            total += 1 + down - (peak >= down ? 1 : 0);
        }
    }
    return total;
}
```

- **Time Complexity:** O(n)
- **Space Complexity:** O(1)

### Approach 3: Two Passes (Optimized)

A left-to-right pass handles rising ratings and a right-to-left pass handles falling ratings, taking the maximum of the two requirements. This is the version implemented in the repository.

```java
public static int candy(int[] ratings) {
    int n = ratings.length;
    int[] candies = new int[n];
    Arrays.fill(candies, 1);
    for (int i = 1; i < n; i++) {
        if (ratings[i] > ratings[i - 1]) {
            candies[i] = candies[i - 1] + 1;
        }
    }
    for (int i = n - 2; i >= 0; i--) {
        if (ratings[i] > ratings[i + 1]) {
            candies[i] = Math.max(candies[i], candies[i + 1] + 1);
        }
    }
    int total = 0;
    for (int c : candies) {
        total += c;
    }
    return total;
}
```

- **Time Complexity:** O(n)
- **Space Complexity:** O(n)

### Comparison

| # | Approach | Time | Space |
|---|----------|------|-------|
| 1 | Repeat Until Stable | O(n^2) in the worst case | O(n) |
| 2 | One Pass with Slopes (Constant Space) | O(n) | O(1) |
| 3 | Two Passes (Optimized) | O(n) | O(n) |

### Optimized Solution

The optimized solution is Approach 3 above, which is also the version implemented in this repository.

## Source Code

### Candy.java

```java
import java.util.Arrays;

public class Candy {

    public static void main(String[] args) {
        int[][] inputs = {{1, 0, 2}, {1, 2, 2}, {1, 3, 2, 2, 1}, {5}};
        for (int[] ratings : inputs) {
            System.out.println("Input  : ratings = " + Arrays.toString(ratings));
            System.out.println("Output : " + candy(ratings));
            System.out.println();
        }
    }

    public static int candy(int[] ratings) {
        int n = ratings.length;
        int[] candies = new int[n];
        Arrays.fill(candies, 1);
        for (int i = 1; i < n; i++) {
            if (ratings[i] > ratings[i - 1]) {
                candies[i] = candies[i - 1] + 1;
            }
        }
        for (int i = n - 2; i >= 0; i--) {
            if (ratings[i] > ratings[i + 1]) {
                candies[i] = Math.max(candies[i], candies[i + 1] + 1);
            }
        }
        int total = 0;
        for (int c : candies) {
            total += c;
        }
        return total;
    }
}
```
